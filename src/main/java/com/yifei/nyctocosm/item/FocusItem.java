package com.yifei.nyctocosm.item;

import com.yifei.nyctocosm.Content;
import com.yifei.nyctocosm.CommonEvents;
import com.yifei.nyctocosm.dais.DaisRitual;
import com.yifei.nyctocosm.network.Network;
import com.yifei.nyctocosm.spell.SeelicField;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

public final class FocusItem extends Item {
    public final boolean mobile;
    public FocusItem(boolean mobile) { super(new Properties().stacksTo(1).rarity(Rarity.RARE)); this.mobile=mobile; }
    public static int souls(ItemStack stack) { return stack.hasTag() && stack.getTag().contains("Souls") ? stack.getTag().getInt("Souls") : 1; }
    public static void addSoul(ItemStack stack, int count) { stack.getOrCreateTag().putInt("Souls",(int)Math.max(0,Math.min(Integer.MAX_VALUE,(long)souls(stack)+count))); }
    @Override public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack=player.getItemInHand(hand);
        if (!level.isClientSide) {
            ServerLevel server=(ServerLevel)level;
            stack=cast(server,player,hand,stack,mobile?null:DaisRitual.findTarget(server,player));
        }
        return InteractionResultHolder.sidedSuccess(stack,level.isClientSide);
    }
    /** 由 RightClickBlock 拦截调用：激活祭坛并照常走施法流程（铁砧的原版界面会被拦在世界交互之前）。 */
    public void useOnDais(ServerLevel server,Player player,InteractionHand hand,ItemStack stack,BlockPos anvil){
        cast(server,player,hand,stack,anvil);
    }
    /** 原包 block/13：先扣魂开灵场，再执行 crafting:dais/cast（既有灵场不影响祭坛激活）。 */
    private ItemStack cast(ServerLevel server,Player player,InteractionHand hand,ItemStack stack,BlockPos dais){
        if (dais!=null)DaisRitual.activate(server,dais);
        var fields=server.getEntitiesOfClass(SeelicField.class,player.getBoundingBox().inflate(64),field -> field.ownedBy(player) && !field.isHypertrace());
        if (!fields.isEmpty()) { fields.forEach(SeelicField::discard); return stack; }
        if (souls(stack)<=0 && !player.isCreative()) { player.displayClientMessage(Component.translatable("message.nyctocosm.no_souls"),true); return stack; }
        SeelicField field=new SeelicField(Content.FIELD.get(),server);
        field.configure(player,mobile,false);
        field.setPos(player.getX(),player.getY()+.08,player.getZ());
        if (server.addFreshEntity(field)) {
            if(!player.isCreative()){
                addSoul(stack,-1);
                if(!mobile&&souls(stack)==0){stack=new ItemStack(Items.GLASS_BOTTLE);player.setItemInHand(hand,stack);}
            }
            Network.sourceVisual(server,"plane",field.position().add(0,.2,0),player.getYRot(),0);
            CommonEvents.resonanceSound(server,player.position(),"spells.cast.magic_plane",1,10);
            Network.sourceVisual(server,"release",player.position().add(0,1,0),0,0);
            CommonEvents.resonanceSound(server,player.position(),"spells.soul_release",1,10);
        }
        player.getCooldowns().addCooldown(this,10);
        if(dais!=null)player.displayClientMessage(Component.translatable("message.nyctocosm.dais_activated"),true);
        else if(!mobile&&DaisRitual.findNearby(server,player.blockPosition(),6)!=null)player.displayClientMessage(Component.translatable("message.nyctocosm.dais_hint"),true);
        else player.displayClientMessage(Component.translatable("message.nyctocosm.field_open"),true);
        return stack;
    }
    @Override public void appendHoverText(ItemStack stack,@Nullable Level level,List<Component> lines,TooltipFlag flag) {
        lines.add(Component.translatable("tooltip.nyctocosm.focus_souls",souls(stack)));
        lines.add(Component.translatable("tooltip.nyctocosm.focus_use"));
        lines.add(Component.translatable("tooltip.nyctocosm.focus_cast"));
    }
}
