package com.yifei.nyctocosm.spell.impl;

import com.yifei.nyctocosm.spell.ElementType;
import com.yifei.nyctocosm.spell.Spell;
import com.yifei.nyctocosm.spell.SpellCategory;
import com.yifei.nyctocosm.spell.SpellHelpers;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * 光辉术（Luminescence）— RADIANCE，持续 BUFF。
 * <p>
 * 原数据包行为：
 * <ol>
 *   <li>生成 spellcircle（ArmorStand CMD7，持续 20 tick 自动移除）</li>
 *   <li>播放 transmutate.luminescence 音效</li>
 *   <li>距离 ≤1 的 in_plane 玩家（施法者自身）：
 *     <ul>
 *       <li>effects:elements/radiance/transmutate/play 光辉粒子（玩家头顶 ~-90° 视角）</li>
 *       <li>tag @s add luminous</li>
 *       <li>luminoustimer = 6000 + power × 600 tick（5 分钟至 35 分钟）</li>
 *     </ul>
 *   </li>
 * </ol>
 * Java 化映射：
 * <ul>
 *   <li>发光效果：用原版 {@link MobEffects#GLOWING} + 调亮等级模拟 luminous tag</li>
 *   <li>持续 tick 用 activeSpell 注册表自带的持续时间（6000 + power*600）</li>
 * </ul>
 */
public class LuminescenceSpell extends Spell {
    public LuminescenceSpell() {
        super("luminescence", ElementType.RADIANCE.mask,
                0f, 0, 6000, 0, SpellCategory.BUFF);
    }

    @Override
    public void onComplete(Player caster, Level level) {
        // 1. 灵魂圈 ArmorStand（CMD7）
        SpellHelpers.spawnSpellCircle(caster, 7);
        // 2. 完成音（后续换自定义 nyctocosm:spells.transmutate.luminescence）
        SpellHelpers.playTransmutateSound(caster);
        // 3. 光辉粒子
        SpellHelpers.playElementParticles(caster, ElementType.RADIANCE);
        SpellHelpers.playBuffSound(caster);
        // 4. 给自身加 Glowing 效果（代替 luminous tag + luminoustimer）
        int power = getPower();
        // 与 Capability 系统的 getPower() 叠加，这里默认用法术自带属性，后续在 Capability 里记录 spellPower
        int finalPower = power;
        com.yifei.nyctocosm.spell.data.ISpellPlayerData data = SpellHelpers.dataOf(caster);
        if (data != null) finalPower = Math.min(50, power + data.getSpellPower());
        int ticks = SpellHelpers.scaledDuration(getDuration(), finalPower, 600);
        SpellHelpers.applyEffect(caster, MobEffects.GLOWING, ticks, 0, true);
        // 5. 写入 activeSpells（当 spellsCast 统计结束后，调度器会把 duration 写入）
        caster.getCapability(com.yifei.nyctocosm.spell.SpellCapabilities.SPELL_PLAYER)
                .ifPresent(d -> d.setActiveSpell(getId(), ticks));
    }

    @Override
    public void onTickActive(Player caster, Level level, int ticksLeft) {
        // 每 2 秒（40 tick）刷新一下发光效果，保证不会因为时长衰减中途中断
        if (ticksLeft % 40 == 0) {
            SpellHelpers.applyEffect(caster, MobEffects.GLOWING, Math.min(80, ticksLeft + 1), 0, true);
        }
    }

    @Override
    public void onExpire(Player caster, Level level) {
        // 发光效果在到期后自动消失，无需手动
        SpellHelpers.actionbar(caster, "spell.nyctocosm.luminescence.expire", "光辉术结束");
    }
}
