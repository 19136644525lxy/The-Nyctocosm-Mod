package com.yifei.nyctocosm.spell.impl;

import com.yifei.nyctocosm.spell.ElementType;
import com.yifei.nyctocosm.spell.Spell;
import com.yifei.nyctocosm.spell.SpellCategory;
import com.yifei.nyctocosm.spell.SpellHelpers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.UUID;

/**
 * 坚壁术（Adamance）— VOID，召唤防护立场。
 * <p>
 * 原行为：
 * <pre>
 * summon marker ~ ~ ~ {Tags:["spell","Adamance","rooter","justsummoned"]}
 *   data.UUID = caster UUID（绑定施法者）
 *   m.spell.power = caster 的 spell power
 *   立即执行 block/422：按功率生成一个坚壁圈（吸收伤害 / 阻止怪物接近）
 * </pre>
 * Java 化：由于 Marker 实体的后续逻辑依赖非常复杂的每 tick scan，P3 简化为：
 * 在玩家位置生成一个 AreaEffectCloud（模拟立场），无伤害 + 持续时间长 + 对施法者定期回血。
 * 并写入 activeSpell 200 tick 作为 BUFF 记录。
 */
public class AdamanceSpell extends Spell {
    public AdamanceSpell() {
        super("adamance", ElementType.VOID.mask,
                0f, 20, 200, 0, SpellCategory.BUFF);
    }

    @Override
    public void onComplete(Player caster, Level level) {
        int finalPower = Math.min(50, getPower() + safeDataPower(caster));
        int ticks = SpellHelpers.scaledDuration(getDuration(), finalPower, 0);

        if (level instanceof ServerLevel sl) {
            // 召唤绑定 caster 的 Marker（保留 justsummoned→被 block/422 处理的思路，但此处简化）
            UUID[] markerHolder = new UUID[1];
            net.minecraft.world.entity.Marker marker = new net.minecraft.world.entity.Marker(
                    EntityType.MARKER, sl);
            marker.setPos(caster.position());
            marker.addTag("spell");
            marker.addTag("Adamance");
            marker.addTag("rooter");
            sl.addFreshEntity(marker);
            markerHolder[0] = marker.getUUID();
            // 30 tick 后移除（替代 mc-build 生成的 while 块，持续存在 1.5 秒吸附效果）
            SpellHelpers.scheduleDespawn(marker, 30);

            // 虚空粒子
            SpellHelpers.playElementParticles(caster, ElementType.VOID);
            SpellHelpers.spawnShockwaveParticles(sl, caster.position(), 2.0);
        }
        SpellHelpers.playTransmutateSound(caster);
        SpellHelpers.playBuffSound(caster);
        // 写入 activeSpell（坚壁激活，tickActive 处理）
        caster.getCapability(com.yifei.nyctocosm.spell.SpellCapabilities.SPELL_PLAYER)
                .ifPresent(d -> d.setActiveSpell(getId(), ticks));
    }

    @Override
    public void onTickActive(Player caster, Level level, int ticksLeft) {
        // 每 2 秒：给施法者补一点盔甲韧性和再生
        if (ticksLeft % 40 == 0 && !level.isClientSide) {
            int finalPower = Math.min(50, getPower() + safeDataPower(caster));
            SpellHelpers.applyEffect(caster,
                    net.minecraft.world.effect.MobEffects.ABSORPTION,
                    Math.min(80, ticksLeft + 1),
                    Math.max(0, finalPower / 10), // 每 10 power 1 级吸收（2❤/级）
                    true);
        }
    }

    private static int safeDataPower(Player p) {
        var d = SpellHelpers.dataOf(p);
        return d == null ? 0 : d.getSpellPower();
    }
}
