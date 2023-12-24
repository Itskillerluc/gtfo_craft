package io.github.itskillerluc.gtfo_craft.client.tile.renderer;

import io.github.itskillerluc.gtfo_craft.client.tile.model.ModelApexDoorLarge;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityApexDoorLarge;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class RenderApexDoorLarge extends GeoBlockRenderer<TileEntityApexDoorLarge> {
    public RenderApexDoorLarge() {
        super(new ModelApexDoorLarge());
    }
}
