package io.github.itskillerluc.gtfo_craft.client.entity.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.entity.EntityBaby;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelBaby extends AnimatedGeoModel<EntityBaby> {
    @Override
    public ResourceLocation getModelLocation(EntityBaby object) {
        return new ResourceLocation(GtfoCraft.MODID, "geo/baby.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityBaby object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/entity/baby.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityBaby animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/baby.animation.json");
    }
}
