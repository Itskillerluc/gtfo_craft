package io.github.itskillerluc.gtfo_craft.entity;

import io.github.itskillerluc.gtfo_craft.entity.ai.EntityAIBlockBreak;
import io.github.itskillerluc.gtfo_craft.registry.BlockRegistry;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

public abstract class ModEntity extends EntityMob {
    public ModEntity(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        super.tasks.addTask(1, new EntityAIBlockBreak(this, goal -> goal.getBlock().equals(BlockRegistry.BREAKABLE_DOOR_SMALL_HELPER) || goal.getBlock().equals(BlockRegistry.BREAKABLE_DOOR_SMALL_CONTROLLER)));
    }
}
