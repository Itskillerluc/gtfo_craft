package io.github.itskillerluc.gtfo_craft.client.entity.renderer;

import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelBigShadowHybrid;
import io.github.itskillerluc.gtfo_craft.entity.EntityBigShadowCharger;
import io.github.itskillerluc.gtfo_craft.entity.EntityBigShadowHybrid;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderBigShadowHybrid extends GeoEntityRenderer<EntityBigShadowHybrid> {
    public RenderBigShadowHybrid(RenderManager renderManager) {
        super(renderManager, new ModelBigShadowHybrid());
    }
    @Override
    public void doRender(EntityBigShadowHybrid entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.enableBlend();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
}
