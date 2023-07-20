package io.github.itskillerluc.gtfo_craft.client.entity.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.entity.EntityFlyer;
import io.github.itskillerluc.gtfo_craft.entity.EntityTank;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelFlyer extends AnimatedGeoModel<EntityFlyer> {
    @Override
    public ResourceLocation getModelLocation(EntityFlyer object) {
        return new ResourceLocation(GtfoCraft.MODID, "geo/flyer.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityFlyer object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/entity/flyer.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityFlyer animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/flyer.animation.json");
    }
}
