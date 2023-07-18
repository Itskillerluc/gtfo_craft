package io.github.itskillerluc.gtfo_craft.registry;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityFog;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityGlowStick;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityTurret;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityRegistry {
    public static void registerTiles() {
        registerTile(TileEntityFog.class, "tile_fog");
        registerTile(TileEntityGlowStick.class, "glow_stick");
        registerTile(TileEntityTurret.class, "turret");
    }

    private static void registerTile(Class<? extends TileEntity> clazz, String key) {
        GameRegistry.registerTileEntity(clazz, new ResourceLocation(GtfoCraft.MODID, key));
    }
}
