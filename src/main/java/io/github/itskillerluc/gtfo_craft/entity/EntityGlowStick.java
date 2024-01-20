package io.github.itskillerluc.gtfo_craft.entity;

import io.github.itskillerluc.gtfo_craft.registry.BlockRegistry;
import io.github.itskillerluc.gtfo_craft.registry.ItemRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityGlowStick extends EntityArrow {
    public static final int TIME = 1200;

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
        if (!world.isAirBlock(getPosition())) return;
        world.setBlockState(getPosition(), BlockRegistry.GLOW_STICK_BLOCK.getDefaultState());
        this.motionX = result.hitVec.x - this.posX;
        this.motionY = result.hitVec.y - this.posY;
        this.motionZ = result.hitVec.z - this.posZ;
        float f2 = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
        this.posX -= this.motionX / (double)f2 * 0.05000000074505806D;
        this.posY -= this.motionY / (double)f2 * 0.05000000074505806D;
        this.posZ -= this.motionZ / (double)f2 * 0.05000000074505806D;
        this.inGround = true;
        this.arrowShake = 7;
        this.setIsCritical(false);
        pickupStatus = PickupStatus.DISALLOWED;
    }
    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(ItemRegistry.GLOW_STICK);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (this.timeInGround >= TIME) {
            this.setDead();
        }
    }
}
