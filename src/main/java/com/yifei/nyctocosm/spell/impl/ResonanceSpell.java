package com.yifei.nyctocosm.spell.impl;

import com.yifei.nyctocosm.spell.ElementType;
import com.yifei.nyctocosm.spell.Spell;
import com.yifei.nyctocosm.spell.SpellCategory;
import com.yifei.nyctocosm.spell.SpellHelpers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

/**
 * 共振术（Resonance）— RESONANCE，60 伤害投射物（射线/弹道 raycast）。
 * <p>
 * 原行为：
 * <pre>
 * tagged caster（确保是施法者本人）
 * positioned ~ ~1.625 ~ positioned ^ ^ ^5 run summon marker {Tags:["spell","Resonance","shooter","justsummoned"]}
 *   → shooter marker（共振弹）在施法者前方 5 格出生
 * 执行 block/250：shooter 每 tick 按速度直线前进，碰撞 LivingEntity 造成伤害
 * 执行 block/251：共振冲击波（spellplane origin 位置，距离 ≤1）
 * </pre>
 * Java 化（简化版 raycast，不需要自定义实体）：
 * <ul>
 *   <li>施法者前方 20 格射线检测（实体优先，命中方块则截断）</li>
 *   <li>伤害 = 60 + power×3（RESONANCE 元素）</li>
 *   <li>命中后在目标位置生成 resonance 共振冲击波粒子</li>
 * </ul>
 */
public class ResonanceSpell extends Spell {
    public ResonanceSpell() {
        super("resonance", ElementType.RESONANCE.mask,
                60f, 80, 0, 0, SpellCategory.PROJECTILE);
    }

    @Override
    public void onComplete(Player caster, Level level) {
        int finalPower = Math.min(50, getPower() + safeDataPower(caster));
        float dmg = SpellHelpers.scaledDamage(getBaseDamage(), finalPower, 3f);

        SpellHelpers.playElementParticles(caster, ElementType.RESONANCE);
        SpellHelpers.playCastSound(caster);
        SpellHelpers.spawnSpellCircle(caster, 8); // CMD8 = Resonance 灵魂圈（原 block/248）

        Vec3 origin = caster.getEyePosition();
        Vec3 fwd = caster.getLookAngle();
        double maxDist = 20.0;
        HitResult hit = raycast(level, caster, origin, fwd, maxDist);

        if (hit == null || hit.getType() == HitResult.Type.MISS) {
            SpellHelpers.actionbar(caster, "spell.nyctocosm.resonance.miss", "共振术未命中");
            return;
        }

        if (hit.getType() == HitResult.Type.ENTITY && hit instanceof EntityHitResult ehr) {
            if (ehr.getEntity() instanceof net.minecraft.world.entity.LivingEntity target
                    && !(target instanceof Player)) {
                SpellHelpers.dealSpellDamage(caster, target, dmg, ElementType.RESONANCE);
                SpellHelpers.actionbar(caster, "spell.nyctocosm.resonance.hit",
                        "共振术命中 " + target.getName().getString() + "，"
                                + String.format("%.1f", dmg) + " 伤害");
                if (level instanceof net.minecraft.server.level.ServerLevel sl) {
                    SpellHelpers.spawnShockwaveParticles(sl, target.position(), 2.0);
                }
            }
        } else if (hit.getType() == HitResult.Type.BLOCK && hit instanceof BlockHitResult bhr) {
            // 命中方块：在命中点放冲击波
            if (level instanceof net.minecraft.server.level.ServerLevel sl) {
                SpellHelpers.spawnShockwaveParticles(sl,
                        Vec3.atCenterOf(bhr.getBlockPos()), 2.5);
            }
            SpellHelpers.actionbar(caster, "spell.nyctocosm.resonance.block", "共振术命中方块");
        }
    }

    /**
     * 简化射线检测：先实体（玩家前方 0.5 格起 + 20 格），再方块。
     * 若 P3.5 有自定义 projectile 实体（ResonanceProjectile），此处可替换为实体弹道。
     */
    private static HitResult raycast(Level level, Player caster, Vec3 origin, Vec3 dir, double maxDist) {
        Vec3 end = origin.add(dir.scale(maxDist));
        // 1. 实体碰撞（AABB 扫掠，取最近）
        net.minecraft.world.phys.AABB box = new net.minecraft.world.phys.AABB(origin, end).inflate(1.0);
        HitResult nearest = null;
        double nearestDistSqr = Double.MAX_VALUE;
        for (var ent : level.getEntities(caster, box,
                e -> e.isPickable() && e.isAttackable()
                        && !(e instanceof Player))) {
            var b = ent.getBoundingBox().inflate(ent.getPickRadius());
            var opt = b.clip(origin, end);
            if (opt.isPresent()) {
                double d = origin.distanceToSqr(opt.get());
                if (d < nearestDistSqr) {
                    nearestDistSqr = d;
                    nearest = new net.minecraft.world.phys.EntityHitResult(ent, opt.get());
                }
            }
        }
        // 2. 方块碰撞（原版 clip）
        BlockHitResult blockHit = level.clip(new net.minecraft.world.level.ClipContext(
                origin, end,
                net.minecraft.world.level.ClipContext.Block.COLLIDER,
                net.minecraft.world.level.ClipContext.Fluid.NONE,
                caster));
        if (blockHit != null && blockHit.getType() == HitResult.Type.BLOCK) {
            double bd = origin.distanceToSqr(blockHit.getLocation());
            if (bd < nearestDistSqr) return blockHit;
        }
        return nearest;
    }

    private static int safeDataPower(Player p) {
        var d = SpellHelpers.dataOf(p);
        return d == null ? 0 : d.getSpellPower();
    }
}
