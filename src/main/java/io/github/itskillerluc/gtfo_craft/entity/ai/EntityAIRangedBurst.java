package io.github.itskillerluc.gtfo_craft.entity.ai;

import io.github.itskillerluc.gtfo_craft.entity.ModEntity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.MathHelper;

public class EntityAIRangedBurst <T extends ModEntity & IRangedAttackMob> extends EntityAIBase {
    private final EntityLiving entityHost;
    private final T rangedAttackEntityHost;
    private EntityLivingBase attackTarget;
    private int rangedAttackTime;
    private final double entityMoveSpeed;
    private int seeTime;
    private final int attackIntervalMin;
    private final int maxRangedAttackTime;
    private final float attackRadius;
    private final float maxAttackDistance;
    private final int burst;
    private final int fullAttackDuration;
    private int burstTime = 0;
    private boolean shouldAttackNextTick = false;
    private int bursted = 0;

    public EntityAIRangedBurst(T attacker, double movespeed, int maxAttackTime, float maxAttackDistanceIn, int burst, int fullAttackDuration)
    {
        this(attacker, movespeed, maxAttackTime, maxAttackTime, maxAttackDistanceIn, burst, fullAttackDuration);
    }

    public EntityAIRangedBurst(T attacker, double movespeed, int p_i1650_4_, int maxAttackTime, float maxAttackDistanceIn, int burst, int fullAttackDuration)
    {
        this.fullAttackDuration = fullAttackDuration;
        this.rangedAttackTime = -1;
        this.burst = burst;

        if (!(attacker instanceof EntityLivingBase))
        {
            throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
        }
        else
        {
            this.rangedAttackEntityHost = attacker;
            this.entityHost = attacker;
            this.entityMoveSpeed = movespeed;
            this.attackIntervalMin = p_i1650_4_;
            this.maxRangedAttackTime = maxAttackTime;
            this.attackRadius = maxAttackDistanceIn;
            this.maxAttackDistance = maxAttackDistanceIn * maxAttackDistanceIn;
            this.setMutexBits(3);
        }
    }

    public boolean shouldExecute()
    {
        EntityLivingBase entitylivingbase = this.entityHost.getAttackTarget();

        if (entitylivingbase == null)
        {
            return false;
        }
        else
        {
            this.attackTarget = entitylivingbase;
            rangedAttackEntityHost.freeze();
            return true;
        }
    }

    public boolean shouldContinueExecuting()
    {
        return this.shouldExecute() || !this.entityHost.getNavigator().noPath();
    }

    public void resetTask()
    {
        ((IRangedAttackMob)entityHost).setSwingingArms(false);
        this.attackTarget = null;
        this.seeTime = 0;
        this.rangedAttackTime = -1;
        rangedAttackEntityHost.unfreeze();
    }

    public void updateTask()
    {

        double d0 = this.entityHost.getDistanceSq(this.attackTarget.posX, this.attackTarget.getEntityBoundingBox().minY, this.attackTarget.posZ);
        boolean flag = this.entityHost.getEntitySenses().canSee(this.attackTarget);

        if (flag)
        {
            ++this.seeTime;
        }
        else
        {
            this.seeTime = 0;
        }

        if (d0 <= (double)this.maxAttackDistance && this.seeTime >= 20)
        {
            this.entityHost.getNavigator().clearPath();
        }
        else
        {
            this.entityHost.getNavigator().tryMoveToEntityLiving(this.attackTarget, this.entityMoveSpeed);
        }

        this.entityHost.getLookHelper().setLookPositionWithEntity(this.attackTarget, 30.0F, 30.0F);
        rangedAttackTime--;
        burstTime++;
        if ((shouldAttackNextTick && (burstTime % (fullAttackDuration / burst)) == 0) || this.rangedAttackTime == 0)
        {
            if (!flag)
            {
                return;
            }
            ((IRangedAttackMob)entityHost).setSwingingArms(true);

            float f = MathHelper.sqrt(d0) / this.attackRadius;
            float lvt_5_1_ = MathHelper.clamp(f, 0.1F, 1.0F);
            this.rangedAttackEntityHost.attackEntityWithRangedAttack(this.attackTarget, lvt_5_1_);
            this.rangedAttackTime = MathHelper.floor(f * (float)(this.maxRangedAttackTime - this.attackIntervalMin) + (float)this.attackIntervalMin);
            shouldAttackNextTick = bursted++ < burst;
            if (bursted > burst) {
                bursted = 0;
                shouldAttackNextTick = false;
                burstTime = 0;
            }
        }
        else if (this.rangedAttackTime < 0)
        {
            float f2 = MathHelper.sqrt(d0) / this.attackRadius;
            shouldAttackNextTick = true;
            this.rangedAttackTime = MathHelper.floor(f2 * (float)(this.maxRangedAttackTime - this.attackIntervalMin) + (float)this.attackIntervalMin);
        } else if (!shouldAttackNextTick) {
            ((IRangedAttackMob)entityHost).setSwingingArms(false);
        }
    }
}
