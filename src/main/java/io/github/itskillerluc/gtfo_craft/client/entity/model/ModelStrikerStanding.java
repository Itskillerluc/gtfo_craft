package io.github.itskillerluc.gtfo_craft.client.entity.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.entity.EntityStrikerStanding;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelStrikerStanding extends AnimatedGeoModel<EntityStrikerStanding> {
    @Override
    public ResourceLocation getModelLocation(EntityStrikerStanding object) {
        return new ResourceLocation(GtfoCraft.MODID, "geo/striker.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityStrikerStanding object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/entity/striker.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityStrikerStanding animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/striker.animation.json");
    }
}
