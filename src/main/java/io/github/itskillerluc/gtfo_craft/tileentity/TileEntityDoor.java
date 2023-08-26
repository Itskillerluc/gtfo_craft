package io.github.itskillerluc.gtfo_craft.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.UUID;

public abstract class TileEntityDoor extends TileEntity {
    private UUID key;
    private boolean locked = false;
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

    public abstract void open();

    public UUID generateKey() {
        key = UUID.randomUUID();
        locked = true;
        return key;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        if (key != null) {
            compound.setTag("key", NBTUtil.createUUIDTag(key));
        }
        compound.setBoolean("locked", locked);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("key")) {
            this.key = NBTUtil.getUUIDFromTag(compound.getCompoundTag("key"));
        }
        locked = compound.getBoolean("locked");
    }

    public boolean isLocked() {
        return locked;
    }

    public boolean unlock(ItemStack key) {
        if (this.key != null && key.hasTagCompound() && key.getTagCompound().hasKey("key")) {
            if (this.key.equals(NBTUtil.getUUIDFromTag(key.getTagCompound().getCompoundTag("key")))) {
                this.locked = false;
                return true;
            }
        }
        return false;
    }
}
