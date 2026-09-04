package com.yifei.nyctocosm.tag;

import com.yifei.nyctocosm.NyctocosmMod;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 动态向 nyctocosm 的 4 个实体类型标签注入所有模组实体。
 * <p>
 * 【根因】原数据包中 nyctocosm:mobs / hostiles 等标签采用硬编码原版 73 种实体列表，
 * 导致 @e[type=#nyctocosm:livingthings] 选择器对其他模组的怪物/生物完全不命中，
 * 最终使魔法伤害与武器技能伤害无法施加到模组怪身上。
 * <p>
 * 【修复方案】监听 Forge TagsUpdatedEvent（SERVER_DATA_LOAD 时机，标签已写入注册表），
 * 扫描 Registries.ENTITY_TYPE 中所有已注册的 EntityType，
 * 按照 EntityType#getBaseClass() 的继承关系，
 * 将额外的 Holder 追加到对应 nyctocosm 命名空间 tag，然后重新 bindTags。
 * <p>
 * 【注入规则】
 *   - LivingEntity（含 Player）→ #nyctocosm:livingthings 与 #nyctocosm:manipulable
 *   - Mob            → #nyctocosm:mobs
 *   - Enemy 接口     → #nyctocosm:hostiles
 */
@Mod.EventBusSubscriber(modid = NyctocosmMod.MOD_ID)
public final class EntityTypeTagInjector {

    /** 四个目标标签的 TagKey */
    public static final TagKey<EntityType<?>> LIVING_THINGS = createTag("livingthings");
    public static final TagKey<EntityType<?>> MOBS          = createTag("mobs");
    public static final TagKey<EntityType<?>> HOSTILES      = createTag("hostiles");
    public static final TagKey<EntityType<?>> MANIPULABLE   = createTag("manipulable");

    private EntityTypeTagInjector() {
    }

    private static TagKey<EntityType<?>> createTag(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.tryBuild(NyctocosmMod.MOD_ID, name));
    }

    @SubscribeEvent
    public static void onTagsUpdated(TagsUpdatedEvent event) {
        // CLIENT_PACKET_RECEIVED 时再次注入避免不同步，但会和客户端包合并，
        // 实际注入由服务端权威完成，客户端仅接受同步数据，故跳过
        if (event.getUpdateCause() != TagsUpdatedEvent.UpdateCause.SERVER_DATA_LOAD) return;

        Registry<EntityType<?>> entityRegistry = event.getRegistryAccess().registryOrThrow(Registries.ENTITY_TYPE);
        if (!(entityRegistry instanceof MappedRegistry<EntityType<?>> mapped)) {
            NyctocosmMod.LOGGER.warn("[Nyctocosm] ENTITY_TYPE 注册表非 MappedRegistry，跳过跨模组标签注入");
            return;
        }

        // 克隆当前所有 tag → Holder.List 映射（可变）
        Map<TagKey<EntityType<?>>, List<Holder<EntityType<?>>>> tagMap = new IdentityHashMap<>();
        for (Map.Entry<TagKey<EntityType<?>>, ?> entry : snapshotTagEntries(mapped).entrySet()) {
            @SuppressWarnings("unchecked")
            var named = (net.minecraft.core.HolderSet.Named<EntityType<?>>) entry.getValue();
            tagMap.put(entry.getKey(), new ArrayList<>(named.stream().toList()));
        }

        int addedLiving = 0, addedMobs = 0, addedHostiles = 0;

        for (Map.Entry<ResourceKey<EntityType<?>>, EntityType<?>> entry : entityRegistry.entrySet()) {
            EntityType<?> type = entry.getValue();
            Class<?> base = type.getBaseClass();

            // 非 LivingEntity 不处理（物品展示框、投掷物等都不属伤害目标）
            if (!LivingEntity.class.isAssignableFrom(base)) continue;

            ResourceKey<EntityType<?>> key = entry.getKey();
            Optional<Holder.Reference<EntityType<?>>> optHolder = entityRegistry.getHolder(key);
            if (optHolder.isEmpty()) continue;
            Holder.Reference<EntityType<?>> holder = optHolder.get();

            // ===== livingthings =====
            addedLiving += addIfAbsent(tagMap, LIVING_THINGS, holder);

            // ===== manipulable（基础实体 livingthings + 投射物等，此处只追加入 livingthings 的实体；
            //       投射物/物品/掉落物等原版已在 JSON 中列出，无需 Java 端动态扩展，它们都属于原版 EntityType）=====
            addIfAbsent(tagMap, MANIPULABLE, holder);

            // ===== mobs：Mob 子类 =====
            if (Mob.class.isAssignableFrom(base) && !Player.class.isAssignableFrom(base)) {
                addedMobs += addIfAbsent(tagMap, MOBS, holder);
            }

            // ===== hostiles：Enemy 接口实现（敌对生物）=====
            if (Enemy.class.isAssignableFrom(base)) {
                addedHostiles += addIfAbsent(tagMap, HOSTILES, holder);
            }
        }

        // 重新调用 bindTags：注意 bindTags 内部会再次遍历所有 byKey 构建反向映射，安全可重入
        try {
            mapped.bindTags(tagMap);
        } catch (RuntimeException e) {
            NyctocosmMod.LOGGER.error("[Nyctocosm] 标签重新绑定失败，跨模组实体伤害可能仍不可用", e);
            return;
        }

        NyctocosmMod.LOGGER.info("[Nyctocosm] 跨模组实体标签注入完成：livingthings +{}，mobs +{}，hostiles +{}",
                addedLiving, addedMobs, addedHostiles);
    }

    /** 向 tagMap.get(tag) 追加 holder，若已存在则 0，否则 1 */
    private static int addIfAbsent(Map<TagKey<EntityType<?>>, List<Holder<EntityType<?>>>> tagMap,
                                   TagKey<EntityType<?>> tag,
                                   Holder<EntityType<?>> holder) {
        List<Holder<EntityType<?>>> list = tagMap.computeIfAbsent(tag, k -> new ArrayList<>());
        if (list.contains(holder)) return 0;
        list.add(holder);
        return 1;
    }

    // =============================================================
    // 内部反射工具：取 MappedRegistry#tags（volatile IdentityHashMap）快照
    // =============================================================
    @SuppressWarnings("unchecked")
    private static Map<TagKey<EntityType<?>>, ?> snapshotTagEntries(MappedRegistry<EntityType<?>> mapped) {
        try {
            return (Map<TagKey<EntityType<?>>, ?>) TAGS_FIELD_GETTER.invoke(mapped);
        } catch (Throwable e) {
            NyctocosmMod.LOGGER.warn("[Nyctocosm] 读取 MappedRegistry#tags 失败，回退为只读副本", e);
            // 回退方案：通过 getTag 暴露的已知 key 重建列表
            Map<TagKey<EntityType<?>>, Object> fallback = new IdentityHashMap<>();
            for (var tagKey : List.of(LIVING_THINGS, MOBS, HOSTILES, MANIPULABLE)) {
                mapped.getTag(tagKey).ifPresent(named -> fallback.put(tagKey, named));
            }
            return fallback;
        }
    }

    /** MappedRegistry#tags 字段访问器（MethodHandle，性能优于反射 Field 每次 setAccessible） */
    private static final MethodHandle TAGS_FIELD_GETTER;

    static {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle mh = null;
        try {
            Field f = MappedRegistry.class.getDeclaredField("f_123056_"); // SRG: tags -> f_123056_
            f.setAccessible(true);
            mh = lookup.unreflectGetter(f);
        } catch (Throwable ignore1) {
            // 官方映射名：tags
            try {
                Field f = MappedRegistry.class.getDeclaredField("tags");
                f.setAccessible(true);
                mh = lookup.unreflectGetter(f);
            } catch (Throwable t) {
                NyctocosmMod.LOGGER.error("[Nyctocosm] 无法访问 MappedRegistry#tags 字段，跨模组标签扩展功能将被禁用", t);
            }
        }
        TAGS_FIELD_GETTER = mh;
    }
}
