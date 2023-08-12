package io.github.itskillerluc.gtfo_craft.block;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.GtfoCraftCreativeTab;
import io.github.itskillerluc.gtfo_craft.registry.BlockRegistry;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityBulkheadDoorSmall;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityBulkheadDoorSmallHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockBulkheadDoorSmallController extends Block implements ITileEntityProvider {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyBool POWERED = PropertyBool.create("powered");
    public static final PropertyBool OPEN = PropertyBool.create("open");

    protected static final AxisAlignedBB NORTH = new AxisAlignedBB(0.3125D, 0.0D, 0D, 0.68750D, 1.0D, 1D);
    protected static final AxisAlignedBB EAST = new AxisAlignedBB(0D, 0.0D, 0.3125D, 1, 1.0D, 0.68750D);
    public BlockBulkheadDoorSmallController(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
        setCreativeTab(GtfoCraftCreativeTab.INSTANCE);
        setRegistryName(new ResourceLocation(GtfoCraft.MODID, "bulkhead_door_small_controller"));
        setUnlocalizedName("bulkhead_door_small");
        setDefaultState(super.getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, false).withProperty(OPEN, false));
    }

    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.INVISIBLE;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityBulkheadDoorSmall();
    }
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer)
                .withProperty(FACING, placer.getHorizontalFacing().rotateYCCW())
                .withProperty(POWERED, false)
                .withProperty(OPEN, false);
    }
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState()
                .withProperty(FACING, EnumFacing.getHorizontal(meta & 0b0011))
                .withProperty(POWERED, (meta & 0b0100) != 0)
                .withProperty(OPEN, (meta & 0b1000) != 0);
    }
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex() | (state.getValue(POWERED) ? 0b100 : 0) | (state.getValue(OPEN) ? 0b1000 : 0);
    }
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.getBlock() != this ? state : state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, POWERED, OPEN);
    }
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        if (state.getValue(OPEN)) {
            return NULL_AABB;
        }
        EnumFacing enumfacing = state.getValue(FACING);
        switch (enumfacing) {
            case WEST:
            case EAST:
                return EAST;
            default:
                return NORTH;
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        for (int i = 3; i >= 0; i--) {
            for (int j = 0; j <= 3; j++) {
                BlockPos xz = pos.offset(placer.getHorizontalFacing().rotateY(), j);
                BlockPos y = pos.offset(EnumFacing.UP, i);
                boolean eastWest = state.getValue(FACING) == EnumFacing.EAST || state.getValue(FACING) == EnumFacing.WEST;
                if (!worldIn.isAirBlock(new BlockPos(eastWest ? xz.getX() : pos.getX(), y.getY(), eastWest ? pos.getZ() : xz.getZ()))) continue;
                worldIn.setBlockState(new BlockPos(eastWest ? xz.getX() : pos.getX(), y.getY(), eastWest ? pos.getZ() : xz.getZ()), BlockRegistry.BULKHEAD_DOOR_SMALL_HELPER.getDefaultState()
                        .withProperty(BlockBulkheadDoorSmallHelper.FACING, placer.getHorizontalFacing().rotateY()), 3);
                if ( worldIn.getTileEntity(new BlockPos(eastWest ? xz.getX() : pos.getX(), y.getY(), eastWest ? pos.getZ() : xz.getZ())) instanceof TileEntityBulkheadDoorSmallHelper) {
                    ((TileEntityBulkheadDoorSmallHelper) worldIn.getTileEntity(new BlockPos(eastWest ? xz.getX() : pos.getX(), y.getY(), eastWest ? pos.getZ() : xz.getZ()))).master = pos;
                }
            }
        }
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    public static void breakDoor(World world, BlockPos pos, EnumFacing facing) {
        for (int i = 3; i >= 0; i--) {
            for (int j = 0; j <= 3; j++) {
                BlockPos xz = pos.offset(facing, j);
                BlockPos y = pos.offset(EnumFacing.UP, i);
                if (!world.getBlockState(pos).getBlock().equals(BlockRegistry.BULKHEAD_DOOR_SMALL_CONTROLLER)) continue;
                boolean eastWest = world.getBlockState(pos).getValue(FACING) == EnumFacing.EAST || world.getBlockState(pos).getValue(FACING) == EnumFacing.WEST;
                if (world.getBlockState(new BlockPos(eastWest ? xz.getX() : pos.getX(), y.getY(), eastWest ? pos.getZ() : xz.getZ())).getBlock().equals(BlockRegistry.BULKHEAD_DOOR_SMALL_CONTROLLER)) continue;
                world.setBlockToAir(new BlockPos(eastWest ? xz.getX() : pos.getX(), y.getY(), eastWest ? pos.getZ() : xz.getZ()));
            }
        }
        world.setBlockToAir(pos);
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return false;
    }
}
