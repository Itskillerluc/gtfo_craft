package io.github.itskillerluc.gtfo_craft.entity;

import io.github.itskillerluc.gtfo_craft.registry.BlockRegistry;
import io.github.itskillerluc.gtfo_craft.registry.ItemRegistry;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityFog;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;

public class EntityFogRepeller extends EntityArrow {
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
    public void setDead() {
        super.setDead();
    }

    @Override
    protected void onHit(RayTraceResult result) {
        if (result.typeOfHit != RayTraceResult.Type.MISS) {
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
            BlockPos blockpos = result.getBlockPos();

            this.motionX = (double)((float)(result.hitVec.x - this.posX));
            this.motionY = (double)((float)(result.hitVec.y - this.posY));
            this.motionZ = (double)((float)(result.hitVec.z - this.posZ));
            float f2 = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
            this.posX -= this.motionX / (double)f2 * 0.05000000074505806D;
            this.posY -= this.motionY / (double)f2 * 0.05000000074505806D;
            this.posZ -= this.motionZ / (double)f2 * 0.05000000074505806D;
            this.inGround = true;
            this.arrowShake = 7;
            this.setIsCritical(false);
            pickupStatus = PickupStatus.DISALLOWED;
        }
    }
    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(ItemRegistry.FOG_REPELLER);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.timeInGround >= TileEntityFog.TOTAL_TIME) {
            this.setDead();
        }
    }
}
