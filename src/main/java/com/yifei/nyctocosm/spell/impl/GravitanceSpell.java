package com.yifei.nyctocosm.spell.impl;

import com.yifei.nyctocosm.spell.ElementType;
import com.yifei.nyctocosm.spell.Spell;
import com.yifei.nyctocosm.spell.SpellCategory;
import com.yifei.nyctocosm.spell.SpellHelpers;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 * 引力术（Gravitance）— VOID，200 tick 持续 AOE。
 * <p>
 * 原行为：positioned ~ ~1.625（眼高）、朝向最近玩家方向，执行 block/348：
 * 生成引力吸心球（每 tick 把周围怪物吸入中心，造成 20 + power×2 伤害）。
 * Java 化：
 * <ul>
 *   <li>60 tick（3 秒）吟唱后（构造 castTime=60，与元数据一致），对施法者前方 6 格半径 AOE 范围目标造成 20 + power×2 伤害</li>
 *   <li>给目标加 LEVITATION（浮升）2 秒 + 减速 3 级 10 秒（模拟「吸进去无法挣脱」效果）</li>
 *   <li>200 tick active：每 10 tick 在中心再造成一次小范围拉拽（伤害较小）</li>
 * </ul>
 */
public class GravitanceSpell extends Spell {
    public GravitanceSpell() {
        super("gravitance", ElementType.VOID.mask,
                20f, 60, 200, 0, SpellCategory.AOE);
    }

    @Override
    public void onComplete(Player caster, Level level) {
        int finalPower = Math.min(50, getPower() + safeDataPower(caster));
        float dmg = SpellHelpers.scaledDamage(getBaseDamage(), finalPower, 2f);
        int ticks = getDuration();

        SpellHelpers.playElementParticles(caster, ElementType.VOID);
        SpellHelpers.playAoESound(caster);

        // 中心 = 施法者前方 3 格
        Vec3 center = caster.position().add(caster.getLookAngle().scale(3.0));
        List<LivingEntity> targets = SpellHelpers.findTargets(level, center,
                6.0, 1.0, 20,
                e -> !(e instanceof Player) && e.isAttackable());
        for (LivingEntity t : targets) {
            SpellHelpers.dealSpellDamage(caster, t, dmg, ElementType.VOID);
            SpellHelpers.applyEffect(t, MobEffects.LEVITATION, 40, 1, false);
            SpellHelpers.applyEffect(t, MobEffects.MOVEMENT_SLOWDOWN, 200, 2, false);
            // 拉拽：朝中心推
            Vec3 pull = center.subtract(t.position()).normalize().scale(0.6);
            t.setDeltaMovement(t.getDeltaMovement().add(pull));
            t.hurtMarked = true;
        }
        // 写入 activeSpell，持续期间每 10 tick 再抽一次
        caster.getCapability(com.yifei.nyctocosm.spell.SpellCapabilities.SPELL_PLAYER)
                .ifPresent(d -> d.setActiveSpell(getId(), ticks));
        SpellHelpers.actionbar(caster, "spell.nyctocosm.gravitance.pull",
                "引力术已部署，命中 " + targets.size() + " 目标");
    }

    @Override
    public void onTickActive(Player caster, Level level, int ticksLeft) {
        // 每 10 tick（0.5 秒）拉一下中心 4 格内目标
        if (ticksLeft % 10 != 0) return;
        int finalPower = Math.min(50, getPower() + safeDataPower(caster));
        float dotDmg = 2 + finalPower * 0.1f;
        Vec3 center = caster.position().add(caster.getLookAngle().scale(3.0));
        List<LivingEntity> targets = SpellHelpers.findTargets(level, center,
                4.0, 0.0, 10,
                e -> !(e instanceof Player) && e.isAttackable());
        for (LivingEntity t : targets) {
            SpellHelpers.dealSpellDamage(caster, t, dotDmg, ElementType.VOID);
            Vec3 pull = center.subtract(t.position()).normalize().scale(0.25);
            t.setDeltaMovement(t.getDeltaMovement().add(pull));
            t.hurtMarked = true;
        }
    }

    private static int safeDataPower(Player p) {
        var d = SpellHelpers.dataOf(p);
        return d == null ? 0 : d.getSpellPower();
    }
}
