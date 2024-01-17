package io.github.itskillerluc.gtfo_craft.entity;

import io.github.itskillerluc.gtfo_craft.EntityStatConfig;
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

public class EntityBaby extends ModEntity implements IAnimatable, gtfoEntity {

    private final AnimationFactory factory = new AnimationFactory(this);
    private static final AnimationBuilder SLEEP1 = new AnimationBuilder().addAnimation("default_sleeping", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder SLEEP2 = new AnimationBuilder().addAnimation("sleeping_1", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder SLEEP3 = new AnimationBuilder().addAnimation("sleeping_2", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder RUN = new AnimationBuilder().addAnimation("run", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder ATTACK = new AnimationBuilder().addAnimation("tongue_attack", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder SCREAM = new AnimationBuilder().addAnimation("scream", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder IDLE = new AnimationBuilder().addAnimation("idle", ILoopType.EDefaultLoopTypes.LOOP);

    private static final DataParameter<Boolean> ATTACKING =
            EntityDataManager.createKey(EntityBaby.class, DataSerializers.BOOLEAN);

    private final int screamLength = 30;
    private boolean isScreaming = false;
    private int screamCounter;

    public EntityBaby(World worldIn) {
        super(worldIn);
        setSize(0.6f, 1.2f);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ATTACKING, false);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setBoolean("attacking", dataManager.get(ATTACKING));
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
        this.dataManager.set(ATTACKING, attacking);
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
        this.tasks.addTask(4, new EntityAITongue<>(this, 1, true, 4, 30, ((int) EntityStatConfig.getAttackSpeed("baby")), 20, 7));

        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, false));
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
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(EntityStatConfig.getMaxHealth("baby"));
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(EntityStatConfig.getFollowRange("baby"));
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(EntityStatConfig.getKnockBackResistance("baby"));
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(EntityStatConfig.getMovementSpeed("baby"));
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(EntityStatConfig.getAttackDamage("baby"));
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(EntityStatConfig.getArmor("baby"));
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(EntityStatConfig.getArmorToughness("baby"));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
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
        if (isAttacking()) {
            event.getController().setAnimation(ATTACK);

            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState screamingPredicate(AnimationEvent<E> event) {
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
        data.addAnimationController(new AnimationController<>(this, "screamController", 0, this::screamingPredicate));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (isScreaming) {
            freeze();
        } else {
            unfreeze();
        }

        if (isScreaming) {
            screamCounter++;

            if (screamCounter >= screamLength) {
                screamCounter = 0;
                isScreaming = false;
            }
        }

        if (rand.nextFloat() < .00012) {
            isScreaming = true;
        }
    }

    @Override
    public void wakeUp() {
        super.wakeUp();
        isScreaming = true;
    }
}
