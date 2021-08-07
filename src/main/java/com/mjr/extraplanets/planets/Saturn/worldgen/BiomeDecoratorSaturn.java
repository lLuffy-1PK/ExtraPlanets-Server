//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mjr.extraplanets.planets.Saturn.worldgen;

import com.mjr.extraplanets.Config;
import com.mjr.extraplanets.blocks.ExtraPlanets_Blocks;
import com.mjr.extraplanets.blocks.fluid.ExtraPlanets_Fluids;
import com.mjr.extraplanets.blocks.planetAndMoonBlocks.BlockBasicSaturn;
import com.mjr.extraplanets.blocks.planetAndMoonBlocks.BlockBasicSaturn.EnumBlockBasic;
import com.mjr.extraplanets.planets.Saturn.worldgen.biomes.BiomeGenSaturn;
import com.mjr.extraplanets.planets.Saturn.worldgen.biomes.BiomeGenSaturnNuclearLand;
import com.mjr.extraplanets.world.features.WorldGenNuclearPile;
import com.mjr.extraplanets.world.features.WorldGenSlimeTree;
import com.mjr.mjrlegendslib.util.WorldGenUtilities;
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

public class BiomeDecoratorSaturn extends BiomeDecoratorSpace {
    // Custom code
    private static final List<CustomOreGenerationData> customOreList = CustomOreGeneration.getOresForObject("Saturn");
    private final WorldGenerator iceGen;
    private final WorldGenerator gravelGen;
    private final WorldGenerator slimeGen;
    private final int LakesPerChunk = 5;
    private WorldGenerator tinGen;
    private WorldGenerator copperGen;
    private WorldGenerator ironGen;
    private WorldGenerator magnesiumGen;
    private World currentWorld;
    private boolean isDecorating = false;

    public BiomeDecoratorSaturn() {
        if (Config.GENERATE_ORES_SATURN) {
            this.copperGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.SATURN_BLOCKS, 4, 5, true, ExtraPlanets_Blocks.SATURN_BLOCKS, 2);
            this.tinGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.SATURN_BLOCKS, 4, 4, true, ExtraPlanets_Blocks.SATURN_BLOCKS, 2);
            this.ironGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.SATURN_BLOCKS, 8, 3, true, ExtraPlanets_Blocks.SATURN_BLOCKS, 2);
            this.magnesiumGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.SATURN_BLOCKS, 4, 6, true, ExtraPlanets_Blocks.SATURN_BLOCKS, 2);
        }
        this.slimeGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.SATURN_BLOCKS, 12, 12, true, ExtraPlanets_Blocks.SATURN_BLOCKS, 10);


        this.iceGen = new WorldGenMinableMeta(Blocks.ICE, 18, 0, true, ExtraPlanets_Blocks.SATURN_BLOCKS, 2);
        this.gravelGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.SATURN_GRAVEL, 12, 0, true, ExtraPlanets_Blocks.SATURN_BLOCKS, 2);
    }

    protected World getCurrentWorld() {
        return this.currentWorld;
    }

    protected void setCurrentWorld(World world) {
        this.currentWorld = world;
    }

    protected void decorate() {
        if (!this.isDecorating) {
            this.isDecorating = true;
            if (Config.GENERATE_ORES_SATURN) {
                this.generateOre(26, this.copperGen, 0, 60);
                this.generateOre(23, this.tinGen, 0, 60);
                this.generateOre(20, this.ironGen, 0, 64);
                this.generateOre(20, this.magnesiumGen, 0, 32);

            }
            this.generateOre(10, this.slimeGen, 60, 256);
            // Custom code
            if (customOreList != null) {
                for (CustomOreGenerationData ore : customOreList) {
                    int amountPerChunk = ore.amountPerChunk;
                    int minY = ore.minY;
                    int maxY = ore.maxY;
                    WorldGenerator blockGeneration = new WorldGenMinableMeta(ore.block, ore.clusterSize, ore.meta, ore.useMeta, ExtraPlanets_Blocks.SATURN_BLOCKS, 2);
                    this.generateOre(amountPerChunk, blockGeneration, minY, maxY);
                }
            }
            // Custom code

            this.generateOre(4, this.iceGen, 50, 120);
            this.generateOre(15, this.gravelGen, 0, 80);
            MinecraftForge.EVENT_BUS.post(new Pre(this.currentWorld, this.rand, new BlockPos(this.posX, 0, this.posZ)));
            int i;
            if (((BiomeAdaptive) this.getCurrentWorld().getBiome(new BlockPos(this.posX, 0, this.posZ))).isInstance(BiomeGenSaturn.class)) {
                for (i = 0; i < this.LakesPerChunk; ++i) {
                    if (this.rand.nextInt(10) == 0) {
                        WorldGenUtilities.generateLake(this.currentWorld, this.rand, new BlockPos(this.posX, 0, this.posZ), ExtraPlanets_Fluids.GLOWSTONE, ExtraPlanets_Blocks.SATURN_BLOCKS.getDefaultState().withProperty(BlockBasicSaturn.BASIC_TYPE, EnumBlockBasic.SURFACE));
                    }
                }
            }

            if (((BiomeAdaptive) this.getCurrentWorld().getBiome(new BlockPos(this.posX, 0, this.posZ))).isInstance(BiomeGenSaturnNuclearLand.class)) {
                for (i = 0; i < this.LakesPerChunk * 2; ++i) {
                    if (this.rand.nextInt(10) == 0) {
                        WorldGenUtilities.generateLake(this.currentWorld, this.rand, new BlockPos(this.posX, 0, this.posZ), ExtraPlanets_Fluids.METHANE, ExtraPlanets_Blocks.SATURN_BLOCKS.getDefaultState().withProperty(BlockBasicSaturn.BASIC_TYPE, EnumBlockBasic.BROKEN_INFECTED_STONE));
                    }
                }

                if (Config.GENERATE_SATURN_NUCLEAR_PILES && this.rand.nextInt(5) == 1) {
                    WorldGenUtilities.generateStructure(new WorldGenNuclearPile(), this.currentWorld, this.rand, new BlockPos(this.posX, 0, this.posZ));
                }

                if (Config.GENERATE_SATURN_SLIME_TREES && this.rand.nextInt(5) == 1) {
                    WorldGenUtilities.generateStructure(new WorldGenSlimeTree(), this.currentWorld, this.rand, new BlockPos(this.posX, 0, this.posZ));
                }
            }

            MinecraftForge.EVENT_BUS.post(new Post(this.currentWorld, this.rand, new BlockPos(this.posX, 0, this.posZ)));
            this.isDecorating = false;
        }
    }
}
