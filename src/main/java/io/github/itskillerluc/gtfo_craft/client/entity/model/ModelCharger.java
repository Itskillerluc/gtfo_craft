package io.github.itskillerluc.gtfo_craft.client.entity.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.entity.EntityCharger;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelCharger extends AnimatedGeoModel<EntityCharger> {
    @Override
    public ResourceLocation getModelLocation(EntityCharger object) {
        return new ResourceLocation(GtfoCraft.MODID, "geo/charger.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityCharger object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/entity/charger.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityCharger animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/charger.animation.json");
    }
}
