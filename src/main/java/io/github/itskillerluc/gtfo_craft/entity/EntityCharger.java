package io.github.itskillerluc.gtfo_craft.entity;

import io.github.itskillerluc.gtfo_craft.EntityStatConfig;
import io.github.itskillerluc.gtfo_craft.entity.ai.AnimatedAttackGoal;
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

public class EntityCharger extends ModEntity implements IAnimatable, gtfoEntity {
    private static final AnimationBuilder SLEEP1 = new AnimationBuilder().addAnimation("default_sleeping", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder SLEEP2 = new AnimationBuilder().addAnimation("sleeping_1", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder SLEEP3 = new AnimationBuilder().addAnimation("sleeping_2", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder RUN = new AnimationBuilder().addAnimation("run", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder MELEE_ATTACK = new AnimationBuilder().addAnimation("melee_attack", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder TONGUE_ATTACK = new AnimationBuilder().addAnimation("tongue_attack", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder SCREAM1 = new AnimationBuilder().addAnimation("scream_1", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder SCREAM2 = new AnimationBuilder().addAnimation("scream_2", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder SCREAM3 = new AnimationBuilder().addAnimation("scream_3", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder IDLE = new AnimationBuilder().addAnimation("idle", ILoopType.EDefaultLoopTypes.LOOP);
    private int nextAttack = rand.nextInt(2);
    private final int screamLength1 = 50;
    private final int screamLength2 = 50;
    private final int screamLength3 = 60;
    private int isScreaming = 0;
    private int screamCounter;
    private final AnimationFactory factory = new AnimationFactory(this);

    private static final DataParameter<Boolean> ATTACKING =
            EntityDataManager.createKey(EntityCharger.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> TONGUE_ATTACKING =
            EntityDataManager.createKey(EntityCharger.class, DataSerializers.BOOLEAN);

    public EntityCharger(World worldIn) {
        super(worldIn);
        setSize(1f, 2.2f);
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
        this.dataManager.set(ATTACKING, compound.getBoolean("attacking"));
        this.dataManager.set(TONGUE_ATTACKING, compound.getBoolean("tongueAttacking"));
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
        return dataManager.get(TONGUE_ATTACKING);
    }

    @Override
    public void setShootingTongue(boolean shooting) {
        dataManager.set(TONGUE_ATTACKING, shooting);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(5, new EntityAITongue<EntityCharger>(this, 1, false, 6, 30, ((int) EntityStatConfig.getAttackSpeed("charger")), 20, 9) {
            @Override
            public boolean shouldExecute() {
                return super.shouldExecute() && getDistanceSq(getAttackTarget()) > 25 && getDistanceSq(getAttackTarget()) < 49;
            }
        });
        this.tasks.addTask(4, new AnimatedAttackGoal<EntityCharger>(this, 1, true, 30) {
            @Override
            public boolean shouldContinueExecuting() {
                return super.shouldContinueExecuting() && (getDistanceSq(getAttackTarget()) < 25 || getDistanceSq(getAttackTarget()) > 49);
            }
        });

        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
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
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(EntityStatConfig.getMaxHealth("charger"));
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(EntityStatConfig.getFollowRange("charger"));
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(EntityStatConfig.getKnockBackResistance("charger"));
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(EntityStatConfig.getMovementSpeed("charger"));
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(EntityStatConfig.getAttackDamage("charger"));
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(EntityStatConfig.getArmor("charger"));
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(EntityStatConfig.getArmorToughness("charger"));
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
        if (isScreaming == 1) {
            event.getController().setAnimation(SCREAM1);
            return PlayState.CONTINUE;
        }
        if (isScreaming == 2) {
            event.getController().setAnimation(SCREAM2);
            return PlayState.CONTINUE;
        }
        if (isScreaming == 3) {
            event.getController().setAnimation(SCREAM3);
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
        if (isScreaming != 0) {
            freeze();
        } else {
            unfreeze();
        }
        if (isScreaming == 1) {
            screamCounter++;

            if (screamCounter >= screamLength1) {
                screamCounter = 0;
                isScreaming = 0;
            }
        }
        if (isScreaming == 2) {
            screamCounter++;

            if (screamCounter >= screamLength2) {
                screamCounter = 0;
                isScreaming = 0;
            }
        }
        if (isScreaming == 3) {
            screamCounter++;

            if (screamCounter >= screamLength3) {
                screamCounter = 0;
                isScreaming = 0;
            }
        }

        if (rand.nextFloat() < .00004) {
            isScreaming = 1;
        }
        if (rand.nextFloat() < .00004) {
            isScreaming = 2;
        }
        if (rand.nextFloat() < .00004) {
            isScreaming = 3;
        }
    }
}
