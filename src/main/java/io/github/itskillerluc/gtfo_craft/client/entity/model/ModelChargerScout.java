package io.github.itskillerluc.gtfo_craft.client.entity.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.entity.EntityChargerScout;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelChargerScout extends AnimatedGeoModel<EntityChargerScout> {
    @Override
    public ResourceLocation getModelLocation(EntityChargerScout object) {
        return new ResourceLocation(GtfoCraft.MODID, "geo/charger_scout.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityChargerScout object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/entity/charger_scout.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityChargerScout animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/charger_scout.animation.json");
    }
}
