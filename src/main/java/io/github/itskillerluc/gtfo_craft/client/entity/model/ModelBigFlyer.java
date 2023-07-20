package io.github.itskillerluc.gtfo_craft.client.entity.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.entity.EntityBigFlyer;
import io.github.itskillerluc.gtfo_craft.entity.EntityFlyer;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelBigFlyer extends AnimatedGeoModel<EntityBigFlyer> {
    @Override
    public ResourceLocation getModelLocation(EntityBigFlyer object) {
        return new ResourceLocation(GtfoCraft.MODID, "geo/big_flyer.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityBigFlyer object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/entity/big_flyer.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityBigFlyer animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/big_flyer.animation.json");
    }
}
