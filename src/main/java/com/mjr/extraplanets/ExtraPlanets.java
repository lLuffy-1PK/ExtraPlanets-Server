package com.mjr.extraplanets;

import com.mjr.extraplanets.blocks.ExtraPlanets_Blocks;
import com.mjr.extraplanets.blocks.fluid.ExtraPlanets_Fluids;
import com.mjr.extraplanets.blocks.machines.ExtraPlanets_Machines;
import com.mjr.extraplanets.client.gui.GuiHandler;
import com.mjr.extraplanets.client.handlers.capabilities.CapabilityStatsClientHandler;
import com.mjr.extraplanets.compatibility.ExtremeReactorsCompatibility;
import com.mjr.extraplanets.compatibility.MCMultiPartCompatibility;
import com.mjr.extraplanets.compatibility.MachineMusePowersuitsCompatibility;
import com.mjr.extraplanets.compatibility.MorePlanetsCompatibility;
import com.mjr.extraplanets.entities.EntityFireBombPrimed;
import com.mjr.extraplanets.entities.EntityNuclearBombPrimed;
import com.mjr.extraplanets.entities.bosses.*;
import com.mjr.extraplanets.entities.bosses.defaultBosses.*;
import com.mjr.extraplanets.entities.landers.*;
import com.mjr.extraplanets.entities.rockets.*;
import com.mjr.extraplanets.entities.vehicles.EntityMarsRover;
import com.mjr.extraplanets.entities.vehicles.EntityVenusRover;
import com.mjr.extraplanets.handlers.BoneMealHandler;
import com.mjr.extraplanets.handlers.MainHandlerServer;
import com.mjr.extraplanets.handlers.capabilities.CapabilityStatsHandler;
import com.mjr.extraplanets.items.ExtraPlanets_Items;
import com.mjr.extraplanets.items.armor.ExtraPlanets_Armor;
import com.mjr.extraplanets.items.armor.modules.ExtraPlanets_Modules;
import com.mjr.extraplanets.items.schematics.*;
import com.mjr.extraplanets.items.tools.ExtraPlanets_Tools;
import com.mjr.extraplanets.moons.Callisto.event.CallistoEvents;
import com.mjr.extraplanets.moons.Deimos.event.DeimosEvents;
import com.mjr.extraplanets.moons.Europa.event.EuropaEvents;
import com.mjr.extraplanets.moons.ExtraPlanets_Moons;
import com.mjr.extraplanets.moons.Ganymede.event.GanymedeEvents;
import com.mjr.extraplanets.moons.Iapetus.event.IapetusEvents;
import com.mjr.extraplanets.moons.Io.event.IoEvents;
import com.mjr.extraplanets.moons.Oberon.event.OberonEvents;
import com.mjr.extraplanets.moons.Phobos.event.PhobosEvents;
import com.mjr.extraplanets.moons.Rhea.event.RheaEvents;
import com.mjr.extraplanets.moons.Titan.event.TitanEvents;
import com.mjr.extraplanets.moons.Titania.event.TitaniaEvents;
import com.mjr.extraplanets.moons.Triton.event.TritonEvents;
import com.mjr.extraplanets.network.ExtraPlanetsChannelHandler;
import com.mjr.extraplanets.planets.Ceres.event.CeresEvents;
import com.mjr.extraplanets.planets.Eris.event.ErisEvents;
import com.mjr.extraplanets.planets.ExtraPlanets_Planets;
import com.mjr.extraplanets.planets.Jupiter.event.JupiterEvents;
import com.mjr.extraplanets.planets.Kepler22b.event.Kepler22bEvents;
import com.mjr.extraplanets.planets.KuiperBelt.KuiperBeltEvents;
import com.mjr.extraplanets.planets.Mercury.event.MercuryEvents;
import com.mjr.extraplanets.planets.Neptune.event.NeptuneEvents;
import com.mjr.extraplanets.planets.Pluto.event.PlutoEvents;
import com.mjr.extraplanets.planets.Saturn.event.SaturnEvents;
import com.mjr.extraplanets.planets.Uranus.event.UranusEvents;
import com.mjr.extraplanets.proxy.CommonProxy;
import com.mjr.extraplanets.recipes.*;
import com.mjr.extraplanets.schematicPages.*;
import com.mjr.mjrlegendslib.util.MessageUtilities;
import com.mjr.mjrlegendslib.util.NetworkUtilities;
import com.mjr.mjrlegendslib.util.RegisterUtilities;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.recipe.SchematicRegistry;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.tile.TileEntityDeconstructor;
import micdoodle8.mods.galacticraft.core.util.WorldUtil;
import micdoodle8.mods.galacticraft.planets.asteroids.AsteroidsModule;
import micdoodle8.mods.galacticraft.planets.mars.MarsModule;
import micdoodle8.mods.galacticraft.planets.venus.VenusModule;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.core.util.Loader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Mod(acceptedMinecraftVersions = "[1.12.2]", certificateFingerprint = ExtraPlanets.certificateFingerprint, dependencies = "required-after:forge@[14.23.1.2555,);required-after:mjrlegendslib@[1.12.2-1.1.9,); required-after:galacticraftcore@[4.0.2.255,); required-after:galacticraftplanets@[4.0.2.255,); after:crafttweaker@[3.0.25.,); before:planetprogression@[1.12.2-0.3.8,]; after:powersuits;", guiFactory = "com.mjr.extraplanets.client.gui.screen.ConfigGuiFactory", modid = "extraplanets", name = "ExtraPlanets", version = "1.12.2-0.6.6")
public class ExtraPlanets {
    public static final String certificateFingerprint = "@FINGERPRINT@";
    public static CreativeTabs BlocksTab = new CreativeTabs("SpaceBlocksTab") {
        public ItemStack getTabIconItem() {
            if (Config.REFINERY_ADVANCED) {
                return new ItemStack(ExtraPlanets_Machines.REFINERY_ADVANCED);
            }
            return new ItemStack(ExtraPlanets_Blocks.DENSE_ICE);
        }
    };
    public static CreativeTabs ItemsTab = new CreativeTabs("SpaceItemsTab") {
        public ItemStack getTabIconItem() {
            if (Config.MERCURY) {
                return new ItemStack(ExtraPlanets_Items.TIER_4_ROCKET);
            }
            if (Config.JUPITER) {
                return new ItemStack(ExtraPlanets_Items.TIER_5_ROCKET);
            }
            if (Config.SATURN) {
                return new ItemStack(ExtraPlanets_Items.TIER_6_ROCKET);
            }
            if (Config.URANUS) {
                return new ItemStack(ExtraPlanets_Items.TIER_7_ROCKET);
            }
            if (Config.NEPTUNE) {
                return new ItemStack(ExtraPlanets_Items.TIER_8_ROCKET);
            }
            if (Config.PLUTO) {
                return new ItemStack(ExtraPlanets_Items.TIER_9_ROCKET);
            }
            if (Config.ERIS) {
                return new ItemStack(ExtraPlanets_Items.TIER_10_ROCKET);
            }
            return new ItemStack(GCItems.rocketTier1);
        }
    };
    public static List<Block> blocksList = new ArrayList();
    public static boolean generateRecipes = false;
    @Mod.Instance("extraplanets")
    public static ExtraPlanets instance;
    public static List<Item> itemList = new ArrayList();
    public static ExtraPlanetsChannelHandler packetPipeline;
    @SidedProxy(clientSide = "com.mjr.extraplanets.proxy.ClientProxy", serverSide = "com.mjr.extraplanets.proxy.CommonProxy")
    public static CommonProxy proxy;

    static {
        ExtraPlanets_Fluids.initFluid();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.load();
        RegisterUtilities.registerEventHandler(new MainHandlerServer());
        if (Config.MERCURY) {
            RegisterUtilities.registerEventHandler(new MercuryEvents());
        }
        if (Config.CERES) {
            RegisterUtilities.registerEventHandler(new CeresEvents());
        }
        if (Config.JUPITER) {
            RegisterUtilities.registerEventHandler(new JupiterEvents());
        }
        if (Config.SATURN) {
            RegisterUtilities.registerEventHandler(new SaturnEvents());
        }
        if (Config.URANUS) {
            RegisterUtilities.registerEventHandler(new UranusEvents());
        }
        if (Config.NEPTUNE) {
            RegisterUtilities.registerEventHandler(new NeptuneEvents());
        }
        if (Config.PLUTO) {
            RegisterUtilities.registerEventHandler(new PlutoEvents());
        }
        if (Config.ERIS) {
            RegisterUtilities.registerEventHandler(new ErisEvents());
        }
        if (Config.KEPLER22B && Config.KEPLER_SOLAR_SYSTEMS) {
            RegisterUtilities.registerEventHandler(new Kepler22bEvents());
        }
        if (Config.CALLISTO) {
            RegisterUtilities.registerEventHandler(new CallistoEvents());
        }
        if (Config.DEIMOS) {
            RegisterUtilities.registerEventHandler(new DeimosEvents());
        }
        if (Config.EUROPA) {
            RegisterUtilities.registerEventHandler(new EuropaEvents());
        }
        if (Config.GANYMEDE) {
            RegisterUtilities.registerEventHandler(new GanymedeEvents());
        }
        if (Config.IO) {
            RegisterUtilities.registerEventHandler(new IoEvents());
        }
        if (Config.PHOBOS) {
            RegisterUtilities.registerEventHandler(new PhobosEvents());
        }
        if (Config.TRITON) {
            RegisterUtilities.registerEventHandler(new TritonEvents());
        }
        if (Config.RHEA) {
            RegisterUtilities.registerEventHandler(new RheaEvents());
        }
        if (Config.TITAN) {
            RegisterUtilities.registerEventHandler(new TitanEvents());
        }
        if (Config.OBERON) {
            RegisterUtilities.registerEventHandler(new OberonEvents());
        }
        if (Config.IAPETUS) {
            RegisterUtilities.registerEventHandler(new IapetusEvents());
        }
        if (Config.TITANIA) {
            RegisterUtilities.registerEventHandler(new TitaniaEvents());
        }
        if (Config.KUIPER_BELT) {
            RegisterUtilities.registerEventHandler(new KuiperBeltEvents());
        }
        ExtraPlanets_Blocks.init();
        ExtraPlanets_Machines.init();
        ExtraPlanets_Fluids.init();
        ExtraPlanets_Tools.init();
        ExtraPlanets_Armor.init();
        ExtraPlanets_Items.init();
        ExtraPlanets_SolarSystems.init();
        ExtraPlanets_Planets.init();
        ExtraPlanets_Moons.init();
        //ExtraPlanets_SpaceStations.init();
        if (Loader.isClassAvailable("net.machinemuse.numina.module.IPowerModule") && Loader.isClassAvailable("net.machinemuse.powersuits.common.ModuleManager")) {
            MachineMusePowersuitsCompatibility.init();
        }
        RegisterUtilities.registerEventHandler(new RegistrationHandler());
        RegisterUtilities.registerEventHandler(new BoneMealHandler());
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        registerFluidSubmergedTextures();
        registerNonMobEntities();
        registerCreatures();
        packetPipeline = ExtraPlanetsChannelHandler.init();
        ExtraPlanets_Recipes.initOther();
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        RegisterUtilities.registerEventHandler(new Config());
        ExtraPlanets_Modules.init();
        CapabilityStatsHandler.register();
        CapabilityStatsClientHandler.register();
        registerSchematicsRecipes();
        registerSchematicsItems();
        addDungeonLoot();
        NetworkUtilities.registerGuiHandler(instance, new GuiHandler());
        if (Config.GC_DECONSTRUCTOR_SUPPORT) {
            RegisterDeconstructorCompatibility();
        }
        if (Config.EXTREME_REACTORS_SUPPORT) {
            ExtremeReactorsCompatibility.init();
        }
        if (Config.MC_MULITPART_SUPPORT) {
            MCMultiPartCompatibility.init();
        }
        if (generateRecipes) {
            ExtraPlanets_RecipeGeneration.generate();
            ExtraPlanets_RecipeGeneration.generateConstants();
        }
        if (Config.GC_PRESSURE || Config.GC_RADIATION) {
            MarsModule.planetMars.addChecklistKeys("space_suit");
            //GalacticraftCore.moonMoon.addChecklistKeys(new String[]{"space_suit"});
            VenusModule.planetVenus.addChecklistKeys("space_suit");
            AsteroidsModule.planetAsteroids.addChecklistKeys("space_suit");
        }
        ExtraPlanetsDimensions.CERES = WorldUtil.getDimensionTypeById(Config.CERES_ID);
        ExtraPlanetsDimensions.ERIS = WorldUtil.getDimensionTypeById(Config.ERIS_ID);
        ExtraPlanetsDimensions.JUPITER = WorldUtil.getDimensionTypeById(Config.JUPITER_ID);
        ExtraPlanetsDimensions.MERCURY = WorldUtil.getDimensionTypeById(Config.MERCURY_ID);
        ExtraPlanetsDimensions.NEPTUNE = WorldUtil.getDimensionTypeById(Config.NEPTUNE_ID);
        ExtraPlanetsDimensions.PLUTO = WorldUtil.getDimensionTypeById(Config.PLUTO_ID);
        ExtraPlanetsDimensions.SATURN = WorldUtil.getDimensionTypeById(Config.SATURN_ID);
        ExtraPlanetsDimensions.URANUS = WorldUtil.getDimensionTypeById(Config.URANUS_ID);
        ExtraPlanetsDimensions.KEPLER22B = WorldUtil.getDimensionTypeById(Config.KEPLER22B_ID);
        ExtraPlanetsDimensions.CALLISTO = WorldUtil.getDimensionTypeById(Config.CALLISTO_ID);
        ExtraPlanetsDimensions.DEIMOS = WorldUtil.getDimensionTypeById(Config.DEIMOS_ID);
        ExtraPlanetsDimensions.EUROPA = WorldUtil.getDimensionTypeById(Config.EUROPA_ID);
        ExtraPlanetsDimensions.GANYMEDE = WorldUtil.getDimensionTypeById(Config.GANYMEDE_ID);
        ExtraPlanetsDimensions.IAPETUS = WorldUtil.getDimensionTypeById(Config.IAPETUS_ID);
        ExtraPlanetsDimensions.IO = WorldUtil.getDimensionTypeById(Config.IO_ID);
        ExtraPlanetsDimensions.OBERON = WorldUtil.getDimensionTypeById(Config.OBERON_ID);
        ExtraPlanetsDimensions.PHOBOS = WorldUtil.getDimensionTypeById(Config.PHOBOS_ID);
        ExtraPlanetsDimensions.RHEA = WorldUtil.getDimensionTypeById(Config.RHEA_ID);
        ExtraPlanetsDimensions.TITAN = WorldUtil.getDimensionTypeById(Config.TITAN_ID);
        ExtraPlanetsDimensions.TITANIA = WorldUtil.getDimensionTypeById(Config.TITANIA_ID);
        ExtraPlanetsDimensions.TRITION = WorldUtil.getDimensionTypeById(Config.TRITON_ID);
        //ExtraPlanetsDimensions.CERES_ORBIT = DimensionType.register("Ceres Space Station", "_ceres_orbit", Config.CERES_SPACE_STATION_ID, WorldProviderCeresOrbit.class, false);
        //ExtraPlanetsDimensions.ERIS_ORBIT = DimensionType.register("Eris Space Station", "_eris_orbit", Config.ERIS_SPACE_STATION_ID, WorldProviderErisOrbit.class, false);
        //ExtraPlanetsDimensions.JUPITER_ORBIT = DimensionType.register("Jupiter Space Station", "_jupiter_orbit", Config.JUPITER_SPACE_STATION_ID, WorldProviderJupiterOrbit.class, false);
        //ExtraPlanetsDimensions.MERCURY_ORBIT = DimensionType.register("Mercury Space Station", "_mercury_orbit", Config.MERCURY_SPACE_STATION_ID, WorldProviderMercuryOrbit.class, false);
        //ExtraPlanetsDimensions.NEPTUNE_ORBIT = DimensionType.register("Neptune Space Station", "_neptune_orbit", Config.NEPTUNE_SPACE_STATION_ID, WorldProviderNeptuneOrbit.class, false);
        //ExtraPlanetsDimensions.PLUTO_ORBIT = DimensionType.register("Pluto Space Station", "_pluto_orbit", Config.PLUTO_SPACE_STATION_ID, WorldProviderPlutoOrbit.class, false);
        //ExtraPlanetsDimensions.SATURN_ORBIT = DimensionType.register("Saturn Space Station", "_saturn_orbit", Config.SATURN_SPACE_STATION_ID, WorldProviderSaturnOrbit.class, false);
        //ExtraPlanetsDimensions.URANUS_ORBIT = DimensionType.register("Uranus Space Station", "_uranus_orbit", Config.URANUS_SPACE_STATION_ID, WorldProviderUranusOrbit.class, false);
        //ExtraPlanetsDimensions.KEPLER22B_ORBIT = DimensionType.register("Kepler22b Space Station", "orbit", Config.KEPLER22B_SPACE_STATION_ID, WorldProviderKepler22bOrbit.class, false);
        //ExtraPlanetsDimensions.MARS_ORBIT = DimensionType.register("Mars Space Station", "_mars_orbit", Config.MARS_SPACE_STATION_ID, WorldProviderMarsOrbit.class, false);
        //ExtraPlanetsDimensions.VENUS_ORBIT = DimensionType.register("Venus Space Station", "_venus_orbit", Config.VENUS_SPACE_STATION_ID, WorldProviderVenusOrbit.class, false);
        //ExtraPlanetsDimensions.CERES_ORBIT_KEEPLOADED = DimensionType.register("Ceres Space Station", "_ceres_orbit", Config.CERES_SPACE_STATION_STATIC_ID, WorldProviderCeresOrbit.class, true);
        //ExtraPlanetsDimensions.ERIS_ORBIT_KEEPLOADED = DimensionType.register("Eris Space Station", "_eris_orbit", Config.ERIS_SPACE_STATION_STATIC_ID, WorldProviderErisOrbit.class, true);
        //ExtraPlanetsDimensions.JUPITER_ORBIT_KEEPLOADED = DimensionType.register("Jupiter Space Station", "_jupiter_orbit", Config.JUPITER_SPACE_STATION_STATIC_ID, WorldProviderJupiterOrbit.class, true);
        //ExtraPlanetsDimensions.MERCURY_ORBIT_KEEPLOADED = DimensionType.register("Mercury Space Station", "_mercury_orbit", Config.MERCURY_SPACE_STATION_STATIC_ID, WorldProviderMercuryOrbit.class, true);
        //ExtraPlanetsDimensions.NEPTUNE_ORBIT_KEEPLOADED = DimensionType.register("Neptune Space Station", "_neptune_orbit", Config.NEPTUNE_SPACE_STATION_STATIC_ID, WorldProviderNeptuneOrbit.class, true);
        //ExtraPlanetsDimensions.PLUTO_ORBIT_KEEPLOADED = DimensionType.register("Pluto Space Station", "_pluto_orbit", Config.PLUTO_SPACE_STATION_STATIC_ID, WorldProviderPlutoOrbit.class, true);
        //ExtraPlanetsDimensions.SATURN_ORBIT_KEEPLOADED = DimensionType.register("Saturn Space Station", "_saturn_orbit", Config.SATURN_SPACE_STATION_STATIC_ID, WorldProviderSaturnOrbit.class, true);
        //ExtraPlanetsDimensions.URANUS_ORBIT_KEEPLOADED = DimensionType.register("Uranus Space Station", "_uranus_orbit", Config.URANUS_SPACE_STATION_STATIC_ID, WorldProviderUranusOrbit.class, true);
        //ExtraPlanetsDimensions.KEPLER22B_ORBIT_KEEPLOADED = DimensionType.register("Kepler22b Space Station", "orbit", Config.KEPLER22B_SPACE_STATION_STATIC_ID, WorldProviderKepler22bOrbit.class, true);
        //ExtraPlanetsDimensions.MARS_ORBIT_KEEPLOADED = DimensionType.register("Mars Space Station", "_mars_orbit", Config.MARS_SPACE_STATION_STATIC_ID, WorldProviderMarsOrbit.class, true);
        //ExtraPlanetsDimensions.VENUS_ORBIT_KEEPLOADED = DimensionType.register("Venus Space Station", "_venus_orbit", Config.VENUS_SPACE_STATION_STATIC_ID, WorldProviderVenusOrbit.class, true);
        proxy.postInit(event);
    }

    private void registerFluidSubmergedTextures() {
        GalacticraftCore.proxy.registerFluidTexture(ExtraPlanets_Fluids.CLEAN_WATER_FLUID, new ResourceLocation("extraplanets", "textures/misc/under_clean_water.png"));
        GalacticraftCore.proxy.registerFluidTexture(ExtraPlanets_Fluids.FROZEN_WATER_FLUID, new ResourceLocation("extraplanets", "textures/misc/under_frozen_water.png"));
        GalacticraftCore.proxy.registerFluidTexture(ExtraPlanets_Fluids.GLOWSTONE_FLUID, new ResourceLocation("extraplanets", "textures/misc/under_glowstone.png"));
        GalacticraftCore.proxy.registerFluidTexture(ExtraPlanets_Fluids.INFECTED_WATER_FLUID, new ResourceLocation("extraplanets", "textures/misc/under_infected_water.png"));
        GalacticraftCore.proxy.registerFluidTexture(ExtraPlanets_Fluids.LIQUID_CARAMEL_FLUID, new ResourceLocation("extraplanets", "textures/misc/under_liquid_caramel.png"));
        GalacticraftCore.proxy.registerFluidTexture(ExtraPlanets_Fluids.LIQUID_CHOCOLATE_FLUID, new ResourceLocation("extraplanets", "textures/misc/under_liquid_chocolate.png"));
        GalacticraftCore.proxy.registerFluidTexture(ExtraPlanets_Fluids.LIQUID_HYDROCARBON_FLUID, new ResourceLocation("extraplanets", "textures/misc/under_liquid_hydrocarbon.png"));
        GalacticraftCore.proxy.registerFluidTexture(ExtraPlanets_Fluids.MAGMA_FLUID, new ResourceLocation("extraplanets", "textures/misc/under_magma.png"));
        GalacticraftCore.proxy.registerFluidTexture(ExtraPlanets_Fluids.METHANE_FLUID, new ResourceLocation("extraplanets", "textures/misc/under_methane.png"));
        GalacticraftCore.proxy.registerFluidTexture(ExtraPlanets_Fluids.NITROGEN_FLUID, new ResourceLocation("extraplanets", "textures/misc/under_nitrogen.png"));
        GalacticraftCore.proxy.registerFluidTexture(ExtraPlanets_Fluids.NITROGEN_ICE_FLUID, new ResourceLocation("extraplanets", "textures/misc/under_nitrogen_ice.png"));
        GalacticraftCore.proxy.registerFluidTexture(ExtraPlanets_Fluids.RADIO_ACTIVE_WATER_FLUID, new ResourceLocation("extraplanets", "textures/misc/under_radio_active_water.png"));
        GalacticraftCore.proxy.registerFluidTexture(ExtraPlanets_Fluids.SALT_FLUID, new ResourceLocation("extraplanets", "textures/misc/under_salt.png"));
    }

    private void registerNonMobEntities() {
        if (Config.CERES && Config.NUCLEAR_BOMB) {
            RegisterUtilities.registerNonMobEntity("extraplanets", instance, EntityNuclearBombPrimed.class, "extraplanets.NuclearBombPrimed", 150, 1, true);
        }
        if (Config.SATURN && Config.FIRE_BOMB) {
            RegisterUtilities.registerNonMobEntity("extraplanets", instance, EntityFireBombPrimed.class, "extraplanets.FireBombPrimed", 150, 1, true);
        }
        if (Config.MERCURY) {
            RegisterUtilities.registerNonMobEntity("extraplanets", instance, EntityTier4Rocket.class, "extraplanets.EntityTier4Rocket", 150, 1, false);
            RegisterUtilities.registerNonMobEntity("extraplanets", instance, EntityMercuryLander.class, "extraplanets.EntityMercuryLander", 150, 5, false);
        }
        if (Config.JUPITER) {
            RegisterUtilities.registerNonMobEntity("extraplanets", instance, EntityTier5Rocket.class, "extraplanets.EntityTier5Rocket", 150, 1, false);
            RegisterUtilities.registerNonMobEntity("extraplanets", instance, EntityJupiterLander.class, "extraplanets.EntityJupiterLander", 150, 5, false);
        }
        if (Config.SATURN) {
            RegisterUtilities.registerNonMobEntity("extraplanets", instance, EntityTier6Rocket.class, "extraplanets.EntityTier6Rocket", 150, 1, false);
            RegisterUtilities.registerNonMobEntity("extraplanets", instance, EntitySaturnLander.class, "extraplanets.EntitSaturnLander", 150, 5, false);
        }
        if (Config.URANUS) {
            RegisterUtilities.registerNonMobEntity("extraplanets", instance, EntityTier7Rocket.class, "extraplanets.EntityTier7Rocket", 150, 1, false);
            RegisterUtilities.registerNonMobEntity("extraplanets", instance, EntityUranusLander.class, "extraplanets.EntityUranusLander", 150, 5, false);
        }
        if (Config.NEPTUNE) {
            RegisterUtilities.registerNonMobEntity("extraplanets", instance, EntityTier8Rocket.class, "extraplanets.EntityTier8Rocket", 150, 1, false);
            RegisterUtilities.registerNonMobEntity("extraplanets", instance, EntityNeptuneLander.class, "extraplanets.EntityNeptuneLander", 150, 5, false);
        }
        if (Config.PLUTO) {
            RegisterUtilities.registerNonMobEntity("extraplanets", instance, EntityTier9Rocket.class, "extraplanets.EntityTier9Rocket", 150, 1, false);
        }
        if (Config.ERIS) {
            RegisterUtilities.registerNonMobEntity("extraplanets", instance, EntityTier10Rocket.class, "extraplanets.EntityTier10Rocket", 150, 1, false);
        }
        if (Config.ERIS && Config.KEPLER22B && Config.ELECTRIC_ROCKET) {
            RegisterUtilities.registerNonMobEntity("extraplanets", instance, EntityElectricRocket.class, "extraplanets.EntityTier10ElectricRocket", 150, 1, false);
        }
        if (Config.MARS_ROVER) {
            RegisterUtilities.registerNonMobEntity("extraplanets", instance, EntityMarsRover.class, "extraplanets.EntityMarsRover", 150, 1, false);
        }
        if (Config.VENUS_ROVER) {
            RegisterUtilities.registerNonMobEntity("extraplanets", instance, EntityVenusRover.class, "extraplanets.EntityVenusRover", 150, 1, false);
        }
        RegisterUtilities.registerNonMobEntity("extraplanets", instance, EntityGeneralLander.class, "EntityGeneralLander", 150, 5, false);
    }

    private void registerCreatures() {
        if (Config.MERCURY) {
            if (Config.USE_DEFAULT_BOSSES) {
                RegisterUtilities.registerMobEntity("extraplanets", instance, EntityCreeperBossMercury.class, "extraplanets.CreeperBossMercury", 894731, 0);
            } else {
                RegisterUtilities.registerMobEntity("extraplanets", instance, EntityEvolvedMagmaCubeBoss.class, "extraplanets.EvolvedMagmaCubeBoss", 3407872, 16579584);
            }
        }
        if (Config.JUPITER) {
            if (Config.USE_DEFAULT_BOSSES) {
                RegisterUtilities.registerMobEntity("extraplanets", instance, EntityCreeperBossJupiter.class, "extraplanets.CreeperBossJupiter", 894731, 0);
            } else {
                RegisterUtilities.registerMobEntity("extraplanets", instance, EntityEvolvedFireBatBoss.class, "extraplanets.EvolvedFireBatBoss", 16167425, 0);
            }
        }
        if (Config.SATURN) {
            if (Config.USE_DEFAULT_BOSSES) {
                RegisterUtilities.registerMobEntity("extraplanets", instance, EntityCreeperBossSaturn.class, "extraplanets.CreeperBossSaturn", 894731, 0);
            } else {
                RegisterUtilities.registerMobEntity("extraplanets", instance, EntityEvolvedGhastBoss.class, "extraplanets.EvolvedGhastBoss", 894731, 0);
            }
        }
        if (Config.URANUS) {
            if (Config.USE_DEFAULT_BOSSES) {
                RegisterUtilities.registerMobEntity("extraplanets", instance, EntityCreeperBossUranus.class, "extraplanets.CreeperBossUranus", 894731, 0);
            } else {
                RegisterUtilities.registerMobEntity("extraplanets", instance, EntityEvolvedIceSlimeBoss.class, "extraplanets.EvolvedIceSlimeBoss", 16382457, 44975);
            }
        }
        if (Config.NEPTUNE) {
            if (Config.USE_DEFAULT_BOSSES) {
                RegisterUtilities.registerMobEntity("extraplanets", instance, EntityCreeperBossNeptune.class, "extraplanets.CreeperBossNeptune", 894731, 0);
            } else {
                RegisterUtilities.registerMobEntity("extraplanets", instance, EntityEvolvedSnowmanBoss.class, "extraplanets.EvolvedSnowmanBoss", 894731, 0);
            }
        }
        if (Config.PLUTO) {
            if (Config.USE_DEFAULT_BOSSES) {
                RegisterUtilities.registerMobEntity("extraplanets", instance, EntityCreeperBossPluto.class, "extraplanets.CreeperBossPluto", 894731, 0);
            } else {
                RegisterUtilities.registerMobEntity("extraplanets", instance, EntityEvolvedSpacemanBoss.class, "extraplanets.EvolvedSpaceManBoss", 894731, 0);
            }
        }
        if (!Config.ERIS) {
            return;
        }
        if (Config.USE_DEFAULT_BOSSES) {
            RegisterUtilities.registerMobEntity("extraplanets", instance, EntityCreeperBossEris.class, "extraplanets.CreeperBossEris", 894731, 0);
        } else {
            RegisterUtilities.registerMobEntity("extraplanets", instance, EntityEvolvedGiantZombieBoss.class, "extraplanets.EvolvedGiantZombieBoss", 894731, 0);
        }
    }

    private void registerSchematicsRecipes() {
        if (Config.MERCURY) {
            SchematicRegistry.registerSchematicRecipe(new SchematicTier4Rocket());
        }
        if (Config.JUPITER) {
            SchematicRegistry.registerSchematicRecipe(new SchematicTier5Rocket());
        }
        if (Config.SATURN) {
            SchematicRegistry.registerSchematicRecipe(new SchematicTier6Rocket());
        }
        if (Config.URANUS) {
            SchematicRegistry.registerSchematicRecipe(new SchematicTier7Rocket());
        }
        if (Config.NEPTUNE) {
            SchematicRegistry.registerSchematicRecipe(new SchematicTier8Rocket());
        }
        if (Config.PLUTO) {
            SchematicRegistry.registerSchematicRecipe(new SchematicTier9Rocket());
        }
        if (Config.ERIS) {
            SchematicRegistry.registerSchematicRecipe(new SchematicTier10Rocket());
        }
        if (Config.ERIS && Config.KEPLER22B && Config.ELECTRIC_ROCKET) {
            SchematicRegistry.registerSchematicRecipe(new SchematicTier10ElectricRocket());
        }
        if (Config.MARS_ROVER) {
            SchematicRegistry.registerSchematicRecipe(new SchematicMarsRover());
        }
        if (Config.VENUS_ROVER) {
            SchematicRegistry.registerSchematicRecipe(new SchematicVenusRover());
        }
    }

    private void registerSchematicsItems() {
        ItemSchematicTier4Rocket.registerSchematicItems();
        ItemSchematicTier5Rocket.registerSchematicItems();
        ItemSchematicTier6Rocket.registerSchematicItems();
        ItemSchematicTier7Rocket.registerSchematicItems();
        ItemSchematicTier8Rocket.registerSchematicItems();
        ItemSchematicTier9Rocket.registerSchematicItems();
        ItemSchematicTier10Rocket.registerSchematicItems();
        ItemSchematicTier10ElectricRocket.registerSchematicItems();
        ItemSchematicVenusRover.registerSchematicItems();
        ItemSchematicMarsRover.registerSchematicItems();
    }

    private void addDungeonLoot() {
        if (Config.MERCURY) {
            if (Config.MORE_PLANETS_COMPATIBILITY) {
                GalacticraftRegistry.addDungeonLoot(11, new ItemStack(ExtraPlanets_Items.TIER_4_SCHEMATIC, 1, 0));
                GalacticraftRegistry.addDungeonLoot(11, new ItemStack(ExtraPlanets_Items.GEIGER_COUNTER, 1, 0));
            } else {
                GalacticraftRegistry.addDungeonLoot(4, new ItemStack(ExtraPlanets_Items.TIER_4_SCHEMATIC, 1, 0));
                GalacticraftRegistry.addDungeonLoot(4, new ItemStack(ExtraPlanets_Items.GEIGER_COUNTER, 1, 0));
            }
        }
        if (Config.JUPITER) {
            if (Config.MORE_PLANETS_COMPATIBILITY) {
                GalacticraftRegistry.addDungeonLoot(4, new ItemStack(ExtraPlanets_Items.TIER_5_SCHEMATIC, 1, 0));
            } else {
                GalacticraftRegistry.addDungeonLoot(5, new ItemStack(ExtraPlanets_Items.TIER_5_SCHEMATIC, 1, 0));
            }
        }
        if (Config.SATURN) {
            if (Config.MORE_PLANETS_COMPATIBILITY) {
                GalacticraftRegistry.addDungeonLoot(5, new ItemStack(ExtraPlanets_Items.TIER_6_SCHEMATIC, 1, 0));
            } else {
                GalacticraftRegistry.addDungeonLoot(6, new ItemStack(ExtraPlanets_Items.TIER_6_SCHEMATIC, 1, 0));
            }
        }
        if (Config.URANUS) {
            GalacticraftRegistry.addDungeonLoot(7, new ItemStack(ExtraPlanets_Items.TIER_7_SCHEMATIC, 1, 0));
        }
        if (Config.NEPTUNE) {
            GalacticraftRegistry.addDungeonLoot(8, new ItemStack(ExtraPlanets_Items.TIER_8_SCHEMATIC, 1, 0));
        }
        if (Config.PLUTO) {
            GalacticraftRegistry.addDungeonLoot(9, new ItemStack(ExtraPlanets_Items.TIER_9_SCHEMATIC, 1, 0));
        }
        if (Config.ERIS) {
            GalacticraftRegistry.addDungeonLoot(10, new ItemStack(ExtraPlanets_Items.TIER_10_SCHEMATIC, 1, 0));
        }
        if (Config.ERIS && Config.KEPLER22B && Config.ELECTRIC_ROCKET) {
            GalacticraftRegistry.addDungeonLoot(10, new ItemStack(ExtraPlanets_Items.TIER_10_ELECTRIC_ROCKET_SCHEMATIC, 1, 0));
        }
        if (Config.MARS_ROVER) {
            GalacticraftRegistry.addDungeonLoot(2, new ItemStack(ExtraPlanets_Items.MARS_ROVER_SCHEMATIC, 1, 0));
        }
        if (Config.VENUS_ROVER) {
            GalacticraftRegistry.addDungeonLoot(3, new ItemStack(ExtraPlanets_Items.VENUS_ROVER_SCHEMATIC, 1, 0));
        }
    }

    private void RegisterDeconstructorCompatibility() {
        TileEntityDeconstructor.knownRecipes.addAll(Tier4RocketRecipes.getTier4RocketRecipes());
        TileEntityDeconstructor.knownRecipes.addAll(Tier5RocketRecipes.getTier5RocketRecipes());
        TileEntityDeconstructor.knownRecipes.addAll(Tier6RocketRecipes.getTier6RocketRecipes());
        TileEntityDeconstructor.knownRecipes.addAll(Tier7RocketRecipes.getTier7RocketRecipes());
        TileEntityDeconstructor.knownRecipes.addAll(Tier8RocketRecipes.getTier8RocketRecipes());
        TileEntityDeconstructor.knownRecipes.addAll(Tier9RocketRecipes.getTier9RocketRecipes());
        TileEntityDeconstructor.knownRecipes.addAll(Tier10RocketRecipes.getTier10RocketRecipes());
        TileEntityDeconstructor.knownRecipes.addAll(Tier10ElectricRocketRecipes.getTier10ElectricRocketRecipes());
        TileEntityDeconstructor.knownRecipes.addAll(MarsRoverRecipes.getMarsRoverRecipes());
        TileEntityDeconstructor.knownRecipes.addAll(VenusRoverRecipes.getVenusRoverRecipes());
        TileEntityDeconstructor.addSalvage(new ItemStack(ExtraPlanets_Items.TIER_4_ITEMS, 1, 3));
        TileEntityDeconstructor.addSalvage(new ItemStack(ExtraPlanets_Items.TIER_4_ITEMS, 1, 4));
        TileEntityDeconstructor.addSalvage(new ItemStack(ExtraPlanets_Items.TIER_5_ITEMS, 1, 3));
        TileEntityDeconstructor.addSalvage(new ItemStack(ExtraPlanets_Items.TIER_5_ITEMS, 1, 4));
        TileEntityDeconstructor.addSalvage(new ItemStack(ExtraPlanets_Items.TIER_5_ITEMS, 1, 6));
        TileEntityDeconstructor.addSalvage(new ItemStack(ExtraPlanets_Items.TIER_6_ITEMS, 1, 3));
        TileEntityDeconstructor.addSalvage(new ItemStack(ExtraPlanets_Items.TIER_6_ITEMS, 1, 4));
        TileEntityDeconstructor.addSalvage(new ItemStack(ExtraPlanets_Items.TIER_7_ITEMS, 1, 3));
        TileEntityDeconstructor.addSalvage(new ItemStack(ExtraPlanets_Items.TIER_7_ITEMS, 1, 4));
        TileEntityDeconstructor.addSalvage(new ItemStack(ExtraPlanets_Items.TIER_7_ITEMS, 1, 6));
        TileEntityDeconstructor.addSalvage(new ItemStack(ExtraPlanets_Items.TIER_8_ITEMS, 1, 3));
        TileEntityDeconstructor.addSalvage(new ItemStack(ExtraPlanets_Items.TIER_8_ITEMS, 1, 4));
        TileEntityDeconstructor.addSalvage(new ItemStack(ExtraPlanets_Items.TIER_9_ITEMS, 1, 3));
        TileEntityDeconstructor.addSalvage(new ItemStack(ExtraPlanets_Items.TIER_9_ITEMS, 1, 4));
        TileEntityDeconstructor.addSalvage(new ItemStack(ExtraPlanets_Items.TIER_10_ITEMS, 1, 3));
        TileEntityDeconstructor.addSalvage(new ItemStack(ExtraPlanets_Items.TIER_10_ITEMS, 1, 4));
        TileEntityDeconstructor.addSalvage(new ItemStack(ExtraPlanets_Items.TIER_11_ITEMS, 1, 6));
    }

    @Mod.EventHandler
    public void onFingerprintViolation(FMLFingerprintViolationEvent event) {
        MessageUtilities.fatalErrorMessageToLog("extraplanets", "Invalid fingerprint detected! The file " + event.getSource().getName() + " may have been tampered with. This version will NOT be supported!");
    }

    @Mod.EventHandler
    public void onStartAboutToStartEvent(FMLServerAboutToStartEvent event) {
        if (Config.MOVE_ID_DIMENSION_LEGACY) {
            File folder = new File(FMLCommonHandler.instance().getSavesDirectory() + "/" + event.getServer().getFolderName());
            String[] idsOld = {"" + Config.IO_ID_LEGACY, "" + Config.EUROPA_ID_LEGACY, "" + Config.PHOBOS_ID_LEGACY, "" + Config.DEIMOS_ID_LEGACY, "" + Config.TRITON_ID_LEGACY, "" + Config.CALLISTO_ID_LEGACY, "" + Config.GANYMEDE_ID_LEGACY, "" + Config.RHEA_ID_LEGACY, "" + Config.TITAN_ID_LEGACY, "" + Config.OBERON_ID_LEGACY, "" + Config.TITANIA_ID_LEGACY, "" + Config.IAPETUS_ID_LEGACY};
            String[] idsNew = new String[12];
            idsNew[0] = "" + Config.IO_ID;
            idsNew[1] = "" + Config.EUROPA_ID;
            idsNew[2] = "" + Config.PHOBOS_ID;
            idsNew[3] = "" + Config.DEIMOS_ID;
            idsNew[4] = "" + Config.TRITON_ID;
            idsNew[5] = "" + Config.CALLISTO_ID;
            idsNew[6] = "" + Config.GANYMEDE_ID;
            idsNew[7] = "" + Config.RHEA_ID;
            idsOld[8] = "" + Config.TITAN_ID;
            idsOld[9] = "" + Config.OBERON_ID;
            idsOld[10] = "" + Config.TITANIA;
            idsOld[11] = "" + Config.IAPETUS;
            if (folder.exists()) {
                for (int i = 0; i < idsOld.length; i++) {
                    File tempFolder = new File(folder.getPath() + "/DIM" + idsOld[i]);
                    File newFolder = new File(folder.getPath() + "/DIM" + idsNew[i]);
                    if (tempFolder.exists()) {
                        if (!newFolder.exists()) {
                            MessageUtilities.infoMessageToLog("extraplanets", "Mirgrated Dimension Folder " + idsOld[i] + " to new name of " + idsNew[i]);
                            tempFolder.renameTo(newFolder);
                        } else {
                            MessageUtilities.infoMessageToLog("extraplanets", "Unable to Mrigrate Folder " + idsOld[i] + " to new name of " + idsNew[i] + " Due to this folder already exists");
                        }
                    }
                }
            }
        }
    }

    @Mod.EventBusSubscriber(modid = "extraplanets")
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void registerBlocksEvent(RegistryEvent.Register<Block> event) {
            for (Block block : ExtraPlanets.blocksList) {
                event.getRegistry().register(block);
            }
        }

        @SubscribeEvent
        public static void registerItemsEvent(RegistryEvent.Register<Item> event) {
            for (Item item : ExtraPlanets.itemList) {
                event.getRegistry().register(item);
            }
            if (Config.ORE_DICTIONARY_INGOTS) {
                ExtraPlanets_Items.OreDictionaryIngotsRegister();
            }
            if (Config.ORE_DICTIONARY_OTHER) {
                ExtraPlanets_Items.OreDictionaryItemsRegister();
            }
            if (Config.ORE_DICTIONARY) {
                ExtraPlanets_Blocks.OreDictionaryRegister();
            }
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void registerRecipesEvent(RegistryEvent.Register<IRecipe> register) {
            ExtraPlanets_Recipes.init();
            if (Config.MORE_PLANETS_ROCKET_CRUSHER_SUPPORT) {
                MorePlanetsCompatibility.init();
            }
        }
    }
}