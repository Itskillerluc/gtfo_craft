package io.github.itskillerluc.gtfo_craft.client.tile.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityTurret;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelTurret extends AnimatedGeoModel<TileEntityTurret> {
    @Override
    public ResourceLocation getModelLocation(TileEntityTurret object) {
        return new ResourceLocation(GtfoCraft.MODID, "geo/turret.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(TileEntityTurret object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/blocks/turret.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(TileEntityTurret animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/turret.animation.json");
    }
}
