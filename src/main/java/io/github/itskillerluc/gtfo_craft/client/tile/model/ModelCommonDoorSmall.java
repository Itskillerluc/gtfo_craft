package io.github.itskillerluc.gtfo_craft.client.tile.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.block.BlockCommonDoorSmallController;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityCommonDoorSmall;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelCommonDoorSmall extends AnimatedGeoModel<TileEntityCommonDoorSmall> {
    @Override
    public ResourceLocation getModelLocation(TileEntityCommonDoorSmall object) {
        if (object.getWorld().getBlockState(object.getPos()).getValue(BlockCommonDoorSmallController.OPEN)) {
            return new ResourceLocation(GtfoCraft.MODID, "geo/common_door_small_open.geo.json");
        } else {
            return new ResourceLocation(GtfoCraft.MODID, "geo/common_door_small_closed.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureLocation(TileEntityCommonDoorSmall object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/blocks/common_door_small.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(TileEntityCommonDoorSmall animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/common_door_small.animation.json");
    }
}
