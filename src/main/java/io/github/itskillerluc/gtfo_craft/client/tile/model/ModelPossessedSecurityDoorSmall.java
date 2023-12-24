package io.github.itskillerluc.gtfo_craft.client.tile.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.block.BlockPossessedSecurityDoorSmallController;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityPossessedSecurityDoorSmall;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelPossessedSecurityDoorSmall extends AnimatedGeoModel<TileEntityPossessedSecurityDoorSmall> {
    @Override
    public ResourceLocation getModelLocation(TileEntityPossessedSecurityDoorSmall object) {
        if (object.getWorld().getBlockState(object.getPos()).getValue(BlockPossessedSecurityDoorSmallController.OPEN)) {
            return new ResourceLocation(GtfoCraft.MODID, "geo/possessed_security_door_small_open.geo.json");
        } else {
            return new ResourceLocation(GtfoCraft.MODID, "geo/possessed_security_door_small_closed.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureLocation(TileEntityPossessedSecurityDoorSmall object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/blocks/possessed_security_door_small.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(TileEntityPossessedSecurityDoorSmall animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/possessed_security_door_small.animation.json");
    }
}
