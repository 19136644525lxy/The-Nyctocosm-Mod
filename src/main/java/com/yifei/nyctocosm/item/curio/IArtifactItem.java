package com.yifei.nyctocosm.item.curio;

import com.yifei.nyctocosm.spell.ElementType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 圣物饰品统一标记接口（ISP 最小接口原则）。
 * <p>
 * 作用：
 * 让 {@link com.yifei.nyctocosm.curio.ArtifactPassiveHandler} 用 instanceof 判断
 * 玩家身上哪些 Curios 物品属于"圣物"，并调用 {@link #getPassiveType()} 分派被动效果。
 * <p>
 * 6 个保护圣物对应 6 个 {@link ElementType}，其余 4 个功能性圣物使用 {@code null}
 * 的 ElementType + 一个显式的 {@link ArtifactPassiveType} 枚举来描述。
 */
public interface IArtifactItem {

    /**
     * 圣物所属元素类型；用于 Protection 系列抗性被动（对应原数据包 m.*.resistance.modifier）。
     * 功能性圣物返回 {@code null}。
     */
    @Nullable
    default ElementType getElementType() {
        return null;
    }

    /**
     * 被动效果类型枚举。功能性圣物必须返回一个非 NONE 值。
     */
    @Nonnull
    ArtifactPassiveType getPassiveType();

    /**
     * 10 圣物被动种类（保护 6 种归类为 ELEMENT_RESISTANCE，剩下 4 种显式枚举）。
     */
    enum ArtifactPassiveType {
        /** 元素抗性：由 {@link #getElementType()} 指定具体哪种元素 */
        ELEMENT_RESISTANCE,
        /** 吸收：击杀对应元素怪物时 +1 魂 */
        ABSORPTION,
        /** 积聚：靠近 hypertrace 时自动增大掉魂概率（依赖 hypertrace 系统，Java 化前佩戴暂无效果） */
        ACCUMULATION,
        /** 改善：佩戴者击杀 nyctocosm 怪物（hypertrace 近似）且在 6 格内时，治疗周围 6 格玩家 + 饱和 */
        AMELIORATION,
        /** 置换：佩戴时施法平面改为跟随施法者移动（原 a.transposition → cast_ring_plane 移动平面） */
        TRANSPOSITION,
        /** 悬浮：潜行（3 魂）→ 缓慢下落 5s */
        LEVITATION,
        /** 驱逐：击杀 dissonance 元素怪触发 6 格爆炸 */
        EXPULSION,
        /** 萃取：潜行右键 → 原地生成 1 魂 + 播放音效 */
        EXTRACTION,
        /** 运动：潜行→冲刺 速度 20+抗性 255 持续 4tick + 10tick CD */
        LOCOMOTION
    }
}
