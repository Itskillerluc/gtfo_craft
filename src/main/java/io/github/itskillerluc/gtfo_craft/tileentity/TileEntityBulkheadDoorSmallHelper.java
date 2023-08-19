package io.github.itskillerluc.gtfo_craft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TileEntityBulkheadDoorSmallHelper extends TileEntity {
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

    public TileEntityBulkheadDoorSmallHelper(BlockPos master, Location location) {
        this.master = master;
        this.setLocation(location);
    }

    public TileEntityBulkheadDoorSmallHelper(){}

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("master", NBTUtil.createPosTag(master));
        compound.setInteger("locaiton", getLocation().ordinal());
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        master = NBTUtil.getPosFromTag(compound.getCompoundTag("master"));
        setLocation(Location.values()[compound.getInteger("location")]);
        super.readFromNBT(compound);
    }
}
