package com.yifei.nyctocosm.entity.spell;

import com.yifei.nyctocosm.entity.NyctocosmEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

/**
 * 法术平面实体 — 对应原数据包 ajspellplane rig（跟随玩家漂浮的施法环）
 * <p>
 * 三种动画状态（由 DataParameter spell_state 同步给客户端，避免服务端/客户端不一致）：
 * <ul>
 *   <li>0 = IDLE   ：空闲循环（默认）</li>
 *   <li>1 = CAST   ：施法中（播放一次，播放完自动回到 IDLE）</li>
 *   <li>2 = FIZZLE ：失败/取消（播放一次，播放完自动回到 IDLE）</li>
 * </ul>
 *
 * <b>为什么用 DataParameter 而不是字段？</b>
 * GeckoLib 动画控制器运行在客户端，服务端应通过 DataParameter（自动同步）或自定义网络包
 * 把状态变更推给客户端，仅靠服务端 setAndContinue 对客户端渲染无效。
 */
public class SpellPlaneEntity extends Entity implements GeoEntity {

    /** 动画状态枚举值 */
    public static final byte STATE_IDLE = 0;
    public static final byte STATE_CAST = 1;
    public static final byte STATE_FIZZLE = 2;

    /** 动画定义（与 .animation.json 中动画名匹配） */
    public static final RawAnimation ANIM_IDLE = RawAnimation.begin().thenLoop("spellplane.idle");
    public static final RawAnimation ANIM_CAST = RawAnimation.begin().thenPlay("spellplane.cast");
    public static final RawAnimation ANIM_FIZZLE = RawAnimation.begin().thenPlay("spellplane.fizzle");

    /** 动画状态 data 参数（服务端→客户端自动同步） */
    private static final EntityDataAccessor<Byte> DATA_STATE =
            SynchedEntityData.defineId(SpellPlaneEntity.class, EntityDataSerializers.BYTE);

    /** GeckoLib 动画实例缓存（每个实体唯一，避免重复构建动画控制器） */
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    /** 上次状态（检测到状态变更时触发动画控制器重启，重新 setAndContinue 新动画） */
    private byte lastState = -1;

    public SpellPlaneEntity(EntityType<? extends SpellPlaneEntity> type, Level level) {
        super(type, level);
    }

    // ============================
    // Entity 基类抽象方法
    // ============================

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_STATE, STATE_IDLE);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        this.entityData.set(DATA_STATE, tag.getByte("SpellPlaneState"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putByte("SpellPlaneState", this.entityData.get(DATA_STATE));
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    // ============================
    // 公共状态 API（服务端调用即可，客户端自动同步）
    // ============================

    /** 获取当前动画状态（客户端/服务端都可用） */
    public byte getAnimationState() {
        return this.entityData.get(DATA_STATE);
    }

    /** 设置为 CAST 状态（播放一次施法动画，结束返回 idle） */
    public void playCast() {
        this.entityData.set(DATA_STATE, STATE_CAST);
    }

    /** 设置为 FIZZLE 状态（播放一次失败动画，结束返回 idle） */
    public void playFizzle() {
        this.entityData.set(DATA_STATE, STATE_FIZZLE);
    }

    /** 重置为 IDLE（一般动画结束回调自动调用，外部无需手动） */
    public void resetIdle() {
        this.entityData.set(DATA_STATE, STATE_IDLE);
    }

    // ============================
    // GeoEntity 接口
    // ============================

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "SpellPlaneController", 0, this::animController));
    }

    /**
     * 动画控制器回调。状态机：
     * <ol>
     *   <li>DataParameter 变更 → 调用 controller.forceAnimationReset() 让 setAndContinue 从 0 重新播放</li>
     *   <li>CAST/FIZZLE 是 Play 模式，播放结束后 AnimationState 会返回 STOP（此时自动切回 IDLE）</li>
     * </ol>
     */
    private <E extends GeoAnimatable> PlayState animController(final AnimationState<E> state) {
        final byte current = getAnimationState();

        // 服务端：状态变化，先切回 IDLE 触发动画复位
        if (lastState != current) {
            state.getController().forceAnimationReset();
            lastState = current;
        }

        switch (current) {
            case STATE_CAST:
                PlayState psCast = state.setAndContinue(ANIM_CAST);
                if (psCast == PlayState.STOP) resetIdle();
                return psCast;
            case STATE_FIZZLE:
                PlayState psFizz = state.setAndContinue(ANIM_FIZZLE);
                if (psFizz == PlayState.STOP) resetIdle();
                return psFizz;
            case STATE_IDLE:
            default:
                return state.setAndContinue(ANIM_IDLE);
        }
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    /**
     * 兼容方法：GeckoLib 4.8 在部分 forge 版本中要求 override，保证编译稳定
     */
    @Override
    public double getTick(Object object) {
        return ((Entity) object).tickCount;
    }
}
