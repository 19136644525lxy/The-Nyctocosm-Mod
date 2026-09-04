package com.yifei.nyctocosm.spell;

/**
 * 法术分类枚举 — 影响 HUD 图标位置、音效、施法调度器的默认行为。
 * <p>
 * 分类与原法术对应关系（参考 PROGRESS.md P3 表格）：
 * <ul>
 *   <li>BUFF       — Luminescence / Subsistence / Reflectance / Adamance / Resistance / Vacance</li>
 *   <li>DEBUFF     — Abhorrence / Forbiddance / Submergence</li>
 *   <li>AOE        — Gravitance / Incandescence / Repellance / Fulminance</li>
 *   <li>PROJECTILE — Resonance / Transmittance</li>
 *   <li>SUMMON     — Incurrence</li>
 *   <li>UTILITY    — Evanescence（Evanescence 本身类似短时间消失/无敌）</li>
 *   <li>INSTANT    — 即时伤害（目前 17 法术没有这种类型，预留）</li>
 * </ul>
 */
public enum SpellCategory {
    /** 增益 BUFF（一般持续时间 &gt; 0，每 tick tickActive） */
    BUFF,
    /** 减益 DEBUFF（给敌人加负面效果） */
    DEBUFF,
    /** 瞬发伤害（没有持续时间，施法完成立刻结算） */
    INSTANT_DAMAGE,
    /** 投射物法术（需要生成实体） */
    PROJECTILE,
    /** 范围法术（以施法者或目标点为球心/立方心结算） */
    AOE,
    /** 召唤类法术 */
    SUMMON,
    /** 工具类（传送、位移、环境修改等） */
    UTILITY,
}
