package io.github.itskillerluc.gtfo_craft.entity;

import io.github.itskillerluc.gtfo_craft.entity.ai.EntityAITongue;
import io.github.itskillerluc.gtfo_craft.entity.ai.gtfoEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
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

public class EntityStrikerCrawling extends ModEntity implements IAnimatable, gtfoEntity {
    private static final AnimationBuilder SLEEPING_1 = new AnimationBuilder().addAnimation("crawling_default_sleeping", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder SLEEPING_2 = new AnimationBuilder().addAnimation("crawling_sleeping_1", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder SLEEPING_3 = new AnimationBuilder().addAnimation("crawling_sleeping_2", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder IDLE = new AnimationBuilder().addAnimation("crawling_default_idle", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder RUN = new AnimationBuilder().addAnimation("crawling_run", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder TONGUE_ATTACK = new AnimationBuilder().addAnimation("crawling_tongue_attack", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder SCREAM_1 = new AnimationBuilder().addAnimation("standing_scream_1", ILoopType.EDefaultLoopTypes.LOOP);
    private final AnimationFactory factory = new AnimationFactory(this);
    private final int screamLength = 30;
    private boolean isScreaming = false;
    private int screamCounter;
    private static final DataParameter<Boolean> ATTACKING =
            EntityDataManager.createKey(EntityStrikerCrawling.class, DataSerializers.BOOLEAN);

    public EntityStrikerCrawling(World worldIn) {
        super(worldIn);
        setSize(1.2f, 1.5f);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ATTACKING, false);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setBoolean("attacking", isAttacking());
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
    public void setAttacking(boolean attacking) {
        dataManager.set(ATTACKING, attacking);
    }

    @Override
    public boolean isShootingTongue() {
        return isAttacking();
    }

    @Override
    public void setShootingTongue(boolean shooting) {
        setAttacking(shooting);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(5, new EntityAITongue<>(this, 1, false, 4, 30, 60, 20, 12));

        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));;
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
    }

    @Override
    public AnimationBuilder getSleeping0() {
        return SLEEPING_1;
    }

    @Override
    public AnimationBuilder getSleeping1() {
        return SLEEPING_2;
    }

    @Override
    public AnimationBuilder getSleeping2() {
        return SLEEPING_3;
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
        if (isShootingTongue()) {
            event.getController().setAnimation(TONGUE_ATTACK);
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState screamPredicate(AnimationEvent<E> event) {
        if (isScreaming) {
            event.getController().setAnimation(SCREAM_1);
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
