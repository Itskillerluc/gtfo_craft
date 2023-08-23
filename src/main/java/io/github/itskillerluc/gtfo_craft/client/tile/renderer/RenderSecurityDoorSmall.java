package io.github.itskillerluc.gtfo_craft.client.tile.renderer;

import io.github.itskillerluc.gtfo_craft.client.tile.model.ModelSecurityDoorSmall;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntitySecurityDoorSmall;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class RenderSecurityDoorSmall extends GeoBlockRenderer<TileEntitySecurityDoorSmall> {
    public RenderSecurityDoorSmall() {
        super(new ModelSecurityDoorSmall());
    }
}
