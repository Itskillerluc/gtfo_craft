package io.github.itskillerluc.gtfo_craft.client.entity.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.entity.EntityBigMother;
import io.github.itskillerluc.gtfo_craft.entity.EntityStriker;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelBigMother extends AnimatedGeoModel<EntityBigMother> {
    @Override
    public ResourceLocation getModelLocation(EntityBigMother object) {
        return new ResourceLocation(GtfoCraft.MODID, "geo/big_mother.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityBigMother object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/entity/big_mother.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityBigMother animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/big_mother.animation.json");
    }
}
