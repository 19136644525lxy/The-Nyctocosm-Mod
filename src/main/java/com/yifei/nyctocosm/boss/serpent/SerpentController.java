package com.yifei.nyctocosm.boss.serpent;

import com.yifei.nyctocosm.CommonEvents;
import com.yifei.nyctocosm.NyctocosmMod;
import com.yifei.nyctocosm.boss.BossEntity;
import com.yifei.nyctocosm.boss.Boss;
import com.yifei.nyctocosm.combat.Combat;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

/** 巨蟒（Deathwalker Serpent）行为控制器：节肢运动、锁敌与激光/酸液招式。由 BossEntity 骨架按 kind==0 分发。 */
public final class SerpentController {
    public final Boss boss;
    private final SerpentBody serpentBody=new SerpentBody();
    private int serpentBodySpeed=2;
    private CompoundTag pendingSerpentBody;
    private final SerpentMotion serpentMotion=new SerpentMotion();
    private boolean serpentInSolid;

    public SerpentController(Boss boss){this.boss=boss;}
    public void initBody(){serpentBody.initialize(boss.position());}
    public void tickBody(){
        if(!boss.level().isClientSide){
            // Source block/6 teleports each hitbox before moving its owning segment.
            for(int i=0;i<boss.parts.length;i++)boss.parts[i].setPos(serpentBody.currentPosition((i+1)*2));
            serpentBody.tick(boss.position(),serpentBodySpeed,boss.getYRot(),boss.getXRot());boss.syncedData().set(BossEntity.BODY,serpentBody.snapshot());
        }else {
            if(pendingSerpentBody!=null){serpentBody.receive(pendingSerpentBody,true);pendingSerpentBody=null;}
            else serpentBody.finishInterpolation();
            for(int i=0;i<boss.parts.length;i++)boss.parts[i].setPos(renderSegment((i+1)*2,1));
        }
    }
    public void onBodySync(){pendingSerpentBody=boss.syncedData().get(BossEntity.BODY).copy();}
    public Vec3 renderSegment(int index,float partial){return serpentBody.position(index,partial,boss.getPosition(partial));}
    public Vec3 segment(int back){return renderSegment(Math.min(41,Math.max(0,back/4)),1);}
    public float segmentYaw(int index,float partial){return serpentBody.yaw(index,partial);}
    public float segmentPitch(int index,float partial){return serpentBody.pitch(index,partial);}
    public Vec3[] renderSegments(float partial){
        Vec3[] positions=new Vec3[42];for(int i=0;i<positions.length;i++)positions[i]=renderSegment(i,partial);return positions;
    }
    public void tick(ServerLevel server,LivingEntity target){
        SerpentAmbientEffects.emit(server,boss);
        boolean solid=!server.getBlockState(boss.blockPosition()).isAir();
        if(solid!=serpentInSolid){serpentInSolid=solid;CommonEvents.sound(server,boss.position(),"serpent.breach",5);}
        if(boss.random().nextInt(180)==0)CommonEvents.sound(server,segment(20+boss.random().nextInt(100)),"serpent.woosh",30);
        if(solid&&boss.random().nextInt(80)==0)CommonEvents.sound(server,boss.position(),"serpent.rumble",30,.5f);
        Vec3 before=boss.position();
        for(LivingEntity living:server.getEntitiesOfClass(LivingEntity.class,boss.getBoundingBox().inflate(2.5)))
            if(Combat.canHit(boss,living)&&boss.distanceTo(living)<=2.5&&living.invulnerableTime==0)
                Combat.hit(boss,living,boss.distanceTo(living)<=2?19.5f:10,Combat.Element.NIGHTMARE);
        UUID locked=serpentMotion.targetId();
        if(locked!=null){
            net.minecraft.world.entity.Entity found=server.getEntity(locked);
            if(found==null&&target!=null&&locked.equals(target.getUUID()))found=target;
            target=found instanceof LivingEntity living&&Combat.canHit(boss,living)?living:null;
        }
        var step=serpentMotion.tick(before,boss.getLookAngle(),target==null?null:target.position(),target==null?null:target.getUUID(),boss.random());
        if(step.startedAttack()){
            var players=server.players().stream().filter(player->Combat.canHit(boss,player)&&boss.distanceToSqr(player)<=200*200).toList();
            if(!players.isEmpty()){target=players.get(boss.random().nextInt(players.size()));serpentMotion.bindTarget(target.getUUID());}
            CommonEvents.sound(server,before,"serpent.scream",50);
            if(serpentMotion.attack()==2)CommonEvents.sound(server,before,"serpent.laser",50);
            if(serpentMotion.attack()==3&&target!=null)spawnSerpentBeams(server,target);
        }
        if(step.startedDive())CommonEvents.sound(server,before,"serpent.dive",40);
        if(step.laser()){
            Vec3 direction=serpentMotion.target().subtract(before).normalize();Vec3 end=before;
            var air=net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.BLOCK,ResourceLocation.fromNamespaceAndPath(NyctocosmMod.ID,"air"));
            for(int distance=1;distance<=150;distance++){
                end=before.add(direction.scale(distance));
                if(!server.getBlockState(BlockPos.containing(end)).is(air))break;
            }
            Combat.serpentLaser(server,boss,before,end,12);
            BossEntity.particleLine(server,before,end,new net.minecraft.core.particles.DustParticleOptions(new org.joml.Vector3f(1,0,0),1.5f),new net.minecraft.core.particles.DustParticleOptions(new org.joml.Vector3f(0,0,0),2),1);
        }
        serpentBodySpeed=step.speed();boss.setDeltaMovement(Vec3.ZERO);boss.setPos(step.position());boss.hurtMarked=true;
        boss.setYRot(SerpentMotion.yaw(step.direction()));
        boss.setXRot((float)-Math.toDegrees(Math.atan2(step.direction().y,step.direction().horizontalDistance())));boss.yBodyRot=boss.getYRot();
    }
    private void spawnSerpentBeams(ServerLevel server,LivingEntity target){
        for(int n=0;n<20;n++)SerpentBeamHazard.spawn(server,boss,target,140+boss.random().nextInt(140));
    }
    public int state(){return serpentMotion.state();}
    public UUID lastSelectedTarget(){return serpentMotion.lastSelectedTargetId();}
    public int attack(){return serpentMotion.attack();}
    public CompoundTag motionForTrace(){return serpentMotion.save();}
    public CompoundTag bodySnapshot(){return serpentBody.snapshot();}
    public void receiveSpawnData(CompoundTag tag){if(boss.kind==0&&tag!=null)serpentBody.receive(tag,false);}
    public void startAttackForTest(ServerLevel server,LivingEntity target,int type){
        boss.setTarget(target);serpentMotion.initialize(boss.position(),boss.getLookAngle(),boss.random());
        serpentMotion.begin(boss.position(),boss.getYRot(),boss.getXRot(),Math.max(1,Math.min(3,type)),target.getUUID());
        if(serpentMotion.attack()==3)spawnSerpentBeams(server,target);
    }
    public void startDiveForTest(LivingEntity target){
        boss.setTarget(target);Vec3 direction=target.getEyePosition().subtract(boss.position()).normalize();
        serpentMotion.initialize(boss.position(),direction,boss.random());serpentMotion.beginDive(boss.position(),direction,target.position(),target.getUUID());
    }
    public void discardArtifacts(ServerLevel server,UUID ownerId,java.util.List<net.minecraft.world.entity.Entity> snapshot){
        for(net.minecraft.world.entity.Entity entity:snapshot)if(entity instanceof SerpentBeamHazard beam&&beam.ownerId().filter(ownerId::equals).isPresent())entity.discard();
    }
    public void deathEffects(ServerLevel server){
        Vec3 at=segment(boss.random().nextInt(160));server.sendParticles(ParticleTypes.PORTAL,at.x,at.y,at.z,12,.8,.8,.8,.2);
        if(boss.deathTime==100){
            for(int i=0;i<40;i++){Vec3 p=segment(i*4);server.sendParticles(ParticleTypes.FLASH,p.x,p.y,p.z,1,0,0,0,0);}
            server.sendParticles(ParticleTypes.EXPLOSION_EMITTER,boss.getX(),boss.getY(),boss.getZ(),4,2,2,2,0);
            server.playSound(null,boss.getX(),boss.getY(),boss.getZ(),net.minecraft.sounds.SoundEvents.GENERIC_EXPLODE,net.minecraft.sounds.SoundSource.HOSTILE,50,.5f);
            CommonEvents.sound(server,boss.position(),"serpent.defeat",50,.5f);
        }
    }
    public void saveExtra(CompoundTag tag){
        tag.put("SerpentMotion",serpentMotion.save());if(serpentMotion.target()!=null)BossEntity.putVec(tag,"SerpentGoal",serpentMotion.target());
        tag.put("SerpentBody",serpentBody.save());
    }
    public void loadExtra(CompoundTag tag){
        serpentMotion.load(tag.getCompound("SerpentMotion"));
        serpentBody.load(tag.getCompound("SerpentBody"));
    }
}
