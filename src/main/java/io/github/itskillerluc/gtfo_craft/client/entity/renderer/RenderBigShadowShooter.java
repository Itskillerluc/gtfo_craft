package io.github.itskillerluc.gtfo_craft.client.entity.renderer;

import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelBigShadowShooter;
import io.github.itskillerluc.gtfo_craft.entity.EntityBigShadowCharger;
import io.github.itskillerluc.gtfo_craft.entity.EntityBigShadowShooter;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderBigShadowShooter extends GeoEntityRenderer<EntityBigShadowShooter> {
    public RenderBigShadowShooter(RenderManager renderManager) {
        super(renderManager, new ModelBigShadowShooter());
    }
    @Override
    public void doRender(EntityBigShadowShooter entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.enableBlend();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
}
