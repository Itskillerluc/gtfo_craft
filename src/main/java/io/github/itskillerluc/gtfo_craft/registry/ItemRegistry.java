package io.github.itskillerluc.gtfo_craft.registry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;

public class ItemRegistry {
    public static void registerItems(RegistryEvent.Register<Item> registryEvent) {
        registryEvent.getRegistry().register(new ItemBlock(BlockRegistry.FOG).setRegistryName(BlockRegistry.FOG.getRegistryName()));
    }
}
