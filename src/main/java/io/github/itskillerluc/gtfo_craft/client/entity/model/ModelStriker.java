package io.github.itskillerluc.gtfo_craft.client.entity.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.entity.EntityStriker;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelStriker extends AnimatedGeoModel<EntityStriker> {
    @Override
    public ResourceLocation getModelLocation(EntityStriker object) {
        return new ResourceLocation(GtfoCraft.MODID, "geo/striker.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityStriker object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/entity/striker.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityStriker animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/striker.animation.json");
    }
}
