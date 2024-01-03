package io.github.itskillerluc.gtfo_craft.entity.ai;

import io.github.itskillerluc.gtfo_craft.entity.ModEntity;

public class EntityAITongueLarge<E extends ModEntity & gtfoEntity> extends EntityAITongue<E>{
    private final int damageTiming;

    public EntityAITongueLarge(E creature, double speedIn, boolean useLongMemory, float animLength, int attackInterval, int damageTiming) {
        super(creature, speedIn, useLongMemory, 12, animLength, attackInterval);
        this.damageTiming = damageTiming;
    }

    @Override
    int damageTiming() {
        return damageTiming;
    }
}
