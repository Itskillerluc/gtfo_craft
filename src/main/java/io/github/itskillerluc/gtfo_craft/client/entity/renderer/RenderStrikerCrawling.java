package io.github.itskillerluc.gtfo_craft.client.entity.renderer;

import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelStrikerCrawling;
import io.github.itskillerluc.gtfo_craft.entity.EntityStrikerCrawling;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderStrikerCrawling extends GeoEntityRenderer<EntityStrikerCrawling> {
    public RenderStrikerCrawling(RenderManager renderManager) {
        super(renderManager, new ModelStrikerCrawling());
    }
}
