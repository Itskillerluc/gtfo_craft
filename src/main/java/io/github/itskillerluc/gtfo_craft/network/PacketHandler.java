package io.github.itskillerluc.gtfo_craft.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
    private static final SimpleNetworkWrapper HANDLER = new SimpleNetworkWrapper("gtfo_channel");

    public static void init() {
        int id = 0;
        HANDLER.registerMessage(BatteryPacket.class, BatteryPacket.class, id++, Side.CLIENT);
        HANDLER.registerMessage(SyncScanPacket.class, SyncScanPacket.class, id++, Side.CLIENT);
        HANDLER.registerMessage(DoorPacket.class, DoorPacket.class, id++, Side.CLIENT);
        HANDLER.registerMessage(BreakDoorPacket.class, BreakDoorPacket.class, id++, Side.SERVER);
        HANDLER.registerMessage(WakeUpPacket.class, WakeUpPacket.class, id++, Side.SERVER);
    }

    /**
     * Send message to all within 64 blocks that have this chunk loaded
     */
    public static void sendToNearby(World world, BlockPos pos, IMessage toSend) {
        if(world instanceof WorldServer) {
            WorldServer ws = (WorldServer) world;

            for (EntityPlayer player : ws.playerEntities) {
                EntityPlayerMP playerMP = (EntityPlayerMP) player;

                if (playerMP.getDistanceSq(pos) < 64 * 64
                        && ws.getPlayerChunkMap().isPlayerWatchingChunk(playerMP, pos.getX() >> 4, pos.getZ() >> 4)) {
                    HANDLER.sendTo(toSend, playerMP);
                }
            }

        }
    }

    public static void sendToNearby(World world, Entity e, IMessage toSend) {
        sendToNearby(world, new BlockPos(e), toSend);
    }

    public static void sendTo(EntityPlayerMP playerMP, IMessage toSend) {
        HANDLER.sendTo(toSend, playerMP);
    }

    public static void sendToServer(IMessage msg) {
        HANDLER.sendToServer(msg);
    }

    private PacketHandler() {}
}
