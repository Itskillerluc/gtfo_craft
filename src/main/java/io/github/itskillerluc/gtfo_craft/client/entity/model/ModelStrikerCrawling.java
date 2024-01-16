package io.github.itskillerluc.gtfo_craft.client.entity.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.entity.EntityStrikerCrawling;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelStrikerCrawling extends AnimatedGeoModel<EntityStrikerCrawling> {
    @Override
    public ResourceLocation getModelLocation(EntityStrikerCrawling object) {
        return new ResourceLocation(GtfoCraft.MODID, "geo/striker.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityStrikerCrawling object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/entity/striker.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityStrikerCrawling animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/striker.animation.json");
    }
}
