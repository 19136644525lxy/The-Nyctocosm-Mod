package com.yifei.nyctocosm.client.compat;

import com.yifei.nyctocosm.dais.DaisMenu;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

/** 祭坛双输入+单输出分类：槽位坐标对齐 DaisMenu GUI（35/71/129, 40），数量直接文本绘制。 */
public final class DaisRecipeCategory implements IRecipeCategory<DaisMenu.Recipe> {
    public static final int WIDTH=130,HEIGHT=62;
    private final IDrawable background,slot,icon;

    public DaisRecipeCategory(IGuiHelper helper) {
        background=helper.createBlankDrawable(WIDTH,HEIGHT);
        slot=helper.getSlotDrawable();
        icon=helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,NyctocosmJeiPlugin.daisIcon());
    }

    @Override public mezz.jei.api.recipe.RecipeType<DaisMenu.Recipe> getRecipeType() {return NyctocosmJeiPlugin.DAIS_TYPE;}
    @Override public Component getTitle() {return Component.translatable("itemGroup.nyctocosm.world");}
    @Override public IDrawable getBackground() {return background;}
    @Override public IDrawable getIcon() {return icon;}

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder,DaisMenu.Recipe recipe,IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT,35,24).setBackground(slot,0,0).addItemStack(new ItemStack(recipe.first(),recipe.firstCount()));
        builder.addSlot(RecipeIngredientRole.INPUT,71,24).setBackground(slot,0,0).addItemStack(new ItemStack(recipe.second(),recipe.secondCount()));
        builder.addSlot(RecipeIngredientRole.OUTPUT,103,24).setBackground(slot,0,0).addItemStack(new ItemStack(recipe.result(),recipe.resultCount()));
    }

    @Override
    public void draw(DaisMenu.Recipe recipe,IRecipeSlotsView recipeSlots,GuiGraphics graphics,double mouseX,double mouseY) {
        graphics.fill(92,30,98,32,0xFF808080);graphics.fill(90,31,96,33,0xFF808080);
    }
}
