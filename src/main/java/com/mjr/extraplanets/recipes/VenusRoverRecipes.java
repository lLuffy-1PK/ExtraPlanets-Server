//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mjr.extraplanets.recipes;

import com.mjr.extraplanets.inventory.vehicles.InventoryVenusRover;
import com.mjr.extraplanets.items.ExtraPlanets_Items;
import micdoodle8.mods.galacticraft.api.recipe.INasaWorkbenchRecipe;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.recipe.NasaWorkbenchRecipe;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class VenusRoverRecipes {
    private static final List<INasaWorkbenchRecipe> venusRoverRecipes = new ArrayList();

    public VenusRoverRecipes() {
    }

    public static ItemStack findMatchingVenusRoverRecipe(InventoryVenusRover craftMatrix) {
        Iterator var1 = venusRoverRecipes.iterator();

        INasaWorkbenchRecipe recipe;
        do {
            if (!var1.hasNext()) {
                return ItemStack.EMPTY;
            }

            recipe = (INasaWorkbenchRecipe) var1.next();
        } while (!recipe.matches(craftMatrix));

        return recipe.getRecipeOutput();
    }

    public static void addVenusRoverRecipe(ItemStack result, HashMap<Integer, ItemStack> input) {
        addVenusRoverRecipe(new NasaWorkbenchRecipe(result, input));
    }

    public static void addVenusRoverRecipe(INasaWorkbenchRecipe recipe) {
        venusRoverRecipes.add(recipe);
    }

    public static List<INasaWorkbenchRecipe> getVenusRoverRecipes() {
        return venusRoverRecipes;
    }

    public static void removeVenusRoverRecipe(INasaWorkbenchRecipe recipe) {
        venusRoverRecipes.remove(recipe);
    }

    public static void removeAllVenusRoverRecipes() {
        venusRoverRecipes.clear();
    }

    public static void registerRoverCraftingRecipe() {
        HashMap<Integer, ItemStack> input = new HashMap();
        input.put(1, new ItemStack(ExtraPlanets_Items.TIER_4_ITEMS, 1, 3));
        input.put(2, new ItemStack(ExtraPlanets_Items.TIER_4_ITEMS, 1, 3));
        input.put(3, new ItemStack(ExtraPlanets_Items.TIER_4_ITEMS, 1, 3));
        input.put(4, new ItemStack(ExtraPlanets_Items.TIER_4_ITEMS, 1, 3));
        input.put(5, new ItemStack(ExtraPlanets_Items.TIER_4_ITEMS, 1, 3));
        input.put(6, new ItemStack(ExtraPlanets_Items.TIER_4_ITEMS, 1, 3));
        input.put(7, new ItemStack(GCItems.partBuggy, 1, 1));
        input.put(8, new ItemStack(ExtraPlanets_Items.TIER_4_ITEMS, 1, 3));
        input.put(9, new ItemStack(ExtraPlanets_Items.TIER_4_ITEMS, 1, 3));
        input.put(10, new ItemStack(ExtraPlanets_Items.TIER_4_ITEMS, 1, 3));
        input.put(11, new ItemStack(ExtraPlanets_Items.TIER_4_ITEMS, 1, 3));
        input.put(12, new ItemStack(ExtraPlanets_Items.TIER_4_ITEMS, 1, 3));
        input.put(13, new ItemStack(ExtraPlanets_Items.TIER_4_ITEMS, 1, 3));
        input.put(14, new ItemStack(ExtraPlanets_Items.TIER_4_ITEMS, 1, 3));
        input.put(15, new ItemStack(ExtraPlanets_Items.TIER_4_ITEMS, 1, 3));
        input.put(16, new ItemStack(ExtraPlanets_Items.ELECTRIC_PARTS, 1, 3));
        input.put(17, new ItemStack(ExtraPlanets_Items.ELECTRIC_PARTS, 1, 1));
        input.put(18, new ItemStack(ExtraPlanets_Items.ELECTRIC_PARTS, 1, 3));
        input.put(19, new ItemStack(ExtraPlanets_Items.ELECTRIC_PARTS, 1, 3));
        input.put(20, new ItemStack(ExtraPlanets_Items.ELECTRIC_PARTS, 1, 1));
        input.put(21, new ItemStack(ExtraPlanets_Items.ELECTRIC_PARTS, 1, 3));
        input.put(22, ItemStack.EMPTY);
        input.put(23, ItemStack.EMPTY);
        input.put(24, ItemStack.EMPTY);
        addVenusRoverRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.VENUS_ROVER, 1, 0), input));
        //HashMap<Integer, ItemStack> input2 = new HashMap(input);
        //input2.put(22, new ItemStack(Blocks.CHEST));
        //input.put(23, ItemStack.EMPTY);
        //input.put(24, ItemStack.EMPTY);
        //addVenusRoverRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.VENUS_ROVER, 1, 1), input2));
        //input2 = new HashMap(input);
        //input2.put(22, ItemStack.EMPTY);
        //input2.put(23, new ItemStack(Blocks.CHEST));
        //input2.put(24, ItemStack.EMPTY);
        //addVenusRoverRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.VENUS_ROVER, 1, 1), input2));
        //input2 = new HashMap(input);
        //input2.put(22, ItemStack.EMPTY);
        //input2.put(23, ItemStack.EMPTY);
        //input2.put(24, new ItemStack(Blocks.CHEST));
        //addVenusRoverRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.VENUS_ROVER, 1, 1), input2));
        //input2 = new HashMap(input);
        //input2.put(22, new ItemStack(Blocks.CHEST));
        //input2.put(23, new ItemStack(Blocks.CHEST));
        //input2.put(24, ItemStack.EMPTY);
        //addVenusRoverRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.VENUS_ROVER, 1, 2), input2));
        //input2 = new HashMap(input);
        //input2.put(22, new ItemStack(Blocks.CHEST));
        //input2.put(23, ItemStack.EMPTY);
        //input2.put(24, new ItemStack(Blocks.CHEST));
        //addVenusRoverRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.VENUS_ROVER, 1, 2), input2));
        //input2 = new HashMap(input);
        //input2.put(22, ItemStack.EMPTY);
        //input2.put(23, new ItemStack(Blocks.CHEST));
        //input2.put(24, new ItemStack(Blocks.CHEST));
        //addVenusRoverRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.VENUS_ROVER, 1, 2), input2));
        //input2 = new HashMap(input);
        //input2.put(22, new ItemStack(Blocks.CHEST));
        //input2.put(23, new ItemStack(Blocks.CHEST));
        //input2.put(24, new ItemStack(Blocks.CHEST));
        //addVenusRoverRecipe(new NasaWorkbenchRecipe(new ItemStack(ExtraPlanets_Items.VENUS_ROVER, 1, 3), input2));
    }
}
