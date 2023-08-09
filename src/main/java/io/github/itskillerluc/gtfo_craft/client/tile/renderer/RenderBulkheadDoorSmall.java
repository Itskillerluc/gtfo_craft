package io.github.itskillerluc.gtfo_craft.client.tile.renderer;

import io.github.itskillerluc.gtfo_craft.client.tile.model.ModelBulkheadDoorSmall;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityBulkheadDoorSmall;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class RenderBulkheadDoorSmall extends GeoBlockRenderer<TileEntityBulkheadDoorSmall> {
    public RenderBulkheadDoorSmall() {
        super(new ModelBulkheadDoorSmall());
    }
}
