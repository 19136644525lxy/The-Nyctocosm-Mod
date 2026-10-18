package com.yifei.nyctocosm.spell;

import com.yifei.nyctocosm.combat.Combat;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

/**
 * 法术属性系统（对照原包 magic/calcstatsspell + calcstatsdamage）。
 * 7 域（global + 六元素）× 施法侧（power/speed/tracechance）+ 承伤侧（resistance/weakness），
 * 各分 base + modifier，total = base + modifier。
 * 存储于玩家 PersistentData，key 格式 "Nyctocosm.m.{domain}.{stat}[.base|.modifier]"。
 */
public final class SpellStats {
    private static final String P = "Nyctocosm.m.";
    private static final String[] CAST_STATS = {"power", "speed", "tracechance"};
    private static final String[] DAMAGE_STATS = {"resistance", "weakness"};

    private SpellStats() {}

    /* ---------- 域与 key ---------- */

    private static String domain(Combat.Element element) {
        return element == null ? "global" : element.name().toLowerCase(java.util.Locale.ROOT);
    }

    private static String key(Combat.Element element, String stat) {
        return P + domain(element) + "." + stat;
    }

    private static String key(Combat.Element element, String stat, String type) {
        return P + domain(element) + "." + stat + "." + type;
    }

    private static CompoundTag data(Player player) {
        return player.getPersistentData();
    }

    /* ---------- 读取 total ---------- */

    /** 施法威力（global + 指定元素）。 */
    public static int power(Player player, Combat.Element element) {
        return total(player, element, "power");
    }

    /** 施法速度（百分比，减少施法时间）。 */
    public static int speed(Player player, Combat.Element element) {
        return total(player, element, "speed");
    }

    /** 追踪触发概率（百分比）。 */
    public static int traceChance(Player player, Combat.Element element) {
        return total(player, element, "tracechance");
    }

    /** 承伤抗性（百分比，减少伤害）。 */
    public static int resistance(Player player, Combat.Element element) {
        return total(player, element, "resistance");
    }

    /** 承伤弱点（百分比，增加伤害）。 */
    public static int weakness(Player player, Combat.Element element) {
        return total(player, element, "weakness");
    }

    private static int total(Player player, Combat.Element element, String stat) {
        return data(player).getInt(key(element, stat));
    }

    /* ---------- 读取 base / modifier ---------- */

    public static int base(Player player, Combat.Element element, String stat) {
        return data(player).getInt(key(element, stat, "base"));
    }

    public static int modifier(Player player, Combat.Element element, String stat) {
        return data(player).getInt(key(element, stat, "modifier"));
    }

    /* ---------- 写入 ---------- */

    public static void setBase(Player player, Combat.Element element, String stat, int value) {
        data(player).putInt(key(element, stat, "base"), value);
    }

    public static void setModifier(Player player, Combat.Element element, String stat, int value) {
        data(player).putInt(key(element, stat, "modifier"), value);
    }

    public static void addModifier(Player player, Combat.Element element, String stat, int value) {
        data(player).putInt(key(element, stat, "modifier"), modifier(player, element, stat) + value);
    }

    /* ---------- 重算（calcstatsspell + calcstatsdamage） ---------- */

    /**
     * 每 tick 重算：total = base + modifier。
     * 对照原包 calcstatsspell.mcfunction + calcstatsdamage.mcfunction。
     */
    public static void recalculate(Player player) {
        recalculateDomain(player, null);
        for (Combat.Element element : Combat.Element.values())
            recalculateDomain(player, element);
    }

    private static void recalculateDomain(Player player, Combat.Element element) {
        for (String stat : CAST_STATS)
            recalculateStat(player, element, stat);
        for (String stat : DAMAGE_STATS)
            recalculateStat(player, element, stat);
    }

    private static void recalculateStat(Player player, Combat.Element element, String stat) {
        int base = base(player, element, stat);
        int modifier = modifier(player, element, stat);
        data(player).putInt(key(element, stat), base + modifier);
    }

    /* ---------- 追踪统计 ---------- */

    private static String trackKey(String kind, Combat.Element element) {
        return P + "track." + kind + "." + domain(element);
    }

    public static int damageDealt(Player player, Combat.Element element) {
        return data(player).getInt(trackKey("damage_dealt", element));
    }

    public static int damageTaken(Player player, Combat.Element element) {
        return data(player).getInt(trackKey("damage_taken", element));
    }

    public static int kills(Player player, Combat.Element element) {
        return data(player).getInt(trackKey("kills", element));
    }

    public static int spellsCast(Player player, Combat.Element element) {
        return data(player).getInt(trackKey("spells_cast", element));
    }

    public static void addDamageDealt(Player player, Combat.Element element, int amount) {
        addTrack(player, "damage_dealt", element, amount);
    }

    public static void addDamageTaken(Player player, Combat.Element element, int amount) {
        addTrack(player, "damage_taken", element, amount);
    }

    public static void addKill(Player player, Combat.Element element) {
        addTrack(player, "kills", element, 1);
    }

    public static void addSpellCast(Player player, Combat.Element element) {
        addTrack(player, "spells_cast", element, 1);
    }

    private static void addTrack(Player player, String kind, Combat.Element element, int amount) {
        data(player).putInt(trackKey(kind, element), data(player).getInt(trackKey(kind, element)) + amount);
    }

    /**
     * 重置追踪标记（对照原包 resettrackers.mcfunction）。
     * 原包中追踪值在读取后由 tag 机制清零；Java 侧简化为每 tick 重算时清零。
     */
    public static void resetTrackers(Player player) {
        for (Combat.Element element : Combat.Element.values()) {
            data(player).remove(trackKey("damage_dealt", element));
            data(player).remove(trackKey("damage_taken", element));
            data(player).remove(trackKey("kills", element));
            data(player).remove(trackKey("spells_cast", element));
        }
    }
}
