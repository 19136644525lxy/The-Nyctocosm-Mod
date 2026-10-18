package com.yifei.nyctocosm.spell;

import com.yifei.nyctocosm.Content;
import com.yifei.nyctocosm.CommonEvents;
import com.yifei.nyctocosm.NyctocosmMod;
import com.yifei.nyctocosm.boss.serpent.SerpentMotion;
import com.yifei.nyctocosm.combat.Combat;
import com.yifei.nyctocosm.entity.SoulEntity;
import com.yifei.nyctocosm.network.Network;
import com.yifei.nyctocosm.spell.ExpulsionBurst;
import com.yifei.nyctocosm.spell.HypertraceService;
import com.yifei.nyctocosm.spell.ResonanceEffects;
import com.yifei.nyctocosm.spell.ResonanceSpell;
import com.yifei.nyctocosm.spell.Spell;
import com.yifei.nyctocosm.spell.SpellArea;
import com.yifei.nyctocosm.spell.SpellBolt;
import com.yifei.nyctocosm.spell.SpellCollisionPolicy;
import java.util.UUID;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import java.util.function.IntUnaryOperator;

/** One real, owned entity replaces the original field rig, marker and scoreboards. */
public final class SeelicField extends Entity {
    private static final EntityDataAccessor<Boolean> MOBILE=SynchedEntityData.defineId(SeelicField.class,EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> TRACE=SynchedEntityData.defineId(SeelicField.class,EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> SPELL=SynchedEntityData.defineId(SeelicField.class,EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> CHARGE=SynchedEntityData.defineId(SeelicField.class,EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> CAST=SynchedEntityData.defineId(SeelicField.class,EntityDataSerializers.INT);
    private static final EntityDataAccessor<CompoundTag> RESONANCE=SynchedEntityData.defineId(SeelicField.class,EntityDataSerializers.COMPOUND_TAG);
    private static final EntityDataAccessor<CompoundTag> LUMINOUS_CIRCLE=SynchedEntityData.defineId(SeelicField.class,EntityDataSerializers.COMPOUND_TAG);
    private UUID owner;
    private int life=-1, cooldown, element;
    public SeelicField(EntityType<? extends SeelicField> type,Level level) { super(type,level); }
    @Override protected void defineSynchedData() { entityData.define(MOBILE,false);entityData.define(TRACE,false);entityData.define(SPELL,-1);entityData.define(CHARGE,5);entityData.define(CAST,0);entityData.define(RESONANCE,new CompoundTag());entityData.define(LUMINOUS_CIRCLE,new CompoundTag()); }
    public void configure(Player player,boolean mobile,boolean trace) {
        owner=player.getUUID();entityData.set(MOBILE,mobile);entityData.set(TRACE,trace);
        // Source Dream power defaults to zero; its attribute modifiers belong to the still-open D22 pipeline.
        life=trace?400:mobile?500:-1;
        if(trace)entityData.set(CHARGE,1);
    }
    public boolean ownedBy(Player player) { return player.getUUID().equals(owner); }
    UUID ownerId(){return owner;}
    public void cancelCast(){
        entityData.set(CAST,0);entityData.set(SPELL,-1);entityData.set(RESONANCE,new CompoundTag());
        // Source cancel_cast sets a mobile plane's duration to zero. The normal
        // plane tick removes it; fixed planes only have their cast cancelled.
        if(mobile())life=0;
    }
    public boolean isHypertrace() { return entityData.get(TRACE); }
    public boolean mobile() { return entityData.get(MOBILE); }
    public int charges() { return entityData.get(CHARGE); }
    void setElement(int value){element=value;}
    public void consume(int n) { entityData.set(CHARGE,Math.max(0,charges()-n));if(charges()==0) discard(); }
    public int castTicks() { return entityData.get(CAST); }
    public int remainingLife(){return life;}
    public void beginCasting(Spell selected){entityData.set(SPELL,selected.ordinal());entityData.set(CAST,selected.castTicks);}
    public void startCasting(ServerLevel server,Player player,Spell selected,IntUnaryOperator traceRoll){
        beginCasting(selected);
        // 原包 cast：施法时间减去 speed% 加成（cast_time -= cast_time * speed / 100）。
        int speed=SpellStats.speed(player,null)+SpellStats.speed(player,selected.elements[0]);
        if(speed>0)entityData.set(CAST,Math.max(0,castTicks()-castTicks()*speed/100));
        HypertraceService.onSpellStarted(server,player,selected,traceRoll);
        if(selected!=Spell.LUMINESCENCE&&selected!=Spell.RESONANCE)CommonEvents.resonanceSound(server,position(),"spells.cast."+selected.id(),6,50);
    }
    public Spell spell() { int n=entityData.get(SPELL);return n<0?null:Spell.values()[Math.floorMod(n,Spell.values().length)]; }
    public CompoundTag luminousCircle(){return entityData.get(LUMINOUS_CIRCLE).copy();}
    public Player owner() { return owner==null?null:level().getPlayerByUUID(owner); }
    @Override public boolean isPickable() { return isHypertrace(); }
    // 原包 magic:hypertrace/pickup：右键回收 +1 充能、pickup 音效 master 20 格；潜行右键全收（pickup_all 逐个）
    @Override public InteractionResult interact(Player player,InteractionHand hand){
        if(level().isClientSide||!isHypertrace()||!ownedBy(player))return InteractionResult.PASS;
        if(level() instanceof ServerLevel server){
            java.util.List<SeelicField> targets=player.isShiftKeyDown()
                ?server.getEntitiesOfClass(SeelicField.class,new net.minecraft.world.phys.AABB(-1.0E6D,-1.0E6D,-1.0E6D,1.0E6D,1.0E6D,1.0E6D),e->e.isHypertrace()&&e.ownedBy(player))
                :java.util.List.of(this);
            for(SeelicField target:targets){
                CommonEvents.addHypertraceCharge(player,1);
                CommonEvents.soundMaster(server,target.position(),"magic.hypertrace.pickup",1,20);
                target.discard();
            }
        }
        return InteractionResult.CONSUME;
    }
    // 原包 magic:hypertrace/destroy：左键摧毁触发三饰品联动、destroy 音效 master 20 格；潜行左键全收（destroy_all 逐个）
    @Override public boolean hurt(DamageSource source,float damage) {
        if(level().isClientSide || !isHypertrace() || damage<=0) return false;
        Player player=owner();
        if(player!=null && source.getEntity()==player && level() instanceof ServerLevel server) {
            java.util.List<SeelicField> targets=player.isShiftKeyDown()
                ?server.getEntitiesOfClass(SeelicField.class,new net.minecraft.world.phys.AABB(-1.0E6D,-1.0E6D,-1.0E6D,1.0E6D,1.0E6D,1.0E6D),e->e.isHypertrace()&&e.ownedBy(player))
                :java.util.List.of(this);
            for(SeelicField target:targets)applyDestroyPerks(server,player,target);
            return true;
        }
        return false;
    }
    /** 原包 artifacts:hypertrace/destroy 联动：amelioration 治疗 7 点+firework 粒子、extraction 召 soul（玩家头顶 1 格）、explosion 爆炸；逐个播 destroy 音效。 */
    private void applyDestroyPerks(ServerLevel server,Player player,SeelicField target){
        if(CommonEvents.hasArtifact(player,"amelioration")){
            player.heal(7);
            player.addEffect(new MobEffectInstance(MobEffects.SATURATION,60,0,false,false));
            for(var p:server.players())server.sendParticles(p,ParticleTypes.FIREWORK,true,player.getX(),player.getY()+1,player.getZ(),50,.2,.2,.2,.2);
        }
        if(CommonEvents.hasArtifact(player,"extraction")){
            SoulEntity soul=Content.SOUL.get().create(server);
            if(soul!=null){soul.setPos(player.getX(),player.getY()+1,player.getZ());server.addFreshEntity(soul);}
            CommonEvents.soundMaster(server,player.position(),"spells.soul_reap",4,10);
        }
        if(CommonEvents.hasArtifact(player,"expulsion"))ExpulsionBurst.explode(server,player,target.position());
        CommonEvents.soundMaster(server,target.position(),"magic.hypertrace.destroy",1,20);
        target.discard();
    }
    @Override public void tick() {
        super.tick();
        if(!(level() instanceof ServerLevel server)) {
            if(spell()==Spell.RESONANCE&&castTicks()>0&&!entityData.get(RESONANCE).isEmpty())ResonanceEffects.clientCharge(level(),resonanceOrigin(),Spell.RESONANCE.castTicks-castTicks());
            return;
        }
        if(!entityData.get(LUMINOUS_CIRCLE).isEmpty()){
            var circle=entityData.get(LUMINOUS_CIRCLE).copy();int left=circle.getInt("Left")-1;
            if(left<=0)circle=new CompoundTag();else circle.putInt("Left",left);entityData.set(LUMINOUS_CIRCLE,circle);
        }
        if(isHypertrace()){
            tickHypertrace(server);
            return;
        }
        Player player=owner();
        if(mobile()){
            if(castTicks()==0&&--life<=0){discard();return;}
            // The source runs `execute at <linked player> run tp @s ~ ~ ~ ~ 0`:
            // @s remains the mobile marker, so it follows the player and
            // copies yaw with pitch fixed to zero. Its type is set at creation.
            if(player!=null&&player.isAlive()){
                setPos(player.position());setRot(player.getYRot(),0.0f);
            }
        }
        if(level().getEntitiesOfClass(Player.class,getBoundingBox().inflate(3),candidate->candidate.distanceToSqr(position())<=9).isEmpty()){
            discard();return;
        }
        boolean ownerAvailable=player!=null&&player.isAlive();
        if(ownerAvailable&&player.isShiftKeyDown()&&player.getXRot()>88){discard();return;}
        if(castTicks()>0) {
            entityData.set(CAST,castTicks()-1);
            if(ownerAvailable&&tickCount%5==0)player.displayClientMessage(Component.translatable("message.nyctocosm.casting",Component.translatable("spell.nyctocosm.native."+spell().id()),castTicks()),true);
            if(castTicks()==0){if(ownerAvailable)finish(server,player);else finishWithoutOwner();}
        } else if(cooldown>0) {
            cooldown--;
        } else if(ownerAvailable){
            for(ItemEntity item:level().getEntitiesOfClass(ItemEntity.class,getBoundingBox().inflate(3))) {
                if(item.getOwner()!=player || item.getAge()>80 || item.getAge()<2 || distanceToSqr(player)>9) continue;
                Spell selected=Spell.from(item.getItem().getItem());
                if(selected==null) continue;
                item.getItem().shrink(1); if(item.getItem().isEmpty())item.discard();
                startCasting(server,player,selected,bound->random.nextInt(bound));
                if(selected==Spell.RESONANCE)beginResonance(server,player);
                if(selected.castTicks==0) finish(server,player);
                break;
            }
        }
    }
    private void tickHypertrace(ServerLevel server){
        if(--life<=0){discard();return;}
        SeelicField nearest=server.getEntitiesOfClass(SeelicField.class,getBoundingBox().inflate(1),
            other->other!=this&&other.isHypertrace()&&other.distanceToSqr(this)<=1)
            .stream().min(java.util.Comparator.comparingDouble(this::distanceToSqr)).orElse(null);
        if(nearest!=null){
            Vec3 away=position().subtract(nearest.position()).normalize();
            if(away.lengthSqr()<1.0e-8)away=Vec3.directionFromRotation(0,getYRot());
            setYRot((float)(Math.toDegrees(Math.atan2(away.z,away.x))-90));
            setPos(position().add(away.scale(.2)));
        }
        float yaw=getYRot()+1;
        setYRot(yaw);setYHeadRot(yaw);setYBodyRot(yaw);
    }
    private void finishWithoutOwner(){
        entityData.set(CAST,0);entityData.set(SPELL,-1);entityData.set(RESONANCE,new CompoundTag());cooldown=50;
    }
    private Vec3 resonanceOrigin(){var tag=entityData.get(RESONANCE);return new Vec3(tag.getDouble("X"),tag.getDouble("Y"),tag.getDouble("Z"));}
    private void beginResonance(ServerLevel server,Player player){
        Vec3 at=player.position().add(0,1.625,0).add(SerpentMotion.localForward(player.getYRot(),player.getXRot()).scale(5));
        CompoundTag tag=new CompoundTag();tag.putDouble("X",at.x);tag.putDouble("Y",at.y);tag.putDouble("Z",at.z);tag.putFloat("Yaw",player.getYRot());tag.putFloat("Pitch",player.getXRot());entityData.set(RESONANCE,tag);
        CommonEvents.resonanceSound(server,position(),"spells.cast.resonance",6,50);
    }
    private void finish(ServerLevel server,Player player) {
        Spell s=spell(); if(s==null)return;
        cooldown=50;
        // 原包 cast：m.spell.power = 基础威力 + global.power + 元素.power，供伤害法术结算。
        int power=s.damage+SpellStats.power(player,null)+SpellStats.power(player,s.elements[0]);
        switch(s) {
            case ABHORRENCE, INCANDESCENCE, FULMINANCE, FORBIDDANCE, GRAVITANCE -> {
                var effect=s==Spell.INCANDESCENCE?SpellBolt.Effect.FIRE:s==Spell.FULMINANCE?SpellBolt.Effect.LIGHTNING:s==Spell.FORBIDDANCE?SpellBolt.Effect.COUNTER:s==Spell.GRAVITANCE?SpellBolt.Effect.GRAVITY:SpellBolt.Effect.DAMAGE;
                Vec3 castPoint=position().add(0,1.625,0);
                Player aimer=server.getEntitiesOfClass(Player.class,getBoundingBox().inflate(3),candidate->candidate.position().distanceToSqr(castPoint)<=4)
                    .stream().min(java.util.Comparator.comparingDouble(candidate->candidate.position().distanceToSqr(castPoint))).orElse(null);
                if(aimer!=null){
                    Vec3 direction=SerpentMotion.localForward(aimer.getYRot(),aimer.getXRot());
                    Vec3 origin=castPoint.add(s==Spell.INCANDESCENCE||s==Spell.FULMINANCE?direction.scale(4):SerpentMotion.localForward(aimer.getYRot(),0).scale(3));
                    float speed=s==Spell.GRAVITANCE?.3f:s==Spell.FORBIDDANCE?1.2f:s==Spell.FULMINANCE?9f:1.5f;
                    SpellBolt bolt=SpellBolt.launch(server,player,origin,direction,power,s.elements[0],speed,s==Spell.FORBIDDANCE?0:2).effect(effect,s.duration);
                    if(s==Spell.ABHORRENCE)bolt.playerAbhorrenceFlight();
                    if(s==Spell.FULMINANCE)bolt.playerFulminanceFlight();
                    if(s==Spell.GRAVITANCE)bolt.playerGravitanceFlight();
                    if(s==Spell.ABHORRENCE||s==Spell.INCANDESCENCE||s==Spell.FULMINANCE)
                        bolt.collisionPolicy(SpellCollisionPolicy.SOURCE_PASSTHROUGH);
                    else if(s==Spell.GRAVITANCE)bolt.collisionPolicy(SpellCollisionPolicy.SOURCE_HOLD_IN_BLOCKED_VOXEL);
                }
            }
            case LUMINESCENCE -> {
                var circle=new CompoundTag();circle.putDouble("X",getX());circle.putDouble("Y",getY());circle.putDouble("Z",getZ());circle.putFloat("Yaw",getYRot());circle.putInt("Left",20);entityData.set(LUMINOUS_CIRCLE,circle);
                for(Player ally:server.getEntitiesOfClass(Player.class,getBoundingBox().inflate(1),target->target.distanceToSqr(position())<=1)){
                    ally.addEffect(new MobEffectInstance(MobEffects.GLOWING,s.duration,0,false,false));
                    ally.removeEffect(MobEffects.DARKNESS);ally.removeEffect(MobEffects.BLINDNESS);
                    ally.getPersistentData().putLong("NyctocosmLuminous",server.getGameTime()+s.duration);
                    Network.sourceVisual(server,"radiance",ally.position(),ally.getYRot(),-90);
                }
            }
            case RESONANCE -> {
                if(entityData.get(RESONANCE).isEmpty())beginResonance(server,player);
                var aim=entityData.get(RESONANCE);
                ResonanceSpell.fire(server,player,resonanceOrigin(),SerpentMotion.localForward(aim.getFloat("Yaw"),aim.getFloat("Pitch")));
                entityData.set(RESONANCE,new CompoundTag());
            }
            case EVANESCENCE -> CommonEvents.recall(player);
            case INCURRENCE -> CommonEvents.incur(player,position());
            case SUBSISTENCE -> {
                for(Player ally:server.getEntitiesOfClass(Player.class,getBoundingBox().inflate(3),target->target.distanceToSqr(position())<=9)){
                    ally.heal(s.damage);ally.addEffect(new MobEffectInstance(MobEffects.SATURATION,60,0));
                    server.sendParticles(ParticleTypes.POOF,ally.getX(),ally.getY()+1,ally.getZ(),50,.2,.2,.2,.2);
                    Network.sourceVisual(server,"radiance",ally.position(),ally.getYRot(),-90);
                }
            }
            case SUBMERGENCE -> {
                // 原包 transmutate：damage = min(global+激活元素power, 50)*4 + 8，全体受害者共享同一结算值
                float spellPower=SpellStats.power(player,null);
                for(Combat.Element e:s.elements)spellPower+=SpellStats.power(player,e);
                float submergenceDamage=s.damage+4f*Math.min(50,spellPower);
                server.getEntitiesOfClass(LivingEntity.class,getBoundingBox().inflate(7),target->Combat.canHit(player,target)&&target.onGround()
                    &&distanceToSqr(target)>=9&&distanceToSqr(target)<=49&&!SpellArea.preventsForcedMovement(target))
                    .stream().sorted(java.util.Comparator.comparingDouble(this::distanceToSqr)).limit(6).forEach(target->SpellArea.createSubmergence(server,player,target,submergenceDamage));
            }
            case ADAMANCE, VACANCE, REFLECTANCE, REPELLANCE -> {
                Vec3 origin=position();
                Player aimer=player;
                if(s==Spell.REFLECTANCE||s==Spell.REPELLANCE){
                    Vec3 castPoint=position().add(0,1.625,0);
                    aimer=server.getEntitiesOfClass(Player.class,getBoundingBox().inflate(3),candidate->candidate.position().distanceToSqr(castPoint)<=4)
                        .stream().min(java.util.Comparator.comparingDouble(candidate->candidate.position().distanceToSqr(castPoint))).orElse(null);
                    if(aimer==null)break;
                    origin=castPoint.add(aimer.getLookAngle().scale(s==Spell.REPELLANCE?3:4));
                }
                SpellArea area=SpellArea.create(server,player,origin,s);
                if(s==Spell.REFLECTANCE){
                    // 原包 block/384：持续=200+power×20，容量=20+power×10，power=min(50,global+resonance)
                    int reflectPower=Math.min(50,SpellStats.power(player,null)+SpellStats.power(player,Combat.Element.RESONANCE));
                    area.overrideReflectance(s.duration+reflectPower*20,s.damage+reflectPower*10);
                }
                area.setYRot(aimer.getYRot());area.setXRot(aimer.getXRot());
            }
            case RESISTANCE -> {
                for(Player ally:server.getEntitiesOfClass(Player.class,getBoundingBox().inflate(2),target->target.distanceToSqr(position())<=4)){
                    ally.getPersistentData().putLong("NyctocosmResistanceUntil",server.getGameTime()+s.duration);
                    CommonEvents.tickSpellStatuses(ally);
                    Network.sourceVisual(server,"radiance",ally.position(),ally.getYRot(),-90);
                    Network.sourceVisual(server,"resistance",ally.position(),0,0);
                }
            }
            case TRANSMITTANCE -> {
                // The source plane transports every player standing on it. Each passenger
                // raycasts along their own facing direction for the source base range (100).
                for(Player passenger:server.getEntitiesOfClass(Player.class,getBoundingBox().inflate(3))){
                    Vec3 at=transmittanceEndpoint(server,passenger,100);
                    Vec3 feet=passenger.isShiftKeyDown()?at:server.getBlockState(BlockPos.containing(at).above(2)).is(passthrough())?at.add(0,2,0):at;
                    if(server.noCollision(passenger,passenger.getBoundingBox().move(feet.subtract(passenger.position())))){
                        passenger.teleportTo(feet.x,feet.y,feet.z);passenger.fallDistance=0;
                        passenger.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING,120,4,false,false));
                        server.sendParticles(ParticleTypes.FLASH,feet.x,feet.y+1,feet.z,10,.3,.3,.3,0);
                    }
                }
            }
        }
        if(s!=Spell.LUMINESCENCE)CommonEvents.resonanceSound(server,position(),"spells.transmutate."+s.id(),10,50);
        else CommonEvents.resonanceSound(server,position(),"spells.cast.luminescence",1,10);
        // 原包追踪：每次成功施法计入 spells_cast。
        for(Combat.Element element:s.elements)SpellStats.addSpellCast(player,element);
        entityData.set(SPELL,-1);
    }
    private static TagKey<net.minecraft.world.level.block.Block> passthrough(){return TagKey.create(Registries.BLOCK,ResourceLocation.fromNamespaceAndPath(NyctocosmMod.ID,"passthrough"));}
    private static Vec3 transmittanceEndpoint(ServerLevel level,Player passenger,int range){
        Vec3 start=passenger.getEyePosition(),direction=passenger.getLookAngle().normalize(),last=start;
        for(int step=1;step<=range;step++){
            Vec3 sample=start.add(direction.scale(step));
            if(!level.getBlockState(BlockPos.containing(sample)).is(passthrough()))break;
            boolean occupied=!level.getEntitiesOfClass(LivingEntity.class,new net.minecraft.world.phys.AABB(sample,sample).inflate(2),entity->entity!=passenger).isEmpty();
            if(occupied)break;
            last=sample;
        }
        return last;
    }
    @Override protected void addAdditionalSaveData(CompoundTag tag) {
        if(owner!=null)tag.putUUID("Owner",owner);tag.putBoolean("Mobile",mobile());tag.putBoolean("Trace",isHypertrace());
        tag.put("ResonanceAim",entityData.get(RESONANCE).copy());tag.putInt("Spell",entityData.get(SPELL));tag.putInt("Cast",castTicks());tag.putInt("Charges",charges());tag.putInt("Life",life);tag.putInt("Cooldown",cooldown);tag.putInt("Element",element);
        tag.put("LuminousCircle",entityData.get(LUMINOUS_CIRCLE).copy());
    }
    @Override protected void readAdditionalSaveData(CompoundTag tag) {
        owner=tag.hasUUID("Owner")?tag.getUUID("Owner"):null;entityData.set(MOBILE,tag.getBoolean("Mobile"));entityData.set(TRACE,tag.getBoolean("Trace"));
        entityData.set(RESONANCE,tag.getCompound("ResonanceAim"));entityData.set(SPELL,tag.getInt("Spell"));entityData.set(CAST,Math.max(0,tag.getInt("Cast")));entityData.set(CHARGE,tag.getInt("Charges"));
        entityData.set(LUMINOUS_CIRCLE,tag.getCompound("LuminousCircle"));
        life=isHypertrace()?Math.min(Math.max(1,tag.getInt("Life")),400):mobile()?Math.min(Math.max(1,tag.getInt("Life")),500):-1;
        cooldown=tag.getInt("Cooldown");element=tag.getInt("Element");
    }
    @Override public Packet<ClientGamePacketListener> getAddEntityPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
}
