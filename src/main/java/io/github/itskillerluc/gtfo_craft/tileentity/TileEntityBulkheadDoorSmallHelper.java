package io.github.itskillerluc.gtfo_craft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TileEntityBulkheadDoorSmallHelper extends TileEntity {
    public BlockPos master;

    public TileEntityBulkheadDoorSmallHelper(BlockPos master) {
        this.master = master;
    }

    public TileEntityBulkheadDoorSmallHelper(){}

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("master", NBTUtil.createPosTag(master));
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        master = NBTUtil.getPosFromTag(compound.getCompoundTag("master"));
        super.readFromNBT(compound);
    }
}
