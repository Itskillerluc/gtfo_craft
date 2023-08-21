package io.github.itskillerluc.gtfo_craft.registry;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.tileentity.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityRegistry {
    public static void registerTiles() {
        registerTile(TileEntityFog.class, "tile_fog");
        registerTile(TileEntityGlowStick.class, "glow_stick");
        registerTile(TileEntityTurret.class, "turret");
        registerTile(TileEntityTripMine.class, "trip_mine");
        registerTile(TileEntityBulkheadDoorSmall.class, "bulkhead_door_small");
        registerTile(TileEntityCocoon.class, "cocoon");
        registerTile(TileEntityBulkheadDoorHelper.class, "bulkhead_door_helper");
        registerTile(TileEntityBulkheadDoorLarge.class, "bulkhead_door_large");
        registerTile(TileEntityBreakableDoor.class, "breakable_door");
        registerTile(TileEntityFogTemporary.class, "fog_temporary");
    }

    private static void registerTile(Class<? extends TileEntity> clazz, String key) {
        GameRegistry.registerTileEntity(clazz, new ResourceLocation(GtfoCraft.MODID, key));
    }
}
