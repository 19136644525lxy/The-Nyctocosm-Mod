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
 * 浸没术（Submergence）— DREAM+VOID，8 + power×4 伤害 + 把地面目标拖入地下（窒息模拟）。
 * <p>
 * 原行为：
 * <pre>
 *   damage = Submergence s.damage(8) + power×4  → 对 LivingEntity 造成 dream+void 复合伤害
 *   以施法者为中心，#nyctocosm:livingthings（不含 immoveable/dais，OnGround:1b），
 *     distance=3..7，limit=6，sort=nearest → 最多 6 个目标
 *   每目标执行 block/403：标记 tag=s.submerging（地下状态）+ 减速 200 tick + 窒息
 *   播放 transmutate.submergence 音效（距离 50）
 * </pre>
 * Java 化：
 * <ul>
 *   <li>目标距离 3~7，最多 6 个，必须 onGround</li>
 *   <li>伤害 = 8 + power×4，主要用 VOID 元素（次要 DREAM 用同一 DamageSource，后续可分两次伤害）</li>
 *   <li>效果：MOVEMENT_SLOWNESS 5 级 + DIG_SPEED 削弱（用 mining fatigue）模拟「地下」</li>
 * </ul>
 */
public class SubmergenceSpell extends Spell {
    public SubmergenceSpell() {
        super("submergence", ElementType.DREAM.mask | ElementType.VOID.mask,
                8f, 80, 0, 0, SpellCategory.DEBUFF);
    }

    @Override
    public void onComplete(Player caster, Level level) {
        int finalPower = Math.min(50, getPower() + safeDataPower(caster));
        float dmg = SpellHelpers.scaledDamage(getBaseDamage(), finalPower, 4f);

        SpellHelpers.playElementParticles(caster, ElementType.VOID);
        SpellHelpers.playElementParticles(caster, ElementType.DREAM);
        SpellHelpers.playTransmutateSound(caster);

        Vec3 origin = caster.position();
        List<LivingEntity> targets = SpellHelpers.findTargets(level, origin,
                7.0, 3.0, 6,
                e -> !(e instanceof Player) && e.onGround() && e.isAttackable());

        for (LivingEntity t : targets) {
            // DREAM+VOID：先造成 DREAM 再造成 VOID，保证两个 DamageType 都计入统计
            SpellHelpers.dealSpellDamage(caster, t, dmg * 0.3f, ElementType.DREAM);
            SpellHelpers.dealSpellDamage(caster, t, dmg * 0.7f, ElementType.VOID);
            // 地下效果：减速 5 级 200 tick + 挖掘疲劳 3 级 200 tick + 黑暗（失明）100 tick
            SpellHelpers.applyEffect(t, MobEffects.MOVEMENT_SLOWDOWN, 200, 4, false);
            SpellHelpers.applyEffect(t, MobEffects.DIG_SLOWDOWN, 200, 2, false);
            SpellHelpers.applyEffect(t, MobEffects.BLINDNESS, 100, 0, false);
            // 地下粒子：drip 粒子
            if (level instanceof ServerLevel sl) {
                sl.sendParticles(ParticleTypes.DRIPPING_OBSIDIAN_TEAR,
                        t.getX(), t.getY() + 0.5, t.getZ(),
                        20, 0.3, 0.5, 0.3, 0);
            }
            SpellHelpers.actionbar(caster, "spell.nyctocosm.submergence.hit",
                    "浸没术命中 " + t.getName().getString() + "，" + String.format("%.1f", dmg) + " 伤害");
        }
    }

    private static int safeDataPower(Player p) {
        var d = SpellHelpers.dataOf(p);
        return d == null ? 0 : d.getSpellPower();
    }
}
