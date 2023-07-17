package io.github.itskillerluc.gtfo_craft.tileentity;

import io.github.itskillerluc.gtfo_craft.entity.EntityGlowStick;
import io.github.itskillerluc.gtfo_craft.registry.BlockRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityGlowStick extends TileEntity implements ITickable {
    public static final int TOTAL_TIME = 400;

    public int time;

    @Override
    public void update() {
        time++;
        if (time >= TOTAL_TIME) {
            if (!world.isRemote) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
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
