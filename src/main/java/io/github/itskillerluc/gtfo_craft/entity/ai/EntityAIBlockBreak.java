package io.github.itskillerluc.gtfo_craft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.EnumDifficulty;

import java.util.function.Predicate;

public class EntityAIBlockBreak extends EntityAIInteractBlock {
    private int breakingTime;
    private int previousBreakProgress = -1;
    private final Predicate<EntityAIBlockBreak> predicate;

    public EntityAIBlockBreak(EntityLiving entityIn, Predicate<EntityAIBlockBreak> predicate) {
        super(entityIn);
        this.predicate = predicate;
    }

    public boolean shouldExecute() {
        return super.shouldExecute() && predicate.test(this);
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
            this.entity.world.setBlockToAir(this.blockPosition);
            this.entity.world.playEvent(1021, this.blockPosition, 0);
            this.entity.world.playEvent(2001, this.blockPosition, Block.getIdFromBlock(this.block));
        }
    }

    public Block getBlock() {
        return this.block;
    }
}
