package io.github.itskillerluc.gtfo_craft.client.entity.renderer;

import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelScoutCharger;
import io.github.itskillerluc.gtfo_craft.entity.EntityScoutCharger;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderScoutCharger extends GeoEntityRenderer<EntityScoutCharger> {
    public RenderScoutCharger(RenderManager renderManager) {
        super(renderManager, new ModelScoutCharger());
    }
}
