package com.yifei.nyctocosm.spell.impl;

import com.yifei.nyctocosm.spell.ElementType;
import com.yifei.nyctocosm.spell.Spell;
import com.yifei.nyctocosm.spell.SpellCategory;
import com.yifei.nyctocosm.spell.SpellHelpers;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * 消逝术（Evanescence）— RESONANCE+VOID，短时间无敌/消失。
 * <p>
 * 原行为（100 tick 吟唱后 transmutate）：
 * <pre>
 * predicate !emissary:in_domain（不在使者领域内才能用）时执行 block/278：
 *   → 让玩家短暂「消失」（视觉隐藏 + 穿透 + 移速加成）
 * </pre>
 * Java 化：
 * <ul>
 *   <li>若玩家带 tag「in_emissary_domain」（P7 世界生成后写入）则取消施放并 Actionbar 提示</li>
 *   <li>给玩家：INVISIBILITY（隐身 200 tick）+ SPEED 3 级 + RESISTANCE 3 级</li>
 *   <li>用 RESONANCE + VOID 双元素粒子</li>
 * </ul>
 */
public class EvanescenceSpell extends Spell {
    public EvanescenceSpell() {
        super("evanescence", ElementType.RESONANCE.mask | ElementType.VOID.mask,
                0f, 100, 0, 0, SpellCategory.UTILITY);
    }

    @Override
    public boolean canCast(Player caster, Level level) {
        if (caster.getTags().contains("in_emissary_domain")) {
            SpellHelpers.actionbar(caster, "spell.nyctocosm.evanescence.domain_blocked",
                    "消逝术在使者领域内无法使用");
            return false;
        }
        return true;
    }

    @Override
    public void onComplete(Player caster, Level level) {
        int finalPower = Math.min(50, getPower() + safeDataPower(caster));
        int ticks = 200 + finalPower * 10; // 10~20 秒

        SpellHelpers.playElementParticles(caster, ElementType.RESONANCE);
        SpellHelpers.playElementParticles(caster, ElementType.VOID);
        SpellHelpers.playTransmutateSound(caster);

        // 隐身 + 速度 + 抗性
        caster.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, ticks, 0, false, false));
        SpellHelpers.applyEffect(caster, MobEffects.MOVEMENT_SPEED, ticks, 2, false); // 速度 3 级（+60%）
        SpellHelpers.applyEffect(caster, MobEffects.DAMAGE_RESISTANCE, ticks, 2, true);
        // 小量夜视（防止玩家自己看不见路）
        SpellHelpers.applyEffect(caster, MobEffects.NIGHT_VISION, ticks, 0, true);
        // 消失粒子
        if (level instanceof ServerLevel sl) {
            for (int i = 0; i < 80; i++) {
                double dx = (sl.getRandom().nextDouble() - 0.5) * 1.2;
                double dy = sl.getRandom().nextDouble() * 2.0;
                double dz = (sl.getRandom().nextDouble() - 0.5) * 1.2;
                sl.sendParticles(ParticleTypes.POOF,
                        caster.getX() + dx, caster.getY() + dy, caster.getZ() + dz,
                        1, 0, 0.01, 0, 0);
            }
        }
        SpellHelpers.actionbar(caster, "spell.nyctocosm.evanescence.start",
                "消逝术已生效（" + ticks / 20 + " 秒）");
    }

    private static int safeDataPower(Player p) {
        var d = SpellHelpers.dataOf(p);
        return d == null ? 0 : d.getSpellPower();
    }
}
