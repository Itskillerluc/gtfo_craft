package io.github.itskillerluc.gtfo_craft.block;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.GtfoCraftCreativeTab;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityDoorHelper;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockApexDoorLargeHelper extends BlockDoorHelper implements ITileEntityProvider {
    protected static final AxisAlignedBB NORTH_CENTER = new AxisAlignedBB(0.25, 0.0D, 0D, 0.75, 1.0D, 1D);
    protected static final AxisAlignedBB EAST_CENTER = new AxisAlignedBB(0D, 0.0D, 0.25, 1, 1.0D, 0.75);
    protected static final AxisAlignedBB NORTH_CORNERL = new AxisAlignedBB(0.125, 0.0D, 0D, 0.875, 1.0D, 1D);
    protected static final AxisAlignedBB EAST_CORNERL = new AxisAlignedBB(0D, 0.0D, 0.125, 1, 1.0D, 0.875);
    protected static final AxisAlignedBB NORTH_CORNERR = new AxisAlignedBB(0.125, 0.0D, 0D, 0.875, 1.0D, 1D);
    protected static final AxisAlignedBB EAST_CORNERR = new AxisAlignedBB(0D, 0.0D, 0.125, 1, 1.0D, 0.875);
    protected static final AxisAlignedBB NORTH_TOP = new AxisAlignedBB(0, 0.75 - 0.0625, 0D, 1, 1, 1D);
    protected static final AxisAlignedBB EAST_TOP = new AxisAlignedBB(0D, 0.75 - 0.0625, 0.125, 1, 1, 0.875);
    protected static final AxisAlignedBB NORTH_LEFT = new AxisAlignedBB(0, 0.0D, 0D, 1, 1.0D, 0.25D + 0.0625);
    protected static final AxisAlignedBB EAST_LEFT = new AxisAlignedBB(0, 0.0D, 0, 0.25 + 0.0625, 1.0D, 1);
    protected static final AxisAlignedBB NORTH_RIGHT = new AxisAlignedBB(0, 0.0D, 0.75D - 0.0625, 1, 1.0D, 1);
    protected static final AxisAlignedBB EAST_RIGHT = new AxisAlignedBB(0.75 - 0.0625, 0.0D, 0, 1, 1.0, 1);

    public BlockApexDoorLargeHelper(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
        setCreativeTab(GtfoCraftCreativeTab.INSTANCE);
        setRegistryName(new ResourceLocation(GtfoCraft.MODID, "apex_door_large_helper"));
        setUnlocalizedName("apex_door_large_helper");
        setDefaultState(super.getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, false).withProperty(OPEN, false));
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (worldIn.getTileEntity(pos) instanceof TileEntityDoorHelper) {
            BlockApexDoorLargeController.breakDoor(worldIn, ((TileEntityDoorHelper) worldIn.getTileEntity(pos)).master, state.getValue(FACING).getOpposite());
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityDoorHelper();
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        if (source.getTileEntity(pos) == null) {
            return BlockDoorHelper.EAST_CENTER;
        }
        EnumFacing enumfacing = state.getValue(BlockDoorHelper.FACING);
        boolean isEastWest = enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.EAST;
        boolean isInversed = enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.SOUTH;
        TileEntityDoorHelper tile = ((TileEntityDoorHelper) source.getTileEntity(pos));
        if (tile == null || !state.getValue(BlockDoorHelper.OPEN)) {
            return isEastWest ? EAST_CENTER : NORTH_CENTER;
        }
        switch (tile.getLocation()) {
            case CORNERL:
                if (!isInversed) {
                    return isEastWest ? EAST_CORNERR : NORTH_CORNERR;
                } else {
                    return isEastWest ? EAST_CORNERL : NORTH_CORNERL;
                }
            case CORNERR:
                if (!isInversed) {
                    return isEastWest ? EAST_CORNERL : NORTH_CORNERL;
                } else {
                    return isEastWest ? EAST_CORNERR : NORTH_CORNERR;
                }
            case CENTER:
                return isEastWest ? EAST_CENTER : NORTH_CENTER;
            case TOP:
                return isEastWest ? EAST_TOP : NORTH_TOP;
            case LEFT:
                if (!isInversed) {
                    return isEastWest ? EAST_LEFT : NORTH_RIGHT;
                } else {
                    return isEastWest ? EAST_RIGHT : NORTH_LEFT;
                }
            case RIGHT:
                if (!isInversed) {
                    return isEastWest ? EAST_RIGHT : NORTH_LEFT;
                } else {
                    return isEastWest ? EAST_LEFT : NORTH_RIGHT;
                }
            default:
                throw new IllegalStateException("Unexpected value: " + ((TileEntityDoorHelper) source.getTileEntity(pos)).getLocation());
        }
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        if (worldIn.getTileEntity(pos) == null) {
            super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn, isActualState);
        }
        EnumFacing enumfacing = state.getValue(BlockDoorHelper.FACING);
        boolean isEastWest = enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.EAST;
        boolean isInversed = enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.SOUTH;
        TileEntityDoorHelper tile = ((TileEntityDoorHelper) worldIn.getTileEntity(pos));
        if (tile == null || !state.getValue(BlockDoorHelper.OPEN)) {
            super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn, isActualState);
        }
        if (tile.getLocation() == TileEntityDoorHelper.Location.CORNERL) {
            if (isEastWest) {
                if (entityBox.intersects(EAST_TOP.offset(pos))) {
                    collidingBoxes.add(EAST_TOP.offset(pos));
                }
            } else if (entityBox.intersects(NORTH_TOP.offset(pos))) {
                collidingBoxes.add(NORTH_TOP.offset(pos));
            }
        } else if (tile.getLocation() == TileEntityDoorHelper.Location.CORNERR) {
            if (isEastWest) {
                if (entityBox.intersects(EAST_TOP.offset(pos))) {
                    collidingBoxes.add(EAST_TOP.offset(pos));
                }
            } else if (entityBox.intersects(NORTH_TOP.offset(pos))) {
                collidingBoxes.add(NORTH_TOP.offset(pos));
            }
        } else {
            super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn, isActualState);
        }
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        if (worldIn.getTileEntity(pos) == null) {
            return NULL_AABB;
        }
        EnumFacing enumfacing = blockState.getValue(BlockDoorHelper.FACING);
        boolean isEastWest = enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.EAST;
        boolean isInversed = enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.SOUTH;
        TileEntityDoorHelper tile = ((TileEntityDoorHelper) worldIn.getTileEntity(pos));
        if (tile == null || !blockState.getValue(BlockDoorHelper.OPEN)) {
            return isEastWest ? EAST_CENTER : NORTH_CENTER;
        }
        switch (tile.getLocation()) {
            case CORNERL:
                if (!isInversed) {
                    return isEastWest ? EAST_CORNERL : NORTH_CORNERR;
                } else {
                    return isEastWest ? EAST_CORNERR : NORTH_CORNERL;
                }
            case CORNERR:
                if (!isInversed) {
                    return isEastWest ? EAST_CORNERR : NORTH_CORNERL;
                } else {
                    return isEastWest ? EAST_CORNERL : NORTH_CORNERR;
                }
            case CENTER:
                return NULL_AABB;
            case TOP:
                return isEastWest ? EAST_TOP : NORTH_TOP;
            case LEFT:
                if (!isInversed) {
                    return isEastWest ? EAST_LEFT : NORTH_RIGHT;
                } else {
                    return isEastWest ? EAST_RIGHT : NORTH_LEFT;
                }
            case RIGHT:
                if (!isInversed) {
                    return isEastWest ? EAST_RIGHT : NORTH_LEFT;
                } else {
                    return isEastWest ? EAST_LEFT : NORTH_RIGHT;
                }
            default:
                throw new IllegalStateException("Unexpected value: " + ((TileEntityDoorHelper) worldIn.getTileEntity(pos)).getLocation());
        }
    }
}
