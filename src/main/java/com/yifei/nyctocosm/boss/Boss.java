package com.yifei.nyctocosm.boss;

import com.yifei.nyctocosm.boss.emissary.EmissaryController;
import com.yifei.nyctocosm.boss.serpent.SerpentController;
import com.yifei.nyctocosm.boss.sorcerer.NullpointStaff;
import com.yifei.nyctocosm.boss.sorcerer.SorcererCastMath;
import com.yifei.nyctocosm.boss.sorcerer.SorcererController;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * 门面实体：运行时类型仍是 Boss（注册、GameTest 反射与外部调用都指向它），
 * 同步数据/生命周期/伤害/存档在 {@link BossEntity} 骨架，三类 Boss 行为在对应控制器。
 * 此处仅保留原 public API 的薄委托与 static 转发，不含任何行为逻辑；
 * attributes/putVec/readVec/ring/particleLine 等 static 工具经继承沿用 BossEntity 实现。
 */
public class Boss extends BossEntity {
    public Boss(EntityType<? extends Boss> type,Level level,int kind) {super(type,level,kind);}

    // ---- 术士（kind==1）----
    public static BossEntity.Attack selectSorcererAttack(int phase,double distance,int roll){return SorcererController.selectSorcererAttack(phase,distance,roll);}
    public Optional<NullpointStaff> nullpoint(){return sorcerer.nullpoint();}
    public void onStaffBroken(NullpointStaff staff){sorcerer.onStaffBroken(staff);}
    public int meteorShotCount(){return sorcerer.meteorShotCount();}
    public int meteorShotsEmitted(){return sorcerer.meteorShotsEmitted();}
    public NullpointStaff spawnNullpointForTest(ServerLevel server){return sorcerer.spawnNullpointForTest(server);}
    public void startSorcererAttackForTest(ServerLevel server,LivingEntity target,String clip){sorcerer.startAttackForTest(server,target,clip);}

    // ---- 巨蟒（kind==0）----
    public int serpentState(){return serpent.state();}
    public UUID serpentLastSelectedTarget(){return serpent.lastSelectedTarget();}
    public int serpentAttack(){return serpent.attack();}
    public CompoundTag serpentMotionForTrace(){return serpent.motionForTrace();}
    public static double serpentSpeedForTier(int tier){return tier<=1?1:tier==2?2:3;}
    public Vec3 serpentRenderSegment(int index,float partial){return serpent.renderSegment(index,partial);}
    public Vec3[] serpentRenderSegments(float partial){return serpent.renderSegments(partial);}
    public float serpentSegmentYaw(int index,float partial){return serpent.segmentYaw(index,partial);}
    public float serpentSegmentPitch(int index,float partial){return serpent.segmentPitch(index,partial);}
    public void startSerpentAttackForTest(ServerLevel server,LivingEntity target,int type){serpent.startAttackForTest(server,target,type);}
    public void startSerpentDiveForTest(LivingEntity target){serpent.startDiveForTest(target);}

    // ---- 天使（kind==2）----
    public static BossEntity.Attack selectEmissaryAttack(int phase,double distance,int roll,boolean flying){return EmissaryController.selectEmissaryAttack(phase,distance,roll,flying);}
    public static List<Player> emissaryFireballTargets(Vec3 origin,Collection<? extends Player> candidates){return EmissaryController.emissaryFireballTargets(origin,candidates);}
    public static Vec3 emissaryFireballDirection(Vec3 origin,float fallbackYaw,float fallbackPitch,List<? extends Player> eligible,int selectedIndex){return EmissaryController.emissaryFireballDirection(origin,fallbackYaw,fallbackPitch,eligible,selectedIndex);}
    public static Vec3 emissaryFlightHeading(Vec3 forward,Vec3 toward,boolean quick){return EmissaryController.emissaryFlightHeading(forward,toward,quick);}
    public static SorcererCastMath.Rotation emissaryFlightRotation(float yaw,float pitch,Vec3 toward,boolean quick){return EmissaryController.emissaryFlightRotation(yaw,pitch,toward,quick);}
    public static boolean emissaryPhysicalHit(LivingEntity owner,LivingEntity target,float damage){return EmissaryController.emissaryPhysicalHit(owner,target,damage);}
    public static boolean emissaryPassable(Level level,BlockPos pos){
        return level.getBlockState(pos).is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.BLOCK,ResourceLocation.fromNamespaceAndPath(com.yifei.nyctocosm.NyctocosmMod.ID,"passthrough")));
    }
    public void startEmissaryAttackForTest(LivingEntity target,String clip){emissary.startAttackForTest(target,clip);}
    public void parryEmissary(ServerLevel server){emissary.parry(server);}
    public Vec3 emissaryGrapplePosition(){return emissary.grapplePosition();}
    public boolean holdsEmissaryGrapple(UUID seat){return emissary.holdsGrapple(seat);}
    public Vec3 emissarySeekerMissileOrigin(int frame){return emissary.seekerMissileOrigin(frame);}
}
