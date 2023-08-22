package io.github.itskillerluc.gtfo_craft.network;

import com.google.common.collect.Lists;
import io.github.itskillerluc.gtfo_craft.registry.BlockRegistry;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityBulkheadDoor;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityBulkheadDoorHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.List;
import java.util.function.Supplier;

public class BulkheadDoorPacket implements IMessage {
    public static final List<Block> CONTROLLERS = Lists.newArrayList(BlockRegistry.BREAKABLE_DOOR_CONTROLLER, BlockRegistry.BULKHEAD_DOOR_SMALL_CONTROLLER, BlockRegistry.BULKHEAD_DOOR_LARGE_CONTROLLER);
    public static final List<Block> HELPERS = Lists.newArrayList(BlockRegistry.BREAKABLE_DOOR_HELPER, BlockRegistry.BULKHEAD_DOOR_SMALL_HELPER, BlockRegistry.BULKHEAD_DOOR_LARGE_HELPER);
    public BlockPos pos;

    public BulkheadDoorPacket() {}
    public BulkheadDoorPacket(BlockPos pos) {
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

    public static class Handler implements IMessageHandler<BulkheadDoorPacket, IMessage> {
        @Override
        public IMessage onMessage(BulkheadDoorPacket message, MessageContext ctx) {
            if (CONTROLLERS.contains(Minecraft.getMinecraft().world.getBlockState(message.pos).getBlock())) {
                ((TileEntityBulkheadDoor) Minecraft.getMinecraft().world.getTileEntity(message.pos)).open();
            } else if (HELPERS.contains(Minecraft.getMinecraft().world.getBlockState(message.pos).getBlock())) {
                ((TileEntityBulkheadDoor) Minecraft.getMinecraft().world.getTileEntity(((TileEntityBulkheadDoorHelper) Minecraft.getMinecraft().world.getTileEntity(message.pos)).master)).open();
            }
            return null;
        }
    }
}
