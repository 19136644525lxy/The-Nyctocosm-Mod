package com.yifei.nyctocosm.spell.impl;

import com.yifei.nyctocosm.spell.ElementType;
import com.yifei.nyctocosm.spell.Spell;
import com.yifei.nyctocosm.spell.SpellCategory;
import com.yifei.nyctocosm.spell.SpellHelpers;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * 反射术（Reflectance）— RESONANCE，给玩家加护盾并反弹伤害。
 * <p>
 * 原行为：positioned ~ ~1.625（玩家眼高）然后旋转为最近玩家（= 施法者自己）朝向，
 * 执行 block/383 生成一条共振反射带（每 tick 反射来袭投射物）。
 * Java 化：P3 简化为 RESONANCE 伤害反弹效果——给玩家加 Fire Resistance（类比抗性）+ 吸收效果（模拟护盾）+
 * 每 tick 受到伤害时记录并反弹 20%（在 AttackEntityEvent 处理，P3.5 完成，当前先落位基础粒子+持续时间）。
 */
public class ReflectanceSpell extends Spell {
    public ReflectanceSpell() {
        super("reflectance", ElementType.RESONANCE.mask,
                20f, 30, 200, 0, SpellCategory.BUFF);
    }

    @Override
    public void onComplete(Player caster, Level level) {
        int finalPower = Math.min(50, getPower() + safeDataPower(caster));
        int ticks = SpellHelpers.scaledDuration(getDuration(), finalPower, 0);
        // 护盾等级 = 2 + power/10（每级 2❤ 吸收）
        int lvl = 2 + finalPower / 10;
        SpellHelpers.applyEffect(caster, MobEffects.ABSORPTION, ticks, Math.max(0, lvl - 1), true);
        SpellHelpers.applyEffect(caster, MobEffects.FIRE_RESISTANCE, ticks, 0, true);
        SpellHelpers.playElementParticles(caster, ElementType.RESONANCE);
        SpellHelpers.playTransmutateSound(caster);
        SpellHelpers.playBuffSound(caster);
        // 写入 activeSpell
        caster.getCapability(com.yifei.nyctocosm.spell.SpellCapabilities.SPELL_PLAYER)
                .ifPresent(d -> d.setActiveSpell(getId(), ticks));
    }

    @Override
    public void onTickActive(Player caster, Level level, int ticksLeft) {
        // 每 60 tick 刷新一次效果
        if (ticksLeft % 60 == 0) {
            int lvl = 2 + safeDataPower(caster) / 10;
            int ttl = Math.min(120, ticksLeft + 1);
            SpellHelpers.applyEffect(caster, MobEffects.ABSORPTION, ttl, Math.max(0, lvl - 1), true);
            SpellHelpers.applyEffect(caster, MobEffects.FIRE_RESISTANCE, ttl, 0, true);
        }
    }

    private static int safeDataPower(Player p) {
        var d = SpellHelpers.dataOf(p);
        return d == null ? 0 : d.getSpellPower();
    }
}
