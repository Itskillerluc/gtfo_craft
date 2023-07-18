package io.github.itskillerluc.gtfo_craft.client.entity.renderer;

import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelStriker;
import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelTank;
import io.github.itskillerluc.gtfo_craft.entity.EntityStriker;
import io.github.itskillerluc.gtfo_craft.entity.EntityTank;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderTank extends GeoEntityRenderer<EntityTank> {
    public RenderTank(RenderManager renderManager) {
        super(renderManager, new ModelTank());
    }
}
