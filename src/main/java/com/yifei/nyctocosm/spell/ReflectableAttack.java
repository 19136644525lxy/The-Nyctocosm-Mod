package com.yifei.nyctocosm.spell;

import com.yifei.nyctocosm.spell.Reflectance;
import java.util.Set;
import java.util.UUID;
import net.minecraft.world.phys.Vec3;

/** The source selector and ownership data for one Reflectance sample. */
public record ReflectableAttack(Vec3 sample, UUID owner, float energyCost, Set<UUID> alreadyReflected) {
    public ReflectableAttack(Vec3 sample, UUID owner, float energyCost) {
        this(sample, owner, energyCost, Set.of());
    }

    public ReflectableAttack {
        alreadyReflected = alreadyReflected == null ? Set.of() : Set.copyOf(alreadyReflected);
    }
}
