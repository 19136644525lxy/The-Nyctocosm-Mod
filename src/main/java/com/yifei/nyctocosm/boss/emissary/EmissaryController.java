package com.yifei.nyctocosm.boss.emissary;

import com.yifei.nyctocosm.CommonEvents;
import com.yifei.nyctocosm.NyctocosmMod;
import com.yifei.nyctocosm.boss.BossEffectTimeline;
import com.yifei.nyctocosm.boss.BossEntity;
import com.yifei.nyctocosm.boss.Boss;
import com.yifei.nyctocosm.boss.sorcerer.SorcererCastMath;
import com.yifei.nyctocosm.combat.Combat;
import com.yifei.nyctocosm.network.Network;
import com.yifei.nyctocosm.spell.SpellBolt;
import com.yifei.nyctocosm.spell.SpellCollisionPolicy;
import com.yifei.nyctocosm.world.portal.SafeTravel;
import java.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/** 天使（Emissary）行为控制器：四阶段转换、地面招式、飞行与抓取。由 BossEntity 骨架按 kind==2 分发。 */
public final class EmissaryController {
    public final Boss boss;
    private boolean emissaryFlying;
    private Vec3 emissaryPoint;
    private Vec3 emissaryFlightAnchor;
    private Vec3 emissaryFlyTarget;
    private final List<Vec3> emissaryOtherFlyTargets=new ArrayList<>();
    private UUID emissaryTargetId,emissarySeatId;
    private int emissaryFlySpeed=5,emissaryFlyTimer,emissaryPoise,emissaryCorkscrewScore;
    private boolean emissaryGrappleTrying,emissaryCorkscrew,emissaryPendingDeath,emissaryQuickTurn;
    private Vec3 emissaryEntranceLanding;
    private boolean emissaryHoverEstablished;
    private int emissaryPartsPhase=-1;
    private static final BossEntity.Attack[][] EMISSARY_ATTACKS={
        {BossEntity.a("phase1.attack.lightning_javelin",51,45,BossEntity.Pattern.VOLLEY,Combat.Element.DISSONANCE),BossEntity.a("phase1.attack.meteor_shower",51,40,BossEntity.Pattern.RAIN,Combat.Element.DISSONANCE),BossEntity.a("phase1.attack.pillars",71,35,BossEntity.Pattern.PILLARS,Combat.Element.DISSONANCE),BossEntity.a("phase1.attack.wingbeat",61,5,BossEntity.Pattern.RING,Combat.Element.DISSONANCE)},
        {BossEntity.a("phase2.attack.celestial_smite",111,80,BossEntity.Pattern.PILLARS,Combat.Element.RADIANCE),BossEntity.a("phase2.attack.flame_geysers",71,40,BossEntity.Pattern.PILLARS,Combat.Element.DISSONANCE),BossEntity.a("phase2.attack.sword_plunge",61,60,BossEntity.Pattern.DASH,Combat.Element.DISSONANCE),BossEntity.a("phase2.attack.wing_blast",81,40,BossEntity.Pattern.RING,Combat.Element.RADIANCE)},
        {BossEntity.a("phase3.attack.cross",21,35,BossEntity.Pattern.BEAM,Combat.Element.RADIANCE),BossEntity.a("phase3.attack.flame_slash",41,50,BossEntity.Pattern.VOLLEY,Combat.Element.DISSONANCE),BossEntity.a("phase3.attack.ground_slam",26,60,BossEntity.Pattern.RING,Combat.Element.DISSONANCE),BossEntity.a("phase3.attack.lightning_field",61,35,BossEntity.Pattern.PILLARS,Combat.Element.DISSONANCE),BossEntity.a("phase3.attack.seeking_missiles",51,15,BossEntity.Pattern.VOLLEY,Combat.Element.DISSONANCE),BossEntity.a("phase3.attack.swordspin",31,60,BossEntity.Pattern.RING,Combat.Element.DISSONANCE),BossEntity.a("phase3.attack.swordswing",21,50,BossEntity.Pattern.MELEE,Combat.Element.DISSONANCE)},
        {BossEntity.a("phase4.attack.apocalypse_rain",61,12,BossEntity.Pattern.RAIN,Combat.Element.DISSONANCE),BossEntity.a("phase4.attack.corkscrew",81,60,BossEntity.Pattern.DASH,Combat.Element.RADIANCE),BossEntity.a("phase4.attack.divine_blast",66,80,BossEntity.Pattern.RING,Combat.Element.RADIANCE),BossEntity.a("phase4.attack.fireball",41,35,BossEntity.Pattern.VOLLEY,Combat.Element.DISSONANCE),BossEntity.a("phase4.attack.godrays",61,40,BossEntity.Pattern.PILLARS,Combat.Element.RADIANCE),BossEntity.a("phase4.attack.grapple",91,25,BossEntity.Pattern.PULL,Combat.Element.DISSONANCE)}};
    private static final BossEntity.Attack STRAFE_LEFT=BossEntity.a("phase2.attack.strafeleft",21,0,BossEntity.Pattern.MOVE,Combat.Element.RADIANCE),STRAFE_RIGHT=BossEntity.a("phase2.attack.straferight",21,0,BossEntity.Pattern.MOVE,Combat.Element.RADIANCE),
        RUN_CLOSE=BossEntity.a("phase3.attack.run",21,0,BossEntity.Pattern.MOVE,Combat.Element.RADIANCE),FLIP_FAR=BossEntity.a("phase3.attack.flipfar",33,0,BossEntity.Pattern.MOVE,Combat.Element.RADIANCE),
        FRONT_KICK=BossEntity.a("phase3.attack.frontkick",21,35,BossEntity.Pattern.MELEE,Combat.Element.DISSONANCE),TAKE_OFF=BossEntity.a("phase4_idle_transition_flying",31,0,BossEntity.Pattern.MOVE,Combat.Element.RADIANCE),LAND=BossEntity.a("phase4_flying_transition_idle",31,0,BossEntity.Pattern.MOVE,Combat.Element.RADIANCE);
    private static final BossEntity.Attack STRAFE_LEFT_3=BossEntity.a("phase3.attack.strafeleft",21,0,BossEntity.Pattern.MOVE,Combat.Element.RADIANCE),STRAFE_RIGHT_3=BossEntity.a("phase3.attack.straferight",21,0,BossEntity.Pattern.MOVE,Combat.Element.RADIANCE),
        GRAPPLE_GRAB=BossEntity.a("phase4.attack.grapple_grab",181,25,BossEntity.Pattern.PULL,Combat.Element.DISSONANCE);

    public EmissaryController(Boss boss){this.boss=boss;}
    public static BossEntity.Attack selectEmissaryAttack(int phase,double distance,int roll,boolean flying){
        int r=Math.floorMod(roll,100);phase=Math.max(1,Math.min(4,phase));
        if(phase==1)return r<46?EMISSARY_ATTACKS[0][0]:r<61?EMISSARY_ATTACKS[0][2]:r<76?EMISSARY_ATTACKS[0][1]:EMISSARY_ATTACKS[0][3];
        if(phase==2)return r<21?STRAFE_RIGHT:r<41?STRAFE_LEFT:r<56?EMISSARY_ATTACKS[1][0]:r<71?EMISSARY_ATTACKS[1][2]:r<86?EMISSARY_ATTACKS[1][3]:EMISSARY_ATTACKS[1][1];
        if(phase==3){
            if(distance>10)return r<41?RUN_CLOSE:r<61?EMISSARY_ATTACKS[2][1]:r<81?EMISSARY_ATTACKS[2][4]:EMISSARY_ATTACKS[2][3];
            return r<11?STRAFE_RIGHT_3:r<21?STRAFE_LEFT_3:r<38?EMISSARY_ATTACKS[2][6]:r<47?EMISSARY_ATTACKS[2][0]:r<55?FRONT_KICK:r<73?EMISSARY_ATTACKS[2][5]:r<90?EMISSARY_ATTACKS[2][2]:FLIP_FAR;
        }
        // Source phase4 block661 decides immediately when no eligible player is within60.
        if(distance>60)return flying?null:TAKE_OFF;
        if(flying)return r<51?LAND:r<66?EMISSARY_ATTACKS[3][0]:r<86?EMISSARY_ATTACKS[3][1]:EMISSARY_ATTACKS[3][5];
        return r<51?TAKE_OFF:r<66?EMISSARY_ATTACKS[3][4]:r<86?EMISSARY_ATTACKS[3][3]:EMISSARY_ATTACKS[3][2];
    }
    private static BossEntity.Attack emissaryAttack(String clip){
        return Arrays.stream(EMISSARY_ATTACKS).flatMap(Arrays::stream).filter(value->value.animation().equals(clip)).findFirst()
            .orElseGet(()->Arrays.stream(new BossEntity.Attack[]{STRAFE_LEFT,STRAFE_RIGHT,STRAFE_LEFT_3,STRAFE_RIGHT_3,RUN_CLOSE,FLIP_FAR,FRONT_KICK,TAKE_OFF,LAND,GRAPPLE_GRAB}).filter(value->value.animation().equals(clip)).findFirst().orElse(null));
    }
    public static List<Player> emissaryFireballTargets(Vec3 origin,Collection<? extends Player> candidates){
        return candidates.stream()
            .filter(player->!player.isSpectator()&&!player.getTags().contains("emissarydebug")&&player.distanceToSqr(origin)<=100*100)
            .map(player->(Player)player).toList();
    }
    public static Vec3 emissaryFireballDirection(Vec3 origin,float fallbackYaw,float fallbackPitch,
            List<? extends Player> eligible,int selectedIndex){
        Vec3 fallback=Vec3.directionFromRotation(fallbackPitch,fallbackYaw);
        if(eligible.isEmpty())return fallback;
        Vec3 toward=eligible.get(Math.floorMod(selectedIndex,eligible.size())).position().subtract(origin);
        return toward.lengthSqr()<1.0E-12?fallback:toward;
    }
    public static Vec3 emissaryFlightHeading(Vec3 forward,Vec3 toward,boolean quick){
        Vec3 aimed=forward.scale(25).add(toward.normalize().scale(.5)).normalize();
        return quick?aimed.scale(10).add(toward.normalize().scale(.5)).normalize():aimed;
    }
    public static SorcererCastMath.Rotation emissaryFlightRotation(float yaw,float pitch,Vec3 toward,boolean quick){
        var turn=SorcererCastMath.turn(yaw,pitch,toward,25);
        return quick?SorcererCastMath.turn(turn.yaw(),turn.pitch(),toward,10):turn;
    }
    public static boolean emissaryPhysicalHit(LivingEntity owner,LivingEntity target,float damage){
        return Combat.canHit(owner,target)&&target.hurt(target.level().damageSources().mobAttack(owner),damage);
    }
    public boolean isFlying(){return emissaryFlying;}
    public void advanceAppearance(int frame){
        var before=boss.syncedData().get(BossEntity.APPEARANCE);if(before.isEmpty())before=EmissaryAppearance.initial(boss.phase());
        var after=EmissaryAppearance.at(before,boss.animation(),frame);if(!after.equals(boss.syncedData().get(BossEntity.APPEARANCE)))boss.syncedData().set(BossEntity.APPEARANCE,after);
    }
    public void beginEntrance(ServerLevel server){
        var safe=SafeTravel.findSurface(server,boss,boss.position());
        if(safe.isEmpty()){boss.discard();return;}
        emissaryEntranceLanding=safe.get();emissaryHoverEstablished=false;emissaryFlying=false;
        boss.attack=null;boss.attackAge=0;
        boss.getNavigation().stop();boss.setDeltaMovement(Vec3.ZERO);boss.setNoGravity(true);boss.setXRot(0);boss.yBodyRot=boss.getYRot();
        boss.setPos(emissaryEntranceLanding.subtract(Vec3.directionFromRotation(50,boss.getYRot()).scale(100)));
        keepEntranceTicking(server);
        boss.animate("arrival",95);CommonEvents.sound(server,emissaryEntranceLanding,"emissary.spawn.meteor",50);
    }
    public void preTick(){
        updateGravity();
        if(boss.phase()<=2||boss.emissaryApproaching()||boss.animation().equals("spawn")){boss.setDeltaMovement(Vec3.ZERO);boss.fallDistance=0;}
    }
    public void updateGravity(){
        // The phase-four source root remains a marker even between flying attacks.
        // Vanilla gravity was undoing Godrays' lift and dropping landing/takeoff roots.
        boss.setNoGravity(boss.isAlive()&&(boss.phase()<=2||boss.phase()==4||emissaryFlying||boss.emissaryApproaching()||boss.animation().equals("spawn")));
    }
    public void establishHover(ServerLevel server){
        if(emissaryHoverEstablished||boss.phase()>2||boss.emissaryApproaching()||boss.animation().equals("spawn"))return;
        // Older saves lost every root-motion command and may contain a phase-one boss on/in the ground.
        server.getChunkAt(boss.blockPosition());
        double surface=server.getHeight(Heightmap.Types.WORLD_SURFACE,boss.getBlockX(),boss.getBlockZ());
        double lift=37*1.5*Math.sin(Math.toRadians(35));
        if(boss.getY()<surface+lift){
            var landing=SafeTravel.findSurface(server,boss,boss.position());
            if(landing.isEmpty())return;
            Vec3 at=landing.get().add(0,lift,0);boss.setPos(at);boss.hurtMarked=true;
        }
        emissaryHoverEstablished=true;boss.setDeltaMovement(Vec3.ZERO);
    }
    public LivingEntity restoreTarget(ServerLevel server,LivingEntity target){
        if(target==null&&emissaryTargetId!=null&&server.getEntity(emissaryTargetId) instanceof LivingEntity savedTarget){boss.setTarget(savedTarget);return savedTarget;}
        return target;
    }
    public void tickLifecycle(ServerLevel server,LivingEntity target){
        String clip=boss.animation();
        int cueAge=clip.matches("phase[1-3]_transition")?EmissaryAppearance.duration(clip)-boss.remaining:(int)boss.animationTime(0);
        BossEffectTimeline.serverFrame(server,boss,clip,cueAge);
        if(clip.equals("arrival")){
            if(emissaryEntranceLanding==null){beginEntrance(server);return;}
            int age=95-boss.remaining;
            Vec3 at=emissaryEntranceLanding.subtract(Vec3.directionFromRotation(50,boss.getYRot()).scale(100-age));
            server.getChunkAt(BlockPos.containing(at));boss.setPos(at);boss.setDeltaMovement(Vec3.ZERO);boss.hurtMarked=true;
            // Source particles are forced: ordinary 32-block delivery hides the distant approach.
            for(ServerPlayer viewer:server.players())if(viewer.distanceToSqr(boss)<=300*300){
                server.sendParticles(viewer,ParticleTypes.FLAME,true,boss.getX(),boss.getY(),boss.getZ(),20,1,1,1,.2);
                server.sendParticles(viewer,ParticleTypes.LAVA,true,boss.getX(),boss.getY(),boss.getZ(),10,1,1,1,.4);
                server.sendParticles(viewer,ParticleTypes.CAMPFIRE_COSY_SMOKE,true,boss.getX(),boss.getY(),boss.getZ(),5,1,1,1,.1);
                server.sendParticles(viewer,ParticleTypes.FLASH,true,boss.getX(),boss.getY(),boss.getZ(),1,1,1,1,0);
                server.sendParticles(viewer,ParticleTypes.SMOKE,true,boss.getX(),boss.getY(),boss.getZ(),10,1,1,1,0);
                server.sendParticles(viewer,ParticleTypes.WAX_ON,true,boss.getX(),boss.getY(),boss.getZ(),5,1,1,1,0);
                server.sendParticles(viewer,ParticleTypes.END_ROD,true,boss.getX(),boss.getY(),boss.getZ(),5,1,1,1,0);
            }
            if(boss.remaining==0){
                CommonEvents.sound(server,emissaryEntranceLanding,"emissary.spawn.land",50);
                boss.animate("spawn",141);tickLifecycle(server,target); // Original spawn keyframe zero moves one block.
            }
        }else if(clip.equals("spawn")){
            int age=141-boss.remaining;
            if(age<=8&&age%2==0)boss.setPos(boss.position().add(Vec3.directionFromRotation(50,boss.getYRot())));
            if(age==20)CommonEvents.sound(server,boss.position(),"emissary.spawn.open",50);
            if(age==60)CommonEvents.sound(server,boss.position(),"emissary.spawn.flap",50);
            if(age>=62&&age<99){
                boss.setPos(boss.position().subtract(Vec3.directionFromRotation(35,boss.getYRot()).scale(1.5)));
                server.sendParticles(ParticleTypes.FLAME,boss.getX(),boss.getY(),boss.getZ(),10,1,1,1,.08);boss.hurtMarked=true;
            }
        }else if(clip.equals("phase1_transition")){
            // Direct particles are client keyframes; sounds use the source event table above.
        }else if(clip.equals("phase2_transition")){
            int age=101-boss.remaining;
            if(age<5)boss.setPos(boss.position().subtract(com.yifei.nyctocosm.boss.serpent.SerpentMotion.forward(boss.getYRot()).scale(5)));
            if(age>=41&&age<60)boss.setPos(boss.position().add(com.yifei.nyctocosm.boss.serpent.SerpentMotion.forward(boss.getYRot()).scale(4)).add(0,-.8,0));
            if(age==60){
                Vec3 toward=target==null?com.yifei.nyctocosm.boss.serpent.SerpentMotion.forward(boss.getYRot()):target.position().subtract(boss.position()).multiply(1,0,1).normalize();
                var landing=SafeTravel.findSurface(server,boss,boss.position().subtract(toward.scale(20)));
                if(landing.isEmpty())landing=SafeTravel.findSurface(server,boss,boss.position());
                landing.ifPresent(boss::setPos);boss.setDeltaMovement(Vec3.ZERO);boss.hurtMarked=true;
            }
        }
        if(clip.equals("arrival")||clip.equals("spawn"))keepEntranceTicking(server);
    }
    private void keepEntranceTicking(ServerLevel server){
        // A short-lived ticket follows the approach even at low simulation distance; never forceload a save permanently.
        server.getChunkSource().addRegionTicket(TicketType.POST_TELEPORT,new net.minecraft.world.level.ChunkPos(boss.blockPosition()),2,boss.getId());
    }
    public boolean tickTransitions(ServerLevel server,LivingEntity target){
        // The source advances one phase at a time, and only after the current attack ends.
        int threshold=boss.phase()==1?1000:boss.phase()==2?600:200;
        if(boss.phase()<4&&boss.getHealth()<=threshold){
            boss.getNavigation().stop();boss.setDeltaMovement(Vec3.ZERO);boss.animate("phase"+boss.phase()+"_transition",boss.phase()==1?81:boss.phase()==2?101:181);
            tickLifecycle(server,target);return true;
        }
        if(boss.phase()==4&&emissaryPendingDeath){emissaryPendingDeath=false;boss.hurtBypassingOverrides(server.damageSources().genericKill(),Float.MAX_VALUE);return true;}
        tickFlight(server,target);
        return false;
    }
    public void tickWithoutTarget(ServerLevel server){
        if(boss.attack==GRAPPLE_GRAB){
            if(boss.attackAge==60)singleFlyTarget(boss.position().add(com.yifei.nyctocosm.boss.serpent.SerpentMotion.localForward(boss.getYRot(),50).scale(150)));
            if(boss.attackAge==160){releaseGrapple(server);chooseFlyTarget(null);}
        }
        if(boss.attackAge==70){emissaryGrappleTrying=false;emissaryQuickTurn=false;}
        if(boss.attackAge==64){emissaryCorkscrew=false;emissaryQuickTurn=false;}
    }
    public void onBeginAttack(ServerLevel server,LivingEntity target,BossEntity.Attack next){
        boss.getNavigation().stop();boss.setDeltaMovement(Vec3.ZERO);
        if(target!=null){emissaryPoint=target.position();emissaryTargetId=target.getUUID();}
        if(next==TAKE_OFF){emissaryFlySpeed=5;chooseFlyTarget(target);}
    }
    public void finishAnimation(ServerLevel server,String finished){
        if(finished.equals("spawn"))emissaryHoverEstablished=emissaryEntranceLanding!=null;
        if(finished.matches("phase[1-3]_transition"))boss.syncedData().set(BossEntity.PHASE,finished.charAt(5)-'0'+1);
        releaseGrapple(server);emissaryCorkscrew=false;emissaryGrappleTrying=false;emissaryQuickTurn=false;
        boss.cooldown=boss.phase()==1?20+boss.random().nextInt(80):boss.phase()==2?boss.random().nextInt(50):boss.phase()==3?boss.random().nextInt(15):boss.random().nextInt(30);
        updateGravity();
    }
    public void parry(ServerLevel server){
        if(boss.kind!=2||boss.phase()!=3||!boss.isAlive())return;
        for(var region:server.getEntitiesOfClass(EmissaryHitRegion.class,boss.getBoundingBox().inflate(64),r->r.ownedBy(boss)))region.discard();
        boss.attack=null;boss.attackAge=0;boss.getNavigation().stop();boss.setDeltaMovement(Vec3.ZERO);
        boolean broken=++emissaryPoise>=7;if(broken)emissaryPoise=0;
        boss.animate(broken?"phase3.parry.break":"phase3.parry",broken?61:21);
        CommonEvents.sound(server,boss.position(),broken?"emissary.attack.parrybreak":"emissary.attack.parry",2);
        server.sendParticles(ParticleTypes.FLASH,boss.getX(),boss.getY()+1.7,boss.getZ(),1,0,0,0,0);
    }
    private void lunge(LivingEntity target,double step,int repeats){
        for(int i=0;i<repeats&&boss.distanceTo(target)>1.5;i++)boss.setPos(boss.position().add(com.yifei.nyctocosm.boss.serpent.SerpentMotion.forward(boss.getYRot()).scale(step)));
    }
    private void region(ServerLevel server,double forward,double up,float width,float height,float damage,boolean parry){
        // Source melee commands use generic damage, not one of the six spell elements.
        EmissaryHitRegion.create(server,boss,boss.position().add(com.yifei.nyctocosm.boss.serpent.SerpentMotion.forward(boss.getYRot()).scale(forward)).add(0,up,0),width,height,damage,null,parry);
    }
    public Vec3 grapplePosition(){
        if(boss.attack==GRAPPLE_GRAB){
            var hand=EmissaryDamagePose.sampleAt(boss,"phase4.attack.grapple_grab","rightmiddlewingtip3",boss.attackAge);
            return EmissaryDamagePose.worldPosition(boss,hand);
        }
        return EmissaryPose.localPoint(boss.position(),boss.getYRot(),boss.getXRot(),new Vec3(0,-1,6));
    }
    public boolean holdsGrapple(UUID seat){return seat.equals(emissarySeatId)&&boss.animation().equals(GRAPPLE_GRAB.animation())&&boss.attackAge<160;}
    private void releaseGrapple(ServerLevel server){
        if(emissarySeatId!=null&&server.getEntity(emissarySeatId) instanceof EmissaryGrappleSeat seat){seat.ejectPassengers();seat.discard();}
        emissarySeatId=null;
    }
    private void chooseFlyTarget(LivingEntity target){
        Vec3 center=flightCenter(target);var players=shuffledFlightPlayers(300);
        emissaryOtherFlyTargets.clear();
        if(players.isEmpty())emissaryFlyTarget=randomWaypoint(center);
        else for(int i=0;i<players.size();i++){
            Vec3 point=randomWaypoint(players.get(i).position());
            if(i==players.size()-1)emissaryFlyTarget=point;else emissaryOtherFlyTargets.add(point);
        }
    }
    private Vec3 randomWaypoint(Vec3 center){
        // Source data-get uses floor at scale100, including negative coordinates.
        return new Vec3(Math.floor(center.x*100)*.01,Math.floor((center.y+20)*100)*.01,Math.floor(center.z*100)*.01)
            .add(boss.random().nextInt(1500)*.02-15,boss.random().nextInt(1500)*.02-15,boss.random().nextInt(1500)*.02-15);
    }
    public List<Player> flightPlayers(double radius){
        var server=(ServerLevel)boss.level();var players=new LinkedHashSet<Player>(server.players());
        players.addAll(server.getEntitiesOfClass(Player.class,new AABB(boss.position(),boss.position()).inflate(radius)));
        return players.stream().filter(p->!p.isSpectator()&&!p.getTags().contains("emissarydebug")&&boss.distanceToSqr(p)<=radius*radius).toList();
    }
    private List<Player> shuffledFlightPlayers(double radius){
        var players=new ArrayList<>(flightPlayers(radius));for(int i=players.size()-1;i>0;i--)Collections.swap(players,i,boss.random().nextInt(i+1));return players;
    }
    private Vec3 attackFlyTarget(LivingEntity fallback,double radius,double up,boolean all){
        var players=shuffledFlightPlayers(radius);emissaryOtherFlyTargets.clear();
        if(all)for(int i=0;i<players.size()-1;i++)emissaryOtherFlyTargets.add(players.get(i).position().add(0,up,0));
        return (players.isEmpty()?fallback==null?flightCenter(null):fallback.position():players.get(players.size()-1).position()).add(0,up,0);
    }
    private void singleFlyTarget(Vec3 point){emissaryOtherFlyTargets.clear();emissaryFlyTarget=point;}
    private boolean reachedFlyTarget(){return boss.position().distanceToSqr(emissaryFlyTarget)<=25||emissaryOtherFlyTargets.stream().anyMatch(point->boss.position().distanceToSqr(point)<=25);}
    private Vec3 flightCenter(LivingEntity target){
        if(target!=null&&target.isAlive()&&!boss.hasIndirectPassenger(target))emissaryFlightAnchor=target.position();
        if(emissaryFlightAnchor==null)emissaryFlightAnchor=boss.position();
        return emissaryFlightAnchor;
    }
    public void tickFlight(ServerLevel server,LivingEntity target){
        if(!emissaryFlying)return;
        Vec3 anchor=flightCenter(target);
        boss.getNavigation().stop();boss.setNoGravity(true);boss.setDeltaMovement(Vec3.ZERO);
        if(emissaryFlyTarget==null)chooseFlyTarget(target);
        for(int step=0;step<Math.max(1,emissaryFlySpeed-1);step++){
            var turn=turnToFlyTargets(25);
            if(emissaryQuickTurn)turn=turnToFlyTargets(10);
            // Preserve source turns near the player; steer back before the eight-chunk visibility limit.
            if(boss.position().distanceToSqr(anchor)>96*96){
                singleFlyTarget(anchor.add(0,20,0));turn=SorcererCastMath.facing(emissaryFlyTarget.subtract(boss.position()));
            }
            boss.setYRot(turn.yaw());boss.setXRot(turn.pitch());boss.yBodyRot=boss.getYRot();
            Vec3 next=boss.position().add(turn.forward().scale(.5));
            Vec3 fromAnchor=next.subtract(anchor);if(fromAnchor.lengthSqr()>128*128)next=anchor.add(fromAnchor.normalize().scale(128));
            if(!server.hasChunkAt(BlockPos.containing(next)))break;
            boss.setPos(next);boss.fallDistance=0;
            if(!Boss.emissaryPassable(server,boss.blockPosition())){
                boss.setPos(boss.getX(),server.getHeight(Heightmap.Types.WORLD_SURFACE,boss.getBlockX(),boss.getBlockZ()),boss.getZ());boss.setXRot(0);
                if(emissarySeatId!=null){singleFlyTarget(boss.position().add(com.yifei.nyctocosm.boss.serpent.SerpentMotion.forward(boss.getYRot()).scale(50)));CommonEvents.sound(server,boss.position(),"emissary.attack.grapple.slam",3);}
                else if(emissaryCorkscrew){chooseFlyTarget(target);emissaryQuickTurn=false;}
            }
            if(reachedFlyTarget()){chooseFlyTarget(target);if(emissaryCorkscrew)emissaryQuickTurn=false;}
            if(emissaryGrappleTrying){
                var victims=server.getEntitiesOfClass(Player.class,boss.getBoundingBox().inflate(1.5),p->Combat.canHit(boss,p)&&p.getBoundingBox().intersects(new AABB(boss.position().add(-1.5,-1.5,-1.5),boss.position().add(1.5,1.5,1.5))));
                if(!victims.isEmpty()){
                    emissaryGrappleTrying=false;singleFlyTarget(boss.position().add(com.yifei.nyctocosm.boss.serpent.SerpentMotion.localForward(boss.getYRot(),-35).scale(100)));
                    boss.beginAttack(server,target,GRAPPLE_GRAB);
                    var seat=EmissaryGrappleSeat.create(server,boss);emissarySeatId=seat.getUUID();
                    for(Player player:server.getEntitiesOfClass(Player.class,boss.getBoundingBox().inflate(5),p->Combat.canHit(boss,p)&&boss.distanceToSqr(p)<=25))player.startRiding(seat,true);
                    CommonEvents.sound(server,boss.position(),"emissary.attack.grapple.grab",3);
                }
            }
            if(emissarySeatId!=null&&server.getEntity(emissarySeatId) instanceof EmissaryGrappleSeat seat)seat.follow(boss);
            if(emissaryCorkscrew){
                emissaryCorkscrewScore=(emissaryCorkscrewScore+1)%36;
                Network.sourceVisual(server,"corkscrew",emissaryCorkscrewScore,boss.position(),boss.getYRot(),boss.getXRot());
                var corkBox=new AABB(boss.position().add(-.5,-.5,-.5),boss.position().add(.5,.5,.5));
                for(Player victim:server.getEntitiesOfClass(Player.class,corkBox))if(!victim.isSpectator()&&!victim.getTags().contains("emissarydebug")&&victim.hurtTime==0)emissaryPhysicalHit(boss,victim,60);
            }
            Network.sourceVisual(server,"flight_step",EmissaryPose.localPoint(boss.position(),boss.getYRot(),boss.getXRot(),new Vec3(0,.5,0)),boss.getYRot(),boss.getXRot());
        }
        Network.sourceVisual(server,"flight",boss.position(),boss.getYRot(),boss.getXRot());
        CommonEvents.resonanceSound(server,boss.position(),"emissary.movement.fly",3,200);boss.hurtMarked=true;
        // ai/subtick -> block736/737 remains reachable for every ordinary flying model.
        // Only the three source tags pause this clock; Apocalypse Rain does not.
        if(!emissaryCorkscrew&&!emissaryGrappleTrying&&emissarySeatId==null)--emissaryFlyTimer;
        if(emissaryFlyTimer<=0){chooseFlyTarget(target);emissaryFlyTimer=20+boss.random().nextInt(40);}
    }
    private SorcererCastMath.Rotation turnToFlyTargets(double distance){
        SorcererCastMath.Rotation turn=null;
        // execute facing entity forks in marker creation order, including multiplayer targets.
        var targets=new ArrayList<>(emissaryOtherFlyTargets);targets.add(emissaryFlyTarget);
        for(Vec3 point:targets){
            turn=SorcererCastMath.turn(boss.getYRot(),boss.getXRot(),point.subtract(boss.position()),distance);
            boss.setYRot(turn.yaw());boss.setXRot(turn.pitch());
        }
        return turn;
    }
    public void perform(ServerLevel server,LivingEntity target){
        String id=boss.attack.animation();
        if(boss.attack.pattern()==BossEntity.Pattern.MOVE){
            Vec3 toward=target.position().subtract(boss.position());if(toward.lengthSqr()<.001)toward=boss.getLookAngle();toward=toward.normalize();Vec3 right=new Vec3(-toward.z,0,toward.x);
            if(boss.attack==STRAFE_LEFT||boss.attack==STRAFE_RIGHT){if(boss.attackAge>=3&&boss.attackAge<17)boss.setPos(boss.position().add(right.scale(boss.attack==STRAFE_LEFT?-4:4)));}
            else if(boss.attack==STRAFE_LEFT_3||boss.attack==STRAFE_RIGHT_3){if(boss.attackAge<=9)boss.setPos(boss.position().add(right.scale(boss.attack==STRAFE_LEFT_3?-1:1)));}
            else if(boss.attack==RUN_CLOSE){boss.setYRot((float)(Math.toDegrees(Math.atan2(toward.z,toward.x))-90));boss.setPos(boss.position().add(toward.multiply(.7,0,.7)));if(boss.distanceTo(target)<=5){boss.finishAnimation(server);return;}if(boss.attackAge>=10)boss.attackAge=-1;boss.remaining=12;}
            else if(boss.attack==FLIP_FAR&&boss.attackAge>=4&&boss.attackAge<=16)boss.setPos(boss.position().add(toward.multiply(-.7,0,-.7)));
            else if(boss.attack==TAKE_OFF){
                boss.setNoGravity(true);
                if(boss.attackAge>=1&&boss.attackAge<=5)boss.setPos(boss.position().add(0,.80625,0));
                if(boss.attackAge==14)emissaryFlying=true;
                if(boss.attackAge==1)CommonEvents.sound(server,boss.position(),"emissary.movement.flytransition",5);
            }else if(boss.attack==LAND){
                if(boss.attackAge>=0&&boss.attackAge<=4){
                    double y=boss.getY()-.80625;
                    // Preserve the existing protection against burying a native boss in terrain.
                    if(server.hasChunkAt(boss.blockPosition()))y=Math.max(y,server.getHeight(Heightmap.Types.WORLD_SURFACE,boss.getBlockX(),boss.getBlockZ()));
                    boss.setPos(boss.getX(),y,boss.getZ());
                }
                if(boss.attackAge==0||boss.attackAge==3||boss.attackAge==6||boss.attackAge>=8&&boss.attackAge<=11)emissaryFlySpeed=Math.max(1,emissaryFlySpeed-1);
                if(boss.attackAge==4){emissaryFlying=false;boss.setNoGravity(true);boss.setXRot(0);}
            }
            if(boss.attack==FLIP_FAR&&boss.attackAge==24)combo(server,target);
            boss.hurtMarked=true;return;
        }
        if(id.endsWith("lightning_javelin")){
            if(boss.attackAge==5)CommonEvents.sound(server,boss.position(),"emissary.attack.lightning_javelin",5);
            // Frame 5 creates the marker, which charges twenty ticks before throwing.
            if(boss.attackAge==25){
                var hand=EmissaryDamagePose.sampleAt(boss,id,"righthand",boss.attackAge);
                Vec3 origin=EmissaryDamagePose.offsetFromLocator(boss,hand,0,0,-3);
                SpellBolt.launch(server,boss,origin,target.getEyePosition().subtract(origin),45,Combat.Element.DISSONANCE,5,2).effect(SpellBolt.Effect.LIGHTNING,40).collisionPolicy(SpellCollisionPolicy.SOURCE_PASSTHROUGH);
            }
        }else if(id.endsWith("meteor_shower")){
            if(boss.attackAge==10){
                CommonEvents.sound(server,boss.position(),"emissary.attack.meteor_shower.activate",5);
                var center=EmissaryDamagePose.sampleAt(boss,id,"center",boss.attackAge);
                Vec3 locatorPosition=EmissaryDamagePose.worldPosition(boss,center);
                Vec3 origin=EmissaryPose.localPoint(locatorPosition,boss.getYRot()+center.yaw(),0,new Vec3(0,0,10));
                EmissaryHazard.create(server,boss,target,EmissaryHazard.Kind.METEOR_SHOWER,origin);
            }
        }else if(id.endsWith("pillars")){
            if(boss.attackAge==10)CommonEvents.resonanceSound(server,boss.position(),"emissary.attack.pillars.throw",50,300);
            if(boss.attackAge==33)EmissaryPillar.launch(server,boss);
        }else if(id.endsWith("wingbeat")){
            if(boss.attackAge==20)CommonEvents.sound(server,boss.position(),"emissary.attack.wingbeat.flap",5);
            if(boss.attackAge==23)EmissaryHazard.wingbeat(server,boss,target);
        }else if(id.endsWith("celestial_smite")){
            if(boss.attackAge==0){CommonEvents.resonanceSound(server,boss.position(),"emissary.attack.celestial_smite.rise",50,300);EmissaryCelestialTear.fireStartupSequence(server,boss);}
            if(boss.attackAge==26)EmissaryCelestialTear.createTelegraph(server,boss);
            if(boss.attackAge==50)EmissaryCelestialTear.activateOwned(server,boss);
            if(boss.attackAge==93)EmissaryCelestialTear.fireOwned(server,boss);
        }else if(id.endsWith("flame_geysers")){
            if(boss.attackAge==0)CommonEvents.resonanceSound(server,boss.position(),"emissary.attack.flame_geysers.charge",50,300);
            if(EmissaryFlameGeyser.isHandCueFrame(boss.attackAge))EmissaryFlameGeyser.handCue(server,boss,boss.attackAge);
            if(boss.attackAge==40)EmissaryFlameGeyser.launch(server,boss);
        }else if(id.endsWith("sword_plunge")){
            if(boss.attackAge==5)CommonEvents.sound(server,boss.position(),"emissary.attack.sword_plunge.conjure",50);
            if(boss.attackAge==45){
                CommonEvents.sound(server,boss.position(),"emissary.attack.sword_plunge.drop",50);
                EmissarySwordPlunge.handCue(server,boss,boss.attackAge);
                EmissarySwordPlunge.create(server,boss);
            }
        }else if(id.endsWith("wing_blast")){
            if(boss.attackAge==0)CommonEvents.resonanceSound(server,boss.position(),"emissary.attack.wing_blast.rise",50,300);
            if(boss.attackAge==12){CommonEvents.resonanceSound(server,boss.position(),"emissary.attack.wing_blast.eyes",50,300);EmissaryWingBlastRay.start(server,boss);}
            if(boss.attackAge==35)EmissaryWingBlastRay.activate(server,boss);
        }else if(id.endsWith("cross")){
            if(boss.attackAge>=5&&boss.attackAge<=7)lunge(target,.65,2);
            if(boss.attackAge==6)for(double at:new double[]{1,2.5,4})region(server,at,1.7,1.5f,1.5f,35,true);
        }else if(id.endsWith("flame_slash")){
            if(boss.attackAge==2)EmissaryFlameSlashWave.launch(server,boss,true);
            if(boss.attackAge==13)EmissaryFlameSlashWave.launch(server,boss,false);
        }else if(id.endsWith("ground_slam")){
            if(boss.attackAge>=1&&boss.attackAge<=4)lunge(target,.8,1);
            if(boss.attackAge==5){region(server,2.5,0,3,.1f,60,false);CommonEvents.sound(server,boss.position(),"emissary.attack.stomp",2);}
        }else if(id.endsWith("lightning_field")){
            if(boss.attackAge==5)CommonEvents.sound(server,boss.position(),"emissary.attack.lightning_field.charge",5);
            if(boss.attackAge==34)EmissaryHazard.lightningField(server,boss);
        }else if(id.endsWith("seeking_missiles")){
            if(boss.attackAge==0)CommonEvents.resonanceSound(server,boss.position(),"emissary.attack.seeking_missiles.charge",50,300);
            if(boss.attackAge>=2&&boss.attackAge<=14&&boss.attackAge%2==0){
                EmissarySeekingMissile.summon(server,boss,boss.attackAge);
            }
            if(boss.attackAge==23){
                for(Entity entity:server.getAllEntities())if(entity instanceof EmissarySeekingMissile missile&&boss.getUUID().equals(missile.ownerId()))missile.activate();
                CommonEvents.resonanceSound(server,boss.position(),"emissary.attack.seeking_missiles.activate",5,100);
            }
        }else if(id.endsWith("swordspin")){
            if(boss.attackAge>=6&&boss.attackAge<=8)lunge(target,.6,2);
            if(boss.attackAge==7){region(server,3,1.5,4,1.5f,60,true);CommonEvents.sound(server,boss.position(),"emissary.attack.sword_swing",2);}
        }else if(id.endsWith("swordswing")||id.endsWith("frontkick")){
            if(id.endsWith("frontkick")){
                if(boss.attackAge>=4&&boss.attackAge<=6)lunge(target,.5,3);
                if(boss.attackAge==4){for(int n=1;n<=5;n++)region(server,n,1,1,1.5f,35,true);CommonEvents.sound(server,boss.position(),"emissary.attack.punch",2);}
            }else{
                if(boss.attackAge==1){region(server,2.5,1.7,4,.8f,50,true);CommonEvents.sound(server,boss.position(),"emissary.attack.sword_swing",2);}
                if(boss.attackAge==2||boss.attackAge==3)lunge(target,.8,1);
            }
        }else if(id.endsWith("apocalypse_rain")){
            if(boss.attackAge==0){emissaryFlyTarget=attackFlyTarget(target,100,30,false);CommonEvents.resonanceSound(server,boss.position(),"emissary.attack.apocalypse_rain.charge",5,300);}
            if(boss.attackAge>=10&&boss.attackAge<=44)EmissaryRainShard.fire(server,boss);
        }else if(id.endsWith("corkscrew")){
            if(boss.attackAge==13){emissaryCorkscrew=true;emissaryQuickTurn=true;emissaryFlyTarget=attackFlyTarget(target,300,0,true);CommonEvents.sound(server,boss.position(),"emissary.attack.corkscrew.spin",5);}
            if(boss.attackAge==64){emissaryCorkscrew=false;emissaryQuickTurn=false;chooseFlyTarget(target);}
        }else if(id.endsWith("divine_blast")){
            if(boss.attackAge==0){
                var center=EmissaryDamagePose.sampleAt(boss,id,"center",boss.attackAge);
                Vec3 origin=EmissaryPose.localPoint(EmissaryDamagePose.worldPosition(boss,center),boss.getYRot()+center.yaw(),boss.getXRot()+center.pitch(),new Vec3(0,.5,0));
                EmissaryHazard.create(server,boss,target,EmissaryHazard.Kind.DIVINE_BLAST,origin);CommonEvents.sound(server,boss.position(),"emissary.attack.divine_blast.charge",5);
            }
        }else if(id.endsWith("fireball")){
            if(boss.attackAge==8||boss.attackAge==18){
                String handName=boss.attackAge==8?"righthand":"lefthand";
                var hand=EmissaryDamagePose.sampleAt(boss,id,handName,boss.attackAge);
                Vec3 origin=EmissaryDamagePose.worldPosition(boss,hand);
                Collection<Player> candidates=server.getEntitiesOfClass(Player.class,new AABB(origin,origin).inflate(100));
                List<Player> eligible=emissaryFireballTargets(origin,candidates);
                int selectedIndex=eligible.isEmpty()?0:boss.random().nextInt(eligible.size());
                Vec3 direction=emissaryFireballDirection(origin,boss.getYRot()+hand.yaw(),boss.getXRot()+hand.pitch(),eligible,selectedIndex);
                SpellBolt.launch(server,boss,origin,direction,35,Combat.Element.DISSONANCE,1,3).effect(SpellBolt.Effect.FIRE,60).emissaryFireballFlight().collisionPolicy(SpellCollisionPolicy.SOURCE_PASSTHROUGH);
                CommonEvents.sound(server,boss.position(),"emissary.attack.fireball.shoot",5);
            }
        }else if(id.endsWith("godrays")){
            if(boss.attackAge>=5&&boss.attackAge<=9){boss.setPos(boss.position().add(0,1.5,0));boss.hurtMarked=true;}
            if(boss.attackAge==15){
                var center=EmissaryDamagePose.sampleAt(boss,id,"center",boss.attackAge);
                Vec3 origin=EmissaryDamagePose.worldPosition(boss,center).add(0,1,0);
                EmissaryHazard.create(server,boss,target,EmissaryHazard.Kind.GODRAYS,origin);CommonEvents.sound(server,boss.position(),"emissary.attack.godrays.charge",5);
            }
        }else if(id.endsWith("grapple")){
            if(boss.attackAge==1)CommonEvents.sound(server,boss.position(),"emissary.attack.grapple.ready",5);
            if(boss.attackAge==15){emissaryGrappleTrying=true;emissaryQuickTurn=true;emissaryFlyTarget=attackFlyTarget(target,100,.7,true);}
            if(boss.attackAge==70){emissaryGrappleTrying=false;emissaryQuickTurn=false;chooseFlyTarget(target);}
        }else if(boss.attack==GRAPPLE_GRAB){
            if(boss.attackAge==60)singleFlyTarget(boss.position().add(com.yifei.nyctocosm.boss.serpent.SerpentMotion.localForward(boss.getYRot(),50).scale(150)));
            if(boss.attackAge==160){releaseGrapple(server);chooseFlyTarget(target);}
        }
        int comboFrame=switch(id){case "phase3.attack.swordswing"->10;case "phase3.attack.cross"->12;case "phase3.attack.frontkick","phase3.attack.ground_slam"->14;case "phase3.attack.swordspin"->17;case "phase3.attack.flipfar"->24;default->-1;};
        if(boss.attackAge==comboFrame)combo(server,target);
    }
    private void combo(ServerLevel server,LivingEntity target){
        if(boss.random().nextFloat()<.7f){
            Vec3 toward=target.position().subtract(boss.position());boss.setYRot((float)(Math.toDegrees(Math.atan2(toward.z,toward.x))-90));boss.setXRot(0);boss.yBodyRot=boss.getYRot();
            boss.beginAttack(server,target,selectEmissaryAttack(3,boss.distanceTo(target),boss.random().nextInt(100),false));
        }
    }
    public Vec3 seekerMissileOrigin(int frame){
        String locator;
        Vec3 offset;
        switch(frame){
            case 2 -> {locator="rightmiddlewingtip2";offset=new Vec3(1,-1,0);}
            case 4 -> {locator="rightmiddlewingtip2";offset=new Vec3(1,1,0);}
            case 6 -> {locator="rightupperwingtip2";offset=new Vec3(-2,-3,0);}
            case 8 -> {return EmissaryPose.localPoint(boss.position(),boss.getYRot(),boss.getXRot(),new Vec3(0,8.5,0));}
            case 10 -> {locator="leftupperwingtip2";offset=new Vec3(2,-3,0);}
            case 12 -> {locator="leftmiddlewingtip2";offset=new Vec3(-1,1,0);}
            case 14 -> {locator="leftmiddlewingtip2";offset=new Vec3(-1,-1,0);}
            default -> throw new IllegalArgumentException("No source seeking missile node at frame "+frame);
        }
        var node=EmissaryDamagePose.sampleAt(boss,"phase3.attack.seeking_missiles",locator,frame);
        return EmissaryDamagePose.offsetFromLocator(boss,node,offset.x,offset.y,offset.z);
    }
    public void markPendingDeath(){emissaryPendingDeath=true;}
    public void updateParts(){
        if(boss.emissaryParts==null)return;
        int currentPhase=boss.phase();
        if(emissaryPartsPhase!=currentPhase){
            for(BossEntity.EmissaryPart part:boss.emissaryParts)part.refreshDimensions();
            emissaryPartsPhase=currentPhase;
        }
        if(currentPhase==1){
            var left=EmissaryDamagePose.sample(boss,"leftmiddlewingtip0");
            var right=EmissaryDamagePose.sample(boss,"rightmiddlewingtip0");
            boss.emissaryParts[0].setPos(EmissaryDamagePose.offsetFromLocator(boss,left,-3,2,0));
            boss.emissaryParts[1].setPos(EmissaryDamagePose.offsetFromLocator(boss,right,3,2,0));
            boss.emissaryParts[2].setPos(EmissaryDamagePose.offsetFromLocator(boss,left,-6,4,0));
            boss.emissaryParts[3].setPos(EmissaryDamagePose.offsetFromLocator(boss,right,6,4,0));
        }else{
            var center=EmissaryDamagePose.worldPosition(boss,EmissaryDamagePose.sample(boss,"center")).add(0,-1.3,0);
            boss.emissaryParts[0].setPos(center);
            for(int i=1;i<boss.emissaryParts.length;i++)boss.emissaryParts[i].setPos(boss.getX(),boss.getY()-128,boss.getZ());
        }
    }
    public void preDie(ServerLevel server){releaseGrapple(server);emissaryCorkscrew=false;emissaryGrappleTrying=false;boss.setDeltaMovement(Vec3.ZERO);}
    public void onRemove(ServerLevel server){
        List<Entity> snapshot=new ArrayList<>();server.getAllEntities().forEach(snapshot::add);
        for(Entity entity:snapshot)if(entity instanceof EmissarySeekingMissile missile&&boss.getUUID().equals(missile.ownerId()))missile.discard();
    }
    public void deathEffects(ServerLevel server){
        BossEffectTimeline.serverFrame(server,boss,"die",boss.deathTime);
        if(boss.deathTime==100){
            Vec3 at=EmissaryDamagePose.worldPosition(boss,EmissaryDamagePose.sampleAt(boss,"die","center",100));
            Network.sourceVisual(server,"repair_emissary_final",at,boss.getYRot(),boss.getXRot());
            CommonEvents.resonanceSound(server,at,"emissary.death.finaldeath",50,300);
        }
    }
    public void saveExtra(CompoundTag tag){
        tag.put("EmissaryAppearance",boss.syncedData().get(BossEntity.APPEARANCE).copy());
        tag.putBoolean("EmissaryFlying",emissaryFlying);if(emissaryPoint!=null)BossEntity.putVec(tag,"EmissaryPoint",emissaryPoint);
        tag.putBoolean("EmissaryHoverEstablished",emissaryHoverEstablished);
        if(emissaryEntranceLanding!=null)BossEntity.putVec(tag,"EmissaryEntranceLanding",emissaryEntranceLanding);
        if(emissaryFlyTarget!=null)BossEntity.putVec(tag,"EmissaryFlyTarget",emissaryFlyTarget);
        var otherTargets=new net.minecraft.nbt.ListTag();for(var point:emissaryOtherFlyTargets){var row=new CompoundTag();BossEntity.putVec(row,"Point",point);otherTargets.add(row);}tag.put("EmissaryOtherFlyTargets",otherTargets);
        if(emissaryFlightAnchor!=null)BossEntity.putVec(tag,"EmissaryFlightAnchor",emissaryFlightAnchor);
        if(emissaryTargetId!=null)tag.putUUID("EmissaryTarget",emissaryTargetId);if(emissarySeatId!=null)tag.putUUID("EmissarySeat",emissarySeatId);
        tag.putInt("EmissaryFlySpeed",emissaryFlySpeed);tag.putInt("EmissaryFlyTimer",emissaryFlyTimer);tag.putInt("EmissaryPoise",emissaryPoise);
        tag.putBoolean("EmissaryGrappleTrying",emissaryGrappleTrying);tag.putBoolean("EmissaryCorkscrew",emissaryCorkscrew);tag.putBoolean("EmissaryPendingDeath",emissaryPendingDeath);
        tag.putBoolean("EmissaryQuickTurn",emissaryQuickTurn);
        tag.putInt("EmissaryCorkscrewScore",emissaryCorkscrewScore);
    }
    public void loadExtra(CompoundTag tag){
        emissaryFlying=tag.getBoolean("EmissaryFlying");if(tag.contains("EmissaryPointX"))emissaryPoint=BossEntity.readVec(tag,"EmissaryPoint");
        var range=boss.getAttribute(Attributes.FOLLOW_RANGE);
        if(range!=null&&range.getBaseValue()==80)range.setBaseValue(300); // Migrate the old native default stored in entity NBT.
        emissaryHoverEstablished=tag.getBoolean("EmissaryHoverEstablished");
        emissaryEntranceLanding=tag.contains("EmissaryEntranceLandingX")?BossEntity.readVec(tag,"EmissaryEntranceLanding"):null;
        if(tag.contains("EmissaryFlyTargetX"))emissaryFlyTarget=BossEntity.readVec(tag,"EmissaryFlyTarget");
        emissaryOtherFlyTargets.clear();for(var row:tag.getList("EmissaryOtherFlyTargets",net.minecraft.nbt.Tag.TAG_COMPOUND))emissaryOtherFlyTargets.add(BossEntity.readVec((CompoundTag)row,"Point"));
        emissaryFlightAnchor=tag.contains("EmissaryFlightAnchorX")?BossEntity.readVec(tag,"EmissaryFlightAnchor"):null;
        emissaryTargetId=tag.hasUUID("EmissaryTarget")?tag.getUUID("EmissaryTarget"):null;emissarySeatId=tag.hasUUID("EmissarySeat")?tag.getUUID("EmissarySeat"):null;
        emissaryFlySpeed=tag.contains("EmissaryFlySpeed")?Math.max(1,Math.min(10,tag.getInt("EmissaryFlySpeed"))):5;emissaryFlyTimer=tag.getInt("EmissaryFlyTimer");emissaryPoise=tag.getInt("EmissaryPoise");
        emissaryGrappleTrying=tag.getBoolean("EmissaryGrappleTrying");emissaryCorkscrew=tag.getBoolean("EmissaryCorkscrew");emissaryPendingDeath=tag.getBoolean("EmissaryPendingDeath");
        emissaryQuickTurn=tag.contains("EmissaryQuickTurn")?tag.getBoolean("EmissaryQuickTurn"):emissaryGrappleTrying||emissaryCorkscrew;
        emissaryCorkscrewScore=Math.floorMod(tag.getInt("EmissaryCorkscrewScore"),36);
        String clip=tag.getString("Animation");BossEntity.Attack saved=emissaryAttack(clip);
        if(tag.getInt("Remaining")>0&&(saved!=null||clip.matches("phase[1-3]_transition")||clip.startsWith("phase3.parry")||clip.equals("spawn")||clip.equals("arrival"))){
            boss.attack=saved;boss.attackAge=Math.max(0,tag.getInt("AttackAge"));boss.animate(clip,tag.getInt("Remaining"));boss.syncedData().set(BossEntity.START,boss.level().getGameTime()-tag.getInt("AnimationAge"));
        }else boss.animate(boss.idle(),0);
        boss.syncedData().set(BossEntity.APPEARANCE,tag.contains("EmissaryAppearance")&&!tag.getCompound("EmissaryAppearance").isEmpty()?tag.getCompound("EmissaryAppearance").copy():EmissaryAppearance.initial(boss.phase()));
        updateGravity();
        if((boss.emissaryApproaching()||boss.animation().equals("spawn"))&&boss.level() instanceof ServerLevel server)keepEntranceTicking(server);
    }
    public void startAttackForTest(LivingEntity target,String clip){
        boss.setTarget(target);
        boss.beginAttack((ServerLevel)boss.level(),target,Objects.requireNonNull(emissaryAttack(clip)));
    }
}
