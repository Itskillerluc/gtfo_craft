package io.github.itskillerluc.gtfo_craft.client.entity.renderer;

import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelScout;
import io.github.itskillerluc.gtfo_craft.entity.EntityScout;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderScout extends GeoEntityRenderer<EntityScout> {
    public RenderScout(RenderManager renderManager) {
        super(renderManager, new ModelScout());
    }
}
