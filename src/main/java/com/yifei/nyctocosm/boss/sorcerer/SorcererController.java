package com.yifei.nyctocosm.boss.sorcerer;

import com.yifei.nyctocosm.Content;
import com.yifei.nyctocosm.CommonEvents;
import com.yifei.nyctocosm.NyctocosmMod;
import com.yifei.nyctocosm.boss.BossEntity;
import com.yifei.nyctocosm.boss.Boss;
import com.yifei.nyctocosm.boss.emissary.EmissaryPose;
import com.yifei.nyctocosm.combat.Combat;
import com.yifei.nyctocosm.network.Network;
import com.yifei.nyctocosm.spell.SpellArea;
import com.yifei.nyctocosm.spell.SpellBolt;
import java.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

/** 术士（Null Sorcerer）行为控制器：法杖、虚空平面、光环与全部招式时间线。由 BossEntity 骨架按 kind==1 分发。 */
public final class SorcererController {
    public final Boss boss;
    private UUID staffId;
    private double sorcererGroundY=Double.NaN;
    private boolean staffBroken;
    private boolean staffShield;
    private UUID sorcererTargetId;
    private final Vec3[] halos=new Vec3[8];
    private boolean independentHalos;
    private final SorcererDash sorcererDash=new SorcererDash();
    private Vec3 meteorTear,meteorLanding,hailCenter;
    private int meteorTearAge=-1,hailAge=-1;
    private int meteorShotsEmitted;
    private float meteorTearYaw,meteorTearPitch;
    private final int[] hailDelay=new int[18];
    // Animation names are the original artist's clips. Durations are their exported keyframe counts.
    public static final BossEntity.Attack[] SORCERER_A={
        BossEntity.a("halobeam",133,10,BossEntity.Pattern.BEAM,Combat.Element.VOID),BossEntity.a("shardhail",64,10,BossEntity.Pattern.VOLLEY,Combat.Element.DREAM),
        BossEntity.a("frozenblades",67,14,BossEntity.Pattern.RING,Combat.Element.DREAM),BossEntity.a("singularitycharge",63,20,BossEntity.Pattern.PULL,Combat.Element.VOID),
        BossEntity.a("voidmeteor",67,15,BossEntity.Pattern.RAIN,Combat.Element.VOID)};
    public static final BossEntity.Attack[] SORCERER_B={
        BossEntity.a("dashstrike",71,15,BossEntity.Pattern.DASH,Combat.Element.VOID),BossEntity.a("javelinswing",67,16,BossEntity.Pattern.MELEE,Combat.Element.VOID),
        BossEntity.a("seekingsingularities",66,13,BossEntity.Pattern.VOLLEY,Combat.Element.VOID),BossEntity.a("voidbeam",67,3,BossEntity.Pattern.BEAM,Combat.Element.VOID),
        BossEntity.a("voidwave",66,20,BossEntity.Pattern.RING,Combat.Element.VOID),BossEntity.a("airdash",66,15,BossEntity.Pattern.DASH,Combat.Element.VOID)};
    public static final BossEntity.Attack ICE_SPIKES=BossEntity.a("icespikes",41,3,BossEntity.Pattern.PILLARS,Combat.Element.VOID);

    public SorcererController(Boss boss){this.boss=boss;}
    public static BossEntity.Attack selectSorcererAttack(int phase,double distance,int roll){
        if((phase&1)==0&&distance>7&&(distance<8||distance>20))return null;
        return switch(Math.max(1,Math.min(6,phase))){
            case 1 -> distance<=15?SORCERER_A[1]:SORCERER_A[2];
            case 2 -> distance<=7?SORCERER_B[1]:SORCERER_B[0];
            case 3 -> distance<=15?(Math.floorMod(roll,2)==0?SORCERER_A[1]:ICE_SPIKES):(Math.floorMod(roll,5)<2?SORCERER_A[2]:SORCERER_A[0]);
            case 4 -> distance<=7?(Math.floorMod(roll,3)==0?SORCERER_B[1]:SORCERER_B[2]):(Math.floorMod(roll,5)<2?SORCERER_B[0]:SORCERER_B[5]);
            case 5 -> distance<=15?(Math.floorMod(roll,7)<2?SORCERER_A[1]:Math.floorMod(roll,7)<4?ICE_SPIKES:SORCERER_A[4]):(Math.floorMod(roll,7)<2?SORCERER_A[2]:Math.floorMod(roll,7)<4?SORCERER_A[0]:SORCERER_A[3]);
            default -> distance<=7?(Math.floorMod(roll,7)<2?SORCERER_B[1]:Math.floorMod(roll,7)<4?SORCERER_B[2]:SORCERER_B[4]):(Math.floorMod(roll,7)<2?SORCERER_B[0]:Math.floorMod(roll,7)<4?SORCERER_B[5]:SORCERER_B[3]);
        };
    }
    private static BossEntity.Attack sorcererAttack(String clip){
        for(BossEntity.Attack candidate:SORCERER_A)if(candidate.animation().equals(clip))return candidate;
        for(BossEntity.Attack candidate:SORCERER_B)if(candidate.animation().equals(clip))return candidate;
        return ICE_SPIKES.animation().equals(clip)?ICE_SPIKES:null;
    }
    private void summonSorcererPlane(ServerLevel server){
        CompoundTag plane=new CompoundTag();plane.putLong("Start",boss.level().getGameTime());plane.putFloat("Yaw",boss.getYRot());
        BossEntity.putVec(plane,"Origin",boss.position().add(0,1,0).add(com.yifei.nyctocosm.boss.serpent.SerpentMotion.forward(boss.getYRot()).scale(-2)));
        boss.syncedData().set(BossEntity.PLANE,plane);CommonEvents.resonanceSound(server,boss.position(),"spells.cast.magic_plane",5,30);
        // Source voidplane is a short summon shockwave, never an endlessly spinning particle ring.
        Vec3 origin=boss.sorcererPlanePosition().add(0,1.6,0);
        Network.sourceVisual(server,"repair_voidplane",origin,boss.getYRot(),-90);
    }
    private void killSorcererPlane(){boss.syncedData().set(BossEntity.PLANE,new CompoundTag());}
    public NullpointStaff findStaff(ServerLevel server){
        if(staffId!=null&&server.getEntity(staffId) instanceof NullpointStaff staff&&!staff.isRemoved())return staff;
        for(NullpointStaff staff:server.getEntitiesOfClass(NullpointStaff.class,boss.getBoundingBox().inflate(32)))if(staff.ownerId().filter(boss.getUUID()::equals).isPresent()){staffId=staff.getUUID();return staff;}
        return null;
    }

    private void plantStaff(ServerLevel server,boolean plunge){
        if(findStaff(server)!=null)return;
        Vec3 forward=boss.getLookAngle();forward=new Vec3(forward.x,0,forward.z);if(forward.lengthSqr()<.001)forward=new Vec3(0,0,1);else forward=forward.normalize();
        Vec3 right=new Vec3(forward.z,0,-forward.x);Vec3 raw=boss.position().add(forward.scale(.85)).subtract(right.scale(.7));
        double surface=localSurface(server,raw,20);
        NullpointStaff staff=Content.NULLPOINT.get().create(server);if(staff==null)return;
        staff.bind(boss);staff.setPos(raw.x,surface,raw.z);staff.setYRot(boss.getYRot());staff.setHealth(300);server.addFreshEntity(staff);staffId=staff.getUUID();staffBroken=false;staffShield=true;sorcererGroundY=surface;boss.setNoGravity(true);boss.getNavigation().stop();boss.setDeltaMovement(Vec3.ZERO);
        if(plunge){
            boss.bossInvulnerableTicks=Math.max(boss.bossInvulnerableTicks,boss.animation().equals("initial_spawn")?200:140);
            server.sendParticles(ParticleTypes.FLASH,staff.getX(),staff.getY()+1,staff.getZ(),5,.1,.1,.1,0);
            server.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK,Blocks.ICE.defaultBlockState()),staff.getX(),staff.getY()+.2,staff.getZ(),100,.3,.2,.3,1);
            for(LivingEntity living:server.getEntitiesOfClass(LivingEntity.class,staff.getBoundingBox().inflate(4)))if(Combat.canHit(boss,living))living.hurt(server.damageSources().mobAttack(boss),20);
        }
    }
    private static double localSurface(ServerLevel server,Vec3 at,int scanDepth){
        BlockPos.MutableBlockPos cursor=new BlockPos.MutableBlockPos((int)Math.floor(at.x),(int)Math.floor(at.y+2),(int)Math.floor(at.z));
        for(int step=0;step<scanDepth&&cursor.getY()>server.getMinBuildHeight()+1;step++,cursor.move(0,-1,0)){
            BlockPos below=cursor.below();if(!server.getBlockState(below).getCollisionShape(server,below).isEmpty())return cursor.getY();
        }
        return Math.floor(at.y);
    }
    private void updateSorcererHover(ServerLevel server){
        if(boss.kind!=1||sorcererDash.active())return;
        if((boss.phase()&1)==0){if(boss.animation().equals("fall")&&Double.isFinite(sorcererGroundY)&&boss.getY()>sorcererGroundY+.1)boss.setPos(boss.getX(),Math.max(sorcererGroundY,boss.getY()-.3),boss.getZ());return;}
        NullpointStaff staff=findStaff(server);if(staff==null||staffBroken||staff.isRemoved())return;
        sorcererGroundY=staff.getY();boss.setNoGravity(true);boss.getNavigation().stop();boss.setDeltaMovement(Vec3.ZERO);
        if(boss.animation().equals("initial_spawn")||boss.animation().equals("risetransition")||boss.animation().equals("rise")||boss.hasSorcererPlane())return;
        double target=sorcererGroundY+5.83;
        if(boss.getY()<target-.03)boss.setPos(boss.getX(),Math.min(target,boss.getY()+.14),boss.getZ());
        else if(boss.getY()>target+.1)boss.setPos(boss.getX(),Math.max(target,boss.getY()-.14),boss.getZ());
    }
    public void tickFields(ServerLevel server){
        DustParticleOptions purple=new DustParticleOptions(new Vector3f(.741f,0,1),1.15f);
        DustParticleOptions black=new DustParticleOptions(new Vector3f(0,0,0),1.2f);
        if(meteorTear!=null){
            if(meteorTearAge>=0){
                meteorTearAge++;
                if(meteorTearAge>60){meteorTearAge=-1;meteorTear=null;}
            }
            if(meteorTear!=null){
                if(meteorTearAge>=0&&meteorTearAge%2==0)BossEntity.ring(server,meteorTear,new Vec3(1,0,0),new Vec3(0,0,1),purple,2.5,24);
                server.sendParticles(black,meteorTear.x,meteorTear.y,meteorTear.z,4,.5,.2,.5,.02);
                if(meteorTearAge>=10&&meteorTearAge<=60&&meteorTearAge%10==0){
                    Player nearest=server.players().stream().filter(player->!player.isSpectator())
                        .filter(player->player.distanceToSqr(meteorTear)<=50*50)
                        .min(Comparator.comparingDouble(player->player.distanceToSqr(meteorTear))).orElse(null);
                    if(nearest!=null){
                        meteorShotsEmitted++;
                        server.sendParticles(ParticleTypes.FLASH,meteorTear.x,meteorTear.y,meteorTear.z,1,0,0,0,0);
                        float yaw=meteorTearYaw+boss.random().nextInt(80)-40;
                        float pitch=meteorTearPitch+boss.random().nextInt(80)-40;
                        SpellBolt.launchMeteor(server,boss,meteorTear,Vec3.directionFromRotation(pitch,yaw),15);
                    }
                }
            }
        }
        if(hailAge>=0&&hailCenter!=null){
            hailAge++;
            Vec3 ball=hailCenter.add(0,Math.min(30,hailAge)*.45+5,0);
            if(hailAge<30){
                server.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK,Blocks.ICE.defaultBlockState()),ball.x,ball.y,ball.z,8,.4,.4,.4,.02);
                server.sendParticles(ParticleTypes.SNOWFLAKE,ball.x,ball.y,ball.z,4,.5,.5,.5,.02);
            }
            if(hailAge==30){server.sendParticles(ParticleTypes.FLASH,ball.x,ball.y,ball.z,2,.3,.3,.3,0);server.sendParticles(ParticleTypes.SNOWFLAKE,ball.x,ball.y,ball.z,100,1,1,1,.1);}
            for(int n=0;n<hailDelay.length;n++)if(hailAge==30+hailDelay[n]){
                Vec3 at=hailCenter.add(boss.random().nextDouble()*30-15,22+boss.random().nextDouble()*4,boss.random().nextDouble()*30-15);
                SpellBolt.launch(server,boss,at,new Vec3(0,-1,0),10,Combat.Element.DREAM,.95f,1.4f).weaponVisual(5);
            }
            if(hailAge>75){hailAge=-1;hailCenter=null;}
        }
    }
    public void tickAnimation(ServerLevel server,LivingEntity target){
        int age=(int)(boss.level().getGameTime()-boss.syncedData().get(BossEntity.START));String clip=boss.animation();
        if(clip.equals("initial_spawn")){
            if(age==161)CommonEvents.sound(server,boss.position(),"sorcerer.spawn.conjure",5);
            if(age==193)CommonEvents.sound(server,boss.position(),"sorcerer.spawn.brandish",5);
            if(age==230)CommonEvents.sound(server,boss.position(),"sorcerer.spawn.twirl",5);
            if(age==250)CommonEvents.sound(server,boss.position(),"sorcerer.spawn.plunge",5);
            if(age==253)plantStaff(server,true);
            if(age==330){boss.animate("rise",133);return;}
        }else if(clip.equals("falltransition")){
            if(age==5){killSorcererPlane();CommonEvents.sound(server,boss.position(),"sorcerer.spawn.conjure",5);}
            if(age==25){boss.bossInvulnerableTicks=0;staffShield=false;boss.setNoGravity(false);boss.animate("fall",66);return;}
        }else if(clip.equals("fall")){
            if(age==30)CommonEvents.sound(server,boss.position(),"sorcerer.staff.woosh",5);
            if(age==37)CommonEvents.sound(server,boss.position(),"sorcerer.fall.blinkout",5);
            if(age==48){CommonEvents.sound(server,boss.position(),"sorcerer.fall.blinkin",5);CommonEvents.sound(server,boss.position(),"sorcerer.staff.woosh",5);}
        }else if(clip.equals("risetransition")){
            if(age==48)CommonEvents.sound(server,boss.position(),"sorcerer.spawn.plunge",5);
            if(age==51)plantStaff(server,true);
            if(age==59){boss.animate("rise",133);return;}
        }else if(clip.equals("rise")){
            boss.setPos(boss.position().add(0,SorcererPlane.riseStep(age),0));
            if(age==44)CommonEvents.sound(server,boss.position(),"sorcerer.rise.woosh",5);
            if(age==127)summonSorcererPlane(server);
        }
        updateSorcererHover(server);
    }
    public boolean performsWithoutTarget(){return boss.attack!=null&&(boss.attack.animation().equals("halobeam")||boss.attack.animation().equals("voidbeam")||sorcererDash.active());}
    public boolean dashActive(){return sorcererDash.active();}
    public void performTimeline(ServerLevel server,LivingEntity target){
        Vec3 particleOrigin=boss.position();float particleYaw=boss.getYRot(),particlePitch=boss.getXRot();
        if(boss.attack.animation().equals("halobeam"))tickHalos(server,target);
        for(SorcererTimeline.Event event:SorcererTimeline.at(boss.attack.animation(),boss.attackAge))performSorcerer(server,target,event);
        if(sorcererDash.active())Network.sorcererCue(boss,boss.attack.animation(),boss.attackAge,particleOrigin,particleYaw,particlePitch,boss.position());
    }
    public void performSorcerer(ServerLevel server,LivingEntity target,SorcererTimeline.Event event){
        if(event.sound()!=null&&event.action()!=SorcererTimeline.Action.HALO_SUMMON&&event.action()!=SorcererTimeline.Action.HALO_FIRE)CommonEvents.sound(server,event.action()==SorcererTimeline.Action.METEOR_RAIN&&meteorTear!=null?meteorTear:boss.position(),event.sound(),5);
        Vec3 center=target==null?boss.position().add(boss.getLookAngle().scale(10)):target.getBoundingBox().getCenter();
        Vec3 forward=center.subtract(boss.getEyePosition()).normalize();
        Vec3 horizontal=new Vec3(forward.x,0,forward.z);
        if(horizontal.lengthSqr()<.001)horizontal=new Vec3(0,0,1);else horizontal=horizontal.normalize();
        Vec3 right=new Vec3(horizontal.z,0,-horizontal.x);
        DustParticleOptions purple=new DustParticleOptions(new Vector3f(.741f,0,1),1.5f);
        DustParticleOptions blue=new DustParticleOptions(new Vector3f(0,.651f,1),1.25f);
        DustParticleOptions black=new DustParticleOptions(new Vector3f(0,0,0),2);
        switch(event.action()){
            case CHARGE -> {} // Source client keyframes supply charge particles.
            case HALO_SUMMON -> {
                int index=Math.max(0,event.tick()/5-1);var node=SorcererBeamNode.summonHalo(server,boss,event.tick());halos[index]=node.position();independentHalos=true;
                CommonEvents.resonanceSound(server,node.position(),"sorcerer.halobeam.summon",5,30);
            }
            case HALO_ACTIVATE -> {
                if(independentHalos){SorcererBeamNode.activate(server,boss.getUUID());break;}
                server.sendParticles(ParticleTypes.FLASH,boss.getX(),boss.getY()+2,boss.getZ(),5,.4,.4,.4,0);
                for(Vec3 halo:halos)if(halo!=null)BossEntity.ring(server,halo,right,new Vec3(0,1,0),blue,1.3,24);
            }
            case HALO_FIRE -> {
                if(independentHalos)break; // Only the old native array-save compatibility path uses derived firing events.
                int index=Math.max(0,(event.tick()-75)/5);Vec3 start=halos[index]==null?haloPosition(target,index):halos[index];Vec3 end=sorcererRayEnd(server,start,target.getEyePosition());
                Combat.line(server,boss,start,end,boss.attack.damage(),boss.attack.element(),1,false);
                sorcererBeam(server,start,end,.6);
                server.sendParticles(ParticleTypes.FLASH,start.x,start.y,start.z,1,0,0,0,0);
            }
            case ICE_FOCUS -> {} // Source block/item particles are exported from the rig.
            case HAIL -> {
                SorcererHail.launchBall(server,boss);
            }
            case VOID_FOCUS -> {} // Source particle rows and xflat portal velocities are client-owned.
            case SINGULARITY -> {
                Vec3 origin=EmissaryPose.localPoint(boss.position(),boss.getYRot(),boss.getXRot(),new Vec3(0,2,.5));
                Player nearest=server.getEntitiesOfClass(Player.class,new AABB(origin,origin).inflate(50),player->!player.isSpectator()&&player.position().distanceToSqr(origin)<=50*50)
                    .stream().min(Comparator.comparingDouble(player->player.position().distanceToSqr(origin))).orElse(null);
                if(nearest!=null)SpellBolt.launch(server,boss,origin,nearest.position().subtract(origin),boss.attack.damage(),boss.attack.element(),.3f,5)
                    .effect(SpellBolt.Effect.DAMAGE,100).weaponVisual(4).impactSound("sorcerer.singularitycharge.explode");
            }
            case BLADE_FOCUS -> {
                FrozenBlade.charge(server,boss);
            }
            case BLADE_SWING -> {
                if(boss.attack.animation().equals("frozenblades"))FrozenBlade.swing(server,boss);
                else server.sendParticles(ParticleTypes.SWEEP_ATTACK,boss.getX()+forward.x*2,boss.getY()+1,boss.getZ()+forward.z*2,7,.5,.2,.5,1);
            }
            case FROZEN_BLADES -> {
                FrozenBlade.launchPair(server,boss);
            }
            case METEOR_TEAR -> {
                meteorTear=boss.position().add(0,12,0);meteorLanding=center;meteorTearAge=-2;meteorTearYaw=boss.getYRot();meteorTearPitch=boss.getXRot();
                BossEntity.ring(server,meteorTear,new Vec3(1,0,0),new Vec3(0,0,1),purple,2.5,32);
                server.sendParticles(black,meteorTear.x,meteorTear.y,meteorTear.z,20,.3,.3,.3,0);
            }
            case METEOR_RAIN -> {
                if(meteorTear!=null){meteorTearAge=0;meteorShotsEmitted=0;server.sendParticles(ParticleTypes.FLASH,meteorTear.x,meteorTear.y,meteorTear.z,1,0,0,0,0);}
            }
            case DASH_AIM -> sorcererDash.aim(server,boss,false);
            case DASH_STEP -> {
                Vec3 entry=event.tick()==50?boss.position():sorcererDash.step(boss);
                Combat.playerFeetSphere(server,boss,entry,1.5,15,Combat.Element.VOID);
            }
            case DASH_FINISH -> {
                Vec3 entry=boss.position();sorcererDash.step(boss);sorcererDash.arrive(server,boss);
                Combat.playerFeetSphere(server,boss,entry.add(com.yifei.nyctocosm.boss.serpent.SerpentMotion.localForward(boss.getYRot(),boss.getXRot()).scale(1.5)),2,15,Combat.Element.VOID);
                Combat.playerFeetSphere(server,boss,boss.position(),1.5,15,Combat.Element.VOID);
            }
            case VOIDWAVE_FOCUS -> {} // Source direct cues are already compiled; no extra tear/flash burst.
            case VOIDWAVE -> {
                Vec3 source=boss.position().add(0,2,0);
                Combat.playerFeetSphere(server,boss,source,6,boss.attack.damage(),boss.attack.element());
                server.sendParticles(ParticleTypes.EXPLOSION_EMITTER,source.x,source.y,source.z,1,0,0,0,0);
                server.sendParticles(ParticleTypes.FLASH,source.x,source.y,source.z,4,.2,.2,.2,0);
                Network.sourceVisual(server,"repair_transmittance",source,boss.getYRot(),boss.getXRot());
            }
            case JAVELIN -> {
                Vec3 source=sorcererJavelinOrigin();
                Combat.playerFeetSphere(server,boss,source,3,boss.attack.damage(),boss.attack.element());
                server.sendParticles(ParticleTypes.SWEEP_ATTACK,source.x,source.y,source.z,7,.5,.2,.5,1);
            }
            case AIR_RISE -> sorcererDash.rise(boss,event.tick());
            case AIR_AIM -> sorcererDash.aim(server,boss,true);
            case AIR_DASH -> sorcererDash.step(boss); // aj.frame=39 is unused by the source's ajsorcererm2 engine.
            case AIR_SLAM -> {
                if(sorcererDash.finished())break;Vec3 entry=boss.position();sorcererDash.arrive(server,boss);
                Combat.playerFeetSphere(server,boss,entry,4,15,Combat.Element.VOID);
            }
            case VOIDBEAM_AIM -> {SorcererBeamNode.aimVoid(server,boss);server.sendParticles(ParticleTypes.FLASH,boss.getX(),boss.getY()+2,boss.getZ(),1,0,0,0,0);}
            case VOIDBEAM -> {
                SorcererBeamNode.voidBeams(server,boss.getUUID());
            }
            case VOIDBEAM_FINISH -> SorcererBeamNode.finishVoid(server,boss.getUUID());
            case SEEKING_ORBS -> {
                for(int n=0;n<8;n++){
                    Vec3 direction=new Vec3(boss.random().nextDouble()*2-1,boss.random().nextDouble()*2-1,boss.random().nextDouble()*2-1).normalize();
                    SpellBolt.launch(server,boss,boss.getEyePosition().add(forward.scale(1.5)),direction,boss.attack.damage(),boss.attack.element(),.24f,1)
                        .seekingSingularity(70+boss.random().nextInt(20)).weaponVisual(7).impactSound("sorcerer.seekingsingularities.impact");
                }
            }
            case ICE_SPIKES -> {
                NullpointStaff staff=findStaff(server);Vec3 origin=staff==null?boss.position():staff.position().add(0,2,0);
                IceSpikeWave.spawn(server,boss,origin,staff==null?boss.getYRot():staff.getYRot());
            }
        }
    }
    private Vec3 sorcererJavelinOrigin(){
        double yaw=Math.toRadians(boss.getYRot()),pitch=Math.toRadians(boss.getXRot());
        Vec3 localUp=new Vec3(-Math.sin(yaw)*Math.sin(pitch),Math.cos(pitch),Math.cos(yaw)*Math.sin(pitch));
        return boss.position().add(localUp).add(boss.getLookAngle().scale(2));
    }
    private Vec3 haloPosition(LivingEntity target,int index){
        Vec3 forward=target.getBoundingBox().getCenter().subtract(boss.getEyePosition());forward=new Vec3(forward.x,0,forward.z);
        if(forward.lengthSqr()<.001)forward=new Vec3(0,0,1);else forward=forward.normalize();
        Vec3 right=new Vec3(forward.z,0,-forward.x);double angle=index*Math.PI/4;
        return boss.position().add(0,2,0).add(right.scale(Math.cos(angle)*5)).add(0,Math.sin(angle)*5,0);
    }
    public void tickHalos(ServerLevel server,LivingEntity target){
        if(independentHalos||target==null)return;
        DustParticleOptions purple=new DustParticleOptions(new Vector3f(.741f,0,1),.7f);
        Vec3 aim=target.getEyePosition();
        for(int n=0;n<halos.length;n++)if(halos[n]!=null&&boss.attackAge>=5+n*5&&boss.attackAge<114){
            Vec3 at=halos[n];Vec3 toward=aim.subtract(at);Vec3 axis=toward.lengthSqr()<.01?new Vec3(0,0,1):toward.normalize();
            Vec3 tangent=new Vec3(axis.z,0,-axis.x);if(tangent.lengthSqr()<.01)tangent=new Vec3(1,0,0);
            BossEntity.ring(server,at,tangent,axis.cross(tangent),purple,boss.attackAge>=67?.5:1,12);
            if(boss.attackAge%2==0)BossEntity.particleLine(server,at,at.add(axis.scale(boss.attackAge>=67?2:3)),purple,purple,.4);
        }
    }
    private static void sorcererBeam(ServerLevel server,Vec3 start,Vec3 end,double step){
        Vec3 delta=end.subtract(start);double length=Math.min(60,delta.length());if(length<.001)return;
        Vec3 direction=delta.normalize();
        DustParticleOptions purple=new DustParticleOptions(new Vector3f(.741f,0,1),.8f),black=new DustParticleOptions(new Vector3f(0,0,0),.6f);
        for(double along=0;along<=length;along+=step){
            Vec3 ringCenter=start.add(direction.scale(along));
            // Two batched envelopes preserve the source's black core/purple outer shell
            // without thousands of individual particle packets per active beam tick.
            server.sendParticles(black,ringCenter.x,ringCenter.y,ringCenter.z,9,.18,.18,.18,0);
            server.sendParticles(purple,ringCenter.x,ringCenter.y,ringCenter.z,12,.38,.38,.38,0);
        }
        server.sendParticles(ParticleTypes.END_ROD,end.x,end.y,end.z,4,.15,.15,.15,.02);
    }
    private static Vec3 sorcererRayEnd(ServerLevel server,Vec3 start,Vec3 intended){
        // Both source rays advance 0.5 block per recursion and stop after 60
        // recursions or on a block outside nyctocosm:passthrough.
        var passthrough=net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.BLOCK,ResourceLocation.fromNamespaceAndPath(NyctocosmMod.ID,"passthrough"));
        Vec3 delta=intended.subtract(start);double length=Math.min(30,delta.length());if(length<.001)return start;
        Vec3 unit=delta.normalize();
        for(double travel=0;travel<=length;travel+=.5){Vec3 point=start.add(unit.scale(travel));
            if(!server.getBlockState(BlockPos.containing(point)).is(passthrough))return point;
        }
        return start.add(unit.scale(length));
    }
    public void onStaffBroken(NullpointStaff staff){if(boss.kind==1&&staffId!=null&&staffId.equals(staff.getUUID()))staffBroken=true;}
    public Optional<NullpointStaff> nullpoint(){return boss.level() instanceof ServerLevel server?Optional.ofNullable(findStaff(server)):Optional.empty();}
    public boolean staffShield(){return staffShield;}
    public boolean tickTransitions(ServerLevel server){
        if(staffBroken&&(boss.phase()&1)==1){staffBroken=false;boss.syncedData().set(BossEntity.PHASE,Math.min(6,boss.phase()+1));boss.animate("falltransition",64);return true;}
        if(((boss.phase()==2&&boss.getHealth()<=666)||(boss.phase()==4&&boss.getHealth()<=333))){
            boss.syncedData().set(BossEntity.PHASE,boss.phase()+1);boss.bossInvulnerableTicks=140;boss.animate("risetransition",60);return true;
        }
        if((boss.phase()&1)==1&&findStaff(server)==null&&!staffBroken){plantStaff(server,false);boss.animate("rise",133);return true;}
        updateSorcererHover(server);
        return false;
    }
    public void onBeginAttack(BossEntity.Attack next,LivingEntity target){
        sorcererDash.finish(boss);
        if(next.animation().equals("dashstrike")||next.animation().equals("airdash"))sorcererDash.begin(boss);
        sorcererTargetId=target==null?null:target.getUUID();
    }
    public void onAttackSelected(LivingEntity target){
        independentHalos=false;
        if(boss.kind==1)sorcererTargetId=target==null?null:target.getUUID();
    }
    public void clearHalos(){independentHalos=false;Arrays.fill(halos,null);}
    public LivingEntity restoreTarget(ServerLevel server,LivingEntity target){
        if(boss.attack!=null&&target==null&&sorcererTargetId!=null&&server.getEntity(sorcererTargetId) instanceof LivingEntity savedTarget&&Combat.canHit(boss,savedTarget)){boss.setTarget(savedTarget);return savedTarget;}
        if(boss.attack!=null&&target!=null)sorcererTargetId=target.getUUID();
        return target;
    }
    public boolean finishAnimation(ServerLevel server,String finished){
        sorcererDash.finish(boss);
        sorcererTargetId=null;Arrays.fill(halos,null);
        if(finished.equals("initial_spawn")&&findStaff(server)==null)plantStaff(server,true);
        if(finished.equals("initial_spawn")){boss.animate("rise",133);return true;}
        if(finished.equals("risetransition")){boss.animate("rise",133);return true;}
        boss.cooldown=10+boss.random().nextInt(50);
        return false;
    }
    public void preDie(){killSorcererPlane();sorcererDash.finish(boss);}
    public void recallStaff(ServerLevel server){NullpointStaff staff=findStaff(server);if(staff!=null)staff.recall();}
    public void deathEffects(ServerLevel server){
        if(boss.deathTime==11)CommonEvents.resonanceSound(server,boss.position(),"sorcerer.die.float",5,30);
        if(boss.deathTime==56){CommonEvents.resonanceSound(server,boss.position(),"spells.soul_release",5,30);CommonEvents.resonanceSound(server,boss.position(),"sorcerer.die.stab",5,30);}
        if(boss.deathTime==116)CommonEvents.resonanceSound(server,boss.position(),"sorcerer.die.poof",5,30);
        if(boss.deathTime==126||boss.deathTime==130)CommonEvents.resonanceSound(server,boss.position(),"sorcerer.die.speardrop",5,30);
    }
    public void discardArtifacts(ServerLevel server,UUID ownerId,List<Entity> snapshot){
        for(Entity entity:snapshot){
            if(entity instanceof SpellBolt bolt&&bolt.getOwner()==boss
                ||entity instanceof SpellArea area&&ownerId.equals(area.ownerId())
                ||entity instanceof IceSpikeWave wave&&wave.ownerId().filter(ownerId::equals).isPresent()
                ||entity instanceof SorcererHail hail&&ownerId.equals(hail.ownerId())
                ||entity instanceof FrozenBlade blade&&ownerId.equals(blade.ownerId())
                ||entity instanceof SorcererBeamNode node&&ownerId.equals(node.ownerId())
                ||entity instanceof NullpointStaff staff&&staff.ownerId().filter(ownerId::equals).isPresent())entity.discard();
        }
        killSorcererPlane();staffId=null;
    }
    public void preLoad(CompoundTag tag){sorcererDash.finish(boss);independentHalos=tag.getBoolean("IndependentHalos");}
    public void preSave(CompoundTag tag){tag.putBoolean("IndependentHalos",independentHalos);}
    public void saveExtra(CompoundTag tag){
        if(sorcererDash.active())tag.put("SorcererDash",sorcererDash.save());
        if(boss.hasSorcererPlane()){CompoundTag plane=boss.syncedData().get(BossEntity.PLANE).copy();plane.putLong("Age",boss.level().getGameTime()-plane.getLong("Start"));tag.put("SorcererPlane",plane);}
        if(staffId!=null)tag.putUUID("Nullpoint",staffId);tag.putBoolean("StaffBroken",staffBroken);tag.putBoolean("StaffShield",staffShield);if(Double.isFinite(sorcererGroundY))tag.putDouble("SorcererGroundY",sorcererGroundY);
        if(meteorTear!=null){tag.putInt("MeteorTearAge",meteorTearAge);BossEntity.putVec(tag,"MeteorTear",meteorTear);if(meteorLanding!=null)BossEntity.putVec(tag,"MeteorLanding",meteorLanding);tag.putInt("MeteorShots",meteorShotsEmitted);tag.putFloat("MeteorTearYaw",meteorTearYaw);tag.putFloat("MeteorTearPitch",meteorTearPitch);}
        if(hailAge>=0&&hailCenter!=null){tag.putInt("HailAge",hailAge);BossEntity.putVec(tag,"HailCenter",hailCenter);tag.putIntArray("HailDelay",hailDelay);}
        if(boss.attack!=null){
            tag.putString("ActiveSorcererAttack",boss.attack.animation());
            if(sorcererTargetId!=null)tag.putUUID("SorcererTarget",sorcererTargetId);
            if(boss.attack.animation().equals("halobeam")){
                CompoundTag savedHalos=new CompoundTag();boolean any=false;
                for(int index=0;index<halos.length;index++)if(halos[index]!=null){BossEntity.putVec(savedHalos,"Halo"+index,halos[index]);any=true;}
                if(any)tag.put("SorcererHalos",savedHalos);
            }
        }
    }
    public void loadExtra(CompoundTag tag){
        killSorcererPlane();boss.attackAge=0;sorcererTargetId=null;Arrays.fill(halos,null);
        String clip=tag.getString("Animation");
        String savedAttackId=tag.contains("ActiveSorcererAttack")?tag.getString("ActiveSorcererAttack"):clip;
        BossEntity.Attack savedAttack=sorcererAttack(savedAttackId);
        if(tag.getInt("Remaining")>0&&savedAttack!=null){
            boss.attack=savedAttack;boss.attackAge=Math.max(0,Math.min(savedAttack.ticks(),tag.getInt("AttackAge")));
            sorcererTargetId=tag.hasUUID("SorcererTarget")?tag.getUUID("SorcererTarget"):null;
            String attackClip=savedAttack==ICE_SPIKES?"idle":savedAttack.animation();
            boss.animate(attackClip,tag.getInt("Remaining"));boss.syncedData().set(BossEntity.START,boss.level().getGameTime()-Math.max(0,tag.getInt("AnimationAge")));
            if(savedAttack.animation().equals("halobeam")){
                CompoundTag savedHalos=tag.getCompound("SorcererHalos");
                for(int index=0;index<halos.length;index++)if(savedHalos.contains("Halo"+index+"X"))halos[index]=BossEntity.readVec(savedHalos,"Halo"+index);
            }
        }else if(tag.contains("AnimationAge")&&tag.getInt("Remaining")>0&&Set.of("initial_spawn","risetransition","rise","falltransition","fall").contains(clip)){
            boss.animate(clip,tag.getInt("Remaining"));boss.syncedData().set(BossEntity.START,boss.level().getGameTime()-tag.getInt("AnimationAge"));
        }
        if(tag.contains("SorcererPlane")){CompoundTag plane=tag.getCompound("SorcererPlane").copy();plane.putLong("Start",boss.level().getGameTime()-plane.getLong("Age"));boss.syncedData().set(BossEntity.PLANE,plane);}
        if(boss.attack!=null&&(boss.attack.animation().equals("dashstrike")||boss.attack.animation().equals("airdash"))){if(tag.contains("SorcererDash"))sorcererDash.load(boss,tag.getCompound("SorcererDash"));else sorcererDash.begin(boss);}
        if(tag.hasUUID("Nullpoint"))staffId=tag.getUUID("Nullpoint");
        staffBroken=tag.getBoolean("StaffBroken");staffShield=tag.contains("StaffShield")?tag.getBoolean("StaffShield"):tag.hasUUID("Nullpoint")&&(boss.phase()&1)==1;
        if(tag.contains("SorcererGroundY"))sorcererGroundY=tag.getDouble("SorcererGroundY");
        if(tag.contains("MeteorTearAge")&&tag.contains("MeteorTearX")){meteorTearAge=tag.getInt("MeteorTearAge");meteorTear=BossEntity.readVec(tag,"MeteorTear");if(tag.contains("MeteorLandingX"))meteorLanding=BossEntity.readVec(tag,"MeteorLanding");meteorShotsEmitted=tag.contains("MeteorShots")?tag.getInt("MeteorShots"):tag.getInt("MeteorWaves");meteorTearYaw=tag.getFloat("MeteorTearYaw");meteorTearPitch=tag.getFloat("MeteorTearPitch");}
        if(tag.contains("HailAge")&&tag.contains("HailCenterX")){hailAge=tag.getInt("HailAge");hailCenter=BossEntity.readVec(tag,"HailCenter");int[] saved=tag.getIntArray("HailDelay");System.arraycopy(saved,0,hailDelay,0,Math.min(saved.length,hailDelay.length));}
    }
    public int meteorShotCount(){return 6;}
    public int meteorShotsEmitted(){return meteorShotsEmitted;}
    public NullpointStaff spawnNullpointForTest(ServerLevel server){plantStaff(server,false);return findStaff(server);}
    public void startAttackForTest(ServerLevel server,LivingEntity target,String clip){
        if(boss.kind!=1)return;
        for(BossEntity.Attack candidate:SORCERER_A)if(candidate.animation().equals(clip)){boss.beginAttack(server,target,candidate);return;}
        for(BossEntity.Attack candidate:SORCERER_B)if(candidate.animation().equals(clip)){boss.beginAttack(server,target,candidate);return;}
        if(ICE_SPIKES.animation().equals(clip))boss.beginAttack(server,target,ICE_SPIKES);
    }
}
