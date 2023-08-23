package io.github.itskillerluc.gtfo_craft.client.tile.renderer;

import io.github.itskillerluc.gtfo_craft.client.tile.model.ModelBreakableDoorLarge;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityBreakableDoorLarge;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class RenderBreakableDoorLarge extends GeoBlockRenderer<TileEntityBreakableDoorLarge> {
    public RenderBreakableDoorLarge() {
        super(new ModelBreakableDoorLarge());
    }
}
