package io.github.itskillerluc.gtfo_craft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;

public class EntityAIInteractBlock extends EntityAIBase {
    protected EntityLiving entity;
    protected BlockPos blockPosition = BlockPos.ORIGIN;
    protected Block block;
    boolean hasStoppedBlockInteraction;
    float entityPositionX;
    float entityPositionZ;

    public EntityAIInteractBlock(EntityLiving entityIn)
    {
        this.entity = entityIn;

        if (!(entityIn.getNavigator() instanceof PathNavigateGround))
        {
            throw new IllegalArgumentException("Unsupported mob type for DoorInteractGoal");
        }
    }

    public boolean shouldExecute()
    {
        if (!this.entity.collidedHorizontally)
        {
            return false;
        }
        else
        {
            PathNavigateGround pathnavigateground = (PathNavigateGround)this.entity.getNavigator();
            Path path = pathnavigateground.getPath();

            if (path != null && !path.isFinished() && pathnavigateground.getEnterDoors())
            {
                for (int i = 0; i < Math.min(path.getCurrentPathIndex() + 2, path.getCurrentPathLength()); ++i)
                {
                    PathPoint pathpoint = path.getPathPointFromIndex(i);
                    this.blockPosition = new BlockPos(pathpoint.x, pathpoint.y + 1, pathpoint.z - 1);

                    if (this.entity.getDistanceSq(this.blockPosition.getX(), this.entity.posY, (double)this.blockPosition.getZ()) <= 3D)
                    {
                        this.block = this.getBlock(this.blockPosition);

                        return true;
                    }
                }

                this.blockPosition = (new BlockPos(this.entity)).up();
                this.block = this.getBlock(this.blockPosition);
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    public boolean shouldContinueExecuting()
    {
        return !this.hasStoppedBlockInteraction;
    }

    public void startExecuting()
    {
        this.hasStoppedBlockInteraction = false;
        this.entityPositionX = (float)((double)((float)this.blockPosition.getX() + 0.5F) - this.entity.posX);
        this.entityPositionZ = (float)((double)((float)this.blockPosition.getZ() + 0.5F) - this.entity.posZ);
    }

    public void updateTask()
    {
        float f = (float)((double)((float)this.blockPosition.getX() + 0.5F) - this.entity.posX);
        float f1 = (float)((double)((float)this.blockPosition.getZ() + 0.5F) - this.entity.posZ);
        float f2 = this.entityPositionX * f + this.entityPositionZ * f1;

        if (f2 < 0.0F)
        {
            this.hasStoppedBlockInteraction = true;
        }
    }

    private Block getBlock(BlockPos pos)
    {
        IBlockState iblockstate = this.entity.world.getBlockState(pos);
        return iblockstate.getBlock();
    }
}
