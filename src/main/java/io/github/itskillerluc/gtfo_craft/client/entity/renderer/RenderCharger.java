package io.github.itskillerluc.gtfo_craft.client.entity.renderer;

import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelCharger;
import io.github.itskillerluc.gtfo_craft.entity.EntityCharger;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderCharger extends GeoEntityRenderer<EntityCharger> {
    public RenderCharger(RenderManager renderManager) {
        super(renderManager, new ModelCharger());
    }
}
