package io.github.itskillerluc.gtfo_craft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public class TileEntityBulkheadDoorHelper extends TileEntity {
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public enum Location {
        CORNERL,
        CORNERR,
        CENTER,
        TOP,
        LEFT,
        RIGHT
    }
    public BlockPos master;
    private Location location = Location.CENTER;

    public TileEntityBulkheadDoorHelper(BlockPos master, Location location) {
        this.master = master;
        this.setLocation(location);
    }

    public TileEntityBulkheadDoorHelper(){}

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("master", master == null ? new NBTTagCompound() : NBTUtil.createPosTag(master));
        compound.setInteger("location", getLocation().ordinal());
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        master = compound.getCompoundTag("master").hasNoTags() ? null : NBTUtil.getPosFromTag(compound.getCompoundTag("master"));
        setLocation(Location.values()[compound.getInteger("location")]);
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        readFromNBT(tag);
    }


    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, -1, getUpdateTag());
    }
}
