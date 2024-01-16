package io.github.itskillerluc.gtfo_craft.block;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.GtfoCraftCreativeTab;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockTerminal extends BlockHorizontal {
    public static final PropertyBool POWERED = PropertyBool.create("powered");

    public BlockTerminal(Material materialIn) {
        super(materialIn);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, Boolean.valueOf(false)));
        setCreativeTab(GtfoCraftCreativeTab.INSTANCE);
        setRegistryName(new ResourceLocation(GtfoCraft.MODID, "terminal"));
        setUnlocalizedName("terminal");
    }

    protected static final AxisAlignedBB SIGN_WEST_AABB = new AxisAlignedBB(0.0D, 0, 0.125, 0.125D, 1.5, 1.0D - 0.125);
    protected static final AxisAlignedBB SIGN_EAST_AABB = new AxisAlignedBB(0.78125D, 0, 0.125, 1.0D, 1.5, 1.0D - .125);
    protected static final AxisAlignedBB SIGN_NORTH_AABB = new AxisAlignedBB(0.125, 0, 0.0D, 1.0D - .125, 1.5, 0.125D + 0.0625);
    protected static final AxisAlignedBB SIGN_SOUTH_AABB = new AxisAlignedBB(0.125, 0, 0.8125, 1.0D - .125, 1.5, 1.0D);

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return getAxisAlignedBB(state);
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return getAxisAlignedBB(blockState);
    }

    private AxisAlignedBB getAxisAlignedBB(IBlockState blockState) {
        switch (blockState.getValue(FACING))
        {
            case NORTH:
            default:
                return SIGN_NORTH_AABB;
            case SOUTH:
                return SIGN_SOUTH_AABB;
            case WEST:
                return SIGN_WEST_AABB;
            case EAST:
                return SIGN_EAST_AABB;
        }
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, POWERED);
    }

    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side) {
        return state.getValue(FACING).getOpposite() == side;
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    protected void updateNeighborsInFront(World worldIn, BlockPos pos, IBlockState state)
    {
        worldIn.notifyNeighborsOfStateChange(pos, this, false);
        EnumFacing enumfacing = state.getValue(FACING);
        worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this, false);
    }

    public boolean canProvidePower(IBlockState state)
    {
        return true;
    }

    public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return blockState.getValue(POWERED).booleanValue() && blockState.getValue(FACING).getOpposite() == side ? 15 : 0;
    }

    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return blockState.getValue(POWERED).booleanValue() && blockState.getValue(FACING).getOpposite() == side ? 15 : 0;
    }

    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        worldIn.notifyNeighborsOfStateChange(pos, this, false);
        EnumFacing enumfacing = state.getValue(FACING);
        worldIn.neighborChanged(pos.offset(enumfacing), this, pos);
        worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this, false);
        super.breakBlock(worldIn, pos, state);
    }

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        EnumFacing enumfacing = placer.getHorizontalFacing();
        return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(FACING, enumfacing);
    }

    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | state.getValue(FACING).getIndex();

        if (state.getValue(POWERED).booleanValue())
        {
            i |= 6;
        }

        return i;
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 3)).withProperty(POWERED, (meta & 0b100) == 0b100 );
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }


    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        boolean result = worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(POWERED, true));
        worldIn.notifyNeighborsOfStateChange(pos, this, false);
        EnumFacing enumfacing = state.getValue(FACING);
        worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this, false);
        return result;
    }
}
