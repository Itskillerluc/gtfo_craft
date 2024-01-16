package io.github.itskillerluc.gtfo_craft.entity.ai;

import io.github.itskillerluc.gtfo_craft.Util;
import io.github.itskillerluc.gtfo_craft.entity.ModEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class EntityAITongue<E extends ModEntity & gtfoEntity> extends EntityAIBase {
    World world;
    protected E attacker;
    protected int attackTick;
    double speedTowardsTarget;
    boolean longMemory;
    Path path;
    private int delayCounter;
    private double targetX;
    private double targetY;
    private double targetZ;
    protected final int attackInterval;
    private int failedPathFindingPenalty = 0;
    private boolean canPenalize = false;
    private final float tongueLength;
    private final float animLength;
    private int animCounter = 0;
    private boolean attacking = false;
    private int damageTiming;
    private final int rangeSqr;

    public EntityAITongue(E creature, double speedIn, boolean useLongMemory, float tongueLength, float animLength, int attackInterval, int damageTiming, int rangeSqr)
    {
        this.attacker = creature;
        this.world = creature.world;
        this.speedTowardsTarget = speedIn;
        this.longMemory = useLongMemory;
        this.setMutexBits(3);
        this.tongueLength = tongueLength;
        this.animLength = animLength;
        this.attackInterval = attackInterval;
        this.damageTiming = damageTiming;
        this.rangeSqr = rangeSqr;
    }

    public boolean shouldExecute() {
        EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();

        if (entitylivingbase == null)
        {
            return false;
        }
        else if (!entitylivingbase.isEntityAlive())
        {
            return false;
        }
        else
        {
            if (canPenalize)
            {
                if (--this.delayCounter <= 0)
                {
                    this.path = this.attacker.getNavigator().getPathToPos(Util.offsetPosTo(entitylivingbase.getPosition(), attacker.getPosition(), tongueLength - 1));
                    this.delayCounter = 4 + this.attacker.getRNG().nextInt(7);
                    return this.path != null;
                }
                else
                {
                    return true;
                }
            }
            this.path = this.attacker.getNavigator().getPathToPos(Util.offsetPosTo(entitylivingbase.getPosition(), attacker.getPosition(), tongueLength - 1));

            if (this.path != null)
            {
                return true;
            }
            else
            {
                return this.getAttackReachSqr(entitylivingbase) >= this.attacker.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
            }
        }
    }

    public boolean shouldContinueExecuting()
    {
        EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();

        if (attacking) return true;
        if (entitylivingbase == null)
        {
            return false;
        }
        else if (!entitylivingbase.isEntityAlive())
        {
            return false;
        }
        else if (!this.longMemory)
        {
            return !this.attacker.getNavigator().noPath();
        }
        else if (!this.attacker.isWithinHomeDistanceFromPosition(new BlockPos(entitylivingbase)))
        {
            return false;
        }
        else
        {
            return !(entitylivingbase instanceof EntityPlayer) || !((EntityPlayer)entitylivingbase).isSpectator() && !((EntityPlayer)entitylivingbase).isCreative();
        }
    }

    public void startExecuting()
    {
        this.attacker.getNavigator().setPath(this.path, this.speedTowardsTarget);
        this.delayCounter = 0;
    }

    public void resetTask()
    {
        EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();

        if (entitylivingbase instanceof EntityPlayer && (((EntityPlayer)entitylivingbase).isSpectator() || ((EntityPlayer)entitylivingbase).isCreative()))
        {
            this.attacker.setAttackTarget(null);
        }

        this.attacker.getNavigator().clearPath();
        attacking = false;
        attacker.setShootingTongue(false);
    }

    public void updateTask()
    {
        EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
        if (entitylivingbase == null) return;
        this.attacker.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
        double d0 = this.attacker.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
        --this.delayCounter;

        if (attacking) {
            if (animCounter <= 1) {
                attacker.setShootingTongue(true);
            }
            if (animCounter == damageTiming) {
                Vec3d hitPosition = attacker.getLookVec().normalize().scale(tongueLength - 1);
                if (attacker.getPosition().add(new Vec3i(hitPosition.x, hitPosition.y, hitPosition.z)).distanceSq(targetX, targetY, targetZ) <= rangeSqr) {
                    this.attacker.attackEntityAsMob(attacker.getAttackTarget());
                }
            }
            if (animCounter > animLength) {
                attacker.unfreeze();
                attacker.setShootingTongue(false);
                resetTask();
                animCounter = 0;
            }
            animCounter++;
            return;
        }

        if ((this.longMemory || this.attacker.getEntitySenses().canSee(entitylivingbase)) && this.delayCounter <= 0 && (this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D || entitylivingbase.getDistanceSq(this.targetX, this.targetY, this.targetZ) >= 1.0D || this.attacker.getRNG().nextFloat() < 0.05F))
        {
            this.targetX = entitylivingbase.posX;
            this.targetY = entitylivingbase.getEntityBoundingBox().minY;
            this.targetZ = entitylivingbase.posZ;
            this.delayCounter = 4 + this.attacker.getRNG().nextInt(7);

            if (d0 > 1024.0D)
            {
                this.delayCounter += 10;
            }
            else if (d0 > 256.0D)
            {
                this.delayCounter += 5;
            }

            BlockPos pos = Util.offsetPosTo(entitylivingbase.getPosition(), attacker.getPosition(), tongueLength - 1);
            if (!this.attacker.getNavigator().tryMoveToXYZ(pos.getX(), pos.getY(), pos.getZ(), this.speedTowardsTarget))
            {
                this.delayCounter += 15;
            }
        }

        this.attackTick = Math.max(this.attackTick - 1, 0);
        this.checkAndPerformAttack(entitylivingbase, d0);
    }

    protected void checkAndPerformAttack(EntityLivingBase target, double reach)
    {
        double d0 = this.getAttackReachSqr(target);

        if (reach <= d0 && this.attackTick <= 0)
        {
            this.attackTick = attackInterval;
            attacking = true;
            attacker.freeze();
            attacker.getNavigator().clearPath();
            attacker.getNavigator().setPath(attacker.getNavigator().getPathToEntityLiving(target), 1);
            attacker.faceEntity(attacker.getAttackTarget(), 360, 360);
        }
    }

    protected double getAttackReachSqr(EntityLivingBase attackTarget)
    {
        return this.attacker.width * 2.0F * this.attacker.width * 2.0F + attackTarget.width + tongueLength * tongueLength;
    }
}
