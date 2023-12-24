package io.github.itskillerluc.gtfo_craft.client.tile.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.block.BlockApexDoorSmallController;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityApexDoorSmall;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelApexDoorSmall extends AnimatedGeoModel<TileEntityApexDoorSmall> {
    @Override
    public ResourceLocation getModelLocation(TileEntityApexDoorSmall object) {
        if (object.getWorld().getBlockState(object.getPos()).getValue(BlockApexDoorSmallController.OPEN)) {
            return new ResourceLocation(GtfoCraft.MODID, "geo/apex_door_small_open.geo.json");
        } else {
            return new ResourceLocation(GtfoCraft.MODID, "geo/apex_door_small_closed.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureLocation(TileEntityApexDoorSmall object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/blocks/apex_door_small.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(TileEntityApexDoorSmall animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/apex_door_small.animation.json");
    }
}
