package com.yifei.nyctocosm.spell.impl;

import com.yifei.nyctocosm.spell.ElementType;
import com.yifei.nyctocosm.spell.Spell;
import com.yifei.nyctocosm.spell.SpellCategory;
import com.yifei.nyctocosm.spell.SpellHelpers;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * 空无术（Vacance）— RADIANCE，无敌 / 清除负面效果。
 * <p>
 * 原行为：
 * <pre>
 * summon marker ~ ~ ~ {Tags:["spell","Vacance","bubble","justsummoned"]}
 *   m.spell.power = caster power
 *   PUUID0~PUUID3 绑定施法者
 *   执行 block/496：生成一个泡泡（每 tick 给玩家刷无伤害/清状态）
 *   播放 vacance transmutate 音效（距离 50）
 * </pre>
 * Java 化：
 * <ul>
 *   <li>100 tick 持续时间 = 5 秒无敌</li>
 *   <li>onComplete：给玩家清所有负面 DEBUFF，并在 100 tick 内每 tick 尝试恢复（效果由 tickActive 维持）</li>
 *   <li>用吸收（ABSORPTION 4❤ = 2 级）+ 抗性 5（减伤 100%）模拟无敌，100 tick 到期自动失效</li>
 * </ul>
 */
public class VacanceSpell extends Spell {
    public VacanceSpell() {
        super("vacance", ElementType.RADIANCE.mask,
                0f, 20, 100, 0, SpellCategory.UTILITY);
    }

    @Override
    public void onComplete(Player caster, Level level) {
        int finalPower = Math.min(50, getPower() + safeDataPower(caster));
        int ticks = SpellHelpers.scaledDuration(getDuration(), finalPower, 0);

        // 清除所有负面效果
        caster.removeAllEffects();
        // 模拟无敌：抗性提升等级 4（MC 最高级 = 4，减伤 80%；再叠加吸收 4❤ 近似无敌）
        SpellHelpers.applyEffect(caster, MobEffects.DAMAGE_RESISTANCE, ticks, 4, true);
        SpellHelpers.applyEffect(caster, MobEffects.ABSORPTION, ticks, 1, true); // 2 级 = 4❤
        // 气泡粒子 + 光辉粒子
        SpellHelpers.playElementParticles(caster, ElementType.RADIANCE);
        if (level instanceof ServerLevel sl) {
            for (int i = 0; i < 30; i++) {
                double dx = (sl.getRandom().nextDouble() - 0.5) * 1.0;
                double dy = sl.getRandom().nextDouble() * 2.0;
                double dz = (sl.getRandom().nextDouble() - 0.5) * 1.0;
                sl.sendParticles(ParticleTypes.BUBBLE,
                        caster.getX() + dx, caster.getY() + dy, caster.getZ() + dz,
                        1, 0, 0.05, 0, 0);
            }
        }
        SpellHelpers.playTransmutateSound(caster);
        SpellHelpers.playBuffSound(caster);
        // 写入 activeSpell
        caster.getCapability(com.yifei.nyctocosm.spell.SpellCapabilities.SPELL_PLAYER)
                .ifPresent(d -> d.setActiveSpell(getId(), ticks));
    }

    @Override
    public void onTickActive(Player caster, Level level, int ticksLeft) {
        // 每 40 tick 刷新一次抗性吸收
        if (ticksLeft % 40 == 0) {
            int ttl = Math.min(80, ticksLeft + 1);
            SpellHelpers.applyEffect(caster, MobEffects.DAMAGE_RESISTANCE, ttl, 4, true);
            SpellHelpers.applyEffect(caster, MobEffects.ABSORPTION, ttl, 1, true);
        }
    }

    private static int safeDataPower(Player p) {
        var d = SpellHelpers.dataOf(p);
        return d == null ? 0 : d.getSpellPower();
    }
}
