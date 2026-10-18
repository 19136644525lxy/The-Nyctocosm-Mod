package com.yifei.nyctocosm.dais;

import com.yifei.nyctocosm.CommonEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public final class DaisBlock extends Block {
    public DaisBlock(){super(BlockBehaviour.Properties.of().strength(5,1200).lightLevel(state->7).noOcclusion());}
    @Override public InteractionResult use(BlockState state,Level level,BlockPos pos,Player player,InteractionHand hand,BlockHitResult hit){
        if(!level.isClientSide){
            // 区块卸载会丢失 pending tick，右键时重启 hum 循环（hasScheduledTick 防重复叠加）
            if(!level.getBlockTicks().hasScheduledTick(pos,this))level.scheduleTick(pos,this,1);
            player.openMenu(new SimpleMenuProvider((id,inventory,p)->new DaisMenu(id,inventory,ContainerLevelAccess.create(level,pos)),Component.translatable("gui.nyctocosm.dais_title")));
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
    @Override public void onPlace(BlockState state,Level level,BlockPos pos,BlockState oldState,boolean moved){
        super.onPlace(state,level,pos,oldState,moved);
        if(!level.isClientSide)level.scheduleTick(pos,this,1);
    }
    /** 原包 root marker daishumtimer：120 tick 周期 dais.hum 氛围循环，master 半径 20。 */
    @Override public void tick(BlockState state,ServerLevel level,BlockPos pos,RandomSource random){
        CommonEvents.soundMaster(level,Vec3.atCenterOf(pos),"dais.hum",1,20);
        level.scheduleTick(pos,this,120);
    }
}
