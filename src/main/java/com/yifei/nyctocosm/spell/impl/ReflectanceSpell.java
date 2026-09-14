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
 * Java 化（现状）：给施法者 ABSORPTION（护盾，2+power/10 级）+ FIRE_RESISTANCE，持续 200t（+power 加成）。
 * <p>
 * 注意：原 block/383 的「共振反射带每 tick 反弹来袭投射物（伤害 20%）」机制（反射器 reflector）
 * 目前<b>尚未 Java 化</b>——原反射器为纯数据包对象（spells 命名空间），本注释曾错误声称
 * "P3.5 已完成反弹 20%"；实际反弹还原列入法术验收阶段（P11）对照原包逐项实现。
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
