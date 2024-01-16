package io.github.itskillerluc.gtfo_craft.client.entity.renderer;

import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelChargerScout;
import io.github.itskillerluc.gtfo_craft.entity.EntityChargerScout;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderChargerScout extends GeoEntityRenderer<EntityChargerScout> {
    public RenderChargerScout(RenderManager renderManager) {
        super(renderManager, new ModelChargerScout());
    }
}
