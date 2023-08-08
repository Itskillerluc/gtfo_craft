package io.github.itskillerluc.gtfo_craft.event;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.block.BlockGenerator;
import io.github.itskillerluc.gtfo_craft.data.Scan;
import io.github.itskillerluc.gtfo_craft.data.ScanWorldSavedData;
import io.github.itskillerluc.gtfo_craft.network.BatteryPacket;
import io.github.itskillerluc.gtfo_craft.network.PacketHandler;
import io.github.itskillerluc.gtfo_craft.network.SyncScanPacket;
import io.github.itskillerluc.gtfo_craft.registry.BlockRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSenderWrapper;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = GtfoCraft.MODID)
public class ForgeEvents {
    @SubscribeEvent
    public static void playerClone(PlayerEvent.Clone event) {
        if(!event.getOriginal().getEntityWorld().isRemote) {
            event.getEntityPlayer().getEntityData().setBoolean(GtfoCraft.MODID + "hasBattery",
                    event.getOriginal().getEntityData().getBoolean(GtfoCraft.MODID + "hasBattery"));
        }
    }

    @SubscribeEvent
    public static void playerLoad(LivingEvent.LivingUpdateEvent event) {
        if (!event.getEntityLiving().getEntityWorld().isRemote && event.getEntityLiving() instanceof EntityPlayer && event.getEntityLiving().ticksExisted == 1) {
            PacketHandler.sendTo((EntityPlayerMP) event.getEntity(), new BatteryPacket(event.getEntity().getEntityData().getBoolean("hasBattery")));
        }
    }

    @SubscribeEvent
    public static void rightClick(PlayerInteractEvent.RightClickBlock event) {
        if (event.getEntityPlayer().getEntityData().getBoolean("hasBattery")) {
            if (event.getWorld().getBlockState(event.getPos()).getBlock().equals(BlockRegistry.GENERATOR)) {
                if (!event.getWorld().getBlockState(event.getPos()).getValue(BlockGenerator.POWERED)) {
                    event.getWorld().setBlockState(event.getPos(), BlockRegistry.GENERATOR.getDefaultState().withProperty(BlockGenerator.POWERED, true).withProperty(BlockGenerator.FACING, event.getWorld().getBlockState(event.getPos()).getValue(BlockGenerator.FACING)));
                    event.getEntityPlayer().getEntityData().setBoolean("hasBattery", false);
                }
            } else if (event.getEntityPlayer().isSneaking()) {
                if (event.getWorld().getBlockState(event.getPos()).getMaterial().isReplaceable()) {
                    event.getWorld().setBlockToAir(event.getPos());
                    event.getWorld().setBlockState(event.getPos(), BlockRegistry.BATTERY.getDefaultState());
                    event.getEntityPlayer().getEntityData().setBoolean("hasBattery", false);
                } else if (event.getFace() != null) {
                    event.getWorld().setBlockState(event.getPos().offset(event.getFace()), BlockRegistry.BATTERY.getDefaultState());
                    event.getEntityPlayer().getEntityData().setBoolean("hasBattery", false);
                }
            } else {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void rightClickItem(PlayerInteractEvent.RightClickItem event) {
        if (event.getEntityPlayer().getEntityData().getBoolean("hasBattery")) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void breakBlock(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getEntityPlayer().getEntityData().getBoolean("hasBattery")) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onUpdate(TickEvent.WorldTickEvent event) {
        World world = event.world;
        if (world == null) return;
        if (world.getWorldTime() % 5 == 0) {

            List<Scan> scanList = ScanWorldSavedData.get(world).scanList;
            scanList.removeIf(scan -> scan.getTimer() > scan.getTime());
            for (Scan scan : scanList) {
                if (scan.getTimer() == scan.getTime()) {
                    List<EntityPlayer> entitiesWithinAABB = event.world.getMinecraftServer().getPlayerList().getPlayers()
                            .stream().filter(entity -> scan.getAABB().contains(new Vec3d(entity.posX, entity.posY + 1, entity.posZ))).collect(Collectors.toList());
                    if (entitiesWithinAABB.size() >= scan.getPlayersNeeded()) {
                        String command = scan.getCommand();
                        ICommandSender icommandsender = CommandSenderWrapper.create(entitiesWithinAABB.get(0)).withEntity(entitiesWithinAABB.get(0), new Vec3d(entitiesWithinAABB.get(0).posX, entitiesWithinAABB.get(0).posY, entitiesWithinAABB.get(0).posZ)).withSendCommandFeedback(world.getGameRules().getBoolean("commandBlockOutput"));
                        ICommandManager icommandmanager = world.getMinecraftServer().getCommandManager();

                        try {
                            int j = icommandmanager.executeCommand(icommandsender, command);

                            if (j < 1) {
                                throw new CommandException("commands.execute.allInvocationsFailed", command);
                            }
                        } catch (CommandException exception) {
                            LogManager.getLogger().log(Level.ERROR, exception.getMessage());
                        }
                    }
                }
                scan.setTimer(scan.getTimer() + 1);
            }
            ScanWorldSavedData.get(world).markDirty();
            for (EntityPlayer playerEntity : event.world.playerEntities) {
                PacketHandler.sendTo((EntityPlayerMP) playerEntity, new SyncScanPacket(scanList));
            }
        }
    }
}
