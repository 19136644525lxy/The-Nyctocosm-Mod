package com.yifei.nyctocosm.spell.impl;

import com.yifei.nyctocosm.spell.ElementType;
import com.yifei.nyctocosm.spell.Spell;
import com.yifei.nyctocosm.spell.SpellCategory;
import com.yifei.nyctocosm.spell.SpellHelpers;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 * 透射术（Transmittance）— VOID，穿透射线。
 * <p>
 * 原行为：
 * <pre>
 * effect at @s rotated ~ 0 positioned ~ ~1 ~ run effects:shockwave/transmittance_first
 *   → 从玩家头部向前发射冲击波（第一发）
 * 按 PUUID 绑定施法者
 * for each in_plane（法术平面内）玩家 distance ≤3：
 *   执行 block/475：每 tick 向玩家朝向发射持续的透射穿透射线
 * </pre>
 * Java 化：
 * <ul>
 *   <li>第一次冲击波粒子 + 前方 30 格射线（穿透非实体方块，打第一个 LivingEntity 并穿透最多 3 个目标）</li>
 *   <li>伤害 = 15 + power×5（VOID 元素）</li>
 *   <li>穿透：对射线 AABB 内所有敌人按距离排序打伤害</li>
 * </ul>
 */
public class TransmittanceSpell extends Spell {
    public TransmittanceSpell() {
        super("transmittance", ElementType.VOID.mask,
                15f, 100, 0, 0, SpellCategory.PROJECTILE);
    }

    @Override
    public void onComplete(Player caster, Level level) {
        int finalPower = Math.min(50, getPower() + safeDataPower(caster));
        float dmg = SpellHelpers.scaledDamage(getBaseDamage(), finalPower, 5f);

        SpellHelpers.playElementParticles(caster, ElementType.VOID);
        SpellHelpers.playCastSound(caster);

        Vec3 origin = caster.getEyePosition();
        Vec3 fwd = caster.getLookAngle();
        double maxDist = 30.0;
        Vec3 end = origin.add(fwd.scale(maxDist));

        // 第一波冲击波（effects:shockwave/transmittance_first）
        if (level instanceof ServerLevel sl) {
            SpellHelpers.spawnShockwaveParticles(sl, origin.add(0, -0.5, 0), 2.0);
            // 虚空透射光束粒子
            for (int i = 0; i < 30; i++) {
                double t = (double) i / 30;
                Vec3 p = origin.add(fwd.scale(maxDist * t));
                sl.sendParticles(ParticleTypes.REVERSE_PORTAL,
                        p.x, p.y, p.z, 1, 0.05, 0.05, 0.05, 0);
            }
        }

        // 射线上所有敌人（穿透 3 个）
        var box = new net.minecraft.world.phys.AABB(origin, end).inflate(1.2);
        List<LivingEntity> onLine = level.getEntitiesOfClass(LivingEntity.class, box,
                e -> !(e instanceof Player) && e.isAttackable() && e.isAlive());
        onLine.sort((a, b) -> Double.compare(
                origin.distanceToSqr(a.position()), origin.distanceToSqr(b.position())));

        int hits = 0;
        for (LivingEntity t : onLine) {
            // 判断是否真正在射线 ±1.5 格内
            Vec3 to = t.position().subtract(origin);
            double proj = to.dot(fwd);
            if (proj < 0 || proj > maxDist) continue;
            Vec3 closest = origin.add(fwd.scale(proj));
            double perpSqr = closest.distanceToSqr(t.position().add(0, t.getBbHeight() / 2, 0));
            if (perpSqr > 2.25) continue; // 1.5²

            SpellHelpers.dealSpellDamage(caster, t, dmg, ElementType.VOID);
            hits++;
            if (hits >= 3) break;
        }
        SpellHelpers.actionbar(caster, "spell.nyctocosm.transmittance.hit",
                "透射术穿透命中 " + hits + " 目标，每发 " + String.format("%.1f", dmg) + " 伤害");
    }

    private static int safeDataPower(Player p) {
        var d = SpellHelpers.dataOf(p);
        return d == null ? 0 : d.getSpellPower();
    }
}
