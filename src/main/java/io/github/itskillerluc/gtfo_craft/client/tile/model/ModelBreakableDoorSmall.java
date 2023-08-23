package io.github.itskillerluc.gtfo_craft.client.tile.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.block.BlockBreakableDoorSmallController;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityBreakableDoorSmall;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelBreakableDoorSmall extends AnimatedGeoModel<TileEntityBreakableDoorSmall> {
    @Override
    public ResourceLocation getModelLocation(TileEntityBreakableDoorSmall object) {
        if (object.getWorld().getBlockState(object.getPos()).getValue(BlockBreakableDoorSmallController.OPEN)) {
            return new ResourceLocation(GtfoCraft.MODID, "geo/breakable_door_small_open.geo.json");
        } else {
            return new ResourceLocation(GtfoCraft.MODID, "geo/breakable_door_small_closed.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureLocation(TileEntityBreakableDoorSmall object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/blocks/breakable_door_small.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(TileEntityBreakableDoorSmall animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/breakable_door_small.animation.json");
    }
}
