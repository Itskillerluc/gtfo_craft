package io.github.itskillerluc.gtfo_craft.entity.ai;

import io.github.itskillerluc.gtfo_craft.entity.ModEntity;

public class EntityAITongueSmall<E extends ModEntity & gtfoEntity> extends EntityAITongue<E>{
    private final int damageTiming;

    public EntityAITongueSmall(E creature, double speedIn, boolean useLongMemory, float animLength, int attackInterval, int damageTiming) {
        super(creature, speedIn, useLongMemory, 1, animLength, attackInterval);
        this.damageTiming = damageTiming;
    }

    @Override
    int damageTiming() {
        return damageTiming;
    }
}
