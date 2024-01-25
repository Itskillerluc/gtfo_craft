package io.github.itskillerluc.gtfo_craft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BatteryPacket implements IMessage,IMessageHandler<BatteryPacket, IMessage> {
    public boolean hasBattery;

    public BatteryPacket() {}
    public BatteryPacket(boolean hasBattery) {
        this.hasBattery = hasBattery;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        hasBattery = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(hasBattery);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public IMessage onMessage(BatteryPacket message, MessageContext ctx) {
        Minecraft.getMinecraft().player.getEntityData().setBoolean("hasBattery", message.hasBattery);
        return null;
    }
}
