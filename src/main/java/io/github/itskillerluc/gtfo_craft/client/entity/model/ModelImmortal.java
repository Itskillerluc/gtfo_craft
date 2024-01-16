package io.github.itskillerluc.gtfo_craft.client.entity.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.entity.EntityImmortal;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelImmortal extends AnimatedGeoModel<EntityImmortal> {
    @Override
    public ResourceLocation getModelLocation(EntityImmortal object) {
        return new ResourceLocation(GtfoCraft.MODID, "geo/immortal.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityImmortal object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/entity/immortal.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityImmortal animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/immortal.animation.json");
    }
}
