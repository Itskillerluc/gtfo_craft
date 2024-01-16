package io.github.itskillerluc.gtfo_craft.registry;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.GtfoCraftCreativeTab;
import io.github.itskillerluc.gtfo_craft.item.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;

public class ItemRegistry {
    public static final Item FOG_REPELLER = new ItemFogRepeller().setRegistryName(new ResourceLocation(GtfoCraft.MODID, "fog_repeller"));
    public static final Item PELLET = new ItemPellet().setRegistryName(new ResourceLocation(GtfoCraft.MODID, "pellet"));
    public static final Item GLOW_STICK = new ItemGlowStick().setRegistryName(new ResourceLocation(GtfoCraft.MODID, "glow_stick"));
    public static final Item AMMO = basicItem("ammo");
    public static final Item KEY = new ItemKey();
    public static final Item C_FOAM_GRENADE = basicItem("c_foam_grenade");
    public static final Item C_FOAM_TRIPMINE = basicItem("c_foam_tripmine");
    public static final Item I_2_LP_SYRINGE = new ItemHealthSyringe();
    public static final Item IIX_SYRINGE = new ItemStrengthSyringe();
    public static final Item LOCK_MELTER = basicItem("lock_melter");
    public static final Item LONG_RANGE_FLASHLIGHT = basicItem("long_range_flashlight");
    public static final Item DISINFECT_SUPPLY = basicItem("disinfect_supply").setMaxStackSize(1);
    public static final Item TOOL_SUPPLY = new ItemToolSupply();
    public static final Item AMMO_SUPPLY = basicItem("ammo_supply").setMaxStackSize(1);
    public static final Item MEDICAL_SUPPLY = new ItemMedicalSupply();
    public static final Item.ToolMaterial HAMMER = EnumHelper.addToolMaterial("hammer", 0, 300, 0.1f, 10, 4);
    public static final Item HAMMER_GAVAL = new ItemHammer(HAMMER).setCreativeTab(GtfoCraftCreativeTab.INSTANCE).setRegistryName(new ResourceLocation(GtfoCraft.MODID, "hammer_gaval")).setUnlocalizedName("hammer_gaval");
    public static final Item HAMMER_SLEDGE = new ItemHammer(HAMMER).setCreativeTab(GtfoCraftCreativeTab.INSTANCE).setRegistryName(new ResourceLocation(GtfoCraft.MODID, "hammer_sledge")).setUnlocalizedName("hammer_sledge");

    public static void registerItems(RegistryEvent.Register<Item> registryEvent) {
        registryEvent.getRegistry().register(new ItemBlock(BlockRegistry.FOG).setRegistryName(BlockRegistry.FOG.getRegistryName()));
        registryEvent.getRegistry().register(new ItemBlock(BlockRegistry.TURRET_SLOW).setRegistryName(BlockRegistry.TURRET_SLOW.getRegistryName()));
        registryEvent.getRegistry().register(new ItemBlock(BlockRegistry.TURRET_MEDIUM).setRegistryName(BlockRegistry.TURRET_MEDIUM.getRegistryName()));
        registryEvent.getRegistry().register(new ItemBlock(BlockRegistry.TURRET_FAST).setRegistryName(BlockRegistry.TURRET_FAST.getRegistryName()));
        registryEvent.getRegistry().register(new ItemBlock(BlockRegistry.BATTERY).setRegistryName(BlockRegistry.BATTERY.getRegistryName()));
        registryEvent.getRegistry().register(new ItemBlock(BlockRegistry.GENERATOR).setRegistryName(BlockRegistry.GENERATOR.getRegistryName()));
        registryEvent.getRegistry().register(new ItemBlock(BlockRegistry.TRIP_MINE).setRegistryName(BlockRegistry.TRIP_MINE.getRegistryName()));
        registryEvent.getRegistry().register(new ItemBlock(BlockRegistry.SECURITY_DOOR_SMALL_CONTROLLER).setRegistryName(BlockRegistry.SECURITY_DOOR_SMALL_CONTROLLER.getRegistryName()));
        registryEvent.getRegistry().register(new ItemBlock(BlockRegistry.SECURITY_DOOR_LARGE_CONTROLLER).setRegistryName(BlockRegistry.SECURITY_DOOR_LARGE_CONTROLLER.getRegistryName()));
        registryEvent.getRegistry().register(new ItemBlock(BlockRegistry.COCOON).setRegistryName(BlockRegistry.COCOON.getRegistryName()));
        registryEvent.getRegistry().register(new ItemBlock(BlockRegistry.FOG_TEMPORARY).setRegistryName(BlockRegistry.FOG_TEMPORARY.getRegistryName()));
        registryEvent.getRegistry().register(new ItemBlock(BlockRegistry.COMMON_DOOR_SMALL_CONTROLLER).setRegistryName(BlockRegistry.COMMON_DOOR_SMALL_CONTROLLER.getRegistryName()));
        registryEvent.getRegistry().register(new ItemBlock(BlockRegistry.COMMON_DOOR_LARGE_CONTROLLER).setRegistryName(BlockRegistry.COMMON_DOOR_LARGE_CONTROLLER.getRegistryName()));
        registryEvent.getRegistry().register(new ItemBlock(BlockRegistry.POSSESSED_SECURITY_DOOR_LARGE_CONTROLLER).setRegistryName(BlockRegistry.POSSESSED_SECURITY_DOOR_LARGE_CONTROLLER.getRegistryName()));
        registryEvent.getRegistry().register(new ItemBlock(BlockRegistry.POSSESSED_SECURITY_DOOR_SMALL_CONTROLLER).setRegistryName(BlockRegistry.POSSESSED_SECURITY_DOOR_SMALL_CONTROLLER.getRegistryName()));
        registryEvent.getRegistry().register(new ItemBlock(BlockRegistry.APEX_DOOR_LARGE_CONTROLLER).setRegistryName(BlockRegistry.APEX_DOOR_LARGE_CONTROLLER.getRegistryName()));
        registryEvent.getRegistry().register(new ItemBlock(BlockRegistry.APEX_DOOR_SMALL_CONTROLLER).setRegistryName(BlockRegistry.APEX_DOOR_SMALL_CONTROLLER.getRegistryName()));
        registryEvent.getRegistry().register(new ItemBlock(BlockRegistry.BULKHEAD_DOOR_LARGE_CONTROLLER).setRegistryName(BlockRegistry.BULKHEAD_DOOR_LARGE_CONTROLLER.getRegistryName()));
        registryEvent.getRegistry().register(new ItemBlock(BlockRegistry.BULKHEAD_DOOR_SMALL_CONTROLLER).setRegistryName(BlockRegistry.BULKHEAD_DOOR_SMALL_CONTROLLER.getRegistryName()));
        registryEvent.getRegistry().register(new ItemBlock(BlockRegistry.TERMINAL).setRegistryName(BlockRegistry.TERMINAL.getRegistryName()));
        registryEvent.getRegistry().register(new ItemBlock(BlockRegistry.SPITTER).setRegistryName(BlockRegistry.SPITTER.getRegistryName()));

        registryEvent.getRegistry().register(FOG_REPELLER);
        registryEvent.getRegistry().register(PELLET);
        registryEvent.getRegistry().register(AMMO);
        registryEvent.getRegistry().register(GLOW_STICK);
        registryEvent.getRegistry().register(KEY);

        registryEvent.getRegistry().register(C_FOAM_GRENADE);
        registryEvent.getRegistry().register(C_FOAM_TRIPMINE);
        registryEvent.getRegistry().register(I_2_LP_SYRINGE);
        registryEvent.getRegistry().register(IIX_SYRINGE);
        registryEvent.getRegistry().register(LOCK_MELTER);
        registryEvent.getRegistry().register(LONG_RANGE_FLASHLIGHT);
        registryEvent.getRegistry().register(DISINFECT_SUPPLY);
        registryEvent.getRegistry().register(TOOL_SUPPLY);
        registryEvent.getRegistry().register(AMMO_SUPPLY);
        registryEvent.getRegistry().register(MEDICAL_SUPPLY);
        registryEvent.getRegistry().register(HAMMER_GAVAL);
        registryEvent.getRegistry().register(HAMMER_SLEDGE);

        TileEntityRegistry.registerTiles();
    }

    static Item basicItem(String name) {
        return new Item().setRegistryName(new ResourceLocation(GtfoCraft.MODID, name)).setUnlocalizedName(name).setCreativeTab(GtfoCraftCreativeTab.INSTANCE);
    }
}
