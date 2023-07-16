package io.github.itskillerluc.gtfo_craft.entity;

import io.github.itskillerluc.gtfo_craft.registry.BlockRegistry;
import io.github.itskillerluc.gtfo_craft.registry.ItemRegistry;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityFog;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityGlowStick extends EntityArrow {
    public static final int TIME = 400;

    public EntityGlowStick(World worldIn) {
        super(worldIn);
    }

    public EntityGlowStick(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    public EntityGlowStick(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
    }


    @Override
    public void setDead() {
        super.setDead();
    }

    @Override
    protected void onHit(RayTraceResult result) {

    }
    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(ItemRegistry.GLOW_STICK);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (world.getBlockState(getPosition()).getMaterial().isReplaceable()) {
            world.setBlockState(getPosition(), BlockRegistry.GLOW_STICK_BLOCK.getDefaultState());
        }
        if (this.timeInGround >= TIME) {
            this.setDead();
        }
    }
}
