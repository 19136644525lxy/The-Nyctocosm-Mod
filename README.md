# Nyctocosm 永夜之国（Forge 1.20.1 原生重构）

基于 Flubberschnub 原作《Nyctocosm》数据包 + 资源包（v0.2.0，MC 1.20.5+）的原生 Forge 1.20.1 重构。

**原作者**：[Flubberschnub](https://www.youtube.com/@FlubberschnubSchnyxteboutle)（YouTube）
**贡献者**：EchoAria

## 授权与所有权（已获原作者明确授权）

- **原数据包（datapack）及全部原作资产**（模型/纹理/音效/文本/美术）：版权归 **Flubberschnub** 所有，原作者保留其原始数据包的所有权。
- **本 Forge 原生重写实现与代码**：归 **Yifei、EchoAria 及本项目贡献者** 所有。
- 原作者已明确授权：允许维护本仓库、分发 Forge 模组并持续扩展。
- 本项目采用 **Apache License 2.0** 开源许可证（详见 [`LICENSE`](LICENSE) 文件）；原数据包与原作资产仍归 Flubberschnub 所有，以授权形式随模组分发。
- 分发要求：本模组在任何平台（GitHub / CurseForge / Modrinth 等）分发时，均须保留对原作者的署名与原项目链接，并保持免费开放、欢迎社区贡献。

- **Minecraft**: 1.20.1　**加载器**: Forge 47.4.27　**Java**: 17
- **modid**: `nyctocosm`（与原数据包/资源包同命名空间，**不可同时加载**）
- **当前版本**: 见 [gradle.properties](gradle.properties) 的 `mod_version`

***

## 一、构建

```powershell
.\gradlew.bat assemble        # 产出 JAR + *-sources.jar 到 build/libs/
```

- 禁止在命令前自动加 `clean`，除非明确要求清理。
- JEI 集成为 `compileOnly` 依赖（BlameJared 仓库），运行时装不装 JEI 均可；装了 JEI 可在物品栏查询祭坛配方。

## 二、运行与回归测试（开发环境）

```powershell
.\gradlew.bat runClient                # 启动开发客户端
.\gradlew.bat runClient -Psmoke        # 客户端自动冒烟场景
.\gradlew.bat runGameTestServer        # 292 项 GameTest 回归
```

**回归环境协议（必须遵守，否则出现假失败）**：
1. 跑 `runGameTestServer` 前必须删除 `run\world\`——scoreboard objective 与测试方块会持久化到世界，复用旧世界会连坐多个测试假失败（boss_health / ice_spikes / source_passthrough / crypt_trigger）。
2. 验收以 `run\logs\latest.log` 中 `All 292 required tests passed` 为准，不能只看 BUILD SUCCESSFUL。
3. `serpent_body` 系列测试读取外部参考 `f:\Work\_nyctocosm_source\datapack\`（由项目根 `Nyctocosm Datapack v0.2.0.zip` 解包）。

***

## 三、游戏内完整流程

### 3.1 传送门 → 主维度

1. **前置——获得灵魂瓶**：制作 **Seelic Scythe（镰刀）**，用它击杀生物后地上会散落灵魂，手持**玻璃瓶**右键灵魂即可收进瓶中获得**灵魂瓶（Soul Bottle）**。
2. **找到远古城市传送门**：传送门位于主世界深暗之域的**远古城市**结构中，自然生成，框内覆盖幽匿脉络——无需自己搭建。
3. **共鸣法术开启**：手持灵魂瓶丢出**回响碎片**施放 Resonance 法术，一道光束轰向传送门（三波开启演出 + 幽匿灵魂粒子），框内填充 `nyctocosm:portal` 方块。
4. 走入传送门进入 **nyctocosm 主维度**。开发中也可用 `/nyctocosm travel` 快速往返（见命令表）。
5. **回程**：在维度内丢下**末影之眼**施放 Evanescence 法术传送回主世界——这是除死亡外唯一的离境方式。

### 3.2 法术系统（灵场施法）

所有玩家法术共用同一条施法链——**灵场（Seelic Field）**：

1. 手持**灵魂瓶（Soul Bottle）** 右键丢出对应**施法材料**（每种法术一种材料）。
2. 地面生成灵场实体开始施法，期间出现法阵粒子；再次丢材料可叠加。
3. 施法完成后自动释放法术（弹道/区域效果）。

**六元素**：dream（梦）/ radiance（辉光）/ resonance（共鸣）/ nightmare（梦魇）/ void（虚空）/ dissonance（失调），各有专属弹道视觉与效果（燃烧/击退/减速/失明等）。

**特殊法术**：
- **Submergence（没溺）**：召唤 4 条触须，减速并逐 tick 拖拽受害者（可穿墙入地），持续结束统一结算伤害。
- **Reflectance（反射器）**：放置反射器，将敌方弹道归属反转；持续与容量随威力缩放。
- **Hypertrace（超痕）**：放置追踪印记（见 3.4 饰品交互）。
- **Incurrence（招引）**：丢**枯萎玫瑰**施法 9 秒，在对应生物群系召唤 Boss（见 3.7）。

**法术属性（SpellStats）**：7 个域（全局+六元素）× power/speed/tracechance/resistance/weakness，存于玩家数据。power 加弹道伤害、speed 缩短施法时间、weakness/resistance 修正承伤。来源包括饰品与 Boss 掉落进度。

### 3.3 灵魂瓶的两种形态

- **灵魂瓶（Soul Bottle）**：展开固定灵场的施法媒介，丢材料施法。
- **位面线圈（Planar Coil）**：展开跟随自身移动的灵场（移动施法形态）。

### 3.4 饰品（13 枚）

**获取途径**：
| 来源 | 掉落 |
| ---- | ---- |
| 石阵祭环（slatehenge）结构箱 | 13 遗物中随机 1 |
| 巨石碑（monolith）结构箱 | 30% 概率 13 遗物中随机 1 |
| Serpent（巨蛇） | protection_dream + absorption 各 1 |
| Sorcerer（术士） | protection_nightmare + levitation 各 1 |
| Emissary（使徒） | protection_dissonance + expulsion 各 1 |
| 噩梦行者（Death Walker） | 20% 概率 accumulation |
| 幻影（Nightmare Apparition） | 20% 概率 amelioration |

**效果**（背包持有即生效）：
- **absorption 吸收**：近战命中吸血 20%。
- **accumulation 累积**：每击杀 +10% 追踪计数。
- **amelioration 改善**：灵场施法时治疗。
- **levitation 悬浮**：悬空潜行缓降（消耗 3 充能）。
- **expulsion 驱逐**：击杀敌人时触发爆炸领域。
- **protection_*（6 系）**：对应元素承伤减免，消耗 hypertrace 充能（200 tick 共享冷却）。

**Hypertrace 交互**（放置的追踪印记实体）：
- **右键**：回收 +1 充能；**潜行右键**：全维度所有自己的 hypertrace 一次全收。
- **左键攻击销毁**：触发持有饰品的联动——佩戴 amelioration→治疗 3.5 心 + 烟花粒子；佩戴 extraction→头顶召唤灵魂；佩戴 expulsion→威力爆炸（dissonance.power×3+7 伤害）；**潜行左键**：全部一次全炸。

### 3.5 武器（4 件）

| 武器 | 获取 | 技能 |
| ---- | ---- | ---- |
| **Seelic Scythe 镰刀** | 工作台：回响碎片×3 + 棍×2 | 潜行+右键 dash 冲刺（speed I）；击杀敌人收魂（头前生成 soul + soul_reap 音效） |
| **Wyrm's Liver 龙刺** | 祭坛合成 | 右键发射弹道，命中附加失明 5 秒 |
| **Nullfissure 虚缝** | 祭坛合成 | 主手挥砍音波；副手施放法术弹 |
| **Semblance of Doom 末日之形** | 原包无生存获取（指令物） | 范围挥扫 |

### 3.6 祭坛（Dais）——仪式合成

**结构搭建**（7×7，以信标为基准）：
1. 中心：**信标**，正上方放**铁砧**。
2. 信标四向 1 格：**石英块** ×4；四向 2 格：**石英楼梯** ×4（朝外、下半砖）。
3. 四角对角（±1,±1）：**涂蜡切制铜台阶** ×4。
4. 外沿十字：**点燃的蜡烛** ×12（必须用打火石点着）。

**激活流程**：手持灵魂瓶**朝铁砧方向施法**（非右键铁砧——那会打开原版铁砧界面；原数据包为"右键铁砧正上方激活"，本模组因施法统一走灵场路径而改为施法激活）。灵场张开瞬间检测面前 1 格满结构，通过则铁砧替换为 Dais 方块 + construct 演出（30 格音效）。

**使用**：右键 Dais 打开合成界面，4 个配方（成本与原包流浪商人一致，wyrmsliver/nullfissure 等）。取产物触发原包演出（flash + lava + explosion + 360° 径向冲击环）。祭坛闲置时每 6 秒发出 hum 氛围音。

### 3.7 Boss 招引与讨伐

**正统召唤**（不用刷怪蛋）：手持灵魂瓶 + **枯萎玫瑰**，在对应生物群系丢出施法（INCURRENCE，9 秒）：

| 生物群系 | Boss |
| ---- | ---- |
| Night Wastes 夜荒地 | **Serpent 巨蛇** |
| Desert of Rime 霜漠 | **Sorcerer 术士** |
| Infernal Chasms 狱渊裂隙 | **Emissary 使徒**（拉入专属维度 + 强制冒险模式） |

群系不符只提示"不可招引"。击杀掉落对应域遗物各 1 + 材料（梦魇鳞片/灵场精华等）。

**Boss 简况**：
- **Serpent**：多节身体（multipart 部位命中），冰尖波/光束危险区。
- **Sorcerer**：时间线多阶段，冰锥/冰刃/光束节点/冲刺/碎片冰雹。
- **Emissary**：六招——剑落（石头尾迹+红尘警示圈+105t 三段冲击波环+火场）、火柱、翼爆（红尘警示线）、火焰斩、火焰间歇泉、天启之雨；完整翅膀受击部位。

### 3.8 普通怪

- **Nightmare Apparition 幻影**：night_wastes 由 phantom 替身转化（上限 30）。漂浮漂移，玩家**走动或疾跑**时警觉（潜行移动不警觉），追击时双眼红尘，消失于黑雾（掉落见 3.4）。
- **Death Walker 噩梦行者**：cimmerian_gorge 由 illusioner 替身转化（上限 3）。游走（崖边自动停步），slam 跺地爆炸（30 格音效）。

### 3.9 Tony 事件

在 **Dream Crypt（梦境地窖）** 结构内、光照为 0 处停留，冷却 10-20 分钟后随机刷新黑猫 **"Tony"**（乱码夹紫色名字 + 黑项圈）。它会看你 20-40 秒然后消失，掉落 **dreams（梦境之书）**——LoreItem，右键逐章阅读（重读推进，封顶章节数）。

### 3.10 结构与战利品

| 结构 | 内容 |
| ---- | ---- |
| Slatehenge 石阵祭环 | 遗物箱（13 遗物随机 1）+ 杂物 |
| Monolith 巨石碑 | catechism + 30% 概率遗物 |
| Dream Crypt 梦境地窖 | Tony 触发 + 进度 |
| Outpost 前哨站 | ancient_journals |
| Nightmare Fossil 梦魇化石 | 氛围结构 |

***

## 四、命令（权限等级 2）

| 命令 | 作用 |
| ---- | ---- |
| `/nyctocosm summon serpent\|sorcerer\|emissary\|death_walker\|nightmare_apparition` | 在面前 8 格召唤（普通怪可多只；Emissary 全维度唯一） |
| `/nyctocosm travel nyctocosm\|dusk_plane\|emissary_domain` | 传送到指定维度 |
| `/nyctocosm return` | 从子维度返回主维度 |
| `/nyctocosm give <item> [count]` | 发放任意注册物品（Tab 补全） |

## 五、创造栏与 JEI

- 创造栏分 5 类：**武器与法器 / 遗物 / 材料 / 刷怪蛋 / 世界与仪式**。
- 安装 JEI 后：搜索物品名可查**祭坛配方**（双输入+单输出，含数量）；未装 JEI 零影响。

## 六、本地化

- `en_us.json` / `zh_cn.json` 双语。群系（夜荒地/霜漠/狱渊裂隙/幽暗峡谷/暮野/暗沼/暮光林地/使徒领域）与结构（梦境地窖/石阵祭环/梦魇化石/巨石碑/前哨站）均已汉化。

***

## 七、项目结构（分包）

```
com.yifei.nyctocosm/
├── NyctocosmMod / Content / CommonEvents   # 入口、注册中心、全局事件
├── spell/       # SeelicField、SpellBolt/SpellArea、Reflectance、HypertraceService 等
├── combat/      # Combat 六元素伤害
├── boss/        # Boss 门面 + BossEntity 基类 + sorcerer/serpent/emissary 三控制器 + OrdinaryController
├── item/        # WeaponItem、FocusItem、ArtifactItem、LoreItem
├── world/       # portal/（PortalBlock 等）、structure/
├── dais/        # DaisBlock/DaisMenu/DaisRitual
├── entity/      # SoulEntity、TonyManager
├── network/     # Network
├── migration/   # LegacyMigration（旧数据包存档迁移）
├── client/      # 渲染/音效/界面 + compat/（JEI）
└── gametest/    # GameTests 系列（不进发布 JAR）
```

动画采用 native_rigs 采样矩阵方案（`assets/nyctocosm/native_rigs/*.json.gz`，AJ 逐帧矩阵离线导入 + 运行时 TRS 插值）；饰品为纯 Java 库存检测（无外部前置依赖）。
