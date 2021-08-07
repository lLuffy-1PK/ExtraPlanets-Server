//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mjr.extraplanets.recipes;

import com.mjr.extraplanets.inventory.rockets.InventorySchematicTier9Rocket;
import com.mjr.extraplanets.items.ExtraPlanets_Items;
import micdoodle8.mods.galacticraft.api.recipe.INasaWorkbenchRecipe;
import micdoodle8.mods.galacticraft.core.recipe.NasaWorkbenchRecipe;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Tier9RocketRecipes {
    private static final List<INasaWorkbenchRecipe> tier9RocketRecipes = new ArrayList();

    public Tier9RocketRecipes() {
    }

    public static ItemStack findMatchingTier9RocketRecipe(InventorySchematicTier9Rocket inventoryRocketBench) {
        Iterator var1 = tier9RocketRecipes.iterator();

        INasaWorkbenchRecipe recipe;
        do {
            if (!var1.hasNext()) {
                return ItemStack.EMPTY;
            }

            recipe = (INasaWorkbenchRecipe) var1.next();
        } while (!recipe.matches(inventoryRocketBench));

        return recipe.getRecipeOutput();
    }

    public static void addTier9RocketRecipe(ItemStack result, HashMap<Integer, ItemStack> input) {
        addTier9RocketRecipe(new NasaWorkbenchRecipe(result, input));
    }

    public static void addTier9RocketRecipe(INasaWorkbenchRecipe recipe) {
        tier9RocketRecipes.add(recipe);
    }

    public static List<INasaWorkbenchRecipe> getTier9RocketRecipes() {
        return tier9RocketRecipes;
    }

    public static void removeTier9RocketRecipe(INasaWorkbenchRecipe recipe) {
        tier9RocketRecipes.remove(recipe);
    }

    public static void removeAllTier9RocketRecipes() {
        tier9RocketRecipes.clear();
    }

    public static void registerRocketCraftingRecipe() {
        HashMap<Integer, ItemStack> input = new HashMap();
        input.put(1, new ItemStack(ExtraPlanets_Items.TIER_9_NOSE_CONE));
        input.put(2, new ItemStack(ExtraPlanets_Items.TIER_9_ITEMS, 1, 3));
        input.put(3, new ItemStack(ExtraPlanets_Items.TIER_9_ITEMS, 1, 3));
        input.put(4, new ItemStack(ExtraPlanets_Items.TIER_9_ITEMS, 1, 3));
        input.put(5, new ItemStack(ExtraPlanets_Items.TIER_9_ITEMS, 1, 3));
        input.put(6, new ItemStack(ExtraPlanets_Items.TIER_9_ITEMS, 1, 3));
        input.put(7, new ItemStack(ExtraPlanets_Items.TIER_9_ITEMS, 1, 3));
        input.put(8, new ItemStack(ExtraPlanets_Items.TIER_9_ITEMS, 1, 3));
        input.put(9, new ItemStack(ExtraPlanets_Items.TIER_9_ITEMS, 1, 3));
        input.put(10, new ItemStack(ExtraPlanets_Items.TIER_9_ITEMS, 1, 3));
        input.put(11, new ItemStack(ExtraPlanets_Items.TIER_9_ITEMS, 1, 3));
        input.put(12, new ItemStack(ExtraPlanets_Items.TIER_9_ITEMS, 1, 1));
        input.put(13, new ItemStack(ExtraPlanets_Items.TIER_9_ITEMS, 1, 2));
        input.put(14, new ItemStack(ExtraPlanets_Items.TIER_9_ITEMS, 1, 2));
        input.put(15, new ItemStack(ExtraPlanets_Items.TIER_9_ITEMS, 1, 0));
        input.put(16, new ItemStack(ExtraPlanets_Items.TIER_9_ITEMS, 1, 1));
        input.put(17, new ItemStack(ExtraPlanets_Items.TIER_9_ITEMS, 1, 2));
        input.put(18, new ItemStack(ExtraPlanets_Items.TIER_9_ITEMS, 1, 2));
        input.put(19, ItemStack.EMPTY);
        input.put(20, ItemStack.EMPTY);
        input.put(21, ItemStack.EMPTY);
        addTier9RocketRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.TIER_9_ROCKET, 1, 0), input));
        //HashMap<Integer, ItemStack> input2 = new HashMap(input);
        //input2.put(19, new ItemStack(Blocks.CHEST));
        //input2.put(20, ItemStack.EMPTY);
        //input2.put(21, ItemStack.EMPTY);
        //addTier9RocketRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.TIER_9_ROCKET, 1, 1), input2));
        //input2 = new HashMap(input);
        //input2.put(19, ItemStack.EMPTY);
        //input2.put(20, new ItemStack(Blocks.CHEST));
        //input2.put(21, ItemStack.EMPTY);
        //addTier9RocketRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.TIER_9_ROCKET, 1, 1), input2));
        //input2 = new HashMap(input);
        //input2.put(19, ItemStack.EMPTY);
        //input2.put(20, ItemStack.EMPTY);
        //input2.put(21, new ItemStack(Blocks.CHEST));
        //addTier9RocketRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.TIER_9_ROCKET, 1, 1), input2));
        //input2 = new HashMap(input);
        //input2.put(19, new ItemStack(Blocks.CHEST));
        //input2.put(20, new ItemStack(Blocks.CHEST));
        //input2.put(21, ItemStack.EMPTY);
        //addTier9RocketRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.TIER_9_ROCKET, 1, 2), input2));
        //input2 = new HashMap(input);
        //input2.put(19, new ItemStack(Blocks.CHEST));
        //input2.put(20, ItemStack.EMPTY);
        //input2.put(21, new ItemStack(Blocks.CHEST));
        //addTier9RocketRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.TIER_9_ROCKET, 1, 2), input2));
        //input2 = new HashMap(input);
        //input2.put(19, ItemStack.EMPTY);
        //input2.put(20, new ItemStack(Blocks.CHEST));
        //input2.put(21, new ItemStack(Blocks.CHEST));
        //addTier9RocketRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.TIER_9_ROCKET, 1, 2), input2));
        //input2 = new HashMap(input);
        //input2.put(19, new ItemStack(Blocks.CHEST));
        //input2.put(20, new ItemStack(Blocks.CHEST));
        //input2.put(21, new ItemStack(Blocks.CHEST));
        //addTier9RocketRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.TIER_9_ROCKET, 1, 3), input2));
    }
}
