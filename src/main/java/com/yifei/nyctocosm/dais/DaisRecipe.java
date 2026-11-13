package com.yifei.nyctocosm.dais;

import com.yifei.nyctocosm.Content;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

/**
 * Data-driven dais recipe: two fixed input slots, each with its own required count.
 *
 * <p>Files live in {@code data/<namespace>/recipes/**.json} and are reloaded by the vanilla
 * {@code RecipeManager}, so datapacks can add new dais recipes or override the built-in ones
 * by reusing the same id:</p>
 *
 * <pre>{@code
 * {
 *   "type": "nyctocosm:dais",
 *   "first":  { "item": "minecraft:soul_sand", "count": 4 },
 *   "second": { "tag": "forge:ingots/gold", "count": 4 },
 *   "result": { "item": "nyctocosm:seelic_essence", "count": 4 },
 *   "priority": 0
 * }
 * }</pre>
 *
 * <p>{@code priority} is optional. Higher priority wins when several recipes match; ties are
 * broken by recipe id so reloads stay deterministic.</p>
 */
public final class DaisRecipe implements Recipe<Container> {
    private final ResourceLocation id;
    private final Ingredient first;
    private final int firstCount;
    private final Ingredient second;
    private final int secondCount;
    private final ItemStack result;
    private final int priority;

    public DaisRecipe(ResourceLocation id, Ingredient first, int firstCount, Ingredient second, int secondCount, ItemStack result, int priority) {
        this.id = id;
        this.first = first;
        this.firstCount = Math.max(1, firstCount);
        this.second = second;
        this.secondCount = Math.max(1, secondCount);
        this.result = result.copy();
        this.priority = priority;
    }

    public Ingredient first() {
        return first;
    }

    public int firstCount() {
        return firstCount;
    }

    public Ingredient second() {
        return second;
    }

    public int secondCount() {
        return secondCount;
    }

    public int priority() {
        return priority;
    }

    /** Defensive copy of the produced stack. */
    public ItemStack result() {
        return result.copy();
    }

    /** The registered {@code nyctocosm:dais} recipe type. */
    @SuppressWarnings("unchecked")
    public static RecipeType<DaisRecipe> type() {
        return (RecipeType<DaisRecipe>) Content.DAIS_RECIPE_TYPE.get();
    }

    @Override
    public boolean matches(Container container, Level level) {
        return matchesInput(first, firstCount, container.getItem(0)) && matchesInput(second, secondCount, container.getItem(1));
    }

    private static boolean matchesInput(Ingredient ingredient, int count, ItemStack stack) {
        return !stack.isEmpty() && stack.getCount() >= count && ingredient.test(stack);
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registries) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registries) {
        return result.copy();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        ingredients.add(first);
        ingredients.add(second);
        return ingredients;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(Content.DAIS.get());
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Content.DAIS_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return type();
    }
}
