package io.github.itskillerluc.gtfo_craft.client.tile.renderer;

import io.github.itskillerluc.gtfo_craft.client.tile.model.ModelCocoon;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityCocoon;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class RenderCocoon extends GeoBlockRenderer<TileEntityCocoon> {
    public RenderCocoon() {
        super(new ModelCocoon());
    }
}
