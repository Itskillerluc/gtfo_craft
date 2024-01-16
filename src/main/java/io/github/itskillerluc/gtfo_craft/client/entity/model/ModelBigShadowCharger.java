package io.github.itskillerluc.gtfo_craft.client.entity.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.entity.EntityBigShadowCharger;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelBigShadowCharger extends AnimatedGeoModel<EntityBigShadowCharger> {
    @Override
    public ResourceLocation getModelLocation(EntityBigShadowCharger object) {
        return new ResourceLocation(GtfoCraft.MODID, "geo/big_shadow.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityBigShadowCharger object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/entity/big_shadow.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityBigShadowCharger animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/big_shadow.animation.json");
    }
}
