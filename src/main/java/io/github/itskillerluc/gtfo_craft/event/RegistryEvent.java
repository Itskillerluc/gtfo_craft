package io.github.itskillerluc.gtfo_craft.event;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.registry.BlockRegistry;
import io.github.itskillerluc.gtfo_craft.registry.EntityRegistry;
import io.github.itskillerluc.gtfo_craft.registry.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;

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
}
