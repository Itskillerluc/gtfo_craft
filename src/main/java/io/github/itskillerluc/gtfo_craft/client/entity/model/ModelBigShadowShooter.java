package io.github.itskillerluc.gtfo_craft.client.entity.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.entity.EntityBigShadowCharger;
import io.github.itskillerluc.gtfo_craft.entity.EntityBigShadowShooter;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelBigShadowShooter extends AnimatedGeoModel<EntityBigShadowShooter> {
    @Override
    public ResourceLocation getModelLocation(EntityBigShadowShooter object) {
        return new ResourceLocation(GtfoCraft.MODID, "geo/big_shadow.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityBigShadowShooter object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/entity/big_shadow.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityBigShadowShooter animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/big_shadow.animation.json");
    }
}
