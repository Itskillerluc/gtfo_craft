
package io.github.itskillerluc.gtfo_craft.client.tile.renderer;

import io.github.itskillerluc.gtfo_craft.client.tile.model.ModelPossessedSecurityDoorLarge;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityPossessedSecurityDoorLarge;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class RenderPossessedSecurityDoorLarge extends GeoBlockRenderer<TileEntityPossessedSecurityDoorLarge> {
    public RenderPossessedSecurityDoorLarge() {
        super(new ModelPossessedSecurityDoorLarge());
    }
}
