package io.github.itskillerluc.gtfo_craft.client.tile.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.block.BlockSecurityDoorLargeController;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntitySecurityDoorLarge;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelSecurityDoorLarge extends AnimatedGeoModel<TileEntitySecurityDoorLarge> {
    @Override
    public ResourceLocation getModelLocation(TileEntitySecurityDoorLarge object) {
        if (object.getWorld().getBlockState(object.getPos()).getValue(BlockSecurityDoorLargeController.OPEN)) {
            return new ResourceLocation(GtfoCraft.MODID, "geo/security_door_large_open.geo.json");
        } else {
            return new ResourceLocation(GtfoCraft.MODID, "geo/security_door_large_closed.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureLocation(TileEntitySecurityDoorLarge object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/blocks/security_door_large.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(TileEntitySecurityDoorLarge animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/security_door_large.animation.json");
    }
}
