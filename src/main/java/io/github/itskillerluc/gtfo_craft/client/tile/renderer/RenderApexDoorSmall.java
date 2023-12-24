package io.github.itskillerluc.gtfo_craft.client.tile.renderer;

import io.github.itskillerluc.gtfo_craft.client.tile.model.ModelApexDoorSmall;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityApexDoorSmall;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class RenderApexDoorSmall extends GeoBlockRenderer<TileEntityApexDoorSmall> {
    public RenderApexDoorSmall() {
        super(new ModelApexDoorSmall());
    }
}
