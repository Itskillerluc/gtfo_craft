package io.github.itskillerluc.gtfo_craft.entity;

import io.github.itskillerluc.gtfo_craft.entity.ai.AnimatedAttackGoal;
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
    private static final AnimationBuilder SLEEP1 = new AnimationBuilder().addAnimation("sleep1", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder SLEEP2 = new AnimationBuilder().addAnimation("sleep2", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder SLEEP3 = new AnimationBuilder().addAnimation("sleep3", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder RUN = new AnimationBuilder().addAnimation("run", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder ATTACK1 = new AnimationBuilder().addAnimation("attack1", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder ATTACK2 = new AnimationBuilder().addAnimation("attack2", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder SCREAM = new AnimationBuilder().addAnimation("scream", ILoopType.EDefaultLoopTypes.LOOP);
    private int nextAttack = rand.nextInt(2);
    private final AnimationFactory factory = new AnimationFactory(this);

    private static final DataParameter<Boolean> ATTACKING =
            EntityDataManager.createKey(EntityBigStriker.class, DataSerializers.BOOLEAN);

    public EntityBigStriker(World worldIn) {
        super(worldIn);
        setSize(1.3f, 4f);
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
    public void setAttacking(boolean attacking) {
        this.dataManager.set(ATTACKING, attacking);
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
        this.tasks.addTask(4, new AnimatedAttackGoal<EntityBigStriker>(this, 1, true, 30) {
            @Override
            public boolean shouldExecute() {
                return nextAttack == 0 && super.shouldExecute();
            }

            @Override
            public void resetTask() {
                super.resetTask();
                nextAttack = rand.nextInt(2);
            }
        });
        this.tasks.addTask(4, new AnimatedAttackGoal<EntityBigStriker>(this, 1, true, 50) {
            @Override
            public boolean shouldExecute() {
                return nextAttack == 1 && super.shouldExecute();
            }

            @Override
            public void resetTask() {
                super.resetTask();
                nextAttack = rand.nextInt(2);
            }
        });

        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));;
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
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
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(5D);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(RUN);
            return PlayState.CONTINUE;
        }

        return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event) {
        if (isAttacking()) {
            event.getController().setAnimation(nextAttack == 0 ? ATTACK1 : ATTACK2);
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
        data.addAnimationController(new AnimationController<>(this, "attackController", 0, this::attackPredicate));
    }
}
