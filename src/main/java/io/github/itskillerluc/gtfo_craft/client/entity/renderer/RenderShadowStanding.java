package io.github.itskillerluc.gtfo_craft.client.entity.renderer;

import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelShadowStanding;
import io.github.itskillerluc.gtfo_craft.entity.EntityShadowStanding;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderShadowStanding extends GeoEntityRenderer<EntityShadowStanding> {
    public RenderShadowStanding(RenderManager renderManager) {
        super(renderManager, new ModelShadowStanding());
    }

    @Override
    public void doRender(EntityShadowStanding entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.enableBlend();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
}
