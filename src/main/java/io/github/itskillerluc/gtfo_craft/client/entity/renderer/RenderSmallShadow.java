package io.github.itskillerluc.gtfo_craft.client.entity.renderer;

import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelSmallShadow;
import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelStriker;
import io.github.itskillerluc.gtfo_craft.entity.EntitySmallShadow;
import io.github.itskillerluc.gtfo_craft.entity.EntityStriker;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderSmallShadow extends GeoEntityRenderer<EntitySmallShadow> {
    public RenderSmallShadow(RenderManager renderManager) {
        super(renderManager, new ModelSmallShadow());
    }
}
