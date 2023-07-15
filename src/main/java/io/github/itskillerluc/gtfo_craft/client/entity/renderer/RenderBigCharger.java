package io.github.itskillerluc.gtfo_craft.client.entity.renderer;

import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelBigCharger;
import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelSmallCharger;
import io.github.itskillerluc.gtfo_craft.entity.EntityBigCharger;
import io.github.itskillerluc.gtfo_craft.entity.EntitySmallCharger;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderBigCharger extends GeoEntityRenderer<EntityBigCharger> {
    public RenderBigCharger(RenderManager renderManager) {
        super(renderManager, new ModelBigCharger());
    }
}
