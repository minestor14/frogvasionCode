package me.Minestor.frogvasion.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.World;

import java.util.List;

public class ConversionPedestalRecipe implements Recipe<SimpleInventory> {
    private final ItemStack output;
    private final List<Ingredient> ingredients;

    public ConversionPedestalRecipe(ItemStack output, List<Ingredient> ingredients){
        this.output = output;
        this.ingredients = ingredients;
    }


    @Override
    public boolean matches(SimpleInventory inv, World world) {
        if(world.isClient()) return false;
        return ingredients.get(0).test(inv.getStack(0)) && ingredients.get(1).test(inv.getStack(1));
    }

    @Override
    public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        return output;
    }

    public List<Ingredient> getIngredientList() {
        return ingredients;
    }
    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return DefaultedList.copyOf(Ingredient.EMPTY, ingredients.get(0), ingredients.get(1));
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(DynamicRegistryManager registryManager) {
        return output.copy();
    }

    public ItemStack getOutput() {
        return output.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }
    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    public static class Type implements RecipeType<ConversionPedestalRecipe> {
        private Type(){}
        public static final Type INSTANCE = new Type();
        public static final String ID = "conversion_recipe";
    }
    public static class Serializer implements RecipeSerializer<ConversionPedestalRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "conversion_recipe";
        public static final Codec<ConversionPedestalRecipe> CODEC = RecordCodecBuilder.create(in -> in.group(
                ItemStack.RECIPE_RESULT_CODEC.fieldOf("output").forGetter(r -> r.output),
                validateAmount(Ingredient.DISALLOW_EMPTY_CODEC, 2).fieldOf("ingredients").forGetter(ConversionPedestalRecipe::getIngredientList)
        ).apply(in, ConversionPedestalRecipe::new));

        private static Codec<List<Ingredient>> validateAmount(Codec<Ingredient> delegate, int count) {
            return Codecs.validate(
                    delegate.listOf(), list -> list.size() != count ? DataResult.error(() -> "Recipe must have exactly " + count +" ingredients!") : DataResult.success(list));
        }

        @Override
        public Codec<ConversionPedestalRecipe> codec() {
            return CODEC;
        }

        @Override
        public ConversionPedestalRecipe read(PacketByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.copyOf(Ingredient.EMPTY,
                    Ingredient.fromPacket(buf), Ingredient.fromPacket(buf));

            ItemStack output = buf.readItemStack();
            return new ConversionPedestalRecipe(output, inputs);
        }

        @Override
        public void write(PacketByteBuf buf, ConversionPedestalRecipe recipe) {
            for (Ingredient ing : recipe.getIngredients()) {
                ing.write(buf);
            }
            buf.writeItemStack(recipe.getOutput());
        }
    }
}
