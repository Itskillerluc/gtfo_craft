package io.github.itskillerluc.gtfo_craft.client.entity.model;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.entity.EntityFogRepeller;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.provider.GeoModelProvider;

public class ModelFogRepeller extends GeoModelProvider<EntityFogRepeller> {
    @Override
    public ResourceLocation getModelLocation(EntityFogRepeller object) {
        return new ResourceLocation(GtfoCraft.MODID, "geo/fog_repeller.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityFogRepeller object) {
        return new ResourceLocation(GtfoCraft.MODID, "textures/entity/fog_repeller.png");
    }
}
