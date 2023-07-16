package io.github.itskillerluc.gtfo_craft.client.entity.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.entity.EntityHybrid;
import io.github.itskillerluc.gtfo_craft.entity.EntityStriker;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelHybrid extends AnimatedGeoModel<EntityHybrid> {
    @Override
    public ResourceLocation getModelLocation(EntityHybrid object) {
        return new ResourceLocation(GtfoCraft.MODID, "geo/hybrid.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityHybrid object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/entity/hybrid.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityHybrid animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/hybrid.animation.json");
    }
}
