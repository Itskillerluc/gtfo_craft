package io.github.itskillerluc.gtfo_craft.client.entity.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.entity.EntityStrikerCrouching;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelStrikerCrouching extends AnimatedGeoModel<EntityStrikerCrouching> {
    @Override
    public ResourceLocation getModelLocation(EntityStrikerCrouching object) {
        return new ResourceLocation(GtfoCraft.MODID, "geo/striker.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityStrikerCrouching object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/entity/striker.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityStrikerCrouching animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/striker.animation.json");
    }
}
