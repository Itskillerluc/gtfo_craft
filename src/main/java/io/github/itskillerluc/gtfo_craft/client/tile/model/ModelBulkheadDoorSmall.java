package io.github.itskillerluc.gtfo_craft.client.tile.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.block.BlockBulkheadDoorSmallController;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityBulkheadDoorSmall;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelBulkheadDoorSmall extends AnimatedGeoModel<TileEntityBulkheadDoorSmall> {
    @Override
    public ResourceLocation getModelLocation(TileEntityBulkheadDoorSmall object) {
        if (object.getWorld().getBlockState(object.getPos()).getValue(BlockBulkheadDoorSmallController.OPEN)) {
            return new ResourceLocation(GtfoCraft.MODID, "geo/bulkhead_door_small_open.geo.json");
        } else {
            return new ResourceLocation(GtfoCraft.MODID, "geo/bulkhead_door_small_closed.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureLocation(TileEntityBulkheadDoorSmall object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/blocks/bulkhead_door_small.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(TileEntityBulkheadDoorSmall animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/bulkhead_door_small.animation.json");
    }
}
