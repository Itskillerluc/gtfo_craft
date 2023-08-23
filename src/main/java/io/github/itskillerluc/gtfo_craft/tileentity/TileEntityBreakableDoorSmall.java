package io.github.itskillerluc.gtfo_craft.tileentity;

import io.github.itskillerluc.gtfo_craft.block.BlockBreakableDoorSmallHelper;
import io.github.itskillerluc.gtfo_craft.block.BlockDoorController;
import io.github.itskillerluc.gtfo_craft.registry.BlockRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class TileEntityBreakableDoorSmall extends TileEntityDoor implements IAnimatable, ITickable {
    private final AnimationFactory manager = new AnimationFactory(this);
    private static final AnimationBuilder OPEN = new AnimationBuilder().playOnce("open").addAnimation("idle_open", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder CLOSE = new AnimationBuilder().playOnce("close").addAnimation("idle_closed", ILoopType.EDefaultLoopTypes.LOOP);
    private int progress = 0;
    public boolean shouldOpen;
    public boolean shouldClose;

    private <E extends TileEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (shouldOpen) {
            event.getController().setAnimation(OPEN);
            return PlayState.CONTINUE;
        }
        if (shouldClose) {
            event.getController().setAnimation(CLOSE);
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.manager;
    }

    @Override
    public void open() {
        IBlockState controller = world.getBlockState(pos);
        EnumFacing facing = controller.getValue(BlockDoorController.FACING);

        for (int i = 2; i >= 0; i--) {
            for (int j = 0; j <= 2; j++) {
                BlockPos xz = pos.offset(facing, -j);
                BlockPos y = pos.offset(EnumFacing.UP, i);
                if (!world.getBlockState(pos).getBlock().equals(BlockRegistry.BREAKABLE_DOOR_SMALL_CONTROLLER)) continue;
                boolean eastWest = facing == EnumFacing.EAST || facing == EnumFacing.WEST;
                BlockPos blockPos = new BlockPos(eastWest ? xz.getX() : pos.getX(), y.getY(), eastWest ? pos.getZ() : xz.getZ());
                if (world.getBlockState(blockPos).getBlock().equals(BlockRegistry.BREAKABLE_DOOR_SMALL_CONTROLLER)) continue;
                TileEntityDoorHelper.Location location;
                world.setBlockState(blockPos, world.getBlockState(blockPos).withProperty(BlockBreakableDoorSmallHelper.OPEN, true).withProperty(BlockBreakableDoorSmallHelper.POWERED, true));
                ((TileEntityDoorHelper) world.getTileEntity(blockPos)).master = this.pos;
                if (j == 2 && i == 2) {
                    location = TileEntityDoorHelper.Location.CORNERR;
                } else if (j == 2) {
                    location = TileEntityDoorHelper.Location.RIGHT;
                } else if (j == 0 && i == 2) {
                    location = TileEntityDoorHelper.Location.CORNERL;
                } else if (j == 0) {
                    location = TileEntityDoorHelper.Location.LEFT;
                } else if (i == 2) {
                    location = TileEntityDoorHelper.Location.TOP;
                } else {
                    location = TileEntityDoorHelper.Location.CENTER;
                }
                ((TileEntityDoorHelper) world.getTileEntity(new BlockPos(eastWest ? xz.getX() : pos.getX(), y.getY(), eastWest ? pos.getZ() : xz.getZ()))).setLocation(location);
            }
        }
        world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockDoorController.OPEN, true));
    }

    public void close() {
        IBlockState controller = world.getBlockState(pos);
        EnumFacing facing = controller.getValue(BlockDoorController.FACING);

        for (int i = 2; i >= 0; i--) {
            for (int j = 0; j <= 2; j++) {
                BlockPos xz = pos.offset(facing, -j);
                BlockPos y = pos.offset(EnumFacing.UP, i);
                if (!world.getBlockState(pos).getBlock().equals(BlockRegistry.BREAKABLE_DOOR_SMALL_CONTROLLER)) continue;
                boolean eastWest = facing == EnumFacing.EAST || facing == EnumFacing.WEST;
                BlockPos blockPos = new BlockPos(eastWest ? xz.getX() : pos.getX(), y.getY(), eastWest ? pos.getZ() : xz.getZ());
                if (world.getBlockState(blockPos).getBlock().equals(BlockRegistry.BREAKABLE_DOOR_SMALL_CONTROLLER)) continue;
                TileEntityDoorHelper.Location location;
                world.setBlockState(blockPos, world.getBlockState(blockPos).withProperty(BlockBreakableDoorSmallHelper.OPEN, false));
                ((TileEntityDoorHelper) world.getTileEntity(blockPos)).master = this.pos;
                if (j == 2 && i == 2) {
                    location = TileEntityDoorHelper.Location.CORNERR;
                } else if (j == 2) {
                    location = TileEntityDoorHelper.Location.RIGHT;
                } else if (j == 0 && i == 2) {
                    location = TileEntityDoorHelper.Location.CORNERL;
                } else if (j == 0) {
                    location = TileEntityDoorHelper.Location.LEFT;
                } else if (i == 2) {
                    location = TileEntityDoorHelper.Location.TOP;
                } else {
                    location = TileEntityDoorHelper.Location.CENTER;
                }
                ((TileEntityDoorHelper) world.getTileEntity(new BlockPos(eastWest ? xz.getX() : pos.getX(), y.getY(), eastWest ? pos.getZ() : xz.getZ()))).setLocation(location);
            }
        }
        world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockDoorController.OPEN, false));
    }

    @Override
    public void update() {
        if (shouldOpen) {
            progress++;
            if (progress > 80) {
                open();
            }
        } else if (shouldClose) {
            progress++;
            if (progress > 80) {
                close();
            }
        }
    }
}
