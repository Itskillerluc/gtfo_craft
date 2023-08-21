package io.github.itskillerluc.gtfo_craft.tileentity;

import io.github.itskillerluc.gtfo_craft.block.BlockBreakableDoorHelper;
import io.github.itskillerluc.gtfo_craft.block.BlockBulkheadDoorSmallController;
import io.github.itskillerluc.gtfo_craft.block.BlockBulkheadDoorSmallHelper;
import io.github.itskillerluc.gtfo_craft.registry.BlockRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class TileEntityBreakableDoor extends TileEntity implements IAnimatable {
    private final AnimationFactory manager = new AnimationFactory(this);

    private <E extends TileEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        return PlayState.CONTINUE;
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
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

    public void open() {
        IBlockState controller = world.getBlockState(pos);
        EnumFacing facing = controller.getValue(BlockBulkheadDoorSmallController.FACING);

        for (int i = 2; i >= 0; i--) {
            for (int j = 0; j <= 2; j++) {
                BlockPos xz = pos.offset(facing, -j);
                BlockPos y = pos.offset(EnumFacing.UP, i);
                if (!world.getBlockState(pos).getBlock().equals(BlockRegistry.BREAKABLE_DOOR_CONTROLLER)) continue;
                boolean eastWest = facing == EnumFacing.EAST || facing == EnumFacing.WEST;
                if (world.getBlockState(new BlockPos(eastWest ? xz.getX() : pos.getX(), y.getY(), eastWest ? pos.getZ() : xz.getZ())).getBlock().equals(BlockRegistry.BREAKABLE_DOOR_CONTROLLER)) continue;
                world.getBlockState(new BlockPos(eastWest ? xz.getX() : pos.getX(), y.getY(), eastWest ? pos.getZ() : xz.getZ())).withProperty(BlockBreakableDoorHelper.OPEN, true);
            }
        }
    }
}
