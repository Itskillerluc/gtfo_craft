package io.github.itskillerluc.gtfo_craft.client.tile.renderer;

import io.github.itskillerluc.gtfo_craft.client.tile.model.ModelBreakableDoorSmall;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityBreakableDoorSmall;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class RenderBreakableDoorSmall extends GeoBlockRenderer<TileEntityBreakableDoorSmall> {
    public RenderBreakableDoorSmall() {
        super(new ModelBreakableDoorSmall());
    }
}
