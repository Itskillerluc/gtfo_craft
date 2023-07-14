package io.github.itskillerluc.gtfo_craft.entity;

import io.github.itskillerluc.gtfo_craft.registry.BlockRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityFogRepeller extends EntityThrowable {
    public static final int DIAMETER = 3;

    public EntityFogRepeller(World worldIn) {
        super(worldIn);
    }

    public EntityFogRepeller(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    public EntityFogRepeller(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.typeOfHit != RayTraceResult.Type.MISS){
            BlockPos pos = result.getBlockPos();
            if (pos == null) {
                pos = result.entityHit.getPosition();
            }
            BlockPos startPos = null;
            if (result.typeOfHit == RayTraceResult.Type.ENTITY) {
                startPos = pos.north((DIAMETER - 1) / 2).east((DIAMETER - 1) / 2).down((DIAMETER - 1) / 2);
            } else {
                switch (result.sideHit) {
                    case DOWN:
                    case UP:
                        startPos = pos.north((DIAMETER - 1) / 2).east((DIAMETER - 1) / 2);
                        break;
                    case NORTH:
                    case SOUTH:
                        startPos = pos.down((DIAMETER - 1) / 2).east((DIAMETER - 1) / 2);
                        break;
                    case WEST:
                    case EAST:
                        startPos = pos.north((DIAMETER - 1) / 2).down((DIAMETER - 1) / 2);
                        break;
                }
            }
            for (int y = 0; y <= DIAMETER; y++) {
                for (int z = 0; z <= DIAMETER; z++) {
                    for (int x = 0; x <= DIAMETER; x++) {
                        BlockPos blockToReplace = startPos.south(x).west(z).up(y);
                        if (world.getBlockState(blockToReplace).getBlock().equals(BlockRegistry.FOG)) {
                            world.setBlockState(blockToReplace, BlockRegistry.EMPTY_FOG.getDefaultState());
                        }
                    }
                }
            }
        }
        if (!this.world.isRemote)
        {
            this.world.setEntityState(this, (byte)3);
            this.setDead();
        }
    }
}
