
package io.github.itskillerluc.gtfo_craft.network;

import io.github.itskillerluc.gtfo_craft.data.Scan;
import io.github.itskillerluc.gtfo_craft.data.ScanWorldSavedData;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.ArrayList;
import java.util.List;

public class SyncScanPacket implements IMessage {
    public List<Scan> scans;

    public SyncScanPacket() {}
    public SyncScanPacket(List<Scan> scans) {
        this.scans = scans;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        scans = new ArrayList<>();
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            scans.add(Scan.fromTag(ByteBufUtils.readTag(buf)));
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(scans.size());
        for (Scan scan : scans) {
            ByteBufUtils.writeTag(buf, scan.toTag());
        }
    }

    public static class Handler implements IMessageHandler<SyncScanPacket, IMessage> {
        @Override
        public IMessage onMessage(SyncScanPacket message, MessageContext ctx) {
            ScanWorldSavedData.get(Minecraft.getMinecraft().world).scanList.clear();
            ScanWorldSavedData.get(Minecraft.getMinecraft().world).scanList.addAll(message.scans);
            return null;
        }
    }
}
