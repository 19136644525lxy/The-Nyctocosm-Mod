package com.yifei.nyctocosm.spell.data;

import com.yifei.nyctocosm.spell.ElementType;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link ISpellPlayerData} 默认实现（纯数据容器，无业务逻辑）。
 * <p>
 * 线程安全策略：
 * <ul>
 *   <li>服务端逻辑单线程执行，所有修改都在 ServerThread tick 内，故普通字段无需 volatile</li>
 *   <li>activeSpells / arrays 使用并发容器防止误用（如异步命令/网络处理）</li>
 * </ul>
 */
public class SpellPlayerData implements ISpellPlayerData {

    // 吟唱状态
    @Nullable
    private String currentSpellId;
    private int castTicksLeft;

    // 数值属性
    private int traceChanceBase;   // 默认 0（基础追踪概率 %）
    private int spellPower;        // 默认 0（上限 50）

    // 元素属性：法术强度（6 元素，index = ElementType.index-1；修复 #4 见接口注释）
    private final int[] elementPower = new int[6];

    // 统计（6 元素，长度固定为 6，index = ElementType.index-1）
    private final long[] damageDealt = new long[6];
    private final int[] kills = new int[6];
    private final int[] spellsCast = new int[6];

    // 持续法术（spellId → remainingTicks）
    private final Map<String, Integer> activeSpells = new ConcurrentHashMap<>();

    // 法术平面绑定
    @Nullable
    private UUID spellPlaneUUID;

    @Override
    @Nullable
    public String getCurrentSpellId() { return currentSpellId; }

    @Override
    public int getCastTicksLeft() { return castTicksLeft; }

    @Override
    public boolean isCasting() { return castTicksLeft > 0 && currentSpellId != null; }

    @Override
    public void startCasting(String spellId, int totalTicks) {
        // 覆盖当前吟唱（如存在则视为取消）
        this.currentSpellId = spellId;
        this.castTicksLeft = Math.max(0, totalTicks);
    }

    @Override
    public void cancelCasting() {
        this.currentSpellId = null;
        this.castTicksLeft = 0;
    }

    @Override
    public int decrementCastTick() {
        if (castTicksLeft > 0) {
            castTicksLeft--;
            if (castTicksLeft == 0) {
                // 完成时不清 currentSpellId，由调度器在 onComplete 后或下一轮清理
            }
        }
        return castTicksLeft;
    }

    @Override
    public int getTraceChanceBase() { return traceChanceBase; }

    @Override
    public void setTraceChanceBase(int v) { this.traceChanceBase = Math.max(0, v); }

    @Override
    public int getSpellPower() { return spellPower; }

    @Override
    public void setSpellPower(int v) { this.spellPower = Math.min(50, Math.max(0, v)); }

    @Override
    public int[] getElementPower() { return elementPower; }

    @Override
    public long[] getDamageDealt() { return damageDealt; }

    @Override
    public int[] getKills() { return kills; }

    @Override
    public int[] getSpellsCast() { return spellsCast; }

    @Override
    public Map<String, Integer> getActiveSpells() { return activeSpells; }

    @Override
    public void setActiveSpell(String spellId, int remainingTicks) {
        activeSpells.put(spellId, Math.max(0, remainingTicks));
    }

    @Override
    @Nullable
    public Integer removeActiveSpell(String spellId) {
        return activeSpells.remove(spellId);
    }

    @Override
    public List<String> tickActiveSpells() {
        if (activeSpells.isEmpty()) return Collections.emptyList();
        List<String> expired = new ArrayList<>(activeSpells.size());
        Iterator<Map.Entry<String, Integer>> it = activeSpells.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Integer> e = it.next();
            int next = e.getValue() - 1;
            if (next <= 0) {
                it.remove();
                expired.add(e.getKey());
            } else {
                e.setValue(next);
            }
        }
        return expired;
    }

    @Override
    @Nullable
    public UUID getSpellPlaneUUID() { return spellPlaneUUID; }

    @Override
    public void setSpellPlaneUUID(@Nullable UUID uuid) { this.spellPlaneUUID = uuid; }

    @Override
    public void copyFrom(ISpellPlayerData other) {
        this.currentSpellId = other.getCurrentSpellId();
        this.castTicksLeft = other.getCastTicksLeft();
        this.traceChanceBase = other.getTraceChanceBase();
        this.spellPower = other.getSpellPower();
        System.arraycopy(other.getElementPower(), 0, this.elementPower, 0, 6);
        System.arraycopy(other.getDamageDealt(), 0, this.damageDealt, 0, 6);
        System.arraycopy(other.getKills(), 0, this.kills, 0, 6);
        System.arraycopy(other.getSpellsCast(), 0, this.spellsCast, 0, 6);
        // 深拷贝 active spells
        this.activeSpells.clear();
        this.activeSpells.putAll(other.getActiveSpells());
        // 注意：spellPlaneUUID 不复制（每个玩家绑定独立的实体）
        this.spellPlaneUUID = null;
    }

    // ========== NBT 序列化（由 Provider 调用）==========
    // 不直接实现 ICapabilitySerializable，减少耦合

    /** 写入 NBT（深拷贝，可被 Provider 调用） */
    public net.minecraft.nbt.CompoundTag serializeNBT() {
        net.minecraft.nbt.CompoundTag tag = new net.minecraft.nbt.CompoundTag();
        if (currentSpellId != null) tag.putString("CurrentSpellId", currentSpellId);
        tag.putInt("CastTicksLeft", castTicksLeft);
        tag.putInt("TraceChanceBase", traceChanceBase);
        tag.putInt("SpellPower", spellPower);
        tag.putIntArray("ElementPower", Arrays.copyOf(elementPower, 6));
        // NBT 支持原生数组直接写入
        tag.putLongArray("DamageDealt", Arrays.copyOf(damageDealt, 6));
        tag.putIntArray("Kills", Arrays.copyOf(kills, 6));
        tag.putIntArray("SpellsCast", Arrays.copyOf(spellsCast, 6));
        if (!activeSpells.isEmpty()) {
            net.minecraft.nbt.CompoundTag act = new net.minecraft.nbt.CompoundTag();
            activeSpells.forEach(act::putInt);
            tag.put("ActiveSpells", act);
        }
        if (spellPlaneUUID != null) tag.putUUID("SpellPlaneUUID", spellPlaneUUID);
        return tag;
    }

    /** 从 NBT 读取（容错：缺失字段用默认值） */
    public void deserializeNBT(net.minecraft.nbt.Tag nbt) {
        if (!(nbt instanceof net.minecraft.nbt.CompoundTag tag)) return;
        currentSpellId = tag.contains("CurrentSpellId", 8) // 8 = Tag.TAG_STRING
                ? tag.getString("CurrentSpellId") : null;
        castTicksLeft = tag.getInt("CastTicksLeft");
        traceChanceBase = tag.getInt("TraceChanceBase");
        spellPower = Math.min(50, tag.getInt("SpellPower"));
        // 11 = Tag.TAG_INT_ARRAY（旧存档无此键时保持全 0）
        if (tag.contains("ElementPower", 11)) {
            int[] arr = tag.getIntArray("ElementPower");
            System.arraycopy(arr, 0, elementPower, 0, Math.min(6, arr.length));
        }
        // 12 = Tag.TAG_LONG_ARRAY
        if (tag.contains("DamageDealt", 12)) {
            long[] arr = tag.getLongArray("DamageDealt");
            System.arraycopy(arr, 0, damageDealt, 0, Math.min(6, arr.length));
        }
        // 11 = Tag.TAG_INT_ARRAY
        if (tag.contains("Kills", 11)) {
            int[] arr = tag.getIntArray("Kills");
            System.arraycopy(arr, 0, kills, 0, Math.min(6, arr.length));
        }
        if (tag.contains("SpellsCast", 11)) {
            int[] arr = tag.getIntArray("SpellsCast");
            System.arraycopy(arr, 0, spellsCast, 0, Math.min(6, arr.length));
        }
        activeSpells.clear();
        // 10 = Tag.TAG_COMPOUND
        if (tag.contains("ActiveSpells", 10)) {
            net.minecraft.nbt.CompoundTag act = tag.getCompound("ActiveSpells");
            for (String k : act.getAllKeys()) activeSpells.put(k, act.getInt(k));
        }
        spellPlaneUUID = tag.hasUUID("SpellPlaneUUID") ? tag.getUUID("SpellPlaneUUID") : null;
    }
}
