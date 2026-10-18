package com.yifei.nyctocosm.boss.emissary;

import com.yifei.nyctocosm.Content;
import com.yifei.nyctocosm.CommonEvents;
import com.yifei.nyctocosm.boss.Boss;
import com.yifei.nyctocosm.combat.Combat;
import com.yifei.nyctocosm.spell.ReflectableAttack;
import com.yifei.nyctocosm.spell.Reflectance;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

/** A persistent cloud/flame Pillars projectile, independent of its casting animation. */
public final class EmissaryPillar extends Entity {
    public enum Kind { FLAME, CLOUD }
    public static final int SOURCE_LIFETIME=300;
    static final float SOURCE_DAMAGE=35;
    private UUID owner;
    private UUID damageOwner;
    private Kind kind=Kind.CLOUD;
    private int age,missingOwnerTicks;

    public EmissaryPillar(EntityType<? extends EmissaryPillar> type,Level level){
        super(type,level);noPhysics=true;setNoGravity(true);setDeltaMovement(Vec3.ZERO);
    }

    public static void launch(ServerLevel level,Boss boss){
        // Source activate creates both markers from the same point, at yaw +60 (flame) / -60 (cloud).
        spawn(level,boss,Kind.FLAME,60);
        spawn(level,boss,Kind.CLOUD,-60);
    }
    private static void spawn(ServerLevel level,Boss boss,Kind kind,float yawOffset){
        var pillar=Content.EMISSARY_PILLAR.get().create(level);
        pillar.owner=boss.getUUID();pillar.damageOwner=boss.getUUID();pillar.kind=kind;pillar.setPos(boss.position());
        pillar.setYRot(boss.getYRot()+yawOffset);pillar.setXRot(0);level.addFreshEntity(pillar);
    }

    public Kind kindForTest(){return kind;}
    public UUID ownerForTest(){return owner;}
    public int ageForTest(){return age;}
    public void ageForTest(int value){age=Math.max(0,Math.min(SOURCE_LIFETIME,value));}
    public void configureForTest(Boss boss,Kind type){owner=boss.getUUID();damageOwner=boss.getUUID();kind=type;}
    public UUID damageOwnerForTest(){return damageOwner;}

    @Override public void tick(){
        super.tick();
        if(!(level() instanceof ServerLevel server))return;
        Entity found=owner==null?null:server.getEntity(owner);
        if(!(found instanceof Boss boss)){
            if(++missingOwnerTicks>20)discard();
            return;
        }
        if(!boss.isAlive()){discard();return;}
        missingOwnerTicks=0;
        boolean expiresThisTick=++age>=SOURCE_LIFETIME;
        if(expiresThisTick){
            CommonEvents.resonanceSound(server,position(),ambientSound(),1,30);
            discard();return;
        }

        Player target=server.getNearestPlayer(getX(),getY(),getZ(),300,candidate->
            candidate instanceof Player player&&!player.isSpectator()&&!player.getTags().contains("emissarydebug"));
        if(target!=null){
            Vec3 aim=target.position().subtract(position());
            if(aim.lengthSqr()>.0001){
                setYRot((float)(Math.toDegrees(Math.atan2(aim.z,aim.x))-90));
                setXRot((float)-Math.toDegrees(Math.asin(Math.max(-1,Math.min(1,aim.normalize().y)))));
            }
        }

        reflect(server);
        double speed=kind==Kind.CLOUD?.4:.6;
        Vec3 next=position().add(getLookAngle().scale(speed));
        if(!server.hasChunkAt(BlockPos.containing(next))){discard();return;}
        setPos(next);
        emitParticles(server);
        CommonEvents.resonanceSound(server,position(),ambientSound(),1,30);
        impactPlayers(server,damageOwner(server,boss));
        if(kind==Kind.FLAME)fireTrail(server);
    }

    /** 原包 slowcast_flame 每 tick 在弹丸沿途 fill 5×24×5 的火，只替换空气；蔓延仍受 doFireTick 管辖。 */
    private void fireTrail(ServerLevel server){
        var fire=net.minecraft.world.level.block.Blocks.FIRE.defaultBlockState();
        BlockPos origin=BlockPos.containing(getX(),getY(),getZ());
        for(int x=-2;x<=2;x++)for(int y=-3;y<=20;y++)for(int z=-2;z<=2;z++){
            BlockPos pos=origin.offset(x,y,z);
            if(server.getBlockState(pos).isAir())server.setBlock(pos,fire,3);
        }
    }

    private String ambientSound(){return kind==Kind.CLOUD
        ?"emissary.attack.pillars.ambient_cloud":"emissary.attack.pillars.ambient_flame";}

    private void reflect(ServerLevel server){
        var reflection=Reflectance.tryReflect(server,new ReflectableAttack(position(),damageOwner==null?owner:damageOwner,SOURCE_DAMAGE));
        if(reflection==null)return;
        damageOwner=reflection.owner().getUUID();
        setYRot(reflection.reflector().getYRot());setXRot(reflection.reflector().getXRot());
    }

    private LivingEntity damageOwner(ServerLevel server,Boss fallback){
        Entity found=damageOwner==null?null:server.getEntity(damageOwner);
        return found instanceof LivingEntity living&&living.isAlive()?living:fallback;
    }

    private void emitParticles(ServerLevel server){
        boolean cloud=kind==Kind.CLOUD;
        var primary=cloud?ParticleTypes.CLOUD:ParticleTypes.FLAME;
        server.sendParticles(primary,getX(),getY()+6,getZ(),cloud?50:50,.8,4,.8,.025);
        if(cloud){
            server.sendParticles(ParticleTypes.SWEEP_ATTACK,getX(),getY()+6,getZ(),7,.8,4,.8,.025);
            server.sendParticles(ParticleTypes.WAX_ON,getX(),getY()+6,getZ(),5,.8,4,.8,.025);
            server.sendParticles(ParticleTypes.ELECTRIC_SPARK,getX(),getY()+6,getZ(),2,.8,4,.8,.025);
        }else{
            server.sendParticles(ParticleTypes.SMOKE,getX(),getY()+6,getZ(),50,.8,4,.8,.025);
            server.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE,getX(),getY()+6,getZ(),6,.8,4,.8,.025);
            server.sendParticles(ParticleTypes.LAVA,getX(),getY()+6,getZ(),2,.8,4,.8,.025);
        }
        var ring=cloud?ParticleTypes.FIREWORK:ParticleTypes.FLAME;
        for(int y:new int[]{0,7,14})for(int degrees=0;degrees<360;degrees+=36){
            double radians=Math.toRadians(degrees);
            server.sendParticles(ring,getX()+Math.cos(radians)*2,getY()+y,getZ()+Math.sin(radians)*2,1,0,0,0,0);
        }
    }

    private void impactPlayers(ServerLevel server,LivingEntity boss){
        int x=BlockPos.containing(getX(),getY(),getZ()).getX();
        int y=BlockPos.containing(getX(),getY(),getZ()).getY();
        int z=BlockPos.containing(getX(),getY(),getZ()).getZ();
        AABB selector=new AABB(x-2,y-3,z-2,x+2,y+18,z+2);
        for(Player player:server.getEntitiesOfClass(Player.class,selector,candidate->{
            BlockPos feet=candidate.blockPosition();
            return !candidate.isSpectator()&&!candidate.getTags().contains("emissarydebug")&&candidate.hurtTime==0
                &&feet.getX()>=x-2&&feet.getX()<=x+1&&feet.getY()>=y-3&&feet.getY()<=y+17
                &&feet.getZ()>=z-2&&feet.getZ()<=z+1;
        })){
            if(kind==Kind.CLOUD){
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,40,0,true,false));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,40,2,true,false));
            }else{
                // 原包 impact_flame：命中点补一圈 3×1×3 的火。
                var fire=net.minecraft.world.level.block.Blocks.FIRE.defaultBlockState();
                BlockPos feet=player.blockPosition();
                for(int dx=-1;dx<=1;dx++)for(int dz=-1;dz<=1;dz++){
                    BlockPos pos=feet.offset(dx,0,dz);
                    if(server.getBlockState(pos).isAir())server.setBlock(pos,fire,3);
                }
            }
            Combat.hit(boss,player,SOURCE_DAMAGE,Combat.Element.DISSONANCE);
            server.sendParticles(ParticleTypes.EXPLOSION,player.getX(),player.getY(),player.getZ(),5,1,1,1,0);
            server.sendParticles(ParticleTypes.FLASH,player.getX(),player.getY(),player.getZ(),5,1,1,1,0);
            CommonEvents.resonanceSound(server,player.position(),kind==Kind.CLOUD
                ?"emissary.attack.pillars.impact_cloud":"emissary.attack.pillars.impact_flame",10,50);
        }
    }

    @Override protected void defineSynchedData(){}
    @Override protected void addAdditionalSaveData(CompoundTag tag){
        if(owner!=null)tag.putUUID("Owner",owner);if(damageOwner!=null)tag.putUUID("DamageOwner",damageOwner);tag.putString("Kind",kind.name());tag.putInt("Age",age);
    }
    @Override protected void readAdditionalSaveData(CompoundTag tag){
        owner=tag.hasUUID("Owner")?tag.getUUID("Owner"):null;
        damageOwner=tag.hasUUID("DamageOwner")?tag.getUUID("DamageOwner"):owner;
        try{kind=Kind.valueOf(tag.getString("Kind"));}catch(IllegalArgumentException ignored){kind=Kind.CLOUD;}
        age=Math.max(0,Math.min(SOURCE_LIFETIME,tag.getInt("Age")));setDeltaMovement(Vec3.ZERO);
    }
    @Override public Packet<ClientGamePacketListener> getAddEntityPacket(){return NetworkHooks.getEntitySpawningPacket(this);}
}
