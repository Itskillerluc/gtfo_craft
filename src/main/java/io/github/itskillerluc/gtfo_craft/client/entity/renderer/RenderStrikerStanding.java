package io.github.itskillerluc.gtfo_craft.client.entity.renderer;

import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelStrikerStanding;
import io.github.itskillerluc.gtfo_craft.entity.EntityStrikerStanding;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderStrikerStanding extends GeoEntityRenderer<EntityStrikerStanding> {
    public RenderStrikerStanding(RenderManager renderManager) {
        super(renderManager, new ModelStrikerStanding());
    }
}
