package io.github.itskillerluc.gtfo_craft.client.entity.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.entity.EntityBaby;
import io.github.itskillerluc.gtfo_craft.entity.EntitySpitter;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.GeoModelProvider;

public class ModelSpitter extends AnimatedGeoModel<EntitySpitter> {
    @Override
    public ResourceLocation getModelLocation(EntitySpitter object) {
        return new ResourceLocation(GtfoCraft.MODID, "geo/spitter.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntitySpitter object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/entity/spitter.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntitySpitter animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/spitter.animation.json");
    }
}
