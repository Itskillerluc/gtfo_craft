package io.github.itskillerluc.gtfo_craft.network;

import io.github.itskillerluc.gtfo_craft.registry.BlockRegistry;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityBulkheadDoorHelper;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityBulkheadDoorSmall;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SmallDoorPacket implements IMessage {
    public BlockPos pos;

    public SmallDoorPacket() {}
    public SmallDoorPacket(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = NBTUtil.getPosFromTag(ByteBufUtils.readTag(buf).getCompoundTag("pos"));
    }

    @Override
    public void toBytes(ByteBuf buf) {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setTag("pos", NBTUtil.createPosTag(pos));
        ByteBufUtils.writeTag(buf, compound);
    }

    public static class Handler implements IMessageHandler<SmallDoorPacket, IMessage> {
        @Override
        public IMessage onMessage(SmallDoorPacket message, MessageContext ctx) {
            if (Minecraft.getMinecraft().world.getBlockState(message.pos).getBlock().equals(BlockRegistry.BULKHEAD_DOOR_SMALL_CONTROLLER)) {
                ((TileEntityBulkheadDoorSmall) Minecraft.getMinecraft().world.getTileEntity(message.pos)).open();
            } else if (Minecraft.getMinecraft().world.getBlockState(message.pos).getBlock().equals(BlockRegistry.BULKHEAD_DOOR_SMALL_HELPER)) {
                ((TileEntityBulkheadDoorSmall) Minecraft.getMinecraft().world.getTileEntity(((TileEntityBulkheadDoorHelper) Minecraft.getMinecraft().world.getTileEntity(message.pos)).master)).open();
            }
            return null;
        }
    }
}
