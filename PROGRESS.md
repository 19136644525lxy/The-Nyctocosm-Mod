# Nyctocosm 1.20.1 Forge 开发进度

> **原作**: Flubberschnub（数据包 + 资源包 v0.2.0，基于 MC 1.20.5+）
> **基线**: nyctocosm-native alpha.20（2026-10-04，另一开发者的原生重写，288 项 GameTest 回归通过）
> **本项目**: Yifei，包名 `com.yifei.nyctocosm`
> **目标版本**: Minecraft 1.20.1 Forge 47.4.23 / Java 17
> **当前版本**: `0.4.0-alpha.1-forge-1.20.1`
> **更新日期**: 2026-10-17

***

## 当前状态 & 下一步（速览）

**已完成**：旧路线清理 → native-main 基线搬入（包名改 `com.yifei.nyctocosm`）→ 构建配置同步 → `gradlew assemble` 通过，产出 JAR + 源码 JAR ✅

**下一步优先级**：

| 优先级 | 阶段 | 内容 | 验收 |
| ---- | ---- | ---- | ---- |
| ✅ 完成 | **R1a** | 94 文件按域分包（2026-10-17，Forge 47.4.27 下 288 项 GameTest 全绿） | `runGameTestServer` → All 288 required tests passed |
| ✅ 完成 | **R1b** | `NativeBoss.java`（1520 行）拆分（2026-10-18）：`boss/BossEntity` 骨架 + `boss/sorcerer|serpent|emissary` 三控制器 + `OrdinaryController`，`NativeBoss` 保留为门面（static 工具转发 + 实例薄委托） | 288 项回归全绿（纯搬移不改行为） |
| ✅ 完成 | **R2** | 技术债清理（2026-10-18）：46 处 `ResourceLocation` 过时构造器 → `fromNamespaceAndPath`/`parse`（19 文件）；`NativeMod` 构造注入 `FMLJavaModLoadingContext`（47.3.10+ 推荐，替代弃用的静态 `get()`）；GameTest 中 `ProjectileImpactEvent.setCanceled(true)` → `setImpactResult(SKIP_ENTITY)`（Forge 源码证实两者等价，均为官方推荐 API） | 编译 [removal] 警告归零 + 288 项回归全绿 |
| ✅ 完成 | **R3** | Emissary 招式补全（2026-10-18）：逐招对照原包 mcfunction 补齐六招——sword_plunge（石头尾迹柱/云雾尾流/红尘警示圈/105 冲击波三段环/20 落火残骸/爆炸密度+21×21×7 火场 fill）、pillars（flame 弹 5×24×5 沿途火场 + 命中 3×3 火圈）、wing_blast（红尘警示线→爆发带粒子 + 爆炸音效 30 格）、flame_slash（命中即结算自毁）、flame_geysers（7×7×7 火场 fill + inculcate 三段冲击环）；apocalypse_rain 经逐项对照基线已等价无缺口 | 288 项回归全绿（删 run/world 后） |
| ⏸️ 低 | **R4+** | 法术属性系统 / Submergence 原触手 / Tony / 普通怪自然生成（见优化路线） | 行为等价 + 观感对照 |

> **回归环境协议（2026-10-17 实测确立）**：
> 1. Forge 固定 **47.4.27**（1.20.1 系列最新；实测比基线作者用的 47.4.13 兼容性更好，且 47.4.23 存在部位命中/坐标精度差异）。
> 2. **跑 `runGameTestServer` 前必须删除 `run/world/`**：scoreboard objective、测试方块会持久化到世界，复用旧世界会连坐 4 个测试假失败（boss_health / ice_spikes / source_passthrough / crypt_trigger）。
> 3. 原数据包外部依赖：`serpent_body_matches_original_function_files` 等测试读取 `f:\Work\_nyctocosm_source\datapack\`（由项目根 `Nyctocosm Datapack v0.2.0.zip` 解包，路径 `..\..\_nyctocosm_source` 相对 `run/`），丢失需重新解包。

***

## 一、路线决策记录（2026-10-17 拍板）

| 决策点 | 结论 | 理由 |
| ---- | ---- | ---- |
| 整体策略 | **以 native-main 为基线**，旧自研代码全量废弃 | 同为 1.20.1 Forge，API 零适配；288 项回归 + 行为规格文档，重写 L 级模块风险远超移植 |
| 动画系统 | **native_rigs 采样矩阵方案**（`RigLibrary` + `RigTransforms` + 12 个 json.gz），放弃 GeckoLib 与 aj2geckolib 脚本 | AJ 逐帧矩阵离线导入，运行时 TRS 插值渲染；三 Boss 动画已跑通，绕开 P5 卡死的 Z 轴 bug |
| 饰品系统 | **纯 Java 库存检测**（`ArtifactItem` + `NativeEvents.hasArtifact`），不使用 Curios | 无外部依赖、无注册问题；旧 Curios 体系已删 |
| 施法触发 | **灵场丢材料**（`SeelicField`，贴近原数据包主路径），蜡烛阵路线废弃 | 蜡烛阵实际属于祭坛（dais）场景，非性施法主路径 |

已删除：旧 `src`（自研 Spell API/Capability/Curios/蜡烛阵）、`tools/aj2geckolib`、`libs` 依赖 jar、`PROGRESS.md` 与 `Java化待办清单.md` 旧版、temp 中 geckolib/curios 参考源码。

保留参考：`功能清单_全量与Java化决策.md`、`功能还原清单_世界与氛围.md`（原数据包功能梳理，对照用）；`temp/nyctocosm-native-main`（基线代码 + docs 行为规格）；原数据包/资源包 zip（行为权威依据）。

***

## 二、R1 分包目标结构（当前为平铺，待重组）

```
com.yifei.nyctocosm/
├── NyctocosmMod / Content / CommonEvents   # 入口、注册中心、全局事件（保持根包，R13 去 Native 前缀重命名）
├── spell/       # Spell 枚举、SeelicField、SpellBolt/SpellArea/SpellCollisionPolicy、
│                # Reflectance/ReflectableAttack、HypertraceService、ResonanceSpell/Effects
├── combat/      # Combat 六元素伤害
├── boss/        # Boss（原 NativeBoss 门面）+ BossEntity 共享基类
│   ├── sorcerer/   # SorcererTimeline/Plane、NullpointStaff、IceSpikeWave、FrozenBlade、
│   │               # SorcererDash/Hail、SorcererBeamNode/Effects、SorcererCastMath
│   ├── serpent/    # SerpentMotion/Body、SerpentBeamHazard、SerpentAmbientEffects
│   └── emissary/   # Emissary* 全系列（20+ 招式类）
├── item/        # WeaponItem/WeaponEffects/WeaponAbilityCooldowns、FocusItem、
│                # ArtifactItem、LoreItem、NullpointStaff(物品侧)
├── world/       # portal/（PortalBlock（原 NativePortalBlock）、PortalActivation、SafeTravel）、
│                # structure/（CryptSlatehengeTrigger、SlatehengeEffect、StructureEventSavedData）
├── block/ menu/ dais/   # 祭坛三件套 + 界面
├── entity/      # SoulEntity、TonyManager
├── network/     # Network（原 NativeNetwork）
├── migration/   # LegacyMigration
├── client/      # ClientEvents、RigLibrary、RigTransforms、GlowingModels、BossMusicController、
│                # 各 EffectController、DaisScreen、ClientSmoke 系列、compat/（JEI 集成）
└── gametest/    # GameTests 系列（原 NativeGameTests*，JAR 排除）
```

**分包注意**：native-main 平铺包内大量 package-private 互相调用，重组时逐个调整可见性；`NativeBoss.java`（1472 行）拆分为 BossEntity 基类 + 三 Boss 控制器，正好解决其交接文档自认的拆分债。

***

## 三、优化路线（承接 native-main 遗留，按交接清单整理）

| 项 | 现状（基线自述） | 方向 |
| ---- | ---- | ---- |
| Emissary 四阶段 | ~~招式大量简化、VFX 不全~~ R3 已补齐六招画面/火场/结算细节（2026-10-18）；剩余：其他招式 locator、完整翅膀受击部位、粒子画面多人细节（原文档自述） | 已还原项建议进游戏连画面核对观感 |
| 法术属性系统 | ~~`magic/calcstatsspell` 未移植~~ 已完成（2026-10-18）：新建 `spell/SpellStats.java`（7 域 × power/speed/tracechance/resistance/weakness 各 base+modifier→total，存玩家 PersistentData）；施法链接入（startCasting 按 speed 减施法时间、finish 按 power 加 bolt 伤害 + spells_cast 追踪）；承伤链接入（Combat.hit 按目标 元素域+global 的 weakness/resistance 修正伤害 + damage_dealt/taken/kills 追踪）；玩家 tick 重算 + resettrackers + tracechance.base 默认 15。修饰源数值保持原包默认（原包 modifier 几乎无写入源，仅 protection 饰品写 resistance.modifier，待后续接入） | 288 项回归全绿 |
| Submergence | ~~非原触手实现~~ 已对齐原包（2026-10-18）：伤害接入属性系统（`damage=8+4*min(50,global+dream+void power)`，finish 结算一次全体受害者共享，NBT 持久化）；per-victim `spells.impact.submergence` 音效；减速 amp10 全程生效；拖拽由速度向量改 teleport 1 格/tick（原包 `tp ^ ^ ^1` 可穿墙入地，速度向量撞地即停）；粒子改 4 条 yaw 局部偏移触须（`^1^^`/`^-1^1^`/`^^0.5^1`/`^^1^` 朝 tendril 0.5 格采样，force 逐玩家发送，1.20.1 force 仅 per-player 重载）。裁决不做：原包玩家双计数 quirk（统一 200 tick 时间轴）、`spells.transmutate.submergence`（原资源包无此 ogg，原包即哑弹） | 288 项回归全绿；建议进游戏连画面核对触须观感 |
| 反射器 reflector | ~~部分实现~~ 已补全（2026-10-18）：对照原包 block/384 接入威力缩放——反射器持续=200+power×20 tick、容量=20+power×10，power=min(50, global.power+resonance.power)（R4 SpellStats 供数，`SeelicField.finish` REFLECTANCE 分支调 `SpellArea.overrideReflectance`，缩放值随实体 NBT 持久化）。已核对其余环节与原包一致：归属转移（reflectFrom setOwner）、容量消耗范围（3 格内全量扣）、后方 3 格免强制位移、放置偏移 ^^^4。选择语义维持基线"nearest 异主"口径（阶段 67 文档化偏差，GameTest 锁定，视同 R5 quirk 裁决） | 289 项回归全绿（新增缩放+NBT 往返测试） |
| 普通怪 | ~~Death Walker/Apparition 行为近似~~ 已完成（2026-10-18）：自然生成原已在 `NativeEvents.joined`（illusioner 替身拦截，night_wastes 50%→幻影上限 30、cimmerian_gorge 20%→噩梦行者上限 3，与原包 predicate 口径一致）；R8 补齐 AI 细节——Walker 游走崖边检测（前方 0.2 格脚下 5 格全空气→停+转 idle，原包 walk.mcfunction）、slam 爆炸音精确 master 半径 30；Apparition 环境×2 粒子（红尘 25 个 5³ normal+头顶黑尘 force per-player）、whisper/attack/dissipate 改 MASTER 半径 20/50/20、漂移目标改最近 survival 2..13 格+防重叠退避（同类/玩家 ≤2 各后退 0.05）、警觉条件改 `sprint||(!sneak&&moving)`（疾跑分支不受潜行限制）、追击期双眼红尘（+1.625y 局部 ^±0.1 ^ ^0.5）。裁决不做：slam tracechance→hypertrace（原包 block/39 死代码，IsPlayer 门检查 walker 自身恒假）、狂暴外观（rig 模型无装备槽对应物）、生成位置差（观感等价） | 289 项回归全绿 |
| Tony/任务/世界事件 | ~~未实现~~ 已完成（2026-10-18）：对照原包 tony/* 补齐细节——猫名 obfuscated 黑 "VES"/"PER" 夹 light_purple "Tony"、黑色项圈（CollarColor:15）、tony.spawn/disappear 改 MASTER 音源半径 20（NativeEvents 新增 soundMaster 字符串重载，复用已有 masterSound）；消失由 discard 改 setHealth(0)（等价原包 Health:0：20 tick 死亡动画后由 tickDeath 移除，无掉落，isDeadOrDying 防重复触发）；LoreItem.discover 修复章节推进 bug（移除 chapter>0 提前返回，每次阅读 progress+1 封顶 chapterCount，对齐原包 nextentry；旧"绑定后不再推进"为基线偏差，使 dreams 第 2 章在生存不可达），GameTest lore_reveals_original_chapters_and_persists 同步改写锁定新语义+章节上限。保留基线偏差：Tony 生成 ±3 搜索（原包 ±300/±30 单次成功率 ~0.5%，实为不可玩） | 289 项回归全绿 |
| 生存获取链/祭坛仪式演出 | ~~部分简化~~ R9 已补齐演出层（2026-10-19）：功能层核实已等价（DaisRitual 结构检测=原包 scan 布局，DaisMenu 4 配方成本/产物=原包 trader）；演出对齐原包——construct/inculcate 音效改 master 半径 30 音量 5（R7 soundMaster 复用）、取产物粒子换原包组合（flash×5 force per-player + lava×10 + explosion×10 + effects:shockwave/inculcate 360° 径向环复用 R3 EmissaryFlameGeyser 惯例，替换原 ENCHANT+SOUL_FIRE 自由组合）、DaisBlock 新增 dais.hum 120 tick 氛围循环（onPlace 启动 scheduledTick，use 时 hasScheduledTick 检查防重复，区块卸载后右键恢复）。裁决不做：符文大阵 15 骨 rig 动画（native_rigs 无 ajinculcate，需 AJ 矩阵导出另立任务）、投料悬浮路径（GUI 方案无对应物） | 289 项回归全绿；观感建议进游戏核对 |
| 全流程对照验收 | 未做原包录像逐项对照 | 建立同场景录制对照流程（交接文档 §12） |
| hypertrace 交互×三饰品联动 | ~~heal 6/伤害固定/soul 位置偏差~~ R10 已对齐（2026-10-19）：原包右键=pickup（+1 charge，潜行=全收 pickup_all）、左键=destroy（三联动，潜行=全收 destroy_all）——SeelicField interact/hurt 潜行分支全维度遍历自己的 hypertrace 逐个处理；amelioration heal 6→7（去多余 36 格门）+firework 粒子 50 force；extraction soul 改玩家头顶+1y + soul_reap master 10 音量 4；expulsion 伤害接 dissonance.power×3+7（R4 供数）；pickup/destroy 音效改 master 20。新增潜行全收 GameTest | 290 项回归全绿 |
| 武器系统演出补全 | ~~描述承诺未兑现/演出缺失~~ R11 已补齐（2026-10-19）：调研核实获取链已完整（镰刀=工作台配方、wyrmsliver/nullfissure=祭坛合成、semblanceofdoom 原包无生存获取）、击杀收魂已在 NativeEvents.killed（spec 误报撤销）；实际差距 5 项——镰刀 dash 补 speed I 1 秒（原包 dash.mcfunction）、龙刺 slash 补自施 slowness I + eye+1y 前方 1 格 flash(force per-player)+ +1.625y 前方 1.5 格 sweep_attack、龙刺 bolt 命中补 blindness 5 秒（SpellBolt.resolveImpact WEAPON==1 挂 directTarget）、虚缝副手 cast 补自施 slowness + +1.625y 前方 0.5 格 flash(force)、全部音效改 master 定向半径（dash/slash 20 vol4、虚缝主手 woosh 20 vol2、副手 halobeam 30 vol5、末日 swing/shoot 30 vol1，WeaponItem 音效 switch 重构为 soundMaster 按手区分）。新增 GameTest scythe_kill_spawns_soul_ahead_of_player（击杀收魂锁定，291 项） | 291 项回归全绿 |
| 遗物获取/创造栏/命令/JEI/本地化 | ~~遗物仅结构箱/单 tab 平铺/无 give 命令/无 JEI/群系结构无翻译~~ R12 已完成（2026-10-19）：①遗物掉落链补强——BossEntity.dropCustomDeathLoot 三 Boss 确定性掉对应域遗物各 1（serpent→protection_dream+absorption、sorcerer→protection_nightmare+levitation、emissary→protection_dissonance+expulsion）、普通怪 20% 概率掉 accumulation/amelioration、monolith 箱追加 30% 概率 13 遗物随机 pool（slatehenge 已有全遗物 pool 不动）；②创造栏拆 5 tab（weapons/artifacts/materials/spawns/world，Content.tab 辅助按注册名过滤，icon 惰性 Supplier 防类加载期触碰注册表）；③命令——/nyctocosm summon 扩 5 实体（spawn switch 显式化，普通怪允许多只共存）、新增 /nyctocosm give <item> [count]（Content.ALL 全注册名 Tab 补全）；④JEI 只读集成——build.gradle 加 BlameJared 仓库 + compileOnly jei 1.20.1 api 15.20.0.106（运行时不强制），client/compat/ 下 NyctocosmJeiPlugin + DaisRecipeCategory 直读 DaisMenu.recipes() 静态表（RecipeType 用 public 构造器，15.20.0.106 的 create 是 (String,String,Class) 签名），未装 JEI 零影响；⑤本地化——biome×8/structure×5/itemGroup×5/message.given 双语键（en_us 自拟名+zh_cn 翻译：夜荒地/霜漠/狱渊裂隙/幽暗峡谷/暮野/暗沼/暮光林地/使徒领域，原资源包无权威值）。新增 GameTest boss_death_drops_domain_artifacts | 292 项回归全绿；JEI 观感与创造栏分类需进游戏核对 |
| 本地化 | 中英双语已带 | 校对 + 补 Boss 标题/字幕/世界文本 |

***

## 四、构建与验证命令

```powershell
.\gradlew.bat assemble                # 构建（禁止自动加 clean）
.\gradlew.bat runGameTestServer       # 292 项回归，logs 必须出现 All ... tests passed
.\gradlew.bat runClient -Psmoke       # 客户端自动冒烟场景（NativeSmoke 测试世界）
```

验收规则（继承自基线项目）：不能只看 `BUILD SUCCESSFUL`，以 `run/logs/latest.log` 的测试计数为准；Boss 逻辑改动需同时跑 GameTest 与真实客户端连续画面（高速运动不能只看截图）。

***

## 五、命名空间约定

| 层级 | 值 | 说明 |
| ---- | ---- | ---- |
| Java 包 | `com.yifei.nyctocosm` | 已完成替换（94 文件） |
| Forge modid | `nyctocosm` | 与原数据包/资源包同命名空间，**不可同时加载** |
| 资源 | `assets/nyctocosm`、`data/nyctocosm` 等 | 含 `native_rigs/*.json.gz`（10 rig + 5607 动画帧），模型纹理音效已内嵌 JAR，无需原资源包 |
| 版本号 | `<语义版本>-forge-<MC版本>` | 如 `0.4.0-alpha.1-forge-1.20.1` |

许可：美术/文本版权归原作者 Flubberschnub（All Rights Reserved），公开发布前需核实授权。
