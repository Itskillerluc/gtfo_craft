package io.github.itskillerluc.gtfo_craft.client.entity.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.entity.EntityBigShadow;
import io.github.itskillerluc.gtfo_craft.entity.EntityStriker;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelBigShadow extends AnimatedGeoModel<EntityBigShadow> {
    @Override
    public ResourceLocation getModelLocation(EntityBigShadow object) {
        return new ResourceLocation(GtfoCraft.MODID, "geo/big_shadow.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityBigShadow object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/entity/big_shadow.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityBigShadow animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/big_shadow.animation.json");
    }
}
