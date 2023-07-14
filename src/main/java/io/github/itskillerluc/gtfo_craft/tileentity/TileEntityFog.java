package io.github.itskillerluc.gtfo_craft.tileentity;

import io.github.itskillerluc.gtfo_craft.registry.BlockRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityFog extends TileEntity implements ITickable {
    public static final int TOTAL_TIME = 200;

    public int time;

    @Override
    public void update() {
        time++;
        if (time >= TOTAL_TIME) {
            if (!world.isRemote) {
                world.setBlockState(pos, BlockRegistry.FOG.getDefaultState());
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("time", time);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.time = compound.getInteger("time");
    }
}
