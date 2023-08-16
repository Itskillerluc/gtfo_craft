package io.github.itskillerluc.gtfo_craft.tileentity;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityFogTemporary extends TileEntity implements ITickable {
    public int totalTime = 200;

    public int time;

    public TileEntityFogTemporary(int totalTime) {
        this.totalTime = totalTime;
    }

    public TileEntityFogTemporary() {
    }

    @Override
    public void update() {
        time++;
        if (time >= totalTime) {
            if (!world.isRemote) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("time", time);
        compound.setInteger("totalTime", totalTime);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.time = compound.getInteger("time");
        this.totalTime = compound.getInteger("totalTime");
    }
}
