# Nyctocosm 全部功能清单与 Java 化决策表

> 用途：梳理模组全部功能（玩家可见行为 + 数值 + 函数路径），供逐项决定「Java 化 / 保留数据 / 保持参考 / 删除」。
> 依据：src\main\resources\data（原数据包保留）+ assets + src\main\java + PROGRESS.md。三份功能深挖报告（Boss 战、施法与数值、世界与氛围）已并入本文。
> 标记：🟢=Java 已完成（⚠=有缺陷）｜🟡=部分 Java（技能链/行为仍在数据包）｜🔴=纯数据包逻辑（Java 化候选）｜📄=数据驱动（建议保留 JSON，不写 Java）。
> 数值口径：Boss/法术伤害均经原包 damage:api 管线（scoreboard ×1000 单位）计算；下表数值为原函数记录值，实施时以原函数逐条核对折算。

## 〇、决策标记约定（每域/每项后留决策栏）

```
[ ] 完全 Java 化   —— 逻辑全部搬 Java（推荐给 🔴 项）
[ ] 保留数据驱动   —— JSON/NBT/表驱动内容保持原样（📄 项）
[ ] 保持参考保留   —— 留在资源里但不再执行（后续清理）
[ ] 修复优先       —— 先修现有 Java 缺陷再谈扩展
```

---

## 一、维度与世界【主体 📄 + 少量 🔴 逻辑】

| 功能点 | 细节（玩家可见） | 归属 | 决策 |
|---|---|---|---|
| 主维度 nyctocosm:nyctocosm | noise 生成、**极暗永恒白日**（fixed_time 6000、无天光、ambient 0.05）、scale 16、床可用、怪生成光照 0–7；经传送门进入 | 📄 JSON | [ ] |
| 战场子维度 dusk_plane / emissary_domain | 有天光 fixed 12700 平地、禁床禁刷怪、Boss 战用；入场/锁 gamemode 为逻辑 | 📄地形 + 🔴入场逻辑 | [ ] |
| 8 群系 | night_wastes(黑石死树/石柱/化石/岩浆湖)、cimmerian_gorge、dark_mire(泥沼树)、desert_of_rime(蓝冰geode/水晶堆/灵魂火)、infernal_chasms(lava湖+magma)、twilight_woodlands(大树/绞藤)、duskfields 等 | 📄 JSON | [ ] |
| 主世界大陆改造 | data/minecraft/worldgen 覆盖 5 组噪声振幅 + factor/offset/jaggedness 密度样条 → 生成特殊大陆区 | 📄（注册表级） | [ ] |
| 峡谷雕刻 | cimmerian_canyon / wastes_canyons（y60–320） | 📄 | [ ] |
| 结构 | monolith_1 / outpost / slatehenge / nightmare_fossil / crypt 地牢（jigsaw + 手写 NBT 房间 17 个 + 加权池 + 70/30 空宝箱）| 📄 NBT/JSON | [ ] |
| slatehenge 生成入口 | structures 函数 place structure（200 格去重）→ 由 advancement/进入触发 | 🔴 | [ ] |
| 砾石掉落 | 维度内砾石→「Nyctocosmic Ash」（糖+NBT 改名） | 📄 loot 表 | [ ] |
| 世界边界振荡 | tick 每 tick worldborder 49M↔59M 循环（nyctocosm:tick） | 🔴（若保留该效果） | [ ] |
| tony 彩蛋猫 | advancement+tonytimer 10–20 分钟随机召唤 | 🔴 | [ ] |

## 二、传送门系统【🔴 大块，≈101 函数，纯逻辑】

| 功能点 | 细节 | 归属 | 决策 |
|---|---|---|---|
| 触发 | resonance 法术 raycast → 沿 **reinforced_deepslate** 框架校验 | 🔴（入口法术已 Java，联动点未接） | [ ] |
| 激活演出 | 三段(0/16/32t)外共鸣动画 + 20 armor_stand 拱形显形 + portal.hum 循环 + swirl/glow 粒子 | 🔴 | [ ] |
| 进入 | 站 origin 2 格内 150t 计时(期间 nausea+darkness)→同坐标 tp 入维 | 🔴 | [ ] |
| 返回/拆除 | remove 停 hum；**返回主世界路径在原包函数中未见直接证据（待确认）** | 🔴/? | [ ] |

## 三、怪物生成与常规怪【🔴 核心协议需 Java】

| 功能点 | 细节 | 归属 | 决策 |
|---|---|---|---|
| 刷怪代理-替换协议 | 群系刷怪表只放 **illusioner**（night_wastes×4/weight100、cimmerian×1–3），每 tick 按 50%/20% 概率 kill 后替换为自定义怪，其余伪死清空 | 🔴 状态机+tick | [ ] |
| Death Walker 死亡行者(上限3) | giant 隐形 hitbox+root 状态机 idle/walking/following/attacking；≤30m 追踪、≤7m slam（10m AOE 震击）；沿地 tp、临崖停；200m 外 500t 消失 | 🔴（AJ 动画壳 death_walker） | [ ] |
| Nightmare Apparition 梦魇幻影(上限30) | armor_stand 浮空 10m 上下；对 ≤13m 非潜行玩家警戒→贴脸 ≤1m wither+瞬伤；光照≥5 推离；接入玩家 darkness 累积 | 🔴 | [ ] |
| 实体承载 | 全部自定义怪 = armor_stand/giant + AJ item_display 动画，**无专属实体 JSON，Java 实体需从零建** | 🔴 | [ ] |

## 四、法术系统【🟢 框架完成，⚠ 有缺陷；若干子机制缺失】

| 功能点 | 现状 | 决策 |
|---|---|---|
| 17 法术元数据（元素/伤害/吟唱/持续） | 🟢 与 spell/impl 构造器**完全一致**（已核验） | [ ] |
| 蜡烛阵（16 固定位全点燃）+ 17 催化剂消耗 | 🟢 CandleMatrixMatcher（P3.5） | [ ] |
| 施法交互顺序（进圈→丢催化剂于圈内→念咒圈演出→transmutate） | 🟡 Java 简化为「持饰右键→验证→直施」；无 spellcircle armor_stand 念咒演出、无"离开 3m 取消"规则 | [ ] |
| 法术平面动画 | 🟢 SpellPlaneEntity/GeoRenderer 骨架，但 **⚠ 实体从不生成、动画不播** | [ ] 修复 |
| Luminescence（0 吟唱法术） | ⚠ castTime=0 → onComplete 永不执行（死路） | [ ] 修复 |
| power 加成（原 magic 六元素属性统计） | ⚠ Java 运行期 finalPower≡0，scaled 伤害/时长全失效；**原 magic 属性统计算法未 Java 化** | [ ] |
| 反射器 reflector（Reflectance 反弹弹道改归属） | 🔴 纯数据包，Java 无 | [ ] |
| trace/hypertrace（暴击追踪掉落机制，charge 上限 5） | 🔴 纯数据包 | [ ] |
| mobile 移动法术平面（transposition，持续=100×power+500） | 🔴 纯数据包（Java 无跟随平面） | [ ] |
| 弹道演出（marker/石英 armor_stand 射线、逐 t 前移） | 🔴 纯数据包 | [ ] |
| 施法冷却 50t | 🟡 Java 未实现统一冷却 | [ ] |

### 17 法术行为对照（原函数数值 → Java 现状）
| 法术 | 原包行为要点（数值=原函数记录） | Java 现状 |
|---|---|---|
| Luminescence | dmg0 ct0 dur6000，光照；luminoustimer=600p+6000 | ⚠ 死路（见上） |
| Incurrence | ct180，按群系召 Boss 入口 | 🟡 占位（Actionbar+打 tag，P6 未接） |
| Evanescence | ct100，速度+夜视+隐身（使者领域内禁用） | 🟢 已实现 |
| Resonance | dmg60 ct80 射线弹道 | 🟢（简化直伤） |
| Abhorrence | dmg15 ct40 dur30 凋零 DoT（dmg/dur=2p+基） | 🟢（简化） |
| Forbiddance | dmg0 ct10 dur35 禁制；dur=5p+35、pierce=power | 🟢（简化，无 pierce 用） |
| Gravitance | dmg20 ct60 dur200 引力拉拽；dmg=4p+20、dur=20p+200 | 🟢（简化） |
| Subsistence | dmg10 ct40 治疗=(4p+10)×1000 | 🟢 |
| Reflectance | dmg20 ct30 dur200 **反弹 20%（反射器机制）** | 🟡 无反射实现（注释承诺未兑现） |
| Submergence | dmg8 ct80 双元素+缓慢+失明 | 🟢 |
| Adamance | dmg0 ct20 dur200 坚壁护盾；dur=40p+200 | 🟢 |
| Incandescence | dmg10 ct20 dur40 点燃 AOE；dmg=8p+10、dur=10p+40 | 🟢 |
| Resistance | dmg0 ct40 dur250 抗性提升 | 🟢 |
| Transmittance | dmg15 ct100 穿透弹；落地 30t 每 t 落雷+void_swirl+shockwave | 🟢（简化） |
| Vacance | dmg0 ct20 dur100 漂浮隐身； | 🟢 |
| Repellance | dmg0 ct50 dur50 斥力吹飞 6/10 格双档 | 🟢 |
| Fulminance | dmg7 ct30 dur20 轰雷 AOE | 🟢 |

## 五、灵魂经济（瓶/戒指）【🟡 语义相反需修正】

| 功能点 | 原包行为 | Java 现状 | 决策 |
|---|---|---|---|
| 灵魂收集 | 死亡处 soul marker 100t，5m 内玩家持玻璃瓶/灵魂瓶/戒指自动 +1 | 🟢（SoulBottleItem/PlanarCircletItem 有对应） | [ ] |
| 灵魂瓶右键 | 原包：右键 = **-1 魂并召施法阵**，≤0 变回玻璃瓶 | ⚠ **Java 相反**：右键 +1、潜行 -50（上限 999）——疑似把「收集/管理」当成交互；容量 999 原包无上限 | [ ] 决策语义 |
| 戒指激活 | 潜行右键 -1 魂 → cast_ring_plane（**免蜡烛**），再按取消 | 🟢 已实现（需 1 魂一致） | [ ] |
| lore 动态魂数 | NBT souls_held + item_modifier 动态显示 | 🟢 tooltip | [ ] |

## 六、圣物/饰品×14【🟡 Handler 已建，数值/分派待核对】

| 圣物（原包行为） | 效果要点 | Java 现状 |
|---|---|---|
| Protection×6（dream…dissonance） | 3×charge → 元素 resistance modifier（+最多 15×3），每 200t 耗 1 charge | 🟢 减伤 40% 简化（LivingDamage） |
| Absorption(void) | void 伤后回 20% 伤害；附近 hypertrace 附加 void 弱化 | 🟢（语义待核对） |
| Amelioration(radiance) | 原包：hypertrace 被摧毁时治疗 6m 盟友 | ⚠ Java 语义相反（击杀时治疗自己） |
| Expulsion(dissonance) | 杀 dissonance 敌 → 15t 后爆炸(3p+7) 6m | 🟢 |
| Extraction(resonance) | 杀敌召 soul（供灵魂瓶） | 🟢 |
| Transposition(dream) | 召**移动**法术平面 | 🔴 未分派（Java 无移动平面） |
| Levitation(dream) | 潜空 charge≥3 潜行 → -3 charge + slow_falling 5s | 🟢 |
| Locomotion | 疾跑 dash（cd10t） | 🟢（⚠ COOLDOWNS WeakHashMap 可被 GC） |
| 其它计数不一致 | 注释 10/12/13/14/16 互相矛盾，实注册 16 | 🟡 文档待清 |
| 被动双触发 | ArtifactPassiveHandler @EventBusSubscriber + 手动 register 双注册 | ⚠ 需修（扣双魂等） |

## 七、四武器【🟡 本体已 Java，技能链仍在数据包】

| 武器 | 原包技能（items 命名空间，点击计分板驱动） | Java 现状 |
|---|---|---|
| Nullfissure(15.5攻) | 主手 3 格扇形挥砍(cd20)；offhand 40 格虚空束 raycast（穿透 passthrough、可被反射） | 🟡 右键触 tryStartCast 初版 |
| Wyrmsliver(14攻) | slash 剑气 marker 逐 t 前移 1 格 50t，噩梦元素伤害+失明 5s，cd20 | 🟡 |
| Seelic Scythe(11攻,reaping) | 右键 dash 前冲≤7 格（沿途 2m 内 11 伤）；击杀召 soul marker | 🟡 |
| Semblance of Doom(19攻+击退III+火焰II) | 主手 2 格全向烈焰斩 21；offhand doomfireball 火球(cd30) | 🟡 |
| ⚠ 关键不兼容 | 原 predicates 硬判**原版 warped_fungus_on_a_stick+CMD**；Java 下发真物品后整套点击链失效 | [ ] 决策：改判定 or Java 事件直驱 |

## 八、Boss 战【🔴 最大块，纯数据包，Java 未启动(P6)】

> 三 Boss 由 Transmutation 咒法按生物群系召唤（desert_of_rime→sorcerer / infernal_chasms→emissary / night_wastes→serpent）；本体视觉全为 AJ item_display 骨架，命中实体是 y≈-100 NoAI 巨血实体；伤害全走 damage:api（底层 10 种 DamageType/Sources 已 Java）。**Java 化需从零建实体 + AI 控制器 + 分阶段动画驱动。**

### Sorcerer 巫师（desert_of_rime）
| 项 | 内容 |
|---|---|
| 承载/数值 | wither_skeleton 主身(ns.health 1000 bossbar 紫) + 法杖 Nullpoint(300) + ajsorcerer/m1/m2+ajnullpoint 骨架；法杖 200t 无敌入场 |
| 阶段 | ns.phase 1–6；**奇数段持杖（需破杖→fall 换 m2）偶数段无杖（≤666/333 血→rise 重新插杖+140t 无敌）** |
| 招式 | 近/远两池×6 段随机：shardhail10 / frozenblades14 / icespikes3 / javelinswing16 / dashstrike15 / airdash15 / halobeam10 / singularitycharge20 / voidmeteor15 / seekingsingularities13 / voidwave20+黑暗4s / voidbeam3（均 slowness10s I 减速；可被 spell 反射） |
| 死亡 | defeat→die 动画→finaldeath：title+advancement+loot bossloot+9×1000XP+lore adherent；100 格无人自动 remove |

### Emissary 使徒（infernal_chasms → 专属维度 emissary_domain）
| 项 | 内容 |
|---|---|
| 召唤/演出 | 主世界造入口 marker→传玩家入域锁 adventure→巨型粒子穹顶(~50m)→陨石落地→spawn 动画开战+BGM |
| 阶段 | em.phase 0–4（em.health 1200，阈值≤1000/600/200），演出期无敌；**hitbox 换装**：P1 Size4 slime→P2 giant→P3/P4 wither_skeleton；受击按段折算再回满=每段阶段血条 |
| 招式 | P1：lightning_javelin45/meteor_shower40/pillars35/wingbeat5；P2：celestial_smite80/sword_plunge60/wing_blast40/flame_geysers40；P3 剑斗：swordswing50/swordspin60/cross35/ground_slam60/flame_slash50/frontkick/lightning_field35/seeking_missiles×7 15/parry；P4 空战：godrays40/apocalypse_rain12/fireball35/divine_blast80/corkscrew60/grapple25 |
| 结局 | 150→1300t 过场对白(Vesper/Auror)+致盲+fillbiome duskfields→escape 传送回入口解锁 adventure→finaldeath loot/XP |

### Serpent 巨蛇（night_wastes）
| 项 | 内容 |
|---|---|
| 承载 | 1 头 + **40 节 armor_stand（程序化蛇身跟随）** + 17 个 Size8 slime hitbox（偶数节）；SerpentHealth 1000 红 bossbar |
| 状态 | 1 游走(>200m 回防)/2 潜地追击(400t)/4 施放(Attack 1–3)/5 死亡 |
| 招式 | 起手 scream+twirl90°：**dive** 潜地扑咬 / **laser** 身后 20 格埋 target、头到即扫射 100t / **beam** 18 枚弹标记随机 140–280t 分批落下；近身接触伤害 |
| 死亡 | 逐节 nightmare_vein/soul_swirl 粒子 100t→remove+尾曲 |
| 备注 | 程序化多段身体 = Java 化最高成本项 |

### BGM 触发（随 Boss）
sorcerer：40m 内→phase{1-6}/start、切段→queue 下一 part（Intro30429/Phase20571t 对拍）；emissary：血线→queue 4–8（8 段×2 part）；serpent：loop 每 1900t 重播→死亡 end 尾曲。🔴 建议随 Boss 实体事件驱动（音频资源可保留原 sounds）。

## 九、伤害引擎【🟡 底层已 Java，上层模型纯数据包】

| 功能点 | 内容 | 归属 |
|---|---|---|
| 元素伤害类型/源 | 10 种 ResourceKey（magic×6+damage+sorcerer×3）+ 类型安全 DamageSource | 🟢 |
| 跨模组怪免疫修复 | EntityTypeTagInjector（livingthings 等 4 tag 注入） | 🟢 |
| 元素伤害 API | 弱化/抗性公式：final=base×(1+(weak+globalWeak)/100)×(1−(res+globalRes)/100)；damage_dealt/taken/kills 标记 + trace 判定 | 🔴 |
| ncdamage 属性模拟 | 护甲/韧性折算（≤80%）、apiercing 穿透、EPF 附魔点(prot/火/爆/弹/落 get_eps≤20/25)、抗性效果 20%×(amp+1) 减伤、≥100 免疫 | 🔴 |
| 玩家扣血模拟 | attributes + instant_health 29 级 | 🔴（如决定还原属性链） |

## 十、特效/屏幕表现【🔴 纯数据包（P10 规划）】
shockwave(冲击波：spellplane/meteor/reflect/inculcate…)｜lightning(电弧/描线/爆炸)｜screen_shake(屏幕震动)｜soul_swirl/void_swirl(±reverse/xflat)｜void_vein/nightmare_vein(单发/连发/爆裂)｜elements(六元素 cast/transmutate 粒子)｜spells/circles(法阵符号)｜transmittance 落地组合（30t 逐 t 落雷+void_swirl×10+shockwave）。决策：Java 粒子系统重实现 or 保留 effects 函数供 Java 调用？ [ ]

## 十一、音乐与氛围【🔴 纯数据包】

| 功能点 | 内容 |
|---|---|
| 玩家恐惧/黑暗系统 | darknessmeter 黑暗中 +1/t→200 进 fear(darkness+slowness)；心跳=heartbeattimer≥calmness；feartimer LCG 掷 3 类惊吓音；光照≥5/luminous 解除 |
| 进维音乐停播 | 每 tick 停全部原版 music.* |
| 自适应音乐队列 | 借 worldborder 秒表倒计时 + queue 奇偶映射 phase start/continue |
| crypt 环境乐 | stepping_on structure 判定进出，播 219082t music.crypt |
| 底噪悬空 | twilight_woodlands/desert_of_rime 引用 ambience.*.mood 但 sounds.json 无对应事件（原包疑漏） |

## 十二、内容层【🔴 纯数据包（lore 书已注册物品）】

| 功能点 | 内容 |
|---|---|
| Dais 合成台 | 结构：anvil+beacon+4石英块+4石英楼梯+4蜡铜台阶+16点燃蜡烛；4 条村民交易：4魂沙+1烈焰棒→4 Seelic essence；20 essence+4金锭→planar_coil；40 essence+52黑染料→wyrmsliver；40 essence+6黑染料→nullfissure；交易后 inculcate 动画+掉落成品(50t 可拾) |
| 书籍/剧情 | 4 条 storyline（written_book 右键翻篇计数）：journals 5 / catechism 3 / dreams 3 / axiotopology 3；Adherent I–III 由三 Boss 击杀发放 |
| Advancement 链 | root + boss defeat×3 + tonyspawn + dash + kill_with_scythe 等钩子 |

## 十三、表现资产与本地化【🔴 P5 未完成 + 发布缺口】

| 项 | 内容 |
|---|---|
| AJ→GeckoLib | 8 rig 实体动画转换未落地（仅 spellplane 原型）；geo UV 格式非法 = 崩溃源；Z 轴符号 bug（P5）未修 |
| 音效缺口 | sounds.json 13 条引用缺文件（含 serpent rumble_-1 vs rumble_-01 零填充不一致）；45 个 ogg 未引用 |
| 语言 | zh_cn 业务键绝大多数为「§7[§c待翻译§7]」占位（仅 artifact.* 真译） |

## 十四、系统层【🔴 未启动】
网络同步（吟唱进度/持续法术/Boss 状态/粒子批包）、HUD（吟唱条/持续法术图标）、配置 GUI（NyctocosmConfig 现为死配置——⚠ refresh() 无调用者）、zh_cn 配置翻译。

---

## 十五、当前 Java 缺陷清单（修复优先，独立于上表）
1. Luminescence castTime=0 → onComplete 永不执行（spell/impl/LuminescenceSpell + SpellCastingManager）
2. ArtifactPassiveHandler 双重注册 → 事件双触发（@EventBusSubscriber + init 手动 register）
3. spell_plane 实体从不生成、playPlaneAnim 恒空转
4. power 系统惰性（finalPower≡0）——与 magic 属性统计算法缺失同源
5. NyctocosmConfig.refresh() 死配置（无 ModConfigEvent）
6. 圣物 TRANSPOSITION/ACCUMULATION 未分派；AMELIORATION 语义与文档相反；onLeftClickEmpty 空实现
7. SoulBottleItem 右键语义与原包相反（见五）
8. 武器判定硬编码原版 wfoas+CMD，与 Java 真物品不兼容（见七）
9. COOLDOWNS WeakHashMap 弱键可被 GC；resolveElement 只比 path 不比 namespace
10. spell/impl 头注与构造矛盾多处（Gravitance/Forbiddance/Reflectance 注释承诺未兑现）

## 十六、遗留不确定点（需人工确认或实测）
- 传送门「返回主世界」路径在原包中无直接证据
- ambience.*.mood 事件悬空（twilight_woodlands/desert_of_rime）
- 自定义怪无专属实体 JSON（全 armor_stand/giant+AJ），Java 实体从零建
- artifacts/amelioration/subtick 被调用但文件不存在（死引用）
- #crafting:all 标签含全部物品（清理 FakeItem 用，可作测试用例）
- humtimer 计分板定义但主循环未使用（疑似遗留，勿投入）
- 伤害数值单位：原包 scoreboard ×1000，Java 需统一换算口径

## 十七、验收口径（已与用户约定）
- 行为等价 + 观感对照：可量化项（伤害/时长/阶段/实体）写对照测试逐项等价；动画演出/音效/屏幕特效用双开运行或录屏人工验收
- 保留数据驱动内容为 JSON，Java 只接管运行逻辑
- 分阶段交付，每阶段一个还原验收门
