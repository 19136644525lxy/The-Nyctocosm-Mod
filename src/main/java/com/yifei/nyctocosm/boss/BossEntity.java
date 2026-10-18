package com.yifei.nyctocosm.boss;

import com.yifei.nyctocosm.Content;
import com.yifei.nyctocosm.CommonEvents;
import com.yifei.nyctocosm.boss.emissary.EmissaryController;
import com.yifei.nyctocosm.boss.serpent.SerpentController;
import com.yifei.nyctocosm.boss.sorcerer.NullpointStaff;
import com.yifei.nyctocosm.boss.sorcerer.SorcererController;
import com.yifei.nyctocosm.combat.Combat;
import com.yifei.nyctocosm.network.Network;
import java.util.*;
import net.minecraft.util.RandomSource;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.network.syncher.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.*;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.minecraftforge.entity.PartEntity;
import org.joml.Vector3f;

/** Boss 实体骨架：同步数据、生命周期分发与 multipart 命中箱。三类 Boss 行为在 sorcerer/serpent/emissary 控制器，普通怪在 OrdinaryController。 */
public abstract class BossEntity extends Monster implements net.minecraftforge.entity.IEntityAdditionalSpawnData {
    public static final EntityDataAccessor<Integer> PHASE=SynchedEntityData.defineId(BossEntity.class,EntityDataSerializers.INT);
    public static final EntityDataAccessor<String> ANIMATION=SynchedEntityData.defineId(BossEntity.class,EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Long> START=SynchedEntityData.defineId(BossEntity.class,EntityDataSerializers.LONG);
    public static final EntityDataAccessor<CompoundTag> PLANE=SynchedEntityData.defineId(BossEntity.class,EntityDataSerializers.COMPOUND_TAG);
    public static final EntityDataAccessor<CompoundTag> BODY=SynchedEntityData.defineId(BossEntity.class,EntityDataSerializers.COMPOUND_TAG);
    public static final EntityDataAccessor<CompoundTag> APPEARANCE=SynchedEntityData.defineId(BossEntity.class,EntityDataSerializers.COMPOUND_TAG);
    public final int kind;
    private final ServerBossEvent bar;
    public int remaining=0, attackAge=0, cooldown=40;
    public Attack attack;
    public int bossInvulnerableTicks;
    public SerpentPart[] parts;
    public EmissaryPart[] emissaryParts;
    private int lastHurtSound=-100;
    private final Set<UUID> participants=new LinkedHashSet<>();
    private boolean defeatRewardsGiven;
    private boolean emissaryDamageApplication,emissaryDamageBypasses;
    public final SorcererController sorcerer;
    public final SerpentController serpent;
    public final EmissaryController emissary;
    public final OrdinaryController ordinary;
    // Animation names are the original artist's clips. Durations are their exported keyframe counts.
    public record Attack(String animation,int ticks,float damage,Pattern pattern,Combat.Element element) {}
    public enum Pattern { BEAM, VOLLEY, RAIN, RING, DASH, PILLARS, PULL, MELEE, MOVE }
    public static Attack a(String name,int ticks,float damage,Pattern pattern,Combat.Element element) {return new Attack(name,ticks,damage,pattern,element);}

    protected BossEntity(EntityType<? extends BossEntity> type,Level level,int kind) {
        super(type,level);this.kind=kind;
        sorcerer=new SorcererController((Boss)this);
        serpent=new SerpentController((Boss)this);
        emissary=new EmissaryController((Boss)this);
        ordinary=new OrdinaryController((Boss)this);
        if(kind==2){syncedData().set(ANIMATION,"phase1_idle");setNoGravity(true);}
        bar=new ServerBossEvent(getDisplayName(),kind==0?BossEvent.BossBarColor.RED:kind==2?BossEvent.BossBarColor.YELLOW:BossEvent.BossBarColor.PURPLE,
            kind==0?BossEvent.BossBarOverlay.NOTCHED_12:kind==1?BossEvent.BossBarOverlay.NOTCHED_6:BossEvent.BossBarOverlay.PROGRESS);
        xpReward=kind==0?2800:kind==1?9000:kind==2?12000:kind==3?5:0;
        if(kind<3)setPersistenceRequired();
        if(kind==4)setNoGravity(true);
        if(kind==0) {setNoGravity(true);cooldown=100+random.nextInt(100);parts=new SerpentPart[20];for(int i=0;i<parts.length;i++)parts[i]=new SerpentPart(this);}
        if(kind==2){emissaryParts=new EmissaryPart[4];for(int i=0;i<emissaryParts.length;i++)emissaryParts[i]=new EmissaryPart(this,i);}
    }
    public static AttributeSupplier.Builder attributes(double health,double damage) {
        return attributes(health,damage,80);
    }
    public static AttributeSupplier.Builder attributes(double health,double damage,double followRange) {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH,health).add(Attributes.ATTACK_DAMAGE,damage)
            .add(Attributes.MOVEMENT_SPEED,.28).add(Attributes.FOLLOW_RANGE,followRange).add(Attributes.KNOCKBACK_RESISTANCE,.9);
    }
    @Override public void knockback(double strength,double x,double z){
        // Source attacks move the proxy hitbox, never the marker that owns the Sorcerer's model.
        // Also covers existing native entities with the former .9 attribute saved in NBT.
        if(kind!=1)super.knockback(strength,x,z);
    }
    @Override protected void registerGoals() {
        targetSelector.addGoal(1,new HurtByTargetGoal(this));
        // The source selects these two encounters by range, not line of sight.
        // The vanilla goal's mustSee flag only controls retaining a target, so
        // also disable the TargetingConditions sight check used during selection.
        targetSelector.addGoal(2,new SourcePlayerTargetGoal(this,kind<3));
    }
    private static final class SourcePlayerTargetGoal extends NearestAttackableTargetGoal<Player> {
        SourcePlayerTargetGoal(BossEntity boss,boolean ignoreSight){
            super(boss,Player.class,boss.kind>=2);
            if(ignoreSight)targetConditions=targetConditions.copy().ignoreLineOfSight();
        }
    }
    @Override protected void defineSynchedData() {super.defineSynchedData();entityData.define(PHASE,1);entityData.define(ANIMATION,"idle");entityData.define(START,0L);entityData.define(PLANE,new CompoundTag());entityData.define(BODY,new CompoundTag());entityData.define(APPEARANCE,new CompoundTag());}
    /** 控制器位于独立包，无法访问 Entity.random 的 protected 字段，由此桥接。 */
    public RandomSource random(){return random;}
    public SynchedEntityData syncedData(){return entityData;}
    public int phase(){return syncedData().get(PHASE);}
    public String animation(){return syncedData().get(ANIMATION);}
    public int emissaryAttackAge(){return attackAge;}
    public boolean isPerformingEmissary(String animation){return kind==2&&attack!=null&&attack.animation().equals(animation);}
    public float animationTime(float partial){return (float)(level().getGameTime()-syncedData().get(START))+partial;}
    public String rig() {
        if(kind==1){
            if(animation().equals("initial_spawn"))return "ajsorcerer";
            if(animation().equals("falltransition"))return "ajsorcererm1";
            if(animation().equals("risetransition")||animation().equals("fall")||animation().equals("die"))return "ajsorcererm2";
            return (phase()&1)==0?"ajsorcererm2":"ajsorcererm1";
        }
        return kind==2?"ajemissary":"ajdeathwalker";
    }
    public boolean hasSorcererPlane(){return kind==1&&syncedData().get(PLANE).contains("Start");}
    public Vec3 sorcererPlanePosition(){return readVec(syncedData().get(PLANE),"Origin");}
    public float sorcererPlaneYaw(){return syncedData().get(PLANE).getFloat("Yaw");}
    public float sorcererPlaneRoll(float partial){
        long age=level().getGameTime()-syncedData().get(PLANE).getLong("Start");
        return age==0?.01f:(age%360)+partial;
    }
    public boolean emissaryApproaching(){return kind==2&&animation().equals("arrival");}
    public String emissaryModelVariant(String bone){
        var state=syncedData().get(APPEARANCE);String variant=(state.isEmpty()?com.yifei.nyctocosm.boss.emissary.EmissaryAppearance.initial(phase()):state).getString(bone);
        return variant.equals("wing_config_1")?"wing_phase2":variant;
    }
    public String emissaryWingVariant(float partial){
        return emissaryModelVariant("rightupperwing");
    }
    public void animate(String name,int duration){syncedData().set(ANIMATION,name);syncedData().set(START,level().getGameTime());remaining=duration;if(kind==2&&!level().isClientSide)emissary.advanceAppearance(0);}
    public String idle(){return kind==2?"phase"+phase()+"_idle"+(phase()==4&&emissary.isFlying()?"_flying":""):"idle";}
    public void beginEntrance(){
        if(kind==1)animate("initial_spawn",331);
        else if(kind==2&&level() instanceof ServerLevel server)emissary.beginEntrance(server);
    }
    @Override public SpawnGroupData finalizeSpawn(net.minecraft.world.level.ServerLevelAccessor level,net.minecraft.world.DifficultyInstance difficulty,MobSpawnType reason,SpawnGroupData group,CompoundTag tag){
        var data=super.finalizeSpawn(level,difficulty,reason,group,tag);beginEntrance();return data;
    }
    @Override public void tick() {
        if(!level().isClientSide&&isAddedToWorld()&&(kind==0||kind==1)&&level() instanceof ServerLevel server
            &&!hasEncounterPlayerWithin(server,encounterRemovalRange(kind))){
            discardEncounterArtifacts(server);bar.removeAllPlayers();discard();return;
        }
        if(kind==0)serpent.initBody();
        if(kind==2&&!level().isClientSide)emissary.preTick();
        super.tick();
        if(kind==0)serpent.tickBody();
        if(kind==2)emissary.updateParts();
    }
    static double encounterRemovalRange(int bossKind){return bossKind==0?500:bossKind==1?100:Double.POSITIVE_INFINITY;}
    private boolean hasEncounterPlayerWithin(ServerLevel server,double range){
        double rangeSqr=range*range;
        for(ServerPlayer player:server.players())if(distanceToSqr(player)<=rangeSqr)return true;
        return false;
    }
    private void discardEncounterArtifacts(ServerLevel server){
        UUID ownerId=getUUID();List<Entity> snapshot=new ArrayList<>();server.getAllEntities().forEach(snapshot::add);
        if(kind==0)serpent.discardArtifacts(server,ownerId,snapshot);
        if(kind==1)sorcerer.discardArtifacts(server,ownerId,snapshot);
    }
    @Override public void onSyncedDataUpdated(EntityDataAccessor<?> key){
        super.onSyncedDataUpdated(key);
        if(BODY.equals(key)&&level().isClientSide)serpent.onBodySync();
    }
    public Vec3 segment(int back){return serpent.segment(back);}
    @Override public void customServerAiStep() {
        super.customServerAiStep();
        bar.setProgress(Math.max(0,getHealth()/getMaxHealth()));bar.setName(getDisplayName());
        if(!isAlive())return;
        ServerLevel server=(ServerLevel)level();
        if(bossInvulnerableTicks>0)bossInvulnerableTicks--;
        if(kind==1)sorcerer.tickFields(server);
        LivingEntity target=getTarget();
        if(kind==2)target=emissary.restoreTarget(server,target);
        if(target!=null && !Combat.canHit(this,target)) {setTarget(null);target=null;}
        if(kind==1)target=sorcerer.restoreTarget(server,target);
        if(kind==0){serpent.tick(server,target);return;}
        if(kind>=3){ordinary.tick(server);return;}
        if(kind==2){emissary.updateGravity();emissary.establishHover(server);}
        if(remaining>0){
            remaining--;
            if(kind==1)sorcerer.tickAnimation(server,target);
            if(kind==2)emissary.tickLifecycle(server,target);
            if(kind==2)emissary.advanceAppearance(attack!=null?attackAge+1:com.yifei.nyctocosm.boss.emissary.EmissaryAppearance.duration(animation())-remaining);
            if(attack!=null) {attackAge++;if(target!=null||kind==1&&sorcerer.performsWithoutTarget())perform(server,target);else if(kind==2&&attack.animation().endsWith("apocalypse_rain"))performEmissary(server,null);else if(kind==2)emissary.tickWithoutTarget(server);}
            if(kind==2)emissary.tickFlight(server,target);
            if(remaining==0)finishAnimation(server);
            return;
        }
        if(kind==1&&sorcerer.tickTransitions(server))return;
        if(kind==2&&emissary.tickTransitions(server,target))return;
        if(target==null)return;
        if(kind!=2||!emissary.isFlying())getLookControl().setLookAt(target,30,30);
        Vec3 difference=target.position().subtract(position());
        if(kind!=2||!emissary.isFlying()){setYRot((float)(Math.toDegrees(Math.atan2(difference.z,difference.x))-90));setXRot(0);yBodyRot=getYRot();}
        if(kind==2||kind==1)getNavigation().stop();
        else if(distanceTo(target)> (kind==1?10:kind==2?6:2)) getNavigation().moveTo(target,kind==2?1.4:1);
        else getNavigation().stop();
        if(--cooldown>0)return;
        double attackDistance=kind==2&&phase()==4?(emissary.flightPlayers(60).isEmpty()?Double.POSITIVE_INFINITY:0):distanceTo(target);
        Attack nextAttack=kind==1?SorcererController.selectSorcererAttack(phase(),distanceTo(target),random.nextInt(35)):kind==2?EmissaryController.selectEmissaryAttack(phase(),attackDistance,random.nextInt(100),emissary.isFlying()):a("slam",91,kind==3?12:8,Pattern.MELEE,Combat.Element.NIGHTMARE);
        if(nextAttack!=null)beginAttack(server,target,nextAttack);
        else if(kind==2&&phase()==4)cooldown=random.nextInt(30); // choose sets AttackTimer even in its decided/no-attack branch.
    }
    public void beginAttack(ServerLevel server,LivingEntity target,Attack next){
        if(kind==1)sorcerer.onBeginAttack(next,target);
        attack=next;attackAge=0;
        sorcerer.onAttackSelected(target);
        if(kind==2)emissary.onBeginAttack(server,target,next);
        sorcerer.clearHalos();
        if(next==SorcererController.ICE_SPIKES){animate("idle",next.ticks());NullpointStaff staff=sorcerer.findStaff(server);if(staff!=null)staff.emit();}
        else animate(next.animation(),next.ticks());
        perform(server,target);
    }
    public void finishAnimation(ServerLevel server){
        String finished=animation();attack=null;
        if(kind==1){if(sorcerer.finishAnimation(server,finished))return;}
        else if(kind==2)emissary.finishAnimation(server,finished);
        else cooldown=35;
        animate(idle(),0);
    }
    private void perform(ServerLevel server,LivingEntity target) {
        if(kind==1&&com.yifei.nyctocosm.boss.sorcerer.SorcererTimeline.attacks().contains(attack.animation())){
            Vec3 particleOrigin=position();float particleYaw=getYRot(),particlePitch=getXRot();
            if(attack.animation().equals("halobeam"))sorcerer.tickHalos(server,target);
            for(com.yifei.nyctocosm.boss.sorcerer.SorcererTimeline.Event event:com.yifei.nyctocosm.boss.sorcerer.SorcererTimeline.at(attack.animation(),attackAge))performSorcerer(server,target,event);
            if(sorcerer.dashActive())Network.sorcererCue((Boss)this,attack.animation(),attackAge,particleOrigin,particleYaw,particlePitch,position());
            return;
        }
        if(kind==2){performEmissary(server,target);return;}
        int strike=Math.max(8,attack.ticks()/2);
        Vec3 center=target.position();
        if(attackAge<strike){
            if(attackAge%5==0)server.sendParticles(ParticleTypes.ENCHANT,center.x,center.y+.1,center.z,15,2,.05,2,0);
            return;
        }
        if(attackAge!=strike)return;
        lookAt(net.minecraft.commands.arguments.EntityAnchorArgument.Anchor.EYES,target.getEyePosition());
        switch(attack.pattern()){
            case BEAM -> Combat.beam(server,this,50,attack.damage(),attack.element(),1);
            case VOLLEY -> {for(int n=0;n<5;n++)com.yifei.nyctocosm.spell.SpellBolt.launch(server,this,getEyePosition(),target.getEyePosition().subtract(getEyePosition()).add(random.nextGaussian()*.7,random.nextGaussian()*.3,random.nextGaussian()*.7),attack.damage(),attack.element(),1.2f,1.5f);}
            case RAIN -> {for(int n=0;n<12;n++){Vec3 at=center.add(random.nextDouble()*12-6,15+random.nextDouble()*5,random.nextDouble()*12-6);com.yifei.nyctocosm.spell.SpellBolt.launch(server,this,at,new Vec3(0,-1,0),attack.damage(),attack.element(),.8f,3);}}
            case RING -> {Combat.area(server,this,position(),8,attack.damage(),attack.element(),1.3);for(int n=0;n<16;n++){double angle=n*Math.PI/8;com.yifei.nyctocosm.spell.SpellBolt.launch(server,this,position().add(0,1,0),new Vec3(Math.cos(angle),0,Math.sin(angle)),attack.damage(),attack.element(),.8f,1);}}
            case DASH -> {setDeltaMovement(target.position().subtract(position()).normalize().scale(1.7));hurtMarked=true;Combat.beam(server,this,Math.min(15,distanceTo(target)+2),attack.damage(),attack.element(),1.5);}
            case PILLARS -> {Combat.area(server,this,center,3.5,attack.damage(),attack.element(),.7);for(int n=0;n<8;n++)server.sendParticles(ParticleTypes.END_ROD,center.x,center.y+n,center.z,20,1,.2,1,.1);}
            case PULL -> {for(LivingEntity living:server.getEntitiesOfClass(LivingEntity.class,getBoundingBox().inflate(12)))if(Combat.canHit(this,living)){Vec3 pull=position().subtract(living.position()).normalize().scale(1.5);living.setDeltaMovement(pull);living.hurtMarked=true;Combat.hit(this,living,attack.damage(),attack.element());}}
            case MELEE -> Combat.area(server,this,position().add(getLookAngle().scale(2)),4,attack.damage(),attack.element(),1);
            case MOVE -> {}
        }
    }
    /** 反射桥：GameTest/Smoke 按声明类在骨架上调用，实际行为在控制器。 */
    private void performEmissary(ServerLevel server,LivingEntity target){emissary.perform(server,target);}
    private void performSorcerer(ServerLevel server,LivingEntity target,com.yifei.nyctocosm.boss.sorcerer.SorcererTimeline.Event event){sorcerer.performSorcerer(server,target,event);}
    private void tickEmissaryFlight(ServerLevel server,LivingEntity target){emissary.tickFlight(server,target);}
    private void updateEmissaryGravity(){emissary.updateGravity();}
    /** 语义等价于原文控制器内的 super.hurt：绕过 hurt 覆写直走 Monster 伤害管线。 */
    public void hurtBypassingOverrides(DamageSource source,float amount){super.hurt(source,amount);}
    @Override public boolean hurt(DamageSource source,float amount){
        if(kind==4&&!source.is(net.minecraft.tags.DamageTypeTags.BYPASSES_INVULNERABILITY))return false;
        if(kind==0&&source.is(net.minecraft.world.damagesource.DamageTypes.IN_WALL))return false;
        if(((animation().equals("spawn")||animation().equals("initial_spawn")||kind==2&&emissaryApproaching()||kind==2&&animation().matches("phase[1-3]_transition"))&&remaining>0||bossInvulnerableTicks>0||kind==1&&sorcerer.staffShield())
            &&!source.is(net.minecraft.tags.DamageTypeTags.BYPASSES_INVULNERABILITY))return false;
        boolean hit=super.hurt(source,amount);
        if(hit&&source.getEntity() instanceof ServerPlayer player)participants.add(player.getUUID());
        if(hit&&level() instanceof ServerLevel server&&tickCount-lastHurtSound>=8){
            if(kind==0)CommonEvents.sound(server,position(),"serpent.hurt",50);
            else if(kind==1)CommonEvents.sound(server,position(),"sorcerer.hurt",4);
            lastHurtSound=tickCount;
        }
        return hit;
    }
    @Override protected void actuallyHurt(DamageSource source,float amount){
        emissaryDamageApplication=kind==2;emissaryDamageBypasses=source.is(net.minecraft.tags.DamageTypeTags.BYPASSES_INVULNERABILITY);
        try{super.actuallyHurt(source,amount);}finally{emissaryDamageApplication=false;emissaryDamageBypasses=false;}
    }
    @Override public void setHealth(float health){
        // Defer only genuinely lethal damage AFTER Forge events, armour and absorption.
        if(emissaryDamageApplication&&!emissaryDamageBypasses&&health<=0&&(phase()<4||remaining>0)){emissary.markPendingDeath();health=1;}
        super.setHealth(health);
    }
    @Override public void startSeenByPlayer(ServerPlayer player){super.startSeenByPlayer(player);if(kind<3)bar.addPlayer(player);}
    @Override public void stopSeenByPlayer(ServerPlayer player){super.stopSeenByPlayer(player);bar.removePlayer(player);}
    @Override public void die(DamageSource source){
        if(kind==1)sorcerer.preDie();
        if(kind==2&&level() instanceof ServerLevel server)emissary.preDie(server);
        super.die(source);bar.removeAllPlayers();animate(kind==3?"death":"die",deathDuration());
        if(level() instanceof ServerLevel server){
            if(kind==2)BossEffectTimeline.serverFrame(server,(Boss)this,"die",0);
            if(kind==0)CommonEvents.sound(server,position(),"serpent.death",50);
            if(kind==1)sorcerer.recallStaff(server);
        }
    }
    @Override public void remove(RemovalReason reason){
        if(kind==2&&(reason==RemovalReason.KILLED||reason==RemovalReason.DISCARDED)&&level() instanceof ServerLevel server)emissary.onRemove(server);
        super.remove(reason);
    }
    private int deathDuration(){return kind==0?100:kind==1?180:kind==2?100:60;}
    @Override public void tickDeath(){
        deathTime++;
        if(level() instanceof ServerLevel server){
            if(kind==0)serpent.deathEffects(server);
            else if(kind==1)sorcerer.deathEffects(server);
            else if(kind==2)emissary.deathEffects(server);
            if(deathTime>=deathDuration()){completeDefeat(server);remove(RemovalReason.KILLED);}
        }
    }
    private void completeDefeat(ServerLevel server){
        if(defeatRewardsGiven||kind>2)return;defeatRewardsGiven=true;
        String boss=kind==0?"serpent":kind==1?"sorcerer":"emissary";net.minecraft.ChatFormatting color=kind==0?net.minecraft.ChatFormatting.DARK_RED:kind==1?net.minecraft.ChatFormatting.DARK_PURPLE:net.minecraft.ChatFormatting.WHITE;
        var advancement=server.getServer().getAdvancements().getAdvancement(ResourceLocation.fromNamespaceAndPath(com.yifei.nyctocosm.NyctocosmMod.ID,"bosses/defeat_"+boss));
        for(UUID id:participants){
            ServerPlayer player=server.getEntity(id) instanceof ServerPlayer inLevel?inLevel:server.getServer().getPlayerList().getPlayer(id);if(player==null)continue;
            var destroy=Content.SOUND_MAP.get("boss.destroy");if(destroy!=null)player.playNotifySound(destroy.get(),net.minecraft.sounds.SoundSource.HOSTILE,1,1);
            player.connection.send(new ClientboundSetTitlesAnimationPacket(20,100,20));
            var title=kind==2?net.minecraft.network.chat.Component.translatable("entity.nyctocosm.celestial_defeated",net.minecraft.network.chat.Component.translatable("entity.nyctocosm.celestial_defeated1").withStyle(color)).withStyle(net.minecraft.ChatFormatting.GOLD)
                :net.minecraft.network.chat.Component.translatable("entity.nyctocosm.boss_defeated",net.minecraft.network.chat.Component.translatable("entity.nyctocosm.boss_defeated1").withStyle(color)).withStyle(net.minecraft.ChatFormatting.GOLD);
            player.connection.send(new ClientboundSetTitleTextPacket(title));
            ItemStack lore=new ItemStack(Content.get("adherent_"+boss));if(!player.getInventory().add(lore))player.drop(lore,false);
            if(advancement!=null)player.getAdvancements().award(advancement,"defeated");
        }
        if(kind==2)for(ServerPlayer player:server.players()){
            if(!player.level().dimension().location().getPath().equals("emissary_domain"))continue;
            CommonEvents.restoreEmissaryMode(player);
            if(advancement!=null)player.getAdvancements().award(advancement,"defeated");
            player.sendSystemMessage(net.minecraft.network.chat.Component.translatable("dialogue.nyctocosm.emissary_defeat.prompt_escape")
                .withStyle(style->style.withColor(net.minecraft.ChatFormatting.AQUA).withUnderlined(true).withClickEvent(new net.minecraft.network.chat.ClickEvent(net.minecraft.network.chat.ClickEvent.Action.RUN_COMMAND,"/nyctocosm return"))));
        }
    }
    @Override protected void dropCustomDeathLoot(DamageSource source,int looting,boolean recentlyHit){
        super.dropCustomDeathLoot(source,looting,recentlyHit);
        if(kind==0){spawnAtLocation(Content.get("nightmare_scale"),24+random.nextInt(9)+random.nextInt(9));spawnAtLocation(Content.get("seelic_essence"),20+random.nextInt(36));}
        if(kind==1){
            int splinters=0,shards=0;for(int roll=0;roll<3;roll++)if(random.nextBoolean())splinters++;else shards+=12+random.nextInt(9);
            if(splinters>0)spawnAtLocation(Content.get("null_splinter"),splinters);if(shards>0)spawnAtLocation(Content.get("void_shard"),shards);
            spawnAtLocation(Content.get("seelic_essence"),20+random.nextInt(36));
        }
        if(kind==2){if(random.nextFloat()<.3)spawnAtLocation(Content.get("semblanceofdoom"));spawnAtLocation(Content.get("seelic_essence"),20+random.nextInt(36));}
        // R12 遗物掉落：Boss 确定性掉对应域饰品各 1，普通怪 20% 概率掉 1
        if(kind==0){spawnAtLocation(Content.get("artifact_protection_dream"));spawnAtLocation(Content.get("artifact_absorption"));}
        if(kind==1){spawnAtLocation(Content.get("artifact_protection_nightmare"));spawnAtLocation(Content.get("artifact_levitation"));}
        if(kind==2){spawnAtLocation(Content.get("artifact_protection_dissonance"));spawnAtLocation(Content.get("artifact_expulsion"));}
        if(kind==3&&random.nextFloat()<.2f)spawnAtLocation(Content.get("artifact_accumulation"));
        if(kind==4&&random.nextFloat()<.2f)spawnAtLocation(Content.get("artifact_amelioration"));
    }
    @Override public void addAdditionalSaveData(CompoundTag tag){
        sorcerer.preSave(tag);
        super.addAdditionalSaveData(tag);tag.putInt("NativePhase",phase());tag.putString("Animation",animation());tag.putInt("Remaining",remaining);tag.putInt("AttackAge",attackAge);tag.putInt("Cooldown",cooldown);
        tag.putInt("AnimationAge",(int)animationTime(0));
        if(kind==1)sorcerer.saveExtra(tag);
        if(kind==0)serpent.saveExtra(tag);
        if(kind==2)emissary.saveExtra(tag);
        if(kind>=3)ordinary.saveExtra(tag);
        tag.putInt("BossInvulnerable",bossInvulnerableTicks);
        tag.putInt("ParticipantCount",participants.size());int participantIndex=0;for(UUID id:participants)tag.putUUID("Participant"+(participantIndex++),id);tag.putBoolean("DefeatRewards",defeatRewardsGiven);
    }
    @Override public void readAdditionalSaveData(CompoundTag tag){
        sorcerer.preLoad(tag);
        super.readAdditionalSaveData(tag);syncedData().set(PHASE,Math.max(1,Math.min(kind==2?4:kind==1?6:1,tag.getInt("NativePhase"))));
        // Resume persisted attack/lifecycle state below; unknown clips fail safely to idle.
        animate(idle(),0);attack=null;cooldown=tag.contains("Cooldown")?tag.getInt("Cooldown"):40;
        if(kind==1)sorcerer.loadExtra(tag);
        if(kind==0)serpent.loadExtra(tag);
        if(kind==2)emissary.loadExtra(tag);
        if(kind>=3)ordinary.loadExtra(tag);
        bossInvulnerableTicks=tag.getInt("BossInvulnerable");
        participants.clear();for(int index=0;index<tag.getInt("ParticipantCount");index++)if(tag.hasUUID("Participant"+index))participants.add(tag.getUUID("Participant"+index));defeatRewardsGiven=tag.getBoolean("DefeatRewards");
        if(kind<3&&getHealth()<=0){attack=null;animate("die",Math.max(1,deathDuration()-deathTime));syncedData().set(START,level().getGameTime()-deathTime);}
    }
    @Override public boolean removeWhenFarAway(double distance){return kind>=3;}
    @Override public net.minecraft.network.protocol.Packet<net.minecraft.network.protocol.game.ClientGamePacketListener> getAddEntityPacket(){return net.minecraftforge.network.NetworkHooks.getEntitySpawningPacket(this);}
    @Override public void writeSpawnData(net.minecraft.network.FriendlyByteBuf buffer){
        buffer.writeNbt(kind==0?serpent.bodySnapshot():new CompoundTag());
    }
    @Override public void readSpawnData(net.minecraft.network.FriendlyByteBuf buffer){
        CompoundTag tag=buffer.readNbt();if(kind==0&&tag!=null)serpent.receiveSpawnData(tag);
    }
    public static void putVec(CompoundTag tag,String key,Vec3 at){tag.putDouble(key+"X",at.x);tag.putDouble(key+"Y",at.y);tag.putDouble(key+"Z",at.z);}
    public static Vec3 readVec(CompoundTag tag,String key){return new Vec3(tag.getDouble(key+"X"),tag.getDouble(key+"Y"),tag.getDouble(key+"Z"));}
    public static void ring(ServerLevel server,Vec3 center,Vec3 axisA,Vec3 axisB,DustParticleOptions particle,double radius,int count){
        Vec3 a=axisA.normalize(),b=axisB.normalize();for(int n=0;n<count;n++){double angle=n*Math.PI*2/count;Vec3 p=center.add(a.scale(Math.cos(angle)*radius)).add(b.scale(Math.sin(angle)*radius));server.sendParticles(particle,p.x,p.y,p.z,1,0,0,0,0);}
    }
    public static void particleLine(ServerLevel server,Vec3 start,Vec3 end,DustParticleOptions first,DustParticleOptions second,double step){
        Vec3 delta=end.subtract(start);double length=delta.length();if(length<.001)return;Vec3 direction=delta.scale(1/length);
        for(double d=0;d<=length;d+=step){Vec3 p=start.add(direction.scale(d));server.sendParticles(((int)(d/step)&1)==0?first:second,p.x,p.y,p.z,1,0,0,0,0);}
    }
    public boolean intersectsDamageParts(AABB box){
        PartEntity<?>[] active=activeDamageParts();if(active==null)return false;
        for(PartEntity<?> part:active)if(part.isPickable()&&part.getBoundingBox().intersects(box))return true;
        return false;
    }
    public double damagePartsDistanceToSqr(Vec3 point){
        double nearest=Double.POSITIVE_INFINITY;
        PartEntity<?>[] active=activeDamageParts();
        if(active!=null)for(PartEntity<?> part:active)if(part.isPickable())nearest=Math.min(nearest,Combat.distanceToBoxSqr(point,part.getBoundingBox()));
        return nearest;
    }
    public boolean damagePartsOnLine(Vec3 start,Vec3 end,double width){
        PartEntity<?>[] active=activeDamageParts();if(active==null)return false;
        for(PartEntity<?> part:active)if(part.isPickable()){
            AABB box=part.getBoundingBox().inflate(width);
            if(box.contains(start)||box.clip(start,end).isPresent())return true;
        }
        return false;
    }
    public int participantCount(){return participants.size();}
    public boolean transitionInvulnerable(){return bossInvulnerableTicks>0;}
    @Override public boolean isMultipartEntity(){return kind==0||kind==2;}
    @Override public PartEntity<?>[] getParts(){return kind==0?parts:kind==2?emissaryParts:null;}
    @Override public void setId(int id){super.setId(id);if(parts!=null)for(int i=0;i<parts.length;i++)parts[i].setId(id+i+1);if(emissaryParts!=null)for(int i=0;i<emissaryParts.length;i++)emissaryParts[i].setId(id+i+1);}
    private PartEntity<?>[] activeDamageParts(){return kind==0?parts:kind==2?emissaryParts:null;}
    public static final class SerpentPart extends PartEntity<BossEntity> {
        SerpentPart(BossEntity parent){super(parent);refreshDimensions();}
        @Override public EntityDimensions getDimensions(Pose pose){return EntityType.SLIME.getDimensions().scale(.255f*8);}
        @Override protected void defineSynchedData(){}
        @Override protected void readAdditionalSaveData(CompoundTag tag){}
        @Override protected void addAdditionalSaveData(CompoundTag tag){}
        @Override public boolean isPickable(){return true;}
        @Override public boolean hurt(DamageSource source,float amount){
        return getParent().hurt(source,amount);}
        @Override public boolean is(Entity entity){return this==entity||getParent()==entity;}
    }
    public static final class EmissaryPart extends PartEntity<BossEntity> {
        private final int index;
        EmissaryPart(BossEntity parent,int index){super(parent);this.index=index;refreshDimensions();}
        @Override public EntityDimensions getDimensions(Pose pose){
            BossEntity parent=getParent();int stage=parent.phase();
            if(stage==1)return EntityType.SLIME.getDimensions().scale(.255f*4);
            if(index>0)return EntityDimensions.fixed(.01f,.01f);
            return stage==2?EntityType.GIANT.getDimensions():EntityType.WITHER_SKELETON.getDimensions();
        }
        @Override protected void defineSynchedData(){}
        @Override protected void readAdditionalSaveData(CompoundTag tag){}
        @Override protected void addAdditionalSaveData(CompoundTag tag){}
        @Override public boolean isPickable(){return getParent().isAlive()&&(getParent().phase()==1||index==0);}
        @Override public boolean hurt(DamageSource source,float amount){return isPickable()&&getParent().hurt(source,amount);}
        @Override public boolean is(Entity entity){return this==entity||getParent()==entity;}
    }
}
