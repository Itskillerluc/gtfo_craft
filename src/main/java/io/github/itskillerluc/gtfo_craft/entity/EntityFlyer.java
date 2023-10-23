package io.github.itskillerluc.gtfo_craft.entity;

import io.github.itskillerluc.gtfo_craft.entity.ai.EntityAIRangedBurst;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityFlying;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.math.BlockPos;
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

public class EntityFlyer extends ModEntity implements IAnimatable, IRangedAttackMob, EntityFlying {
    private static final AnimationBuilder FLY = new AnimationBuilder().addAnimation("fly", ILoopType.EDefaultLoopTypes.LOOP);
    private final AnimationFactory factory = new AnimationFactory(this);

    public EntityFlyer(World worldIn) {
        super(worldIn);
        setSize(1.2f, 1.2f);
        this.moveHelper = new EntityFlyHelper(this);
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    protected void updateAITasks() {
        if (this.moveHelper.action != EntityMoveHelper.Action.MOVE_TO) {
            this.motionY = (Math.sin(this.ticksExisted * 0.1) * 0.1) -  MathHelper.clamp((this.posY - (world.getHeight((int) this.posX, (int)this.posZ) + 2)), -0.1, 0.1);
        }
    }

    @Override
    public void fall(float distance, float damageMultiplier) {

    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {

    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        setNoGravity(true);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
    }

    @Override
    protected PathNavigate createNavigator(World worldIn) {
        return new PathNavigateFlying(this, worldIn);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));

        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(4, new EntityAIRangedBurst(this, 1, 30, 10, 3, 10));
        this.tasks.addTask(2, new EntityAIWanderAvoidWaterFlying(this, 1.0D));

        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));;
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, false, false));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
        this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.4000000059604645D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20000000298023224D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(100.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.5D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12D);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(FLY);
        return PlayState.CONTINUE;
    }
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        EntityPellet entityPellet = new EntityPellet(world, this, 3);
        double d0 = target.posX - this.posX;
        double d1 = target.getEntityBoundingBox().minY + (double)(target.height / 3.0F) - entityPellet.posY;
        double d2 = target.posZ - this.posZ;
        double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
        entityPellet.shoot(d0, d1 + d3 * 0.20000000298023224D, d2, 2F, (float)(14 - this.world.getDifficulty().getDifficultyId() * 4));
        this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.world.spawnEntity(entityPellet);
    }

    @Override
    public void setSwingingArms(boolean swingingArms) {
        this.isSwingInProgress = swingingArms;
    }
}
