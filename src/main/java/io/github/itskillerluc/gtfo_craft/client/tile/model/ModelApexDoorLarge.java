package io.github.itskillerluc.gtfo_craft.client.tile.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.block.BlockApexDoorLargeController;
import io.github.itskillerluc.gtfo_craft.block.BlockSecurityDoorLargeController;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityApexDoorLarge;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntitySecurityDoorLarge;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelApexDoorLarge extends AnimatedGeoModel<TileEntityApexDoorLarge> {
    @Override
    public ResourceLocation getModelLocation(TileEntityApexDoorLarge object) {
        if (object.getWorld().getBlockState(object.getPos()).getValue(BlockApexDoorLargeController.OPEN)) {
            return new ResourceLocation(GtfoCraft.MODID, "geo/apex_door_large_open.geo.json");
        } else {
            return new ResourceLocation(GtfoCraft.MODID, "geo/apex_door_large_closed.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureLocation(TileEntityApexDoorLarge object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/blocks/apex_door_large.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(TileEntityApexDoorLarge animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/apex_door_large.animation.json");
    }
}
