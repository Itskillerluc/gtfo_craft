package io.github.itskillerluc.gtfo_craft.client.entity.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.entity.EntityBigShadowHybrid;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelBigShadowHybrid extends AnimatedGeoModel<EntityBigShadowHybrid> {
    @Override
    public ResourceLocation getModelLocation(EntityBigShadowHybrid object) {
        return new ResourceLocation(GtfoCraft.MODID, "geo/big_shadow.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityBigShadowHybrid object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/entity/big_shadow.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityBigShadowHybrid animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/big_shadow.animation.json");
    }
}
