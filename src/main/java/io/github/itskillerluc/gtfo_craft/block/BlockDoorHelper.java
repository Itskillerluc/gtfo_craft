package io.github.itskillerluc.gtfo_craft.block;

import io.github.itskillerluc.gtfo_craft.network.DoorPacket;
import io.github.itskillerluc.gtfo_craft.network.PacketHandler;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityDoor;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityDoorHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class BlockDoorHelper extends Block {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyBool POWERED = PropertyBool.create("powered");
    public static final PropertyBool OPEN = PropertyBool.create("open");
    protected static final AxisAlignedBB NORTH_CENTER = new AxisAlignedBB(0.375, 0.0D, 0D, 0.625, 1.0D, 1D);
    protected static final AxisAlignedBB EAST_CENTER = new AxisAlignedBB(0D, 0.0D, 0.375, 1, 1.0D, 0.625);
    protected static final AxisAlignedBB NORTH_CORNERL = new AxisAlignedBB(0.375, 0.0D, 0D, 0.625, 1.0D, 1D);
    protected static final AxisAlignedBB EAST_CORNERL = new AxisAlignedBB(0D, 0.0D, 0.375, 1, 1.0D, 0.625);
    protected static final AxisAlignedBB NORTH_CORNERR = new AxisAlignedBB(0.375, 0.0D, 0D, 0.625, 1.0D, 1D);
    protected static final AxisAlignedBB EAST_CORNERR = new AxisAlignedBB(0D, 0.0D, 0.375, 1, 1.0D, 0.625);
    protected static final AxisAlignedBB NORTH_TOP = new AxisAlignedBB(0.125, 0.4375, 0D, 0.875, 1, 1D);
    protected static final AxisAlignedBB EAST_TOP = new AxisAlignedBB(0D, 0.4375, 0.125, 1, 1, 0.875);
    protected static final AxisAlignedBB NORTH_LEFT = new AxisAlignedBB(0.125, 0.0D, 0D, 0.875, 1.0D, 0.25D);
    protected static final AxisAlignedBB EAST_LEFT = new AxisAlignedBB(0, 0.0D, 0.125D, 0.25, 1.0D, 0.875);
    protected static final AxisAlignedBB NORTH_RIGHT = new AxisAlignedBB(0.125, 0.0D, 0.75D, 0.875, 1.0D, 1);
    protected static final AxisAlignedBB EAST_RIGHT = new AxisAlignedBB(0.75, 0.0D, 0.125, 1, 1.0, 0.875);

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer)
                .withProperty(FACING, placer.getHorizontalFacing())
                .withProperty(POWERED, false)
                .withProperty(OPEN, false);
    }
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(FACING, EnumFacing.getHorizontal(meta & 0b0011))
                .withProperty(POWERED, (meta & 0b0100) != 0)
                .withProperty(OPEN, (meta & 0b1000) != 0);
    }

    public BlockDoorHelper(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
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
            return isEastWest ? BlockDoorHelper.EAST_CENTER : BlockDoorHelper.NORTH_CENTER;
        }
        switch (tile.getLocation()) {
            case CORNERL:
                if (!isInversed) {
                    return isEastWest ? BlockDoorHelper.EAST_CORNERR : BlockDoorHelper.NORTH_CORNERR;
                } else {
                    return isEastWest ? BlockDoorHelper.EAST_CORNERL : BlockDoorHelper.NORTH_CORNERL;
                }
            case CORNERR:
                if (!isInversed) {
                    return isEastWest ? BlockDoorHelper.EAST_CORNERL : BlockDoorHelper.NORTH_CORNERL;
                } else {
                    return isEastWest ? BlockDoorHelper.EAST_CORNERR : BlockDoorHelper.NORTH_CORNERR;
                }
            case CENTER:
                return isEastWest ? EAST_CENTER : NORTH_CENTER;
            case TOP:
                return isEastWest ? BlockDoorHelper.EAST_TOP : BlockDoorHelper.NORTH_TOP;
            case LEFT:
                if (!isInversed) {
                    return isEastWest ? BlockDoorHelper.EAST_LEFT : BlockDoorHelper.NORTH_RIGHT;
                } else {
                    return isEastWest ? BlockDoorHelper.EAST_RIGHT : BlockDoorHelper.NORTH_LEFT;
                }
            case RIGHT:
                if (!isInversed) {
                    return isEastWest ? BlockDoorHelper.EAST_RIGHT : BlockDoorHelper.NORTH_LEFT;
                } else {
                    return isEastWest ? BlockDoorHelper.EAST_LEFT : BlockDoorHelper.NORTH_RIGHT;
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
                if (entityBox.intersects(BlockDoorHelper.EAST_TOP.offset(pos))) {
                    collidingBoxes.add(BlockDoorHelper.EAST_TOP.offset(pos));
                }
            } else if (entityBox.intersects(BlockDoorHelper.NORTH_TOP.offset(pos))) {
                collidingBoxes.add(BlockDoorHelper.NORTH_TOP.offset(pos));
            }
        } else if (tile.getLocation() == TileEntityDoorHelper.Location.CORNERR) {
            if (isEastWest) {
                if (entityBox.intersects(BlockDoorHelper.EAST_TOP.offset(pos))) {
                    collidingBoxes.add(BlockDoorHelper.EAST_TOP.offset(pos));
                }
            } else if (entityBox.intersects(BlockDoorHelper.NORTH_TOP.offset(pos))) {
                collidingBoxes.add(BlockDoorHelper.NORTH_TOP.offset(pos));
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
            return isEastWest ? BlockDoorHelper.EAST_CENTER : BlockDoorHelper.NORTH_CENTER;
        }
        switch (tile.getLocation()) {
            case CORNERL:
                if (!isInversed) {
                    return isEastWest ? BlockDoorHelper.EAST_CORNERL : BlockDoorHelper.NORTH_CORNERR;
                } else {
                    return isEastWest ? BlockDoorHelper.EAST_CORNERR : BlockDoorHelper.NORTH_CORNERL;
                }
            case CORNERR:
                if (!isInversed) {
                    return isEastWest ? BlockDoorHelper.EAST_CORNERR : BlockDoorHelper.NORTH_CORNERL;
                } else {
                    return isEastWest ? BlockDoorHelper.EAST_CORNERL : BlockDoorHelper.NORTH_CORNERR;
                }
            case CENTER:
                return NULL_AABB;
            case TOP:
                return isEastWest ? BlockDoorHelper.EAST_TOP : BlockDoorHelper.NORTH_TOP;
            case LEFT:
                if (!isInversed) {
                    return isEastWest ? BlockDoorHelper.EAST_LEFT : BlockDoorHelper.NORTH_RIGHT;
                } else {
                    return isEastWest ? BlockDoorHelper.EAST_RIGHT : BlockDoorHelper.NORTH_LEFT;
                }
            case RIGHT:
                if (!isInversed) {
                    return isEastWest ? BlockDoorHelper.EAST_RIGHT : BlockDoorHelper.NORTH_LEFT;
                } else {
                    return isEastWest ? BlockDoorHelper.EAST_LEFT : BlockDoorHelper.NORTH_RIGHT;
                }
            default:
                throw new IllegalStateException("Unexpected value: " + ((TileEntityDoorHelper) worldIn.getTileEntity(pos)).getLocation());
        }
    }
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex() | (state.getValue(POWERED) ? 0b100 : 0) | (state.getValue(OPEN) ? 0b1000 : 0);
    }
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, POWERED, OPEN);
    }
    @Override
    public boolean isCollidable() {
        return true;
    }
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }



    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        BlockPos blockpos1 = pos.up();
        IBlockState iblockstate1 = worldIn.getBlockState(blockpos1);
        boolean flag = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(blockpos1);

        if (blockIn != this && (flag || blockIn.getDefaultState().canProvidePower()) && flag != iblockstate1.getValue(POWERED))
        {
            if (!state.getValue(OPEN))
            {
                if (worldIn.getTileEntity(pos) instanceof TileEntityDoorHelper && worldIn.getTileEntity(pos) != null) {
                    ((TileEntityDoor) worldIn.getTileEntity(((TileEntityDoorHelper) worldIn.getTileEntity(pos)).master)).open();
                    for (EntityPlayerMP player : worldIn.getMinecraftServer().getPlayerList().getPlayers()) {
                        PacketHandler.sendTo(player, new DoorPacket(pos));
                    }
                    worldIn.markBlockRangeForRenderUpdate(pos, pos);
                }
            }
        }
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

}
