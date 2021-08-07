//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mjr.extraplanets.recipes;

import com.mjr.extraplanets.inventory.rockets.InventorySchematicTier5Rocket;
import com.mjr.extraplanets.items.ExtraPlanets_Items;
import micdoodle8.mods.galacticraft.api.recipe.INasaWorkbenchRecipe;
import micdoodle8.mods.galacticraft.core.recipe.NasaWorkbenchRecipe;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Tier5RocketRecipes {
    private static final List<INasaWorkbenchRecipe> tier5RocketRecipes = new ArrayList();

    public Tier5RocketRecipes() {
    }

    public static ItemStack findMatchingTier5RocketRecipe(InventorySchematicTier5Rocket inventoryRocketBench) {
        Iterator var1 = tier5RocketRecipes.iterator();

        INasaWorkbenchRecipe recipe;
        do {
            if (!var1.hasNext()) {
                return ItemStack.EMPTY;
            }

            recipe = (INasaWorkbenchRecipe) var1.next();
        } while (!recipe.matches(inventoryRocketBench));

        return recipe.getRecipeOutput();
    }

    public static void addTier5RocketRecipe(ItemStack result, HashMap<Integer, ItemStack> input) {
        addTier5RocketRecipe(new NasaWorkbenchRecipe(result, input));
    }

    public static void addTier5RocketRecipe(INasaWorkbenchRecipe recipe) {
        tier5RocketRecipes.add(recipe);
    }

    public static List<INasaWorkbenchRecipe> getTier5RocketRecipes() {
        return tier5RocketRecipes;
    }

    public static void removeTier5RocketRecipe(INasaWorkbenchRecipe recipe) {
        tier5RocketRecipes.remove(recipe);
    }

    public static void removeAllTier5RocketRecipes() {
        tier5RocketRecipes.clear();
    }

    public static void registerRocketCraftingRecipe() {
        HashMap<Integer, ItemStack> input = new HashMap();
        input.put(1, new ItemStack(ExtraPlanets_Items.TIER_5_NOSE_CONE));
        input.put(2, new ItemStack(ExtraPlanets_Items.TIER_5_ITEMS, 1, 3));
        input.put(3, new ItemStack(ExtraPlanets_Items.TIER_5_ITEMS, 1, 3));
        input.put(4, new ItemStack(ExtraPlanets_Items.TIER_5_ITEMS, 1, 3));
        input.put(5, new ItemStack(ExtraPlanets_Items.TIER_5_ITEMS, 1, 3));
        input.put(6, new ItemStack(ExtraPlanets_Items.TIER_5_ITEMS, 1, 3));
        input.put(7, new ItemStack(ExtraPlanets_Items.TIER_5_ITEMS, 1, 3));
        input.put(8, new ItemStack(ExtraPlanets_Items.TIER_5_ITEMS, 1, 3));
        input.put(9, new ItemStack(ExtraPlanets_Items.TIER_5_ITEMS, 1, 3));
        input.put(10, new ItemStack(ExtraPlanets_Items.TIER_5_ITEMS, 1, 3));
        input.put(11, new ItemStack(ExtraPlanets_Items.TIER_5_ITEMS, 1, 3));
        input.put(12, new ItemStack(ExtraPlanets_Items.TIER_5_ITEMS, 1, 1));
        input.put(13, new ItemStack(ExtraPlanets_Items.TIER_5_ITEMS, 1, 2));
        input.put(14, new ItemStack(ExtraPlanets_Items.TIER_5_ITEMS, 1, 2));
        input.put(15, new ItemStack(ExtraPlanets_Items.TIER_5_ITEMS, 1, 0));
        input.put(16, new ItemStack(ExtraPlanets_Items.TIER_5_ITEMS, 1, 1));
        input.put(17, new ItemStack(ExtraPlanets_Items.TIER_5_ITEMS, 1, 2));
        input.put(18, new ItemStack(ExtraPlanets_Items.TIER_5_ITEMS, 1, 2));
        input.put(19, ItemStack.EMPTY);
        input.put(20, ItemStack.EMPTY);
        input.put(21, ItemStack.EMPTY);
        addTier5RocketRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.TIER_5_ROCKET, 1, 0), input));
        //HashMap<Integer, ItemStack> input2 = new HashMap(input);
        //input2.put(19, new ItemStack(Blocks.CHEST));
        //input2.put(20, ItemStack.EMPTY);
        //input2.put(21, ItemStack.EMPTY);
        //addTier5RocketRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.TIER_5_ROCKET, 1, 1), input2));
        //input2 = new HashMap(input);
        //input2.put(19, ItemStack.EMPTY);
        //input2.put(20, new ItemStack(Blocks.CHEST));
        //input2.put(21, ItemStack.EMPTY);
        //addTier5RocketRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.TIER_5_ROCKET, 1, 1), input2));
        //input2 = new HashMap(input);
        //input2.put(19, ItemStack.EMPTY);
        //input2.put(20, ItemStack.EMPTY);
        //input2.put(21, new ItemStack(Blocks.CHEST));
        //addTier5RocketRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.TIER_5_ROCKET, 1, 1), input2));
        //input2 = new HashMap(input);
        //input2.put(19, new ItemStack(Blocks.CHEST));
        //input2.put(20, new ItemStack(Blocks.CHEST));
        //input2.put(21, ItemStack.EMPTY);
        //addTier5RocketRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.TIER_5_ROCKET, 1, 2), input2));
        //input2 = new HashMap(input);
        //input2.put(19, new ItemStack(Blocks.CHEST));
        //input2.put(20, ItemStack.EMPTY);
        //input2.put(21, new ItemStack(Blocks.CHEST));
        //addTier5RocketRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.TIER_5_ROCKET, 1, 2), input2));
        //input2 = new HashMap(input);
        //input2.put(19, ItemStack.EMPTY);
        //input2.put(20, new ItemStack(Blocks.CHEST));
        //input2.put(21, new ItemStack(Blocks.CHEST));
        //addTier5RocketRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.TIER_5_ROCKET, 1, 2), input2));
        //input2 = new HashMap(input);
        //input2.put(19, new ItemStack(Blocks.CHEST));
        //input2.put(20, new ItemStack(Blocks.CHEST));
        //input2.put(21, new ItemStack(Blocks.CHEST));
        //addTier5RocketRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.TIER_5_ROCKET, 1, 3), input2));
    }
}
