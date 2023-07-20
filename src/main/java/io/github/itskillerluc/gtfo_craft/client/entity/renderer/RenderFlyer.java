package io.github.itskillerluc.gtfo_craft.client.entity.renderer;

import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelBaby;
import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelFlyer;
import io.github.itskillerluc.gtfo_craft.entity.EntityBaby;
import io.github.itskillerluc.gtfo_craft.entity.EntityFlyer;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderFlyer extends GeoEntityRenderer<EntityFlyer> {
    public RenderFlyer(RenderManager renderManager) {
        super(renderManager, new ModelFlyer());
    }
}
