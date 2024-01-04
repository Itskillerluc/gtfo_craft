package io.github.itskillerluc.gtfo_craft.entity.ai;

import io.github.itskillerluc.gtfo_craft.entity.ModEntity;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIAttackRangedBow;

public class EntityAIAttackRangedStrafe<T extends ModEntity & IRangedAttackMob> extends EntityAIAttackRangedBow<T> {
    public EntityAIAttackRangedStrafe(T p_i47515_1_, double p_i47515_2_, int p_i47515_4_, float p_i47515_5_) {
        super(p_i47515_1_, p_i47515_2_, p_i47515_4_, p_i47515_5_);
    }
    @Override
    protected boolean isBowInMainhand() {
        return true;
    }
}
