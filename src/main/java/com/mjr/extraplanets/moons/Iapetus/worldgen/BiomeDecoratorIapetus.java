//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mjr.extraplanets.moons.Iapetus.worldgen;

import com.mjr.extraplanets.Config;
import com.mjr.extraplanets.blocks.ExtraPlanets_Blocks;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.world.gen.CustomOreGeneration;
import micdoodle8.mods.galacticraft.core.world.gen.CustomOreGenerationData;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.List;

public class BiomeDecoratorIapetus extends BiomeDecoratorSpace {
    // Custom code
    private static final List<CustomOreGenerationData> customOreList = CustomOreGeneration.getOresForObject("Iapetus");
    private final WorldGenerator gravelGen;
    private final WorldGenerator fossilsGen;
    private final WorldGenerator iceGen;
    private WorldGenerator copperGen;
    private WorldGenerator tinGen;
    private WorldGenerator ironGen;
    private World currentWorld;

    public BiomeDecoratorIapetus() {
        if (Config.GENERATE_ORES_IAPETUS) {
            this.copperGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.IAPETUS_BLOCKS, 4, 5, true, ExtraPlanets_Blocks.IAPETUS_BLOCKS, 2);
            this.tinGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.IAPETUS_BLOCKS, 4, 4, true, ExtraPlanets_Blocks.IAPETUS_BLOCKS, 2);
            this.ironGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.IAPETUS_BLOCKS, 8, 3, true, ExtraPlanets_Blocks.IAPETUS_BLOCKS, 2);
        }

        this.gravelGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.IAPETUS_GRAVEL, 12, 0, true, ExtraPlanets_Blocks.IAPETUS_BLOCKS, 2);
        this.fossilsGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.FOSSIL, 3, 0, true, ExtraPlanets_Blocks.IAPETUS_BLOCKS, 1);
        this.iceGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.IAPETUS_BLOCKS, 20, 6, true, ExtraPlanets_Blocks.IAPETUS_BLOCKS, 0);
    }

    protected World getCurrentWorld() {
        return this.currentWorld;
    }

    protected void setCurrentWorld(World world) {
        this.currentWorld = world;
    }

    protected void decorate() {
        if (Config.GENERATE_ORES_IAPETUS) {
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
                WorldGenerator blockGeneration = new WorldGenMinableMeta(ore.block, ore.clusterSize, ore.meta, ore.useMeta, ExtraPlanets_Blocks.IAPETUS_BLOCKS, 2);
                this.generateOre(amountPerChunk, blockGeneration, minY, maxY);
            }
        }
        // Custom code

        this.generateOre(15, this.gravelGen, 0, 80);
        this.generateOre(10, this.fossilsGen, 0, 256);
        this.generateOre(90, this.iceGen, 0, 256);
    }
}
