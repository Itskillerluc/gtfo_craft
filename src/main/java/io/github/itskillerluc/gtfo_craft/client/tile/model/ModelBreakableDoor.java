package io.github.itskillerluc.gtfo_craft.client.tile.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.block.BlockBreakableDoorController;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityBreakableDoor;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityBulkheadDoorLarge;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelBreakableDoor extends AnimatedGeoModel<TileEntityBreakableDoor> {
    @Override
    public ResourceLocation getModelLocation(TileEntityBreakableDoor object) {
        if (object.getWorld().getBlockState(object.getPos()).getValue(BlockBreakableDoorController.OPEN)) {
            return new ResourceLocation(GtfoCraft.MODID, "geo/breakable_door_open.geo.json");
        } else {
            return new ResourceLocation(GtfoCraft.MODID, "geo/bulkhead_door_small.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureLocation(TileEntityBreakableDoor object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/blocks/bulkhead_door_small.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(TileEntityBreakableDoor animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/bulkhead_door_small.animation.json");
    }
}
