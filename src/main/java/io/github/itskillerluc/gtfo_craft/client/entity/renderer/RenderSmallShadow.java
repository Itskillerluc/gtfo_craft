package io.github.itskillerluc.gtfo_craft.client.entity.renderer;

import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelSmallShadow;
import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelStriker;
import io.github.itskillerluc.gtfo_craft.entity.EntitySmallShadow;
import io.github.itskillerluc.gtfo_craft.entity.EntityStriker;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import org.lwjgl.opengl.GL11;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderSmallShadow extends GeoEntityRenderer<EntitySmallShadow> {
    public RenderSmallShadow(RenderManager renderManager) {
        super(renderManager, new ModelSmallShadow());
    }

    @Override
    public void renderLate(EntitySmallShadow animatable, float ticks, float red, float green, float blue, float partialTicks) {
        super.renderLate(animatable, ticks, red, green, blue, partialTicks);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableDepth();
    }

    @Override
    public void renderAfter(EntitySmallShadow animatable, float ticks, float red, float green, float blue, float partialTicks) {
        super.renderAfter(animatable, ticks, red, green, blue, partialTicks);
        GlStateManager.disableBlend();
        GlStateManager.enableDepth();
    }
}
