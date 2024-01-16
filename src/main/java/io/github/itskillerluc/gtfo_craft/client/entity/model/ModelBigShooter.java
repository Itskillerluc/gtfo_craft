package io.github.itskillerluc.gtfo_craft.client.entity.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.entity.EntityBigShooter;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelBigShooter extends AnimatedGeoModel<EntityBigShooter> {
    @Override
    public ResourceLocation getModelLocation(EntityBigShooter object) {
        return new ResourceLocation(GtfoCraft.MODID, "geo/big_shooter.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityBigShooter object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/entity/big_shooter.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityBigShooter animatable) {
        return new ResourceLocation(GtfoCraft.MODID, "animations/big_shooter.animation.json");
    }
}
