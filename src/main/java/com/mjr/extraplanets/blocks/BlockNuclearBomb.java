//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mjr.extraplanets.blocks;

import com.mjr.mjrlegendslib.block.BlockBasicExplosion;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BlockNuclearBomb extends BlockBasicExplosion {
    public BlockNuclearBomb() {
    }

    public void onBlockDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
        //if (!world.isRemote) {
        //    EntityNuclearBombPrimed EntityNuclearBombPrimed = new EntityNuclearBombPrimed(world, (double)((float)pos.getX() + 0.5F), (double)pos.getY(), (double)((float)pos.getZ() + 0.5F), explosion.getExplosivePlacedBy());
        //    EntityNuclearBombPrimed.setFuse(world.rand.nextInt(EntityNuclearBombPrimed.getFuse() / 4) + EntityNuclearBombPrimed.getFuse() / 8);
        //    world.spawnEntity(EntityNuclearBombPrimed);
        //}

    }

    public void explode(World world, BlockPos pos, IBlockState state, EntityLivingBase igniter) {
        //if (!world.isRemote && (Boolean)state.getValue(EXPLODE)) {
        //    EntityNuclearBombPrimed EntityNuclearBombPrimed = new EntityNuclearBombPrimed(world, (double)((float)pos.getX() + 0.5F), (double)pos.getY(), (double)((float)pos.getZ() + 0.5F), igniter);
        //    world.spawnEntity(EntityNuclearBombPrimed);
        //    world.playSound((EntityPlayer)null, EntityNuclearBombPrimed.posX, EntityNuclearBombPrimed.posY, EntityNuclearBombPrimed.posZ, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
        //}

    }
}
