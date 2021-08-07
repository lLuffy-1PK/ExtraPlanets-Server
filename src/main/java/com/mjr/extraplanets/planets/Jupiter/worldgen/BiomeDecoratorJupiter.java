//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mjr.extraplanets.planets.Jupiter.worldgen;

import com.mjr.extraplanets.Config;
import com.mjr.extraplanets.blocks.ExtraPlanets_Blocks;
import com.mjr.extraplanets.blocks.fluid.ExtraPlanets_Fluids;
import com.mjr.extraplanets.world.features.WorldGenBasicHideout;
import com.mjr.extraplanets.world.features.WorldGenSpaceShip;
import com.mjr.mjrlegendslib.util.WorldGenUtilities;
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

public class BiomeDecoratorJupiter extends BiomeDecoratorSpace {
    // Custom code
    private static final List<CustomOreGenerationData> customOreList = CustomOreGeneration.getOresForObject("Jupiter");
    private final WorldGenerator gravelGen;
    private final WorldGenerator ashRockGen;
    private final WorldGenerator volcanicRockGen;
    private final int LakesPerChunk = 2;
    private WorldGenerator tinGen;
    private WorldGenerator copperGen;
    private WorldGenerator ironGen;
    private WorldGenerator palladiumGen;
    private WorldGenerator nickelGen;
    private WorldGenerator redGemGen;
    private WorldGenerator skyBlocksGen;
    private World currentWorld;
    private boolean isDecorating = false;

    public BiomeDecoratorJupiter() {
        if (Config.GENERATE_ORES_JUPITER) {
            this.copperGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.JUPITER_BLOCKS, 4, 5, true, ExtraPlanets_Blocks.JUPITER_BLOCKS, 2);
            this.tinGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.JUPITER_BLOCKS, 4, 4, true, ExtraPlanets_Blocks.JUPITER_BLOCKS, 2);
            this.ironGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.JUPITER_BLOCKS, 8, 3, true, ExtraPlanets_Blocks.JUPITER_BLOCKS, 2);
            this.palladiumGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.JUPITER_BLOCKS, 4, 6, true, ExtraPlanets_Blocks.JUPITER_BLOCKS, 2);
            this.nickelGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.JUPITER_BLOCKS, 4, 7, true, ExtraPlanets_Blocks.JUPITER_BLOCKS, 2);
            this.redGemGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.JUPITER_BLOCKS, 4, 11, true, ExtraPlanets_Blocks.JUPITER_BLOCKS, 2);
        }

        this.gravelGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.JUPITER_GRAVEL, 12, 0, true, ExtraPlanets_Blocks.JUPITER_BLOCKS, 2);
        this.ashRockGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.ASH_ROCK, 5, 0, true, ExtraPlanets_Blocks.JUPITER_BLOCKS, 1);
        this.volcanicRockGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.VOLCANIC_ROCK, 5, 0, true, ExtraPlanets_Blocks.JUPITER_BLOCKS, 1);
        if (Config.GENERATE_JUITPER_SKY_FEATURE) {
            this.skyBlocksGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.JUPITER_BLOCKS, 3, 2, false, Blocks.AIR, 0);
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
            if (Config.GENERATE_ORES_JUPITER) {
                this.generateOre(26, this.copperGen, 0, 60);
                this.generateOre(23, this.tinGen, 0, 60);
                this.generateOre(20, this.ironGen, 0, 64);
                this.generateOre(20, this.palladiumGen, 0, 32);
                this.generateOre(20, this.nickelGen, 32, 40);
                this.generateOre(10, this.redGemGen, 0, 10);
            }

            // Custom code
            if (customOreList != null) {
                for (CustomOreGenerationData ore : customOreList) {
                    int amountPerChunk = ore.amountPerChunk;
                    int minY = ore.minY;
                    int maxY = ore.maxY;
                    WorldGenerator blockGeneration = new WorldGenMinableMeta(ore.block, ore.clusterSize, ore.meta, ore.useMeta, ExtraPlanets_Blocks.JUPITER_BLOCKS, 2);
                    this.generateOre(amountPerChunk, blockGeneration, minY, maxY);
                }
            }
            // Custom code

            this.generateOre(15, this.gravelGen, 0, 80);
            this.generateOre(10, this.ashRockGen, 0, 256);
            this.generateOre(10, this.volcanicRockGen, 0, 256);
            if (Config.GENERATE_JUITPER_SKY_FEATURE) {
                this.generateOre(5, this.skyBlocksGen, 63, 256);
            }

            MinecraftForge.EVENT_BUS.post(new Pre(this.currentWorld, this.rand, new BlockPos(this.posX, 0, this.posZ)));

            for (int i = 0; i < this.LakesPerChunk; ++i) {
                if (this.rand.nextInt(10) == 0) {
                    WorldGenUtilities.generateLake(this.currentWorld, this.rand, new BlockPos(this.posX, 0, this.posZ), ExtraPlanets_Fluids.MAGMA, ExtraPlanets_Blocks.JUPITER_BLOCKS);
                }
            }

            if (Config.GENERATE_JUPITER_BASIC_HIDEOUTS && this.rand.nextInt(250) == 1) {
                WorldGenUtilities.generateStructure(new WorldGenBasicHideout(), this.currentWorld, this.rand, new BlockPos(this.posX, 0, this.posZ));
            }

            if (Config.GENERATE_JUPITER_SPACE_SHIP && this.rand.nextInt(175) == 1) {
                WorldGenUtilities.generateStructure(new WorldGenSpaceShip(), this.currentWorld, this.rand, new BlockPos(this.posX, 0, this.posZ));
            }

            MinecraftForge.EVENT_BUS.post(new Post(this.currentWorld, this.rand, new BlockPos(this.posX, 0, this.posZ)));
            this.isDecorating = false;
        }
    }
}
