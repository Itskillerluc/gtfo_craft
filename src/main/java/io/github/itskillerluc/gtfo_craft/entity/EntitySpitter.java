package io.github.itskillerluc.gtfo_craft.entity;

import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
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
    private final DataParameter<EnumFacing> FACING = EntityDataManager.createKey(EntitySpitter.class, DataSerializers.FACING);
    public static final int RANGE = 4;
    private final AnimationFactory factory = new AnimationFactory(this);
    public EntitySpitter(World worldIn) {
        super(worldIn);
        this.dataManager.register(FACING, EnumFacing.UP);
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

    public boolean onValidSurface() {
        if (!this.world.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty()) {
            return false;
        } else {
            int i = Math.max(1, 4 / 16);
            int j = Math.max(1, 4 / 16);
            BlockPos blockpos = this.getPosition().offset(this.getFacing().getOpposite());
            EnumFacing enumfacing = this.getFacing().rotateYCCW();
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for (int k = 0; k < i; ++k) {
                for (int l = 0; l < j; ++l) {
                    int i1 = (i - 1) / -2;
                    int j1 = (j - 1) / -2;
                    blockpos$mutableblockpos.setPos(blockpos).move(enumfacing, k + i1).move(EnumFacing.UP, l + j1);
                    IBlockState iblockstate = this.world.getBlockState(blockpos$mutableblockpos);

                    if (iblockstate.isSideSolid(this.world, blockpos$mutableblockpos, getFacing()))
                        continue;

                    if (!iblockstate.getMaterial().isSolid() && !BlockRedstoneDiode.isDiode(iblockstate)) {
                        return false;
                    }
                }
            }

            return this.world.getEntitiesInAABBexcluding(this, this.getEntityBoundingBox(), entity -> entity instanceof EntitySpitter).isEmpty();
        }
    }
}
