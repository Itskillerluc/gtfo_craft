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
        GtfoCraft.proxy.registerItemRenderer(ItemRegistry.FOG_REPELLER, 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(ItemRegistry.PELLET, 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(ItemRegistry.AMMO, 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(ItemRegistry.GLOW_STICK, 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(ItemRegistry.BREAKABLE_DOOR, 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.TURRET_SLOW), 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.TURRET_MEDIUM), 0, "inventory");
        GtfoCraft.proxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.TURRET_FAST), 0, "inventory");
    }
}
