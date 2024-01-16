package io.github.itskillerluc.gtfo_craft.client.entity.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.entity.EntityShadowStanding;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelShadowStanding extends AnimatedGeoModel<EntityShadowStanding> {
    @Override
    public ResourceLocation getModelLocation(EntityShadowStanding object) {
        return new ResourceLocation(GtfoCraft.MODID, "geo/shadow.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityShadowStanding object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/entity/shadow.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityShadowStanding animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/shadow.animation.json");
    }
}
