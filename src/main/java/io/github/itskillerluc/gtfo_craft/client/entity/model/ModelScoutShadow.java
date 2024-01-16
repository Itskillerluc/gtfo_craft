package io.github.itskillerluc.gtfo_craft.client.entity.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.entity.EntityScoutShadow;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelScoutShadow extends AnimatedGeoModel<EntityScoutShadow> {
    @Override
    public ResourceLocation getModelLocation(EntityScoutShadow object) {
        return new ResourceLocation(GtfoCraft.MODID, "geo/shadow.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityScoutShadow object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/entity/shadow.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityScoutShadow animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/shooter.animation.json");
    }
}
