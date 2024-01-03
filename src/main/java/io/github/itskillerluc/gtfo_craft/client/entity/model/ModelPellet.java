package io.github.itskillerluc.gtfo_craft.client.entity.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.entity.EntityPellet;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelPellet extends AnimatedGeoModel<EntityPellet> {
    private static final ResourceLocation MODEL = new ResourceLocation(GtfoCraft.MODID, "geo/glowing_orb.geo.json");
    private static final ResourceLocation TEXTURE = new ResourceLocation(GtfoCraft.MODID, "textures/entity/glowing_orb.png");
    private static final ResourceLocation ANIMATION = new ResourceLocation(GtfoCraft.MODID, "animations/glowing_orb.animation.json");

    @Override
    public ResourceLocation getModelLocation(EntityPellet object) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureLocation(EntityPellet object) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityPellet animatable) {
        return ANIMATION;
    }
}
