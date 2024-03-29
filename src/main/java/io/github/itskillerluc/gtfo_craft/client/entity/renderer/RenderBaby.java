package io.github.itskillerluc.gtfo_craft.client.entity.renderer;

import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelBaby;
import io.github.itskillerluc.gtfo_craft.entity.EntityBaby;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderBaby extends GeoEntityRenderer<EntityBaby> {
    public RenderBaby(RenderManager renderManager) {
        super(renderManager, new ModelBaby());
    }
}
