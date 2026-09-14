package com.yifei.nyctocosm.spell.data;

import com.yifei.nyctocosm.spell.ElementType;
import com.yifei.nyctocosm.spell.Spell;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.UUID;

/**
 * 玩家法术数据 Capability 接口。
 * <p>
 * 对应原 scoreboard 中 m.spell.* / m.hypertrace.* / s.* 等玩家持久化状态。
 * 所有写入仅限服务端；需要同步到客户端的字段通过网络包推送（P2-5/P8 实现）。
 */
public interface ISpellPlayerData {

    // ========== 吟唱状态 ==========

    /** 当前正在吟唱的法术 ID（null = 不在吟唱） */
    @Nullable
    String getCurrentSpellId();

    /** 获取当前 Spell 实例（通过 ID 查表；未注册或 null 时返回 null） */
    @Nullable
    default Spell getCurrentSpell() {
        String id = getCurrentSpellId();
        return id == null ? null : com.yifei.nyctocosm.spell.SpellRegistry.byId(id);
    }

    /** 吟唱剩余 tick（castTime 递减到 0 即完成） */
    int getCastTicksLeft();

    /** 是否正在吟唱（{@link #getCastTicksLeft()} &gt; 0） */
    boolean isCasting();

    /**
     * 开始吟唱一个法术（由 SpellCastingManager 调用，会覆盖当前正在吟唱的法术并取消它）。
     *
     * @param spellId   法术 ID
     * @param totalTicks 总吟唱 tick（通常 = spell.getCastTime()）
     */
    void startCasting(String spellId, int totalTicks);

    /** 手动取消当前吟唱（触发 Spell.onCancelled 钩子由调度器负责） */
    void cancelCasting();

    /**
     * 推进吟唱 tick（每服务端 playerTick 调用一次）。
     *
     * @return 推进后的剩余 tick；0 表示本次推进刚好吟唱结束
     */
    int decrementCastTick();

    // ========== 法术属性（对应 m.spell.power / m.global.tracechance.base 等）==========

    /** 获取基础追踪概率（百分比 0~100+，原 m.global.tracechance.base） */
    int getTraceChanceBase();

    void setTraceChanceBase(int v);

    /** 法术强度加成（上限 50，原 m.spell.power；运行期由 recalcSpellPower 从元素统计求和刷新） */
    int getSpellPower();

    void setSpellPower(int v);

    // ========== 元素属性（对应原 magic 命名空间 m.<element>.power）==========

    /**
     * 按元素维度的法术强度（6 元素数组，index = Element.index-1，原 m.&lt;element&gt;.power）。
     * 修复 #4：此前只有全局 spellPower 且无任何运行期来源（仅 PlayerEvent.Clone 复制），
     * 法术强度恒为 0、scaledDamage/scaledDuration 全部退化为基础值。
     * 本数组即原 magic 属性统计的 Java 落点——默认全 0（=原包无任何加成时的状态），
     * 数值来源（圣物/击杀/装备加成）由后续 magic 属性系统 Java 化时写入。
     */
    int[] getElementPower();

    /** 读取某元素法术强度（默认 0） */
    default int getElementPower(ElementType type) {
        return getElementPower()[type.index - 1];
    }

    /** 覆盖某元素法术强度（钳制 0~50） */
    default void setElementPower(ElementType type, int value) {
        getElementPower()[type.index - 1] = Math.max(0, Math.min(50, value));
    }

    /** 累加某元素法术强度（钳制 0~50） */
    default void addElementPower(ElementType type, int delta) {
        getElementPower()[type.index - 1] =
                Math.max(0, Math.min(50, getElementPower()[type.index - 1] + delta));
    }

    /** 按元素维度统计：造成的总伤害（6 元素数组，index=Element.index-1） */
    long[] getDamageDealt();

    /** 按元素维度统计：击杀数（6 元素数组） */
    int[] getKills();

    /** 按元素维度统计：施法次数（6 元素数组） */
    int[] getSpellsCast();

    /** 便捷方法：按元素累加伤害（自动同步统计） */
    default void addDamageDealt(ElementType type, float dmg) {
        if (dmg <= 0) return;
        long[] arr = getDamageDealt();
        arr[type.index - 1] += (long) (dmg * 1000L);
    }

    default void addKill(ElementType type) { getKills()[type.index - 1]++; }

    default void addSpellCast(ElementType type) { getSpellsCast()[type.index - 1]++; }

    // ========== 持续法术（BUFF/DEBUFF）==========

    /** 激活中的持续法术（spellId → 剩余 tick） */
    Map<String, Integer> getActiveSpells();

    /** 是否激活了某个持续法术 */
    default boolean isActive(String spellId) {
        return getActiveSpells().containsKey(spellId);
    }

    /**
     * 设置/覆盖一个持续法术（由调度器在 onComplete 时设置）。
     *
     * @param spellId       法术 ID
     * @param remainingTicks 剩余激活 tick
     */
    void setActiveSpell(String spellId, int remainingTicks);

    /** 移除一个持续法术（到期或手动取消） */
    @Nullable
    Integer removeActiveSpell(String spellId);

    /**
     * 每 tick 递减所有持续法术，返回到期被移除的 spellId 集合（由调度器调用触发 onExpire）。
     */
    java.util.List<String> tickActiveSpells();

    // ========== 绑定关系（法术平面实体）==========

    /** 玩家关联的法术平面实体 UUID（null = 未绑定） */
    @Nullable
    UUID getSpellPlaneUUID();

    void setSpellPlaneUUID(@Nullable UUID uuid);

    // ========== 副本与持久化 ==========

    /** 把其他玩家的数据复制过来（用于玩家死亡重生、从末地返回等同步场景；deep copy） */
    void copyFrom(ISpellPlayerData other);
}
