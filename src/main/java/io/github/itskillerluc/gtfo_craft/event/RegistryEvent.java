package io.github.itskillerluc.gtfo_craft.event;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.registry.BlockRegistry;
import io.github.itskillerluc.gtfo_craft.registry.EntityRegistry;
import io.github.itskillerluc.gtfo_craft.registry.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = GtfoCraft.MODID)
public class RegistryEvent {
    @SubscribeEvent
    public static void entityRegistry(net.minecraftforge.event.RegistryEvent.Register<EntityEntry> registryEvent) {
        EntityRegistry.registerEntities(registryEvent);
    }

    @SubscribeEvent
    public static void itemRegistry(net.minecraftforge.event.RegistryEvent.Register<Item> registryEvent) {
        ItemRegistry.registerItems(registryEvent);
    }

    @SubscribeEvent
    public static void blockRegistry(net.minecraftforge.event.RegistryEvent.Register<Block> registryEvent) {
        BlockRegistry.registerBlocks(registryEvent);
    }
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelRegister(ModelRegistryEvent event) {
        GtfoCraft.proxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.FOG), 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.TURRET_SLOW), 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.TURRET_MEDIUM), 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.TURRET_FAST), 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.GENERATOR), 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BATTERY), 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.TRIP_MINE), 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.SECURITY_DOOR_SMALL_CONTROLLER), 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.SECURITY_DOOR_LARGE_CONTROLLER), 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.COMMON_DOOR_LARGE_CONTROLLER), 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.COCOON), 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.FOG_TEMPORARY), 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.COMMON_DOOR_SMALL_CONTROLLER), 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.POSSESSED_SECURITY_DOOR_LARGE_CONTROLLER), 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.POSSESSED_SECURITY_DOOR_SMALL_CONTROLLER), 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.APEX_DOOR_LARGE_CONTROLLER), 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.APEX_DOOR_SMALL_CONTROLLER), 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BULKHEAD_DOOR_LARGE_CONTROLLER), 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BULKHEAD_DOOR_SMALL_CONTROLLER), 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.TERMINAL), 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.SPITTER), 0, "inventory");

        GtfoCraft.proxy.registerItemRenderer(ItemRegistry.FOG_REPELLER, 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(ItemRegistry.PELLET, 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(ItemRegistry.AMMO, 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(ItemRegistry.GLOW_STICK, 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(ItemRegistry.KEY, 0, "inventory");

        GtfoCraft.proxy.registerItemRenderer(ItemRegistry.C_FOAM_GRENADE, 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(ItemRegistry.C_FOAM_TRIPMINE, 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(ItemRegistry.I_2_LP_SYRINGE, 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(ItemRegistry.IIX_SYRINGE, 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(ItemRegistry.LOCK_MELTER, 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(ItemRegistry.LONG_RANGE_FLASHLIGHT, 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(ItemRegistry.DISINFECT_SUPPLY, 0, "inventory");
    }
}
