package io.github.itskillerluc.gtfo_craft.client.entity.renderer;

import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelBigShadow;
import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelStriker;
import io.github.itskillerluc.gtfo_craft.entity.EntityBigShadow;
import io.github.itskillerluc.gtfo_craft.entity.EntitySmallShadow;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderBigShadow extends GeoEntityRenderer<EntityBigShadow> {
    public RenderBigShadow(RenderManager renderManager) {
        super(renderManager, new ModelBigShadow());
    }
}
