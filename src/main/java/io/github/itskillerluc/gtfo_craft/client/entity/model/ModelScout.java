package io.github.itskillerluc.gtfo_craft.client.entity.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.entity.EntityBaby;
import io.github.itskillerluc.gtfo_craft.entity.EntityScout;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelScout extends AnimatedGeoModel<EntityScout> {
    @Override
    public ResourceLocation getModelLocation(EntityScout object) {
        return new ResourceLocation(GtfoCraft.MODID, "geo/scout.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityScout object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/entity/scout.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityScout animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/scout.animation.json");
    }
}
