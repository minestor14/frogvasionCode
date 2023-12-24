package me.Minestor.frogvasion.recipe;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FloradicAltarRecipe implements Recipe<SimpleInventory> {
    private final ItemStack output;
    private final List<Ingredient> ingredients;
    private final Optional<String> nbt;
    public FloradicAltarRecipe(ItemStack output, List<Ingredient> ingredients, Optional<String> nbt) {
        this.output = output;
        this.ingredients = ingredients;

        if(nbt.isPresent()){
            try{
                this.output.setNbt(StringNbtReader.parse(nbt.get()));
            } catch (CommandSyntaxException ignored) {}
        }
        this.nbt = nbt;
    }
    @Override
    public boolean matches(SimpleInventory inv, World world) {
        if(world.isClient) return false;
        List<Integer> invCompleted = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            for (int j = 0; j < inv.size(); j++) {
                if (ingredient.test(inv.getStack(j)) && !invCompleted.contains(j)) {
                    invCompleted.add(j);
                    break;
                }
            }
        }
        return invCompleted.size() == 4;
    }

    @Override
    public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        return output;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(DynamicRegistryManager registryManager) {
        ItemStack stack = output.copy();
        if(nbt.isPresent()){
            try {
                stack.setNbt(StringNbtReader.parse(nbt.get()));
            } catch (CommandSyntaxException ignored) {}
        }
        return stack;
    }
    public ItemStack getOutput() {
        return getResult(DynamicRegistryManager.EMPTY);
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return DefaultedList.copyOf(Ingredient.EMPTY, ingredients.get(0), ingredients.get(1), ingredients.get(2), ingredients.get(3));
    }

    public List<Ingredient> getIngredientsList() {
        return ingredients;
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

    public static class Type implements RecipeType<FloradicAltarRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "floradic_altar_recipe";
    }
    public static class Serializer implements RecipeSerializer<FloradicAltarRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "floradic_altar_recipe";
        public static final Codec<FloradicAltarRecipe> CODEC = RecordCodecBuilder.create(in -> in.group(
                ItemStack.RECIPE_RESULT_CODEC.fieldOf("output").forGetter(r -> r.output),
                validateAmount(Ingredient.DISALLOW_EMPTY_CODEC, 4).fieldOf("ingredients").forGetter(FloradicAltarRecipe::getIngredientsList),
                Codecs.NON_EMPTY_STRING.optionalFieldOf("nbt_output").forGetter(r -> r.nbt)
        ).apply(in, FloradicAltarRecipe::new));

        private static Codec<List<Ingredient>> validateAmount(Codec<Ingredient> delegate, int count) {
            return Codecs.validate(
                    delegate.listOf(), list -> list.size() != count ? DataResult.error(() -> "Recipe must have exactly " + count + " ingredients!") : DataResult.success(list)
            );
        }

        @Override
        public Codec<FloradicAltarRecipe> codec() {
            return CODEC;
        }

        @Override
        public FloradicAltarRecipe read(PacketByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.copyOf(Ingredient.EMPTY,
                    Ingredient.fromPacket(buf), Ingredient.fromPacket(buf), Ingredient.fromPacket(buf), Ingredient.fromPacket(buf));

            ItemStack output = buf.readItemStack();
            return new FloradicAltarRecipe(output, inputs, buf.readOptional(PacketByteBuf::readString));
        }

        @Override
        public void write(PacketByteBuf buf, FloradicAltarRecipe recipe) {
            for (Ingredient ing : recipe.getIngredients()) {
                ing.write(buf);
            }
            buf.writeItemStack(recipe.getOutput());
            buf.writeOptional(recipe.nbt, PacketByteBuf::writeString);
        }
    }
}
