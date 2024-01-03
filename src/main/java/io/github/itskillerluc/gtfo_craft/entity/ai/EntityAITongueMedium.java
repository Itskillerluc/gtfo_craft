package io.github.itskillerluc.gtfo_craft.entity.ai;

import io.github.itskillerluc.gtfo_craft.entity.ModEntity;

public class EntityAITongueMedium<E extends ModEntity & gtfoEntity> extends EntityAITongue<E>{

    public EntityAITongueMedium(E creature, double speedIn, boolean useLongMemory, float tongueLength, float animLength, int attackInterval) {
        super(creature, speedIn, useLongMemory, tongueLength, animLength, attackInterval);
    }

    @Override
    int damageTiming() {
        return 0;
    }
}
