package com.yifei.nyctocosm.spell.impl;

import com.yifei.nyctocosm.spell.ElementType;
import com.yifei.nyctocosm.spell.Spell;
import com.yifei.nyctocosm.spell.SpellCategory;
import com.yifei.nyctocosm.spell.SpellHelpers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * 维生术（Subsistence）— RADIANCE，瞬时治疗 + 饱和。
 * <p>
 * 原 transmutate:
 * <pre>
 *   距离 ≤3 内的所有玩家（施法者自己）：
 *     heal = (10 + power×4) × 1000（= hp 10.0 ~ 30.0，即 5❤ ~ 15❤）
 *     effect give @s saturation 3 0 true（3 tick 瞬间 饱和点）
 *     particle poof ~ ~1 ~ 0.2 0.2 0.2 0.2 50（烟尘治疗粒子）
 *     rotated ~ -90 radiance 光辉粒子
 *   shockwave spellplane 冲击波
 *   音效 subsistence transmutate（距离 50）
 * </pre>
 * Java 化：
 * <ul>
 *   <li>瞬时完成（duration=0），无持续 tickActive</li>
 *   <li>heal = 10 + power×4（满 power 50 合计 210 HP = 105❤）</li>
 *   <li>saturation 3 tick = 原版饱和 3 等级 × 2 tick 约等满 saturation，接近原行为</li>
 * </ul>
 */
public class SubsistenceSpell extends Spell {
    public SubsistenceSpell() {
        super("subsistence", ElementType.RADIANCE.mask,
                10f, 40, 0, 0, SpellCategory.BUFF);
    }

    @Override
    public void onComplete(Player caster, Level level) {
        int finalPower = Math.min(50, getPower() + safeDataPower(caster));
        float healHp = SpellHelpers.scaledDamage(getBaseDamage(), finalPower, 4f); // 10 + p*4

        // 对施法者（以及原数据包 distance ≤3 内的玩家，这里简化为施法者本人；P3.5 还原范围）
        SpellHelpers.heal(caster, healHp);
        SpellHelpers.saturate(caster, 3, 1.0f);
        // 治疗粒子
        if (level instanceof net.minecraft.server.level.ServerLevel sl) {
            sl.sendParticles(
                    net.minecraft.core.particles.ParticleTypes.POOF,
                    caster.getX(), caster.getY() + 1, caster.getZ(),
                    50, 0.2, 0.2, 0.2, 0.2);
        }
        // 光辉粒子 + 冲击波 + 音
        SpellHelpers.playElementParticles(caster, ElementType.RADIANCE);
        if (level instanceof net.minecraft.server.level.ServerLevel sl) {
            SpellHelpers.spawnShockwaveParticles(sl, caster.position(), 3.0);
        }
        SpellHelpers.playTransmutateSound(caster);
        SpellHelpers.actionbar(caster, "spell.nyctocosm.subsistence.heal",
                "维生术：回复 " + String.format("%.1f", healHp) + " HP");
    }

    private static int safeDataPower(Player p) {
        var d = SpellHelpers.dataOf(p);
        return d == null ? 0 : d.getSpellPower();
    }
}
