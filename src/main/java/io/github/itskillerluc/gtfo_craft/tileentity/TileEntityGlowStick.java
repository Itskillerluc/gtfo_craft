package io.github.itskillerluc.gtfo_craft.tileentity;

import io.github.itskillerluc.gtfo_craft.entity.EntityGlowStick;
import io.github.itskillerluc.gtfo_craft.registry.BlockRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityGlowStick extends TileEntity implements ITickable {
    @Override
    public void update() {
        if (world.getEntitiesWithinAABB(EntityGlowStick.class, new AxisAlignedBB(getPos())).isEmpty()) {
            world.setBlockState(getPos(), Blocks.AIR.getDefaultState());
        }
    }
}
