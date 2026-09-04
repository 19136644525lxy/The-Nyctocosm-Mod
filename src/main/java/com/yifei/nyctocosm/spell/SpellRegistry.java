package com.yifei.nyctocosm.spell;

import com.yifei.nyctocosm.NyctocosmMod;
import com.yifei.nyctocosm.spell.impl.*;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * 法术注册中心（简单静态注册表，非 Forge 原生 IForgeRegistry）。
 * <p>
 * 为什么不用 Forge 自定义注册表？Spell 是纯业务单例，不参与存档/数据包同步/命令参数解析，
 * 直接用线程安全的 ConcurrentHashMap 更简洁。后续若附属模组需要扩展，可通过静态 addSpell() 方法注册。
 *
 * 设计：
 * <ul>
 *   <li>启动时通过 {@link #init()} 一次性注册 17 个原版法术（P3 填充每个实现类的逻辑）</li>
 *   <li>附属模组可调用 {@link #addSpell(Spell)} 追加自定义法术</li>
 *   <li>按元素/分类筛选的辅助方法方便 UI 与调度器使用</li>
 * </ul>
 */
public final class SpellRegistry {

    /** 注册表 Key：法术小写 ID → Spell 单例 */
    private static final Map<String, Spell> REGISTRY = new ConcurrentHashMap<>(32);
    /** 反向索引：按元素掩码加速筛选 */
    private static final Map<Integer, List<Spell>> BY_ELEMENT_CACHE = new ConcurrentHashMap<>();
    /** 反向索引：按分类加速筛选 */
    private static final Map<SpellCategory, List<Spell>> BY_CATEGORY_CACHE = new ConcurrentHashMap<>();

    /** 是否已经初始化（重复调用 init 幂等） */
    private static volatile boolean initialized = false;

    private SpellRegistry() {}

    /**
     * 初始化注册 17 个 Nyctocosm 原版法术（由 {@link NyctocosmMod} 构造函数调用，仅首次调用有效）。
     * <p>
     * <b>顺序</b>与原 spells/functions/load.mcfunction 中 scoreboard set 顺序一致，方便对照：
     * <pre>
     * Luminescence → Incurrence → Evanescence → Resonance → Abhorrence → Forbiddance → Gravitance
     * → Subsistence → Reflectance → Submergence → Adamance → Incandescence → Resistance
     * → Transmittance → Vacance → Repellance → Fulminance
     * </pre>
     */
    public static synchronized void init() {
        if (initialized) return;
        initialized = true;

        addSpell(new LuminescenceSpell());
        addSpell(new IncurrenceSpell());
        addSpell(new EvanescenceSpell());
        addSpell(new ResonanceSpell());
        addSpell(new AbhorrenceSpell());
        addSpell(new ForbiddanceSpell());
        addSpell(new GravitanceSpell());
        addSpell(new SubsistenceSpell());
        addSpell(new ReflectanceSpell());
        addSpell(new SubmergenceSpell());
        addSpell(new AdamanceSpell());
        addSpell(new IncandescenceSpell());
        addSpell(new ResistanceSpell());
        addSpell(new TransmittanceSpell());
        addSpell(new VacanceSpell());
        addSpell(new RepellanceSpell());
        addSpell(new FulminanceSpell());
    }

    /** 注册法术（线程安全，启动期/附属模组均可调用）。若 ID 冲突记录警告并忽略。 */
    public static void addSpell(Spell spell) {
        Spell prev = REGISTRY.putIfAbsent(spell.getId(), spell);
        if (prev != null) {
            NyctocosmMod.LOGGER.warn("[SpellRegistry] 法术 ID 冲突被忽略：{} (已有 {}, 新 {})",
                    spell.getId(), prev.getClass().getSimpleName(), spell.getClass().getSimpleName());
            return;
        }
        // 清空缓存（新法术加入后重建）
        BY_ELEMENT_CACHE.clear();
        BY_CATEGORY_CACHE.clear();
    }

    // ========== 查询 ==========

    @Nullable
    public static Spell byId(String id) { return REGISTRY.get(id); }

    @Nullable
    public static Spell byId(ResourceLocation id) {
        return NyctocosmMod.MOD_ID.equals(id.getNamespace()) ? REGISTRY.get(id.getPath()) : null;
    }

    /** 返回所有法术（不可变视图） */
    public static Collection<Spell> all() {
        return Collections.unmodifiableCollection(REGISTRY.values());
    }

    /** 流式遍历 */
    public static Stream<Spell> stream() { return REGISTRY.values().stream(); }

    /** 按元素掩码取交集（只要包含任一匹配元素就算命中） */
    public static List<Spell> byElementMaskAny(int elementMask) {
        if (elementMask == 0) return Collections.emptyList();
        return BY_ELEMENT_CACHE.computeIfAbsent(elementMask, k -> {
            List<Spell> list = new ArrayList<>();
            for (Spell s : REGISTRY.values()) if ((s.getElementMask() & k) != 0) list.add(s);
            return Collections.unmodifiableList(list);
        });
    }

    /** 取包含指定分类的法术列表 */
    public static List<Spell> byCategory(SpellCategory cat) {
        return BY_CATEGORY_CACHE.computeIfAbsent(cat, k -> {
            List<Spell> list = new ArrayList<>();
            for (Spell s : REGISTRY.values()) if (s.getCategory() == k) list.add(s);
            return Collections.unmodifiableList(list);
        });
    }

    /** 获取法术总数（用于调试/断言） */
    public static int size() { return REGISTRY.size(); }
}
