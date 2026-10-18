package com.yifei.nyctocosm.spell;

import com.yifei.nyctocosm.Content;
import com.yifei.nyctocosm.CommonEvents;
import com.yifei.nyctocosm.NyctocosmMod;
import com.yifei.nyctocosm.combat.Combat;
import com.yifei.nyctocosm.spell.ReflectableAttack;
import com.yifei.nyctocosm.spell.Reflectance;
import com.yifei.nyctocosm.spell.SeelicField;
import com.yifei.nyctocosm.spell.Spell;
import com.yifei.nyctocosm.spell.SpellBolt;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

/** Persistent spell effects are separate from the focus, allowing overlapping casts. */
public final class SpellArea extends Entity {
    private static final EntityDataAccessor<Integer> SPELL=SynchedEntityData.defineId(SpellArea.class,EntityDataSerializers.INT);
    private UUID owner;
    private UUID target;
    private int life;
    private int gatheringTicks=26;
    private float reflectanceEnergy=20;
    private float submergenceDamage=Spell.SUBMERGENCE.damage;
    // 原包 raycast 四个局部起点：^1^^、^-1^1^、^^0.5^1、^^1^（rotated ~ 0）
    private static final Vec3[] SUBMERGENCE_STRANDS={new Vec3(1,0,0),new Vec3(-1,1,0),new Vec3(0,.5,1),new Vec3(0,1,0)};
    public SpellArea(EntityType<? extends SpellArea> type,Level level){super(type,level);}
    public static SpellArea create(ServerLevel level,Player player,Vec3 origin,Spell spell){
        SpellArea area=new SpellArea(Content.AREA.get(),level);area.owner=player.getUUID();area.life=spell.duration;area.entityData.set(SPELL,spell.ordinal());
        area.setPos(origin);area.setYRot(player.getYRot());area.setXRot(player.getXRot());level.addFreshEntity(area);return area;
    }
    public static SpellArea createSubmergence(ServerLevel level,Player player,LivingEntity target){
        return createSubmergence(level,player,target,Spell.SUBMERGENCE.damage);
    }
    public static SpellArea createSubmergence(ServerLevel level,Player player,LivingEntity target,float damage){
        SpellArea area=new SpellArea(Content.AREA.get(),level);area.owner=player.getUUID();area.target=target.getUUID();area.life=200;
        area.submergenceDamage=damage;
        area.entityData.set(SPELL,Spell.SUBMERGENCE.ordinal());area.setPos(target.getX(),target.getY()-5,target.getZ());level.addFreshEntity(area);
        // 原包 submerge 末行：每个受害者处播放 impact 音效
        CommonEvents.resonanceSound(level,target.position(),"spells.impact.submergence",10,50);
        return area;
    }
    public Spell spell(){return Spell.values()[Math.floorMod(entityData.get(SPELL),Spell.values().length)];}
    public UUID ownerId(){return owner;}
    /** 原包 block/384：按施法威力缩放反射器持续与容量。 */
    public void overrideReflectance(int scaledLife,float scaledEnergy){life=scaledLife;reflectanceEnergy=scaledEnergy;}
    public int remainingLifeForTest(){return life;}
    Player reflectionOwner(ServerLevel server){
        if(isRemoved()||spell()!=Spell.REFLECTANCE||owner==null)return null;
        if(!(server.getEntity(owner) instanceof Player player)||!player.isAlive())return null;
        return player;
    }
    void consumeReflectance(float damage){
        if(isRemoved()||spell()!=Spell.REFLECTANCE||damage<=0||!(level() instanceof ServerLevel server))return;
        reflectanceEnergy-=damage;
        CommonEvents.sound(server,position(),"spells.impact.reflectance",10);
        server.sendParticles(ParticleTypes.FLASH,getX(),getY(),getZ(),3,.2,.2,.2,0);
        if(reflectanceEnergy<=0)discard();
    }
    public static boolean preventsForcedMovement(Entity entity){
        var data=entity.getPersistentData();
        return data.contains("NyctocosmImmoveableUntil")&&data.getLong("NyctocosmImmoveableUntil")>=entity.level().getGameTime();
    }
    private void protectMovement(Player player){player.getPersistentData().putLong("NyctocosmImmoveableUntil",level().getGameTime()+1);}
    @Override protected void defineSynchedData(){entityData.define(SPELL,Spell.ADAMANCE.ordinal());}
    @Override public void tick(){
        super.tick();if(!(level() instanceof ServerLevel server))return;
        Player player=owner==null?null:level().getPlayerByUUID(owner);
        if(player==null||!player.isAlive()){discard();return;}
        if(spell()==Spell.REPELLANCE){
            if(!server.getBlockState(blockPosition()).is(TagKey.create(Registries.BLOCK,ResourceLocation.fromNamespaceAndPath(NyctocosmMod.ID,"passthrough")))){discard();return;}
            if(gatheringTicks>0){
                server.sendParticles(ParticleTypes.CLOUD,getX(),getY(),getZ(),3,.1,.1,.1,.3);
                if(--gatheringTicks>0)return;
                // Removing the source gathering tag also runs the movement branch on tick 26.
                server.sendParticles(ParticleTypes.EXPLOSION,getX(),getY(),getZ(),5,.4,.4,.4,0);
                server.sendParticles(ParticleTypes.CLOUD,getX(),getY(),getZ(),100,.1,.1,.1,.7);
            }
            if(life<=0){discard();return;}
            --life;
        }else if(--life<=0){discard();return;}
        switch(spell()){
            case SUBMERGENCE -> {
                Entity found=target==null?null:server.getEntity(target);
                if(!(found instanceof LivingEntity victim)||!victim.isAlive()||!Combat.canHit(player,victim)){discard();return;}
                int age=200-life;
                // 原包 block/165：减速 amp10 全程无条件生效
                victim.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,2,10,false,false));
                if(age<=80){
                    victim.addEffect(new MobEffectInstance(MobEffects.LEVITATION,2,2,false,false));
                }else{
                    if(age==81)Combat.hit(player,victim,submergenceDamage,Combat.Element.VOID);
                    Vec3 pull=position().subtract(victim.position());
                    if(pull.lengthSqr()<=1){discard();return;}
                    // 原包 block/175：tp ^ ^ ^1 强制传送穿墙入地，速度向量会撞地即停
                    Vec3 at=victim.position().add(pull.normalize());
                    if(victim instanceof net.minecraft.server.level.ServerPlayer sp)sp.connection.teleport(at.x,at.y,at.z,sp.getYRot(),sp.getXRot());
                    else victim.teleportTo(at.x,at.y,at.z);
                }
                // 原包 raycast：4 条 yaw 局部偏移触须朝 tendril 脚部采样，0.5 格/2 粒子，距 1 格止，60 步封顶
                float yawRad=(float)Math.toRadians(victim.getYRot());
                for(Vec3 local:SUBMERGENCE_STRANDS){
                    Vec3 start=victim.position().add(local.yRot(-yawRad));
                    Vec3 delta=position().subtract(start);double len=delta.length();
                    if(len<=1)continue;
                    Vec3 step=delta.scale(1/len);
                    for(int n=0;n<60&&n*.5<len-1;n++){
                        Vec3 point=start.add(step.scale(n*.5));
                        forceParticles(server,ParticleTypes.SOUL_FIRE_FLAME,point.x,point.y,point.z,2,0,0,0,.01);
                    }
                }
                forceParticles(server,ParticleTypes.REVERSE_PORTAL,getX(),getY()+5,getZ(),10,.1,.1,.1,.1);
            }
            case ADAMANCE -> {
                protectMovement(player);
                player.addEffect(new MobEffectInstance(MobEffects.LEVITATION,20,255,false,false));
                if(!player.isShiftKeyDown()){
                    // FakePlayer ignores teleportTo; update the server position as well.
                    player.setPos(getX(),getY(),getZ());
                    player.teleportTo(getX(),getY(),getZ());player.setDeltaMovement(Vec3.ZERO);player.fallDistance=0;
                }
            }
            case REFLECTANCE -> {
                Vec3 normal=getLookAngle();
                Vec3 behind=position().subtract(normal.scale(3));
                for(Player protectedPlayer:server.getEntitiesOfClass(Player.class,new net.minecraft.world.phys.AABB(behind,behind).inflate(3),candidate->candidate.position().distanceToSqr(behind)<=9))protectMovement(protectedPlayer);
                AABB projectileSearch=new AABB(position(),position()).inflate(Reflectance.SOURCE_RADIUS+1.0e-4);
                for(SpellBolt projectile:server.getEntitiesOfClass(SpellBolt.class,projectileSearch,
                    candidate->candidate.position().distanceToSqr(position())<=Reflectance.SOURCE_RADIUS*Reflectance.SOURCE_RADIUS+1.0e-7
                        &&!candidate.reflectedByReflectanceThisTick()&&!candidate.handlesOwnReflection())){
                    var reflection=Reflectance.tryReflect(server,new ReflectableAttack(projectile.position(),
                        projectile.getOwner()==null?null:projectile.getOwner().getUUID(),projectile.spellDamage()));
                    if(reflection==null)continue;
                    projectile.reflectFrom(reflection.owner(),reflection.direction());
                    server.sendParticles(ParticleTypes.FLASH,projectile.getX(),projectile.getY(),projectile.getZ(),5,.2,.2,.2,0);
                }
            }
            case VACANCE -> {
                pushEntities(server,player,6,1,.5,true);
                clearVacable(server);
            }
            case REPELLANCE -> {
                setPos(position().add(getLookAngle().scale(2)));
                clearVacable(server);
                if(!server.getBlockState(blockPosition()).is(TagKey.create(Registries.BLOCK,ResourceLocation.fromNamespaceAndPath(NyctocosmMod.ID,"passthrough")))){discard();return;}
                pushEntities(server,player,6,1.5,2,false);
                server.sendParticles(ParticleTypes.CLOUD,getX(),getY(),getZ(),20,4,4,4,.05);
                if(life==0)discard();
            }
            default -> {}
        }
        if(tickCount%5==0)server.sendParticles(ParticleTypes.ENCHANT,getX(),getY()+.5,getZ(),12,2,.5,2,.02);
    }
    // 1.20.1 的 force 粒子只有 per-player 重载，逐玩家发送
    private static void forceParticles(ServerLevel level,ParticleOptions type,double x,double y,double z,int count,double dx,double dy,double dz,double speed){
        for(var player:level.players())level.sendParticles(player,type,true,x,y,z,count,dx,dy,dz,speed);
    }
    private void clearVacable(ServerLevel level){
        var vacable=TagKey.create(Registries.BLOCK,ResourceLocation.fromNamespaceAndPath(NyctocosmMod.ID,"vacable"));
        for(BlockPos pos:BlockPos.betweenClosed(blockPosition().offset(-6,-6,-6),blockPosition().offset(6,6,6)))
            if(level.hasChunkAt(pos)&&level.getBlockState(pos).is(vacable))level.setBlock(pos,Blocks.AIR.defaultBlockState(),3);
    }
    private void pushEntities(ServerLevel level,Player player,double radius,double power,double playerStep,boolean radial){
        for(Entity entity:level.getEntities(this,getBoundingBox().inflate(radius))){
            if(entity==player||entity instanceof SpellArea||entity instanceof SeelicField||entity instanceof net.minecraft.world.entity.decoration.ArmorStand)continue;
            if(entity.distanceToSqr(position())>radius*radius)continue;
            if(!radial&&preventsForcedMovement(entity))continue;
            if(entity instanceof LivingEntity living&&!Combat.canHit(player,living))continue;
            if(!(entity instanceof LivingEntity)&&!(entity instanceof Projectile)&&!(entity instanceof net.minecraft.world.entity.item.ItemEntity))continue;
            // 'facing entity @s' is evaluated at the bubble; Repellance inherits the gust rotation.
            Vec3 direction=radial?entity.position().subtract(position()).normalize():getLookAngle();
            if(entity instanceof Player affected){
                if(!radial&&affected.isPassenger())continue;
                Vec3 at=entity.position().add(direction.scale(playerStep));
                if(level.noCollision(affected,affected.getBoundingBox().move(at.subtract(affected.position())))){
                    affected.teleportTo(at.x,at.y,at.z);affected.setPos(at);
                }
            }else{entity.setDeltaMovement(direction.scale(power));entity.hurtMarked=true;}
        }
    }
    @Override protected void addAdditionalSaveData(CompoundTag tag){if(owner!=null)tag.putUUID("Owner",owner);if(target!=null)tag.putUUID("Target",target);tag.putInt("Life",life);tag.putInt("GatheringTicks",gatheringTicks);tag.putInt("Spell",entityData.get(SPELL));tag.putFloat("ReflectanceEnergy",reflectanceEnergy);tag.putFloat("SubmergenceDamage",submergenceDamage);}
    @Override protected void readAdditionalSaveData(CompoundTag tag){
        owner=tag.hasUUID("Owner")?tag.getUUID("Owner"):null;target=tag.hasUUID("Target")?tag.getUUID("Target"):null;
        life=tag.getInt("Life");entityData.set(SPELL,tag.getInt("Spell"));reflectanceEnergy=tag.contains("ReflectanceEnergy")?tag.getFloat("ReflectanceEnergy"):20;
        submergenceDamage=tag.contains("SubmergenceDamage")?tag.getFloat("SubmergenceDamage"):Spell.SUBMERGENCE.damage;
        gatheringTicks=tag.contains("GatheringTicks")?Math.max(0,Math.min(26,tag.getInt("GatheringTicks"))):Math.max(0,26-(Spell.REPELLANCE.duration-life));
        if(spell()==Spell.REPELLANCE&&!tag.contains("GatheringTicks")&&gatheringTicks>0)life=Spell.REPELLANCE.duration;
    }
    @Override public Packet<ClientGamePacketListener> getAddEntityPacket(){return NetworkHooks.getEntitySpawningPacket(this);}
}
