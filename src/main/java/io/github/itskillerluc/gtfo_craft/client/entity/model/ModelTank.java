package io.github.itskillerluc.gtfo_craft.client.entity.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.entity.EntityStriker;
import io.github.itskillerluc.gtfo_craft.entity.EntityTank;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelTank extends AnimatedGeoModel<EntityTank> {
    @Override
    public ResourceLocation getModelLocation(EntityTank object) {
        return new ResourceLocation(GtfoCraft.MODID, "geo/tank.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityTank object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/entity/tank.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityTank animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/tank.animation.json");
    }
}
