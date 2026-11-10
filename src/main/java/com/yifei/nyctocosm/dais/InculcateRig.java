package com.yifei.nyctocosm.dais;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

/**
 * Server-driven copy of the original Animated Java {@code inculcate} rig spawned by the
 * dais. The 111 command-keyframes are baked into {@code native_rigs/ajinculcate.json.gz}
 * and rendered by {@code InculcateRigRenderer}; this entity only tracks the ritual clock
 * and replays the command keyframe particle pulses.
 */
public final class InculcateRig extends Entity {
    /** local_anim_time of the last frame ({@code leaf_110}). */
    public static final int LAST_FRAME = 110;
    /** Frames whose command keyframes run crafting:dais/effects/inculcate1. */
    private static final int[] PULSES = {10, 76, 85, 99};

    public InculcateRig(EntityType<? extends InculcateRig> type, Level level) {
        super(type, level);
        noPhysics = true;
        setNoGravity(true);
    }

    public InculcateRig(Level level, Vec3 position) {
        this(com.yifei.nyctocosm.Content.INCULCATE_RIG.get(), level);
        // DisplayRenderer applies the yaw as the rig root rotation; 180 matches the
        // datapack's per-tick `tp @s ~ ~ ~ ~180 ~`.
        moveTo(position.x, position.y, position.z, 180f, 0f);
    }

    @Override
    public void tick() {
        super.tick();
        if (!(level() instanceof ServerLevel server)) return;
        int age = tickCount;
        for (int pulse : PULSES) if (age == pulse) inculcate1(server);
        if (age == LAST_FRAME) {
            inculcate2(server);
            discard();
        }
    }

    /** crafting:dais/effects/inculcate1 */
    private void inculcate1(ServerLevel server) {
        server.sendParticles(ParticleTypes.FLASH, getX(), getY(), getZ(), 5, .1, .1, .1, 0);
        server.sendParticles(ParticleTypes.LAVA, getX(), getY(), getZ(), 10, 0, 0, 0, .1);
    }

    /** crafting:dais/effects/inculcate2 (shockwave/inculcate is a no-op in the original pack). */
    private void inculcate2(ServerLevel server) {
        inculcate1(server);
        server.sendParticles(ParticleTypes.EXPLOSION, getX(), getY(), getZ(), 10, .1, .1, .1, 0);
    }

    @Override protected void defineSynchedData() {}
    @Override protected void readAdditionalSaveData(CompoundTag tag) {}
    @Override protected void addAdditionalSaveData(CompoundTag tag) {}
    @Override public Packet<ClientGamePacketListener> getAddEntityPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
}
