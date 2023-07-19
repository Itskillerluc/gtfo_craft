package io.github.itskillerluc.gtfo_craft.mixin;

import io.github.itskillerluc.gtfo_craft.registry.BlockRegistry;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityMob.class)
@Debug(export = true,print = true)
public abstract class MixinDoorBreakAI extends EntityLiving {

    public MixinDoorBreakAI(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();

    }
}
