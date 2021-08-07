//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mjr.extraplanets.moons.Europa.worldgen;

import com.mjr.extraplanets.Config;
import com.mjr.extraplanets.blocks.ExtraPlanets_Blocks;
import com.mjr.extraplanets.blocks.fluid.ExtraPlanets_Fluids;
import com.mjr.extraplanets.blocks.planetAndMoonBlocks.BlockBasicEuropa;
import com.mjr.extraplanets.blocks.planetAndMoonBlocks.BlockBasicEuropa.EnumBlockBasic;
import com.mjr.extraplanets.moons.Europa.worldgen.biomes.BiomeGenEuropaIceValleys;
import com.mjr.mjrlegendslib.util.WorldGenUtilities;
import com.mjr.mjrlegendslib.world.features.WorldGenSphere;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeAdaptive;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.world.gen.CustomOreGeneration;
import micdoodle8.mods.galacticraft.core.world.gen.CustomOreGenerationData;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Post;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Pre;

import java.util.List;

public class BiomeDecoratorEuropa extends BiomeDecoratorSpace {
    // Custom code
    private static final List<CustomOreGenerationData> customOreList = CustomOreGeneration.getOresForObject("Europa");
    private final WorldGenerator gravelGen;
    private final WorldGenerator fossilsGen;
    private final WorldGenerator iceGen;
    private final WorldGenerator iceSubSurfaceGen;
    private final WorldGenerator iceSurfaceGen;
    private final int LakesPerChunk = 5;
    private WorldGenerator copperGen;
    private WorldGenerator tinGen;
    private WorldGenerator ironGen;
    private World currentWorld;

    public BiomeDecoratorEuropa() {
        if (Config.GENERATE_ORES_EUROPA) {
            this.copperGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.EUROPA_BLOCKS, 4, 5, true, ExtraPlanets_Blocks.EUROPA_BLOCKS, 2);
            this.tinGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.EUROPA_BLOCKS, 4, 4, true, ExtraPlanets_Blocks.EUROPA_BLOCKS, 2);
            this.ironGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.EUROPA_BLOCKS, 20, 3, true, ExtraPlanets_Blocks.EUROPA_BLOCKS, 2);
        }

        this.gravelGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.EUROPA_GRAVEL, 12, 0, true, ExtraPlanets_Blocks.EUROPA_BLOCKS, 2);
        this.fossilsGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.FOSSIL, 3, 0, true, ExtraPlanets_Blocks.EUROPA_BLOCKS, 1);
        this.iceGen = new WorldGenMinableMeta(Blocks.ICE, 10, 0, true, ExtraPlanets_Blocks.EUROPA_BLOCKS, 2);
        this.iceSubSurfaceGen = new WorldGenMinableMeta(Blocks.ICE, 10, 0, true, ExtraPlanets_Blocks.EUROPA_BLOCKS, 1);
        this.iceSurfaceGen = new WorldGenMinableMeta(Blocks.ICE, 10, 0, true, ExtraPlanets_Blocks.EUROPA_BLOCKS, 0);
    }

    protected World getCurrentWorld() {
        return this.currentWorld;
    }

    protected void setCurrentWorld(World world) {
        this.currentWorld = world;
    }

    protected void decorate() {
        if (Config.GENERATE_ORES_EUROPA) {
            this.generateOre(26, this.copperGen, 0, 60);
            this.generateOre(23, this.tinGen, 0, 60);
            this.generateOre(20, this.ironGen, 0, 64);
        }

        // Custom code
        if (customOreList != null) {
            for (CustomOreGenerationData ore : customOreList) {
                int amountPerChunk = ore.amountPerChunk;
                int minY = ore.minY;
                int maxY = ore.maxY;
                WorldGenerator blockGeneration = new WorldGenMinableMeta(ore.block, ore.clusterSize, ore.meta, ore.useMeta, ExtraPlanets_Blocks.EUROPA_BLOCKS, 2);
                this.generateOre(amountPerChunk, blockGeneration, minY, maxY);
            }
        }
        // Custom code

        this.generateOre(15, this.gravelGen, 0, 80);
        this.generateOre(10, this.fossilsGen, 0, 256);
        this.generateOre(15, this.iceGen, 0, 256);
        this.generateOre(15, this.iceSubSurfaceGen, 0, 256);
        if (!((BiomeAdaptive) this.getCurrentWorld().getBiome(new BlockPos(this.posX, 0, this.posZ))).isInstance(BiomeGenEuropaIceValleys.class)) {
            this.generateOre(15, this.iceSurfaceGen, 0, 256);
        }

        MinecraftForge.EVENT_BUS.post(new Pre(this.currentWorld, this.rand, new BlockPos(this.posX, 0, this.posZ)));

        int i;
        for (i = 0; i < this.LakesPerChunk; ++i) {
            if (this.rand.nextInt(10) == 0) {
                WorldGenUtilities.generateLake(this.currentWorld, this.rand, new BlockPos(this.posX, 0, this.posZ), ExtraPlanets_Fluids.SALT, ExtraPlanets_Blocks.CERES_BLOCKS);
            }
        }

        for (i = 0; i < 1; ++i) {
            if (this.rand.nextInt(100) == 0) {
                WorldGenUtilities.generateLake(this.currentWorld, this.rand, new BlockPos(this.posX, 0, this.posZ), ExtraPlanets_Fluids.RADIO_ACTIVE_WATER, ExtraPlanets_Blocks.CERES_BLOCKS);
            }
        }

        if (Config.GENERATE_EUROPA_IRON_CHUNKS) {
            for (i = 0; i < 2; ++i) {
                if (this.rand.nextInt(20) == 1) {
                    WorldGenUtilities.generateStructureWithRangeY(new WorldGenSphere(Config.DEBUG_MODE, "extraplanets", ExtraPlanets_Blocks.EUROPA_BLOCKS.getDefaultState().withProperty(BlockBasicEuropa.BASIC_TYPE, EnumBlockBasic.ORE_IRON), 12, 0), this.currentWorld, this.rand, new BlockPos(this.posX, 0, this.posZ), 20, 40);
                }
            }
        }

        MinecraftForge.EVENT_BUS.post(new Post(this.currentWorld, this.rand, new BlockPos(this.posX, 0, this.posZ)));
    }
}
