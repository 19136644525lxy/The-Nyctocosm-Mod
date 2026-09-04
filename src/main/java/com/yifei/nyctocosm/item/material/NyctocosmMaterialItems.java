package com.yifei.nyctocosm.item.material;

import com.yifei.nyctocosm.NyctocosmMod;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * 材料类物品注册中心（单一职责：注册 5 个 crafting / catalyst 材料）。
 * <p>
 * 这些物品在原数据包里以"原版物品 + CustomModelData + NBT display"方式提供，
 * 这里全部转为独立模组物品：
 * <ul>
 *   <li>nyctocosmic_ash    —— 夜宙之尘（原 SUGAR 包装）</li>
 *   <li>nightmare_scale    —— 梦魇鳞片（龙鳞刃合成材料）</li>
 *   <li>null_splinter      —— 虚无碎片（法杖合成材料）</li>
 *   <li>seelic_essence     —— 希里克精华（镰刀/法术阵材料）</li>
 *   <li>void_shard         —— 虚空碎片（移换/悬浮圣物合成材料）</li>
 * </ul>
 */
public final class NyctocosmMaterialItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, NyctocosmMod.MOD_ID);

    // 夜宙之尘：SUGAR 包装版 → 独立模组物品（Rarity 保持普通，stacksTo 64，与原行为一致）
    public static final RegistryObject<Item> NYCTOCOSMIC_ASH = ITEMS.register("nyctocosmic_ash",
            () -> new Item(new Item.Properties()) {
                @Override
                public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level,
                                            @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
                    // 保持原数据包 lore（原 display.Lore 为空，故此处不填）
                }
            });

    // 梦魇鳞片（材料，原 iron_nugget override）
    public static final RegistryObject<Item> NIGHTMARE_SCALE = ITEMS.register("nightmare_scale",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));

    // 虚无碎片（材料，原 echo_shard override）
    public static final RegistryObject<Item> NULL_SPLINTER = ITEMS.register("null_splinter",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE)));

    // 希里克精华（材料，原 glowstone_dust override / 祭坛 dais 掉）
    public static final RegistryObject<Item> SEELIC_ESSENCE = ITEMS.register("seelic_essence",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE)));

    // 虚空碎片（材料，原 diamond override）
    public static final RegistryObject<Item> VOID_SHARD = ITEMS.register("void_shard",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));

    private NyctocosmMaterialItems() {}

    /** FML 启动期注册入口。 */
    public static void init(IEventBus modBus) {
        ITEMS.register(modBus);
    }
}
