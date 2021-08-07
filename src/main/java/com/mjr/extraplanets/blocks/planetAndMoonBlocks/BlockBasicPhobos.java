//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mjr.extraplanets.blocks.planetAndMoonBlocks;

import com.google.common.base.Predicate;
import com.mjr.extraplanets.ExtraPlanets;
import micdoodle8.mods.galacticraft.api.block.IDetectableResource;
import micdoodle8.mods.galacticraft.api.block.IPlantableBlock;
import micdoodle8.mods.galacticraft.api.block.ITerraformableBlock;
import micdoodle8.mods.galacticraft.core.blocks.ISortableBlock;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockBasicPhobos extends Block implements IDetectableResource, IPlantableBlock, ITerraformableBlock, ISortableBlock {
    public static final PropertyEnum<BlockBasicPhobos.EnumBlockBasic> BASIC_TYPE = PropertyEnum.create("basictypephobos", BlockBasicPhobos.EnumBlockBasic.class);

    public BlockBasicPhobos(String name) {
        super(Material.ROCK);
        this.setUnlocalizedName(name);
        this.setCreativeTab(ExtraPlanets.BlocksTab);
    }

    public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
        IBlockState state = world.getBlockState(pos);
        if (state.getValue(BASIC_TYPE) == BlockBasicPhobos.EnumBlockBasic.STONE) {
            return 6.0F;
        } else {
            return state.getValue(BASIC_TYPE) != BlockBasicPhobos.EnumBlockBasic.ORE_COPPER && state.getValue(BASIC_TYPE) != BlockBasicPhobos.EnumBlockBasic.ORE_IRON && state.getValue(BASIC_TYPE) != BlockBasicPhobos.EnumBlockBasic.ORE_TIN ? super.getExplosionResistance(world, pos, exploder, explosion) : 3.0F;
        }
    }

    public float getBlockHardness(IBlockState state, World world, BlockPos pos) {
        if (state.getValue(BASIC_TYPE) != BlockBasicPhobos.EnumBlockBasic.SURFACE && state.getValue(BASIC_TYPE) != BlockBasicPhobos.EnumBlockBasic.SUB_SURFACE) {
            return state.getValue(BASIC_TYPE) != BlockBasicPhobos.EnumBlockBasic.ORE_COPPER && state.getValue(BASIC_TYPE) != BlockBasicPhobos.EnumBlockBasic.ORE_IRON && state.getValue(BASIC_TYPE) != BlockBasicPhobos.EnumBlockBasic.ORE_TIN ? 1.5F : 5.0F;
        } else {
            return 0.5F;
        }
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        EnumBlockBasic type = state.getValue(BASIC_TYPE);
        switch (type) {
            case ORE_IRON:
                return Item.getItemFromBlock(this);
            // new ItemStack(MarsBlocks.marsBlock, 1, 3).getItem();
            //return new ItemStack(Blocks.IRON_ORE).getItem();
            case ORE_TIN:
                return Item.getItemFromBlock(this);
            //return BlockName.resource.getInstance().getItemDropped(BlockName.resource.getBlockState(ResourceBlock.lead_ore),rand,0);
            //return new ItemStack(GCBlocks.blockMoon, 1, 1).getItem();
            case ORE_COPPER:
                return Item.getItemFromBlock(this);
            //return BlockName.resource.getInstance().getItemDropped(BlockName.resource.getBlockState(ResourceBlock.lead_block),rand,0);
            default:
                return Item.getItemFromBlock(this);
        }
        //return Item.getItemFromBlock(this);
    }

    public int damageDropped(IBlockState state) {
        int meta = state.getBlock().getMetaFromState(state);
        return meta;
    }

    public int quantityDropped(IBlockState state, int fortune, Random random) {
        return 1;
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        BlockBasicPhobos.EnumBlockBasic[] var3 = BlockBasicPhobos.EnumBlockBasic.values();
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            BlockBasicPhobos.EnumBlockBasic blockBasic = var3[var5];
            list.add(new ItemStack(this, 1, blockBasic.getMeta()));
        }

    }

    public boolean isValueable(IBlockState state) {
        BlockBasicPhobos.EnumBlockBasic type = state.getValue(BASIC_TYPE);
        switch (type) {
            case ORE_IRON:
            case ORE_TIN:
            case ORE_COPPER:
                return true;
            default:
                return false;
        }
    }

    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        return false;
    }

    public int requiredLiquidBlocksNearby() {
        return 4;
    }

    public boolean isPlantable(IBlockState arg0) {
        return false;
    }

    public boolean isTerraformable(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        IBlockState stateAbove = world.getBlockState(pos.up());
        return state.getValue(BASIC_TYPE) == BlockBasicPhobos.EnumBlockBasic.SURFACE && !stateAbove.isFullCube();
    }

    public boolean isReplaceableOreGen(IBlockState state, IBlockAccess world, BlockPos pos, Predicate<IBlockState> target) {
        if (target != Blocks.STONE) {
            return false;
        } else {
            return state.getValue(BASIC_TYPE) == BlockBasicPhobos.EnumBlockBasic.STONE;
        }
    }

    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(BASIC_TYPE, BlockBasicPhobos.EnumBlockBasic.byMetadata(meta));
    }

    public int getMetaFromState(IBlockState state) {
        return state.getValue(BASIC_TYPE).getMeta();
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BASIC_TYPE);
    }

    public EnumSortCategoryBlock getCategory(int meta) {
        switch (meta) {
            case 3:
            case 4:
            case 5:
                return EnumSortCategoryBlock.ORE;
            default:
                return EnumSortCategoryBlock.GENERAL;
        }
    }

    public enum EnumBlockBasic implements IStringSerializable {
        SURFACE(0, "phobos_surface"),
        SUB_SURFACE(1, "phobos_sub_surface"),
        STONE(2, "phobos_stone"),
        ORE_IRON(3, "phobos_ore_iron"),
        ORE_TIN(4, "phobos_ore_tin"),
        ORE_COPPER(5, "phobos_ore_copper");

        private final int meta;
        private final String name;

        EnumBlockBasic(int meta, String name) {
            this.meta = meta;
            this.name = name;
        }

        public static BlockBasicPhobos.EnumBlockBasic byMetadata(int meta) {
            return values()[meta];
        }

        public int getMeta() {
            return this.meta;
        }

        public String getName() {
            return this.name;
        }
    }
}
