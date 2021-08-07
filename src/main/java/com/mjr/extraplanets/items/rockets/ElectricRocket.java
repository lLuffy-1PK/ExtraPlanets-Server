//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mjr.extraplanets.items.rockets;

import com.mjr.extraplanets.ExtraPlanets;
import com.mjr.extraplanets.blocks.ExtraPlanets_Blocks;
import com.mjr.extraplanets.entities.rockets.EntityElectricRocket;
import com.mjr.extraplanets.tileEntities.blocks.TileEntityRocketChargingPad;
import com.mjr.mjrlegendslib.util.TranslateUtilities;
import micdoodle8.mods.galacticraft.api.entity.IRocketType.EnumRocketType;
import micdoodle8.mods.galacticraft.api.item.IHoldableItem;
import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ElectricRocket extends Item implements IHoldableItem {
    public ElectricRocket(String name) {
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setMaxStackSize(1);
        this.setUnlocalizedName(name);
        this.setCreativeTab(ExtraPlanets.ItemsTab);
    }

    public EnumActionResult onItemUse(EntityPlayer playerIn, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        boolean padFound = false;
        TileEntity tile = null;
        ItemStack stack = playerIn.getHeldItem(hand);
        if (world.isRemote && playerIn instanceof EntityPlayerSP) {
            ClientProxyCore.playerClientHandler.onBuild(8, (EntityPlayerSP) playerIn);
            return EnumActionResult.FAIL;
        } else {
            float centerX = -1.0F;
            float centerY = -1.0F;
            float centerZ = -1.0F;

            for (int i = -1; i < 2; ++i) {
                for (int j = -1; j < 2; ++j) {
                    BlockPos pos1 = pos.add(i, 0, j);
                    IBlockState state = world.getBlockState(pos1);
                    Block id = state.getBlock();
                    int meta = id.getMetaFromState(state);
                    if (id == ExtraPlanets_Blocks.ADVANCED_LAUCHPAD_FULL && meta == 3) {
                        padFound = true;
                        tile = world.getTileEntity(pos.add(i, 0, j));
                        centerX = (float) (pos.getX() + i) + 0.5F;
                        centerY = (float) pos.getY() + 0.4F;
                        centerZ = (float) (pos.getZ() + j) + 0.5F;
                        break;
                    }
                }

                if (padFound) {
                    break;
                }
            }

            if (padFound) {
                if (tile instanceof TileEntityRocketChargingPad) {
                    if (((TileEntityRocketChargingPad) tile).getDockedEntity() != null) {
                        return EnumActionResult.FAIL;
                    } else {
                        EntityElectricRocket spaceship = new EntityElectricRocket(world, centerX, centerY, centerZ, EnumRocketType.values()[stack.getItemDamage()]);
                        spaceship.setPosition(spaceship.posX, spaceship.posY + spaceship.getOnPadYOffset(), spaceship.posZ);
                        spaceship.owner = playerIn.getUniqueID();
                        world.spawnEntity(spaceship);
                        if (!playerIn.capabilities.isCreativeMode) {
                            stack.shrink(1);
                        }

                        if (spaceship.rocketType.getPreFueled()) {
                            spaceship.setCurrentPowerCapacity(spaceship.getPowerMaxCapacity());
                        }

                        return EnumActionResult.SUCCESS;
                    }
                } else {
                    return EnumActionResult.FAIL;
                }
            } else {
                return EnumActionResult.FAIL;
            }
        }
    }

    public void getSubItems(CreativeTabs par2CreativeTabs, NonNullList<ItemStack> par3List) {
        if (this.isInCreativeTab(par2CreativeTabs)) {
            for (int i = 0; i < EnumRocketType.values().length; ++i) {
                par3List.add(new ItemStack(this, 1, i));
            }

        }
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, @Nullable World world, List<String> par2List, ITooltipFlag flagIn) {
        EnumRocketType type;
        if (itemStack.getItemDamage() < 10) {
            type = EnumRocketType.values()[itemStack.getItemDamage()];
        } else {
            type = EnumRocketType.values()[itemStack.getItemDamage() - 10];
        }

        if (!type.getTooltip().isEmpty()) {
            par2List.add(type.getTooltip());
        }

        if (type.getPreFueled()) {
            par2List.add(EnumColor.RED + "§o" + TranslateUtilities.translate("gui.creative_only.desc"));
        }

        if (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("RocketFuel")) {
            EntityElectricRocket rocket = new EntityElectricRocket(FMLClientHandler.instance().getWorldClient(), 0.0D, 0.0D, 0.0D, EnumRocketType.values()[itemStack.getItemDamage()]);
            par2List.add(TranslateUtilities.translate("gui.message.fuel.name") + ": " + itemStack.getTagCompound().getInteger("RocketFuel") + " / " + rocket.getCurrentPowerCapacity());
        }

        par2List.add(EnumColor.DARK_AQUA + TranslateUtilities.translate("rocket_pad.electric.desc"));
        par2List.add(TranslateUtilities.translate("gui.rover.information.2"));
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        return super.getUnlocalizedName(itemStack) + ".t10Rocket";
    }

    public boolean shouldHoldLeftHandUp(EntityPlayer player) {
        return true;
    }

    public boolean shouldHoldRightHandUp(EntityPlayer player) {
        return true;
    }

    public boolean shouldCrouch(EntityPlayer player) {
        return true;
    }
}
