package com.yifei.nyctocosm.spell;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 蜡烛阵 + 掉落物 精确匹配器（SRP，对照原 spells/scan.mcfunction + block/7.mcfunction）。
 * <p>
 * 与 P2 遗留简化版的关键区别：
 * <ol>
 *   <li><b>蜡烛阵必须精确命中 16 个固定 (dx,dz) 偏移</b>（颜色不限，只要是 #candles 且点燃），而非 7×7 范围内任意 16 根。
 *     布局：X=±3 列每列 Z=0,±1（共 6 根）+ Z=±3 行每行 X=0,±1（共 6 根）+ (±2,±2) 对角（共 4 根）= 16。</li>
 *   <li><b>法术由「3 格范围内掉落 Item 实体」唯一决定（17 掉落物 ⇄ 17 法术一一对应）</b>，
 *     匹配成功后消耗（移除）该 Item，最后再校验 spell.canCast() 作为兜底。</li>
 * </ol>
 *
 * 匹配失败不抛异常、不改写世界状态（线程安全，纯函数式查询 + 单写入「消耗 Item」）。
 */
public final class CandleMatrixMatcher {

    private CandleMatrixMatcher() {}

    // ============================================================
    // 16 个蜡烛固定偏移 (dx, dz) — 与原 scan.mcfunction 逐行一一对应
    // ============================================================

    /** 不可变偏移列表（初始化构建一次，后续只读） */
    private static final List<BlockPos> CANDLE_OFFSETS;

    static {
        List<BlockPos> list = new ArrayList<>(16);
        // X=±3 列（Z: 0 / +1 / -1）— 对应 scan L4~L9
        for (int z : new int[]{0, 1, -1}) {
            list.add(new BlockPos(3, 0, z));
            list.add(new BlockPos(-3, 0, z));
        }
        // Z=±3 行（X: 0 / +1 / -1）— 对应 scan L10~L15
        for (int x : new int[]{0, 1, -1}) {
            list.add(new BlockPos(x, 0, 3));
            list.add(new BlockPos(x, 0, -3));
        }
        // 对角 (±2, ±2) — 对应 scan L16~L19
        list.add(new BlockPos(2, 0, 2));
        list.add(new BlockPos(-2, 0, 2));
        list.add(new BlockPos(2, 0, -2));
        list.add(new BlockPos(-2, 0, -2));
        CANDLE_OFFSETS = Collections.unmodifiableList(list);
    }

    /** 调试用：获取 16 个蜡烛偏移（命令 /nyctocosm matrix 可展示） */
    public static List<BlockPos> candleOffsets() { return CANDLE_OFFSETS; }

    // ============================================================
    // 17 掉落物 Item → Spell ID 映射（一一对应，block/7.mcfunction 逐行对照）
    // ============================================================

    private static final Map<Item, String> ITEM_TO_SPELL_ID = new ConcurrentHashMap<>(32);

    static {
        ITEM_TO_SPELL_ID.put(Items.GLOWSTONE_DUST,     "luminescence");  // glowstone
        ITEM_TO_SPELL_ID.put(Items.ECHO_SHARD,         "resonance");     // echo_shard
        ITEM_TO_SPELL_ID.put(Items.ENDER_EYE,          "evanescence");   // ender_eye
        ITEM_TO_SPELL_ID.put(Items.WITHER_ROSE,        "incurrence");    // wither_rose
        ITEM_TO_SPELL_ID.put(Items.COAL,               "abhorrence");    // coal
        ITEM_TO_SPELL_ID.put(Items.GHAST_TEAR,         "forbiddance");   // ghast_tear
        ITEM_TO_SPELL_ID.put(Items.CRYING_OBSIDIAN,    "gravitance");    // crying_obsidian
        ITEM_TO_SPELL_ID.put(Items.APPLE,              "subsistence");   // apple
        ITEM_TO_SPELL_ID.put(Items.IRON_INGOT,         "reflectance");   // iron_ingot
        ITEM_TO_SPELL_ID.put(Items.SOUL_SAND,          "submergence");   // soul_sand
        ITEM_TO_SPELL_ID.put(Items.COBBLESTONE,        "adamance");      // cobblestone
        ITEM_TO_SPELL_ID.put(Items.GUNPOWDER,          "incandescence"); // gunpowder
        ITEM_TO_SPELL_ID.put(Items.COOKED_BEEF,        "resistance");    // cooked_beef
        ITEM_TO_SPELL_ID.put(Items.ENDER_PEARL,        "transmittance"); // ender_pearl
        ITEM_TO_SPELL_ID.put(Items.GLASS_BOTTLE,       "vacance");       // glass_bottle
        ITEM_TO_SPELL_ID.put(Items.FEATHER,            "repellance");    // feather
        ITEM_TO_SPELL_ID.put(Items.QUARTZ,             "fulminance");    // quartz
    }

    // ============================================================
    // 一、蜡烛阵校验（精确 16 坐标 + 全部点燃蜡烛/蜡烛蛋糕）
    // ============================================================

    /**
     * 校验单个蜡烛位置是否为点燃蜡烛（用于命令逐位调试 + verifyMatrix 内部复用）。
     *
     * @param level  世界
     * @param center 玩家 blockPosition
     * @param off    相对偏移（必须来自 {@link #candleOffsets()}）
     * @return 该偏移位置为点燃 CandleBlock 或 CandleCakeBlock 返回 true
     */
    public static boolean verifyMatrixPosition(Level level, BlockPos center, BlockPos off) {
        BlockPos pos = center.offset(off);
        BlockState state = level.getBlockState(pos);
        Block b = state.getBlock();
        if (b instanceof CandleBlock) return Boolean.TRUE.equals(state.getValue(CandleBlock.LIT));
        if (b instanceof CandleCakeBlock) return true;
        return false;
    }

    /**
     * 校验玩家脚下 16 个固定坐标全部为点燃蜡烛（颜色不限）。
     * <p>
     * 原理：对每个 (dx,dz) 偏移执行与原 scan.mcfunction 的 `unless block ~dx ~ ~dz #candles[lit=true] → fail`
     * 等价判断：CandleBlock 且 lit=true，或 CandleCakeBlock（蛋糕蜡烛视为点燃）。
     *
     * @return true = 16 根蜡烛坐标完全匹配并全部点燃；false = 缺任何一根
     */
    public static boolean verifyMatrix(Player caster) {
        Level level = caster.level();
        BlockPos center = caster.blockPosition();
        for (BlockPos off : CANDLE_OFFSETS) {
            if (!verifyMatrixPosition(level, center, off)) return false;
        }
        return true;
    }

    // ============================================================
    // 二、掉落物 → 法术匹配（扫描玩家周围 3 格内 Item 实体）
    // ============================================================

    /**
     * 扫描玩家 3 格范围内的 Item 实体，按 Item.id 匹配到对应法术。
     * <ol>
     *   <li>找到第一个匹配映射表的 ItemEntity（与原 `as @e[type=item,distance=..3]` 顺序等价，
     *       对于 17 种互斥材料的场景，任取第一个合法即可）</li>
     *   <li>通过 {@link SpellRegistry#byId(String)} 查表得到 Spell；若法术缺失（注册表变更）返回 null</li>
     *   <li>执行 {@code spell.canCast()} 做最终校验（如 Evanescence 使者领域内禁用等）</li>
     *   <li>若所有校验通过：消耗（移除）此 Item 实体并返回 Spell（触发方 SpellCastingManager 负责启动吟唱）</li>
     * </ol>
     *
     * @return 匹配成功返回 Spell；失败（没有匹配掉落物 / canCast=false / 注册表失效）返回 null
     */
    @Nullable
    public static Spell matchAndConsumeCatalyst(Player caster) {
        Level level = caster.level();
        if (level.isClientSide) return null;

        // 范围：±3 立方体 + 玩家位置（AABB 六参数构造，与原 distance=..3 对应）
        Vec3 pos = caster.position();
        AABB box = new AABB(pos.x, pos.y, pos.z, pos.x, pos.y, pos.z).inflate(3.0);
        List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, box);

        Spell matched = null;
        ItemEntity matchedItem = null;
        for (ItemEntity ie : items) {
            ItemStack stack = ie.getItem();
            if (stack.isEmpty()) continue;
            String spellId = ITEM_TO_SPELL_ID.get(stack.getItem());
            if (spellId == null) continue;
            Spell s = SpellRegistry.byId(spellId);
            if (s == null) continue;
            if (!s.canCast(caster, level)) continue;
            matched = s;
            matchedItem = ie;
            break;
        }

        if (matched == null || matchedItem == null) return null;

        // 消耗催化剂（1 个）：原行为是整个 item 实体被 kill @s 移除
        // 若 Stack size > 1，只减 1，剩余保留（与 mc 指令语义略有差别但更合理）
        ItemStack stack = matchedItem.getItem();
        if (stack.getCount() <= 1) {
            matchedItem.discard();
        } else {
            stack.shrink(1);
            matchedItem.setItem(stack);
        }
        return matched;
    }

    // ============================================================
    // 辅助：调试命令列出所有掉落物 → 法术 映射
    // ============================================================

    /** 返回不可变视图（命令列表用） */
    public static Map<Item, String> catalystMap() {
        return Collections.unmodifiableMap(ITEM_TO_SPELL_ID);
    }
}
