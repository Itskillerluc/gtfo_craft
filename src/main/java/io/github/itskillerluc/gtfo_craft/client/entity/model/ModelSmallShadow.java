package io.github.itskillerluc.gtfo_craft.client.entity.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.entity.EntitySmallShadow;
import io.github.itskillerluc.gtfo_craft.entity.EntityStriker;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelSmallShadow extends AnimatedGeoModel<EntitySmallShadow> {
    @Override
    public ResourceLocation getModelLocation(EntitySmallShadow object) {
        return new ResourceLocation(GtfoCraft.MODID, "geo/small_shadow.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntitySmallShadow object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/entity/small_shadow.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntitySmallShadow animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/small_shadow.animation.json");
    }
}
