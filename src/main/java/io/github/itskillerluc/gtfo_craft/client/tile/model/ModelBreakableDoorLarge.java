package io.github.itskillerluc.gtfo_craft.client.tile.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.block.BlockBreakableDoorLargeController;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityBreakableDoorLarge;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelBreakableDoorLarge extends AnimatedGeoModel<TileEntityBreakableDoorLarge> {
    @Override
    public ResourceLocation getModelLocation(TileEntityBreakableDoorLarge object) {
        if (object.getWorld().getBlockState(object.getPos()).getValue(BlockBreakableDoorLargeController.OPEN)) {
            return new ResourceLocation(GtfoCraft.MODID, "geo/breakable_door_large_open.geo.json");
        } else {
            return new ResourceLocation(GtfoCraft.MODID, "geo/breakable_door_large_closed.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureLocation(TileEntityBreakableDoorLarge object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/blocks/breakable_door_large.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(TileEntityBreakableDoorLarge animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/breakable_door_large.animation.json");
    }
}
