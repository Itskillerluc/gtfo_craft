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
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityBigShadow extends EntityMob implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);

    private static final DataParameter<Boolean> ATTACKING =
            EntityDataManager.createKey(EntityBigShadow.class, DataSerializers.BOOLEAN);

    public EntityBigShadow(World worldIn) {
        super(worldIn);
        setSize(1.6f, 4f);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ATTACKING, true);
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
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(4, new StrikerAttackGoal(this, 1, true));

        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));;
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(100.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(5D);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        AnimationBuilder builder = new AnimationBuilder();
        boolean cont = false;
        if (event.isMoving() && !dataManager.get(ATTACKING)) {
            builder.addAnimation("animation.big_shadow.walk", ILoopType.EDefaultLoopTypes.LOOP);
            cont = true;
        }

        if (dataManager.get(ATTACKING)) {
            builder.addAnimation("animation.big_shadow.attack", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
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
        private EntityBigShadow entity;
        private int animCounter = 0;
        private int animTickLength = 20;

        public StrikerAttackGoal(EntityBigShadow pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
            super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
            entity = pMob;
        }

        @Override
        protected void checkAndPerformAttack(EntityLivingBase pEnemy, double pDistToEnemySqr) {
            if (pDistToEnemySqr <= this.getAttackReachSqr(pEnemy) && this.attackTick <= 0) {
                if(entity != null) {
                    entity.dataManager.set(ATTACKING, true);
                    animCounter = 0;
                }
            }

            super.checkAndPerformAttack(pEnemy, pDistToEnemySqr);
        }

        @Override
        public void updateTask() {
            super.updateTask();
            if(entity.isAttacking()) {
                animCounter++;

                if(animCounter >= animTickLength) {
                    animCounter = 0;
                    entity.dataManager.set(ATTACKING, false);
                }
            }
        }


        @Override
        public void resetTask() {
            animCounter = 0;
            entity.dataManager.set(ATTACKING, false);
            super.resetTask();
        }
    }
}
