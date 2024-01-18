package io.github.itskillerluc.gtfo_craft.entity;

import io.github.itskillerluc.gtfo_craft.EntityStatConfig;
import io.github.itskillerluc.gtfo_craft.entity.ai.AnimatedAttackGoal;
import io.github.itskillerluc.gtfo_craft.entity.ai.EntityAIRangedBurst;
import io.github.itskillerluc.gtfo_craft.entity.ai.gtfoEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityHybrid extends ModEntity implements IAnimatable, IRangedAttackMob, gtfoEntity {
    private static final AnimationBuilder SLEEP1 = new AnimationBuilder().addAnimation("default_sleeping", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder SLEEP2 = new AnimationBuilder().addAnimation("sleeping_1", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder SLEEP3 = new AnimationBuilder().addAnimation("sleeping_2", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder RUN = new AnimationBuilder().addAnimation("run", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder IDLE = new AnimationBuilder().addAnimation("idle", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder MELEE_ATTACK = new AnimationBuilder().addAnimation("melee_attack", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder RANGED_ATTACK = new AnimationBuilder().addAnimation("ranged_attack", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder SCREAM1 = new AnimationBuilder().addAnimation("scream_1", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder SCREAM2 = new AnimationBuilder().addAnimation("scream_2", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder SCREAM3 = new AnimationBuilder().addAnimation("scream_3", ILoopType.EDefaultLoopTypes.LOOP);
    private final AnimationFactory factory = new AnimationFactory(this);
    private final int screamLength1 = 50;
    private final int screamLength2 = 60;
    private final int screamLength3 = 60;
    private int isScreaming = 0;
    private int screamCounter;
    private static final DataParameter<Boolean> ATTACKING =
            EntityDataManager.createKey(EntityHybrid.class, DataSerializers.BOOLEAN);

    private static final DataParameter<Boolean> RANGED_ATTACKING =
            EntityDataManager.createKey(EntityHybrid.class, DataSerializers.BOOLEAN);

    public EntityHybrid(World worldIn) {
        super(worldIn);
        setSize(.8f, 2);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ATTACKING, false);
        this.dataManager.register(RANGED_ATTACKING, false);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setBoolean("attacking", isAttacking());
        compound.setBoolean("rangedAttacking", dataManager.get(RANGED_ATTACKING));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.dataManager.set(ATTACKING, compound.getBoolean("attacking"));
        this.dataManager.set(RANGED_ATTACKING, compound.getBoolean("rangedAttacking"));
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
        return false;
    }

    @Override
    public void setShootingTongue(boolean shooting) {

    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(4, new EntityAIRangedBurst<EntityHybrid>(this, 1, ((int) EntityStatConfig.getAttackSpeed("hybrid")), 20, 12, 60) {
            @Override
            public boolean shouldExecute() {
                return super.shouldExecute() && EntityHybrid.super.getAttackTarget().getDistance(EntityHybrid.this) > 6;
            }
        });
        this.tasks.addTask(4, new AnimatedAttackGoal<EntityHybrid>(this, 1, false, 50) {
            @Override
            public boolean shouldExecute() {
                return super.shouldExecute() && EntityHybrid.super.getAttackTarget().getDistance(EntityHybrid.this) <= 6;
            }

            @Override
            public boolean shouldContinueExecuting() {
                return super.shouldContinueExecuting() && EntityHybrid.super.getAttackTarget().getDistance(EntityHybrid.this) <= 6;
            }
        });

        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));;
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
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(EntityStatConfig.getMaxHealth("hybrid"));
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(EntityStatConfig.getFollowRange("hybrid"));
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(EntityStatConfig.getKnockBackResistance("hybrid"));
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(EntityStatConfig.getMovementSpeed("hybrid"));
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(EntityStatConfig.getAttackDamage("hybrid"));
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(EntityStatConfig.getArmor("hybrid"));
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(EntityStatConfig.getArmorToughness("hybrid"));
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
        if (dataManager.get(RANGED_ATTACKING)) {
            event.getController().setAnimation(RANGED_ATTACK);
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
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        EntityPellet entityPellet = new EntityPellet(world, this, ((int) EntityStatConfig.getProjectileDamage("hybrid")));
        double d0 = target.posX - this.posX;
        double d1 = target.getEntityBoundingBox().minY + (double)(target.height / 3.0F) - entityPellet.posY;
        double d2 = target.posZ - this.posZ;
        double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
        entityPellet.shoot(d0, d1 + d3 * 0.20000000298023224D, d2, 1.6F, (float)(14 - this.world.getDifficulty().getDifficultyId() * 4));
        this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.world.spawnEntity(entityPellet);
    }

    @Override
    public void setSwingingArms(boolean swingingArms) {
        dataManager.set(RANGED_ATTACKING, swingingArms);
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
