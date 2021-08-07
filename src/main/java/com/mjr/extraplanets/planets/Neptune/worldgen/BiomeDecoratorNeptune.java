//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mjr.extraplanets.planets.Neptune.worldgen;

import com.mjr.extraplanets.Config;
import com.mjr.extraplanets.blocks.ExtraPlanets_Blocks;
import com.mjr.extraplanets.blocks.fluid.ExtraPlanets_Fluids;
import com.mjr.extraplanets.world.features.WorldGenFrozenNitrogenPile;
import com.mjr.mjrlegendslib.util.WorldGenUtilities;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.world.gen.CustomOreGeneration;
import micdoodle8.mods.galacticraft.core.world.gen.CustomOreGenerationData;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.List;

public class BiomeDecoratorNeptune extends BiomeDecoratorSpace {
    // Custom code
    private static final List<CustomOreGenerationData> customOreList = CustomOreGeneration.getOresForObject("Neptune");
    private final WorldGenerator frozenNitrogenGen;
    private final int LakesPerChunk = 5;
    private WorldGenerator copperGen;
    private WorldGenerator tinGen;
    private WorldGenerator ironGen;
    private WorldGenerator zincGen;
    private WorldGenerator blueGemGen;
    private World currentWorld;
    private boolean isDecorating = false;

    public BiomeDecoratorNeptune() {
        if (Config.GENERATE_ORES_NEPTUNE) {
            this.copperGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.NEPTUNE_BLOCKS, 4, 5, true, ExtraPlanets_Blocks.NEPTUNE_BLOCKS, 2);
            this.tinGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.NEPTUNE_BLOCKS, 4, 4, true, ExtraPlanets_Blocks.NEPTUNE_BLOCKS, 2);
            this.ironGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.NEPTUNE_BLOCKS, 8, 3, true, ExtraPlanets_Blocks.NEPTUNE_BLOCKS, 2);
            this.zincGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.NEPTUNE_BLOCKS, 4, 6, true, ExtraPlanets_Blocks.NEPTUNE_BLOCKS, 2);
            this.blueGemGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.NEPTUNE_BLOCKS, 4, 10, true, ExtraPlanets_Blocks.NEPTUNE_BLOCKS, 2);
        }

        this.frozenNitrogenGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.FROZEN_NITROGEN, 8, 0, true, ExtraPlanets_Blocks.NEPTUNE_BLOCKS, 0);
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
            if (Config.GENERATE_ORES_NEPTUNE) {
                this.generateOre(26, this.copperGen, 0, 60);
                this.generateOre(23, this.tinGen, 0, 60);
                this.generateOre(20, this.ironGen, 0, 64);
                this.generateOre(20, this.zincGen, 0, 32);
                this.generateOre(10, this.blueGemGen, 0, 10);
            }

            // Custom code
            if (customOreList != null) {
                for (CustomOreGenerationData ore : customOreList) {
                    int amountPerChunk = ore.amountPerChunk;
                    int minY = ore.minY;
                    int maxY = ore.maxY;
                    WorldGenerator blockGeneration = new WorldGenMinableMeta(ore.block, ore.clusterSize, ore.meta, ore.useMeta, ExtraPlanets_Blocks.NEPTUNE_BLOCKS, 2);
                    this.generateOre(amountPerChunk, blockGeneration, minY, maxY);
                }
            }
            // Custom code

            this.generateOre(5, this.frozenNitrogenGen, 0, 256);

            for (int i = 0; i < this.LakesPerChunk; ++i) {
                if (this.rand.nextInt(10) == 0) {
                    WorldGenUtilities.generateLake(this.currentWorld, this.rand, new BlockPos(this.posX, 0, this.posZ), ExtraPlanets_Fluids.NITROGEN, ExtraPlanets_Blocks.NEPTUNE_BLOCKS);
                }
            }

            if (Config.GENERATE_NEPTUNE_FROZEN_NITROGEN_PILES && this.rand.nextInt(20) == 1) {
                WorldGenUtilities.generateStructure(new WorldGenFrozenNitrogenPile(), this.currentWorld, this.rand, new BlockPos(this.posX, 0, this.posZ));
            }

            this.isDecorating = false;
        }
    }
}
