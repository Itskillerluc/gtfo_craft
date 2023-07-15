package io.github.itskillerluc.gtfo_craft.client.entity.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.entity.EntitySmallCharger;
import io.github.itskillerluc.gtfo_craft.entity.EntityStriker;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelSmallCharger extends AnimatedGeoModel<EntitySmallCharger> {
    @Override
    public ResourceLocation getModelLocation(EntitySmallCharger object) {
        return new ResourceLocation(GtfoCraft.MODID, "geo/small_charger.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntitySmallCharger object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/entity/small_charger.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntitySmallCharger animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/small_charger.animation.json");
    }
}
