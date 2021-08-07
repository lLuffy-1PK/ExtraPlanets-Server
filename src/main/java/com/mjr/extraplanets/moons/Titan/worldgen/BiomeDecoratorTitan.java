//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mjr.extraplanets.moons.Titan.worldgen;

import com.mjr.extraplanets.Config;
import com.mjr.extraplanets.blocks.ExtraPlanets_Blocks;
import com.mjr.extraplanets.blocks.fluid.ExtraPlanets_Fluids;
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

public class BiomeDecoratorTitan extends BiomeDecoratorSpace {
    // Custom code
    private static final List<CustomOreGenerationData> customOreList = CustomOreGeneration.getOresForObject("Titan");
    private final WorldGenerator gravelGen;
    private final WorldGenerator fossilsGen;
    private final WorldGenerator rocksGen;
    private final int LakesPerChunk = 5;
    private WorldGenerator copperGen;
    private WorldGenerator tinGen;
    private WorldGenerator ironGen;
    private World currentWorld;

    public BiomeDecoratorTitan() {
        if (Config.GENERATE_ORES_TITAN) {
            this.copperGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.TITAN_BLOCKS, 4, 5, true, ExtraPlanets_Blocks.TITAN_BLOCKS, 2);
            this.tinGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.TITAN_BLOCKS, 4, 4, true, ExtraPlanets_Blocks.TITAN_BLOCKS, 2);
            this.ironGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.TITAN_BLOCKS, 8, 3, true, ExtraPlanets_Blocks.TITAN_BLOCKS, 2);
        }

        this.gravelGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.TITAN_GRAVEL, 12, 0, true, ExtraPlanets_Blocks.TITAN_BLOCKS, 2);
        this.fossilsGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.FOSSIL, 3, 0, true, ExtraPlanets_Blocks.TITAN_BLOCKS, 1);
        this.rocksGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.TITAN_BLOCKS, 12, 8, true, ExtraPlanets_Blocks.TITAN_BLOCKS, 0);
    }

    protected World getCurrentWorld() {
        return this.currentWorld;
    }

    protected void setCurrentWorld(World world) {
        this.currentWorld = world;
    }

    protected void decorate() {
        if (Config.GENERATE_ORES_TITAN) {
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
                WorldGenerator blockGeneration = new WorldGenMinableMeta(ore.block, ore.clusterSize, ore.meta, ore.useMeta, ExtraPlanets_Blocks.TITAN_BLOCKS, 2);
                this.generateOre(amountPerChunk, blockGeneration, minY, maxY);
            }
        }
        // Custom code

        this.generateOre(15, this.gravelGen, 0, 80);
        this.generateOre(10, this.fossilsGen, 0, 256);
        this.generateOre(60, this.rocksGen, 40, 120);
        MinecraftForge.EVENT_BUS.post(new Pre(this.currentWorld, this.rand, new BlockPos(this.posX, 0, this.posZ)));

        for (int i = 0; i < this.LakesPerChunk; ++i) {
            if (this.rand.nextInt(10) == 0) {
                WorldGenUtilities.generateLake(this.currentWorld, this.rand, new BlockPos(this.posX, 0, this.posZ), ExtraPlanets_Fluids.METHANE, ExtraPlanets_Blocks.FROZEN_NITROGEN);
            }
        }

        MinecraftForge.EVENT_BUS.post(new Post(this.currentWorld, this.rand, new BlockPos(this.posX, 0, this.posZ)));
    }
}
