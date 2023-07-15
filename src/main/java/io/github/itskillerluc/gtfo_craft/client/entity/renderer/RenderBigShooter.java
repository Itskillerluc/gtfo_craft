package io.github.itskillerluc.gtfo_craft.client.entity.renderer;

import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelBigShooter;
import io.github.itskillerluc.gtfo_craft.entity.EntityBigShooter;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderBigShooter extends GeoEntityRenderer<EntityBigShooter> {
    public RenderBigShooter(RenderManager renderManager) {
        super(renderManager, new ModelBigShooter());
    }
}
