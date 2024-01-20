package io.github.itskillerluc.gtfo_craft.network;

import io.github.itskillerluc.gtfo_craft.entity.ModEntity;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class WakeUpPacket implements IMessage {
    public UUID uuid;

    public WakeUpPacket(UUID uuid) {
        this.uuid = uuid;
    }

    public WakeUpPacket() {}

    @Override
    public void fromBytes(ByteBuf buf) {
        uuid = new UUID(buf.readLong(), buf.readLong());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(uuid.getMostSignificantBits());
        buf.writeLong(uuid.getLeastSignificantBits());
    }

    public static class Handler implements IMessageHandler<WakeUpPacket, IMessage> {
        @Override
        public IMessage onMessage(WakeUpPacket message, MessageContext ctx) {
            ModEntity entity = (ModEntity) ctx.getServerHandler().player.getServerWorld().getEntityFromUuid(message.uuid);
            entity.getDataManager().set(ModEntity.SLEEPING, false);
            return null;
        }
    }
}
