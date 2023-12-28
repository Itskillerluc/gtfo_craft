package io.github.itskillerluc.gtfo_craft.tileentity;

import io.github.itskillerluc.gtfo_craft.block.BlockSpitter;
import io.github.itskillerluc.gtfo_craft.registry.BlockRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntitySpitter extends TileEntity implements ITickable {
    public int coolDown = 600;

    public int coolDownTimer;
    public int range = 2;


    public TileEntitySpitter(){}

    public TileEntitySpitter(int coolDown) {
        this.coolDown = coolDown;
    }

    @Override
    public void update() {
        if (!world.getBlockState(pos).getValue(BlockSpitter.CHARGED)) {
            coolDownTimer++;
            if (coolDownTimer >= coolDown) {
                if (!world.isRemote) {
                    world.setBlockState(pos, BlockRegistry.SPITTER.getDefaultState().withProperty(BlockSpitter.FACING, world.getBlockState(pos).getValue(BlockSpitter.FACING)).withProperty(BlockSpitter.CHARGED, true));
                }
            }
        } else {
            EntityPlayer player = world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), range * 2, false);
            if (player != null && player.getDistanceSq(pos.getX(), pos.getY(), pos.getZ()) < range * range) {
                world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 2, false);
                coolDownTimer = 0;
                world.setBlockState(pos, BlockRegistry.SPITTER.getDefaultState().withProperty(BlockSpitter.FACING, world.getBlockState(pos).getValue(BlockSpitter.FACING)).withProperty(BlockSpitter.CHARGED, false));
            } else if (player != null && player.getDistanceSq(pos.getX(), pos.getY(), pos.getZ()) < range * range * 2) {
                player.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1, 1);
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("coolDown", coolDown);
        compound.setInteger("coolDownTimer", coolDownTimer);
        compound.setInteger("range", range);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.coolDown = compound.getInteger("coolDown");
        this.coolDownTimer = compound.getInteger("coolDownTimer");
        this.range = compound.getInteger("range");
    }
}
