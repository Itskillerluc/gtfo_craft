package io.github.itskillerluc.gtfo_craft.client.entity.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.entity.EntityShadowScout;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelShadowScout extends AnimatedGeoModel<EntityShadowScout> {
    @Override
    public ResourceLocation getModelLocation(EntityShadowScout object) {
        return new ResourceLocation(GtfoCraft.MODID, "geo/shadow_scout.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityShadowScout object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/entity/shadow_scout.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityShadowScout animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/shadow_scout.animation.json");
    }
}
