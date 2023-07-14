package io.github.itskillerluc.gtfo_craft.client.entity.renderer;

import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelBigStriker;
import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelStriker;
import io.github.itskillerluc.gtfo_craft.entity.EntityBigStriker;
import io.github.itskillerluc.gtfo_craft.entity.EntityStriker;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderBigStriker extends GeoEntityRenderer<EntityBigStriker> {
    public RenderBigStriker(RenderManager renderManager) {
        super(renderManager, new ModelBigStriker());
    }
}
