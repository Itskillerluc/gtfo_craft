package io.github.itskillerluc.gtfo_craft.entity;

import com.google.common.base.Optional;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Random;

public class EntityScout extends ModEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    private boolean isScreaming = false;

    private static final DataParameter<Boolean> ATTACKING =
            EntityDataManager.createKey(EntityScout.class, DataSerializers.BOOLEAN);

    protected static final DataParameter<Optional<BlockPos>> SUMMON_POS =
            EntityDataManager.createKey(EntityScout.class, DataSerializers.OPTIONAL_BLOCK_POS);

    protected static final DataParameter<String> COMMAND =
            EntityDataManager.createKey(EntityScout.class, DataSerializers.STRING);

    public EntityScout(World worldIn) {
        super(worldIn);
        setSize(0.6f, 2f);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ATTACKING, true);
        this.dataManager.register(SUMMON_POS, Optional.absent());
        this.dataManager.register(COMMAND, "");
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        if (this.dataManager.get(ATTACKING)) {
            compound.setBoolean("attacking", true);
        }
        if (dataManager.get(SUMMON_POS).isPresent()) {
            compound.setTag("summonPos", NBTUtil.createPosTag(dataManager.get(SUMMON_POS).get()));
        } else {
            compound.setTag("summonPos", new NBTTagCompound());
        }
        compound.setString("command", dataManager.get(COMMAND));
    }

    @Override
    public boolean hitByEntity(Entity entityIn) {
        if (isScreaming) {
            return false;
        } else {
            isScreaming = true;
            return super.hitByEntity(entityIn);
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.dataManager.set(ATTACKING, compound.getBoolean("attacking"));
        if (compound.getCompoundTag("summonPos").hasNoTags()) {
            dataManager.set(SUMMON_POS, Optional.absent());
        } else {
            dataManager.set(SUMMON_POS, Optional.of(NBTUtil.getPosFromTag(compound.getCompoundTag("summonPos"))));
        }
        this.dataManager.set(COMMAND, compound.getString("command"));
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

    class ScoutTendrilGoal extends EntityAIBase {
        private int animCounter = 0;
        private int animTickLength = 20;
        private final float chance;
        private final float range;

        ScoutTendrilGoal(float chance, float range) {
            this.chance = chance;
            this.range = range;
        }

        @Override
        public void startExecuting() {
            EntityScout.this.dataManager.set(ATTACKING, true);
        }

        @Override
        public void updateTask() {
            super.updateTask();
            if (world.isAnyPlayerWithinRangeAt(posX, posY, posZ, range)) {
                scream();
            }
            if(EntityScout.this.isAttacking()) {
                animCounter++;
                if(animCounter >= animTickLength) {
                    animCounter = 0;
                    EntityScout.this.dataManager.set(ATTACKING, false);
                    resetTask();
                }
            }
        }

        public void scream() {
            //TODO: SCREAAAAM and stop the animation
            if (!EntityScout.this.world.isRemote) {
                world.getMinecraftServer().getCommandManager().executeCommand(EntityScout.this,dataManager.get(COMMAND));
            }
            isScreaming = true;
            Entity entity = new EntityShooter(world);
            entity.setPosition(posX, posY, posZ);
            world.spawnEntity(entity);
            despawnEntity();
        }

        @Override
        public boolean shouldExecute() {
            return new Random().nextFloat() <= chance;
        }

        @Override
        public void resetTask() {
            animCounter = 0;
            EntityScout.this.dataManager.set(ATTACKING, false);
            super.resetTask();
        }
    }
}
