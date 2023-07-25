package io.github.itskillerluc.gtfo_craft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class BatteryPacket implements IMessage {
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

    public static class Handler implements IMessageHandler<BatteryPacket, IMessage> {
        @Override
        public IMessage onMessage(BatteryPacket message, MessageContext ctx) {
            Minecraft.getMinecraft().player.getEntityData().setBoolean("hasBattery", message.hasBattery);
            return null;
        }
    }
}
