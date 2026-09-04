# Nyctocosm 1.20.1 Forge 移植 - 开发进度

> **原作**: Flubberschnub（数据包 + 资源包 v0.2.0，基于 MC 1.20.5+）
> **Forge 移植**: Yifei
> **目标版本**: Minecraft 1.20.1 Forge 47.4.23
> **Java 包**: `com.yifei.nyctocosm`
> **Mod ID**: `nyctocosm`
> **更新日期**: 2026-09-02

***

## 当前状态 & 下一步（速览）

**已完成**：P0 构建配置 / P1 基础注册 / P2 自研法术 API 框架 / **P3 17 法术具体实现** ✅ / **P3.5 精确蜡烛阵 + 17 催化剂匹配** ✅

**下一步优先级**（推荐顺序，P4 立刻开工）：

| 优先级  | 阶段      | 内容                                                | 依赖        |
| ---- | ------- | ------------------------------------------------- | --------- |
| 🔥 高 | **P4**  | Curios API 集成：戒指触发施法 + 灵魂瓶(法术容量) + 10 种圣物(被动BUFF) | P3.5 ✅    |
| ⏸️ 中 | **P5**  | AJ→GeckoLib 坐标映射修复（Z 轴符号相反）                       | P4 并行不阻塞  |
| ⏸️ 低 | **P6+** | Boss 实体 / 维度世界生成 / 网络 HUD 同步 / 配置界面               | 视 P4 进度推进 |

***

## 一、项目总览

将 Nyctocosm 数据包+资源包组合移植为 1.20.1 Forge 模组。当前策略已由「包装式移植」升级为**全 Java 化**：

- **自研法术 API**（替代原 `spells` 命名空间的 scoreboard 驱动逻辑）

- **GeckoLib 动画系统**（替代原 Animated Java 的 item\_display 矩阵驱动骨骼系统）

- **Boss 实体 Java 化**（替代原 `sorcerer/emissary/serpent` 的 mcfunction 状态机）

- 数据包 `.mcfunction` 与资源包 JSON 暂时保留以方便对照参考，功能替换完成后可移除对应命名空间。

### 前置依赖 JAR（已配置 `flatDir` 本地依赖）

| 模组         | 版本                  | 用途                     |
| ---------- | ------------------- | ---------------------- |
| GeckoLib   | forge-1.20.1-4.8.4  | 动画系统（替代 Animated Java） |
| Curios API | forge-5.14.1+1.20.1 | 饰品槽位（戒指/灵魂瓶等）          |

***

## 二、阶段定义与完成度（P0 \~ P11）

### P0 - 项目骨架 + 构建配置 ✅ 完成

| 项                | 内容                                                                      |
| ---------------- | ----------------------------------------------------------------------- |
| mod\_id / Java 包 | `nyctocosm` / `com.yifei.nyctocosm`                                     |
| Forge / 映射       | 47.4.23 / official 1.20.1                                               |
| 构建脚本             | `build.gradle` 清理模板注释，配置 `withSourcesJar()`，`flatDir` 引入 `libs/` 本地 JAR |
| mods.toml        | 声明 `geckolib` / `curios` 前置依赖，作者 `Yifei, Flubberschnub`                 |
| 源码 JAR           | 产出 `nyctocosm-*-sources.jar`（全局强制规则）                                    |

### P1 - 基础 Java 注册系统 ✅ 完成

#### 伤害类型注册

- `NyctocosmDamageTypes`：6 元素 + 通用 + 3 Boss 变体 共 10 个 `ResourceKey<DamageType>`

- `NyctocosmDamageSources`：类型安全的 `DamageSource` 构建工具

- 对应 `data/*/damage_type/*.json` **保留**（数据驱动注册表，JSON=定义，Java=引用）

#### 自定义物品包装式入口

- `NyctocosmItems`：25 个 `ItemStack`（SNBT 注入 CustomModelData + NBT 字段）

- 创造模式栏 `nyctocosm:main`（标题「永夜之国」，图标灵魂镰刀）

- 分类：镰刀(4) / 圣物(10) / 卷册(5) / 载体(3) / 材料(4)

#### 粒子系统

- `NyctocosmClient`：6 种元素粒子类型注册 + provider 订阅

- `ElementParticle`：通用元素粒子基类（DRY 设计）

#### 实体系统骨架

- `NyctocosmEntities`：`DeferredRegister<EntityType<?>>`，已注册 `SPELL_PLANE`

- `SpellPlaneEntity`：法术平面实体（实现 `GeoEntity`，3 状态动画机 IDLE/CAST/FIZZLE，`SynchedEntityData` 服务端→客户端同步）

- `NyctocosmGeoModel` + `SpellPlaneModel` + `SpellPlaneRenderer`：GeckoLib 模型/渲染器骨架

#### 标签注入

- `EntityTypeTagInjector`：动态将所有 LivingEntity/Mob 注入伤害标签，解决模组怪物对魔法伤害免疫的问题

### P2 - 自研法术 API 框架 ✅ 完成（2026-08-31）

**目标**：用 Java OOP 设计替代原 `spells` 命名空间 \~2000 个 mcfunction 的 scoreboard 状态机。

**已交付**：6 个核心类 + 17 个法术占位实现 + Capability 持久化 + 服务端调度器，构建通过（`nyctocosm-0.3.0-rc-1.jar` + 源码 JAR 产出）。

#### P2-1：枚举与分类 ✅

- `ElementType`（6 元素，位掩码组合，对应 s.type1\~6）：

  - `DREAM` / `RADIANCE` / `RESONANCE` / `NIGHTMARE` / `VOID` / `DISSONANCE`

  - 每个元素绑定：DamageType Key + 粒子 ARGB 颜色 + HUD 高亮色

  - `byIndex(int)` / `fromMask(int)` 便捷查询

- `SpellCategory`（7 分类）：BUFF / DEBUFF / INSTANT\_DAMAGE / PROJECTILE / AOE / SUMMON / UTILITY

#### P2-2：Spell 抽象基类 + 注册中心 ✅

- `Spell` 抽象类（不可变单例）：`id` / `elementMask` / `baseDamage` / `castTime` / `duration` / `power` / `category`

- 生命周期钩子（默认空实现）：`canCast` → `onStartCast` → `onTickCasting` → `onComplete` → `onTickActive` → `onExpire` / `onCancelled`

- `SpellRegistry`：ConcurrentHashMap 静态注册表（非 Forge Registry），`init()` 注册 17 法术，支持附属模组 `addSpell()` 扩展

- 17 个法术 impl 占位类（`spell/impl/*.java`）：元数据与原 `load.mcfunction` 完全一致

#### P2-3：玩家 Capability 法术数据 ✅

- `ISpellPlayerData` 接口：吟唱状态 / 法术属性 / 6 元素统计 / 持续法术 Map / 法术平面 UUID 绑定

- `SpellPlayerData` 实现：ConcurrentHashMap + 原生数组，NBT 序列化/反序列化（容错读取）

- `SpellPlayerProvider`：`ICapabilitySerializable<CompoundTag>`，LazyOptional 包装，自动 invalidate

#### P2-4：施法调度器 ✅

- `SpellCastingManager`（@Mod.EventBusSubscriber 自动注册）：

  - `PlayerTickEvent` 推进吟唱 + 持续法术 + 移动取消检测

  - `PlayerEvent.Clone` 玩家死亡/末地返回时清理吟唱状态

  - `tryStartCast(Player)` 外部 API：蜡烛扫描 → 遍历法术 canCast → 启动吟唱

  - `scanLitCandles(Player)`：7×7 范围内 ≥16 根点燃 CandleBlock/CandleCakeBlock

  - `playPlaneAnim(Player, byte)`：通过 UUID 查找并驱动绑定的 SpellPlaneEntity 播放 CAST/FIZZLE

  - 异常隔离：单个法术 `onComplete` / `onTickActive` 异常不崩服务器

#### P2-5：Capability 注册与挂载 ✅

- `SpellCapabilities`（MOD 总线）：

  - `RegisterCapabilitiesEvent` 注册 `ISpellPlayerData` Capability

  - `FMLConstructModEvent` 调用 `SpellRegistry.init()` 注册 17 法术

- 嵌套 `ForgeBusHandlers`（Forge 总线）：

  - `AttachCapabilitiesEvent<Entity>` 给 Player 挂载 SpellPlayerProvider

  - `PlayerEvent.Clone` 复制死亡玩家的统计/属性/持续法术（不复制吟唱状态/平面 UUID）

#### 构建验证 ✅

- `gradlew assemble` 成功

- 产出：`build/libs/nyctocosm-0.3.0-rc-1.jar` (83MB) + `nyctocosm-0.3.0-rc-1-sources.jar` (83MB)

- 启动期日志预期：`[SpellCapabilities] 已注册 ISpellPlayerData Capability` + `[SpellRegistry] 已注册 17 个法术`

#### P2 遗留（P3 处理）

- 蜡烛阵精确还原（原数据包 20 个固定位置，当前为简化 7×7 区域）

- 蜡烛+掉落物的精确法术匹配（当前遍历注册表取首个 canCast，P3 改为按掉落物精确匹配）

- 网络同步：客户端 HUD 需要的吟唱进度/持续法术列表（P2 用 DataParameter 暂代，P8 实现专用网络包）

### P3 - 17 种法术具体实现 ⏳ 待做

从 `spells/functions/load.mcfunction` 提取的 17 法术元数据：

| 法术            | 元素组合                | 基础伤害 | 持续(tick) | 吟唱(tick) | 分类              |
| ------------- | ------------------- | ---- | -------- | -------- | --------------- |
| Luminescence  | RADIANCE            | 0    | 6000     | 0        | BUFF（照明）        |
| Incurrence    | NIGHTMARE           | 0    | 0        | 180      | SUMMON（招致）      |
| Evanescence   | RESONANCE+VOID      | 0    | 0        | 100      | UTILITY（消逝）     |
| Resonance     | RESONANCE           | 60   | 0        | 80       | PROJECTILE（投射物） |
| Abhorrence    | NIGHTMARE           | 15   | 30       | 40       | DEBUFF（憎恶）      |
| Forbiddance   | DISSONANCE          | 0    | 35       | 10       | DEBUFF（禁制）      |
| Gravitance    | VOID                | 20   | 200      | 60       | AOE（引力）         |
| Subsistence   | RADIANCE            | 10   | 0        | 40       | BUFF（维生）        |
| Reflectance   | RESONANCE           | 20   | 200      | 30       | BUFF（反射）        |
| Submergence   | DREAM+VOID          | 8    | 0        | 80       | DEBUFF（浸没）      |
| Adamance      | VOID                | 0    | 200      | 20       | BUFF（坚壁）        |
| Incandescence | RADIANCE+DISSONANCE | 10   | 40       | 20       | AOE（白炽）         |
| Resistance    | RADIANCE            | 0    | 250      | 40       | BUFF（抗性）        |
| Transmittance | VOID                | 15   | 0        | 100      | PROJECTILE（透射）  |
| Vacance       | RADIANCE            | 0    | 100      | 20       | UTILITY（空无）     |
| Repellance    | DISSONANCE          | 0    | 50       | 50       | AOE（斥力）         |
| Fulminance    | RADIANCE+DISSONANCE | 7    | 20       | 30       | AOE（轰雷）         |

每个法术在 `com.yifei.nyctocosm.spell.impl` 包下对应一个类，override `onComplete()` / `onTickActive()`。

### P3 - 17 种法术具体实现 ✅ 完成（2026-09-01）

17 个法术已全部覆盖原 `cast.mcfunction` / `transmutate.mcfunction` 的核心行为：

| 分类           | 法术                                                                         | 核心效果（Java 实现）                  |
| ------------ | -------------------------------------------------------------------------- | ------------------------------ |
| BUFF×6       | Luminescence / Subsistence / Resistance / Adamance / Reflectance / Vacance | 发光、治疗、抗性提升、坚壁反伤护盾、荆棘反射、漂浮隐身    |
| DEBUFF×3     | Forbiddance / Abhorrence / Submergence                                     | 禁制虚弱失明、凋零 DoT、双元素伤害+地面目标缓慢+失明  |
| AOE×4        | Gravitance / Incandescence / Repellance / Fulminance                       | 引力拉拽+虚空伤害、点燃双 AOE、斥力击退、大范围轰雷闪光 |
| PROJECTILE×2 | Resonance / Transmittance                                                  | 共振射线单目标 60 伤害、虚空透射穿透多目标        |
| SUMMON×1     | Incurrence                                                                 | 按生物群系决定 Boss + 梦魇粒子（P6 后实装召唤）  |
| UTILITY×1    | Evanescence                                                                | 速度+抗性+夜视+隐身（使者领域内禁用）           |

**P3 配套交付**：

- `SpellHelpers`：粒子/伤害/MobEffect/目标筛选/法术圈/定时移除/ActionBar 等 15+ 工具方法

- `ElementType.getChatFormat()`：命令/提示用元素颜色

- `SpellCastingManager.castDirect()`：命令专用直施法 API

- `command.NyctocosmCommands`：`/nyctocosm list|cast <id>|cancel|status [player]`（Forge `RegisterCommandsEvent`）

**构建验证** ✅：`gradlew assemble` 成功 → `nyctocosm-0.3.0-rc-1.jar` + `*-sources.jar`

**API 差异修正（MC 1.20.5+ → Forge 1.20.1）**：`MOVEMENT_SLOWNESS→MOVEMENT_SLOWDOWN`，`isOnGround()→onGround()`，`ArmorStand.setMarker` 退化反射，`NbtUtils.readItem→ItemStack.of`，命令注册 `root→root.build`

***

### P3.5 - 蜡烛阵精确还原 + 掉落物匹配 ✅ 完成（2026-09-02）

**原数据包解析结论**（替换 P2 简化版「7×7 任意 16 根 + 遍历注册表」）：

| 组件                           | 原实现（精确对照 spells:scan / block/7.mcfunction）                                                                                                                                                                                                                                                                                                                                                        |
| ---------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **蜡烛阵 16 固定坐标（颜色不限，必须全部点燃）** | ① X=±3 列 Z∈{0,±1}（6 根）② Z=±3 行 X∈{0,±1}（6 根）③ (±2,±2) 对角（4 根）。颜色不限（CandleBlock \[lit=true] 或 CandleCakeBlock）                                                                                                                                                                                                                                                                                     |
| **17 种互斥催化剂（法术唯一由 Item 决定）** | glowstone\_dust→Luminescence，echo\_shard→Resonance，ender\_eye→Evanescence，wither\_rose→Incurrence，coal→Abhorrence，ghast\_tear→Forbiddance，crying\_obsidian→Gravitance，apple→Subsistence，iron\_ingot→Reflectance，soul\_sand→Submergence，cobblestone→Adamance，gunpowder→Incandescence，cooked\_beef→Resistance，ender\_pearl→Transmittance，glass\_bottle→Vacance，feather→Repellance，quartz→Fulminance |
| **流程**                       | 玩家触发饰品 → verifyMatrix（全部点燃=通过）→ 3 格内 ItemEntity 按 Item.id 匹配 → 消耗 1 个催化剂 → spell.canCast 兜底 → startCasting                                                                                                                                                                                                                                                                                        |

**代码交付**：

- 新增 `spell.CandleMatrixMatcher`（SRP）：

  - `CANDLE_OFFSETS`：16 偏移不可变列表

  - `verifyMatrix(Player)` / `verifyMatrixPosition(Level,BlockPos,BlockPos)`：逐位点燃判定

  - `ITEM_TO_SPELL_ID`：17 Item → SpellId 静态映射（ConcurrentHashMap）

  - `matchAndConsumeCatalyst(Player)`：±3 立方体内查找第一个匹配 ItemEntity → 校验 canCast → shrink 1 或 discard → 返回 Spell

- `SpellCastingManager.tryStartCast(Player)` 改写：顺序 verifyMatrix → matchAndConsume → onStartCast → startCasting；失败均播放 FIZZLE

- 遗留 `scanLitCandles(Player)` 保留为 `verifyMatrix` 委托（向后兼容）

- `/nyctocosm matrix`（权限 2）：逐位显示 16 蜡烛 world 坐标与 ✅/❌，显示通过数/总数

- `/nyctocosm catalyst`（权限 0）：列出 17 催化剂 → 法术（带分类 + 元素组合）

**构建验证** ✅：`gradlew assemble` 成功，产出 `nyctocosm-0.3.0-rc-1.jar` + `*-sources.jar`

***

### P4 - 武器技能 & 饰品（Curios） ⏳ 待做（下一步立刻开始）

- 武器：`seelic_scythe`（冲刺/收割/射线）、`wyrmsliver`（挥砍/召唤）、`nullfissure`（挥舞/投射）
  目前 items 是包装式 `ItemStack`，P4 改为自定义 `Item` 子类 + 右键/攻击事件驱动技能

- 饰品（Curios API）：

  - 戒指（Planar Circlet）：触发法术扫描/吟唱入口

  - 灵魂瓶（Soul Bottle）：法术容量（对应 `bottle/add/subtract`）

  - 圣物（10 种）：被动 BUFF（替代 `artifacts` 命名空间）

### P5 - AJ→GeckoLib 动画转换 ⏳ 搁置（框架完成后回头修）

**当前状态**：

- `tools/aj2geckolib/` 已实现：

  - `matrix_utils.py` 矩阵分解（4×4→R+S+T）+ 四元数 unwrap

  - `aj_parser.py` AJ 数据包 mcfunction 解析器

  - `geckolib_writer.py` .animation.json 生成器

  - `geo_builder.py` .geo.json + 纹理图集生成器（从资源包物品模型提取 cubes/UV）

  - `verify.py` 数值验证器（模拟原版 vs GeckoLib 渲染管线）

  - `convert.py` 主入口

**已知问题**：`verify.py` 检测到 Z 轴符号完全相反（\~5 方块量级误差），原因是 AJ item\_display 的 `transformation` 矩阵映射到 GeckoLib 骨骼 local 坐标时，`Ry(π)` 共轭推导有错误，需要对照 GeckoLib `RenderUtils.prepMatrixForBone` + `BakedModelFactory.constructBone` 重新推导。

**策略**：先搁置，P2-P4 核心功能可用后再回来修，不阻塞主路径。

### P6 - Boss 实体 Java 化 ⏳ 待做

| Boss              | Rig 数量                                               | 复杂度 | 备注                                                                                                                                |
| ----------------- | ---------------------------------------------------- | --- | --------------------------------------------------------------------------------------------------------------------------------- |
| Sorcerer（法师）      | sorcerer / sorcererm1 / sorcererm2 / ajsorcererspear | 高   | 6 阶段 + 10+ 技能（dashstrike/voidwave/singularity/javelinswing/frozenblades/airdash/voidmeteor/voidbeam/halobeam/shardhail/icespikes） |
| Emissary（使者）      | inculcate                                            | 极高  | 4+ 阶段 + 20+ 技能 + 过场动画 + 逃脱阶段                                                                                                      |
| Serpent（巨蛇）       | —                                                    | 中   | 多段身体 + headai 朝向 + 激光                                                                                                             |
| Death Walker（亡行者） | deathwalker / death\_walker                          | 中   | 近战 AI                                                                                                                             |

复用 `SpellPlaneEntity` 的模式：`GeoEntity` + `SynchedEntityData` 同步动画状态 + 独立 AI 控制器类（不写在 Entity 类中，遵循 SRP）。

### P7 - 维度/世界生成 ⏳ 待做

- 2 个 `DimensionType`：`nyctocosm` / `dusk`

- 3 个 `LevelStem`（dimension）：`nyctocosm` / `dusk_plane` / `emissary_domain`

- 8 个 Biome：`dark_mire` / `duskfields` / `night_wastes` / `desert_of_rime` 等

- 结构：`outpost` / `monolith_1` / `slatehenge` / `crypt`

- 世界边界动态缩放（49000000 ↔ 59000000）

### P8 - 网络通信完整化 ⏳ 待做

- 服务端 → 客户端：

  - 玩家法术数据（吟唱进度、持续法术列表、法术平面绑定）

  - Boss 动画状态触发

  - 法术粒子批处理包

- 客户端 → 服务端：

  - 饰品右键/潜行动作（避免只靠 scoreboard 触发器）

  - 武器技能按键

### P9 - 配置系统完善 ⏳ 待做

- `NyctocosmConfig` 现有 11 个开关 → 同步：

  - Forge `ConfigGui`（Cloth Config API 可选前置）

  - `en_us.json` + `zh_cn.json` 配置项翻译

### P10 - 客户端体验（HUD/屏幕特效/本地化）⏳ 待做

- HUD：吟唱进度条（替代原 `spelltimer` scoreboard 显示）、持续法术图标

- 屏幕特效：失谐震屏（原 `effects/predicates/screen_shake`）、虚空视界、梦之迷雾

- 语言文件补全：Boss 技能名、法术效果提示、饰品描述

### P11 - 集成测试与平衡 ⏳ 待做

- 17 法术全流程单测（启动集成服务器 / 玩家模拟）

- 6 元素伤害对原版+模组怪物有效验证（EntityTypeTagInjector 覆盖）

- 数值平衡：`calcstatsspell`（原 scoreboard 伤害计算）→ Java `SpellDamageCalculator`

- bossbar/音乐同步：`music/` 命名空间（emissary/sorcerer 分阶段 BGM）

***

## 三、Java 包结构（含规划中的子包）

```
com.yifei.nyctocosm/
├── NyctocosmMod.java                    # 入口
├── config/NyctocosmConfig.java          # 配置
├── client/
│   ├── NyctocosmClient.java             # 客户端入口（粒子注册、渲染器）
│   ├── ElementParticle.java             # 元素粒子
│   ├── hud/                             # P10：HUD 组件
│   └── geckolib/
│       ├── NyctocosmGeoModel.java       # GeoModel 基类
│       ├── model/                       # SpellPlaneModel 等
│       └── renderer/                    # SpellPlaneRenderer 等
├── damage/
│   ├── NyctocosmDamageTypes.java        # 10 DamageType 常量
│   ├── NyctocosmDamageSources.java      # DamageSource 构建器
│   └── SpellDamageCalculator.java       # P3/P11：法术伤害计算器
├── entity/
│   ├── NyctocosmEntities.java           # EntityType 注册
│   ├── spell/
│   │   └── SpellPlaneEntity.java        # 法术平面实体
│   ├── projectilespell/                 # P3：法术投射物实体（Resonance 等）
│   └── boss/                            # P6：Boss 实体
├── spell/                               # P2/P3：自研法术 API
│   ├── ElementType.java                 # 6 元素枚举（位掩码）
│   ├── SpellCategory.java               # 法术分类枚举
│   ├── Spell.java                       # 抽象基类
│   ├── SpellRegistry.java               # 注册中心
│   ├── SpellCastingManager.java         # 施法调度器（服务端 tick）
│   ├── data/                            # Capability 包
│   │   ├── ISpellPlayerData.java
│   │   ├── SpellPlayerData.java
│   │   └── SpellPlayerProvider.java
│   ├── effect/                          # P3：法术效果（Buff/Debuff/AOE）
│   └── impl/                            # P3：17 个 Spell 实现类
├── item/
│   ├── NyctocosmItems.java              # 创造栏 + 25 包装入口
│   ├── weapon/                          # P4：自定义武器（镰刀等）
│   └── curios/                          # P4：Curios 饰品（戒指/瓶子/圣物）
├── capability/                          # P7+：其他 Capability
├── network/                             # P8：网络包
├── tag/
│   └── EntityTypeTagInjector.java       # 动态伤害标签注入
├── world/                               # P7：维度/生物群系/结构
└── util/                                # 公共工具（RNG、Vec3 数学等）
```

***

## 四、关键设计原则

1. **全 Java 化优先**：scoreboard / mcfunction 驱动逻辑 → Java OOP 替代，保留 JSON 对照方便对齐
2. **SRP 严格执行**：Entity 类只保留持久化/同步字段，AI/技能下沉到独立 Controller/Strategy 类
3. **数据一致性**：服务端 authoritative，所有状态变更用 `SynchedEntityData` / 自定义网络包推送到客户端
4. **类型安全优先**：所有 DamageType / EntityType / Item 用 DeferredRegister + Java 常量，不使用魔法字符串
5. **线程安全**：服务端调度器使用线程安全容器；渲染端所有 GeckoLib 状态只读，不跨线程修改
6. **源码 jar 强制**：`build.gradle` 保留 `withSourcesJar()`
7. **清理模板注释**：所有 MDK/官方模板注释已清理，仅保留功能性中文注释
8. **构建命令不加 clean**：除非用户明确要求「清理并构建」

***

## 五、命名空间约定

| 层级          | 命名空间                                                                            | 说明                                    |
| ----------- | ------------------------------------------------------------------------------- | ------------------------------------- |
| Java 包      | `com.yifei.nyctocosm`                                                           | 按 Yifei 要求                            |
| Forge modid | `nyctocosm`                                                                     | modid 不能含点号                           |
| 数据包命名空间     | `nyctocosm` / `magic` / `spells` / `sorcerer` / `emissary` / `serpent` / `aj_*` | 暂时保留，对应 Java 模块完成后逐个移除                |
| 资源包命名空间     | `nyctocosm` / `space` / `particle` / `animated_java`                            | 保留纹理/模型，geo/animation 由 Python 脚本生成覆盖 |

***

## 六、运行时验证清单（按阶段）

### 已完成验证（构建）

- [x] `gradlew assemble` 构建成功，产出 JAR + 源码 JAR

- [x] 10 DamageType + 25 物品 + 6 粒子 + 1 实体注册无报错

### 待验证（按 P 顺序）

- **P2**：服务端启动无事件泄漏；客户端进入世界无 ClassCast；`/function spells:*` 等价命令（后续用命令注册）

- **P3**：17 法术 `onComplete()` 效果与原 mcfunction 行为一致（粒子/伤害/标记/实体召唤）

- **P4**：Curios 戒指穿戴后，潜行+右键 16 蜡烛正确匹配法术，吟唱结束触发

- **P5**：`verify.py` 全部通过（顶点误差 <1e-3，UV 误差 <1e-6）

- **P6**：Boss 实体能召唤、能受伤、能按阶段切换动画与技能

- **P11**：全流程集成 + 平衡数值与原数据包一致

***

## 七、参考资料

- Forge 映射源码：`f:\Work\Nyctocosm\Nyctocosm Mod\temp\forge-1.20.1-47.4.23_mapped_official_1.20.1-sources\`

- GeckoLib 1.20.1 源码：`f:\Work\Nyctocosm\Nyctocosm Mod\temp\geckolib-1.20.1\`

- Curios API 1.20.1 源码：未提供（用法参考官方文档 + 示例）

- 原数据包：`f:\Work\Nyctocosm\Nyctocosm Datapack v0.2.0\`

- 原资源包：`f:\Work\Nyctocosm\Nyctocosm Resources v0.2.0\`

- 1.20.1 `Display` 类：`net.minecraft.world.entity.Display`

- 1.20.1 `DamageType` 注册表：`Registries.DAMAGE_TYPE`（已后向移植）

- 转换脚本：`tools/aj2geckolib/*.py`（P5 阶段用）

