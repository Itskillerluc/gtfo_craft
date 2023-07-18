package io.github.itskillerluc.gtfo_craft.registry;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.GtfoCraftCreativeTab;
import io.github.itskillerluc.gtfo_craft.item.ItemFogRepeller;
import io.github.itskillerluc.gtfo_craft.item.ItemGlowStick;
import io.github.itskillerluc.gtfo_craft.item.ItemPellet;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;

public class ItemRegistry {
    public static final Item FOG_REPELLER = new ItemFogRepeller().setRegistryName(new ResourceLocation(GtfoCraft.MODID, "fog_repeller"));
    public static final Item PELLET = new ItemPellet().setRegistryName(new ResourceLocation(GtfoCraft.MODID, "pellet"));
    public static final Item GLOW_STICK = new ItemGlowStick().setRegistryName(new ResourceLocation(GtfoCraft.MODID, "glow_stick"));
    public static final Item AMMO = new Item().setCreativeTab(GtfoCraftCreativeTab.INSTANCE).setRegistryName(new ResourceLocation(GtfoCraft.MODID, "ammo")).setUnlocalizedName("ammo");

    public static void registerItems(RegistryEvent.Register<Item> registryEvent) {
        registryEvent.getRegistry().register(new ItemBlock(BlockRegistry.FOG).setRegistryName(BlockRegistry.FOG.getRegistryName()));
        registryEvent.getRegistry().register(new ItemBlock(BlockRegistry.TURRET_SLOW).setRegistryName(BlockRegistry.TURRET_SLOW.getRegistryName()));
        registryEvent.getRegistry().register(FOG_REPELLER);
        registryEvent.getRegistry().register(PELLET);
        registryEvent.getRegistry().register(AMMO);
        registryEvent.getRegistry().register(GLOW_STICK);

        TileEntityRegistry.registerTiles();
    }
}
