package io.github.itskillerluc.gtfo_craft.client.entity.renderer;

import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelBigFlyer;
import io.github.itskillerluc.gtfo_craft.entity.EntityBigFlyer;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderBigFlyer extends GeoEntityRenderer<EntityBigFlyer> {
    public RenderBigFlyer(RenderManager renderManager) {
        super(renderManager, new ModelBigFlyer());
    }
}
