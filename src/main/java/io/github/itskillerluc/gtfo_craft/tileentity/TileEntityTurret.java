package io.github.itskillerluc.gtfo_craft.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.Optional;

public class TileEntityTurret extends TileEntity implements ITickable, IAnimatable {
    public int damage = 0;
    public int speed = 0;
    public int range = 0;
    private EnumFacing direction = EnumFacing.NORTH;
    public static final int CAPACITY = 512;
    public int ammo = 0;
    private int timer = 0;
    private boolean shouldShoot;
    private final AnimationFactory manager = new AnimationFactory(this);
    private static final AnimationBuilder SHOOT = new AnimationBuilder().addAnimation("shoot");
    private static final AnimationBuilder IDLE = new AnimationBuilder().loop("idle");



    public TileEntityTurret(){}

    public TileEntityTurret(int damage, int speed, int range, EnumFacing direction) {
        this.damage = damage;
        this.speed = speed;
        this.direction = direction;
        this.range = range;
    }

    @Override
    public void update() {
        if (ammo >= 1 && timer >= speed) {
            int scalar = 0;
            for (int i = 1; i <= range; i++) {
                if (world.isAirBlock(getPos().offset(direction, i))) {
                    scalar++;
                } else {
                    break;
                }
            }
            Vec3d expansion = new Vec3d(direction.getDirectionVec()).scale(scalar);
            Optional<EntityLivingBase> target = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(getPos().offset(direction)).expand(expansion.x, expansion.y, expansion.z).expand(direction.rotateY().getDirectionVec().getX(), direction.rotateY().getDirectionVec().getY(), direction.rotateY().getDirectionVec().getZ()))
                    .stream().min(Comparator.comparingInt(entity -> (int) entity.getDistanceSq(pos)));

            shouldShoot = false;

            target.ifPresent(entityLiving -> {
                entityLiving.attackEntityFrom(DamageSource.GENERIC, damage);
                world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_NOTE_SNARE, SoundCategory.BLOCKS, 1, 1, false);
                timer = 0;
                shouldShoot = true;
                ammo--;
            });
        }
        timer++;
    }

    @Override
    public NBTTagCompound writeToNBT( NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("timer", this.timer);
        compound.setInteger("ammo", this.ammo);
        compound.setInteger("damage", this.damage);
        compound.setInteger("speed", this.speed);
        compound.setInteger("range", this.range);
        compound.setString("direction", this.direction.getName2());
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.timer = compound.getInteger("timer");
        this.ammo = compound.getInteger("ammo");
        this.damage = compound.getInteger("damage");
        this.speed = compound.getInteger("speed");
        this.range = compound.getInteger("range");
        this.direction = EnumFacing.byName(compound.getString("direction"));
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound tag = super.getUpdateTag();
        tag.setInteger("timer", this.timer);
        tag.setInteger("ammo", this.ammo);
        tag.setInteger("damage", this.damage);
        tag.setInteger("speed", this.speed);
        tag.setInteger("range", this.range);
        tag.setBoolean("shouldShoot", this.shouldShoot);
        tag.setString("direction", this.direction.getName2());
        return tag;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        super.handleUpdateTag(tag);
        this.timer = tag.getInteger("timer");
        this.ammo = tag.getInteger("ammo");
        this.damage = tag.getInteger("damage");
        this.speed = tag.getInteger("speed");
        this.range = tag.getInteger("range");
        this.shouldShoot = tag.getBoolean("shouldShoot");
        this.direction = EnumFacing.byName(tag.getString("direction"));
    }


    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, -1, getUpdateTag());
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }


    private <E extends TileEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (shouldShoot) {
            event.getController().clearAnimationCache();
            event.getController().setAnimation(SHOOT);
            shouldShoot = false;
            return PlayState.CONTINUE;
        } else if (event.getController().getAnimationState() == AnimationState.Stopped){
            event.getController().clearAnimationCache();
            event.getController().setAnimation(IDLE);
            return PlayState.CONTINUE;
        }
        return PlayState.CONTINUE;
    }
    @Override
    public AnimationFactory getFactory() {
        return manager;
    }
}
