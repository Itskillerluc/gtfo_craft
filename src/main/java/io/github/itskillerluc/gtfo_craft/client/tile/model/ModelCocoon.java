package io.github.itskillerluc.gtfo_craft.client.tile.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityCocoon;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityTurret;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelCocoon extends AnimatedGeoModel<TileEntityCocoon> {
    @Override
    public ResourceLocation getModelLocation(TileEntityCocoon object) {
        return new ResourceLocation(GtfoCraft.MODID, "geo/cocoon.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(TileEntityCocoon object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/entity/cocoon.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(TileEntityCocoon animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/cocoon.animation.json");
    }
}
