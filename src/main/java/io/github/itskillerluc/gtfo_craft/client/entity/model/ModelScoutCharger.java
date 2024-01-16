package io.github.itskillerluc.gtfo_craft.client.entity.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.entity.EntityCharger;
import io.github.itskillerluc.gtfo_craft.entity.EntityScoutCharger;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelScoutCharger extends AnimatedGeoModel<EntityScoutCharger> {
    @Override
    public ResourceLocation getModelLocation(EntityScoutCharger object) {
        return new ResourceLocation(GtfoCraft.MODID, "geo/charger.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityScoutCharger object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/entity/charger.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityScoutCharger animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/charger.animation.json");
    }
}
