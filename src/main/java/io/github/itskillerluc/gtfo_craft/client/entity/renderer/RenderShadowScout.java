package io.github.itskillerluc.gtfo_craft.client.entity.renderer;

import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelShadowScout;
import io.github.itskillerluc.gtfo_craft.entity.EntityShadowScout;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderShadowScout extends GeoEntityRenderer<EntityShadowScout> {
    public RenderShadowScout(RenderManager renderManager) {
        super(renderManager, new ModelShadowScout());
    }

    @Override
    public void doRender(EntityShadowScout entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.enableBlend();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
}
