package io.github.itskillerluc.gtfo_craft.entity;

import io.github.itskillerluc.gtfo_craft.registry.BlockRegistry;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.world.World;

public abstract class ModEntity extends EntityMob {
    public ModEntity(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        super.tasks.addTask(1, new EntityAIBreakDoor(this) {
            @Override
            public boolean shouldExecute() {
                return super.shouldExecute() && this.doorBlock.equals(BlockRegistry.BREAKABLE_DOOR);
            }
        });
    }
}
