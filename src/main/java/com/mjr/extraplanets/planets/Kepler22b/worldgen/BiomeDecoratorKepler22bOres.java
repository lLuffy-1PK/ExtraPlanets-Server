//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mjr.extraplanets.planets.Kepler22b.worldgen;

import com.mjr.extraplanets.Config;
import com.mjr.extraplanets.blocks.ExtraPlanets_Blocks;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.world.gen.CustomOreGeneration;
import micdoodle8.mods.galacticraft.core.world.gen.CustomOreGenerationData;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.List;

public class BiomeDecoratorKepler22bOres extends BiomeDecoratorSpace {
    // Custom code
    private static final List<CustomOreGenerationData> customOreList = CustomOreGeneration.getOresForObject("Kepler22b");
    private final WorldGenerator blueDiamondOre;
    private final WorldGenerator redDiamondOre;
    private final WorldGenerator yellowDiamondOre;
    private final WorldGenerator purpleDiamondOre;
    private final WorldGenerator greenDiamondOre;
    private final WorldGenerator marbleGen;
    private WorldGenerator copperGen;
    private WorldGenerator tinGen;
    private WorldGenerator ironGen;
    private WorldGenerator denseCoal;
    private WorldGenerator platinumOre;
    private World currentWorld;
    private boolean isDecorating = false;

    public BiomeDecoratorKepler22bOres() {
        if (Config.GENERATE_ORES_KEPLER22B) {
            this.copperGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 4, 3, true, ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 1);
            this.tinGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 4, 4, true, ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 1);
            this.ironGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 8, 2, true, ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 1);
            this.denseCoal = new WorldGenMinableMeta(ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 8, 5, true, ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 1);
            this.platinumOre = new WorldGenMinableMeta(ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 3, 13, true, ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 1);
        }
        this.blueDiamondOre = new WorldGenMinableMeta(ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 8, 6, true, ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 1);
        this.redDiamondOre = new WorldGenMinableMeta(ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 8, 7, true, ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 1);
        this.yellowDiamondOre = new WorldGenMinableMeta(ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 8, 8, true, ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 1);
        this.purpleDiamondOre = new WorldGenMinableMeta(ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 8, 9, true, ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 1);
        this.greenDiamondOre = new WorldGenMinableMeta(ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 8, 10, true, ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 1);


        this.marbleGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.DECORATIVE_BLOCKS, 10, 0, true, ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 1);
    }

    protected World getCurrentWorld() {
        return this.currentWorld;
    }

    protected void setCurrentWorld(World world) {
        this.currentWorld = world;
    }

    public void decorate() {
        if (!this.isDecorating) {
            this.isDecorating = true;
            if (Config.GENERATE_ORES_KEPLER22B) {
                this.generateOre(26, this.copperGen, 0, 60);
                this.generateOre(23, this.tinGen, 0, 60);
                this.generateOre(20, this.ironGen, 0, 64);
                this.generateOre(4, this.denseCoal, 0, 25);
                this.generateOre(4, this.platinumOre, 0, 10);
            }

            this.generateOre(4, this.blueDiamondOre, 0, 10);
            this.generateOre(4, this.redDiamondOre, 0, 10);
            this.generateOre(4, this.yellowDiamondOre, 0, 10);
            this.generateOre(4, this.purpleDiamondOre, 0, 10);
            this.generateOre(4, this.greenDiamondOre, 0, 10);


            // Custom code
            if (customOreList != null) {
                for (CustomOreGenerationData ore : customOreList) {
                    int amountPerChunk = ore.amountPerChunk;
                    int minY = ore.minY;
                    int maxY = ore.maxY;
                    WorldGenerator blockGeneration = new WorldGenMinableMeta(ore.block, ore.clusterSize, ore.meta, ore.useMeta, ExtraPlanets_Blocks.KEPLER22B_BLOCKS, 1);
                    this.generateOre(amountPerChunk, blockGeneration, minY, maxY);
                }
            }
            // Custom code

            this.generateOre(20, this.marbleGen, 0, 30);
            this.isDecorating = false;
        }
    }
}
