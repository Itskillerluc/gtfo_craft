package io.github.itskillerluc.gtfo_craft.tileentity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.Optional;

public class TileEntityTripMine extends TileEntity implements ITickable {
    public static final int RANGE = 10;
    private int laserLength = RANGE;
    private EnumFacing direction = EnumFacing.NORTH;

    public TileEntityTripMine(){}

    public TileEntityTripMine(EnumFacing direction) {
        this.direction = direction;
    }

    @Override
    public void update() {
        if (world.getWorldTime() % 5 == 0) {
            laserLength = checkForObstruction();
        }
        Vec3d scale = new Vec3d(this.direction.getDirectionVec()).scale(this.laserLength);
        Optional<EntityLivingBase> target = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(getPos()).expand(scale.x, scale.y, scale.z))
                .stream().min(Comparator.comparingInt(entity -> (int) entity.getDistanceSq(pos)));

        target.ifPresent(entity -> {
            entity.attackEntityFrom(DamageSource.causeExplosionDamage(world.createExplosion(null, this.pos.getX(), this.pos.getY(), this.pos.getZ(), 5, false)), 20);
            world.setBlockToAir(pos);
        });
    }

    private int checkForObstruction() {
        for (int i = 1; i <= laserLength; i++) {
            BlockPos pos = this.getPos().offset(direction, i);
            if (!world.getBlockState(pos).getMaterial().isReplaceable()) {
                return i;
            }
        }
        return RANGE;
    }

    @Override
    public NBTTagCompound writeToNBT( NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setString("direction", this.direction.getName2());
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.direction = EnumFacing.byName(compound.getString("direction"));
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound tag = super.getUpdateTag();
        tag.setString("direction", this.direction.getName2());
        return tag;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        super.handleUpdateTag(tag);
        this.direction = EnumFacing.byName(tag.getString("direction"));
    }


    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, -1, getUpdateTag());
    }

    public EnumFacing getDirection() {
        return direction;
    }

    public int getLaserLength() {
        return laserLength;
    }

    public void setLaserLength(int laserLength) {
        this.laserLength = laserLength;
    }
}
