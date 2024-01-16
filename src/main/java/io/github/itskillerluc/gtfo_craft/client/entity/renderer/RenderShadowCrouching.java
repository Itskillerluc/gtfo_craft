package io.github.itskillerluc.gtfo_craft.client.entity.renderer;

import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelShadowCrouching;
import io.github.itskillerluc.gtfo_craft.entity.EntityShadowCrouching;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderShadowCrouching extends GeoEntityRenderer<EntityShadowCrouching> {
    public RenderShadowCrouching(RenderManager renderManager) {
        super(renderManager, new ModelShadowCrouching());
    }

    @Override
    public void doRender(EntityShadowCrouching entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.enableBlend();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
}
