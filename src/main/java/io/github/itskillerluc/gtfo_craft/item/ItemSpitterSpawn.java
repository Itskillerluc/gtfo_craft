package io.github.itskillerluc.gtfo_craft.item;

import io.github.itskillerluc.gtfo_craft.entity.EntitySpitter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ItemSpitterSpawn extends Item {
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack itemstack = player.getHeldItem(hand);
        BlockPos blockpos = pos.offset(facing);

        if (player.canPlayerEdit(blockpos, facing, itemstack))
        {
            EntitySpitter spitter = this.createEntity(worldIn, blockpos, facing);

            if (spitter != null)
            {
                if (!worldIn.isRemote)
                {
                    spitter.playSound(SoundEvents.BLOCK_LAVA_POP, 1, 1);
                    worldIn.spawnEntity(spitter);
                }

                itemstack.shrink(1);
            }

            return EnumActionResult.SUCCESS;
        }
        else
        {
            return EnumActionResult.FAIL;
        }
    }

    @Nullable
    private EntitySpitter createEntity(World worldIn, BlockPos pos, EnumFacing clickedSide)
    {
        EntitySpitter spitter = new EntitySpitter(worldIn);
        spitter.setFacing(clickedSide);
        switch (clickedSide) {
            case DOWN:
                spitter.setPosition(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
                break;
            case UP:
                spitter.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                break;
            case NORTH:
                spitter.setPosition(pos.getX() + 0.5, pos.getY() + 0.25, pos.getZ() + 0.75f);
                break;
            case SOUTH:
                spitter.setPosition(pos.getX() + 0.5, pos.getY() + 0.25, pos.getZ() + 0.25);
                break;
            case WEST:
                spitter.setPosition(pos.getX() + 0.75, pos.getY() + 0.25, pos.getZ() + 0.5);
                break;
            case EAST:
                spitter.setPosition(pos.getX() + 0.25, pos.getY() + 0.25, pos.getZ() + 0.5);
                break;
        }
        return spitter;
    }
}