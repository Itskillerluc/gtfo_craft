package io.github.itskillerluc.gtfo_craft.network;

import com.google.common.collect.Lists;
import io.github.itskillerluc.gtfo_craft.registry.BlockRegistry;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityCommonDoorLarge;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityCommonDoorSmall;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityDoorHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.List;

public class BreakDoorPacket implements IMessage,IMessageHandler<BreakDoorPacket, IMessage> {
    public static final List<Block> CONTROLLERS = Lists.newArrayList(BlockRegistry.COMMON_DOOR_SMALL_CONTROLLER, BlockRegistry.COMMON_DOOR_LARGE_CONTROLLER);
    public static final List<Block> HELPERS = Lists.newArrayList(BlockRegistry.COMMON_DOOR_SMALL_HELPER, BlockRegistry.COMMON_DOOR_LARGE_HELPER);
    public BlockPos pos;

    public BreakDoorPacket() {}
    public BreakDoorPacket(BlockPos pos) {
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

    @Override
    public IMessage onMessage(BreakDoorPacket message, MessageContext ctx) {
        TileEntity entity = ctx.getServerHandler().player.getServerWorld().getTileEntity(message.pos);
        if (entity instanceof TileEntityCommonDoorLarge) {
            ((TileEntityCommonDoorLarge) entity).destroy();
        } else if (entity instanceof TileEntityCommonDoorSmall) {
            ((TileEntityCommonDoorSmall) entity).destroy();
        } else if (entity instanceof TileEntityDoorHelper) {
            TileEntity te = ctx.getServerHandler().player.getServerWorld().getTileEntity(((TileEntityDoorHelper) entity).master);
            if (te instanceof TileEntityCommonDoorSmall) {
                ((TileEntityCommonDoorSmall) te).destroy();
            } else if (te instanceof TileEntityCommonDoorLarge) {
                ((TileEntityCommonDoorLarge) te).destroy();
            }
        }
        return null;
    }
}
