package io.github.itskillerluc.gtfo_craft.client.tile.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.block.BlockBulkheadDoorLargeController;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityBulkheadDoorLarge;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelBulkheadDoorLarge extends AnimatedGeoModel<TileEntityBulkheadDoorLarge> {
    @Override
    public ResourceLocation getModelLocation(TileEntityBulkheadDoorLarge object) {
        if (object.getWorld().getBlockState(object.getPos()).getValue(BlockBulkheadDoorLargeController.OPEN)) {
            return new ResourceLocation(GtfoCraft.MODID, "geo/bulkhead_door_large_open.geo.json");
        } else {
            return new ResourceLocation(GtfoCraft.MODID, "geo/bulkhead_door_large_closed.geo.json");
        }
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
