package io.github.itskillerluc.gtfo_craft.client.entity.renderer;

import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelBigShadowCharger;
import io.github.itskillerluc.gtfo_craft.entity.EntityBigShadowCharger;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderBigShadowCharger extends GeoEntityRenderer<EntityBigShadowCharger> {
    public RenderBigShadowCharger(RenderManager renderManager) {
        super(renderManager, new ModelBigShadowCharger());
    }

    @Override
    public void doRender(EntityBigShadowCharger entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.enableBlend();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
}
