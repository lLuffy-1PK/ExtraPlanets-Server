//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mjr.extraplanets.planets.Ceres.worldgen;

import com.mjr.extraplanets.Config;
import com.mjr.extraplanets.blocks.ExtraPlanets_Blocks;
import com.mjr.extraplanets.blocks.fluid.ExtraPlanets_Fluids;
import com.mjr.extraplanets.world.features.WorldGenSatelliteTower;
import com.mjr.mjrlegendslib.util.WorldGenUtilities;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.world.gen.CustomOreGeneration;
import micdoodle8.mods.galacticraft.core.world.gen.CustomOreGenerationData;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Post;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Pre;

import java.util.List;

public class BiomeDecoratorCeres extends BiomeDecoratorSpace {
    // Custom code
    private static final List<CustomOreGenerationData> customOreList = CustomOreGeneration.getOresForObject("Ceres");
    private final WorldGenerator gravelGen;
    private final WorldGenerator fossilsGen;
    private final int LakesPerChunk = 5;
    private WorldGenerator copperGen;
    private WorldGenerator tinGen;
    private WorldGenerator ironGen;
    private WorldGenerator uraniumGen;
    private World currentWorld;
    private boolean isDecorating = false;

    public BiomeDecoratorCeres() {
        if (Config.GENERATE_ORES_CERES) {
            this.copperGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.CERES_BLOCKS, 4, 5, true, ExtraPlanets_Blocks.CERES_BLOCKS, 2);
            this.tinGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.CERES_BLOCKS, 4, 4, true, ExtraPlanets_Blocks.CERES_BLOCKS, 2);
            this.ironGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.CERES_BLOCKS, 8, 3, true, ExtraPlanets_Blocks.CERES_BLOCKS, 2);
            this.uraniumGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.CERES_BLOCKS, 3, 6, true, ExtraPlanets_Blocks.CERES_BLOCKS, 2);
        }

        this.gravelGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.CERES_GRAVEL, 12, 0, true, ExtraPlanets_Blocks.CERES_BLOCKS, 2);
        this.fossilsGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.FOSSIL, 3, 0, true, ExtraPlanets_Blocks.CERES_BLOCKS, 1);
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
            if (Config.GENERATE_ORES_CERES) {
                this.generateOre(26, this.copperGen, 0, 60);
                this.generateOre(23, this.tinGen, 0, 60);
                this.generateOre(20, this.ironGen, 0, 64);
                this.generateOre(10, this.uraniumGen, 0, 64);
            }

            // Custom code
            if (customOreList != null) {
                for (CustomOreGenerationData ore : customOreList) {
                    int amountPerChunk = ore.amountPerChunk;
                    int minY = ore.minY;
                    int maxY = ore.maxY;
                    WorldGenerator blockGeneration = new WorldGenMinableMeta(ore.block, ore.clusterSize, ore.meta, ore.useMeta, ExtraPlanets_Blocks.CERES_BLOCKS, 2);
                    this.generateOre(amountPerChunk, blockGeneration, minY, maxY);
                }
            }
            // Custom code

            this.generateOre(15, this.gravelGen, 0, 80);
            this.generateOre(10, this.fossilsGen, 0, 256);
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

            if (Config.GENERATE_CERES_SATELLITE_TOWER && this.rand.nextInt(300) == 1) {
                WorldGenUtilities.generateStructure(new WorldGenSatelliteTower(), this.currentWorld, this.rand, new BlockPos(this.posX, 0, this.posZ));
            }

            MinecraftForge.EVENT_BUS.post(new Post(this.currentWorld, this.rand, new BlockPos(this.posX, 0, this.posZ)));
            this.isDecorating = false;
        }
    }
}
