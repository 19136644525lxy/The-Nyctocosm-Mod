package com.yifei.nyctocosm.item;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.yifei.nyctocosm.NyctocosmMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 * Nyctocosm 创造模式物品栏与物品入口
 * <p>
 * 设计原则（方案 A - 包装式入口）：
 * 本类不注册新的 Item 类型，而是注册一个 {@link CreativeModeTab}，将 25 个
 * "带正确 NBT 的原版物品" ItemStack 放入创造栏。
 * <p>
 * 玩家从创造栏拿到的物品，与 mcfunction give 命令产出的物品完全一致，
 * 因此数据包中的 predicate（如 {@code items:["minecraft:warped_fungus_on_a_stick"], nbt:"{scythe:1}"})
 * 能完美识别，无需修改任何 mcfunction。
 * <p>
 * 物品清单（25 个）：
 * <ul>
 *   <li>镰刀类 4：seelic_scythe / wyrmsliver / nullfissure / semblanceofdoom（基于 warped_fungus_on_a_stick）</li>
 *   <li>圣物类 13：transposition / amelioration / levitation / absorption / accumulation /
 *       protection_{dream,radiance,resonance,nightmare,void,dissonance} / expulsion / extraction（基于 red_dye）</li>
 *   <li>戒指+瓶 2：soul_bottle / planar_coil（基于 carrot_on_a_stick）</li>
 *   <li>卷册类 5：journal / catechism / dreams / axiotopology / adherent（基于 written_book）</li>
 *   <li>载体类 1：nyctocosmic_ash（基于 sugar，gravel 维度掉落物）</li>
 * </ul>
 */
public class NyctocosmItems {

    // ==================== SNBT 常量（从原数据包 give mcfunction 精确复制） ====================
    // 注意：必须在 NYCTOCOSM_TAB 之前声明，避免 Java 静态初始化器的非法前向引用。

    // --- 镰刀类（warped_fungus_on_a_stick，主手使用，click_scythe 触发） ---
    private static final String SEELIC_SCYTHE_NBT =
            "{display:{Name:'{\"translate\":\"item.nyctocosm.seelic_scythe\",\"color\":\"dark_aqua\",\"italic\":false}'," +
            "Lore:['{\"translate\":\"item.nyctocosm.seelic_scythe.lore\",\"color\":\"dark_gray\",\"italic\":false}'," +
            "'[{\"translate\":\"item.nyctocosm.seelic_scythe.lore1\",\"color\":\"dark_gray\",\"italic\":false," +
            "\"with\":[{\"translate\":\"item.nyctocosm.seelic_scythe.lore2\",\"color\":\"dark_aqua\"}]}]']}," +
            "CustomModelData:1,scythe:1,reaping:1," +
            "AttributeModifiers:[{AttributeName:\"generic.attack_damage\",Name:\"generic.attack_damage\"," +
            "Amount:11,Operation:0,UUID:[I;-128239938,-731427959,-1751239047,616489124],Slot:\"mainhand\"}," +
            "{AttributeName:\"generic.attack_speed\",Name:\"generic.attack_speed\"," +
            "Amount:-2.4,Operation:0,UUID:[I;-1407112446,-697809930,-1142195741,-406150722],Slot:\"mainhand\"}]}";

    private static final String WYRMSLIVER_NBT =
            "{display:{Name:'{\"translate\":\"item.nyctocosm.wyrmsliver\",\"color\":\"dark_red\",\"italic\":false}'," +
            "Lore:['{\"translate\":\"item.nyctocosm.wyrmsliver.lore\",\"color\":\"dark_gray\",\"italic\":false}'," +
            "'[{\"translate\":\"item.nyctocosm.wyrmsliver.lore1\",\"color\":\"dark_gray\",\"italic\":false," +
            "\"with\":[{\"translate\":\"item.nyctocosm.wyrmsliver.lore2\",\"color\":\"dark_red\"}]}]']}," +
            "CustomModelData:2,wyrmsliver:1," +
            "AttributeModifiers:[{AttributeName:\"generic.attack_damage\",Name:\"generic.attack_damage\"," +
            "Amount:14,Operation:0,UUID:[I;-128239938,-731427959,-1751239047,616489124],Slot:\"mainhand\"}," +
            "{AttributeName:\"generic.attack_speed\",Name:\"generic.attack_speed\"," +
            "Amount:-2.7,Operation:0,UUID:[I;-1407112446,-697809930,-1142195741,-406150722],Slot:\"mainhand\"}]}";

    private static final String NULLFISSURE_NBT =
            "{display:{Name:'{\"translate\":\"item.nyctocosm.nullfissure\",\"color\":\"dark_purple\",\"italic\":false}'," +
            "Lore:['{\"translate\":\"item.nyctocosm.nullfissure.lore\",\"color\":\"dark_gray\",\"italic\":false}'," +
            "'[{\"translate\":\"item.nyctocosm.nullfissure.lore1\",\"color\":\"dark_gray\",\"italic\":false," +
            "\"with\":[{\"translate\":\"item.nyctocosm.nullfissure.lore2\",\"color\":\"dark_purple\"}]}]']}," +
            "CustomModelData:10,nullfissure:1," +
            "AttributeModifiers:[{AttributeName:\"generic.attack_damage\",Name:\"generic.attack_damage\"," +
            "Amount:15.5,Operation:0,UUID:[I;-128239938,-731427959,-1751239047,616489124],Slot:\"mainhand\"}," +
            "{AttributeName:\"generic.attack_speed\",Name:\"generic.attack_speed\"," +
            "Amount:-3.0,Operation:0,UUID:[I;-1407112446,-697809930,-1142195741,-406150722],Slot:\"mainhand\"}]}";

    private static final String SEMBLANCEOFDOOM_NBT =
            "{display:{Name:'[{\"translate\":\"item.nyctocosm.semblanceofdoom1\",\"color\":\"#FF4D00\",\"italic\":false}," +
            "{\"translate\":\"item.nyctocosm.semblanceofdoom2\",\"color\":\"#FF4D00\",\"italic\":false}]'," +
            "Lore:['{\"translate\":\"item.nyctocosm.semblanceofdoom.lore\",\"color\":\"dark_gray\",\"italic\":false}'," +
            "'[{\"translate\":\"item.nyctocosm.semblanceofdoom.lore1\",\"color\":\"dark_gray\",\"italic\":false," +
            "\"with\":[{\"translate\":\"item.nyctocosm.semblanceofdoom.lore2\",\"color\":\"gold\",\"italic\":false}]}]']}," +
            "HideFlags:253,Unbreakable:1b,CustomModelData:20,semblanceofdoom:1," +
            "Enchantments:[{id:\"minecraft:knockback\",lvl:3s},{id:\"minecraft:fire_aspect\",lvl:2s}]," +
            "AttributeModifiers:[{AttributeName:\"generic.attack_damage\",Name:\"generic.attack_damage\"," +
            "Amount:19,Operation:0,UUID:[I;1675042379,-89308887,-1676345572,-1207857754],Slot:\"mainhand\"}," +
            "{AttributeName:\"generic.attack_speed\",Name:\"generic.attack_speed\"," +
            "Amount:-3.3,Operation:0,UUID:[I;67248978,467288328,-1870584502,1194817511],Slot:\"mainhand\"}]}";

    // --- 圣物类（red_dye，物品栏即激活，靠 tag 检测） ---
    private static final String TRANSPOSITION_NBT =
            "{display:{Name:'{\"translate\":\"artifact.transposition\"}'," +
            "Lore:['{\"translate\":\"artifact.type.dream\"}','{\"translate\":\"artifact.transposition.lore\"}'," +
            "'{\"translate\":\"artifact.transposition.lore1\"}']},CustomModelData:1,transposition:1,artifact:1}";

    private static final String AMELIORATION_NBT =
            "{display:{Name:'{\"translate\":\"artifact.amelioration\"}'," +
            "Lore:['{\"translate\":\"artifact.type.radiance\"}','{\"translate\":\"artifact.amelioration.lore\"}']}," +
            "CustomModelData:2,amelioration:1,artifact:1}";

    private static final String LEVITATION_NBT =
            "{display:{Name:'{\"translate\":\"artifact.levitation\"}'," +
            "Lore:['{\"translate\":\"artifact.type.dream\"}','{\"translate\":\"artifact.levitation.lore\"}']}," +
            "CustomModelData:3,levitation:1,artifact:1}";

    private static final String ABSORPTION_NBT =
            "{display:{Name:'{\"translate\":\"artifact.absorption\"}'," +
            "Lore:['{\"translate\":\"artifact.type.void\"}','{\"translate\":\"artifact.absorption.lore\"}'," +
            "'{\"translate\":\"artifact.absorption.lore1\"}']},CustomModelData:4,absorption:1,artifact:1}";

    private static final String ACCUMULATION_NBT =
            "{display:{Name:'{\"translate\":\"artifact.accumulation\"}'," +
            "Lore:['{\"translate\":\"artifact.type.resonance\"}','{\"translate\":\"artifact.accumulation.lore\"}']}," +
            "CustomModelData:5,accumulation:1,artifact:1}";

    private static final String PROTECTION_DREAM_NBT =
            "{display:{Name:'{\"translate\":\"artifact.protection_dream\"}'," +
            "Lore:['{\"translate\":\"artifact.type.dream\"}','{\"translate\":\"artifact.protection.lore\"}'," +
            "'{\"translate\":\"artifact.protection.lore1\"}']},CustomModelData:6,protection_dream:1,artifact:1}";

    private static final String PROTECTION_RADIANCE_NBT =
            "{display:{Name:'{\"translate\":\"artifact.protection_radiance\"}'," +
            "Lore:['{\"translate\":\"artifact.type.radiance\"}','{\"translate\":\"artifact.protection.lore\"}'," +
            "'{\"translate\":\"artifact.protection.lore1\"}']},CustomModelData:7,protection_radiance:1,artifact:1}";

    private static final String PROTECTION_RESONANCE_NBT =
            "{display:{Name:'{\"translate\":\"artifact.protection_resonance\"}'," +
            "Lore:['{\"translate\":\"artifact.type.resonance\"}','{\"translate\":\"artifact.protection.lore\"}'," +
            "'{\"translate\":\"artifact.protection.lore1\"}']},CustomModelData:8,protection_resonance:1,artifact:1}";

    private static final String PROTECTION_NIGHTMARE_NBT =
            "{display:{Name:'{\"translate\":\"artifact.protection_nightmare\"}'," +
            "Lore:['{\"translate\":\"artifact.type.nightmare\"}','{\"translate\":\"artifact.protection.lore\"}'," +
            "'{\"translate\":\"artifact.protection.lore1\"}']},CustomModelData:9,protection_nightmare:1,artifact:1}";

    private static final String PROTECTION_VOID_NBT =
            "{display:{Name:'{\"translate\":\"artifact.protection_void\"}'," +
            "Lore:['{\"translate\":\"artifact.type.void\"}','{\"translate\":\"artifact.protection.lore\"}'," +
            "'{\"translate\":\"artifact.protection.lore1\"}']},CustomModelData:10,protection_void:1,artifact:1}";

    private static final String PROTECTION_DISSONANCE_NBT =
            "{display:{Name:'{\"translate\":\"artifact.protection_dissonance\"}'," +
            "Lore:['{\"translate\":\"artifact.type.dissonance\"}','{\"translate\":\"artifact.protection.lore\"}'," +
            "'{\"translate\":\"artifact.protection.lore1\"}']},CustomModelData:11,protection_dissonance:1,artifact:1}";

    private static final String EXPULSION_NBT =
            "{display:{Name:'{\"translate\":\"artifact.expulsion\"}'," +
            "Lore:['{\"translate\":\"artifact.type.dissonance\"}','{\"translate\":\"artifact.expulsion.lore\"}'," +
            "'{\"translate\":\"artifact.expulsion.lore1\"}']},CustomModelData:12,expulsion:1,artifact:1}";

    private static final String EXTRACTION_NBT =
            "{display:{Name:'{\"translate\":\"artifact.extraction\"}'," +
            "Lore:['{\"translate\":\"artifact.type.resonance\"}','{\"translate\":\"artifact.extraction.lore\"}']}," +
            "CustomModelData:13,extraction:1,artifact:1}";

    // --- 戒指 + 灵魂瓶（carrot_on_a_stick，副手 / 主手使用触发） ---
    private static final String SOUL_BOTTLE_NBT =
            "{display:{Name:'{\"translate\":\"item.nyctocosm.soul_bottle\",\"italic\":false}'," +
            "Lore:['[{\"translate\":\"item.nyctocosm.container.soul_count\",\"color\":\"dark_gray\",\"italic\":false," +
            "\"with\":[{\"text\":\"1\",\"color\":\"dark_aqua\"}]}]']}," +
            "CustomModelData:1,soul_bottle:1,souls_held:1}";

    private static final String PLANAR_COIL_NBT =
            "{display:{Name:'{\"translate\":\"item.nyctocosm.planar_coil\",\"color\":\"gold\",\"italic\":false," +
            "\"with\":[{\"translate\":\"item.nyctocosm.planar_coil1\",\"color\":\"aqua\"}]}'," +
            "Lore:['{\"translate\":\"item.nyctocosm.planar_coil.lore\",\"color\":\"dark_gray\",\"italic\":false}']}," +
            "CustomModelData:2,planar_circlet:1,active:0,souls_held:1}";

    // --- 卷册类（written_book，右键阅读触发 l.uselorebook） ---
    private static final String JOURNAL_NBT =
            "{CustomModelData:1,display:{Name:'[{\"translate\":\"[%s] \",\"color\":\"gold\",\"italic\":false," +
            "\"with\":[{\"translate\":\"item.nyctocosm.prefix.lore\"}]},{\"translate\":\"item.nyctocosm.journal\"," +
            "\"color\":\"aqua\",\"italic\":false}]',Lore:['{\"translate\":\"book.byAuthor\",\"color\":\"gray\"," +
            "\"italic\":false,\"with\":[{\"translate\":\"item.nyctocosm.journal.author\"}]},{\"translate\":" +
            "\"item.nyctocosm.book.generation\",\"color\":\"gray\",\"italic\":false},{\"translate\":" +
            "\"item.nyctocosm.journal.lore\",\"color\":\"dark_gray\"}]},lorebook:1,storyline:0," +
            "title:\"Ancient Journals\",author:\"The Researcher\",generation:2," +
            "pages:['{\"translate\":\"item.nyctocosm.journal.page1\"}'],HideFlags:32b}";

    private static final String CATECHISM_NBT =
            "{CustomModelData:2,display:{Name:'[{\"translate\":\"[%s] \",\"color\":\"gold\",\"italic\":false," +
            "\"with\":[{\"translate\":\"item.nyctocosm.prefix.lore\"}]},{\"translate\":\"item.nyctocosm.catechism\"," +
            "\"color\":\"dark_purple\",\"italic\":false}]',Lore:['{\"translate\":\"book.byAuthor\",\"color\":\"gray\"," +
            "\"italic\":false,\"with\":[{\"translate\":\"item.nyctocosm.catechism.author\"}]},{\"translate\":" +
            "\"item.nyctocosm.book.generation\",\"color\":\"gray\",\"italic\":false},{\"translate\":" +
            "\"item.nyctocosm.catechism.lore\",\"color\":\"dark_gray\"}]},lorebook:1,storyline:1," +
            "title:\"Catechism of the Seelic Mystics\",author:\"The Archmage\",generation:2," +
            "pages:['{\"translate\":\"item.nyctocosm.catechism.page1\"}'],HideFlags:32b}";

    private static final String DREAMS_NBT =
            "{CustomModelData:4,display:{Name:'[{\"translate\":\"[%s] \",\"color\":\"gold\",\"italic\":false," +
            "\"with\":[{\"translate\":\"item.nyctocosm.prefix.lore\"}]},{\"translate\":\"item.nyctocosm.dreams\"," +
            "\"color\":\"aqua\",\"italic\":false}]',Lore:['{\"translate\":\"book.byAuthor\",\"color\":\"gray\"," +
            "\"italic\":false,\"with\":[{\"translate\":\"item.nyctocosm.dreams.author\"}]},{\"translate\":" +
            "\"item.nyctocosm.book.generation\",\"color\":\"gray\",\"italic\":false},{\"translate\":" +
            "\"item.nyctocosm.dreams.lore\",\"color\":\"dark_gray\"}]},lorebook:1,storyline:2," +
            "title:\"Dreams of the Dead Star\",author:\"The Beast\",generation:2," +
            "pages:['{\"translate\":\"item.nyctocosm.dreams.page1\"}'],HideFlags:32b}";

    private static final String AXIOTOPOLOGY_NBT =
            "{CustomModelData:5,display:{Name:'[{\"translate\":\"[%s] \",\"color\":\"gold\",\"italic\":false," +
            "\"with\":[{\"translate\":\"item.nyctocosm.prefix.lore\"}]},{\"translate\":\"item.nyctocosm.axiotopology\"," +
            "\"color\":\"dark_aqua\",\"italic\":false}]',Lore:['{\"translate\":\"book.byAuthor\",\"color\":\"gray\"," +
            "\"italic\":false,\"with\":[{\"translate\":\"item.nyctocosm.axiotopology.author\"}]},{\"translate\":" +
            "\"item.nyctocosm.book.generation\",\"color\":\"gray\",\"italic\":false},{\"translate\":" +
            "\"item.nyctocosm.axiotopology.lore\",\"color\":\"dark_gray\"}]},title:\"Theorem One\"," +
            "author:\"Tarph-Xolaeta\",lorebook:1,storyline:3,generation:2," +
            "pages:['{\"translate\":\"item.nyctocosm.axiotopology.page1\"}'],HideFlags:32b}";

    private static final String ADHERENT_NBT =
            "{CustomModelData:3,display:{Name:'{\"translate\":\"item.nyctocosm.adherent.title\",\"color\":\"gold\"," +
            "\"italic\":false,\"with\":[{\"translate\":\"item.nyctocosm.book_number.1\"},{\"translate\":" +
            "\"item.nyctocosm.adherent.1.title\",\"color\":\"dark_red\",\"italic\":true}]}}'," +
            "Lore:['{\"translate\":\"book.byAuthor\",\"color\":\"gray\",\"italic\":false,\"with\":[{\"translate\":" +
            "\"item.nyctocosm.adherent.author\"}]},{\"translate\":\"item.nyctocosm.book.generation\",\"color\":\"gray\"," +
            "\"italic\":\"false\"},{\"translate\":\"item.nyctocosm.adherent.lore\",\"color\":\"dark_gray\"}]}," +
            "title:\"Adherent I\",author:\"Dusk\",generation:2," +
            "pages:['{\"translate\":\"item.nyctocosm.adherent.1.page1\"}','{\"translate\":\"item.nyctocosm.adherent.1.page2\"}']," +
            "HideFlags:32b}";

    // --- 载体类（sugar，gravel 维度掉落物） ---
    private static final String NYCTOCOSMIC_ASH_NBT =
            "{display:{Name:'{\"translate\":\"item.nyctocosm.nyctocosmic_ash\",\"italic\":false}'}}";

    // ==================== 创造模式物品栏注册 ====================

    /** 创造模式物品栏 DeferredRegister，挂到 mod 事件总线 */
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NyctocosmMod.MOD_ID);

    /** Nyctocosm 主物品栏，图标用灵魂镰刀 */
    public static final RegistryObject<CreativeModeTab> NYCTOCOSM_TAB = CREATIVE_TABS.register("main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.nyctocosm"))
                    .icon(() -> new ItemStack(com.yifei.nyctocosm.item.equipment.NyctocosmEquipmentItems.SEELIC_SCYTHE.get()))
                    .displayItems((params, output) -> {
                        // === 武器类（4）—— 独立模组 Item
                        output.accept(new ItemStack(com.yifei.nyctocosm.item.equipment.NyctocosmEquipmentItems.SEELIC_SCYTHE.get()));
                        output.accept(new ItemStack(com.yifei.nyctocosm.item.equipment.NyctocosmEquipmentItems.WYRMSLIVER.get()));
                        output.accept(new ItemStack(com.yifei.nyctocosm.item.equipment.NyctocosmEquipmentItems.NULLFISSURE.get()));
                        output.accept(new ItemStack(com.yifei.nyctocosm.item.equipment.NyctocosmEquipmentItems.SEMBLANCE_OF_DOOM.get()));

                        // === Curios 饰品（16）—— 戒指 + 灵魂瓶 + 14 圣物
                        output.accept(new ItemStack(com.yifei.nyctocosm.item.curio.NyctocosmCurioItems.PLANAR_CIRCLET.get()));
                        output.accept(new ItemStack(com.yifei.nyctocosm.item.curio.NyctocosmCurioItems.SOUL_BOTTLE.get()));
                        output.accept(new ItemStack(com.yifei.nyctocosm.item.curio.NyctocosmCurioItems.ARTIFACT_PROTECTION_DREAM.get()));
                        output.accept(new ItemStack(com.yifei.nyctocosm.item.curio.NyctocosmCurioItems.ARTIFACT_PROTECTION_RADIANCE.get()));
                        output.accept(new ItemStack(com.yifei.nyctocosm.item.curio.NyctocosmCurioItems.ARTIFACT_PROTECTION_RESONANCE.get()));
                        output.accept(new ItemStack(com.yifei.nyctocosm.item.curio.NyctocosmCurioItems.ARTIFACT_PROTECTION_NIGHTMARE.get()));
                        output.accept(new ItemStack(com.yifei.nyctocosm.item.curio.NyctocosmCurioItems.ARTIFACT_PROTECTION_VOID.get()));
                        output.accept(new ItemStack(com.yifei.nyctocosm.item.curio.NyctocosmCurioItems.ARTIFACT_PROTECTION_DISSONANCE.get()));
                        output.accept(new ItemStack(com.yifei.nyctocosm.item.curio.NyctocosmCurioItems.ARTIFACT_ABSORPTION.get()));
                        output.accept(new ItemStack(com.yifei.nyctocosm.item.curio.NyctocosmCurioItems.ARTIFACT_ACCUMULATION.get()));
                        output.accept(new ItemStack(com.yifei.nyctocosm.item.curio.NyctocosmCurioItems.ARTIFACT_AMELIORATION.get()));
                        output.accept(new ItemStack(com.yifei.nyctocosm.item.curio.NyctocosmCurioItems.ARTIFACT_EXTRACTION.get()));
                        output.accept(new ItemStack(com.yifei.nyctocosm.item.curio.NyctocosmCurioItems.ARTIFACT_TRANSPOSITION.get()));
                        output.accept(new ItemStack(com.yifei.nyctocosm.item.curio.NyctocosmCurioItems.ARTIFACT_LEVITATION.get()));
                        output.accept(new ItemStack(com.yifei.nyctocosm.item.curio.NyctocosmCurioItems.ARTIFACT_EXPULSION.get()));
                        output.accept(new ItemStack(com.yifei.nyctocosm.item.curio.NyctocosmCurioItems.ARTIFACT_LOCOMOTION.get()));

                        // === 卷册类（5）—— 独立模组 WrittenBook，右键打开阅读 GUI
                        output.accept(com.yifei.nyctocosm.item.book.NyctocosmBookItems.createBookStack("journal"));
                        output.accept(com.yifei.nyctocosm.item.book.NyctocosmBookItems.createBookStack("catechism"));
                        output.accept(com.yifei.nyctocosm.item.book.NyctocosmBookItems.createBookStack("dreams"));
                        output.accept(com.yifei.nyctocosm.item.book.NyctocosmBookItems.createBookStack("axiotopology"));
                        output.accept(com.yifei.nyctocosm.item.book.NyctocosmBookItems.createBookStack("adherent"));

                        // === 材料类（5）—— 独立模组 Item
                        output.accept(new ItemStack(com.yifei.nyctocosm.item.material.NyctocosmMaterialItems.NYCTOCOSMIC_ASH.get()));
                        output.accept(new ItemStack(com.yifei.nyctocosm.item.material.NyctocosmMaterialItems.NIGHTMARE_SCALE.get()));
                        output.accept(new ItemStack(com.yifei.nyctocosm.item.material.NyctocosmMaterialItems.NULL_SPLINTER.get()));
                        output.accept(new ItemStack(com.yifei.nyctocosm.item.material.NyctocosmMaterialItems.SEELIC_ESSENCE.get()));
                        output.accept(new ItemStack(com.yifei.nyctocosm.item.material.NyctocosmMaterialItems.VOID_SHARD.get()));
                    })
                    .build());

    // ==================== 工具方法 ====================

    /**
     * 构建带指定 NBT 的原版物品 ItemStack。
     * <p>
     * SNBT 字符串来自原数据包 give mcfunction 的精确复制，
     * 保证玩家从创造栏拿到的物品与 mcfunction 产出的物品完全一致。
     *
     * @param item 原版物品类型（如 warped_fungus_on_a_stick / red_dye / carrot_on_a_stick / written_book / sugar）
     * @param snbt 物品 NBT 的 SNBT 字符串
     * @return 带 NBT 的 ItemStack
     */
    private static ItemStack buildStack(Item item, String snbt) {
        ItemStack stack = new ItemStack(item);
        try {
            stack.setTag(TagParser.parseTag(snbt));
        } catch (CommandSyntaxException e) {
            NyctocosmMod.LOGGER.error("无法解析 Nyctocosm 物品 NBT（物品：{}）: {}", item, snbt, e);
        }
        return stack;
    }
}
