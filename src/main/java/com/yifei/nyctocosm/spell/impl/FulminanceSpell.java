package com.yifei.nyctocosm.spell.impl;

import com.yifei.nyctocosm.spell.ElementType;
import com.yifei.nyctocosm.spell.Spell;
import com.yifei.nyctocosm.spell.SpellCategory;
import com.yifei.nyctocosm.spell.SpellHelpers;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 * 轰雷术（Fulminance）— RADIANCE+DISSONANCE，20 tick AOE 闪电脉冲。
 * <p>
 * 原行为：
 * <pre>
 * flash particle ~ ~1 ~（瞬闪）
 * positioned ~ ~1.625、朝最近玩家方向，执行 block/530：
 *   轰雷射线 + 每 tick 冲击（20 tick 持续 DoT = 7 + power×3 damage）
 * </pre>
 * Java 化：
 * <ul>
 *   <li>先闪一下 flash 粒子</li>
 *   <li>前方 8 格 × 60° 锥范围目标：立即 7 + power×3 伤害（光辉 50% / 失谐 50%）</li>
 *   <li>20 tick active：每 5 tick 再 2 伤害 + 发光效果（模拟雷电追踪）</li>
 *   <li>召唤几道原版 LIGHTNING_BOLT（概率触发，power 越大越高）</li>
 * </ul>
 */
public class FulminanceSpell extends Spell {
    public FulminanceSpell() {
        super("fulminance", ElementType.RADIANCE.mask | ElementType.DISSONANCE.mask,
                7f, 30, 20, 0, SpellCategory.AOE);
    }

    @Override
    public void onComplete(Player caster, Level level) {
        int finalPower = Math.min(50, getPower() + safeDataPower(caster));
        float dmg = SpellHelpers.scaledDamage(getBaseDamage(), finalPower, 3f);

        // Flash + 音
        if (level instanceof ServerLevel sl) {
            sl.sendParticles(ParticleTypes.FLASH,
                    caster.getX(), caster.getY() + 1, caster.getZ(), 1, 0, 0, 0, 0);
        }
        SpellHelpers.playElementParticles(caster, ElementType.RADIANCE);
        SpellHelpers.playElementParticles(caster, ElementType.DISSONANCE);
        SpellHelpers.playAoESound(caster);

        Vec3 origin = caster.position().add(0, 1.625, 0);
        Vec3 fwd = caster.getLookAngle();
        List<LivingEntity> targets = SpellHelpers.findTargets(level, origin,
                8.0, 0.0, 15, e -> {
                    if (e instanceof Player || !e.isAttackable()) return false;
                    Vec3 to = e.position().add(0, e.getBbHeight() / 2, 0)
                            .subtract(origin).normalize();
                    return to.dot(fwd) >= 0.5; // 60° 锥
                });
        for (LivingEntity t : targets) {
            SpellHelpers.dealSpellDamage(caster, t, dmg * 0.5f, ElementType.RADIANCE);
            SpellHelpers.dealSpellDamage(caster, t, dmg * 0.5f, ElementType.DISSONANCE);
            // 模拟雷击：发光 + 眩晕（瞬间减速）
            SpellHelpers.applyEffect(t, MobEffects.GLOWING, 40, 0, true);
            SpellHelpers.applyEffect(t, MobEffects.MOVEMENT_SLOWDOWN, 20, 2, false);
        }
        // 随机召唤闪电：power 10 概率加一次，最多 3 次
        if (level instanceof ServerLevel sl) {
            int bolts = Math.min(3, (finalPower + 9) / 17);
            for (LivingEntity t : targets) {
                if (bolts-- <= 0) break;
                net.minecraft.world.entity.LightningBolt bolt =
                        net.minecraft.world.entity.EntityType.LIGHTNING_BOLT.create(sl);
                if (bolt == null) continue;
                bolt.moveTo(t.position());
                bolt.setCause(caster instanceof net.minecraft.server.level.ServerPlayer sp ? sp : null);
                sl.addFreshEntity(bolt);
            }
        }
        caster.getCapability(com.yifei.nyctocosm.spell.SpellCapabilities.SPELL_PLAYER)
                .ifPresent(d -> d.setActiveSpell(getId(), getDuration()));
        SpellHelpers.actionbar(caster, "spell.nyctocosm.fulminance.hit",
                "轰雷术命中 " + targets.size() + " 目标，" + String.format("%.1f", dmg) + " 伤害");
    }

    @Override
    public void onTickActive(Player caster, Level level, int ticksLeft) {
        if (ticksLeft % 5 != 0) return;
        int finalPower = Math.min(50, getPower() + safeDataPower(caster));
        float dot = 2 + finalPower * 0.1f;
        Vec3 origin = caster.position().add(0, 1.625, 0);
        Vec3 fwd = caster.getLookAngle();
        List<LivingEntity> targets = SpellHelpers.findTargets(level, origin,
                8.0, 0.0, 15, e -> {
                    if (e instanceof Player || !e.isAttackable()) return false;
                    Vec3 to = e.position().add(0, e.getBbHeight() / 2, 0)
                            .subtract(origin).normalize();
                    return to.dot(fwd) >= 0.5;
                });
        for (LivingEntity t : targets) {
            SpellHelpers.dealSpellDamage(caster, t, dot, ElementType.RADIANCE);
        }
    }

    private static int safeDataPower(Player p) {
        var d = SpellHelpers.dataOf(p);
        return d == null ? 0 : d.getSpellPower();
    }
}
