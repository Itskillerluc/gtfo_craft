package io.github.itskillerluc.gtfo_craft.client.entity.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.entity.EntityShadowCrouching;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelShadowCrouching extends AnimatedGeoModel<EntityShadowCrouching> {
    @Override
    public ResourceLocation getModelLocation(EntityShadowCrouching object) {
        return new ResourceLocation(GtfoCraft.MODID, "geo/shadow.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityShadowCrouching object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/entity/shadow.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityShadowCrouching animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/shadow.animation.json");
    }
}
