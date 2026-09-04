package com.yifei.nyctocosm.spell.impl;

import com.yifei.nyctocosm.spell.ElementType;
import com.yifei.nyctocosm.spell.Spell;
import com.yifei.nyctocosm.spell.SpellCategory;
import com.yifei.nyctocosm.spell.SpellHelpers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * 招致术（Incurrence）— NIGHTMARE，按当前所在生物群系召唤对应 Boss。
 * <p>
 * 原行为（180 tick 吟唱后 transmutate）：
 * <pre>
 * rotated ~ -90 run effects:elements/nightmare/transmutate/play
 * if predicate nyctocosm:biomes/night_wastes       run serpent:incur
 * if predicate nyctocosm:biomes/desert_of_rime    run sorcerer:incur
 * if predicate nyctocosm:biomes/infernal_chasms  run emissary:incur
 * </pre>
 * Java 化：
 * <ul>
 *   <li>由于 Boss 召唤依赖 serpent/sorcerer/emissary 各自的 incur 复杂 mcfunction（P6 未实现），
 *     P3 暂用「占位逻辑」：按生物群系（forge biome key 字符串匹配）打印 Actionbar 提示，
 *     同时标记玩家「招致中」状态并召唤大量梦魇粒子（效果近似 incur 前摇）</li>
 *   <li>待 P6 Boss 实体实现后，把对应 incur() 方法塞进 switch 即可。</li>
 * </ul>
 */
public class IncurrenceSpell extends Spell {
    public IncurrenceSpell() {
        super("incurrence", ElementType.NIGHTMARE.mask,
                0f, 180, 0, 0, SpellCategory.SUMMON);
    }

    /** 3 个触发生物群系（同原 predicates 对应 biome key） */
    private static final ResourceKey<net.minecraft.world.level.biome.Biome> NIGHT_WASTES =
            biomeKey("night_wastes");
    private static final ResourceKey<net.minecraft.world.level.biome.Biome> DESERT_OF_RIME =
            biomeKey("desert_of_rime");
    private static final ResourceKey<net.minecraft.world.level.biome.Biome> INFERNAL_CHASMS =
            biomeKey("infernal_chasms");

    private static ResourceKey<net.minecraft.world.level.biome.Biome> biomeKey(String path) {
        return ResourceKey.create(Registries.BIOME,
                ResourceLocation.fromNamespaceAndPath("nyctocosm", path));
    }

    @Override
    public void onComplete(Player caster, Level level) {
        SpellHelpers.playElementParticles(caster, ElementType.NIGHTMARE);
        SpellHelpers.playTransmutateSound(caster);

        // 判定当前所在生物群系
        ResourceKey<net.minecraft.world.level.biome.Biome> current;
        try {
            current = level.getBiome(caster.blockPosition()).unwrapKey().orElse(null);
        } catch (Exception ex) {
            current = null;
        }

        String boss = "（未知生物群系）";
        if (NIGHT_WASTES.equals(current)) {
            boss = "巨蛇（Serpent）";
        } else if (DESERT_OF_RIME.equals(current)) {
            boss = "法师（Sorcerer）";
        } else if (INFERNAL_CHASMS.equals(current)) {
            boss = "使者（Emissary）";
        }
        SpellHelpers.actionbar(caster, "spell.nyctocosm.incurrence.summon",
                "招致术：生物群系检测到 → " + boss + "（P6 Boss 实体完成后实装召唤）");
        caster.addTag("incurring");
        // 大量梦魇粒子
        if (level instanceof net.minecraft.server.level.ServerLevel sl) {
            for (int i = 0; i < 60; i++) {
                double dx = (sl.getRandom().nextDouble() - 0.5) * 2.0;
                double dy = sl.getRandom().nextDouble() * 2.5;
                double dz = (sl.getRandom().nextDouble() - 0.5) * 2.0;
                sl.sendParticles(net.minecraft.core.particles.ParticleTypes.ENTITY_EFFECT,
                        caster.getX() + dx, caster.getY() + dy, caster.getZ() + dz,
                        0, 0.60f, 0.40f, 0.67f, 1.0);
            }
        }
    }
}
