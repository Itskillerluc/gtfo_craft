package io.github.itskillerluc.gtfo_craft.client.tile.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.block.BlockCommonDoorLargeController;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityCommonDoorLarge;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelCommonDoorLarge extends AnimatedGeoModel<TileEntityCommonDoorLarge> {
    @Override
    public ResourceLocation getModelLocation(TileEntityCommonDoorLarge object) {
        if (object.getWorld().getBlockState(object.getPos()).getValue(BlockCommonDoorLargeController.OPEN)) {
            return new ResourceLocation(GtfoCraft.MODID, "geo/common_door_large_open.geo.json");
        } else {
            return new ResourceLocation(GtfoCraft.MODID, "geo/common_door_large_closed.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureLocation(TileEntityCommonDoorLarge object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/blocks/common_door_large.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(TileEntityCommonDoorLarge animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/common_door_large.animation.json");
    }
}
