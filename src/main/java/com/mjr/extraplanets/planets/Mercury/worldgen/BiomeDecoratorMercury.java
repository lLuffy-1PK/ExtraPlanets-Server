//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mjr.extraplanets.planets.Mercury.worldgen;

import com.mjr.extraplanets.Config;
import com.mjr.extraplanets.blocks.ExtraPlanets_Blocks;
import com.mjr.extraplanets.blocks.fluid.ExtraPlanets_Fluids;
import com.mjr.mjrlegendslib.util.WorldGenUtilities;
import com.mjr.mjrlegendslib.world.features.WorldGenSphere;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.blocks.BlockBasic;
import micdoodle8.mods.galacticraft.core.blocks.BlockBasic.EnumBlockBasic;
import micdoodle8.mods.galacticraft.core.world.gen.CustomOreGeneration;
import micdoodle8.mods.galacticraft.core.world.gen.CustomOreGenerationData;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import micdoodle8.mods.galacticraft.planets.mars.blocks.BlockBasicMars;
import micdoodle8.mods.galacticraft.planets.mars.blocks.MarsBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Post;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Pre;

import java.util.List;

public class BiomeDecoratorMercury extends BiomeDecoratorSpace {
    // Custom code
    private static final List<CustomOreGenerationData> customOreList = CustomOreGeneration.getOresForObject("Mercury");
    private final WorldGenerator gravelGen;
    private final WorldGenerator fossilsGen;
    private WorldGenerator tinGen;
    private WorldGenerator copperGen;
    private WorldGenerator ironGen;
    private WorldGenerator mercuryGen;
    private WorldGenerator carbonGen;
    private WorldGenerator potashGen;
    private World currentWorld;
    private boolean isDecorating = false;

    public BiomeDecoratorMercury() {
        if (Config.GENERATE_ORES_MERCURY) {
            this.copperGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.MERCURY_BLOCKS, 4, 5, true, ExtraPlanets_Blocks.MERCURY_BLOCKS, 2);
            this.tinGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.MERCURY_BLOCKS, 4, 4, true, ExtraPlanets_Blocks.MERCURY_BLOCKS, 2);
            this.ironGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.MERCURY_BLOCKS, 8, 3, true, ExtraPlanets_Blocks.MERCURY_BLOCKS, 2);
            this.mercuryGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.MERCURY_BLOCKS, 4, 6, true, ExtraPlanets_Blocks.MERCURY_BLOCKS, 2);
            this.carbonGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.MERCURY_BLOCKS, 4, 10, true, ExtraPlanets_Blocks.MERCURY_BLOCKS, 2);
        }

        this.gravelGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.MERCURY_GRAVEL, 12, 0, true, ExtraPlanets_Blocks.MERCURY_BLOCKS, 2);
        this.fossilsGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.FOSSIL, 3, 0, true, ExtraPlanets_Blocks.MERCURY_BLOCKS, 1);
        if (Config.RADIATION) {
            this.potashGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.ORE_POTASH, 5, 0, true, ExtraPlanets_Blocks.MERCURY_BLOCKS, 2);
        }

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
            if (Config.GENERATE_ORES_MERCURY) {
                this.generateOre(26, this.copperGen, 0, 60);
                this.generateOre(23, this.tinGen, 0, 60);
                this.generateOre(20, this.ironGen, 0, 64);
                this.generateOre(20, this.mercuryGen, 0, 64);
                this.generateOre(20, this.carbonGen, 0, 64);
            }

            // Custom code
            if (customOreList != null) {
                for (CustomOreGenerationData ore : customOreList) {
                    int amountPerChunk = ore.amountPerChunk;
                    int minY = ore.minY;
                    int maxY = ore.maxY;
                    WorldGenerator blockGeneration = new WorldGenMinableMeta(ore.block, ore.clusterSize, ore.meta, ore.useMeta, ExtraPlanets_Blocks.MERCURY_BLOCKS, 2);
                    this.generateOre(amountPerChunk, blockGeneration, minY, maxY);
                }
            }
            // Custom code

            this.generateOre(15, this.gravelGen, 0, 80);
            this.generateOre(10, this.fossilsGen, 0, 256);
            if (Config.RADIATION) {
                this.generateOre(10, this.potashGen, 0, 20);
            }

            MinecraftForge.EVENT_BUS.post(new Pre(this.currentWorld, this.rand, new BlockPos(this.posX, 0, this.posZ)));

            int i;
            for (i = 0; i < 1; ++i) {
                if (this.rand.nextInt(100) == 0) {
                    WorldGenUtilities.generateLake(this.currentWorld, this.rand, new BlockPos(this.posX, 0, this.posZ), ExtraPlanets_Fluids.INFECTED_WATER, ExtraPlanets_Blocks.MERCURY_BLOCKS);
                }
            }

            if (Config.GENERATE_MERCURY_METEORS) {
                for (i = 0; i < 1; ++i) {
                    if (this.rand.nextInt(25) == 1) {
                        WorldGenUtilities.generateStructureWithRandom(new WorldGenSphere(true, "extraplanets", GCBlocks.basicBlock.getDefaultState().withProperty(BlockBasic.BASIC_TYPE, EnumBlockBasic.DECO_BLOCK_METEOR_IRON), 8, 2), this.currentWorld, this.rand, new BlockPos(this.posX, 0, this.posZ), 6);
                    }
                }

                for (i = 0; i < 1; ++i) {
                    if (this.rand.nextInt(25) == 1) {
                        WorldGenUtilities.generateStructureWithRandom(new WorldGenSphere(true, "extraplanets", MarsBlocks.marsBlock.getDefaultState().withProperty(BlockBasicMars.BASIC_TYPE, micdoodle8.mods.galacticraft.planets.mars.blocks.BlockBasicMars.EnumBlockBasic.DESH_BLOCK), 8, 2), this.currentWorld, this.rand, new BlockPos(this.posX, 0, this.posZ), 6);
                    }
                }
            }

            MinecraftForge.EVENT_BUS.post(new Post(this.currentWorld, this.rand, new BlockPos(this.posX, 0, this.posZ)));
            this.isDecorating = false;
        }
    }
}
