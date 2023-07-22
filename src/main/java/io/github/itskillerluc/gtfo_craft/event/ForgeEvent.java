package io.github.itskillerluc.gtfo_craft.event;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.registry.BlockRegistry;
import io.github.itskillerluc.gtfo_craft.registry.ItemRegistry;
import io.github.itskillerluc.gtfo_craft.util.Rendering;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
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

    @SubscribeEvent
    public static void renderPlayerEvent(RenderPlayerEvent.Pre event) {

        if (event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND).getItem() == ItemRegistry.AMMO) {
            event.setCanceled(true);
            GlStateManager.pushMatrix();
            GlStateManager.enableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
            event.getRenderer().bindTexture(event.getRenderer().getEntityTexture(((AbstractClientPlayer) event.getEntityPlayer())));

//            event.getRenderer().getMainModel().bipedLeftLeg.rotateAngleX = 0;
//            event.getRenderer().getMainModel().bipedLeftLeg.offsetX = 0.1f;
//            event.getRenderer().getMainModel().bipedRightLeg.rotateAngleX = 0;
//            event.getRenderer().getMainModel().bipedRightLeg.offsetX = -0.1f;
            float f4 = event.getRenderer().prepareScale(((AbstractClientPlayer) event.getEntityPlayer()), event.getPartialRenderTick());
            float f7 = event.getEntityPlayer().prevRotationPitch + (event.getEntityPlayer().rotationPitch - event.getEntityPlayer().prevRotationPitch) * event.getPartialRenderTick();
            float f = interpolateRotation(event.getEntityPlayer().prevRenderYawOffset, event.getEntityPlayer().renderYawOffset, event.getPartialRenderTick());
            render(event.getEntityPlayer().limbSwing, event.getEntityPlayer().limbSwingAmount, event.getEntityPlayer().ticksExisted, event.getEntityPlayer().getRotationYawHead(), f7, f4, event.getEntity(), event.getRenderer().getMainModel());
            float f8 = (float)event.getEntityPlayer().ticksExisted + event.getPartialRenderTick();
            GlStateManager.disableRescaleNormal();
            GlStateManager.rotate(190.0F, 0.0F, 1.0F, 0.0F);
            event.getRenderer().getMainModel().setRotationAngles(event.getEntityPlayer().limbSwing, event.getEntityPlayer().limbSwingAmount, event.getEntityPlayer().ticksExisted, event.getEntityPlayer().getRotationYawHead(), f7, f4, event.getEntity());
            GlStateManager.disableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
            GlStateManager.popMatrix();
        }
    }

    private static void render(float limbSwing, float limbSwingAmount, int ageInTicks, float netHeadYaw, float headPitch, float scale, Entity entityIn, ModelBiped model) {
        model.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        GlStateManager.pushMatrix();
        if (model.isChild)
        {
            float f = 2.0F;
            GlStateManager.scale(0.75F, 0.75F, 0.75F);
            GlStateManager.translate(0.0F, 16.0F * scale, 0.0F);
            model.bipedHead.render(scale);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
            model.bipedBody.render(scale);
            model.bipedRightArm.render(scale);
            model.bipedLeftArm.render(scale);
            model.bipedRightLeg.render(scale);
            model.bipedLeftLeg.render(scale);
            model.bipedHeadwear.render(scale);
        }
        else
        {
            if (entityIn.isSneaking())
            {
                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }

//            model.bipedHead.rotateAngleY += ((float) Math.PI);
//            model.bipedBody.rotateAngleY += ((float) Math.PI);
//            model.bipedRightArm.rotateAngleY += ((float) Math.PI);
//            model.bipedLeftArm.rotateAngleY += ((float) Math.PI);
//            model.bipedRightLeg.rotateAngleY += ((float) Math.PI);
//            model.bipedLeftLeg.rotateAngleY += ((float) Math.PI);
//            model.bipedHeadwear.rotateAngleY += ((float) Math.PI);
            model.bipedHead.render(scale);
            model.bipedBody.render(scale);
            model.bipedRightArm.render(scale);
            model.bipedLeftArm.render(scale);
            model.bipedRightLeg.render(scale);
            model.bipedLeftLeg.render(scale);
            model.bipedHeadwear.render(scale);
        }

        GlStateManager.popMatrix();
    }

    protected static float interpolateRotation(float prevYawOffset, float yawOffset, float partialTicks)
    {
        float f;

        for (f = yawOffset - prevYawOffset; f < -180.0F; f += 360.0F)
        {
            ;
        }

        while (f >= 180.0F)
        {
            f -= 360.0F;
        }

        return prevYawOffset + partialTicks * f;
    }
}
