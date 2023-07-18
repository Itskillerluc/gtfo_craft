package io.github.itskillerluc.gtfo_craft.tileentity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

import java.util.Comparator;
import java.util.Optional;

public class TileEntityTurret extends TileEntity implements ITickable {
    public final int damage;
    public final int speed;
    public final int range;
    private final EnumFacing direction;
    private int timer = 0;

    public TileEntityTurret(int damage, int speed, int range, EnumFacing direction) {
        this.damage = damage;
        this.speed = speed;
        this.direction = direction;
        this.range = range;
    }

    @Override
    public void update() {
        if (timer >= speed) {
            int scalar = 0;
            for (int i = 1; i <= range; i++) {
                if (world.isAirBlock(getPos().offset(direction, i))) {
                    scalar++;
                } else {
                    break;
                }
            }
            Vec3d expansion = new Vec3d(direction.getDirectionVec()).scale(scalar);
            Optional<EntityLivingBase> target = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(getPos()).expand(expansion.x, expansion.y, expansion.z))
                    .stream().min(Comparator.comparingInt(entity -> (int) entity.getDistanceSq(pos)));

            target.ifPresent(entityLiving -> {
                entityLiving.attackEntityFrom(DamageSource.GENERIC, damage);
                world.playSound(((double) pos.getX()), ((double) pos.getY()), ((double) pos.getZ()), SoundEvents.BLOCK_NOTE_SNARE, SoundCategory.BLOCKS, 1, 1, false);
                timer = 0;
            });
        }
        timer++;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("timer", this.timer);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.timer = compound.getInteger("timer");
    }
}
