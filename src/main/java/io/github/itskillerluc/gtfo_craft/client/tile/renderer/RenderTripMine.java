package io.github.itskillerluc.gtfo_craft.client.tile.renderer;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityTripMine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.Vector3d;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import org.lwjgl.opengl.GL11;

import javax.vecmath.Vector4f;

public class RenderTripMine extends TileEntitySpecialRenderer<TileEntityTripMine> {
    @Override
    public void render(TileEntityTripMine te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
        createLine(new Vec3d(x, y, z), 0.1f, new Vector4f(1, .5f, .5f, 5f), new Vector4f(255, 255, 255, 255), partialTicks, te);
    }

    private void createLine(Vec3d c1, float width, Vector4f color, Vector4f brightness, float partialTick, TileEntityTripMine te) {
        Tessellator tessellator = Tessellator.getInstance();

        tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_ONE, GL11.GL_ONE);
        GlStateManager.enableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.pushMatrix();
        this.bindTexture(new ResourceLocation(GtfoCraft.MODID, "textures/effects/laser.png"));

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP p = mc.player;
        double doubleX = p.lastTickPosX + (p.posX - p.lastTickPosX) * partialTick;
        double doubleY = p.lastTickPosY + (p.posY - p.lastTickPosY) * partialTick;
        double doubleZ = p.lastTickPosZ + (p.posZ - p.lastTickPosZ) * partialTick;
        GlStateManager.translate(-doubleX, -doubleY, -doubleZ);
        double ex = te.getDirection().getDirectionVec().getX();
        double ey = te.getDirection().getDirectionVec().getY();
        double ez = te.getDirection().getDirectionVec().getZ();
        Vec3d start = new Vec3d(te.getPos().getX() +  (ex < 0 ? 1 : ((ex == 0 ? 1 : 0) * 0.5f)), te.getPos().getY() + (ey < 0 ? 1 : ((ey == 0 ? 1 : 0) * 0.5f)), te.getPos().getZ() +  (ez < 0 ? 1 : ((ez == 0 ? 1 : 0) * 0.5f)));
        Vec3d end = start.addVector(ex * te.getLaserLength(), ey * te.getLaserLength(), ez * te.getLaserLength());
        Vec3d playerPos = new Vec3d(doubleX, doubleY + p.getEyeHeight(), doubleZ);

        Vec3d PS = start.subtract(playerPos);
        Vec3d SE = end.subtract(start);

        Vec3d crossBeam = PS.crossProduct(SE).normalize();
        Vec3d p1 = start.add(crossBeam.scale(width * .5d));
        Vec3d p2 = start.subtract(crossBeam.scale(width * .5d));
        Vec3d p3 = end.add(crossBeam.scale(width * .5d));
        Vec3d p4 = end.subtract(crossBeam.scale(width * .5d));

        BufferBuilder bufferBuilder = tessellator.getBuffer();
        int b1 = 240 >> 16 & 65535;
        int b2 = 240 & 65535;
        //.color(color.x, color.y, color.z, color.w * 0.5f).lightmap(b1, b2)
        bufferBuilder.pos(p1.x, p1.y, p1.z).tex(0.0D, 0.0D).lightmap(b1, b2).color(color.x, color.y, color.z, color.w * 0.5f).endVertex();
        bufferBuilder.pos(p3.x, p3.y, p3.z).tex(1.0D, 0.0D).lightmap(b1, b2).color(color.x, color.y, color.z, color.w * 0.5f).endVertex();
        bufferBuilder.pos(p4.x, p4.y, p4.z).tex(1.0D, 1.0D).lightmap(b1, b2).color(color.x, color.y, color.z, color.w * 0.5f).endVertex();
        bufferBuilder.pos(p2.x, p2.y, p2.z).tex(0.0D, 1.0D).lightmap(b1, b2).color(color.x, color.y, color.z, color.w * 0.5f).endVertex();
        tessellator.draw();

        GlStateManager.popMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }
}
