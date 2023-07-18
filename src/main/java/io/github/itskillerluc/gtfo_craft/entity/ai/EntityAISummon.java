package io.github.itskillerluc.gtfo_craft.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

import java.util.function.Supplier;

public class EntityAISummon<T extends EntityLiving> extends EntityAIBase {
    public final int chance;
    public final T entity;
    public final Supplier<? extends Entity> spawnFactory;

    public EntityAISummon(int chance, T entity, Supplier<? extends Entity> spawnFactory) {
        this.chance = chance;
        this.entity = entity;
        this.spawnFactory = spawnFactory;
    }

    @Override
    public boolean shouldExecute() {
        return entity.getAttackTarget() != null && entity.getAttackTarget().getDistance(entity) < 20 &&
                entity.ticksExisted % chance == 0 && !entity.world.isRemote;
    }

    @Override
    public void startExecuting() {
        entity.world.spawnEntity(spawnFactory.get());
    }
}
