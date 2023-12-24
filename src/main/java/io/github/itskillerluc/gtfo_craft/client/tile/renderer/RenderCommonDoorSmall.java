package io.github.itskillerluc.gtfo_craft.client.tile.renderer;

import io.github.itskillerluc.gtfo_craft.client.tile.model.ModelCommonDoorSmall;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityCommonDoorSmall;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class RenderCommonDoorSmall extends GeoBlockRenderer<TileEntityCommonDoorSmall> {
    public RenderCommonDoorSmall() {
        super(new ModelCommonDoorSmall());
    }
}
