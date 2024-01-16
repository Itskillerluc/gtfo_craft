package io.github.itskillerluc.gtfo_craft.entity;

import io.github.itskillerluc.gtfo_craft.entity.ai.AnimatedAttackGoal;
import io.github.itskillerluc.gtfo_craft.entity.ai.EntityAITongue;
import io.github.itskillerluc.gtfo_craft.entity.ai.gtfoEntity;
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

public class EntityBigStriker extends ModEntity implements IAnimatable, gtfoEntity {
    private static final AnimationBuilder SLEEP1 = new AnimationBuilder().addAnimation("default_sleeping", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder SLEEP2 = new AnimationBuilder().addAnimation("sleeping_1", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder SLEEP3 = new AnimationBuilder().addAnimation("sleeping_2", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder RUN = new AnimationBuilder().addAnimation("run", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder TONGUE_ATTACK = new AnimationBuilder().addAnimation("tongue_attack", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder MELEE_ATTACK = new AnimationBuilder().addAnimation("melee_attack", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder SCREAM = new AnimationBuilder().addAnimation("scream", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder IDLE = new AnimationBuilder().addAnimation("idle", ILoopType.EDefaultLoopTypes.LOOP);
    private final AnimationFactory factory = new AnimationFactory(this);
    private boolean isScreaming = false;
    private int screamCounter;
    private final int screamLength = 60;

    private static final DataParameter<Boolean> ATTACKING =
            EntityDataManager.createKey(EntityBigStriker.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> TONGUE_ATTACKING =
            EntityDataManager.createKey(EntityBigStriker.class, DataSerializers.BOOLEAN);

    public EntityBigStriker(World worldIn) {
        super(worldIn);
        setSize(1.3f, 4f);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ATTACKING, false);
        this.dataManager.register(TONGUE_ATTACKING, false);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setBoolean("attacking", isAttacking());
        compound.setBoolean("tongueAttacking", isShootingTongue());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        setAttacking(compound.getBoolean("attacking"));
        setShootingTongue(compound.getBoolean("tongueAttacking"));
    }

    public boolean isAttacking(){
        return this.dataManager.get(ATTACKING);
    }

    @Override
    public void setAttacking(boolean attacking) {
        this.dataManager.set(ATTACKING, attacking);
    }

    @Override
    public boolean isShootingTongue() {
        return this.dataManager.get(TONGUE_ATTACKING);
    }

    @Override
    public void setShootingTongue(boolean shooting) {
        this.dataManager.set(TONGUE_ATTACKING, shooting);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(5, new EntityAITongue<EntityBigStriker>(this, 1, false, 12, 30, 60, 20, 17) {
            @Override
            public boolean shouldExecute() {
                return super.shouldExecute() && getDistanceSq(getAttackTarget()) > 30;
            }
        });
        this.tasks.addTask(4, new AnimatedAttackGoal<EntityBigStriker>(this, 1, true, 30) {
            @Override
            public boolean shouldExecute() {
                return super.shouldExecute() && getDistanceSq(getAttackTarget()) < 30;
            }

            @Override
            public boolean shouldContinueExecuting() {
                return super.shouldContinueExecuting() && getDistanceSq(getAttackTarget()) < 30;
            }
        });

        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));;
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
    }

    @Override
    public AnimationBuilder getSleeping0() {
        return SLEEP1;
    }

    @Override
    public AnimationBuilder getSleeping1() {
        return SLEEP2;
    }

    @Override
    public AnimationBuilder getSleeping2() {
        return SLEEP3;
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
        if (isShootingTongue()) return PlayState.STOP;
        if (event.isMoving()) {
            event.getController().setAnimation(RUN);
            return PlayState.CONTINUE;
        } else if (dataManager.get(SLEEPING)) {
            event.getController().setAnimation(getSleepingAnimation());
            return PlayState.CONTINUE;
        } else {
            event.getController().setAnimation(IDLE);
            return PlayState.CONTINUE;
        }
    }

    private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event) {
        if (!isShootingTongue() && isAttacking()) {
            event.getController().setAnimation(MELEE_ATTACK);
            return PlayState.CONTINUE;
        }
        if (isShootingTongue()) {
            event.getController().setAnimation(TONGUE_ATTACK);
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState screamPredicate(AnimationEvent<E> event) {
        if (isScreaming) {
            event.getController().setAnimation(SCREAM);
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
        data.addAnimationController(new AnimationController<>(this, "attackController", 0, this::attackPredicate));
        data.addAnimationController(new AnimationController<>(this, "screamController", 0, this::screamPredicate));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (isScreaming) {
            screamCounter++;

            if (screamCounter >= screamLength) {
                screamCounter = 0;
                isScreaming = false;
            }
        }
        if (rand.nextFloat() < 0.004) {
            isScreaming = true;
        }
    }
}
