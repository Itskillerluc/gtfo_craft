package io.github.itskillerluc.gtfo_craft.registry;

import io.github.itskillerluc.gtfo_craft.block.BlockFog;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialTransparent;
import net.minecraftforge.event.RegistryEvent;

public class BlockRegistry {
    public static final Material FOG_MATERIAL = new MaterialTransparent(MapColor.AIR);
    public static final BlockFog FOG = new BlockFog();

    public static void registerBlocks(RegistryEvent.Register<Block> registryEvent) {
        registryEvent.getRegistry().register(FOG);
    }
}
