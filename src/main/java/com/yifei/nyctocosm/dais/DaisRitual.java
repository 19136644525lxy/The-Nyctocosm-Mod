package com.yifei.nyctocosm.dais;

import com.yifei.nyctocosm.Content;
import com.yifei.nyctocosm.CommonEvents;
import net.minecraft.core.*;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.Vec3;

/** Original dais/scan layout, expressed as block predicates instead of commands. */
public final class DaisRitual {
    /** 返回首个不符处的提示键；完整结构返回 null。对照原包 crafting:dais/scan 的逐条 unless block。 */
    public static String fault(Level level,BlockPos anvil){
        if(!level.getBlockState(anvil).is(Blocks.ANVIL)||!level.getBlockState(anvil.below()).is(Blocks.BEACON))
            return "message.nyctocosm.dais_missing_core";
        BlockPos base=anvil.below();
        for(Direction direction:Direction.Plane.HORIZONTAL){
            if(!level.getBlockState(base.relative(direction)).is(Blocks.QUARTZ_BLOCK))return "message.nyctocosm.dais_missing_quartz";
            var stair=level.getBlockState(base.relative(direction,2));
            if(!stair.is(Blocks.QUARTZ_STAIRS)||stair.getValue(StairBlock.FACING)!=direction.getOpposite()
                ||stair.getValue(StairBlock.HALF)!=Half.BOTTOM||stair.getValue(StairBlock.SHAPE)!=StairsShape.STRAIGHT)
                return "message.nyctocosm.dais_missing_quartz";
        }
        for(int x:new int[]{-1,1})for(int z:new int[]{-1,1}){
            var slab=level.getBlockState(base.offset(x,0,z));
            if(!slab.is(Blocks.WAXED_CUT_COPPER_SLAB)||slab.getValue(SlabBlock.TYPE)!=SlabType.BOTTOM)
                return "message.nyctocosm.dais_missing_slab";
        }
        for(int x=-3;x<=3;x++)for(int z=-3;z<=3;z++){
            boolean candle=(Math.abs(x)==3&&Math.abs(z)<=1)||(Math.abs(z)==3&&Math.abs(x)<=1)||(Math.abs(x)==2&&Math.abs(z)==2);
            if(!candle)continue;
            var state=level.getBlockState(base.offset(x,0,z));
            if(!state.is(BlockTags.CANDLES)||!state.hasProperty(CandleBlock.LIT)||!state.getValue(CandleBlock.LIT))
                return "message.nyctocosm.dais_missing_candles";
        }
        return null;
    }
    public static boolean matches(Level level,BlockPos anvil){
        return fault(level,anvil)==null;
    }
    /** 在半径内找"铁砧正上方 + 信标"的锚点（不校验其余结构），用于定位玩家想激活的祭坛。 */
    public static BlockPos anchorNear(Level level,BlockPos center,int radius){
        for(BlockPos pos:BlockPos.betweenClosed(center.offset(-radius,-radius,-radius),center.offset(radius,radius,radius)))
            if(level.getBlockState(pos).is(Blocks.ANVIL)&&level.getBlockState(pos.below()).is(Blocks.BEACON))return pos.immutable();
        return null;
    }
    public static boolean activate(ServerLevel level,BlockPos anvil){
        if(!matches(level,anvil))return false;
        // Keep the physical ritual base intact. A real block replaces the anvil and opens a native menu.
        if(!level.setBlockAndUpdate(anvil,Content.DAIS.get().defaultBlockState()))return false;
        Vec3 center=Vec3.atCenterOf(anvil);
        // 原包 crafting:dais/construct + summon_ring：常驻符文环（pos/up/neg + 普通 glyph 四层）
        // 由 marker 盔甲架头饰 quartz CMD7/8 渲染；Java 侧用一个 DaisRing 实体承载四层。
        level.getEntitiesOfClass(DaisRing.class,new net.minecraft.world.phys.AABB(anvil).inflate(2)).forEach(DaisRing::discard);
        level.addFreshEntity(new DaisRing(level,center.add(0,1,0)));
        // 原包 block/19：dais.construct master 半径 30 音量 5；SOUL 粒子保留（原包演出由 rig 承载）
        CommonEvents.soundMaster(level,center,"dais.construct",5,30);
        level.sendParticles(ParticleTypes.SOUL,center.x,center.y,center.z,60,1.5,.7,1.5,.02);
        return true;
    }
    /** 原包 `positioned ^ ^ ^1`：玩家面前 1 格（脚部高度）就是铁砧时可直接激活。 */
    public static BlockPos findTarget(Level level,Player player){
        double yaw=Math.toRadians(player.getYRot());
        BlockPos ahead=BlockPos.containing(player.getX()-Math.sin(yaw),player.getY(),player.getZ()+Math.cos(yaw));
        return matches(level,ahead)?ahead:null;
    }
    /** 半径内寻找已搭好但尚未激活的祭坛，用于提示玩家如何激活。 */
    public static BlockPos findNearby(Level level,BlockPos center,int radius){
        for(BlockPos pos:BlockPos.betweenClosed(center.offset(-radius,-radius,-radius),center.offset(radius,radius,radius)))
            if(level.getBlockState(pos).is(Blocks.ANVIL)&&matches(level,pos))return pos.immutable();
        return null;
    }
    private DaisRitual(){}
}
