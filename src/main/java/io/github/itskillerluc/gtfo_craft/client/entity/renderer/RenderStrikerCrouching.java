package io.github.itskillerluc.gtfo_craft.client.entity.renderer;

import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelStrikerCrouching;
import io.github.itskillerluc.gtfo_craft.entity.EntityStrikerCrouching;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderStrikerCrouching extends GeoEntityRenderer<EntityStrikerCrouching> {
    public RenderStrikerCrouching(RenderManager renderManager) {
        super(renderManager, new ModelStrikerCrouching());
    }
}
