package io.github.itskillerluc.gtfo_craft.entity;

import io.github.itskillerluc.gtfo_craft.EntityStatConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityChargerScout extends ModEntity implements IAnimatable {
    private static final AnimationBuilder FEEL = new AnimationBuilder().addAnimation("feelers", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder PATROL = new AnimationBuilder().addAnimation("patrol", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder SCREAM = new AnimationBuilder().addAnimation("scream", ILoopType.EDefaultLoopTypes.LOOP);

    private final AnimationFactory factory = new AnimationFactory(this);
    private boolean isScreaming = false;
    private int screamCounter = 0;
    private int screamMaxTime = 55;
    private int counter;
    private final int counterLimit = ((int) EntityStatConfig.getAttackSpeed("charger_scout"));
    private final int feelingTime = 240;
    private int feelingCounter = 0;

    private static final DataParameter<Boolean> ATTACKING =
            EntityDataManager.createKey(EntityChargerScout.class, DataSerializers.BOOLEAN);

    private static final DataParameter<Boolean> FEELING =
            EntityDataManager.createKey(EntityChargerScout.class, DataSerializers.BOOLEAN);

    protected static final DataParameter<String> COMMAND =
            EntityDataManager.createKey(EntityChargerScout.class, DataSerializers.STRING);


    public EntityChargerScout(World worldIn) {
        super(worldIn);
        setSize(0.6f, 2f);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ATTACKING, false);
        this.dataManager.register(COMMAND, "");
        this.dataManager.register(FEELING, false);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setBoolean("attacking", isAttacking());
        compound.setString("command", dataManager.get(COMMAND));
        compound.setBoolean("feeling", dataManager.get(FEELING));
        compound.setInteger("feelingCounter", feelingCounter);
        compound.setInteger("counter", counter);
    }

    @Override
    public boolean isEntityInvulnerable(DamageSource p_180431_1_) {
        return super.isEntityInvulnerable(p_180431_1_) || isScreaming;
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
        this.dataManager.set(COMMAND, compound.getString("command"));
        this.dataManager.set(FEELING, compound.getBoolean("feeling"));
        feelingCounter = compound.getInteger("feelingCounter");
        counter = compound.getInteger("counter");
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
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, false));
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
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(EntityStatConfig.getMaxHealth("charger_scout"));
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(EntityStatConfig.getFollowRange("charger_scout"));
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(EntityStatConfig.getKnockBackResistance("charger_scout"));
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(EntityStatConfig.getMovementSpeed("charger_scout"));
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(EntityStatConfig.getAttackDamage("charger_scout"));
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(EntityStatConfig.getArmor("charger_scout"));
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(EntityStatConfig.getArmorToughness("charger_scout"));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (dataManager.get(FEELING)) {
            event.getController().setAnimation(FEEL);
            return PlayState.CONTINUE;
        }
        if (isScreaming) {
            event.getController().setAnimation(SCREAM);
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(PATROL);
        return PlayState.CONTINUE;
    }
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (isScreaming) {
            freeze();
        } else {
            unfreeze();
        }
        if (dataManager.get(FEELING) || isScreaming) {
            freeze();
        } else {
            unfreeze();
        }

        if (navigator.noPath()) {
            Vec3d pos = RandomPositionGenerator.getLandPos(this, 10, 7);
            if (pos != null) {
                navigator.tryMoveToXYZ(pos.x, pos.y, pos.z, 1);
            }
        }
        if (counter > counterLimit) {
            dataManager.set(FEELING, true);
        } else {
            counter++;
        }

        if (dataManager.get(FEELING)) {
            feelingCounter++;
            if (world.isAnyPlayerWithinRangeAt(posX, posY, posZ, 5)) {
                dataManager.set(FEELING, false);
                isScreaming = true;
            }
            if (feelingCounter > feelingTime) {
                dataManager.set(FEELING, false);
                counter = 0;
                feelingCounter = 0;
            }
        }

        if (isScreaming) {
            if (screamCounter > screamMaxTime) {
                isScreaming = false;
                if (!EntityChargerScout.this.world.isRemote) {
                    world.getMinecraftServer().getCommandManager().executeCommand(EntityChargerScout.this,dataManager.get(COMMAND));
                }
                Entity entity = new EntityScoutCharger(world);
                entity.setPosition(posX, posY, posZ);
                if (!world.isRemote) {
                    world.spawnEntity(entity);
                    world.removeEntity(this);
                }
            }
            screamCounter++;
        }
    }

    @Override
    public void freeze() {
        super.freeze();
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0);
    }

    @Override
    public void unfreeze() {
        super.unfreeze();
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
    }
}
