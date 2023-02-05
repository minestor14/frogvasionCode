package me.Minestor.frogvasion.Recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class ConversionPedestalRecipe implements Recipe<SimpleInventory> {
    private final Identifier id;
    private final ItemStack output;
    private final DefaultedList<Ingredient> ingredients;

    public ConversionPedestalRecipe(Identifier id,ItemStack output,DefaultedList<Ingredient> ingredients){
        this.id = id;
        this.output = output;
        this.ingredients = ingredients;
    }


    @Override
    public boolean matches(SimpleInventory inv, World world) {
        if(world.isClient()) return false;
        return ingredients.get(0).test(inv.getStack(0)) && ingredients.get(1).test(inv.getStack(1));
    }

    @Override
    public ItemStack craft(SimpleInventory inventory) {
        return output;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput() {
        return output.copy();
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<ConversionPedestalRecipe> {
        private Type(){}
        public static final Type INSTANCE = new Type();
        public static final String ID = "conversion_recipe";
    }
    public static class Serializer implements RecipeSerializer<ConversionPedestalRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "conversion_recipe";

        @Override
        public ConversionPedestalRecipe read(Identifier id, JsonObject json) {
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "output"));

            JsonArray ingredients = JsonHelper.getArray(json, "ingredients");
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(2, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new ConversionPedestalRecipe(id, output, inputs);
        }

        @Override
        public ConversionPedestalRecipe read(Identifier id, PacketByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromPacket(buf));
            }

            ItemStack output = buf.readItemStack();
            return new ConversionPedestalRecipe(id, output, inputs);
        }

        @Override
        public void write(PacketByteBuf buf, ConversionPedestalRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.write(buf);
            }
            buf.writeItemStack(recipe.getOutput());
        }
    }
}
