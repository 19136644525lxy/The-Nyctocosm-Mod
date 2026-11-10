package com.yifei.nyctocosm.dais;

import com.yifei.nyctocosm.Content;
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
 * Server-driven copy of the persistent dais glyph rings. The original pack summons four
 * invisible marker armour stands ({@code pos}, {@code up}, {@code neg} and the plain
 * glyph stand) whose head slot holds {@code quartz} with CustomModelData 7/8; the tick
 * functions spin their yaw and ease {@code Pose.Head[0]}. Here one entity carries all
 * four rings and the renderer derives yaw/pitch from the entity tick count.
 */
public final class DaisRing extends Entity {
    public DaisRing(EntityType<? extends DaisRing> type, Level level) {
        super(type, level);
        noPhysics = true;
        setNoGravity(true);
    }

    public DaisRing(Level level, Vec3 position) {
        this(Content.DAIS_RING.get(), level);
        moveTo(position.x, position.y, position.z, 0f, 0f);
    }

    @Override
    public void tick() {
        super.tick();
        if (!(level() instanceof ServerLevel server)) return;
        // crafting:__generated__/block/18: the plain glyph ring emits one firework particle
        // per tick with a 3/3/3 spread around ~ ~1.6 ~.
        server.sendParticles(ParticleTypes.FIREWORK, getX(), getY() + 1.6, getZ(), 1, 3, 3, 3, 0);
        // The rings only exist while their owner dais block is still there.
        if (tickCount % 20 == 0 && !server.getBlockState(blockPosition().below()).is(Content.DAIS.get())) discard();
    }

    @Override protected void defineSynchedData() {}
    @Override protected void readAdditionalSaveData(CompoundTag tag) {}
    @Override protected void addAdditionalSaveData(CompoundTag tag) {}
    @Override public Packet<ClientGamePacketListener> getAddEntityPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
}
