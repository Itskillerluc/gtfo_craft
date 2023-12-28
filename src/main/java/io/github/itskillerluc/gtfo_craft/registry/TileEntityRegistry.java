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
        registerTile(TileEntitySecurityDoorSmall.class, "security_door_small");
        registerTile(TileEntitySecurityDoorLarge.class, "security_door_large");
        registerTile(TileEntityDoorHelper.class, "door_helper");
        registerTile(TileEntityCommonDoorLarge.class, "common_door_large");
        registerTile(TileEntityCommonDoorSmall.class, "common_door_small");
        registerTile(TileEntityFogTemporary.class, "fog_temporary");
        registerTile(TileEntityPossessedSecurityDoorLarge.class, "possessed_security_door_large");
        registerTile(TileEntityPossessedSecurityDoorSmall.class, "possessed_security_door_small");
        registerTile(TileEntityApexDoorLarge.class, "apex_door_large");
        registerTile(TileEntityApexDoorSmall.class, "apex_door_small");
        registerTile(TileEntityBulkheadDoorLarge.class, "bulkhead_door_large");
        registerTile(TileEntityBulkheadDoorSmall.class, "bulkhead_door_small");
        registerTile(TileEntitySpitter.class, "spitter");
        registerTile(TileEntityCocoon.class, "cocoon");
    }

    private static void registerTile(Class<? extends TileEntity> clazz, String key) {
        GameRegistry.registerTileEntity(clazz, new ResourceLocation(GtfoCraft.MODID, key));
    }
}
