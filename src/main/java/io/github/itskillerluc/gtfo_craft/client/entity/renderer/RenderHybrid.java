package io.github.itskillerluc.gtfo_craft.client.entity.renderer;

import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelHybrid;
import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelStriker;
import io.github.itskillerluc.gtfo_craft.entity.EntityHybrid;
import io.github.itskillerluc.gtfo_craft.entity.EntityStriker;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderHybrid extends GeoEntityRenderer<EntityHybrid> {
    public RenderHybrid(RenderManager renderManager) {
        super(renderManager, new ModelHybrid());
    }
}
