package io.github.itskillerluc.gtfo_craft.client.tile.renderer;

import io.github.itskillerluc.gtfo_craft.client.tile.model.ModelSecurityDoorLarge;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntitySecurityDoorLarge;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class RenderSecurityDoorLarge extends GeoBlockRenderer<TileEntitySecurityDoorLarge> {
    public RenderSecurityDoorLarge() {
        super(new ModelSecurityDoorLarge());
    }
}
