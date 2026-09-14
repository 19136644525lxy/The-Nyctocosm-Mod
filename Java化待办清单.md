# Nyctocosm 剩余 Java 化待办清单（2026-09，缺陷修复轮后）

> 依据：功能清单_全量与Java化决策.md + 三份功能深挖报告 + 代码现状。
> 标记：🟢=Java 已有｜🟡=部分/近似（注明差距）｜🔴=纯数据包未动｜📄=数据驱动，**建议不 Java 化**。
> 工作量：S=1~2 天 · M=3~7 天 · L=2 周+（单人，含验收对照）。

## 首测反馈登记（用户实测 2026-09，未修，优先级待定）
1. **武器技能可触发 ✅**（冲刺/剑气/斩/火球生效）。
2. **技能粒子特效缺失**：scythe dash、wyrmsliver slash、nullfissure cast 目前无/少粒子演出（doom 有基础粒子），需按原包 items/effects 演出补齐（sweep_attack/flash/soul/swirl 等）。
3. **使用动作/动画不对**：武器使用应带正确 UseAnim 与挥砍/施法动作（现多为默认手臂挥动；nullfissure 无动作），需核对每个技能的动作与持械姿态。
4. **Curios 饰品未正常注册**：具体症状待下次测试补充（槽位不显示 / 物品放不进槽 / 被动不生效 / tooltip 缺失）；怀疑点：槽位数据包是否随包加载、物品↔槽 tag 关联、或手测环境（mods 是否含 curios/geckolib 版本）——排查项占位。

---

## 0. 已完成（勿重复排期）
- 17 法术元数据/蜡烛阵/催化剂匹配；缺陷修复 10 项（Luminescence 死路、双注册、spell_plane 生成、power 通路、config 接线、圣物语义、灵魂瓶右键、resolveElement、弱引用、注释）
- 4 武器注册 + 右键直驱技能（冲刺/剑气/斩/火球，弹道为近似）
- 10 伤害类型/源 + 实体标签注入 + Curio 4 槽 16 物品

---

## 1. 施法系统深度还原（原 spells/magic 残留逻辑）
| 项 | 现状 | 差距 | 工作量 |
|---|---|---|---|
| 反射器 reflector（弹道反射改归属） | 🔴 数据包（spells/…reflect） | 需事件级弹道拦截；Reflectance/部分 Boss 招依赖 | M |
| hypertrace / tracechance（暴击追踪掉落、charge≤5） | 🔴 数据包（magic/artifacts/accumulation） | 机制链整条 + ACCUMULATION 圣物才有意义 | M |
| 元素属性系统 magic（power/speed/tracechance/resistance/weakness，base+modifier+global，calcstatsspell 每用重算） | 🟡 仅打通 power 数组通路，其余字段无 | 数据模型+重算+来源（圣物/击杀/装备） | M |
| 法术平面免蜡烛路径 cast_ring_plane / 平面生命周期（hum、离 3m 取消、dusk 上限） | 🟡 已生成+移动跟随 | 免蜡烛戒指直施、取消规则、平面存活语义 | S-M |
| 弹道/演出实体逐 tick（Resonance 射线、Transmittance 穿透落雷、Submergence 等 marker 弹道） | 🟡 多为即时直伤近似 | 逐 tick 弹道实体或等效调度 | M |
| 施法统一冷却 50t / 玩家死亡重置细节 | 🟡 | 调度器补齐 | S |

## 2. 灵魂经济端到端 + 判定兼容（关键前置）
| 项 | 现状 | 差距 | 工作量 |
|---|---|---|---|
| soul marker 生成/拾取（5m 自动 +1、玻璃瓶→瓶、戒指/瓶 NBT） | 🔴 数据包 items:tick 仍在跑，但 predicate 硬判原版 carrot/wfoas+CMD | **决策**：改数据包 predicate 指向 Java 物品，或 Java 事件全接管拾取 | M |
| 四武器/瓶/戒的通用「原版物品判定」兼容层 | 🟡 | 同上，全局判定方案（影响 #8 延伸与灵魂链） | M |

## 3. Boss 三件套（P6，最大块）
| 项 | 现状 | 内容 | 工作量 |
|---|---|---|---|
| Sorcerer 巫师 | 🔴 | 6 段持杖/破杖轮换、12 招分池随机、bossbar/无敌窗口、die/finaldeath/loot/advancement | L |
| Emissary 使徒 | 🔴 | 专属维度 arena、4 阶段换 hitbox、20+ 招、爆甲/空战、过场对白+escape | L |
| Serpent 巨蛇 | 🔴 | 1头+40节程序化身体、dive/laser/beam、状态机 | L（身体跟随最高成本） |
| Boss 通用支撑 | 🔴 | 实体从零建、伤害走已 Java 的 10 伤害类型、Bossbar、BGM 触发、击败剧情/Adherent | M |
| 前提 | 🔴 | AJ→GeckoLib 动画（P5）决定观感还原能否验收 | 见 §6 |

## 4. 常规怪与刷怪协议
| 项 | 现状 | 差距 | 工作量 |
|---|---|---|---|
| illusioner「代理+每 tick 替换」刷怪协议 | 🔴 | 群系刷怪表仍illusioner，替换逻辑 tick 驱动 | M |
| Death Walker（状态机 idle/walk/follow/slam、临崖停、超距消失） | 🔴 | 全行为 + 生成条件 | M |
| Nightmare Apparition（警戒/贴脸/光照推开/黑暗累积） | 🔴 | 全行为 + 与玩家 darknessmeter 联动 | S-M |
| 实体承载方案 | 🔴 | 原全为 armor_stand/giant+AJ 视觉，需决定 Java 实体建模方式 | M（并入上三项） |

## 5. 世界/入口引擎（nyctocosm 函数逻辑）
| 项 | 现状 | 差距 | 工作量 |
|---|---|---|---|
| 传送门（≈101 函数：框架校验/三段共鸣/拱形显形/150t 计时 tp/拆除） | 🔴 | 全流程 Java；返回主世界路径原包无证据需实测确认 | L |
| 进维效果（停原版音乐、氛围 scoreboard） | 🔴 | tick 逻辑搬 Java 或保留 | S-M |
| fear/darkness 环境系统（心跳/惊吓 LCG/光照解除） | 🔴 | 与怪/氛围联动 | M |
| worldborder 振荡 / tony 彩蛋 / slatehenge 触发 | 🔴 | 小功能，可随世界引擎 | S×3 |
| 砾石灰烬 / 维度战利品 | 📄 保留 | — | — |

## 6. 表现层与资产（决定观感验收可行性）
| 项 | 现状 | 差距 | 工作量 |
|---|---|---|---|
| P5 AJ→GeckoLib：8 rig 动画转换 + geo 修复（UV/axis）+ Z 轴符号 bug | 🔴 | 工具链已备（tools/aj2geckolib），需解包 zip 重跑+修映射 | L |
| Boss/怪渲染模型 | 🔴 | 依赖 P5 | 随 P5 |
| 屏幕特效（震屏/迷雾/虚空视界）、HUD（吟唱条/持续图标） | 🔴 P10 | 网络包先行 | M |
| 音乐自适应队列（music 命名空间状态机） | 🔴 | 建议随 Boss 事件触发 | M |

## 7. 系统层
| 项 | 现状 | 差距 | 工作量 |
|---|---|---|---|
| 网络同步包（玩家法术数据/Boss 状态/粒子批包） | 🔴 P8 | 服务端→客户端 | M |
| 配置 GUI + 配置项真正接线（现有 11 开关多处无消费点） | 🟡 已修 refresh | 逐开关核对是否 gate 了功能 | S-M |
| zh 本地化与键名整理 | ⏸ 用户挂起 | — | M（另列） |

## 8. 内容层
| 项 | 现状 | 差距 | 工作量 |
|---|---|---|---|
| Dais 合成台（4 配方/结构校验/inculcate 动画） | 🔴 | Java 化或「Java 触发 + 数据包流程」 | M |
| lore 书籍剧情链（翻篇计数/Adherent 发放/advancement 钩子） | 🟡 书已注册 | storyline 状态机 Java | S-M |

---

## 不该 Java 化（留数据/JSON，勿排期）
维度/维度类型 JSON、8 群系与世界生成（noise/密度/雕刻/结构 set/NBT 房间）、战利品表、damage_type 定义、curios 槽位 tag、sounds.json 事件定义、物品模型/纹理/音效资产。→ 已在“功能清单_全量与Java化决策.md”标记 📄。

## 推荐推进顺序（供拍板）
1. §2 判定兼容决策（卡灵魂链与武器延伸）→ 2. §1 施法深度（反射器/hypertrace/属性系统/弹道）→ 3. P5 动画（Boss 前提）→ 4. §5 世界引擎（传送门）→ 5. §3/§4 Boss+怪 → 6. §7/§8 系统层与内容层。
（替换语言：最终以「行为等价+观感对照」验收；每块结束跑一次集成对照。）
