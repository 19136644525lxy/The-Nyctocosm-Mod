package com.yifei.nyctocosm.client;

import net.minecraft.client.gui.GuiGraphics;

/** 模组 GUI 共用的纯色绘制：灰白渐变面板 + 内凹槽位，不依赖任何贴图。 */
public final class GuiPaint {
    public static final int BORDER=0xFF2B3641,SLOT_BORDER=0xFF37414B,SLOT_FILL=0xFF9AA1A7,INK=0xFF3B4652;
    private GuiPaint(){}
    /** 带投影与描边的竖向渐变面板。 */
    public static void panel(GuiGraphics g,int x,int y,int w,int h){
        g.fill(x-4,y-4,x+w+4,y+h+4,0x33000000);
        g.fill(x-2,y-2,x+w+2,y+h+2,BORDER);
        g.fillGradient(x,y,x+w,y+h,0xFFF3F4F5,0xFFCED2D5);
        g.fill(x,y,x+w,y+1,0x40FFFFFF);
    }
    /** 16×16 物品槽的内凹底座。 */
    public static void slot(GuiGraphics g,int px,int py){
        g.fill(px-1,py-1,px+17,py+17,SLOT_BORDER);
        g.fill(px,py,px+16,py+16,SLOT_FILL);
        g.fill(px,py,px+16,py+1,0x30000000);
        g.fill(px,py,px+1,py+16,0x20000000);
    }
    /** 水平箭头（2px 杆 + 4 段阶梯三角头），y 为箭头垂直中心。 */
    public static void arrow(GuiGraphics g,int x,int y,int length){
        int tail=x+length-4;
        g.fill(x,y-1,tail,y+1,INK);
        for(int i=0;i<4;i++)g.fill(tail+i,y-3+i,tail+i+1,y+3-i,INK);
    }
    /** 等长等粗的加号，cx/cy 为中心。 */
    public static void plus(GuiGraphics g,int cx,int cy){
        g.fill(cx-3,cy-1,cx+4,cy+2,INK);
        g.fill(cx-1,cy-3,cx+2,cy+4,INK);
    }
}
