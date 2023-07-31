package io.github.itskillerluc.gtfo_craft.client.entity.renderer;

import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelSpitter;
import io.github.itskillerluc.gtfo_craft.entity.EntitySpitter;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderSpitter extends GeoEntityRenderer<EntitySpitter> {
    public RenderSpitter(RenderManager renderManager) {
        super(renderManager, new ModelSpitter());
    }

    @Override
    public void render(GeoModel model, EntitySpitter animatable, float partialTicks, float red, float green, float blue, float alpha) {
        GlStateManager.pushMatrix();
        switch (animatable.getFacing()) {
            case DOWN:
                GlStateManager.translate(0, 0.5, 0);
                GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
                break;
            case UP:
                break;
            case NORTH:
                GlStateManager.translate(0, 0.25, -0.25);
                GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                break;
            case SOUTH:
                GlStateManager.translate(0, 0.25, 0.25);
                GlStateManager.rotate(90.0F, -1.0F, 0.0F, 0.0F);
                break;
            case WEST:
                GlStateManager.translate(-0.25, 0.25, 0);
                GlStateManager.rotate(90.0F, 0.0F, 0, -1.0F);
                break;
            case EAST:
                GlStateManager.translate(0.25f, 0.25, 0);
                GlStateManager.rotate(90.0F, 0.0F, 0, 1.0F);
                break;
        }
        super.render(model, animatable, partialTicks, red, green, blue, alpha);
        GlStateManager.popMatrix();
    }
}
