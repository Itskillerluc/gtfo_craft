package io.github.itskillerluc.gtfo_craft.client.tile.renderer;

import io.github.itskillerluc.gtfo_craft.client.tile.model.ModelTurret;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityTurret;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class RenderTurret extends GeoBlockRenderer<TileEntityTurret> {
    public RenderTurret() {
        super(new ModelTurret());
    }
}
