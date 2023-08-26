package io.github.itskillerluc.gtfo_craft.item;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.GtfoCraftCreativeTab;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityDoor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemKey extends Item {
    public ItemKey() {
        this.maxStackSize = 1;
        this.setCreativeTab(GtfoCraftCreativeTab.INSTANCE);
        setRegistryName(new ResourceLocation(GtfoCraft.MODID, "key"));
        setUnlocalizedName("key");
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.getTileEntity(pos) instanceof TileEntityDoor) {
            TileEntityDoor door = (TileEntityDoor) worldIn.getTileEntity(pos);
            ItemStack heldItem = player.getHeldItem(hand);
            if (heldItem.hasTagCompound() && heldItem.getTagCompound().hasKey("key")) {
                if (door.unlock(heldItem)) {
                    player.sendStatusMessage(new TextComponentString("You unlocked the door!"), true);
                    if (!heldItem.getTagCompound().hasKey("reuseable") || !heldItem.getTagCompound().getBoolean("reusable")) {
                        heldItem.shrink(1);
                    }
                    return EnumActionResult.SUCCESS;
                } else {
                    player.sendStatusMessage(new TextComponentString("This key doesn't seem to fit..."), true);
                    return EnumActionResult.FAIL;
                }
            } else {
                NBTTagCompound compound = new NBTTagCompound();
                compound.setTag("key", NBTUtil.createUUIDTag(door.generateKey()));
                heldItem.setTagCompound(compound);
                return EnumActionResult.SUCCESS;
            }
        }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }
}
