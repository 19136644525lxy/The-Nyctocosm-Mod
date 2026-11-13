package com.yifei.nyctocosm.client.compat;

import com.yifei.nyctocosm.NyctocosmMod;
import com.yifei.nyctocosm.dais.DaisRecipe;
import java.util.List;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

/** JEI 只读集成：祭坛配方来自数据包（{@link DaisRecipe}），未安装 JEI 时本类不加载。 */
@JeiPlugin
public class NyctocosmJeiPlugin implements IModPlugin {
    public static final ResourceLocation DAIS_UID=ResourceLocation.fromNamespaceAndPath(NyctocosmMod.ID,"dais");
    public static final mezz.jei.api.recipe.RecipeType<DaisRecipe> DAIS_TYPE=new mezz.jei.api.recipe.RecipeType<>(DAIS_UID,DaisRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {return ResourceLocation.fromNamespaceAndPath(NyctocosmMod.ID,"jei_plugin");}

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper helper=registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new DaisRecipeCategory(helper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        // 配方由 RecipeManager 在登录/重载时同步到客户端，这里直接读取即可
        var level=Minecraft.getInstance().level;
        registration.addRecipes(DAIS_TYPE,level==null?List.of():level.getRecipeManager().getAllRecipesFor(DaisRecipe.type()));
    }

    /** 分类内物品图标（惰性构建，避免类加载期触碰注册表）。 */
    static ItemStack daisIcon() {return new ItemStack(com.yifei.nyctocosm.Content.DAIS.get().asItem());}
}
