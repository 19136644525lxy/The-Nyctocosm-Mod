package com.yifei.nyctocosm.item;

import com.yifei.nyctocosm.network.Network;
import net.minecraft.core.particles.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import java.util.ArrayList;
import java.util.List;

/** Native particle emitters using the source weapons' colors and sampling geometry. */
public final class WeaponEffects {
    private static final DustColorTransitionOptions FISSURE=new DustColorTransitionOptions(new Vector3f(.741f,0,1),new Vector3f(0,.569f,1),1.5f);
    private static final DustParticleOptions BLACK=new DustParticleOptions(new Vector3f(0,0,0),2);
    private static final DustParticleOptions FISSURE_BLACK=new DustParticleOptions(new Vector3f(0,0,0),.6f);
    private static final DustParticleOptions FISSURE_PURPLE=new DustParticleOptions(new Vector3f(.741f,0,1),1);
    public record ParticleCall(ParticleOptions particle,Vec3 position,int count,double spread,double speed){}
    public static List<ParticleCall> offhandPlan(int kind,Vec3 eye,Vec3 end,Vec3 forward){
        Vec3 axis=forward.normalize();
        if(axis.lengthSqr()<1e-8)return List.of();
        List<ParticleCall> calls=new ArrayList<>();
        if(kind==2){
            Vec3 ray=end.subtract(eye);double length=ray.length(),start=Math.min(2,length);
            Vec3 reference=Math.abs(axis.y)<.99?new Vec3(0,1,0):new Vec3(1,0,0);
            Vec3 right=axis.cross(reference).normalize(),up=right.cross(axis).normalize();
            calls.add(new ParticleCall(ParticleTypes.FLASH,eye.add(axis.scale(.5)),1,0,0));
            for(double distance=start;distance<=length+.001;distance+=1){
                Vec3 center=eye.add(axis.scale(distance));
                ring(calls,FISSURE_BLACK,center,right,up,.2,18);
                ring(calls,FISSURE_PURPLE,center,right,up,.4,20);
            }
        }else if(kind==3){
            Vec3 muzzle=eye.add(axis);
            calls.add(new ParticleCall(ParticleTypes.FLASH,muzzle,1,0,0));
            calls.add(new ParticleCall(ParticleTypes.EXPLOSION,muzzle,1,0,0));
            calls.add(new ParticleCall(ParticleTypes.LAVA,muzzle,10,0,.2));
            calls.add(new ParticleCall(ParticleTypes.FALLING_LAVA,muzzle,20,.6,0));
            // Six rays are emitted immediately; the source effect adds three more
            // over its following three subticks (see dissonanceAfterburstPlan).
            for(int rayIndex=0;rayIndex<6;rayIndex++)dissonanceRay(calls,muzzle,rayIndex);
        }
        return List.copyOf(calls);
    }
    public static List<ParticleCall> dissonanceAfterburstPlan(Vec3 eye,Vec3 forward,int rayIndex){
        Vec3 axis=forward.normalize();if(axis.lengthSqr()<1e-8)return List.of();
        List<ParticleCall> calls=new ArrayList<>();dissonanceRay(calls,eye.add(axis),6+Math.floorMod(rayIndex,3));return List.copyOf(calls);
    }
    private static void dissonanceRay(List<ParticleCall> calls,Vec3 muzzle,int rayIndex){
        double golden=Math.PI*(3-Math.sqrt(5)),y=1-(rayIndex+.5)*2/9,radial=Math.sqrt(Math.max(0,1-y*y)),angle=rayIndex*golden;
        Vec3 direction=new Vec3(Math.cos(angle)*radial,y,Math.sin(angle)*radial);
        for(int n=0;n<=5;n++)calls.add(new ParticleCall(ParticleTypes.FLAME,muzzle.add(direction.scale(n*.2)),1,0,.000003));
        for(int n=0;n<=2;n++)calls.add(new ParticleCall(ParticleTypes.LARGE_SMOKE,muzzle.subtract(direction.scale(n*.2)),1,0,.0000005));
    }
    private static void ring(List<ParticleCall> calls,ParticleOptions particle,Vec3 center,Vec3 right,Vec3 up,double radius,int points){
        for(int n=0;n<points;n++){
            double angle=n*Math.PI*2/points;
            calls.add(new ParticleCall(particle,center.add(right.scale(Math.cos(angle)*radius)).add(up.scale(Math.sin(angle)*radius)),1,0,0));
        }
    }
    /**
     * 副手武器技粒子。原包对这些效果用的都是 {@code particle ... force}（服务端直发、全客户端可见），
     * 因此这里同样走服务端强制发送，不再依赖自定义网络包——包只承载客户端才能算出的精细细节。
     */
    public static void offhand(ServerLevel level,LivingEntity owner,int kind,Vec3 end){
        Vec3 eye=owner.getEyePosition(),forward=owner.getLookAngle();
        Vec3 endpoint=end==null?eye.add(forward.scale(40)):end;
        if(kind==2)laser(level,eye,endpoint,forward);
        else if(kind==3)dissonanceBurst(level,eye,forward);
        else Network.weaponEffect(owner,kind,eye,endpoint,forward);
    }
    /** 原包 items/nullfissure/cast：眼位前方 0.5 格 flash + 沿射线每格的黑色内环/紫色外环。 */
    private static void laser(ServerLevel level,Vec3 eye,Vec3 end,Vec3 forward){
        Vec3 axis=forward.normalize();
        if(axis.lengthSqr()<1e-8)return;
        force(level,ParticleTypes.FLASH,eye.add(axis.scale(.5)),1,0,0);
        double length=end.subtract(eye).length();
        for(double distance=Math.min(2,length);distance<=length+.001;distance+=1){
            Vec3 center=eye.add(axis.scale(distance));
            // 环状截面用同半径的软采样近似：逐点发包会产生上千个包
            force(level,FISSURE_BLACK,center,8,.18,0);
            force(level,FISSURE_PURPLE,center,10,.34,0);
        }
    }
    /** 原包 items/semblanceofdoom/cast：枪口爆发 + 九条黄金角射线（延迟的三条同样在此一次性给出）。 */
    private static void dissonanceBurst(ServerLevel level,Vec3 eye,Vec3 forward){
        Vec3 axis=forward.normalize();
        if(axis.lengthSqr()<1e-8)return;
        Vec3 muzzle=eye.add(axis);
        force(level,ParticleTypes.FLASH,muzzle,1,0,0);
        force(level,ParticleTypes.EXPLOSION,muzzle,1,0,0);
        force(level,ParticleTypes.LAVA,muzzle,10,.2,.2);
        force(level,ParticleTypes.FALLING_LAVA,muzzle,20,.6,0);
        for(int rayIndex=0;rayIndex<9;rayIndex++){
            double golden=Math.PI*(3-Math.sqrt(5)),y=1-(rayIndex+.5)*2/9,radial=Math.sqrt(Math.max(0,1-y*y)),angle=rayIndex*golden;
            Vec3 direction=new Vec3(Math.cos(angle)*radial,y,Math.sin(angle)*radial);
            for(int n=0;n<=5;n++)force(level,ParticleTypes.FLAME,muzzle.add(direction.scale(n*.2)),1,0,.000003);
            for(int n=0;n<=2;n++)force(level,ParticleTypes.LARGE_SMOKE,muzzle.subtract(direction.scale(n*.2)),1,0,.0000005);
        }
    }
    /**
     * 原包 {@code particle ... force}：强制发送。
     * 客户端 LevelRenderer.addParticleInternal 对非 force 粒子有两条丢弃规则——距相机超过 32 格、
     * 或玩家粒子设置低于"全部"时直接丢弃，所以武器技必须走 force。
     */
    private static void force(ServerLevel level,ParticleOptions particle,Vec3 at,int count,double spread,double speed){
        force(level,particle,at,count,spread,spread,spread,speed);
    }
    private static void force(ServerLevel level,ParticleOptions particle,Vec3 at,int count,double dx,double dy,double dz,double speed){
        var packet=new net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket(particle,true,
            at.x,at.y,at.z,(float)dx,(float)dy,(float)dz,(float)speed,count);
        for(var player:level.players())
            if(player.blockPosition().closerToCenterThan(at,512))player.connection.send(packet);
    }
    /** 供武器代码直接使用的强制发送入口（原包 flash 等单点特效）。 */
    public static void forcedFlash(ServerLevel level,Vec3 at){force(level,ParticleTypes.FLASH,at,1,0,0);}
    public static void forcedParticle(ServerLevel level,ParticleOptions particle,Vec3 at,int count,double spread,double speed){
        force(level,particle,at,count,spread,speed);
    }
    public static void forceBox(ServerLevel level,ParticleOptions particle,Vec3 at,int count,double dx,double dy,double dz,double speed){
        force(level,particle,at,count,dx,dy,dz,speed);
    }
    public static void clientOffhand(Level level,int kind,Vec3 eye,Vec3 end,Vec3 forward){
        if(level==null||!level.isClientSide)return;
        emit(level,offhandPlan(kind,eye,end,forward));
    }
    public static void clientDissonanceAfterburst(Level level,Vec3 eye,Vec3 forward,int rayIndex){
        if(level!=null&&level.isClientSide)emit(level,dissonanceAfterburstPlan(eye,forward,rayIndex));
    }
    private static void emit(Level level,List<ParticleCall> calls){
        for(ParticleCall call:calls){
            Vec3 p=call.position;
            for(int n=0;n<call.count;n++)level.addParticle(call.particle,
                p.x+(level.random.nextDouble()*2-1)*call.spread,p.y+(level.random.nextDouble()*2-1)*call.spread,p.z+(level.random.nextDouble()*2-1)*call.spread,
                level.random.nextGaussian()*call.speed,level.random.nextGaussian()*call.speed,level.random.nextGaussian()*call.speed);
        }
    }
    public static void sweep(ServerLevel level,LivingEntity owner,int kind){
        Vec3 forward=owner.getLookAngle();
        // 正上/正下时水平基向量退化，退化为固定朝向，避免整圈采样塌缩到一条线上
        Vec3 flat=new Vec3(forward.z,0,-forward.x);
        Vec3 left=flat.lengthSqr()<1e-6?new Vec3(1,0,0):flat.normalize();
        Vec3 up=forward.cross(left).normalize();
        Vec3 origin=owner.getEyePosition();
        if(kind==2){
            for(int i=0;i<=24;i++){
                double offset=-.4+i/30.0;
                Vec3 direction=forward.scale(.2).add((owner.onGround()?left:up).scale(offset)).normalize();
                Vec3 p=origin.add(direction.scale(3));
                force(level,FISSURE,p,1,0,0);
                force(level,ParticleTypes.ENCHANTED_HIT,p,1,0,0);
                force(level,ParticleTypes.SWEEP_ATTACK,p,1,.1,0);
            }
        }else if(kind==3){
            origin=owner.position().add(0,owner.onGround()?1.5:.9,0);
            for(int i=0;i<72;i++){
                double angle=i*5; // Source generated coordinates use these radian samples.
                Vec3 p=origin.add((owner.onGround()?left:up).scale(Math.sin(angle)*2)).add(forward.scale(Math.cos(angle)*2));
                force(level,ParticleTypes.FLAME,p,1,0,.01);
                force(level,ParticleTypes.SMOKE,p,1,0,0);
                force(level,ParticleTypes.FIREWORK,p,1,0,0);
            }
        }
    }
    public static void dash(ServerLevel level,Vec3 start,Vec3 end){
        Vec3 path=end.subtract(start);int count=Math.max(1,(int)Math.ceil(path.length()));
        for(int i=0;i<=count;i++){
            Vec3 p=start.add(path.scale((double)i/count)).add(0,1,0);
            force(level,BLACK,p,50,.2,.4,.2,.1);
            force(level,ParticleTypes.PORTAL,p,10,.2,.4,.2,1);
        }
    }
    private WeaponEffects(){}
}
