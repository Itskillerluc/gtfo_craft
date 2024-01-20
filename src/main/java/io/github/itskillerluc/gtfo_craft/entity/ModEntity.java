package io.github.itskillerluc.gtfo_craft.entity;

import com.google.common.base.Optional;
import io.github.itskillerluc.gtfo_craft.entity.ai.EntityAIBlockBreak;
import io.github.itskillerluc.gtfo_craft.network.PacketHandler;
import io.github.itskillerluc.gtfo_craft.network.WakeUpPacket;
import io.github.itskillerluc.gtfo_craft.registry.BlockRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.builder.AnimationBuilder;

import javax.annotation.Nullable;

public abstract class ModEntity extends EntityMob {
    private static final DataParameter<Boolean> FROZEN =
            EntityDataManager.createKey(ModEntity.class, DataSerializers.BOOLEAN);

    public static final DataParameter<Boolean> SLEEPING =
            EntityDataManager.createKey(ModEntity.class, DataSerializers.BOOLEAN);

    protected int sleepingCounter = 0;

    public ModEntity(World worldIn) {
        super(worldIn);
    }

    @Override
    protected PathNavigate createNavigator(World worldIn) {
        PathNavigateGround nav = new PathNavigateGround(this, worldIn);
        nav.setBreakDoors(true);
        nav.setEnterDoors(true);
        return nav;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(FROZEN, false);
        this.dataManager.register(SLEEPING, false);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setBoolean("frozen", dataManager.get(FROZEN));
        compound.setBoolean("sleeping", dataManager.get(SLEEPING));
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        dataManager.set(FROZEN, compound.getBoolean("frozen"));
        dataManager.set(SLEEPING, compound.getBoolean("sleeping"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setBoolean("frozen", dataManager.get(FROZEN));
        compound.setBoolean("sleeping", dataManager.get(SLEEPING));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        dataManager.set(FROZEN, compound.getBoolean("frozen"));
        dataManager.set(SLEEPING, compound.getBoolean("sleeping"));
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        super.tasks.addTask(1, new EntityAIBlockBreak(this, goal -> goal.getBlock().equals(BlockRegistry.COMMON_DOOR_SMALL_HELPER) || goal.getBlock().equals(BlockRegistry.COMMON_DOOR_SMALL_CONTROLLER) || goal.getBlock().equals(BlockRegistry.COMMON_DOOR_LARGE_HELPER) || goal.getBlock().equals(BlockRegistry.COMMON_DOOR_LARGE_CONTROLLER)));
    }

    @Override
    public boolean isOnSameTeam(Entity entityIn) {
        return entityIn instanceof ModEntity;
    }

    public void freeze() {
        setAIMoveSpeed(0);
    }

    public void unfreeze() {
        setAIMoveSpeed((float) getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        EntityPlayer nearestPlayerNotCreative = world.getNearestPlayerNotCreative(this, 7);
        if (dataManager.get(SLEEPING) && nearestPlayerNotCreative != null && !nearestPlayerNotCreative.isSneaking() && !(nearestPlayerNotCreative.motionX == 0 && Math.abs(nearestPlayerNotCreative.motionY) < 0.1 && nearestPlayerNotCreative.motionZ == 0)) {
            if (nearestPlayerNotCreative.isSprinting()) {
                sleepingCounter = 0;
                dataManager.set(SLEEPING, false);
                wakeUp();
            }
            sleepingCounter++;
        } else if (sleepingCounter > 0){
            sleepingCounter--;
        }
        if (sleepingCounter > 120) {
            dataManager.set(SLEEPING, false);
            sleepingCounter = 0;
            wakeUp();
        }
        setNoAI(dataManager.get(SLEEPING));
    }

    @Override
    protected void damageEntity(DamageSource damageSrc, float damageAmount) {
        super.damageEntity(damageSrc, damageAmount);
        dataManager.set(SLEEPING, false);
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer entityIn) {
        super.onCollideWithPlayer(entityIn);
        dataManager.set(SLEEPING, false);
    }

    public abstract AnimationBuilder getSleeping0();
    public abstract AnimationBuilder getSleeping1();
    public abstract AnimationBuilder getSleeping2();

    public void wakeUp() {
        PacketHandler.sendToServer(new WakeUpPacket(this.entityUniqueID));
    }

    public AnimationBuilder getSleepingAnimation() {
        if (sleepingCounter > 60) {
            return getSleeping2();
        }
        if (sleepingCounter > 1) {
            return getSleeping1();
        }
        return getSleeping0();
    }
}
