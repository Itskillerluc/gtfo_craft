package io.github.itskillerluc.gtfo_craft.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;

public abstract class TileEntityBulkheadDoor extends TileEntity {
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

    public abstract void open();
}
