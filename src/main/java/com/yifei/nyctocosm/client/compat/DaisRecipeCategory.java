package com.yifei.nyctocosm.client.compat;

import com.yifei.nyctocosm.client.GuiPaint;
import com.yifei.nyctocosm.dais.DaisRecipe;
import java.util.ArrayList;
import java.util.List;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

/** 祭坛双输入+单输出分类：面板风格与 DaisScreen 一致（灰白渐变 + 内凹槽位 + 箭头）。 */
public final class DaisRecipeCategory implements IRecipeCategory<DaisRecipe> {
    public static final int WIDTH=146,HEIGHT=54;
    private static final int IN_A=36,IN_B=72,OUT=110,SLOT_Y=20;
    private final IDrawable background=new Background(),icon;

    public DaisRecipeCategory(IGuiHelper helper) {
        icon=helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,NyctocosmJeiPlugin.daisIcon());
    }

    /** 背景先于物品绘制，槽位底座与装饰必须画在这里，否则会盖住物品。 */
    private static final class Background implements IDrawable {
        @Override public int getWidth() {return WIDTH;}
        @Override public int getHeight() {return HEIGHT;}
        @Override public void draw(GuiGraphics graphics,int x,int y) {
            GuiPaint.panel(graphics,x,y,WIDTH,HEIGHT);
            GuiPaint.slot(graphics,x+IN_A,y+SLOT_Y);
            GuiPaint.slot(graphics,x+IN_B,y+SLOT_Y);
            GuiPaint.slot(graphics,x+OUT,y+SLOT_Y);
            GuiPaint.plus(graphics,x+62,y+SLOT_Y+8);
            GuiPaint.arrow(graphics,x+92,y+SLOT_Y+8,16);
        }
    }

    @Override public RecipeType<DaisRecipe> getRecipeType() {return NyctocosmJeiPlugin.DAIS_TYPE;}
    @Override public Component getTitle() {return Component.translatable("gui.nyctocosm.dais_title");}
    @Override public IDrawable getBackground() {return background;}
    @Override public IDrawable getIcon() {return icon;}

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder,DaisRecipe recipe,IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT,IN_A,SLOT_Y).addItemStacks(stacks(recipe.first(),recipe.firstCount()));
        builder.addSlot(RecipeIngredientRole.INPUT,IN_B,SLOT_Y).addItemStacks(stacks(recipe.second(),recipe.secondCount()));
        builder.addSlot(RecipeIngredientRole.OUTPUT,OUT,SLOT_Y).addItemStack(recipe.result());
    }

    /** 每个匹配物品都显示配方要求的数量。 */
    private static List<ItemStack> stacks(Ingredient ingredient,int count) {
        List<ItemStack> stacks=new ArrayList<>();
        for(ItemStack stack:ingredient.getItems()) {
            ItemStack copy=stack.copy();
            copy.setCount(count);
            stacks.add(copy);
        }
        return stacks;
    }
}
