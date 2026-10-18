package com.yifei.nyctocosm.boss.emissary;

import com.yifei.nyctocosm.Content;
import com.yifei.nyctocosm.CommonEvents;
import com.yifei.nyctocosm.boss.emissary.EmissaryDamagePose;
import com.yifei.nyctocosm.boss.Boss;
import com.yifei.nyctocosm.combat.Combat;
import com.yifei.nyctocosm.network.Network;
import com.yifei.nyctocosm.spell.ReflectableAttack;
import com.yifei.nyctocosm.spell.Reflectance;
import com.yifei.nyctocosm.spell.SpellArea;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

/** One of the six independently aimed, animated wing rays in the Emissary's Wing Blast. */
public final class EmissaryWingBlastRay extends Entity {
    public static final int SOURCE_LIFETIME=43;
    static final float SOURCE_DAMAGE=40;
    private static final double SOURCE_RANGE=100;
    private static final double IMPACT_RADIUS=4;
    private static final String ANIMATION="phase2.attack.wing_blast";
    private static final String[] LOCATORS={"rightupperwingtip1","leftupperwingtip1","rightmiddlewingtip1","leftmiddlewingtip1","rightlowerwingtip1","leftlowerwingtip1"};
    private UUID owner;
    private UUID damageOwner;
    private int wing;
    private int age;
    private boolean active;
    private Vec3 lastTraceEnd;

    public EmissaryWingBlastRay(EntityType<? extends EmissaryWingBlastRay> type,Level level){
        super(type,level);noPhysics=true;setNoGravity(true);setDeltaMovement(Vec3.ZERO);
    }

    public static void start(ServerLevel level,Boss boss){
        var online=new java.util.LinkedHashSet<Player>(level.players());
        online.addAll(level.getEntitiesOfClass(Player.class,boss.getBoundingBox().inflate(300)));
        for(int index=0;index<LOCATORS.length;index++){
            var locator=EmissaryDamagePose.sampleAt(boss,ANIMATION,LOCATORS[index],12);
            Vec3 at=EmissaryDamagePose.worldPosition(boss,locator);
            var ray=Content.EMISSARY_WING_BLAST_RAY.get().create(level);
            ray.owner=boss.getUUID();ray.damageOwner=boss.getUUID();ray.wing=index;ray.setPos(at);
            var targets=online.stream().filter(player->!player.isSpectator()&&!player.getTags().contains("emissarydebug")
                &&player.distanceToSqr(at)<=300*300).toList();
            if(!targets.isEmpty())ray.aimAt(targets.get(boss.getRandom().nextInt(targets.size())).getEyePosition());
            else ray.setRot(boss.getYRot(),boss.getXRot());
            level.addFreshEntity(ray);
        }
    }

    public static void activate(ServerLevel level,Boss boss){
        for(Entity entity:level.getAllEntities())if(entity instanceof EmissaryWingBlastRay ray&&boss.getUUID().equals(ray.owner))ray.active=true;
    }

    static void removeOwned(ServerLevel level,Boss boss){
        for(Entity entity:level.getAllEntities())if(entity instanceof EmissaryWingBlastRay ray&&boss.getUUID().equals(ray.owner))ray.discard();
    }

    public UUID ownerId(){return owner;}
    public int wingForTest(){return wing;}
    public int ageForTest(){return age;}
    public boolean activeForTest(){return active;}
    Vec3 traceForTest(ServerLevel level){return trace(level,null,false);}
    void activateForTest(){active=true;}
    public void configureForTest(Boss boss,int sourceWing,boolean blasting){owner=boss.getUUID();damageOwner=boss.getUUID();wing=Math.floorMod(sourceWing,LOCATORS.length);active=blasting;}
    public UUID damageOwnerForTest(){return damageOwner;}
    public Vec3 lastTraceEndForTest(){return lastTraceEnd;}
    public void ageForTest(int value){age=Math.max(0,Math.min(SOURCE_LIFETIME,value));}

    private void aimAt(Vec3 target){
        Vec3 direction=target.subtract(position()).normalize();
        setYRot((float)(Math.toDegrees(Math.atan2(direction.z,direction.x))-90));
        setXRot((float)-Math.toDegrees(Math.asin(Math.max(-1,Math.min(1,direction.y)))));
    }

    @Override public void tick(){
        super.tick();
        if(!(level() instanceof ServerLevel server))return;
        Entity found=owner==null?null:server.getEntity(owner);
        Boss boss=found instanceof Boss emissary?emissary:null;
        age++;
        if(active)turnForWing();
        Vec3 end=trace(server,boss,active);
        lastTraceEnd=end;
        if(boss!=null)Network.emissaryBeam(boss,position(),end,false);
        else Network.emissaryBeam(this,position(),end,false);
        if(age>=SOURCE_LIFETIME)discard();
    }

    private void turnForWing(){
        float yaw=(wing%2==0?.3f:-.3f);
        float pitch=switch(wing){case 0,1->-.3f;case 4,5->.3f;default->0;};
        setYRot(getYRot()+yaw);setXRot(getXRot()+pitch);
    }

    private Vec3 trace(ServerLevel server,Boss boss,boolean damaging){
        Vec3 origin=position(),direction=getLookAngle(),point=origin;
        Set<UUID> reflected=new HashSet<>();
        // 原包 raycast/raycast_signal 每步 1 格撒红尘认尘，尺寸随 timer 增长（警示线→爆发带）。
        float dustSize=dustSizeForAge();
        for(int step=1;step<=SOURCE_RANGE;step++){
            point=origin.add(direction.scale(step));
            BlockPos block=BlockPos.containing(point);
            if(!server.hasChunkAt(block)||!Boss.emissaryPassable(server,block))break;
            server.sendParticles(new net.minecraft.core.particles.DustParticleOptions(new org.joml.Vector3f(1f,0f,0f),dustSize),
                point.x,point.y,point.z,1,0,0,0,0);
            if(damaging&&playerAt(server,point))break;
            if(damaging){
                var reflection=Reflectance.tryReflect(server,new ReflectableAttack(point,damageOwner==null?owner:damageOwner,SOURCE_DAMAGE,reflected));
                if(reflection!=null){
                    SpellArea reflector=reflection.reflector();
                    reflected.add(reflector.getUUID());damageOwner=reflection.owner().getUUID();direction=reflection.direction();
                    setYRot(reflector.getYRot());setXRot(reflector.getXRot());origin=point;continue;
                }
            }
        }
        if(damaging){
            Entity attacker=damageOwner==null?boss:server.getEntity(damageOwner);
            impact(server,attacker instanceof net.minecraft.world.entity.LivingEntity living?living:boss,point);
            CommonEvents.resonanceSound(server,point,"minecraft:entity.generic.explode",1,30);
            server.sendParticles(ParticleTypes.FLASH,point.x,point.y+1,point.z,1,0,0,0,0);
            server.sendParticles(ParticleTypes.EXPLOSION,point.x,point.y+1,point.z,2,.3,.3,.3,0);
            server.sendParticles(ParticleTypes.LAVA,point.x,point.y+1,point.z,2,.3,.3,.3,0);
        }
        return point;
    }

    /** 原包红尘尺寸：警示期 timer*0.0869 上限 22；爆发期 (RANGE+timer) 每 4 格一段，同斜率封顶 1.91。 */
    private float dustSizeForAge(){
        int timer=Math.max(0,age-12);
        return (float)Math.min(1.9130435,timer*0.08695652);
    }

    private static boolean playerAt(ServerLevel server,Vec3 point){
        AABB cell=new AABB(point.x-.5,point.y-.5,point.z-.5,point.x+.5,point.y+.5,point.z+.5);
        for(Player player:server.getEntitiesOfClass(Player.class,cell,candidate->!candidate.isSpectator()&&!candidate.getTags().contains("emissarydebug")))
            if(cell.contains(player.position()))return true;
        return false;
    }

    private static void impact(ServerLevel server,net.minecraft.world.entity.LivingEntity attacker,Vec3 center){
        AABB bounds=new AABB(center,center).inflate(IMPACT_RADIUS);
        for(Player player:server.getEntitiesOfClass(Player.class,bounds,candidate->!candidate.isSpectator()
            &&!candidate.getTags().contains("emissarydebug")&&candidate.hurtTime==0
            &&candidate.position().distanceToSqr(center)<=IMPACT_RADIUS*IMPACT_RADIUS)){
            if(attacker!=null)Combat.hit(attacker,player,SOURCE_DAMAGE,Combat.Element.RADIANCE);
        }
    }

    @Override protected void defineSynchedData(){}
    @Override protected void addAdditionalSaveData(CompoundTag tag){
        if(owner!=null)tag.putUUID("Owner",owner);if(damageOwner!=null)tag.putUUID("DamageOwner",damageOwner);tag.putInt("Wing",wing);tag.putInt("Age",age);tag.putBoolean("Active",active);
    }
    @Override protected void readAdditionalSaveData(CompoundTag tag){
        owner=tag.hasUUID("Owner")?tag.getUUID("Owner"):null;damageOwner=tag.hasUUID("DamageOwner")?tag.getUUID("DamageOwner"):owner;wing=Math.floorMod(tag.getInt("Wing"),LOCATORS.length);
        age=Math.max(0,Math.min(SOURCE_LIFETIME,tag.getInt("Age")));active=tag.getBoolean("Active");setDeltaMovement(Vec3.ZERO);
    }
    @Override public Packet<ClientGamePacketListener> getAddEntityPacket(){return NetworkHooks.getEntitySpawningPacket(this);}
}
