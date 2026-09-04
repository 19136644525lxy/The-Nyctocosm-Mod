package com.yifei.nyctocosm.spell;

import com.yifei.nyctocosm.spell.data.ISpellPlayerData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * 法术抽象基类（单例，无玩家状态）。
 * <p>
 * 设计原则（SRP + OCP）：
 * <ul>
 *   <li>每个法术是一个不可变单例（由 {@link SpellRegistry} 持有），不保存玩家状态。</li>
 *   <li>玩家相关状态（吟唱 tick、激活持续时间、法术强度）全部放在 {@link ISpellPlayerData} 中。</li>
 *   <li>子类只需要 override 对应的生命周期钩子，默认空实现方便快速扩展。</li>
 * </ul>
 *
 * 生命周期（由 {@link com.yifei.nyctocosm.spell.SpellCastingManager} 驱动）：
 * <pre>
 *  玩家触发（戒指+蜡烛）
 *    → canCast() 返回 true
 *    → onStartCast()          （立刻播放 CAST 动画 / 消耗物品）
 *    → onTickCasting() N 次   （每 tick 推进 castTime，期间可做粒子/音效提示）
 *    → onComplete()           （吟唱完成 → 触发实际效果；持续法术则进入 tickActive）
 *  若 duration > 0：
 *    → onTickActive() M 次    （每 tick 执行持续效果）
 *    → onExpire()             （到期清理）
 *  若中途玩家移动/取消：
 *    → cancelCasting()        （onCancelled 钩子 + FIZZLE 动画）
 * </pre>
 */
public abstract class Spell {

    /** 法术 ID（小写英文，作为注册 key 与翻译 key 前缀） */
    private final String id;
    /** 元素掩码（多个元素用 | 组合，对照 {@link ElementType#mask}） */
    private final int elementMask;
    /** 基础伤害（0 = 不直接造成伤害） */
    private final float baseDamage;
    /** 吟唱时间（tick，20 tick = 1 秒） */
    private final int castTime;
    /** 持续时间（tick；0 = 无持续效果，只在 onComplete 触发一次） */
    private final int duration;
    /** 基础法术强度加成（原 s.power，上限 50） */
    private final int power;
    /** 分类 */
    private final SpellCategory category;

    protected Spell(String id, int elementMask, float baseDamage,
                    int castTime, int duration, int power, SpellCategory category) {
        this.id = id;
        this.elementMask = elementMask;
        this.baseDamage = baseDamage;
        this.castTime = castTime;
        this.duration = duration;
        this.power = Math.min(power, 50); // 与原数据包一致，power 上限 50
        this.category = category;
    }

    // ========== 基本属性 ==========

    public String getId() { return id; }
    public int getElementMask() { return elementMask; }
    public float getBaseDamage() { return baseDamage; }
    public int getCastTime() { return castTime; }
    public int getDuration() { return duration; }
    public int getPower() { return power; }
    public SpellCategory getCategory() { return category; }

    /** 是否包含指定元素 */
    public boolean hasElement(ElementType type) {
        return (elementMask & type.mask) != 0;
    }

    /** 翻译 key：spell.nyctocosm.&lt;id&gt; */
    public String getTranslationKey() {
        return "spell.nyctocosm." + id;
    }

    /** 获取显示文本（需要语言文件配合，未翻译时回退为英文 ID） */
    public Component getDisplayName() {
        return Component.translatable(getTranslationKey(), id);
    }

    // ========== 生命周期钩子（子类按需 override）==========

    /**
     * 能否开始吟唱（默认 true，子类可覆盖：检查材料、蜡烛、饰品容量、冷却等）。
     * 失败时返回 false，调度器播放 FIZZLE 但不进入吟唱。
     */
    public boolean canCast(Player caster, Level level) {
        return true;
    }

    /** 开始吟唱（ticksLeft == castTime），默认空实现。 */
    public void onStartCast(Player caster, Level level) {}

    /**
     * 每 tick 吟唱中调用（ticksLeft 从 castTime-1 递减到 1）。
     * 可做粒子/音效提示，或校验位置是否仍有效（失败时返回 false 中断吟唱）。
     *
     * @return true 继续吟唱，false 取消吟唱（触发 onCancelled）
     */
    public boolean onTickCasting(Player caster, Level level, int ticksLeft) {
        return true;
    }

    /** 吟唱完成（ticksLeft == 0），子类在此实现法术实际效果。 */
    public void onComplete(Player caster, Level level) {}

    /**
     * 持续法术每 tick 激活中调用（仅当 duration &gt; 0）。
     *
     * @param ticksLeft 剩余激活 tick（从 duration-1 递减到 0，ticksLeft==0 后会触发 onExpire）
     */
    public void onTickActive(Player caster, Level level, int ticksLeft) {}

    /** 持续法术到期（或被手动移除），默认空实现。 */
    public void onExpire(Player caster, Level level) {}

    /** 吟唱被取消（玩家移动/切物品/死亡/右键中断等），默认空实现。 */
    public void onCancelled(Player caster, Level level) {}

    @Override
    public String toString() {
        return "Spell{" + id + "}";
    }
}
