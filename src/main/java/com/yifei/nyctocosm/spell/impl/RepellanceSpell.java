package com.yifei.nyctocosm.spell.impl;

import com.yifei.nyctocosm.spell.ElementType;
import com.yifei.nyctocosm.spell.Spell;
import com.yifei.nyctocosm.spell.SpellCategory;
import com.yifei.nyctocosm.spell.SpellHelpers;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 * 斥力术（Repellance）— DISSONANCE，召唤阵风击退敌人。
 * <p>
 * 原行为：
 * <pre>
 * summon marker ~ ~1.625 ~ {Tags:["spell","Repellance","gust","gathering","justsummoned"]}
 *   将 gust 传送至眼高 ^ ^ ^3（玩家前方 3 格）
 *   spell.power = caster power
 *   PUUID0~PUUID3 绑定施法者
 *   执行 block/513：gust 每 tick 向前推进，50 tick 内击退路径上的所有 LivingEntity
 *   播放 transmutate.repellance 音效（距离 50）
 * </pre>
 * Java 化：
 * <ul>
 *   <li>Marker 复杂逐帧推进跳过，简化为：以施法者前方 6 格为扇形中心（120° 锥），距离 ≤7 的目标立即大击退 + 2 虚弱</li>
 *   <li>50 tick active：每 10 tick 再小击退一波</li>
 * </ul>
 */
public class RepellanceSpell extends Spell {
    public RepellanceSpell() {
        super("repellance", ElementType.DISSONANCE.mask,
                0f, 50, 50, 0, SpellCategory.AOE);
    }

    @Override
    public void onComplete(Player caster, Level level) {
        int finalPower = Math.min(50, getPower() + safeDataPower(caster));
        float knockBase = 0.8f + finalPower * 0.02f;

        SpellHelpers.playElementParticles(caster, ElementType.DISSONANCE);
        SpellHelpers.playAoESound(caster);

        Vec3 origin = caster.position().add(0, 1.625, 0);
        Vec3 fwd = caster.getLookAngle();

        // 召唤一个 DISSONANCE gust Marker 作为视觉锚点（P5 动画系统补完）
        if (level instanceof net.minecraft.server.level.ServerLevel sl) {
            net.minecraft.world.entity.Marker gust = new net.minecraft.world.entity.Marker(
                    EntityType.MARKER, sl);
            Vec3 gustPos = origin.add(fwd.scale(3.0));
            gust.setPos(gustPos);
            gust.addTag("spell");
            gust.addTag("Repellance");
            gust.addTag("gust");
            sl.addFreshEntity(gust);
            SpellHelpers.scheduleDespawn(gust, 50);
        }

        // 前方锥型 120°、距离 7
        List<LivingEntity> targets = SpellHelpers.findTargets(level, origin,
                7.0, 1.0, 15,
                e -> {
                    if (e instanceof Player || !e.isAttackable()) return false;
                    Vec3 to = e.position().add(0, e.getBbHeight() / 2, 0)
                            .subtract(origin).normalize();
                    return to.dot(fwd) >= -0.5; // 120° 锥
                });
        for (LivingEntity t : targets) {
            // 击退 = 从 caster 指向目标的方向 × 基础击退
            Vec3 away = t.position().subtract(caster.position()).normalize().scale(knockBase);
            away = away.add(0, 0.3, 0); // 向上抬一点
            t.setDeltaMovement(t.getDeltaMovement().add(away));
            t.hurtMarked = true;
            // 5 秒减速（被击退之后不好跑）
            SpellHelpers.applyEffect(t, MobEffects.MOVEMENT_SLOWDOWN, 100, 1, false);
            // 少量失谐伤害
            SpellHelpers.dealSpellDamage(caster, t, 1 + finalPower * 0.1f, ElementType.DISSONANCE);
        }
        caster.getCapability(com.yifei.nyctocosm.spell.SpellCapabilities.SPELL_PLAYER)
                .ifPresent(d -> d.setActiveSpell(getId(), getDuration()));
        SpellHelpers.actionbar(caster, "spell.nyctocosm.repellance.hit",
                "斥力术命中 " + targets.size() + " 目标");
    }

    @Override
    public void onTickActive(Player caster, Level level, int ticksLeft) {
        if (ticksLeft % 10 != 0) return;
        Vec3 origin = caster.position().add(0, 1.625, 0);
        Vec3 fwd = caster.getLookAngle();
        List<LivingEntity> targets = SpellHelpers.findTargets(level, origin,
                6.0, 1.0, 10, e -> {
                    if (e instanceof Player || !e.isAttackable()) return false;
                    Vec3 to = e.position().add(0, e.getBbHeight() / 2, 0)
                            .subtract(origin).normalize();
                    return to.dot(fwd) >= -0.5;
                });
        for (LivingEntity t : targets) {
            Vec3 away = t.position().subtract(caster.position()).normalize().scale(0.4);
            away = away.add(0, 0.15, 0);
            t.setDeltaMovement(t.getDeltaMovement().add(away));
            t.hurtMarked = true;
        }
    }

    private static int safeDataPower(Player p) {
        var d = SpellHelpers.dataOf(p);
        return d == null ? 0 : d.getSpellPower();
    }
}
