package io.github.itskillerluc.gtfo_craft.client.entity.renderer;

import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelPellet;
import io.github.itskillerluc.gtfo_craft.entity.EntityPellet;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.math.Vec3d;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

import javax.vecmath.Vector2d;

public class RenderPellet extends GeoProjectilesRenderer<EntityPellet> {
    public RenderPellet(RenderManager renderManager) {
        super(renderManager, new ModelPellet());
    }

    @Override
    public void doRender(EntityPellet entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        Vec3d direction = new Vec3d(entity.motionX, entity.motionY, entity.motionZ);
        Vector2d hDir = new Vector2d((float) direction.x, (float) direction.z);
        hDir.normalize();
        float angleY = (float) Math.toDegrees(Math.acos(hDir.x));
        GlStateManager.rotate((hDir.y > 0 ? -angleY : angleY) - entityYaw, 0, 1, 0);
        GlStateManager.rotate((float) Math.toDegrees(Math.asin(direction.normalize().y)), (float) hDir.x, 0, (float) hDir.y);
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        GlStateManager.popMatrix();
    }
}
