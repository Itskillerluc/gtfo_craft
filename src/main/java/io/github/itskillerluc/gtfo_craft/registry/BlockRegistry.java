package io.github.itskillerluc.gtfo_craft.registry;

import io.github.itskillerluc.gtfo_craft.block.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialTransparent;
import net.minecraftforge.event.RegistryEvent;

public class BlockRegistry {
    public static final Material FOG_MATERIAL = new MaterialTransparent(MapColor.AIR);
    public static final BlockFog FOG = new BlockFog();
    public static final BlockFogEmpty EMPTY_FOG = new BlockFogEmpty();
    public static final Block TURRET_SLOW = new BlockTurret(Material.IRON, MapColor.IRON, "turret_slow", 8, 40, 10).setBlockUnbreakable();
    public static final Block TURRET_MEDIUM = new BlockTurret(Material.IRON, MapColor.IRON, "turret_medium", 5, 25, 10).setBlockUnbreakable();
    public static final Block TURRET_FAST = new BlockTurret(Material.IRON, MapColor.IRON, "turret_fast", 2, 10, 10).setBlockUnbreakable();
    public static final BlockGlowStick GLOW_STICK_BLOCK = new BlockGlowStick();
    public static final BlockGenerator GENERATOR = new BlockGenerator(Material.IRON);
    public static final Block BATTERY = new BlockBattery().setBlockUnbreakable();
    public static final BlockTripMine TRIP_MINE = new BlockTripMine(Material.IRON, MapColor.IRON);
    public static final BlockDoorController SECURITY_DOOR_SMALL_CONTROLLER = new BlockSecurityDoorSmallController(Material.IRON, MapColor.IRON);
    public static final BlockSecurityDoorSmallHelper SECURITY_DOOR_SMALL_HELPER = new BlockSecurityDoorSmallHelper(Material.IRON, MapColor.IRON);
    public static final BlockSecurityDoorLargeController SECURITY_DOOR_LARGE_CONTROLLER = new BlockSecurityDoorLargeController(Material.IRON, MapColor.IRON);
    public static final BlockSecurityDoorLargeHelper SECURITY_DOOR_LARGE_HELPER = new BlockSecurityDoorLargeHelper(Material.IRON, MapColor.IRON);
    public static final BlockBreakableDoorSmallController BREAKABLE_DOOR_SMALL_CONTROLLER = new BlockBreakableDoorSmallController(Material.IRON, MapColor.IRON);
    public static final BlockBreakableDoorSmallHelper BREAKABLE_DOOR_SMALL_HELPER = new BlockBreakableDoorSmallHelper(Material.IRON, MapColor.IRON);
    public static final BlockBreakableDoorLargeController BREAKABLE_DOOR_LARGE_CONTROLLER = new BlockBreakableDoorLargeController(Material.IRON, MapColor.IRON);
    public static final BlockBreakableDoorLargeHelper BREAKABLE_DOOR_LARGE_HELPER = new BlockBreakableDoorLargeHelper(Material.IRON, MapColor.IRON);
    public static final BlockCocoon COCOON = new BlockCocoon(Material.WEB, MapColor.WHITE_STAINED_HARDENED_CLAY);
    public static final BlockFogTemporary FOG_TEMPORARY = new BlockFogTemporary();

    public static void registerBlocks(RegistryEvent.Register<Block> registryEvent) {
        registryEvent.getRegistry().register(FOG);
        registryEvent.getRegistry().register(EMPTY_FOG);
        registryEvent.getRegistry().register(GLOW_STICK_BLOCK);
        registryEvent.getRegistry().register(TURRET_SLOW);
        registryEvent.getRegistry().register(TURRET_MEDIUM);
        registryEvent.getRegistry().register(TURRET_FAST);
        registryEvent.getRegistry().register(BREAKABLE_DOOR_SMALL_CONTROLLER);
        registryEvent.getRegistry().register(BREAKABLE_DOOR_SMALL_HELPER);
        registryEvent.getRegistry().register(GENERATOR);
        registryEvent.getRegistry().register(BATTERY);
        registryEvent.getRegistry().register(TRIP_MINE);
        registryEvent.getRegistry().register(SECURITY_DOOR_SMALL_CONTROLLER);
        registryEvent.getRegistry().register(SECURITY_DOOR_SMALL_HELPER);
        registryEvent.getRegistry().register(SECURITY_DOOR_LARGE_CONTROLLER);
        registryEvent.getRegistry().register(SECURITY_DOOR_LARGE_HELPER);
        registryEvent.getRegistry().register(BREAKABLE_DOOR_LARGE_CONTROLLER);
        registryEvent.getRegistry().register(BREAKABLE_DOOR_LARGE_HELPER);
        registryEvent.getRegistry().register(COCOON);
        registryEvent.getRegistry().register(FOG_TEMPORARY);
    }
}
