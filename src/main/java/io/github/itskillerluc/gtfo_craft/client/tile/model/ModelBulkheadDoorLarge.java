package io.github.itskillerluc.gtfo_craft.client.tile.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityBulkheadDoorLarge;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityBulkheadDoorSmall;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelBulkheadDoorLarge extends AnimatedGeoModel<TileEntityBulkheadDoorLarge> {
    @Override
    public ResourceLocation getModelLocation(TileEntityBulkheadDoorLarge object) {
        return new ResourceLocation(GtfoCraft.MODID, "geo/bulkhead_door_large.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(TileEntityBulkheadDoorLarge object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/blocks/bulkhead_door_large.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(TileEntityBulkheadDoorLarge animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/bulkhead_door_large.animation.json");
    }
}
