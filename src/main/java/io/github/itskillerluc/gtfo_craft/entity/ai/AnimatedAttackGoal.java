package io.github.itskillerluc.gtfo_craft.entity.ai;

import io.github.itskillerluc.gtfo_craft.entity.EntityBaby;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;

public class AnimatedAttackGoal<E extends EntityCreature & gtfoEntity> extends EntityAIAttackMelee {
    private E entity;
    private int animCounter = 0;
    private final int animTickLength;

    public AnimatedAttackGoal(E pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen, int length) {
        super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        entity = pMob;
        this.animTickLength = length;
    }

    @Override
    protected void checkAndPerformAttack(EntityLivingBase pEnemy, double pDistToEnemySqr) {
        if (pDistToEnemySqr <= this.getAttackReachSqr(pEnemy) && this.attackTick <= 0) {
            if (entity != null) {
                entity.setAttacking(true);
                animCounter = 0;
            }
        }

        super.checkAndPerformAttack(pEnemy, pDistToEnemySqr);
    }

    @Override
    public void updateTask() {
        super.updateTask();
        if (entity.isAttacking()) {
            animCounter++;

            if (animCounter >= animTickLength) {
                animCounter = 0;
                entity.setAttacking(false);
            }
        }
    }


    @Override
    public void resetTask() {
        animCounter = 0;
        entity.setAttacking(false);
        super.resetTask();
    }
}
