package com.mjr.extraplanets.tileEntities.machines;

import com.mjr.extraplanets.blocks.ExtraPlanets_Blocks;
import com.mjr.extraplanets.blocks.fluid.ExtraPlanets_Fluids;
import com.mjr.extraplanets.blocks.machines.BasicDensifier;
import com.mjr.extraplanets.items.ExtraPlanets_Items;
import micdoodle8.mods.galacticraft.api.transmission.NetworkType;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseElectricBlockWithInventory;
import micdoodle8.mods.galacticraft.core.util.FluidUtil;
import micdoodle8.mods.galacticraft.core.wrappers.FluidHandlerWrapper;
import micdoodle8.mods.galacticraft.core.wrappers.IFluidHandlerWrapper;
import micdoodle8.mods.miccore.Annotations.NetworkedField;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;

public class TileEntityBasicDensifier extends TileBaseElectricBlockWithInventory implements ISidedInventory, IFluidHandlerWrapper {
    public static final int PROCESS_TIME_REQUIRED = 5;
    private final int tankCapacity = 20000;
    @NetworkedField(targetSide = Side.CLIENT)
    public FluidTank inputTank;
    @NetworkedField(targetSide = Side.CLIENT)
    public int processTicks = 0;
    @NetworkedField(targetSide = Side.CLIENT)
    public int outputTextureOffsetX;
    @NetworkedField(targetSide = Side.CLIENT)
    public int outputTextureOffsetY;
    private int amountDrain = 0;
    private ItemStack producingStack = null;

    public TileEntityBasicDensifier() {
        super("container.basic.densifier.name");
        getClass();
        this.inputTank = new FluidTank(20000);
        this.inventory = NonNullList.withSize(3, ItemStack.EMPTY);
    }

    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing));
    }

    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return (T) new FluidHandlerWrapper(this, facing);
        return super.getCapability(capability, facing);
    }

    public void update() {
        super.update();
        if (!this.world.isRemote) {
            checkFluidTankTransfer(2, this.inputTank);
            if (canProcess() && this.hasEnoughEnergyToRun) {
                if (this.processTicks == 0) {
                    this.processTicks = 5;
                } else if (--this.processTicks <= 0) {
                    updateOutput();
                    smeltItem();
                    this.processTicks = canProcess() ? 5 : 0;
                }
            } else {
                this.processTicks = 0;
            }
        }
    }

    private void checkFluidTankTransfer(int slot, FluidTank tank) {
        ItemStack item = getStackInSlot(slot);
        if (item != null) {
            FluidStack stack = FluidUtil.getFluidContained(item);
            if (item.getItem() instanceof net.minecraftforge.fluids.UniversalBucket && !FluidUtil.isEmptyContainer(item) && stack != null && stack.getFluid() != null)
                if (stack.getFluid().equals(ExtraPlanets_Fluids.LIQUID_CARAMEL_FLUID)) {
                    if (this.inputTank.getFluid() == null) {
                        this.inputTank.setFluid(new FluidStack(ExtraPlanets_Fluids.LIQUID_CARAMEL_FLUID, 0));
                        tank.fill(FluidRegistry.getFluidStack("liquid_caramel_fluid", 1000), true);
                        setInventorySlotContents(slot, new ItemStack(Items.BUCKET));
                    } else if (stack.getFluid() == this.inputTank.getFluid().getFluid()) {
                        tank.fill(FluidRegistry.getFluidStack("liquid_caramel_fluid", 1000), true);
                        setInventorySlotContents(slot, new ItemStack(Items.BUCKET));
                    }
                } else if (stack.getFluid().equals(ExtraPlanets_Fluids.LIQUID_CHOCOLATE_FLUID)) {
                    if (this.inputTank.getFluid() == null) {
                        this.inputTank.setFluid(new FluidStack(ExtraPlanets_Fluids.LIQUID_CHOCOLATE_FLUID, 0));
                        tank.fill(FluidRegistry.getFluidStack("liquid_chocolate_fluid", 1000), true);
                        setInventorySlotContents(slot, new ItemStack(Items.BUCKET));
                    } else if (stack.getFluid() == this.inputTank.getFluid().getFluid()) {
                        tank.fill(FluidRegistry.getFluidStack("liquid_chocolate_fluid", 1000), true);
                        setInventorySlotContents(slot, new ItemStack(Items.BUCKET));
                    }
                } else if (stack.getFluid().equals(ExtraPlanets_Fluids.NITROGEN_ICE_FLUID)) {
                    if (this.inputTank.getFluid() == null) {
                        this.inputTank.setFluid(new FluidStack(ExtraPlanets_Fluids.NITROGEN_ICE_FLUID, 0));
                        tank.fill(FluidRegistry.getFluidStack("nitrogen_ice_fluid", 1000), true);
                        setInventorySlotContents(slot, new ItemStack(Items.BUCKET));
                    } else if (stack.getFluid() == this.inputTank.getFluid().getFluid()) {
                        tank.fill(FluidRegistry.getFluidStack("nitrogen_ice_fluid", 1000), true);
                        setInventorySlotContents(slot, new ItemStack(Items.BUCKET));
                    }
                } else if (stack.getFluid().equals(ExtraPlanets_Fluids.GLOWSTONE_FLUID)) {
                    if (this.inputTank.getFluid() == null) {
                        this.inputTank.setFluid(new FluidStack(ExtraPlanets_Fluids.GLOWSTONE_FLUID, 0));
                        tank.fill(FluidRegistry.getFluidStack("glowstone_fluid", 1000), true);
                        setInventorySlotContents(slot, new ItemStack(Items.BUCKET));
                    } else if (stack.getFluid() == this.inputTank.getFluid().getFluid()) {
                        tank.fill(FluidRegistry.getFluidStack("glowstone_fluid", 1000), true);
                        setInventorySlotContents(slot, new ItemStack(Items.BUCKET));
                    }
                } else if (stack.getFluid().equals(ExtraPlanets_Fluids.NITROGEN_FLUID)) {
                    if (this.inputTank.getFluid() == null) {
                        this.inputTank.setFluid(new FluidStack(ExtraPlanets_Fluids.NITROGEN_FLUID, 0));
                        tank.fill(FluidRegistry.getFluidStack("nitrogen_fluid", 1000), true);
                        setInventorySlotContents(slot, new ItemStack(Items.BUCKET));
                    } else if (stack.getFluid() == this.inputTank.getFluid().getFluid()) {
                        tank.fill(FluidRegistry.getFluidStack("nitrogen_fluid", 1000), true);
                        setInventorySlotContents(slot, new ItemStack(Items.BUCKET));
                    }
                } else if (stack.getFluid().equals(ExtraPlanets_Fluids.FROZEN_WATER_FLUID)) {
                    if (this.inputTank.getFluid() == null) {
                        this.inputTank.setFluid(new FluidStack(ExtraPlanets_Fluids.FROZEN_WATER_FLUID, 0));
                        tank.fill(FluidRegistry.getFluidStack("frozen_water_fluid", 1000), true);
                        setInventorySlotContents(slot, new ItemStack(Items.BUCKET));
                    } else if (stack.getFluid() == this.inputTank.getFluid().getFluid()) {
                        tank.fill(FluidRegistry.getFluidStack("frozen_water_fluid", 1000), true);
                        setInventorySlotContents(slot, new ItemStack(Items.BUCKET));
                    }
                } else if (stack.getFluid().equals(ExtraPlanets_Fluids.LIQUID_HYDROCARBON_FLUID)) {
                    if (this.inputTank.getFluid() == null) {
                        this.inputTank.setFluid(new FluidStack(ExtraPlanets_Fluids.LIQUID_HYDROCARBON_FLUID, 0));
                        tank.fill(FluidRegistry.getFluidStack("liquid_hydrocarbon_fluid", 1000), true);
                        setInventorySlotContents(slot, new ItemStack(Items.BUCKET));
                    } else if (stack.getFluid() == this.inputTank.getFluid().getFluid()) {
                        tank.fill(FluidRegistry.getFluidStack("liquid_hydrocarbon_fluid", 1000), true);
                        setInventorySlotContents(slot, new ItemStack(Items.BUCKET));
                    }
                }
        }
    }

    public void updateOutput() {
        if (this.inputTank.getFluid().equals(new FluidStack(ExtraPlanets_Fluids.LIQUID_CARAMEL_FLUID, 0))) {
            this.producingStack = new ItemStack(ExtraPlanets_Items.CARAMEL_BAR, 6);
        } else if (this.inputTank.getFluid().equals(new FluidStack(ExtraPlanets_Fluids.LIQUID_CHOCOLATE_FLUID, 0))) {
            this.producingStack = new ItemStack(ExtraPlanets_Items.CHOCOLATE_BAR, 6);
        } else if (this.inputTank.getFluid().equals(new FluidStack(ExtraPlanets_Fluids.NITROGEN_ICE_FLUID, 0))) {
            this.producingStack = new ItemStack(Blocks.ICE, 6);
        } else if (this.inputTank.getFluid().equals(new FluidStack(ExtraPlanets_Fluids.GLOWSTONE_FLUID, 0))) {
            this.producingStack = new ItemStack(Blocks.GLOWSTONE, 1);
        } else if (this.inputTank.getFluid().equals(new FluidStack(ExtraPlanets_Fluids.NITROGEN_FLUID, 0))) {
            this.producingStack = new ItemStack(ExtraPlanets_Blocks.FROZEN_NITROGEN, 2);
        } else if (this.inputTank.getFluid().equals(new FluidStack(ExtraPlanets_Fluids.FROZEN_WATER_FLUID, 0))) {
            this.producingStack = new ItemStack(Blocks.ICE, 3);
        } else if (this.inputTank.getFluid().equals(new FluidStack(ExtraPlanets_Fluids.LIQUID_HYDROCARBON_FLUID, 0))) {
            this.producingStack = new ItemStack(Items.COAL, 3);
        }
    }

    public void updateTextureOffset() {
        if (this.inputTank.getFluid() == null)
            return;
        if (this.inputTank.getFluid().equals(new FluidStack(ExtraPlanets_Fluids.LIQUID_CARAMEL_FLUID, 0))) {
            this.outputTextureOffsetX = 48;
            this.outputTextureOffsetY = 0;
        } else if (this.inputTank.getFluid().equals(new FluidStack(ExtraPlanets_Fluids.LIQUID_CHOCOLATE_FLUID, 0))) {
            this.outputTextureOffsetX = 16;
            this.outputTextureOffsetY = 0;
        } else if (this.inputTank.getFluid().equals(new FluidStack(ExtraPlanets_Fluids.NITROGEN_ICE_FLUID, 0))) {
            this.outputTextureOffsetX = 0;
            this.outputTextureOffsetY = 0;
        } else if (this.inputTank.getFluid().equals(new FluidStack(ExtraPlanets_Fluids.GLOWSTONE_FLUID, 0))) {
            this.outputTextureOffsetX = 64;
        } else if (this.inputTank.getFluid().equals(new FluidStack(ExtraPlanets_Fluids.NITROGEN_FLUID, 0))) {
            this.outputTextureOffsetX = 0;
            this.outputTextureOffsetY = 45;
        } else if (this.inputTank.getFluid().equals(new FluidStack(ExtraPlanets_Fluids.FROZEN_WATER_FLUID, 0))) {
            this.outputTextureOffsetX = 16;
            this.outputTextureOffsetY = 45;
        } else if (this.inputTank.getFluid().equals(new FluidStack(ExtraPlanets_Fluids.LIQUID_HYDROCARBON_FLUID, 0))) {
            this.outputTextureOffsetX = 32;
            this.outputTextureOffsetY = 45;
        }
    }

    public int getScaledFuelLevel(int i) {
        return this.inputTank.getFluidAmount() * i / this.inputTank.getCapacity();
    }

    public boolean canProcess() {
        if (this.inputTank.getFluidAmount() <= 0 && this.amountDrain <= 0)
            return false;
        return !getDisabled(0);
    }

    public boolean canCrystallize() {
        if (this.producingStack.isEmpty())
            return false;
        if (getInventory().get(1).isEmpty())
            return true;
        if (!getInventory().get(1).isEmpty() && !getInventory().get(1).isItemEqual(this.producingStack))
            return false;
        int result = getInventory().get(1).isEmpty() ? 0 : (getInventory().get(1).getCount() + this.producingStack.getCount());
        return (result <= getInventoryStackLimit() && result <= this.producingStack.getMaxStackSize());
    }

    public boolean hasInputs() {
        return this.inputTank.getFluidAmount() >= 50;
    }

    public void smeltItem() {
        ItemStack resultItemStack = this.producingStack;
        if (canProcess() && canCrystallize() && hasInputs()) {
            int amountToDrain = 50;
            this.amountDrain += 50;
            this.inputTank.drain(50, true);
            if (this.amountDrain >= 1000) {
                this.amountDrain = 0;
                if (getInventory().get(1).isEmpty()) {
                    getInventory().set(1, resultItemStack.copy());
                } else if (getInventory().get(1).isItemEqual(resultItemStack)) {
                    if (getInventory().get(1).getCount() + resultItemStack.getCount() > 64) {
                        for (int i = 0; i < getInventory().get(1).getCount() + resultItemStack.getCount() - 64; i++) {
                            float var = 0.7F;
                            double dx = (this.world.rand.nextFloat() * var) + (1.0F - var) * 0.5D;
                            double dy = (this.world.rand.nextFloat() * var) + (1.0F - var) * 0.5D;
                            double dz = (this.world.rand.nextFloat() * var) + (1.0F - var) * 0.5D;
                            EntityItem entityitem = new EntityItem(this.world, getPos().getX() + dx, getPos().getY() + dy, getPos().getZ() + dz, new ItemStack(resultItemStack.getItem(), 1, resultItemStack.getItemDamage()));
                            entityitem.setPickupDelay(10);
                            this.world.spawnEntity(entityitem);
                        }
                        getInventory().get(1).setCount(64);
                    } else {
                        getInventory().get(1).grow(resultItemStack.getCount());
                    }
                }
            }
        }
    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.processTicks = nbt.getInteger("smeltingTicks");
        if (nbt.hasKey("inputTank"))
            this.inputTank.readFromNBT(nbt.getCompoundTag("inputTank"));
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("smeltingTicks", this.processTicks);
        if (this.inputTank.getFluid() != null)
            nbt.setTag("inputTank", this.inputTank.writeToNBT(new NBTTagCompound()));
        return nbt;
    }

    public int[] getSlotsForFace(EnumFacing side) {
        if (side == EnumFacing.DOWN)
            return new int[]{1};
        return new int[]{0, 1, 2};
    }

    public boolean canInsertItem(int slotID, ItemStack itemStack, EnumFacing side) {
        if (itemStack != null && isItemValidForSlot(slotID, itemStack)) {
            switch (slotID) {
                case 0:
                    return ItemElectricBase.isElectricItemCharged(itemStack);
                case 1:
                    return false;
                case 2:
                    return FluidUtil.isFilledContainer(itemStack);
            }
            return false;
        }
        return false;
    }

    public boolean canExtractItem(int slotID, ItemStack itemStack, EnumFacing side) {
        if (itemStack != null) {
            switch (slotID) {
                case 0:
                    //return (ItemElectricBase.isElectricItemEmpty(itemStack) || !shouldPullEnergy());
                    return (ItemElectricBase.isElectricItemEmpty(itemStack) || !shouldPullEnergy());
                case 1:
                    return true;
                case 2:
                    return (itemStack == new ItemStack(Items.BUCKET));
            }
            return false;
        }
        return false;
    }

    public boolean isItemValidForSlot(int slotID, ItemStack itemStack) {
        switch (slotID) {
            case 0:
                return (itemStack != null && ItemElectricBase.isElectricItem(itemStack.getItem()));
            case 1:
                return true;
            case 2:
                return FluidUtil.isValidContainer(itemStack);
        }
        return false;
    }

    public boolean shouldUseEnergy() {
        return canProcess();
    }

    public EnumFacing getElectricInputDirection() {
        return getFront().rotateYCCW();
    }

    public EnumFacing getFront() {
        IBlockState state = this.world.getBlockState(getPos());
        if (state.getBlock() instanceof BasicDensifier)
            return (EnumFacing) state.getValue((IProperty) BasicDensifier.FACING);
        return EnumFacing.NORTH;
    }

    public boolean canConnect(EnumFacing direction, NetworkType type) {
        if (type == NetworkType.POWER)
            return (direction == getElectricInputDirection());
        if (type == NetworkType.FLUID)
            return (direction == getInputPipe());
        return false;
    }

    private EnumFacing getInputPipe() {
        return getFront().rotateY();
    }

    public boolean canDrain(EnumFacing from, Fluid fluid) {
        return false;
    }

    public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain) {
        return null;
    }

    public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain) {
        return null;
    }

    public boolean canFill(EnumFacing from, Fluid fluid) {
        if (from == getInputPipe())
            return (this.inputTank.getFluid() == null || this.inputTank.getFluidAmount() < this.inputTank.getCapacity());
        return false;
    }

    public int fill(EnumFacing from, FluidStack resource, boolean doFill) {
        int used = 0;
        if (from == getInputPipe() && resource != null) {
            String liquidName = FluidRegistry.getFluidName(resource);
            if (liquidName != null && (
                    resource.equals(new FluidStack(ExtraPlanets_Fluids.LIQUID_CARAMEL_FLUID, 0)) || resource.equals(new FluidStack(ExtraPlanets_Fluids.LIQUID_CHOCOLATE_FLUID, 0)) || resource
                            .equals(new FluidStack(ExtraPlanets_Fluids.NITROGEN_ICE_FLUID, 0)) || resource.equals(new FluidStack(ExtraPlanets_Fluids.GLOWSTONE_FLUID, 0)) || resource.equals(new FluidStack(ExtraPlanets_Fluids.NITROGEN_FLUID, 0)) || resource
                            .equals(new FluidStack(ExtraPlanets_Fluids.FROZEN_WATER_FLUID, 0)) || resource.equals(new FluidStack(ExtraPlanets_Fluids.LIQUID_HYDROCARBON_FLUID, 0)))) {
                if (this.inputTank.getFluid() == null)
                    if (resource.getFluid().equals(ExtraPlanets_Fluids.LIQUID_CARAMEL_FLUID)) {
                        this.inputTank.setFluid(new FluidStack(ExtraPlanets_Fluids.LIQUID_CARAMEL_FLUID, 0));
                    } else if (resource.getFluid().equals(ExtraPlanets_Fluids.LIQUID_CHOCOLATE_FLUID)) {
                        this.inputTank.setFluid(new FluidStack(ExtraPlanets_Fluids.LIQUID_CHOCOLATE_FLUID, 0));
                    } else if (resource.getFluid().equals(ExtraPlanets_Fluids.NITROGEN_ICE_FLUID)) {
                        this.inputTank.setFluid(new FluidStack(ExtraPlanets_Fluids.NITROGEN_ICE_FLUID, 0));
                    } else if (resource.getFluid().equals(ExtraPlanets_Fluids.GLOWSTONE_FLUID)) {
                        this.inputTank.setFluid(new FluidStack(ExtraPlanets_Fluids.GLOWSTONE_FLUID, 0));
                    } else if (resource.getFluid().equals(ExtraPlanets_Fluids.NITROGEN_FLUID)) {
                        this.inputTank.setFluid(new FluidStack(ExtraPlanets_Fluids.NITROGEN_FLUID, 0));
                    } else if (resource.getFluid().equals(ExtraPlanets_Fluids.FROZEN_WATER_FLUID)) {
                        this.inputTank.setFluid(new FluidStack(ExtraPlanets_Fluids.FROZEN_WATER_FLUID, 0));
                    } else if (resource.getFluid().equals(ExtraPlanets_Fluids.LIQUID_HYDROCARBON_FLUID)) {
                        this.inputTank.setFluid(new FluidStack(ExtraPlanets_Fluids.LIQUID_HYDROCARBON_FLUID, 0));
                    }
                if (this.inputTank.getFluid().equals(resource) &&
                        this.inputTank.getFluidAmount() == 0)
                    this.inputTank.setFluid(resource);
                used = this.inputTank.fill(resource, doFill);
            }
        }
        return used;
    }

    public FluidTankInfo[] getTankInfo(EnumFacing from) {
        return new FluidTankInfo[]{new FluidTankInfo(this.inputTank)};
    }
}
