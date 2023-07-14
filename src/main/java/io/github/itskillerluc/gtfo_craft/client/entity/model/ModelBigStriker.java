package io.github.itskillerluc.gtfo_craft.client.entity.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.entity.EntityBigStriker;
import io.github.itskillerluc.gtfo_craft.entity.EntityStriker;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelBigStriker extends AnimatedGeoModel<EntityBigStriker> {
    @Override
    public ResourceLocation getModelLocation(EntityBigStriker object) {
        return new ResourceLocation(GtfoCraft.MODID, "geo/big_striker.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityBigStriker object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/entity/big_striker.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityBigStriker animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/big_striker.animation.json");
    }
}
