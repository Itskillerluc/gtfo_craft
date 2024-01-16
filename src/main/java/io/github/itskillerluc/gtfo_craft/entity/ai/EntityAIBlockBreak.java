package io.github.itskillerluc.gtfo_craft.entity.ai;

import io.github.itskillerluc.gtfo_craft.network.BreakDoorPacket;
import io.github.itskillerluc.gtfo_craft.network.PacketHandler;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;

import java.util.function.Predicate;

public class EntityAIBlockBreak extends EntityAIInteractBlock {
    private int breakingTime;
    private int previousBreakProgress = -1;
    private final Predicate<EntityAIBlockBreak> predicate;

    public EntityAIBlockBreak(EntityLiving entityIn, Predicate<EntityAIBlockBreak> predicate) {
        super(entityIn);
        this.predicate = predicate;
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
                    this.blockPosition = new BlockPos(pathpoint.x, pathpoint.y + 1, pathpoint.z).offset(entity.getHorizontalFacing());

                    if (this.entity.getDistanceSq(this.blockPosition.getX(), this.entity.posY, (double)this.blockPosition.getZ()) <= 3D)
                    {
                        this.block = this.getBlock(this.blockPosition);

                        if (predicate.test(this)) {
                            return true;
                        }
                    }
                }


                this.blockPosition = (new BlockPos(this.entity)).up();
                this.block = this.getBlock(this.blockPosition);
                return predicate.test(this);
            }
            else
            {
                return false;
            }
        }
    }

    public void startExecuting() {
        super.startExecuting();
        this.breakingTime = 0;
    }

    public boolean shouldContinueExecuting() {
        double d0 = this.entity.getDistanceSq(this.blockPosition);
        if (this.breakingTime <= 240) {

            return predicate.test(this) && d0 < 4.0D;
        }
        return false;
    }

    public void resetTask()
    {
        super.resetTask();
        this.entity.world.sendBlockBreakProgress(this.entity.getEntityId(), this.blockPosition, -1);
    }

    public void updateTask()
    {
        super.updateTask();

        if (this.entity.getRNG().nextInt(20) == 0)
        {
            this.entity.world.playEvent(1019, this.blockPosition, 0);
        }

        ++this.breakingTime;
        int i = (int)((float)this.breakingTime / 240.0F * 10.0F);

        if (i != this.previousBreakProgress)
        {
            this.entity.world.sendBlockBreakProgress(this.entity.getEntityId(), this.blockPosition, i);
            this.previousBreakProgress = i;
        }

        if (this.breakingTime == 240)
        {

            PacketHandler.sendToServer(new BreakDoorPacket(blockPosition));


            this.entity.world.playEvent(1021, this.blockPosition, 0);
            this.entity.world.playEvent(2001, this.blockPosition, Block.getIdFromBlock(this.block));
        }
    }

    public Block getBlock() {
        return this.block;
    }

    private Block getBlock(BlockPos pos)
    {
        IBlockState iblockstate = this.entity.world.getBlockState(pos);
        return iblockstate.getBlock();
    }
}
