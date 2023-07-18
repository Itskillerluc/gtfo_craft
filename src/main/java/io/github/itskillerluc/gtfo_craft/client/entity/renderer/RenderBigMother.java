package io.github.itskillerluc.gtfo_craft.client.entity.renderer;

import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelBigMother;
import io.github.itskillerluc.gtfo_craft.client.entity.model.ModelMother;
import io.github.itskillerluc.gtfo_craft.entity.EntityBigMother;
import io.github.itskillerluc.gtfo_craft.entity.EntityMother;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderBigMother extends GeoEntityRenderer<EntityBigMother> {
    public RenderBigMother(RenderManager renderManager) {
        super(renderManager, new ModelBigMother());
    }
}
