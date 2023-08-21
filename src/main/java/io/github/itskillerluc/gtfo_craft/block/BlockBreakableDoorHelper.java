package io.github.itskillerluc.gtfo_craft.block;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.GtfoCraftCreativeTab;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityBreakableDoor;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityBulkheadDoorHelper;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityBulkheadDoorSmall;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockBreakableDoorHelper extends Block implements ITileEntityProvider {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyBool POWERED = PropertyBool.create("powered");
    public static final PropertyBool OPEN = PropertyBool.create("open");
    protected static final AxisAlignedBB NORTH_CENTER = new AxisAlignedBB(0.375, 0.0D, 0D, 0.625, 1.0D, 1D);
    protected static final AxisAlignedBB EAST_CENTER = new AxisAlignedBB(0D, 0.0D, 0.375, 1, 1.0D, 0.625);
    protected static final AxisAlignedBB NORTH_CORNERL = new AxisAlignedBB(0.375, 0.0D, 0D, 0.625, 1.0D, 1D);
    protected static final AxisAlignedBB EAST_CORNERL = new AxisAlignedBB(0D, 0.0D, 0.375, 1, 1.0D, 0.625);
    protected static final AxisAlignedBB NORTH_CORNERR = new AxisAlignedBB(0.375, 0.0D, 0D, 0.625, 1.0D, 1D);
    protected static final AxisAlignedBB EAST_CORNERR = new AxisAlignedBB(0D, 0.0D, 0.375, 1, 1.0D, 0.625);
    protected static final AxisAlignedBB NORTH_TOP = new AxisAlignedBB(0.375, 0.0D, 0D, 0.625, 1.0D, 1D);
    protected static final AxisAlignedBB EAST_TOP = new AxisAlignedBB(0D, 0.0D, 0.375, 1, 1.0D, 0.625);
    protected static final AxisAlignedBB NORTH_LEFT = new AxisAlignedBB(0.375, 0.0D, 0D, 0.625, 1.0D, 1D);
    protected static final AxisAlignedBB EAST_LEFT = new AxisAlignedBB(0D, 0.0D, 0.375, 1, 1.0D, 0.625);
    protected static final AxisAlignedBB NORTH_RIGHT = new AxisAlignedBB(0.375, 0.0D, 0D, 0.625, 1.0D, 1D);
    protected static final AxisAlignedBB EAST_RIGHT = new AxisAlignedBB(0D, 0.0D, 0.375, 1, 1.0D, 0.625);
    public BlockBreakableDoorHelper(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
        setCreativeTab(GtfoCraftCreativeTab.INSTANCE);
        setRegistryName(new ResourceLocation(GtfoCraft.MODID, "breakable_door_helper"));
        setUnlocalizedName("breakable_door_helper");
        setDefaultState(super.getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, false).withProperty(OPEN, false));
    }


    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
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
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex() | (state.getValue(POWERED) ? 0b100 : 0) | (state.getValue(OPEN) ? 0b1000 : 0);
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, POWERED, OPEN);
    }
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        if (state.getValue(OPEN) || source.getTileEntity(pos) == null) {
            return new AxisAlignedBB(0, 0, 0, 0, 0, 0);
        }
        EnumFacing enumfacing = state.getValue(FACING);
        boolean isEastWest = enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.EAST;
        TileEntityBulkheadDoorHelper tile = ((TileEntityBulkheadDoorHelper) source.getTileEntity(pos));
        if (tile == null || !state.getValue(OPEN)) {
            return isEastWest ? EAST_CENTER : NORTH_CENTER;
        }
        switch (tile.getLocation()) {
            case CORNERL:
                return isEastWest ? EAST_CORNERL : NORTH_CORNERL;
            case CORNERR:
                return isEastWest ? EAST_CORNERR : NORTH_CORNERR;
            case CENTER:
                return isEastWest ? EAST_CENTER : NORTH_CENTER;
            case TOP:
                return isEastWest ? EAST_TOP : NORTH_TOP;
            case LEFT:
                return isEastWest ? EAST_LEFT : NORTH_LEFT;
            case RIGHT:
                return isEastWest ? EAST_RIGHT : NORTH_RIGHT;
            default:
                throw new IllegalStateException("Unexpected value: " + ((TileEntityBulkheadDoorHelper) source.getTileEntity(pos)).getLocation());
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (worldIn.getTileEntity(pos) instanceof TileEntityBulkheadDoorHelper) {
            BlockBreakableDoorController.breakDoor(worldIn, ((TileEntityBulkheadDoorHelper) worldIn.getTileEntity(pos)).master, state.getValue(FACING).getOpposite());
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.INVISIBLE;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityBulkheadDoorHelper();
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return false;
    }
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        BlockPos blockpos1 = pos.up();
        IBlockState iblockstate1 = worldIn.getBlockState(blockpos1);
        boolean flag = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(blockpos1);

        if (blockIn != this && (flag || blockIn.getDefaultState().canProvidePower()) && flag != iblockstate1.getValue(POWERED))
        {
            worldIn.setBlockState(blockpos1, iblockstate1.withProperty(POWERED, flag), 2);

            if (flag != state.getValue(OPEN))
            {
                if (worldIn.getTileEntity(pos) instanceof TileEntityBulkheadDoorHelper && worldIn.getTileEntity(pos) != null) {
                    ((TileEntityBreakableDoor) worldIn.getTileEntity(((TileEntityBulkheadDoorHelper) worldIn.getTileEntity(pos)).master)).open();
                    worldIn.markBlockRangeForRenderUpdate(pos, pos);
                }
            }
        }
    }
}
