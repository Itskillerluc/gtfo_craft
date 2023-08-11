package io.github.itskillerluc.gtfo_craft.registry;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.GtfoCraftCreativeTab;
import io.github.itskillerluc.gtfo_craft.item.ItemFogRepeller;
import io.github.itskillerluc.gtfo_craft.item.ItemGlowStick;
import io.github.itskillerluc.gtfo_craft.item.ItemPellet;
import io.github.itskillerluc.gtfo_craft.item.ItemSpitterSpawn;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDoor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;

public class ItemRegistry {
    public static final Item FOG_REPELLER = new ItemFogRepeller().setRegistryName(new ResourceLocation(GtfoCraft.MODID, "fog_repeller"));
    public static final Item PELLET = new ItemPellet().setRegistryName(new ResourceLocation(GtfoCraft.MODID, "pellet"));
    public static final Item GLOW_STICK = new ItemGlowStick().setRegistryName(new ResourceLocation(GtfoCraft.MODID, "glow_stick"));
    public static final Item AMMO = new Item().setCreativeTab(GtfoCraftCreativeTab.INSTANCE).setRegistryName(new ResourceLocation(GtfoCraft.MODID, "ammo")).setUnlocalizedName("ammo");
    public static final Item BREAKABLE_DOOR = new ItemDoor(BlockRegistry.BREAKABLE_DOOR).setCreativeTab(GtfoCraftCreativeTab.INSTANCE).setRegistryName(new ResourceLocation(GtfoCraft.MODID, "breakable_door")).setUnlocalizedName("breakable_door");
    public static final Item SPITTER = new ItemSpitterSpawn().setRegistryName(new ResourceLocation(GtfoCraft.MODID, "spitter")).setUnlocalizedName("spitter").setCreativeTab(GtfoCraftCreativeTab.INSTANCE);

    public static void registerItems(RegistryEvent.Register<Item> registryEvent) {
        registryEvent.getRegistry().register(new ItemBlock(BlockRegistry.FOG).setRegistryName(BlockRegistry.FOG.getRegistryName()));
        registryEvent.getRegistry().register(new ItemBlock(BlockRegistry.TURRET_SLOW).setRegistryName(BlockRegistry.TURRET_SLOW.getRegistryName()));
        registryEvent.getRegistry().register(new ItemBlock(BlockRegistry.TURRET_MEDIUM).setRegistryName(BlockRegistry.TURRET_MEDIUM.getRegistryName()));
        registryEvent.getRegistry().register(new ItemBlock(BlockRegistry.TURRET_FAST).setRegistryName(BlockRegistry.TURRET_FAST.getRegistryName()));
        registryEvent.getRegistry().register(new ItemBlock(BlockRegistry.BATTERY).setRegistryName(BlockRegistry.BATTERY.getRegistryName()));
        registryEvent.getRegistry().register(new ItemBlock(BlockRegistry.GENERATOR).setRegistryName(BlockRegistry.GENERATOR.getRegistryName()));
        registryEvent.getRegistry().register(new ItemBlock(BlockRegistry.TRIP_MINE).setRegistryName(BlockRegistry.TRIP_MINE.getRegistryName()));
        registryEvent.getRegistry().register(new ItemBlock(BlockRegistry.BULKHEAD_DOOR_SMALL_CONTROLLER).setRegistryName(BlockRegistry.BULKHEAD_DOOR_SMALL_CONTROLLER.getRegistryName()));
        registryEvent.getRegistry().register(new ItemBlock(BlockRegistry.BULKHEAD_DOOR_SMALL_HELPER).setRegistryName(BlockRegistry.BULKHEAD_DOOR_SMALL_HELPER.getRegistryName()));
        registryEvent.getRegistry().register(new ItemBlock(BlockRegistry.COCOON).setRegistryName(BlockRegistry.COCOON.getRegistryName()));
        registryEvent.getRegistry().register(FOG_REPELLER);
        registryEvent.getRegistry().register(PELLET);
        registryEvent.getRegistry().register(AMMO);
        registryEvent.getRegistry().register(GLOW_STICK);
        registryEvent.getRegistry().register(BREAKABLE_DOOR);
        registryEvent.getRegistry().register(SPITTER);

        TileEntityRegistry.registerTiles();
    }
}
