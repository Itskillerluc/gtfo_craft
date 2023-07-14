package io.github.itskillerluc.gtfo_craft.client.entity.renderer;

import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelShooter;
import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelStriker;
import io.github.itskillerluc.gtfo_craft.entity.EntityShooter;
import io.github.itskillerluc.gtfo_craft.entity.EntityStriker;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderShooter extends GeoEntityRenderer<EntityShooter> {
    public RenderShooter(RenderManager renderManager) {
        super(renderManager, new ModelShooter());
    }
}
