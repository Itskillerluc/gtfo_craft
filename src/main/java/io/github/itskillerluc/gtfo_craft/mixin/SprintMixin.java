package io.github.itskillerluc.gtfo_craft.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EntityPlayerSP.class)
@Debug(export = true)
public class SprintMixin {
    @ModifyVariable(method = "onLivingUpdate()V", at = @At("STORE"), ordinal = 4)
    private boolean injected(boolean flag4) {
        return !Minecraft.getMinecraft().player.getEntityData().getBoolean("hasBattery") && flag4;
    }
}
