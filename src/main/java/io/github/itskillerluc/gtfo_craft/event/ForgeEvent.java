package io.github.itskillerluc.gtfo_craft.event;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.registry.BlockRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = GtfoCraft.MODID, value = Side.CLIENT)
public class ForgeEvent {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void renderBlockOverlay(RenderGameOverlayEvent.Post event) {
        if (Minecraft.getMinecraft().player.isInsideOfMaterial(BlockRegistry.FOG_MATERIAL)) {
            renderFogOverlay(event.getResolution());
        }
    }



    @SideOnly(Side.CLIENT)
    private static void renderFogOverlay(ScaledResolution resolution)
    {

        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(GtfoCraft.MODID, "textures/blocks/fog.png"));
        int y = resolution.getScaledHeight();

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        drawModalRectWithCustomSizedTexture(resolution.getScaledWidth(), y, 16, 16, 255, 255, 255, 50);

        GlStateManager.disableDepth();
    }

    private static void drawModalRectWithCustomSizedTexture(int width, int height, float textureWidth, float textureHeight, int r, int g, int b, int alpha)
    {
        float f = 1.0F / textureWidth;
        float f1 = 1.0F / textureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferbuilder.pos(0, height, 0.0D).tex((float) 0 * f, ((float) 0 + (float)height) * f1).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(width, height, 0.0D).tex(((float) 0 + (float)width) * f, ((float) 0 + (float)height) * f1).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(width, 0, 0.0D).tex(((float) 0 + (float)width) * f, (float) 0 * f1).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(0, 0, 0.0D).tex((float) 0 * f, (float) 0 * f1).color(r, g, b, alpha).endVertex();
        tessellator.draw();
    }
}
