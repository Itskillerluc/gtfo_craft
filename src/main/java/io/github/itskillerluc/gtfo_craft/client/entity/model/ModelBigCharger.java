package io.github.itskillerluc.gtfo_craft.client.entity.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.entity.EntityBigCharger;
import io.github.itskillerluc.gtfo_craft.entity.EntitySmallCharger;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelBigCharger extends AnimatedGeoModel<EntityBigCharger> {
    @Override
    public ResourceLocation getModelLocation(EntityBigCharger object) {
        return new ResourceLocation(GtfoCraft.MODID, "geo/big_charger.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityBigCharger object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/entity/big_charger.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityBigCharger animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/big_charger.animation.json");
    }
}
