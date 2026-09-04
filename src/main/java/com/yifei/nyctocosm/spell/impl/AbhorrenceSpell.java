package com.yifei.nyctocosm.spell.impl;

import com.yifei.nyctocosm.spell.ElementType;
import com.yifei.nyctocosm.spell.Spell;
import com.yifei.nyctocosm.spell.SpellCategory;
import com.yifei.nyctocosm.spell.SpellHelpers;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 * 憎恶术（Abhorrence）— NIGHTMARE，瞬伤 15 + 30 tick 持续 DoT + 中毒。
 * <p>
 * 原行为：positioned ~ ~1.625 玩家眼高，朝向最近玩家（施法者），执行 block/312：
 * 给最近目标加 30 tick 持续（每秒 2 伤害）。
 * Java 化：
 * <ul>
 *   <li>对前方 6 格内最近的 2 个 LivingEntity 造成 15 + power×3 直接伤害（梦魇元素）</li>
 *   <li>再加 POISON 2 级 + WITHER 1 级 = 持续 DoT 30 tick</li>
 * </ul>
 */
public class AbhorrenceSpell extends Spell {
    public AbhorrenceSpell() {
        super("abhorrence", ElementType.NIGHTMARE.mask,
                15f, 40, 30, 0, SpellCategory.DEBUFF);
    }

    @Override
    public void onComplete(Player caster, Level level) {
        int finalPower = Math.min(50, getPower() + safeDataPower(caster));
        float dmg = SpellHelpers.scaledDamage(getBaseDamage(), finalPower, 3f);
        int ticks = SpellHelpers.scaledDuration(getDuration(), finalPower, 0);

        SpellHelpers.playElementParticles(caster, ElementType.NIGHTMARE);
        SpellHelpers.playTransmutateSound(caster);

        Vec3 origin = caster.position().add(0, 1.625, 0);
        List<LivingEntity> targets = SpellHelpers.findTargets(level, origin,
                6.0, 0.0, 2,
                e -> !(e instanceof Player) && e.isAttackable());

        for (LivingEntity t : targets) {
            SpellHelpers.dealSpellDamage(caster, t, dmg, ElementType.NIGHTMARE);
            t.addEffect(new MobEffectInstance(MobEffects.POISON, ticks, 1, false, true));
            t.addEffect(new MobEffectInstance(MobEffects.WITHER, ticks, 0, false, true));
            SpellHelpers.actionbar(caster, "spell.nyctocosm.abhorrence.hit",
                    "憎恶术命中 " + t.getName().getString() + "，" + String.format("%.1f", dmg) + " 伤害");
        }
    }

    private static int safeDataPower(Player p) {
        var d = SpellHelpers.dataOf(p);
        return d == null ? 0 : d.getSpellPower();
    }
}
