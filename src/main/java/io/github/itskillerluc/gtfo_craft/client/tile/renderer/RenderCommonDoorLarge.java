package io.github.itskillerluc.gtfo_craft.client.tile.renderer;

import io.github.itskillerluc.gtfo_craft.client.tile.model.ModelCommonDoorLarge;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityCommonDoorLarge;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class RenderCommonDoorLarge extends GeoBlockRenderer<TileEntityCommonDoorLarge> {
    public RenderCommonDoorLarge() {
        super(new ModelCommonDoorLarge());
    }
}
