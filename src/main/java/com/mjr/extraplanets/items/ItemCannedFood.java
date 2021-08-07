//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mjr.extraplanets.items;

import com.mjr.extraplanets.ExtraPlanets;
import com.mjr.mjrlegendslib.item.ItemBasicMeta;
import com.mjr.mjrlegendslib.util.TranslateUtilities;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemCannedFood extends ItemBasicMeta {
    public ItemCannedFood(String name) {
        super(name, ExtraPlanets.ItemsTab, getItemList());
    }

    public static String[] getItemList() {
        return new String[]{"dehydrated_porkchop", "dehydrated_fish", "dehydrated_salmon", "dehydrated_chicken"};
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        return itemStack.getItemDamage() < 5 ? this.getUnlocalizedName() + ".canned_food" : this.getUnlocalizedName() + "." + getItemList()[itemStack.getItemDamage()];
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, @Nullable World world, List<String> par3List, ITooltipFlag flagIn) {
        if (itemStack.getItemDamage() < 19) {
            par3List.add(EnumColor.BRIGHT_GREEN + TranslateUtilities.translate(this.getUnlocalizedName() + "." + getItemList()[itemStack.getItemDamage()] + ".name"));
        }

    }

    public int getHealAmount(ItemStack itemStack) {
        switch (itemStack.getItemDamage()) {
            case 0:
                return 16;
            case 1:
                return 4;
            case 2:
                return 4;
            case 3:
                return 12;
            default:
                return 0;
        }
    }

    public float getSaturationModifier(ItemStack itemStack) {
        switch (itemStack.getItemDamage()) {
            case 0:
                return 0.8F;
            case 1:
                return 0.1F;
            case 2:
                return 0.1F;
            case 3:
                return 0.6F;
            default:
                return 0.0F;
        }
    }

    /**
     * onItemUseFinish => func_77654_b(не обфусцируется)
     */
    public ItemStack func_77654_b(ItemStack itemStack, World par2World, EntityLivingBase par3EntityPlayer) {
        if (par3EntityPlayer instanceof EntityPlayer && itemStack.getItemDamage() < 5) {
            itemStack.shrink(1);

            EntityPlayer entityplayer = (EntityPlayer) par3EntityPlayer;
            entityplayer.getFoodStats().addStats(this.getHealAmount(itemStack), this.getSaturationModifier(itemStack));
            par2World.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, par2World.rand.nextFloat() * 0.1F + 0.9F);
            //if (!par2World.isRemote) {
            //    par3EntityPlayer.entityDropItem(new ItemStack(GCItems.canister, 1, 0), 0.0F);
            //}

            return itemStack;
        } else {
            return super.onItemUseFinish(itemStack, par2World, par3EntityPlayer);
        }
    }

    public int getMaxItemUseDuration(ItemStack itemStack) {
        return itemStack.getItemDamage() < 5 ? 32 : super.getMaxItemUseDuration(itemStack);
    }

    public EnumAction getItemUseAction(ItemStack itemStack) {
        return itemStack.getItemDamage() < 5 ? EnumAction.EAT : super.getItemUseAction(itemStack);
    }

    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer playerIn, EnumHand hand) {
        ItemStack itemStackIn = playerIn.getHeldItem(hand);
        if (playerIn.canEat(false)) {
            playerIn.setActiveHand(hand);
            return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
        } else {
            return new ActionResult(EnumActionResult.FAIL, itemStackIn);
        }
    }
}
