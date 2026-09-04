package com.yifei.nyctocosm.spell.data;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 玩家法术数据 Capability Provider（挂载到 Player 实体）。
 * <p>
 * 用法：
 * <pre>
 *   player.getCapability(SpellPlayerProvider.CAPABILITY).ifPresent(data -&gt; {...});
 * </pre>
 * Provider 同时实现 {@link ICapabilitySerializable} 自动参与 Forge 存档读写 + 玩家死亡复制流程。
 *
 * <b>线程安全</b>：底层 {@link SpellPlayerData} 本身设计良好，Capability 只在主线程被调用，无并发问题。
 */
public class SpellPlayerProvider implements ICapabilitySerializable<CompoundTag> {

    /** Capability Token（在 P2-5 通过 {@link net.minecraftforge.event.AttachCapabilitiesEvent} 注册）。
     *  由 Forge 在 {@code @SubscribeEvent RegisterCapabilitiesEvent} 阶段生成具体实例。
     *  这里用 LazyOptional 封装默认实例，避免 Capability 未注册时 NPE。 */
    public static final net.minecraftforge.common.capabilities.CapabilityToken<ISpellPlayerData> TOKEN =
            new net.minecraftforge.common.capabilities.CapabilityToken<>() {};

    /** 数据实例（每 Provider = 每玩家一个） */
    private final SpellPlayerData data = new SpellPlayerData();
    private final LazyOptional<ISpellPlayerData> holder = LazyOptional.of(() -> data);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == com.yifei.nyctocosm.spell.SpellCapabilities.SPELL_PLAYER
                ? holder.cast()
                : LazyOptional.empty();
    }

    /** Forge invalidate 时释放（不保留强引用避免内存泄漏） */
    public void invalidate() { holder.invalidate(); }

    @Override
    public CompoundTag serializeNBT() { return data.serializeNBT(); }

    @Override
    public void deserializeNBT(CompoundTag nbt) { data.deserializeNBT(nbt); }
}
