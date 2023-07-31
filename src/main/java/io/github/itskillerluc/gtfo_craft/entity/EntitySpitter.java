package io.github.itskillerluc.gtfo_craft.entity;

import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntitySpitter extends EntityLiving implements IAnimatable {
    private static final DataParameter<EnumFacing> FACING = EntityDataManager.createKey(EntitySpitter.class, DataSerializers.FACING);
    public static final int RANGE = 4;
    private int cooldown = 200;
    private final AnimationFactory factory = new AnimationFactory(this);
    public EntitySpitter(World worldIn) {
        super(worldIn);
        setSize(0.5f, 0.5f);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(FACING, EnumFacing.UP);
    }

    public EnumFacing getFacing() {
        return dataManager.get(FACING);
    }

    public void setFacing(EnumFacing facing) {
        dataManager.set(FACING, facing);
    }
    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("facing", dataManager.get(FACING).getIndex());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.dataManager.set(FACING, EnumFacing.getFront(compound.getInteger("facing")));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5);
    }

    @Override
    public void registerControllers(AnimationData data) {

    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public void move(MoverType type, double x, double y, double z) {
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        EntityPlayer player = world.getClosestPlayerToEntity(this, RANGE * 1.5f);
        if (player != null && cooldown <= 0) {
            if (player.getDistance(this) > RANGE) {
                playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1, 1);
            } else {
                world.createExplosion(this, this.posX, this.posY, this.posZ, 6, false);
                cooldown = 200;
            }
        }
        cooldown--;
    }
}
