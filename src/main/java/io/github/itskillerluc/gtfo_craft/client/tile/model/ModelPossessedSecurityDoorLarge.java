package io.github.itskillerluc.gtfo_craft.client.tile.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.block.BlockPossessedSecurityDoorLargeController;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityPossessedSecurityDoorLarge;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelPossessedSecurityDoorLarge extends AnimatedGeoModel<TileEntityPossessedSecurityDoorLarge> {
    @Override
    public ResourceLocation getModelLocation(TileEntityPossessedSecurityDoorLarge object) {
        if (object.getWorld().getBlockState(object.getPos()).getValue(BlockPossessedSecurityDoorLargeController.OPEN)) {
            return new ResourceLocation(GtfoCraft.MODID, "geo/possessed_security_door_large_open.geo.json");
        } else {
            return new ResourceLocation(GtfoCraft.MODID, "geo/possessed_security_door_large_closed.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureLocation(TileEntityPossessedSecurityDoorLarge object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/blocks/possessed_security_door_large.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(TileEntityPossessedSecurityDoorLarge animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/possessed_security_door_large.animation.json");
    }
}
