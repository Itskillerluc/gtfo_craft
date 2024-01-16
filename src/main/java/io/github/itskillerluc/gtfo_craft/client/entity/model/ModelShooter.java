package io.github.itskillerluc.gtfo_craft.client.entity.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.entity.EntityShooter;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelShooter extends AnimatedGeoModel<EntityShooter> {
    @Override
    public ResourceLocation getModelLocation(EntityShooter object) {
        return new ResourceLocation(GtfoCraft.MODID, "geo/shooter.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityShooter object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/entity/shooter.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityShooter animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/shooter.animation.json");
    }
}
