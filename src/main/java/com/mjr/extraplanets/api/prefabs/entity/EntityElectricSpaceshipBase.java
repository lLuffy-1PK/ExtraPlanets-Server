//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mjr.extraplanets.api.prefabs.entity;

import com.google.common.base.Predicate;
import com.mjr.mjrlegendslib.util.TranslateUtilities;
import io.netty.buffer.ByteBuf;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.entity.IIgnoreShift;
import micdoodle8.mods.galacticraft.api.entity.ITelemetry;
import micdoodle8.mods.galacticraft.api.prefab.entity.EntitySpaceshipBase;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3Dim;
import micdoodle8.mods.galacticraft.api.world.IExitHeight;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.client.screen.GameScreenText;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.network.IPacketReceiver;
import micdoodle8.mods.galacticraft.core.network.PacketDynamic;
import micdoodle8.mods.galacticraft.core.tile.TileEntityTelemetry;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.util.DamageSourceGC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public abstract class EntityElectricSpaceshipBase extends Entity implements IPacketReceiver, IIgnoreShift, ITelemetry {
    public static final Predicate<Entity> rocketSelector = new Predicate<Entity>() {
        public boolean apply(Entity e) {
            return e instanceof EntitySpaceshipBase && e.isEntityAlive();
        }
    };
    private static final UUID nullUUID = new UUID(0L, 0L);
    private final ArrayList<BlockVec3Dim> telemetryList = new ArrayList();
    public int launchPhase;
    public int timeUntilLaunch;
    public float timeSinceLaunch;
    public float rollAmplitude;
    public float shipDamage;
    public UUID owner;
    protected long ticks = 0L;
    protected double dragAir;
    protected float currentPowerCapacity;
    protected float powerMaxCapacity;
    private boolean addToTelemetry = false;
    private double syncAdjustX = 0.0D;
    private double syncAdjustY = 0.0D;
    private double syncAdjustZ = 0.0D;
    private boolean syncAdjustFlag = false;

    public EntityElectricSpaceshipBase(World par1World) {
        super(par1World);
        this.launchPhase = EntityElectricSpaceshipBase.EnumLaunchPhase.UNIGNITED.ordinal();
        this.preventEntitySpawning = true;
        this.ignoreFrustumCheck = true;
    }

    public boolean isInRangeToRenderDist(double distance) {
        double d0 = this.getEntityBoundingBox().getAverageEdgeLength();
        if (Double.isNaN(d0)) {
            d0 = 1.0D;
        }

        d0 = d0 * 64.0D * 5.0D;
        return distance < d0 * d0;
    }

    public abstract int getPreLaunchWait();

    protected boolean canTriggerWalking() {
        return false;
    }

    protected void entityInit() {
    }

    public AxisAlignedBB getCollisionBox(Entity par1Entity) {
        return null;
    }

    public AxisAlignedBB getCollisionBoundingBox() {
        return null;
    }

    public boolean canBePushed() {
        return false;
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        if (!this.world.isRemote && !this.isDead) {
            boolean flag = par1DamageSource.getTrueSource() instanceof EntityPlayer && ((EntityPlayer) par1DamageSource.getTrueSource()).capabilities.isCreativeMode;
            Entity e = par1DamageSource.getTrueSource();
            if (this.isEntityInvulnerable(par1DamageSource) || this.posY > 300.0D || e instanceof EntityLivingBase && !(e instanceof EntityPlayer)) {
                return false;
            } else {
                if (this.owner != null && e instanceof EntityPlayer && !((EntityPlayer) e).capabilities.isCreativeMode) {
                    if (this.owner.compareTo(nullUUID) != 0) {
                        if (e.getUniqueID().compareTo(this.owner) != 0) {
                            return false;
                        }
                    }
                }
                this.rollAmplitude = 10.0F;
                this.markVelocityChanged();
                this.shipDamage += par2 * 10.0F;
                if (e instanceof EntityPlayer && ((EntityPlayer) e).capabilities.isCreativeMode) {
                    this.shipDamage = 100.0F;
                }

                if (flag || this.shipDamage > 90.0F && !this.world.isRemote) {
                    this.removePassengers();
                    if (flag) {
                        this.setDead();
                    } else {
                        this.setDead();
                        this.dropShipAsItem();
                    }

                    return true;
                } else {
                    return true;
                }
            }
        } else {
            return true;
        }
    }

    public void dropShipAsItem() {
        if (!this.world.isRemote) {
            Iterator var1 = this.getItemsDropped(new ArrayList()).iterator();

            while (var1.hasNext()) {
                ItemStack item = (ItemStack) var1.next();
                EntityItem EntityItem = this.entityDropItem(item, 0.0F);
                if (item.hasTagCompound()) {
                    EntityItem.getItem().setTagCompound(item.getTagCompound().copy());
                }
            }

        }
    }

    public abstract List<ItemStack> getItemsDropped(List<ItemStack> var1);

    public void performHurtAnimation() {
        this.rollAmplitude = 5.0F;
        this.shipDamage += this.shipDamage * 10.0F;
    }

    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    public boolean shouldRiderSit() {
        return false;
    }

    public void onUpdate() {
        ++this.ticks;
        super.onUpdate();
        Iterator var1;
        if (this.addToTelemetry) {
            this.addToTelemetry = false;
            var1 = (new ArrayList(this.telemetryList)).iterator();

            while (var1.hasNext()) {
                BlockVec3Dim vec = (BlockVec3Dim) var1.next();
                TileEntity t1 = vec.getTileEntityNoLoad();
                if (t1 instanceof TileEntityTelemetry && !t1.isInvalid() && ((TileEntityTelemetry) t1).linkedEntity == this) {
                    ((TileEntityTelemetry) t1).addTrackedEntity(this);
                }
            }
        }

        Entity e;
        for (var1 = this.getPassengers().iterator(); var1.hasNext(); e.fallDistance = 0.0F) {
            e = (Entity) var1.next();
        }

        if (this.posY > (this.world.provider instanceof IExitHeight ? ((IExitHeight) this.world.provider).getYCoordinateToTeleport() : 1200.0D) && this.launchPhase != EntityElectricSpaceshipBase.EnumLaunchPhase.LANDING.ordinal()) {
            this.onReachAtmosphere();
        }

        if (this.rollAmplitude > 0.0F) {
            --this.rollAmplitude;
        }

        if (this.shipDamage > 0.0F) {
            --this.shipDamage;
        }

        if (!this.world.isRemote) {
            if (this.posY < 0.0D) {
                this.setDead();
            } else if (this.posY > (this.world.provider instanceof IExitHeight ? ((IExitHeight) this.world.provider).getYCoordinateToTeleport() : 1200.0D) + (double) (this.launchPhase == EntityElectricSpaceshipBase.EnumLaunchPhase.LANDING.ordinal() ? 355 : 100)) {
                var1 = this.getPassengers().iterator();

                while (var1.hasNext()) {
                    e = (Entity) var1.next();
                    if (e instanceof EntityPlayerMP) {
                        GCPlayerStats stats = GCPlayerStats.get(e);
                        if (stats.isUsingPlanetSelectionGui()) {
                            this.setDead();
                        }
                    } else {
                        this.setDead();
                    }
                }
            }

            if (this.timeSinceLaunch > 50.0F && this.onGround) {
                this.failRocket();
            }
        }

        if (this.launchPhase == EntityElectricSpaceshipBase.EnumLaunchPhase.UNIGNITED.ordinal()) {
            this.timeUntilLaunch = this.getPreLaunchWait();
        }

        if (this.launchPhase >= EntityElectricSpaceshipBase.EnumLaunchPhase.LAUNCHED.ordinal()) {
            ++this.timeSinceLaunch;
        } else {
            this.timeSinceLaunch = 0.0F;
        }

        if (this.timeUntilLaunch > 0 && this.launchPhase == EntityElectricSpaceshipBase.EnumLaunchPhase.IGNITED.ordinal()) {
            --this.timeUntilLaunch;
        }

        AxisAlignedBB box = this.getEntityBoundingBox().expand(0.2D, 0.2D, 0.2D);
        List<?> var15 = this.world.getEntitiesWithinAABBExcludingEntity(this, box);
        if (var15 != null && !var15.isEmpty()) {
            for (int var52 = 0; var52 < var15.size(); ++var52) {
                Entity var17 = (Entity) var15.get(var52);
                if (this.getPassengers().contains(var17)) {
                    var17.applyEntityCollision(this);
                }
            }
        }

        if (this.timeUntilLaunch == 0 && this.launchPhase == EntityElectricSpaceshipBase.EnumLaunchPhase.IGNITED.ordinal()) {
            this.setLaunchPhase(EntityElectricSpaceshipBase.EnumLaunchPhase.LAUNCHED);
            this.onLaunch();
        }

        if (this.rotationPitch > 90.0F) {
            this.rotationPitch = 90.0F;
        }

        if (this.rotationPitch < -90.0F) {
            this.rotationPitch = -90.0F;
        }

        this.motionX = -(50.0D * Math.cos((double) this.rotationYaw / 57.29577951308232D) * Math.sin((double) this.rotationPitch * 0.01D / 57.29577951308232D));
        this.motionZ = -(50.0D * Math.sin((double) this.rotationYaw / 57.29577951308232D) * Math.sin((double) this.rotationPitch * 0.01D / 57.29577951308232D));
        if (this.launchPhase < EntityElectricSpaceshipBase.EnumLaunchPhase.LAUNCHED.ordinal()) {
            this.motionX = this.motionY = this.motionZ = 0.0D;
        }

        if (this.world.isRemote) {
            this.setPosition(this.posX, this.posY, this.posZ);
            if (this.shouldMoveClientSide()) {
                this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            }
        } else {
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        }

        this.setRotation(this.rotationYaw, this.rotationPitch);
        if (this.world.isRemote) {
            this.setPosition(this.posX, this.posY, this.posZ);
        }

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (!this.world.isRemote && this.ticks % 3L == 0L) {
            GalacticraftCore.packetPipeline.sendToDimension(new PacketDynamic(this), this.world.provider.getDimension());
        }

    }

    protected boolean shouldMoveClientSide() {
        return true;
    }

    public abstract boolean hasValidPower();

    public void decodePacketdata(ByteBuf buffer) {
        if (!this.world.isRemote) {
            (new Exception()).printStackTrace();
        }

        int newLaunchPhase = buffer.readInt();
        if (this.launchPhase != newLaunchPhase) {
            this.setLaunchPhase(EntityElectricSpaceshipBase.EnumLaunchPhase.values()[newLaunchPhase]);
            if (newLaunchPhase == EntityElectricSpaceshipBase.EnumLaunchPhase.LANDING.ordinal()) {
                this.syncAdjustFlag = true;
            }
        } else if (this.hasValidPower()) {
            if (Math.abs(this.syncAdjustX - this.posX) > 0.2D) {
                this.motionX += (this.syncAdjustX - this.posX) / 40.0D;
            }

            if (Math.abs(this.syncAdjustY - this.posY) > 0.2D) {
                this.motionY += (this.syncAdjustY - this.posY) / 40.0D;
            }

            if (Math.abs(this.syncAdjustZ - this.posZ) > 0.2D) {
                this.motionZ += (this.syncAdjustZ - this.posZ) / 40.0D;
            }
        }

        this.timeSinceLaunch = buffer.readFloat();
        this.timeUntilLaunch = buffer.readInt();
    }

    public void getNetworkedData(ArrayList<Object> list) {
        if (!this.world.isRemote) {
            list.add(this.launchPhase);
            list.add(this.timeSinceLaunch);
            list.add(this.timeUntilLaunch);
        }
    }

    public void turnYaw(float f) {
        this.rotationYaw += f;
    }

    public void turnPitch(float f) {
        this.rotationPitch += f;
    }

    protected void failRocket() {
        Iterator var1 = this.getPassengers().iterator();

        while (var1.hasNext()) {
            Entity passenger = (Entity) var1.next();
            passenger.attackEntityFrom(DamageSourceGC.spaceshipCrash, 81.0F);
        }

        if (!ConfigManagerCore.disableSpaceshipGrief) {
            this.world.createExplosion(this, this.posX, this.posY, this.posZ, 5.0F, true);
        }

        this.setDead();
    }

    @SideOnly(Side.CLIENT)
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean b) {
        this.setRotation(yaw, pitch);
        if (this.syncAdjustFlag && this.world.isBlockLoaded(new BlockPos(x, 255.0D, z)) && this.hasValidPower()) {
            EntityPlayer p = FMLClientHandler.instance().getClientPlayerEntity();
            double dx = x - p.posX;
            double dz = z - p.posZ;
            if (dx * dx + dz * dz < 1024.0D) {
                if (!this.world.loadedEntityList.contains(this)) {
                    try {
                        this.world.loadedEntityList.add(this);
                    } catch (Exception var18) {
                        var18.printStackTrace();
                    }
                }

                this.posX = x;
                this.posY = y;
                this.posZ = z;
                int cx = MathHelper.floor(x / 16.0D);
                int cz = MathHelper.floor(z / 16.0D);
                if (!this.addedToChunk || this.chunkCoordX != cx || this.chunkCoordZ != cz) {
                    if (this.addedToChunk && this.world.isBlockLoaded(new BlockPos(this.chunkCoordX << 4, 255, this.chunkCoordZ << 4), true)) {
                        this.world.getChunkFromChunkCoords(this.chunkCoordX, this.chunkCoordZ).removeEntityAtIndex(this, this.chunkCoordY);
                    }

                    this.addedToChunk = true;
                    this.world.getChunkFromChunkCoords(cx, cz).addEntity(this);
                }

                this.syncAdjustX = 0.0D;
                this.syncAdjustY = 0.0D;
                this.syncAdjustZ = 0.0D;
                this.syncAdjustFlag = false;
            }
        } else {
            this.syncAdjustX = x;
            this.syncAdjustY = y;
            this.syncAdjustZ = z;
        }

    }

    protected void writeEntityToNBT(NBTTagCompound nbt) {
        nbt.setInteger("launchPhase", this.launchPhase + 1);
        nbt.setInteger("timeUntilLaunch", this.timeUntilLaunch);
        nbt.setUniqueId("Owner", this.owner);
        if (this.telemetryList.size() > 0) {
            NBTTagList teleNBTList = new NBTTagList();
            Iterator var3 = (new ArrayList(this.telemetryList)).iterator();

            while (var3.hasNext()) {
                BlockVec3Dim vec = (BlockVec3Dim) var3.next();
                NBTTagCompound tag = new NBTTagCompound();
                vec.writeToNBT(tag);
                teleNBTList.appendTag(tag);
            }

            nbt.setTag("telemetryList", teleNBTList);
        }

    }

    protected void readEntityFromNBT(NBTTagCompound nbt) {
        this.timeUntilLaunch = nbt.getInteger("timeUntilLaunch");
        this.owner = nbt.getUniqueId("Owner");
        boolean hasOldTags = false;
        if (nbt.getKeySet().contains("launched")) {
            hasOldTags = true;
            boolean launched = nbt.getBoolean("launched");
            if (launched) {
                this.setLaunchPhase(EntityElectricSpaceshipBase.EnumLaunchPhase.LAUNCHED);
            }
        }

        if (nbt.getKeySet().contains("ignite")) {
            hasOldTags = true;
            int ignite = nbt.getInteger("ignite");
            if (ignite == 1) {
                this.setLaunchPhase(EntityElectricSpaceshipBase.EnumLaunchPhase.IGNITED);
            }
        }

        if (hasOldTags) {
            if (this.launchPhase < EntityElectricSpaceshipBase.EnumLaunchPhase.LAUNCHED.ordinal() && this.launchPhase != EntityElectricSpaceshipBase.EnumLaunchPhase.IGNITED.ordinal()) {
                this.setLaunchPhase(EntityElectricSpaceshipBase.EnumLaunchPhase.UNIGNITED);
            }
        } else {
            this.setLaunchPhase(EntityElectricSpaceshipBase.EnumLaunchPhase.values()[nbt.getInteger("launchPhase") - 1]);
        }

        this.telemetryList.clear();
        if (nbt.hasKey("telemetryList")) {
            NBTTagList teleNBT = nbt.getTagList("telemetryList", 10);
            if (teleNBT.tagCount() > 0) {
                for (int j = teleNBT.tagCount() - 1; j >= 0; --j) {
                    NBTTagCompound tag1 = teleNBT.getCompoundTagAt(j);
                    if (tag1 != null) {
                        this.telemetryList.add(BlockVec3Dim.readFromNBT(tag1));
                    }
                }

                this.addToTelemetry = true;
            }
        }

    }

    public boolean getLaunched() {
        return this.launchPhase >= EntityElectricSpaceshipBase.EnumLaunchPhase.LAUNCHED.ordinal();
    }

    public boolean canBeRidden() {
        return false;
    }

    public void ignite() {
        this.setLaunchPhase(EntityElectricSpaceshipBase.EnumLaunchPhase.IGNITED);
    }

    public double getMountedYOffset() {
        return -0.9D;
    }

    public double getOnPadYOffset() {
        return 0.0D;
    }

    public void onLaunch() {
        MinecraftForge.EVENT_BUS.post(new EntityElectricSpaceshipBase.RocketLaunchEvent(this));
    }

    public void onReachAtmosphere() {
    }

    @SideOnly(Side.CLIENT)
    public void spawnParticle(String var1, double var2, double var4, double var6, double var8, double var10, double var12) {
    }

    public boolean canRiderInteract() {
        return true;
    }

    public ResourceLocation getSpaceshipGui() {
        return GalacticraftRegistry.getResouceLocationForDimension(this.world.provider.getClass());
    }

    public void setLaunchPhase(EntityElectricSpaceshipBase.EnumLaunchPhase phase) {
        this.launchPhase = phase.ordinal();
    }

    public boolean shouldIgnoreShiftExit() {
        return this.launchPhase >= EntityElectricSpaceshipBase.EnumLaunchPhase.LAUNCHED.ordinal();
    }

    public void addTelemetry(TileEntityTelemetry tile) {
        this.telemetryList.add(new BlockVec3Dim(tile));
    }

    public ArrayList<TileEntityTelemetry> getTelemetry() {
        ArrayList<TileEntityTelemetry> returnList = new ArrayList();
        Iterator var2 = (new ArrayList(this.telemetryList)).iterator();

        while (var2.hasNext()) {
            BlockVec3Dim vec = (BlockVec3Dim) var2.next();
            TileEntity t1 = vec.getTileEntity();
            if (t1 instanceof TileEntityTelemetry && !t1.isInvalid() && ((TileEntityTelemetry) t1).linkedEntity == this) {
                returnList.add((TileEntityTelemetry) t1);
            }
        }

        return returnList;
    }

    public void transmitData(int[] data) {
        data[0] = this.timeUntilLaunch;
        data[1] = (int) this.posY;
        data[3] = (int) this.currentPowerCapacity;
        data[4] = (int) this.rotationPitch;
    }

    public void receiveData(int[] data, String[] str) {
        int countdown = data[0];
        str[0] = "";
        str[1] = countdown == 400 ? TranslateUtilities.translate("gui.rocket.on_launchpad") : (countdown > 0 ? TranslateUtilities.translate("gui.rocket.countdown") + ": " + countdown / 20 : TranslateUtilities.translate("gui.rocket.launched"));
        str[2] = TranslateUtilities.translate("gui.rocket.height") + ": " + data[1];
        str[3] = GameScreenText.makeSpeedString(data[2]);
        str[4] = TranslateUtilities.translate("gui.message.fuel.name") + ": " + data[3] + "%";
    }

    public void adjustDisplay(int[] data) {
        GL11.glRotatef((float) data[4], -1.0F, 0.0F, 0.0F);
        GL11.glTranslatef(0.0F, this.height / 4.0F, 0.0F);
    }

    public float getRenderOffsetY() {
        return 1.34F;
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender() {
        double height = this.posY + (double) this.getEyeHeight();
        if (height > 255.0D) {
            height = 255.0D;
        }

        BlockPos blockpos = new BlockPos(this.posX, height, this.posZ);
        return this.world.isBlockLoaded(blockpos) ? this.world.getCombinedLight(blockpos, 0) : 0;
    }

    public enum EnumLaunchPhase {
        UNIGNITED,
        IGNITED,
        LAUNCHED,
        LANDING;

        EnumLaunchPhase() {
        }
    }

    public static class RocketLaunchEvent extends EntityEvent {
        public final EntityElectricSpaceshipBase rocket;

        public RocketLaunchEvent(EntityElectricSpaceshipBase entity) {
            super(entity);
            this.rocket = entity;
        }
    }
}
