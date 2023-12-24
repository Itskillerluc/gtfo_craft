
package io.github.itskillerluc.gtfo_craft.client.tile.renderer;

import io.github.itskillerluc.gtfo_craft.client.tile.model.ModelPossessedSecurityDoorSmall;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityPossessedSecurityDoorSmall;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class RenderPossessedSecurityDoorSmall extends GeoBlockRenderer<TileEntityPossessedSecurityDoorSmall> {
    public RenderPossessedSecurityDoorSmall() {
        super(new ModelPossessedSecurityDoorSmall());
    }
}
