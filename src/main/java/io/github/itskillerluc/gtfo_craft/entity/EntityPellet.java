package io.github.itskillerluc.gtfo_craft.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityPellet extends EntityThrowable {
    public int damage = 2;
    public EntityPellet(World worldIn) {
        super(worldIn);
    }

    public EntityPellet(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    public EntityPellet(World worldIn, EntityLivingBase throwerIn, int damage) {
        super(worldIn, throwerIn);
        this.damage = damage;
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.entityHit != null) {
            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), damage);
        }

        if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)3);
            this.setDead();
        }
    }
}
