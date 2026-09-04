package com.yifei.nyctocosm.spell;

import com.yifei.nyctocosm.damage.NyctocosmDamageTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

import javax.annotation.Nullable;

/**
 * 6 元素枚举（位掩码，支持多元素组合）。
 * <p>
 * 与原数据包 s.type1 ~ s.type6 一一对应：
 * <ul>
 *   <li>type1 = DREAM（梦）</li>
 *   <li>type2 = RADIANCE（光辉）</li>
 *   <li>type3 = RESONANCE（共振）</li>
 *   <li>type4 = NIGHTMARE（梦魇）</li>
 *   <li>type5 = VOID（虚空）</li>
 *   <li>type6 = DISSONANCE（失谐）</li>
 * </ul>
 *
 * 使用位运算判断组合：
 * <pre>
 *   // 同时包含 RADIANCE 和 DISSONANCE（例如 Incandescence / Fulminance）
 *   if ((spell.getElementMask() &amp; (RADIANCE.mask | DISSONANCE.mask)) != 0) { ... }
 * </pre>
 */
public enum ElementType {
    /** 梦元素 type1 — Submergence 等 */
    DREAM(1, 0x01, "dream", NyctocosmDamageTypes.DREAM, 0x88AADDFF, 0x6FB5E4),
    /** 光辉元素 type2 — Luminescence/Resistance/Subsistence/Incandescence/Vacance/Fulminance 等 */
    RADIANCE(2, 0x02, "radiance", NyctocosmDamageTypes.RADIANCE, 0xFFFFFFB0, 0xF4E382),
    /** 共振元素 type3 — Resonance/Reflectance/Evanescence 等 */
    RESONANCE(3, 0x04, "resonance", NyctocosmDamageTypes.RESONANCE, 0xFF88DD88, 0x7FCF7F),
    /** 梦魇元素 type4 — Incurrence/Abhorrence 等 */
    NIGHTMARE(4, 0x08, "nightmare", NyctocosmDamageTypes.NIGHTMARE, 0xFFAA66AA, 0x9966AA),
    /** 虚空元素 type5 — Gravitance/Submergence/Adamance/Transmittance/Evanescence 等 */
    VOID(5, 0x10, "void", NyctocosmDamageTypes.VOID, 0xFF443366, 0x605090),
    /** 失谐元素 type6 — Forbiddance/Repellance/Incandescence/Fulminance 等 */
    DISSONANCE(6, 0x20, "dissonance", NyctocosmDamageTypes.DISSONANCE, 0xFFCC5555, 0xCC6666);

    /** 1-based 序号（对应原 s.typeN） */
    public final int index;
    /** 位掩码（用于组合判断） */
    public final int mask;
    /** 元素英文名（用于翻译 key、资源名等） */
    public final String name;
    /** 对应的 DamageType Key（可为 null，若法术不直接造成伤害） */
    @Nullable
    public final ResourceKey<DamageType> damageTypeKey;
    /** 粒子颜色（ARGB 整数） */
    public final int particleColor;
    /** HUD 高亮色（RGB 整数，无 A） */
    public final int hudColor;

    ElementType(int index, int mask, String name,
                @Nullable ResourceKey<DamageType> damageTypeKey,
                int particleColor, int hudColor) {
        this.index = index;
        this.mask = mask;
        this.name = name;
        this.damageTypeKey = damageTypeKey;
        this.particleColor = particleColor;
        this.hudColor = hudColor;
    }

    /** 由 1-based index 取元素（index 越界返回 null） */
    @Nullable
    public static ElementType byIndex(int index) {
        for (ElementType t : values()) if (t.index == index) return t;
        return null;
    }

    /** 由 mask 中每一位返回元素列表 */
    public static java.util.List<ElementType> fromMask(int mask) {
        java.util.List<ElementType> result = new java.util.ArrayList<>(6);
        for (ElementType t : values()) if ((mask & t.mask) != 0) result.add(t);
        return result;
    }

    /** 命令/Actionbar 用：每个元素对应一种聊天颜色（语义化显示） */
    public ChatFormatting getChatFormat() {
        return switch (this) {
            case DREAM -> ChatFormatting.AQUA;
            case RADIANCE -> ChatFormatting.YELLOW;
            case RESONANCE -> ChatFormatting.GREEN;
            case NIGHTMARE -> ChatFormatting.DARK_PURPLE;
            case VOID -> ChatFormatting.DARK_BLUE;
            case DISSONANCE -> ChatFormatting.RED;
        };
    }

    /** 返回元素英文名（与原数据包 artifact.type.xxx 键后缀一致），用于翻译 key 生成 */
    public String getSerializedName() {
        return this.name;
    }
}
