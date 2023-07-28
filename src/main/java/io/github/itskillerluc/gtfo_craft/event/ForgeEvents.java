package io.github.itskillerluc.gtfo_craft.event;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.block.BlockGenerator;
import io.github.itskillerluc.gtfo_craft.network.BatteryPacket;
import io.github.itskillerluc.gtfo_craft.network.PacketHandler;
import io.github.itskillerluc.gtfo_craft.registry.BlockRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
}
