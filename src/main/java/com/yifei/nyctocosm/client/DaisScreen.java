package com.yifei.nyctocosm.client;

import com.yifei.nyctocosm.dais.DaisMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public final class DaisScreen extends AbstractContainerScreen<DaisMenu>{
    private static final int PANEL_W=176,PANEL_H=166;
    public DaisScreen(DaisMenu menu,Inventory inventory,Component title){
        super(menu,inventory,title);
        imageWidth=PANEL_W;imageHeight=PANEL_H;titleLabelY=7;
    }
    @Override protected void renderBg(GuiGraphics graphics,float partial,int mouseX,int mouseY){
        int x=leftPos,y=topPos;
        GuiPaint.panel(graphics,x,y,PANEL_W,PANEL_H);
        // 提示放在标题与输入槽之间，避免压到背包标签；手动居中以关闭文字阴影
        Component hint=Component.translatable("gui.nyctocosm.dais_hint");
        graphics.drawString(font,hint,x+(PANEL_W-font.width(hint))/2,y+18,GuiPaint.INK,false);
        for(Slot slot:menu.slots)GuiPaint.slot(graphics,x+slot.x,y+slot.y);
        GuiPaint.plus(graphics,x+61,y+48);
        GuiPaint.arrow(graphics,x+92,y+48,30);
    }
    @Override protected void renderLabels(GuiGraphics graphics,int mouseX,int mouseY){
        graphics.drawString(font,title,8,titleLabelY,0xFF2B3641,false);
        graphics.drawString(font,playerInventoryTitle,8,inventoryLabelY,GuiPaint.INK,false);
    }
    @Override public void render(GuiGraphics graphics,int x,int y,float partial){renderBackground(graphics);super.render(graphics,x,y,partial);renderTooltip(graphics,x,y);}
}
