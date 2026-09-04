package com.yifei.nyctocosm.spell.impl;

import com.yifei.nyctocosm.spell.ElementType;
import com.yifei.nyctocosm.spell.Spell;
import com.yifei.nyctocosm.spell.SpellCategory;
import com.yifei.nyctocosm.spell.SpellHelpers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 * 白炽术（Incandescence）— RADIANCE+DISSONANCE，AOE 点燃伤害。
 * <p>
 * 原行为：positioned ~ ~1.625（眼高）朝最近玩家方向，执行 block/439：
 * 白炽光束击中范围内的怪物，每 tick 烧 + 光闪（还有 light_candle.mcfunction 自动点燃蜡烛）。
 * Java 化：
 * <ul>
 *   <li>对施法者前方扇形 5×8 锥（距离 ≤6）目标造成 10 + power×2 直接伤害</li>
 *   <li>再加 SET_ON_FIRE 4~8 秒（按 power）</li>
 *   <li>40 tick active：每 5 tick 再 2 点火伤</li>
 *   <li>自动点燃周围蜡烛（调用轻触蜡烛方块的 setFlame 等价逻辑）</li>
 * </ul>
 */
public class IncandescenceSpell extends Spell {
    public IncandescenceSpell() {
        super("incandescence", ElementType.RADIANCE.mask | ElementType.DISSONANCE.mask,
                10f, 20, 40, 0, SpellCategory.AOE);
    }

    @Override
    public void onComplete(Player caster, Level level) {
        int finalPower = Math.min(50, getPower() + safeDataPower(caster));
        float dmg = SpellHelpers.scaledDamage(getBaseDamage(), finalPower, 2f);
        int fireSeconds = 4 + (finalPower / 10);

        SpellHelpers.playElementParticles(caster, ElementType.RADIANCE);
        SpellHelpers.playElementParticles(caster, ElementType.DISSONANCE);
        SpellHelpers.playAoESound(caster);

        Vec3 origin = caster.position().add(0, 1.625, 0);
        Vec3 fwd = caster.getLookAngle();
        // 锥型过滤：距离 ≤6 且 cos(夹角) ≥ 0.5（夹角 ≤60°）
        List<LivingEntity> targets = SpellHelpers.findTargets(level, origin,
                6.0, 0.0, 10,
                e -> {
                    if (e instanceof Player || !e.isAttackable()) return false;
                    Vec3 to = e.position().add(0, e.getBbHeight() / 2, 0).subtract(origin).normalize();
                    return to.dot(fwd) >= 0.5;
                });
        for (LivingEntity t : targets) {
            // 光辉 60% + 失谐 40%
            SpellHelpers.dealSpellDamage(caster, t, dmg * 0.6f, ElementType.RADIANCE);
            SpellHelpers.dealSpellDamage(caster, t, dmg * 0.4f, ElementType.DISSONANCE);
            t.setSecondsOnFire(fireSeconds);
        }
        // 点燃周围蜡烛（3×3）
        if (level instanceof ServerLevel sl) {
            BlockPos p0 = caster.blockPosition();
            for (int dx = -3; dx <= 3; dx++)
                for (int dz = -3; dz <= 3; dz++) {
                    BlockPos pos = p0.offset(dx, 0, dz);
                    BlockState state = sl.getBlockState(pos);
                    if (state.getBlock() instanceof net.minecraft.world.level.block.CandleBlock
                            && Boolean.FALSE.equals(state.getValue(net.minecraft.world.level.block.CandleBlock.LIT))) {
                        sl.setBlock(pos,
                                state.setValue(net.minecraft.world.level.block.CandleBlock.LIT, true), 3);
                        sl.sendParticles(ParticleTypes.FLAME,
                                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                                1, 0, 0.1, 0, 0);
                    }
                }
        }
        // 写入 active
        caster.getCapability(com.yifei.nyctocosm.spell.SpellCapabilities.SPELL_PLAYER)
                .ifPresent(d -> d.setActiveSpell(getId(), getDuration()));
        SpellHelpers.actionbar(caster, "spell.nyctocosm.incandescence.hit",
                "白炽术命中 " + targets.size() + " 目标，已点燃");
    }

    @Override
    public void onTickActive(Player caster, Level level, int ticksLeft) {
        if (ticksLeft % 5 != 0) return;
        int finalPower = Math.min(50, getPower() + safeDataPower(caster));
        float dot = 2 + finalPower * 0.1f;
        Vec3 origin = caster.position().add(0, 1.625, 0);
        Vec3 fwd = caster.getLookAngle();
        List<LivingEntity> targets = SpellHelpers.findTargets(level, origin,
                6.0, 0.0, 10, e -> {
                    if (e instanceof Player || !e.isAttackable()) return false;
                    Vec3 to = e.position().add(0, e.getBbHeight() / 2, 0).subtract(origin).normalize();
                    return to.dot(fwd) >= 0.5;
                });
        for (LivingEntity t : targets) {
            SpellHelpers.dealSpellDamage(caster, t, dot, ElementType.RADIANCE);
            t.setSecondsOnFire(Math.max(t.getRemainingFireTicks() / 20, 2));
        }
    }

    private static int safeDataPower(Player p) {
        var d = SpellHelpers.dataOf(p);
        return d == null ? 0 : d.getSpellPower();
    }
}
