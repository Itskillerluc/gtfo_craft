package io.github.itskillerluc.gtfo_craft.client.entity.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.entity.EntityMother;
import io.github.itskillerluc.gtfo_craft.entity.EntityStriker;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelMother extends AnimatedGeoModel<EntityMother> {
    @Override
    public ResourceLocation getModelLocation(EntityMother object) {
        return new ResourceLocation(GtfoCraft.MODID, "geo/mother.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityMother object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/entity/mother.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityMother animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/mother.animation.json");
    }
}
