package com.yifei.nyctocosm.boss;

import com.yifei.nyctocosm.CommonEvents;
import com.yifei.nyctocosm.boss.serpent.SerpentMotion;
import com.yifei.nyctocosm.combat.Combat;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

/** 普通怪（kind==3 噩梦行者 / kind==4 幻影）行为控制器，由 BossEntity 骨架按 kind>=3 分发。 */
public final class OrdinaryController {
    public final Boss boss;
    private int ordinaryTimer,ordinaryState,farTicks;
    private int floatTimer=40;
    private boolean apparitionAlerted,floatUp;

    public OrdinaryController(Boss boss){this.boss=boss;}
    public void tick(ServerLevel server){
        Player nearest=server.getNearestPlayer(boss,boss.kind==3?200:200);
        if(nearest==null){if(++farTicks>=(boss.kind==3?500:200))boss.discard();}
        else farTicks=0;
        if(boss.kind==4){tickApparition(server,nearest);return;}
        Player target=server.getNearestPlayer(boss,30);
        if(target!=null&&(target.isCreative()||target.isSpectator()))target=null;
        if(boss.remaining>0){
            boss.remaining--;boss.getNavigation().stop();
            if(boss.remaining==41){
                Vec3 center=boss.position().add(boss.getLookAngle().multiply(3,0,3));
                Combat.area(server,boss,center,10,15,Combat.Element.NIGHTMARE,1);
                server.sendParticles(ParticleTypes.EXPLOSION_EMITTER,center.x,center.y,center.z,1,0,0,0,0);
                server.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK,Blocks.DIRT.defaultBlockState()),center.x,center.y,center.z,1000,3,.5,3,.2);
                CommonEvents.masterSound(server,center,net.minecraft.sounds.SoundEvents.GENERIC_EXPLODE,3,30);
            }
            if(boss.remaining==0){boss.animate("walk",0);ordinaryState=2;}
            return;
        }
        if(target!=null){
            ordinaryState=2;boss.getLookControl().setLookAt(target,90,90);boss.lookAt(net.minecraft.commands.arguments.EntityAnchorArgument.Anchor.FEET,target.position());
            boss.getNavigation().moveTo(target,.72);
            if(boss.distanceTo(target)<=7){boss.getNavigation().stop();boss.animate("slam",91);boss.remaining=91;ordinaryState=3;}
            else if(!boss.animation().equals("walk"))boss.animate("walk",0);
            return;
        }
        boss.getNavigation().stop();
        if(ordinaryTimer--<=0){
            if(ordinaryState==1){ordinaryState=0;ordinaryTimer=100+boss.random().nextInt(100);boss.animate("idle",0);}
            else{ordinaryState=1;ordinaryTimer=100+boss.random().nextInt(900);boss.setYRot(boss.random().nextFloat()*360-180);boss.animate("walk",0);}
        }
        if(ordinaryState==1){
            Vec3 f=SerpentMotion.forward(boss.getYRot()).scale(.2);
            // 原包 walk.mcfunction 崖边检测：前方 0.2 格脚下 1..5 格全空气则停下转 idle
            BlockPos base=BlockPos.containing(boss.getX()+f.x,boss.getY(),boss.getZ()+f.z);
            boolean cliff=true;
            for(int i=1;i<=5&&cliff;i++)if(!server.getBlockState(base.below(i)).isAir())cliff=false;
            if(cliff){ordinaryState=0;ordinaryTimer=100+boss.random().nextInt(100);boss.animate("idle",0);}
            else{boss.setDeltaMovement(f.x,boss.getDeltaMovement().y,f.z);boss.hurtMarked=true;}
        }
    }
    private void tickApparition(ServerLevel server,Player nearest){
        boss.setDeltaMovement(Vec3.ZERO);boss.hurtMarked=true;
        // 原包环境粒子：红尘 25 个 5x5x5 normal + 头顶黑尘 1 个 force（block/17）
        server.sendParticles(new DustParticleOptions(new Vector3f(.561f,0,0),.5f),boss.getX(),boss.getY(),boss.getZ(),25,5,5,5,0);
        for(var p:server.players())server.sendParticles(p,new DustParticleOptions(new Vector3f(0,0,0),1),true,boss.getX(),boss.getY()+1,boss.getZ(),1,.6,.6,.6,0);
        if(--floatTimer<=0){floatTimer=20+boss.random().nextInt(60);floatUp=!floatUp;}
        boss.setPos(boss.getX(),boss.getY()+(floatUp?.005:-.005),boss.getZ());
        if(boss.tickCount%8==0)CommonEvents.soundMaster(server,boss.position(),"nightmare_apparition.whisper",1,20);
        Player target=server.getNearestPlayer(boss,20);if(target!=null)boss.lookAt(net.minecraft.commands.arguments.EntityAnchorArgument.Anchor.FEET,target.position());
        if(!server.getBlockState(boss.blockPosition()).isAir())boss.setPos(boss.getX(),boss.getY()+.05,boss.getZ());
        if(server.getMaxLocalRawBrightness(boss.blockPosition())>=5){Vec3 back=boss.getLookAngle().multiply(-1.5,0,-1.5);boss.setPos(boss.getX()+back.x,boss.getY(),boss.getZ()+back.z);}
        if(!apparitionAlerted){
            // 漂移目标=最近 survival 玩家 2..13 格（原包 block/20）
            Player drift=server.getNearestPlayer(boss.getX(),boss.getY(),boss.getZ(),13,p->p instanceof Player pl&&!pl.isCreative()&&!pl.isSpectator()&&pl.distanceTo(boss)>=2);
            if(drift!=null){Vec3 toward=drift.position().subtract(boss.position());if(toward.lengthSqr()>.001){toward=toward.normalize().scale(.05);boss.setPos(boss.getX()+toward.x,boss.getY(),boss.getZ()+toward.z);}}
            retreat(server);
            if(target!=null&&!target.isCreative()&&!target.isSpectator()){
                double distance=boss.distanceTo(target);
                boolean moving=target.getDeltaMovement().multiply(1,0,1).lengthSqr()>.0025;
                // 原包 block/21/22：疾跑即警觉；未潜行且移动也警觉
                if(distance<=13&&(target.isSprinting()||(!target.isShiftKeyDown()&&moving))){
                    apparitionAlerted=true;ordinaryTimer=200+boss.random().nextInt(50);CommonEvents.soundMaster(server,boss.position(),"nightmare_apparition.attack",5,50);
                }
            }
            return;
        }
        if(--ordinaryTimer<=0){dissipate(server);return;}
        target=server.getNearestPlayer(boss,200);if(target==null)return;
        Vec3 toward=target.position().subtract(boss.position());if(toward.lengthSqr()>.001){toward=toward.normalize().scale(.2);boss.setPos(boss.getX()+toward.x,boss.getY()+toward.y,boss.getZ()+toward.z);}
        // 原包 block/23：追击期双眼红尘（眼部 +1.625y，局部 ^±0.1 ^ ^0.5 各 10 个）
        Vec3 eye=boss.position().add(0,1.625,0);
        for(double side:new double[]{-.1,.1}){
            Vec3 off=new Vec3(side,0,.5).yRot(-boss.getYRot()*((float)Math.PI/180));
            server.sendParticles(new DustParticleOptions(new Vector3f(.561f,0,0),.5f),eye.x+off.x,eye.y+off.y,eye.z+off.z,10,0,0,0,0);
        }
        if(boss.distanceTo(target)<=1){target.addEffect(new MobEffectInstance(MobEffects.WITHER,200,0));target.hurt(server.damageSources().magic(),6);dissipate(server);}
    }
    /** 原包 block/20 防重叠分支：同类幻影 ≤2 格或玩家 ≤2 格时各后退 0.05。 */
    private void retreat(ServerLevel server){
        var twin=server.getEntitiesOfClass(Boss.class,boss.getBoundingBox().inflate(2),e->e!=boss&&e.kind==4&&e.distanceToSqr(boss)<=4);
        Player close=server.getNearestPlayer(boss,2);
        if(!twin.isEmpty())stepBack(twin.get(0).position());
        if(close!=null)stepBack(close.position());
    }
    private void stepBack(Vec3 from){
        Vec3 away=boss.position().subtract(from);
        if(away.lengthSqr()>.000001){away=away.normalize().scale(.05);boss.setPos(boss.getX()+away.x,boss.getY(),boss.getZ()+away.z);}
    }
    private void dissipate(ServerLevel server){server.sendParticles(ParticleTypes.SQUID_INK,boss.getX(),boss.getY()+1,boss.getZ(),200,1,1,1,.1);CommonEvents.soundMaster(server,boss.position(),"nightmare_apparition.dissipate",5,20);boss.discard();}
    public void saveExtra(CompoundTag tag){
        tag.putInt("OrdinaryTimer",ordinaryTimer);tag.putInt("OrdinaryState",ordinaryState);tag.putInt("FarTicks",farTicks);tag.putInt("FloatTimer",floatTimer);tag.putBoolean("ApparitionAlerted",apparitionAlerted);tag.putBoolean("FloatUp",floatUp);
    }
    public void loadExtra(CompoundTag tag){
        ordinaryTimer=tag.getInt("OrdinaryTimer");ordinaryState=tag.getInt("OrdinaryState");farTicks=tag.getInt("FarTicks");floatTimer=tag.getInt("FloatTimer");apparitionAlerted=tag.getBoolean("ApparitionAlerted");floatUp=tag.getBoolean("FloatUp");
        int slamRemaining=tag.getInt("Remaining");
        if(boss.kind==3&&tag.getString("Animation").equals("slam")&&slamRemaining>0){
            ordinaryState=3;boss.animate("slam",Math.min(91,slamRemaining));boss.syncedData().set(BossEntity.START,boss.level().getGameTime()-Math.max(0,tag.getInt("AnimationAge")));
        }else if(boss.kind==3&&ordinaryState==3)ordinaryState=2;
    }
}
