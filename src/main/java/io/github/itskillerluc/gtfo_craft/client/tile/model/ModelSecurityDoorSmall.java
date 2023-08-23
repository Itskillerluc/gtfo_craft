package io.github.itskillerluc.gtfo_craft.client.tile.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.block.BlockSecurityDoorSmallController;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntitySecurityDoorSmall;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelSecurityDoorSmall extends AnimatedGeoModel<TileEntitySecurityDoorSmall> {
    @Override
    public ResourceLocation getModelLocation(TileEntitySecurityDoorSmall object) {
        if (object.getWorld().getBlockState(object.getPos()).getValue(BlockSecurityDoorSmallController.OPEN)) {
            return new ResourceLocation(GtfoCraft.MODID, "geo/security_door_small_open.geo.json");
        } else {
            return new ResourceLocation(GtfoCraft.MODID, "geo/security_door_small_closed.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureLocation(TileEntitySecurityDoorSmall object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/blocks/security_door_small.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(TileEntitySecurityDoorSmall animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/security_door_small.animation.json");
    }
}
