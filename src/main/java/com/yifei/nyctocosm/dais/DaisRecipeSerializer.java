package com.yifei.nyctocosm.dais;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

/**
 * Serializer for {@link DaisRecipe}.
 *
 * <p>JSON shape: every input is a normal vanilla ingredient object plus a {@code count} field;
 * {@code result} is the usual item stack object (Forge supports an optional {@code nbt} field).
 * The same data is used by {@code /reload} and by the login recipe sync
 * ({@code ClientboundUpdateRecipesPacket}), so dedicated servers work without extra packets.</p>
 */
public final class DaisRecipeSerializer implements RecipeSerializer<DaisRecipe> {
    public static final DaisRecipeSerializer INSTANCE = new DaisRecipeSerializer();

    private DaisRecipeSerializer() {
    }

    @Override
    public DaisRecipe fromJson(ResourceLocation id, JsonObject json) {
        JsonObject first = GsonHelper.getAsJsonObject(json, "first");
        JsonObject second = GsonHelper.getAsJsonObject(json, "second");
        JsonObject result = GsonHelper.getAsJsonObject(json, "result");
        return new DaisRecipe(
            id,
            Ingredient.fromJson(first),
            count(first, "first"),
            Ingredient.fromJson(second),
            count(second, "second"),
            ShapedRecipe.itemStackFromJson(result),
            GsonHelper.getAsInt(json, "priority", 0));
    }

    private static int count(JsonObject input, String name) {
        int count = GsonHelper.getAsInt(input, "count", 1);
        if (count < 1) {
            throw new JsonSyntaxException("Dais recipe input '" + name + "' count must be at least 1");
        }
        return count;
    }

    /** Writes the recipe back to JSON; not part of the 1.20.1 serializer interface, kept for data generation. */
    public void toJson(JsonObject json, DaisRecipe recipe) {
        json.add("first", input(recipe.first(), recipe.firstCount()));
        json.add("second", input(recipe.second(), recipe.secondCount()));
        json.add("result", result(recipe.result()));
        if (recipe.priority() != 0) {
            json.addProperty("priority", recipe.priority());
        }
    }

    private static JsonObject input(Ingredient ingredient, int count) {
        JsonElement serialized = ingredient.toJson();
        if (!serialized.isJsonObject()) {
            throw new JsonSyntaxException("Dais recipe inputs must describe a single item or tag");
        }
        JsonObject out = serialized.getAsJsonObject();
        out.addProperty("count", count);
        return out;
    }

    private static JsonObject result(ItemStack stack) {
        JsonObject out = new JsonObject();
        out.addProperty("item", BuiltInRegistries.ITEM.getKey(stack.getItem()).toString());
        out.addProperty("count", stack.getCount());
        if (stack.hasTag()) {
            out.addProperty("nbt", stack.getTag().toString());
        }
        return out;
    }

    @Override
    public DaisRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
        Ingredient first = Ingredient.fromNetwork(buffer);
        int firstCount = buffer.readVarInt();
        Ingredient second = Ingredient.fromNetwork(buffer);
        int secondCount = buffer.readVarInt();
        ItemStack result = buffer.readItem();
        int priority = buffer.readVarInt();
        return new DaisRecipe(id, first, firstCount, second, secondCount, result, priority);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, DaisRecipe recipe) {
        recipe.first().toNetwork(buffer);
        buffer.writeVarInt(recipe.firstCount());
        recipe.second().toNetwork(buffer);
        buffer.writeVarInt(recipe.secondCount());
        buffer.writeItem(recipe.result());
        buffer.writeVarInt(recipe.priority());
    }
}
