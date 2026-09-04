package com.yifei.nyctocosm.item.curio;

import com.yifei.nyctocosm.NyctocosmMod;
import com.yifei.nyctocosm.spell.ElementType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

/**
 * Curios 饰品 Item 统一注册器（单一职责：只做 DeferredRegister 注册 & 构造实例）。
 * <p>
 * 设计：
 * <ul>
 *   <li>戒指 1 + 灵魂瓶 1：独立类 PlanarCircletItem / SoulBottleItem 负责完整逻辑（SRP 分离）。</li>
 *   <li>10 种圣物：使用通用 {@link ArtifactItem} 类 + 构造参数描述。
 *       6 个 Protection 系列共享 ElementType，其余 4 个功能性用 {@link IArtifactItem.ArtifactPassiveType} 区分。</li>
 *   <li>所有物品 maxStack = 1，非堆叠，由 ArtifactPassiveHandler 在 LivingTick 中判定是否"圣物已装备"。</li>
 * </ul>
 * <p>
 * 对应原数据包物品 CMD（CustomModelData）映射（与方案 A 一一对应）：
 * <table>
 *     <tr><th>原 CMD</th><th>圣物 ID（注册名）</th><th>类型</th></tr>
 *     <tr><td>1</td><td>artifact_transposition</td><td>TRANSPOSITION (dream)</td></tr>
 *     <tr><td>2</td><td>artifact_amelioration</td><td>AMELIORATION (radiance)</td></tr>
 *     <tr><td>3</td><td>artifact_levitation</td><td>LEVITATION (dream)</td></tr>
 *     <tr><td>4</td><td>artifact_absorption</td><td>ABSORPTION (void)</td></tr>
 *     <tr><td>5</td><td>artifact_accumulation</td><td>ACCUMULATION (resonance)</td></tr>
 *     <tr><td>6</td><td>artifact_protection_dream</td><td>Protection DREAM</td></tr>
 *     <tr><td>7</td><td>artifact_protection_radiance</td><td>Protection RADIANCE</td></tr>
 *     <tr><td>8</td><td>artifact_protection_resonance</td><td>Protection RESONANCE</td></tr>
 *     <tr><td>9</td><td>artifact_protection_nightmare</td><td>Protection NIGHTMARE</td></tr>
 *     <tr><td>10</td><td>artifact_protection_void</td><td>Protection VOID</td></tr>
 *     <tr><td>11</td><td>artifact_protection_dissonance</td><td>Protection DISSONANCE</td></tr>
 *     <tr><td>12</td><td>artifact_expulsion</td><td>EXPULSION (dissonance)</td></tr>
 *     <tr><td>13</td><td>artifact_extraction</td><td>EXTRACTION (resonance)</td></tr>
 * </table>
 * 按用户 P4 要求取"10 种"：6 Protection + Absorption + Accumulation + Amelioration + Extraction = 10。
 * Locomotion/Transposition/Levitation/Expulsion 作为扩展，默认也注册，便于后续使用。
 */
public final class NyctocosmCurioItems {

    /** 饰品 Item DeferredRegister，挂在 Forge 物品注册表上 */
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, NyctocosmMod.MOD_ID);

    private NyctocosmCurioItems() {}

    // ==================== 1. 戒指 + 灵魂瓶 ====================

    public static final RegistryObject<Item> PLANAR_CIRCLET = ITEMS.register("planar_circlet", PlanarCircletItem::new);
    public static final RegistryObject<Item> SOUL_BOTTLE = ITEMS.register("soul_bottle", SoulBottleItem::new);

    // ==================== 2. 10 种主圣物（P4 正式清单） ====================

    // Protection 系列 6（6 × ELEMENT_RESISTANCE，每个带元素类型）
    public static final RegistryObject<Item> ARTIFACT_PROTECTION_DREAM =
            regProtection("artifact_protection_dream", ElementType.DREAM);
    public static final RegistryObject<Item> ARTIFACT_PROTECTION_RADIANCE =
            regProtection("artifact_protection_radiance", ElementType.RADIANCE);
    public static final RegistryObject<Item> ARTIFACT_PROTECTION_RESONANCE =
            regProtection("artifact_protection_resonance", ElementType.RESONANCE);
    public static final RegistryObject<Item> ARTIFACT_PROTECTION_NIGHTMARE =
            regProtection("artifact_protection_nightmare", ElementType.NIGHTMARE);
    public static final RegistryObject<Item> ARTIFACT_PROTECTION_VOID =
            regProtection("artifact_protection_void", ElementType.VOID);
    public static final RegistryObject<Item> ARTIFACT_PROTECTION_DISSONANCE =
            regProtection("artifact_protection_dissonance", ElementType.DISSONANCE);

    // 功能性 4 个（凑满 P4 要求的 10 种）
    public static final RegistryObject<Item> ARTIFACT_ABSORPTION =
            regArtifact("artifact_absorption", IArtifactItem.ArtifactPassiveType.ABSORPTION, null);
    public static final RegistryObject<Item> ARTIFACT_ACCUMULATION =
            regArtifact("artifact_accumulation", IArtifactItem.ArtifactPassiveType.ACCUMULATION, null);
    public static final RegistryObject<Item> ARTIFACT_AMELIORATION =
            regArtifact("artifact_amelioration", IArtifactItem.ArtifactPassiveType.AMELIORATION, null);
    public static final RegistryObject<Item> ARTIFACT_EXTRACTION =
            regArtifact("artifact_extraction", IArtifactItem.ArtifactPassiveType.EXTRACTION, null);

    // ==================== 3. 扩展圣物（可后续启用，默认注册以便调试） ====================

    public static final RegistryObject<Item> ARTIFACT_TRANSPOSITION =
            regArtifact("artifact_transposition", IArtifactItem.ArtifactPassiveType.TRANSPOSITION, null);
    public static final RegistryObject<Item> ARTIFACT_LEVITATION =
            regArtifact("artifact_levitation", IArtifactItem.ArtifactPassiveType.LEVITATION, null);
    public static final RegistryObject<Item> ARTIFACT_EXPULSION =
            regArtifact("artifact_expulsion", IArtifactItem.ArtifactPassiveType.EXPULSION, null);
    public static final RegistryObject<Item> ARTIFACT_LOCOMOTION =
            regArtifact("artifact_locomotion", IArtifactItem.ArtifactPassiveType.LOCOMOTION, null);

    // ==================== 工具方法 ====================

    private static RegistryObject<Item> regProtection(String name, ElementType element) {
        return regArtifact(name, IArtifactItem.ArtifactPassiveType.ELEMENT_RESISTANCE, element);
    }

    /** 通用 ArtifactItem 注册（Supplier 延迟构造，避免注册顺序问题） */
    private static RegistryObject<Item> regArtifact(String name,
                                                    IArtifactItem.ArtifactPassiveType passive,
                                                    ElementType element) {
        return ITEMS.register(name, () -> new ArtifactItem(passive, element));
    }

    // ==================== 通用圣物 Item 实现（内部类，职责单一） ====================

    /**
     * 通用圣物 Item 类：实现 ICurioItem + IArtifactItem，允许放入 charm 槽。
     * 具体被动效果由 {@link com.yifei.nyctocosm.curio.ArtifactPassiveHandler} 分派，
     * 符合 DIP（依赖抽象的被动类型而非每个圣物写一个类）。
     */
    public static class ArtifactItem extends Item implements ICurioItem, IArtifactItem {

        private final ArtifactPassiveType passiveType;
        private final ElementType element; // nullable

        public ArtifactItem(ArtifactPassiveType passiveType, ElementType element) {
            super(new Item.Properties().stacksTo(1));
            this.passiveType = passiveType;
            this.element = element;
        }

        @Override
        @Nonnull
        public ArtifactPassiveType getPassiveType() {
            return passiveType;
        }

        @Override
        public ElementType getElementType() {
            return element;
        }

        @Override
        public boolean canEquip(SlotContext context, ItemStack stack) {
            // Protection 圣物走 relic 槽，功能性圣物走 artifact 槽
            String targetSlot = (element != null)
                    ? com.yifei.nyctocosm.curio.NyctocosmCurios.SLOT_RELIC
                    : com.yifei.nyctocosm.curio.NyctocosmCurios.SLOT_ARTIFACT;
            return targetSlot.equals(context.identifier());
        }

        /** 动态 tooltip：显示"元素 X 抗性 / 功能性：Y"，避免写静态 JSON 维护成本。 */
        @Override
        public void appendHoverText(@Nonnull ItemStack stack, @javax.annotation.Nullable net.minecraft.world.level.Level level,
                                    @Nonnull java.util.List<Component> tooltip, @Nonnull net.minecraft.world.item.TooltipFlag flag) {
            ChatFormatting fmt = (element != null) ? element.getChatFormat() : ChatFormatting.LIGHT_PURPLE;
            if (element != null) {
                // 保护圣物
                tooltip.add(Component.translatable("artifact.type." + element.getSerializedName()).withStyle(fmt));
                tooltip.add(Component.translatable("artifact.protection.lore").withStyle(ChatFormatting.DARK_GRAY));
                tooltip.add(Component.translatable("artifact.protection.lore1").withStyle(ChatFormatting.DARK_GRAY));
            } else {
                // 功能性圣物：根据 passiveType 映射（与原 give.mcfunction 中的 translate 键一一对应）
                String typeKey = switch (passiveType) {
                    case ABSORPTION -> "artifact.type.void";
                    case ACCUMULATION, EXTRACTION -> "artifact.type.resonance";
                    case AMELIORATION -> "artifact.type.radiance";
                    case TRANSPOSITION, LEVITATION -> "artifact.type.dream";
                    case EXPULSION -> "artifact.type.dissonance";
                    case LOCOMOTION -> "artifact.type.locomotion";
                    default -> "artifact.type.unknown";
                };
                String nameKey = switch (passiveType) {
                    case ABSORPTION -> "artifact.absorption";
                    case ACCUMULATION -> "artifact.accumulation";
                    case AMELIORATION -> "artifact.amelioration";
                    case TRANSPOSITION -> "artifact.transposition";
                    case LEVITATION -> "artifact.levitation";
                    case EXPULSION -> "artifact.expulsion";
                    case EXTRACTION -> "artifact.extraction";
                    case LOCOMOTION -> "artifact.locomotion";
                    default -> null;
                };
                if (nameKey != null) {
                    tooltip.add(Component.translatable(typeKey).withStyle(fmt));
                    tooltip.add(Component.translatable(nameKey + ".lore").withStyle(ChatFormatting.DARK_GRAY));
                    try {
                        String extra = Component.translatable(nameKey + ".lore1").getString();
                        if (!extra.startsWith("translate:")) {
                            tooltip.add(Component.translatable(nameKey + ".lore1").withStyle(ChatFormatting.DARK_GRAY));
                        }
                    } catch (Exception ignored) {
                        // 没 .lore1 的圣物（如 Amelioration、Levitation）直接跳过
                    }
                }
            }
        }
    }
}
