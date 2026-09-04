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
 * 抗性术（Resistance）— RADIANCE，持续抗性 BUFF。
 * <p>
 * 原行为：
 * <pre>
 * 距离 ≤2 内玩家：
 *   radiance 光辉粒子（rotated ~ -90）
 *   s.resistance_timer = 250 + power×100 tick
 *   tag @s add resistant
 *   particle crit ~ ~1 ~ 0.2 0.3 0.2 0.1 100（暴击粒子 = 神圣护盾感）
 * 音效：transmutate.resistance（距离 50）
 * </pre>
 * Java 化：用原版 DAMAGE_RESISTANT = 抗性提升，等级 1（20% 减伤，接近 original resistant tag），250 + power×100 tick。
 */
public class ResistanceSpell extends Spell {
    public ResistanceSpell() {
        super("resistance", ElementType.RADIANCE.mask,
                0f, 40, 250, 0, SpellCategory.BUFF);
    }

    @Override
    public void onComplete(Player caster, Level level) {
        int finalPower = Math.min(50, getPower() + safeDataPower(caster));
        int ticks = SpellHelpers.scaledDuration(getDuration(), finalPower, 100);

        SpellHelpers.applyEffect(caster, MobEffects.DAMAGE_RESISTANCE, ticks, 0, false);
        SpellHelpers.playElementParticles(caster, ElementType.RADIANCE);
        if (level instanceof ServerLevel sl) {
            sl.sendParticles(ParticleTypes.CRIT,
                    caster.getX(), caster.getY() + 1, caster.getZ(),
                    100, 0.2, 0.3, 0.2, 0.1);
        }
        SpellHelpers.playTransmutateSound(caster);
        SpellHelpers.playBuffSound(caster);
        // 持续时间记入 active
        caster.getCapability(com.yifei.nyctocosm.spell.SpellCapabilities.SPELL_PLAYER)
                .ifPresent(d -> d.setActiveSpell(getId(), ticks));
    }

    @Override
    public void onTickActive(Player caster, Level level, int ticksLeft) {
        if (ticksLeft % 40 == 0) {
            SpellHelpers.applyEffect(caster, MobEffects.DAMAGE_RESISTANCE,
                    Math.min(80, ticksLeft + 1), 0, false);
        }
    }

    private static int safeDataPower(Player p) {
        var d = SpellHelpers.dataOf(p);
        return d == null ? 0 : d.getSpellPower();
    }
}
