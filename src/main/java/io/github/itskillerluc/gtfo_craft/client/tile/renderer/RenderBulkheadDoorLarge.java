package io.github.itskillerluc.gtfo_craft.client.tile.renderer;

import io.github.itskillerluc.gtfo_craft.client.tile.model.ModelBulkheadDoorLarge;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityBulkheadDoorLarge;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class RenderBulkheadDoorLarge extends GeoBlockRenderer<TileEntityBulkheadDoorLarge> {
    public RenderBulkheadDoorLarge() {
        super(new ModelBulkheadDoorLarge());
    }
}
