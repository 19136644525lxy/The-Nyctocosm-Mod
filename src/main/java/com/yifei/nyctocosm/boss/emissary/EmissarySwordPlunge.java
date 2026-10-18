package com.yifei.nyctocosm.boss.emissary;

import com.yifei.nyctocosm.Content;
import com.yifei.nyctocosm.CommonEvents;
import com.yifei.nyctocosm.boss.emissary.EmissaryDamagePose;
import com.yifei.nyctocosm.boss.Boss;
import com.yifei.nyctocosm.combat.Combat;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

/** Independent carrier for the Emissary's 110-tick Sword Plunge attack. */
public final class EmissarySwordPlunge extends Entity {
    static final int MAX_AGE = 110;
    static final int FAST_SUBSTEPS = 200;
    public static final int TOTAL_SUBSTEPS= 250;
    private static final int SUBSTEPS_PER_TICK = 3;
    private static final float SOURCE_DAMAGE = 60;
    private UUID owner;
    private int age;
    private int substeps;
    private boolean shockwavePlayed;
    private boolean explosionPlayed;

    public EmissarySwordPlunge(EntityType<? extends EmissarySwordPlunge> type, Level level) {
        super(type, level);
        noPhysics = true;
        setNoGravity(true);
        setDeltaMovement(Vec3.ZERO); // The source is an ItemDisplay; its NBT Motion is not integrated by Display.tick.
    }

    public static EmissarySwordPlunge create(ServerLevel level, Boss boss) {
        // The source selector is @a (online players), rather than all Player entities in nearby chunks.
        var nearby = new java.util.LinkedHashSet<Player>(level.players());
        nearby.addAll(level.getEntitiesOfClass(Player.class, boss.getBoundingBox().inflate(300)));
        var candidates = nearby.stream().filter(player ->
            !player.isSpectator() && !player.getTags().contains("emissarydebug") && player.distanceToSqr(boss) <= 300 * 300).toList();
        if (candidates.isEmpty()) return null;
        return createAtTarget(level, boss, candidates.get(boss.getRandom().nextInt(candidates.size())), boss.getRandom());
    }

    public static EmissarySwordPlunge createAtTarget(ServerLevel level, Boss boss, Player target) {
        return createAtTarget(level, boss, target, boss.getRandom());
    }

    public static void handCue(ServerLevel level,Boss boss,int frame){
        var hand=EmissaryDamagePose.sampleAt(boss,"phase2.attack.sword_plunge","righthand",frame);
        for(int sample=0;sample<15;sample++){
            Vec3 at=EmissaryDamagePose.offsetFromLocator(boss,hand,0,0,sample*.2);
            level.sendParticles(ParticleTypes.FIREWORK,at.x,at.y,at.z,3,.2,.2,.2,.2);
            level.sendParticles(ParticleTypes.FLASH,at.x,at.y,at.z,2,.2,.2,.2,0);
        }
    }

    public static EmissarySwordPlunge createAtTarget(ServerLevel level, Boss boss, Player target, net.minecraft.util.RandomSource random) {
        if (target == null || target.isSpectator() || target.getTags().contains("emissarydebug")
            || target.level() != level || target.distanceToSqr(boss) > 300 * 300) return null;

        // Sword Plunge block344 has no Rotation; block335 belongs to a different attack.
        return createAtTarget(level, boss, target, 0, 0);
    }

    public static EmissarySwordPlunge createAtTarget(ServerLevel level, Boss boss, Player target, int yaw, int pitch) {
        if (target == null || target.isSpectator() || target.getTags().contains("emissarydebug")
            || target.level() != level || target.distanceToSqr(boss) > 300 * 300) return null;
        int surfaceY = surfaceY(level, target.position());
        var sword = Content.EMISSARY_SWORD_PLUNGE.get().create(level);
        sword.owner = boss.getUUID();
        sword.setPos(target.getX(), surfaceY + 220, target.getZ());
        sword.setYRot(0);
        sword.setXRot(0);
        sword.setDeltaMovement(Vec3.ZERO);
        level.addFreshEntity(sword);
        CommonEvents.sound(level, new Vec3(target.getX(), surfaceY, target.getZ()), "emissary.attack.sword_plunge.fall", 20);
        return sword;
    }

    public int ageForTest() { return age; }
    public int substepsForTest() { return substeps; }
    public boolean shockwavePlayedForTest() { return shockwavePlayed; }
    public boolean explosionPlayedForTest() { return explosionPlayed; }
    public UUID ownerForTest() { return owner; }
    @Override public AABB getBoundingBoxForCulling(){return getBoundingBox().inflate(80);}
    @Override public boolean shouldRenderAtSqrDistance(double distance){return distance<=256*256;}

    @Override public void tick() {
        super.tick();
        if (!(level() instanceof ServerLevel server)) return;
        if (explosionPlayed) { discard(); return; }
        age++;

        Set<UUID> hitThisTick = new HashSet<>();
        for (int step = 0; step < SUBSTEPS_PER_TICK && substeps < TOTAL_SUBSTEPS; step++) {
            substeps++;
            double distance = substeps <= FAST_SUBSTEPS ? 1.0 : 0.2;
            setPos(getX(), getY() - distance, getZ());
            collectSourceSweepPlayers(server, hitThisTick);
            sourceStoneTrail(server);
        }
        applySweepHits(server, hitThisTick);
        // subtick 每刻在剑下方 60 格留下闪光与云雾尾流。
        forced(server, ParticleTypes.FLASH, getX(), getY() - 60, getZ(), 1, 1, 1, 1, 0);
        forced(server, ParticleTypes.CLOUD, getX(), getY() - 60, getZ(), 50, 3, 3, 3, .5);
        if (substeps >= FAST_SUBSTEPS) warningRing(server);

        if (age == 105 && !shockwavePlayed) {
            shockwavePlayed = true;
            sourceShockwave(server);
            fireDebris(server);
        }
        if (age == 108) shockwaveLateStage(server); // 原包 timer 4/6 两段后置环合并
        if (age >= MAX_AGE && !explosionPlayed) explode(server);
    }

    /** 原包所有粒子命令均为 force 模式：普通 32 格投递会让 220 格高空的剑对被锁定的玩家隐形。 */
    private static void forced(ServerLevel server, ParticleOptions particle, double x, double y, double z,
            int count, double dx, double dy, double dz, double speed) {
        for (ServerPlayer viewer : server.players())
            server.sendParticles(viewer, particle, true, x, y, z, count, dx, dy, dz, speed);
    }

    /** slowcast 的石尘柱：仅在剑下方 0..44 格穿过非穿透方块时展开，半径与密度随深度线性收窄。 */
    private void sourceStoneTrail(ServerLevel server) {
        var stone = new BlockParticleOption(ParticleTypes.BLOCK, net.minecraft.world.level.block.Blocks.STONE.defaultBlockState());
        for (int depth = 0; depth <= 44; depth++) {
            var at = BlockPos.containing(getX(), getY() - depth, getZ());
            if (Boss.emissaryPassable(server, at)) continue;
            int count = depth == 44 ? 1 : Math.max(1, Math.round(36f * (44 - depth) / 44f));
            double spread = depth == 44 ? 0 : (45 - depth) / 7.0;
            forced(server, stone, at.getX() + .5, at.getY() + .5, at.getZ() + .5, count, spread, 2, 2, 0);
        }
    }

    /** 剑减速后，原包在地表画 90 个红尘点组成的 15 格警示圈。 */
    private void warningRing(ServerLevel server) {
        var dust = new DustParticleOptions(new org.joml.Vector3f(1f, 0f, 0f), 1f);
        double ground = surfaceY(server, position());
        for (int angle = 0; angle < 90; angle++) {
            double theta = Math.toRadians(angle * 4);
            forced(server, dust, getX() + Math.cos(theta) * 15, ground, getZ() + Math.sin(theta) * 15, 1, 0, 0, 0, 0);
        }
    }

    private void collectSourceSweepPlayers(ServerLevel server, Set<UUID> hitThisTick) {
        // slowcast.mcfunction selects dx=19,dy=100,dz=3 after positioning at sword (-10,-60,-2).
        // Selector dimensions expand by one block on their positive ends, matching Minecraft's delta box.
        var selection = new AABB(getX() - 10, getY() - 60, getZ() - 2,
            getX() + 10, getY() + 41, getZ() + 2);
        for (Player player : server.getEntitiesOfClass(Player.class, selection,
            candidate -> !candidate.isSpectator() && !candidate.getTags().contains("emissarydebug")
                && selection.contains(candidate.position()))) hitThisTick.add(player.getUUID());
    }

    private void applySweepHits(ServerLevel server, Set<UUID> hitThisTick) {
        Entity found = owner == null ? null : server.getEntity(owner);
        Boss boss = found instanceof Boss emissary ? emissary : null;
        for (UUID id : hitThisTick) {
            Entity entity = server.getEntity(id);
            if (entity instanceof Player player)
                Combat.hitGeneric(this, boss, player, SOURCE_DAMAGE);
        }
    }

    private void sourceShockwave(ServerLevel server) {
        // block334 在剑下方 -10..40 六个高度引爆炸环、闪光与二十个燃烧坠块。
        for (int y : new int[]{-10, 0, 10, 20, 30, 40}) {
            server.sendParticles(ParticleTypes.FLASH, getX(), getY() + y, getZ(), 4, .2, .2, .2, 0);
            shockwaveStage(server, y, 1.2, false);
        }
    }

    /** effects:shockwave/meteor_shower 的横向爆环：count=0 让粒子获得径向初速，形成扩散圆环。 */
    private void shockwaveStage(ServerLevel server, int height, double speed, boolean late) {
        for (int angle = 0; angle < 360; angle += 6) {
            double theta = Math.toRadians(angle);
            double vx = Math.cos(theta) * speed, vz = Math.sin(theta) * speed;
            forced(server, ParticleTypes.FLAME, getX(), getY() + height, getZ(), 0, vx, 0, vz, 1);
            if (late) {
                forced(server, ParticleTypes.LARGE_SMOKE, getX(), getY() + height, getZ(), 0, vx, 0, vz, 1);
                forced(server, ParticleTypes.FIREWORK, getX(), getY() + height, getZ(), 0, vx, 0, vz, 1);
            }
        }
        for (int angle = 0; angle < 360; angle += 18)
            forced(server, ParticleTypes.END_ROD, getX(), getY() + height, getZ(), 0,
                Math.cos(Math.toRadians(angle)) * speed, 0, Math.sin(Math.toRadians(angle)) * speed, 1);
    }

    /** 原包 timer108 的后置环速度 1.5 并加入烟雾与烟花；在 110 爆炸前一并释放。 */
    private void shockwaveLateStage(ServerLevel server) {
        for (int y : new int[]{-10, 0, 10, 20, 30, 40}) shockwaveStage(server, y, 1.5, true);
    }

    /** block334 末段将 20 个火焰坠块按随机仰角锥抛出，落地即熄灭成火。 */
    private void fireDebris(ServerLevel server) {
        var random = bossRandom(server);
        for (int height : new int[]{10, 20, 30, 0})
            for (int i = 0; i < 5; i++) {
                float yaw = (random.nextInt(360) - 180) + (random.nextInt(90) - 45);
                float pitch = -45 + (random.nextInt(90) - 45);
                var debris = net.minecraft.world.entity.item.FallingBlockEntity.fall(server,
                    BlockPos.containing(getX(), getY() + height, getZ()), net.minecraft.world.level.block.Blocks.FIRE.defaultBlockState());
                debris.dropItem = false;
                debris.setDeltaMovement(Vec3.directionFromRotation(pitch, yaw).scale(1.5));
                server.addFreshEntity(debris);
            }
    }

    private net.minecraft.util.RandomSource bossRandom(ServerLevel server) {
        var found = owner == null ? null : server.getEntity(owner);
        return found instanceof Boss boss ? boss.getRandom() : server.random;
    }

    private void explode(ServerLevel server) {
        explosionPlayed = true; // Persist the one-shot before dispatching damage or visual events.
        Vec3 ground = new Vec3(getX(), surfaceY(server, position()), getZ());
        CommonEvents.sound(server, position(), "emissary.attack.sword_plunge.explode", 50);
        Vec3 base = position().add(0, -20, 0);
        for (int layer = 0; layer <= 9; layer++) {
            double y = base.y + layer * 8;
            forced(server, ParticleTypes.EXPLOSION_EMITTER, getX(), y, getZ(), 10, 4, 4, 4, 0);
            forced(server, ParticleTypes.FLAME, getX(), y, getZ(), 60, 4, 4, 4, .4);
            forced(server, ParticleTypes.FLASH, getX(), y, getZ(), 50, 4, 4, 4, .4);
            forced(server, ParticleTypes.FIREWORK, getX(), y, getZ(), 50, 4, 4, 4, .4);
        }
        sourceFireField(server);

        Entity found = owner == null ? null : server.getEntity(owner);
        Boss boss = found instanceof Boss emissary ? emissary : null;
        var area = new AABB(ground, ground).inflate(15);
        for (Player player : server.getEntitiesOfClass(Player.class, area,
            candidate -> !candidate.isSpectator() && !candidate.getTags().contains("emissarydebug")
                && candidate.hurtTime == 0 && candidate.distanceToSqr(ground) <= 15 * 15))
            Combat.hitGeneric(this, boss, player, SOURCE_DAMAGE);
        discard();
    }

    /** 引爆收尾的 fill：以剑为中心 21×21×7 的火场，只替换空气，熄灭自然。 */
    private void sourceFireField(ServerLevel server) {
        var fire = net.minecraft.world.level.block.Blocks.FIRE.defaultBlockState();
        for (int x = -10; x <= 10; x++)
            for (int y = -10; y <= 10; y++)
                for (int z = -3; z <= 3; z++) {
                    var pos = new BlockPos(getBlockX() + x, getBlockY() + y, getBlockZ() + z);
                    if (server.getBlockState(pos).is(net.minecraft.world.level.block.Blocks.AIR))
                        server.setBlock(pos, fire, 3);
                }
    }

    private static int surfaceY(ServerLevel level, Vec3 at) {
        BlockPos column = BlockPos.containing(at.x, at.y, at.z);
        return level.getHeight(Heightmap.Types.WORLD_SURFACE, column.getX(), column.getZ());
    }

    @Override protected void defineSynchedData() {}

    @Override protected void addAdditionalSaveData(CompoundTag tag) {
        if (owner != null) tag.putUUID("Owner", owner);
        tag.putInt("Age", age);
        tag.putInt("Substeps", substeps);
        tag.putBoolean("ShockwavePlayed", shockwavePlayed);
        tag.putBoolean("ExplosionPlayed", explosionPlayed);
    }

    @Override protected void readAdditionalSaveData(CompoundTag tag) {
        owner = tag.hasUUID("Owner") ? tag.getUUID("Owner") : null;
        age = Math.max(0, Math.min(MAX_AGE, tag.getInt("Age")));
        substeps = Math.max(0, Math.min(TOTAL_SUBSTEPS, tag.getInt("Substeps")));
        shockwavePlayed = tag.getBoolean("ShockwavePlayed");
        explosionPlayed = tag.getBoolean("ExplosionPlayed");
        setDeltaMovement(Vec3.ZERO);
    }

    @Override public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
