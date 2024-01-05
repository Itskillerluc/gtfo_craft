package io.github.itskillerluc.gtfo_craft.client.event;

import com.google.common.collect.Lists;
import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.client.tile.renderer.RenderTripMine;
import io.github.itskillerluc.gtfo_craft.data.Scan;
import io.github.itskillerluc.gtfo_craft.data.ScanWorldSavedData;
import io.github.itskillerluc.gtfo_craft.registry.BlockRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix;
import org.lwjgl.util.vector.Matrix2f;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import software.bernie.geckolib3.core.util.MathUtil;
import software.bernie.shadowed.eliotlash.mclib.utils.MathUtils;

import javax.vecmath.Matrix3d;
import javax.vecmath.Matrix4d;
import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = GtfoCraft.MODID, value = Side.CLIENT)
public class ForgeEvent {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void renderBlockOverlay(RenderGameOverlayEvent.Post event) {
        if (Minecraft.getMinecraft().player.isInsideOfMaterial(BlockRegistry.FOG_MATERIAL)) {
            renderFogOverlay(event.getResolution());
        }
    }

    @SubscribeEvent
    public static void renderHand(RenderHandEvent event) {
        if (Minecraft.getMinecraft().player.getEntityData().getBoolean("hasBattery") && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0 && !Minecraft.getMinecraft().gameSettings.hideGUI && !Minecraft.getMinecraft().playerController.isSpectator()) {
            event.setCanceled(true);
            renderArmFirstPerson(0, 0, EnumHandSide.RIGHT);
            GlStateManager.pushMatrix();
            ItemStack heldItem = new ItemStack(BlockRegistry.BATTERY);
            GlStateManager.translate(0, -0.3, -1);
            GlStateManager.scale(1.2, 1.2, 1.2);
            GlStateManager.rotate(180, 0, 1, 0);
            GlStateManager.rotate(90, 0, 0, 1);
            Minecraft.getMinecraft().getRenderItem().renderItem(heldItem, ItemCameraTransforms.TransformType.FIXED);
            GlStateManager.popMatrix();
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

        if (event.getEntityPlayer().getEntityData().getBoolean("hasBattery")) {
            event.setCanceled(true);
            GlStateManager.pushMatrix();
            GlStateManager.enableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
            event.getRenderer().bindTexture(event.getRenderer().getEntityTexture(((AbstractClientPlayer) event.getEntityPlayer())));

            EntityLivingBase entity = event.getEntityPlayer();
            float partialTicks = event.getPartialRenderTick();
            ModelBiped mainModel = event.getRenderer().getMainModel();
            mainModel.swingProgress = entity.getSwingProgress(partialTicks);
            boolean shouldSit = entity.isRiding() && (entity.getRidingEntity() != null && entity.getRidingEntity().shouldRiderSit());
            mainModel.isRiding = shouldSit;
            mainModel.isChild = entity.isChild();

            float f = interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks);
            float f1 = interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialTicks);
            float f2 = f1 - f;

            if (shouldSit && entity.getRidingEntity() instanceof EntityLivingBase)
            {
                EntityLivingBase entitylivingbase = (EntityLivingBase)entity.getRidingEntity();
                f = interpolateRotation(entitylivingbase.prevRenderYawOffset, entitylivingbase.renderYawOffset, partialTicks);
                f2 = f1 - f;
                float f3 = MathHelper.wrapDegrees(f2);

                if (f3 < -85.0F)
                {
                    f3 = -85.0F;
                }

                if (f3 >= 85.0F)
                {
                    f3 = 85.0F;
                }

                f = f1 - f3;

                if (f3 * f3 > 2500.0F)
                {
                    f += f3 * 0.2F;
                }

                f2 = f1 - f;
            }

            float f7 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
            float f8 = (float)entity.ticksExisted + partialTicks;
            applyRotations(entity, f8, f, partialTicks);
            float f4 = event.getRenderer().prepareScale((AbstractClientPlayer) entity, partialTicks);
            float f5 = 0.0F;
            float f6 = 0.0F;

            if (!entity.isRiding())
            {
                f5 = entity.prevLimbSwingAmount + (entity.limbSwingAmount - entity.prevLimbSwingAmount) * partialTicks;
                f6 = entity.limbSwing - entity.limbSwingAmount * (1.0F - partialTicks);

                if (entity.isChild())
                {
                    f6 *= 3.0F;
                }

                if (f5 > 1.0F)
                {
                    f5 = 1.0F;
                }
                f2 = f1 - f; // Forge: Fix MC-1207
            }

            GlStateManager.enableAlpha();
            mainModel.setLivingAnimations(entity, f6, f5, partialTicks);
            mainModel.setRotationAngles(f6, f5, f8, f2, f7, f4, entity);
            render(f6, f5, entity.ticksExisted, f2, f7, f4, entity, mainModel);
            ItemStack heldItem = new ItemStack(BlockRegistry.BATTERY);
            GlStateManager.pushMatrix();

            GlStateManager.rotate(50, 1, 0, 0);
            GlStateManager.rotate(180, 0, 1, 0);
            GlStateManager.rotate(90, 0, 0, 1);

            GlStateManager.translate(entity.isSneaking() ? 0.1 : -0.1, -0.15, entity.isSneaking() ? 0.2 : 0);
            GlStateManager.scale(1.5f, 1.5f, 1.5f);

            Minecraft.getMinecraft().getItemRenderer().renderItem(entity, heldItem, ItemCameraTransforms.TransformType.NONE);
            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
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
                GlStateManager.translate(0.0F, 0.333333F, 0.0F);
                model.bipedBody.rotateAngleX = 0.5F;
                model.bipedRightLeg.rotationPointZ = 4.0F;
                model.bipedLeftLeg.rotationPointZ = 4.0F;
                model.bipedRightLeg.rotationPointY = 9.0F;
                model.bipedLeftLeg.rotationPointY = 9.0F;
                model.bipedHead.rotationPointY = 1.0F;
            }

            model.bipedLeftArm.rotateAngleX = -1;
            model.bipedLeftArm.rotateAngleY = 0.1f;
            model.bipedRightArm.rotateAngleX = -1;
            model.bipedRightArm.rotateAngleY = -0.1f;


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

    protected static <T extends EntityLivingBase> void applyRotations(T entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
    {
        GlStateManager.rotate(180.0F - rotationYaw, 0.0F, 1.0F, 0.0F);

        if (entityLiving.deathTime > 0)
        {
            float f = ((float)entityLiving.deathTime + partialTicks - 1.0F) / 20.0F * 1.6F;
            f = MathHelper.sqrt(f);

            if (f > 1.0F)
            {
                f = 1.0F;
            }

            GlStateManager.rotate(f * 90F, 0.0F, 0.0F, 1.0F);
        }
        else
        {
            String s = TextFormatting.getTextWithoutFormattingCodes(entityLiving.getName());

            if (s != null && ("Dinnerbone".equals(s) || "Grumm".equals(s)) && (!(entityLiving instanceof EntityPlayer) || ((EntityPlayer)entityLiving).isWearing(EnumPlayerModelParts.CAPE)))
            {
                GlStateManager.translate(0.0F, entityLiving.height + 0.1F, 0.0F);
                GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
            }
        }
    }

    private static void renderArmFirstPerson(float p_187456_1_, float p_187456_2_, EnumHandSide p_187456_3_)
    {
        boolean flag = p_187456_3_ != EnumHandSide.LEFT;
        GlStateManager.pushMatrix();
        AbstractClientPlayer abstractclientplayer = transform(true, p_187456_1_, p_187456_2_);
        RenderPlayer renderplayer = (RenderPlayer)Minecraft.getMinecraft().getRenderManager().<AbstractClientPlayer>getEntityRenderObject(abstractclientplayer);

        float a = 1.0F;
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        float a1 = 0.0625F;
        ModelPlayer modelplayer = renderplayer.getMainModel();
        setModelVisibilities(abstractclientplayer);
        GlStateManager.enableBlend();
        modelplayer.isSneak = false;
        modelplayer.swingProgress = 0.0F;
        modelplayer.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, abstractclientplayer);
        modelplayer.bipedRightArm.rotateAngleX = 0.0F;
        modelplayer.bipedRightArm.render(0.0625F);
        modelplayer.bipedRightArmwear.rotateAngleX = 0.0F;
        modelplayer.bipedRightArmwear.render(0.0625F);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        transform(false, p_187456_1_, p_187456_2_);

        modelplayer.bipedLeftArm.rotateAngleX = 0.0F;
        modelplayer.bipedLeftArm.render(0.0625F);
        modelplayer.bipedLeftArmwear.rotateAngleX = 0.0F;
        modelplayer.bipedLeftArmwear.render(0.0625F);
        GlStateManager.popMatrix();



        GlStateManager.enableCull();
    }

    private static AbstractClientPlayer transform(boolean flag, float p_187456_1_, float p_187456_2_){
        float f = flag ? 1.0F : -1.0F;
        float f1 = MathHelper.sqrt(p_187456_2_);
        float f2 = -0.3F * MathHelper.sin(f1 * (float)Math.PI);
        float f3 = 0.4F * MathHelper.sin(f1 * ((float)Math.PI * 2F));
        float f4 = -0.4F * MathHelper.sin(p_187456_2_ * (float)Math.PI);
        GlStateManager.translate(f * (f2 + 0.64000005F), f3 + -0.6F + p_187456_1_ * -0.6F, f4 + -0.71999997F);
        GlStateManager.rotate(f * 45.0F, 0.0F, 1.0F, 0.0F);
        float f5 = MathHelper.sin(p_187456_2_ * p_187456_2_ * (float)Math.PI);
        float f6 = MathHelper.sin(f1 * (float)Math.PI);
        GlStateManager.rotate(f * f6 * 70.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(f * f5 * -20.0F, 0.0F, 0.0F, 1.0F);
        AbstractClientPlayer abstractclientplayer = Minecraft.getMinecraft().player;
        Minecraft.getMinecraft().getTextureManager().bindTexture(abstractclientplayer.getLocationSkin());
        GlStateManager.translate(f * -1.0F, 3.6F, 3.5F);
        GlStateManager.rotate(f * 120.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(200.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(f * -135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(f * 5.6F, 0.0F, 0.0F);
        GlStateManager.disableCull();
        return abstractclientplayer;
    }

    private static void setModelVisibilities(AbstractClientPlayer clientPlayer)
    {
        ModelPlayer modelplayer = ((RenderPlayer) Minecraft.getMinecraft().getRenderManager().<AbstractClientPlayer>getEntityRenderObject(clientPlayer)).getMainModel();

        if (clientPlayer.isSpectator())
        {
            modelplayer.setVisible(false);
            modelplayer.bipedHead.showModel = true;
            modelplayer.bipedHeadwear.showModel = true;
        }
        else
        {
            ItemStack itemstack = clientPlayer.getHeldItemMainhand();
            ItemStack itemstack1 = clientPlayer.getHeldItemOffhand();
            modelplayer.setVisible(true);
            modelplayer.bipedHeadwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.HAT);
            modelplayer.bipedBodyWear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.JACKET);
            modelplayer.bipedLeftLegwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.LEFT_PANTS_LEG);
            modelplayer.bipedRightLegwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.RIGHT_PANTS_LEG);
            modelplayer.bipedLeftArmwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.LEFT_SLEEVE);
            modelplayer.bipedRightArmwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.RIGHT_SLEEVE);
            modelplayer.isSneak = clientPlayer.isSneaking();
            ModelBiped.ArmPose modelbiped$armpose = ModelBiped.ArmPose.EMPTY;
            ModelBiped.ArmPose modelbiped$armpose1 = ModelBiped.ArmPose.EMPTY;

            if (!itemstack.isEmpty())
            {
                modelbiped$armpose = ModelBiped.ArmPose.ITEM;

                if (clientPlayer.getItemInUseCount() > 0)
                {
                    EnumAction enumaction = itemstack.getItemUseAction();

                    if (enumaction == EnumAction.BLOCK)
                    {
                        modelbiped$armpose = ModelBiped.ArmPose.BLOCK;
                    }
                    else if (enumaction == EnumAction.BOW)
                    {
                        modelbiped$armpose = ModelBiped.ArmPose.BOW_AND_ARROW;
                    }
                }
            }

            if (!itemstack1.isEmpty())
            {
                modelbiped$armpose1 = ModelBiped.ArmPose.ITEM;

                if (clientPlayer.getItemInUseCount() > 0)
                {
                    EnumAction enumaction1 = itemstack1.getItemUseAction();

                    if (enumaction1 == EnumAction.BLOCK)
                    {
                        modelbiped$armpose1 = ModelBiped.ArmPose.BLOCK;
                    }
                    // FORGE: fix MC-88356 allow offhand to use bow and arrow animation
                    else if (enumaction1 == EnumAction.BOW)
                    {
                        modelbiped$armpose1 = ModelBiped.ArmPose.BOW_AND_ARROW;
                    }
                }
            }

            if (clientPlayer.getPrimaryHand() == EnumHandSide.RIGHT)
            {
                modelplayer.rightArmPose = modelbiped$armpose;
                modelplayer.leftArmPose = modelbiped$armpose1;
            }
            else
            {
                modelplayer.rightArmPose = modelbiped$armpose1;
                modelplayer.leftArmPose = modelbiped$armpose;
            }
        }
    }

    @SubscribeEvent
    public static void renderWorld(RenderWorldLastEvent event) {
        synchronized (ScanWorldSavedData.get(Minecraft.getMinecraft().world).scanList) {
            for (Scan scan : ScanWorldSavedData.get(Minecraft.getMinecraft().world).scanList) {
                if (scan == null) {
                    return;
                }

                Tessellator tessellator = Tessellator.getInstance();

                GlStateManager.enableBlend();
                GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_SRC_ALPHA);
                GlStateManager.enableDepth();
                GlStateManager.depthMask(false);
                GlStateManager.pushMatrix();
                Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(GtfoCraft.MODID, "textures/effects/scan.png"));
                EntityPlayerSP player = Minecraft.getMinecraft().player;
                double doubleX = player.lastTickPosX + (player.posX - player.lastTickPosX) * event.getPartialTicks();
                double doubleY = player.lastTickPosY + (player.posY - player.lastTickPosY) * event.getPartialTicks();
                double doubleZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.getPartialTicks();
                GlStateManager.rotate(0, 1, 0, 0);
                GlStateManager.translate(-doubleX, -doubleY, -doubleZ);
                tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
                BufferBuilder buffer = tessellator.getBuffer();
                int y1 = scan.getPos1().getY();
                int y2 = scan.getPos2().getY();
                int x1 = scan.getPos1().getX();
                int x2 = scan.getPos2().getX();
                int z1 = scan.getPos1().getZ();
                int z2 = scan.getPos2().getZ();
                double y = Math.min(y1, y2);

                if (x1 > x2 ^ z1 > z2) {
                    buffer.pos(x1, y + 0.005d, scan.getPos1().getZ()).tex(0, 0 + Minecraft.getMinecraft().world.getWorldTime() % 100 / 100d).color((scan.getColor() & 0xFF0000) >> 16, (scan.getColor() & 0x00FF00) >> 8, (scan.getColor() & 0x0000FF), 255).endVertex();
                    buffer.pos(x2, y + 0.005d, scan.getPos1().getZ()).tex(1, 0 + Minecraft.getMinecraft().world.getWorldTime() % 100 / 100d).color((scan.getColor() & 0xFF0000) >> 16, (scan.getColor() & 0x00FF00) >> 8, (scan.getColor() & 0x0000FF), 255).endVertex();
                    buffer.pos(x2, y + 0.005d, scan.getPos2().getZ()).tex(1, 1 + Minecraft.getMinecraft().world.getWorldTime() % 100 / 100d).color((scan.getColor() & 0xFF0000) >> 16, (scan.getColor() & 0x00FF00) >> 8, (scan.getColor() & 0x0000FF), 255).endVertex();
                    buffer.pos(x1, y + 0.005d, scan.getPos2().getZ()).tex(0f, 1 + Minecraft.getMinecraft().world.getWorldTime() % 100 / 100d).color((scan.getColor() & 0xFF0000) >> 16, (scan.getColor() & 0x00FF00) >> 8, (scan.getColor() & 0x0000FF), 255).endVertex();
                } else {
                    buffer.pos(x2, y + 0.005d, scan.getPos1().getZ()).tex(1, 0 + Minecraft.getMinecraft().world.getWorldTime() % 100 / 100d).color((scan.getColor() & 0xFF0000) >> 16, (scan.getColor() & 0x00FF00) >> 8, (scan.getColor() & 0x0000FF), 255).endVertex();
                    buffer.pos(x1, y + 0.005d, scan.getPos1().getZ()).tex(0, 0 + Minecraft.getMinecraft().world.getWorldTime() % 100 / 100d).color((scan.getColor() & 0xFF0000) >> 16, (scan.getColor() & 0x00FF00) >> 8, (scan.getColor() & 0x0000FF), 255).endVertex();
                    buffer.pos(x1, y + 0.005d, scan.getPos2().getZ()).tex(0f, 1 + Minecraft.getMinecraft().world.getWorldTime() % 100 / 100d).color((scan.getColor() & 0xFF0000) >> 16, (scan.getColor() & 0x00FF00) >> 8, (scan.getColor() & 0x0000FF), 255).endVertex();
                    buffer.pos(x2, y + 0.005d, scan.getPos2().getZ()).tex(1, 1 + Minecraft.getMinecraft().world.getWorldTime() % 100 / 100d).color((scan.getColor() & 0xFF0000) >> 16, (scan.getColor() & 0x00FF00) >> 8, (scan.getColor() & 0x0000FF), 255).endVertex();
                }
                tessellator.draw();

                tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
                Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(GtfoCraft.MODID, "textures/effects/laser.png"));

                if (((x1 < x2) && (z1 > z2)) || ((x1 > x2) && (z1 > z2))) {
                    buffer.pos(-0.3f + MathUtil.lerpValues((float) scan.getTimer() / (float) scan.getTime(), x1, x2), y + 0.001d, scan.getPos1().getZ()).tex(0, 1).color((scan.getColor() & 0xFF0000) >> 16, (scan.getColor() & 0x00FF00) >> 8, (scan.getColor() & 0x0000FF), 255).endVertex();
                    buffer.pos(MathUtil.lerpValues((float) scan.getTimer() / (float) scan.getTime(), x1, x2), y + 0.001d, scan.getPos1().getZ()).tex(0, 0).color((scan.getColor() & 0xFF0000) >> 16, (scan.getColor() & 0x00FF00) >> 8, (scan.getColor() & 0x0000FF), 255).endVertex();
                    buffer.pos(MathUtil.lerpValues((float) scan.getTimer() / (float) scan.getTime(), x1, x2), y + 0.001d, scan.getPos2().getZ()).tex(1, 0).color((scan.getColor() & 0xFF0000) >> 16, (scan.getColor() & 0x00FF00) >> 8, (scan.getColor() & 0x0000FF), 255).endVertex();
                    buffer.pos(-0.3f + MathUtil.lerpValues((float) scan.getTimer() / (float) scan.getTime(), x1, x2), y + 0.001d, scan.getPos2().getZ()).tex(1f, 1).color((scan.getColor() & 0xFF0000) >> 16, (scan.getColor() & 0x00FF00) >> 8, (scan.getColor() & 0x0000FF), 255).endVertex();
                } else {
                    buffer.pos(MathUtil.lerpValues((float) scan.getTimer() / (float) scan.getTime(), x1, x2), y + 0.001d, scan.getPos1().getZ()).tex(0, 0).color((scan.getColor() & 0xFF0000) >> 16, (scan.getColor() & 0x00FF00) >> 8, (scan.getColor() & 0x0000FF), 255).endVertex();
                    buffer.pos(-0.3f + MathUtil.lerpValues((float) scan.getTimer() / (float) scan.getTime(), x1, x2), y + 0.001d, scan.getPos1().getZ()).tex(0, 1).color((scan.getColor() & 0xFF0000) >> 16, (scan.getColor() & 0x00FF00) >> 8, (scan.getColor() & 0x0000FF), 255).endVertex();
                    buffer.pos(-0.3f + MathUtil.lerpValues((float) scan.getTimer() / (float) scan.getTime(), x1, x2), y + 0.001d, scan.getPos2().getZ()).tex(1f, 1).color((scan.getColor() & 0xFF0000) >> 16, (scan.getColor() & 0x00FF00) >> 8, (scan.getColor() & 0x0000FF), 255).endVertex();
                    buffer.pos(MathUtil.lerpValues((float) scan.getTimer() / (float) scan.getTime(), x1, x2), y + 0.001d, scan.getPos2().getZ()).tex(1, 0).color((scan.getColor() & 0xFF0000) >> 16, (scan.getColor() & 0x00FF00) >> 8, (scan.getColor() & 0x0000FF), 255).endVertex();
                }

                tessellator.draw();



                if (scan.getLinkedPos() != null) {
                    tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
                    Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(GtfoCraft.MODID, "textures/effects/beam.png"));

                    Vec3d start = new Vec3d( (x1 + x2) / 2f, y + 0.1f, (scan.getPos1().getZ() + scan.getPos2().getZ()) / 2f);
                    Vec3d end = new Vec3d(scan.getLinkedPos().getX() + 0.5f, scan.getLinkedPos().getY() + 0.5f, scan.getLinkedPos().getZ() + 0.5f);
                    Vec3d playerPos = new Vec3d(doubleX, doubleY + player.getEyeHeight(), doubleZ);

                    Vec3d PS = start.subtract(playerPos);
                    Vec3d SE = end.subtract(start);
                    float width = 0.2f;

                    Vec3d crossBeam = PS.crossProduct(SE).normalize();
                    Vec3d p1 = start.add(crossBeam.scale(width * .5d));
                    Vec3d p2 = start.subtract(crossBeam.scale(width * .5d));
                    Vec3d p3 = end.add(crossBeam.scale(width * .5d));
                    Vec3d p4 = end.subtract(crossBeam.scale(width * .5d));

                    BufferBuilder bufferBuilder = tessellator.getBuffer();

                    bufferBuilder.pos(p1.x, p1.y, p1.z).tex(0.0D, 0.0D).color((scan.getColor() & 0xFF0000) >> 16, (scan.getColor() & 0x00FF00) >> 8, (scan.getColor() & 0x0000FF), 255).endVertex();
                    bufferBuilder.pos(p3.x, p3.y, p3.z).tex(1.0D, 0.0D).color((scan.getColor() & 0xFF0000) >> 16, (scan.getColor() & 0x00FF00) >> 8, (scan.getColor() & 0x0000FF), 255).endVertex();
                    bufferBuilder.pos(p4.x, p4.y, p4.z).tex(1.0D, 1.0D).color((scan.getColor() & 0xFF0000) >> 16, (scan.getColor() & 0x00FF00) >> 8, (scan.getColor() & 0x0000FF), 255).endVertex();
                    bufferBuilder.pos(p2.x, p2.y, p2.z).tex(0.0D, 1.0D).color((scan.getColor() & 0xFF0000) >> 16, (scan.getColor() & 0x00FF00) >> 8, (scan.getColor() & 0x0000FF), 255).endVertex();
                    tessellator.draw();
                }

                GlStateManager.enableBlend();
                GlStateManager.depthMask(true);

                GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GlStateManager.popMatrix();
            }
        }
    }
}
