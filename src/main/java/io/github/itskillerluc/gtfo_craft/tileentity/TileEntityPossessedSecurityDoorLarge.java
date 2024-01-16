package io.github.itskillerluc.gtfo_craft.tileentity;

import io.github.itskillerluc.gtfo_craft.block.BlockDoorController;
import io.github.itskillerluc.gtfo_craft.block.BlockPossessedSecurityDoorLargeHelper;
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

public class TileEntityPossessedSecurityDoorLarge extends TileEntityDoor implements IAnimatable, ITickable {
    private final AnimationFactory manager = new AnimationFactory(this);
    private static final AnimationBuilder OPEN = new AnimationBuilder().playOnce("open_hinges").playOnce("idle_unhinged").playOnce("open").addAnimation("idle_open", ILoopType.EDefaultLoopTypes.LOOP);
    private int progress = 0;
    public boolean shouldOpen;
    private <E extends TileEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (shouldOpen) {
            event.getController().setAnimation(OPEN);
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
        return manager;
    }

    @Override
    public void open() {
        shouldOpen = true;
    }

    public void openInternal() {
        IBlockState controller = world.getBlockState(pos);
        EnumFacing facing = controller.getValue(BlockDoorController.FACING);

        for (int i = 3; i >= 0; i--) {
            for (int j = 0; j <= 6; j++) {
                BlockPos xz = pos.offset(facing, -j);
                BlockPos y = pos.offset(EnumFacing.UP, i);
                if (!world.getBlockState(pos).getBlock().equals(BlockRegistry.POSSESSED_SECURITY_DOOR_LARGE_CONTROLLER)) continue;
                boolean eastWest = facing == EnumFacing.EAST || facing == EnumFacing.WEST;
                BlockPos blockPos = new BlockPos(eastWest ? xz.getX() : pos.getX(), y.getY(), eastWest ? pos.getZ() : xz.getZ());
                if (world.getBlockState(blockPos).getBlock().equals(BlockRegistry.POSSESSED_SECURITY_DOOR_LARGE_CONTROLLER)) continue;
                TileEntityDoorHelper.Location location;
                world.setBlockState(blockPos, world.getBlockState(blockPos).withProperty(BlockPossessedSecurityDoorLargeHelper.OPEN, true).withProperty(BlockPossessedSecurityDoorLargeHelper.POWERED, true));
                ((TileEntityDoorHelper) world.getTileEntity(blockPos)).master = this.pos;
                if (j == 6 && i == 3) {
                    location = TileEntityDoorHelper.Location.CORNERR;
                } else if (j == 6) {
                    location = TileEntityDoorHelper.Location.RIGHT;
                } else if (j == 0 && i == 3) {
                    location = TileEntityDoorHelper.Location.CORNERL;
                } else if (j == 0) {
                    location = TileEntityDoorHelper.Location.LEFT;
                } else if (i == 3) {
                    location = TileEntityDoorHelper.Location.TOP;
                } else {
                    location = TileEntityDoorHelper.Location.CENTER;
                }
                ((TileEntityDoorHelper) world.getTileEntity(new BlockPos(eastWest ? xz.getX() : pos.getX(), y.getY(), eastWest ? pos.getZ() : xz.getZ()))).setLocation(location);
            }
        }
        world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockDoorController.OPEN, true));
    }

    @Override
    public void update() {
        if (shouldOpen) {
            progress++;
            if (progress > 100) {
                openInternal();
            }
        }
    }
}
