package io.github.itskillerluc.gtfo_craft.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityStriker extends ModEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);

    private static final DataParameter<Boolean> ATTACKING =
            EntityDataManager.createKey(EntityStriker.class, DataSerializers.BOOLEAN);

    public EntityStriker(World worldIn) {
        super(worldIn);
        setSize(0.6f, 2f);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ATTACKING, false);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        if (this.dataManager.get(ATTACKING)) {
            compound.setBoolean("attacking", true);
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.dataManager.set(ATTACKING, compound.getBoolean("attacking"));
    }

    public boolean isAttacking(){
        return this.dataManager.get(ATTACKING);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));

        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(4, new StrikerAttackGoal(1, true));

        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));;
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, false, false));
    }

    @Override
    public AnimationBuilder getSleeping0() {
        return null;
    }

    @Override
    public AnimationBuilder getSleeping1() {
        return null;
    }

    @Override
    public AnimationBuilder getSleeping2() {
        return null;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(100.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.5D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12D);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        AnimationBuilder builder = new AnimationBuilder();
        boolean cont = false;
        if (event.isMoving() && !dataManager.get(ATTACKING)) {
            builder.addAnimation("animation.striker.walk", ILoopType.EDefaultLoopTypes.LOOP);
            cont = true;
        }

        if (dataManager.get(ATTACKING)) {
            builder.addAnimation("animation.striker.attack", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
            cont = true;
        }
        event.getController().setAnimation(builder);
        return cont ? PlayState.CONTINUE : PlayState.STOP;
    }
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    class StrikerAttackGoal extends EntityAIAttackMelee {
        private int animCounter = 0;
        private int animTickLength = 20;

        public StrikerAttackGoal(double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
            super(EntityStriker.this, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        }

        @Override
        protected void checkAndPerformAttack(EntityLivingBase pEnemy, double pDistToEnemySqr) {
            if (pDistToEnemySqr <= this.getAttackReachSqr(pEnemy) && this.attackTick <= 0) {
                EntityStriker.this.dataManager.set(ATTACKING, true);
                animCounter = 0;
            }

            super.checkAndPerformAttack(pEnemy, pDistToEnemySqr);
        }

        @Override
        public void updateTask() {
            super.updateTask();
            if(EntityStriker.this.isAttacking()) {
                animCounter++;

                if(animCounter >= animTickLength) {
                    animCounter = 0;
                    EntityStriker.this.dataManager.set(ATTACKING, false);
                }
            }
        }


        @Override
        public void resetTask() {
            animCounter = 0;
            EntityStriker.this.dataManager.set(ATTACKING, false);
            super.resetTask();
        }
    }
}
