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
 * 禁制术（Forbiddance）— DISSONANCE，35 tick DEBUFF（减速+虚弱+禁跳跃）。
 * <p>
 * 原行为：positioned ~ ~1.625（施法者眼高）然后旋转为距离 ≤2 的最近玩家朝向，
 * 执行 block/330：每 tick 给目标注入禁跳跃/禁速。
 * Java 化：
 * <ul>
 *   <li>35 tick = 1.75 秒持续</li>
 *   <li>目标：施法者自身前方 5 格内最近的非玩家实体（或者对敌对玩家也可用，P3 暂对 5 格内敌对实体生效）</li>
 *   <li>效果：MOVEMENT_SLOWNESS 4 级 + WEAKNESS 3 级</li>
 * </ul>
 */
public class ForbiddanceSpell extends Spell {
    public ForbiddanceSpell() {
        super("forbiddance", ElementType.DISSONANCE.mask,
                0f, 10, 35, 0, SpellCategory.DEBUFF);
    }

    @Override
    public void onComplete(Player caster, Level level) {
        int finalPower = Math.min(50, getPower() + safeDataPower(caster));
        int ticks = SpellHelpers.scaledDuration(getDuration(), finalPower, 0);

        SpellHelpers.playElementParticles(caster, ElementType.DISSONANCE);
        SpellHelpers.playTransmutateSound(caster);

        // 前方 5 格，最多 3 个非玩家 LivingEntity（P3 简化）
        Vec3 origin = caster.position().add(0, 1.625, 0);
        List<LivingEntity> targets = SpellHelpers.findTargets(level, origin,
                5.0, 1.0, 3,
                e -> !(e instanceof Player) && e.isAttackable());

        for (LivingEntity t : targets) {
            // 失谐 35 tick：减速 4 级（80% 减）+ 虚弱 3 级（3×0.5 伤害减）
            SpellHelpers.applyEffect(t, MobEffects.MOVEMENT_SLOWDOWN, ticks, 3, false);
            SpellHelpers.applyEffect(t, MobEffects.WEAKNESS, ticks, 2, false);
            // 一点失谐伤害
            SpellHelpers.dealSpellDamage(caster, t, 1f, ElementType.DISSONANCE);
            SpellHelpers.actionbar(caster, "spell.nyctocosm.forbiddance.hit",
                    "禁制术命中 " + t.getName().getString());
        }

        // 写入 activeSpell：用作短效果冷却记录（onTickActive 刷新）
        if (!targets.isEmpty()) {
            caster.getCapability(com.yifei.nyctocosm.spell.SpellCapabilities.SPELL_PLAYER)
                    .ifPresent(d -> d.setActiveSpell(getId(), 20));
        }
    }

    private static int safeDataPower(Player p) {
        var d = SpellHelpers.dataOf(p);
        return d == null ? 0 : d.getSpellPower();
    }
}
