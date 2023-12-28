package io.github.itskillerluc.gtfo_craft.block;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.GtfoCraftCreativeTab;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntitySpitter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockSpitter extends Block implements ITileEntityProvider {
    public static final PropertyDirection FACING = BlockDirectional.FACING;
    public static final PropertyBool CHARGED = PropertyBool.create("charged");

    protected static final AxisAlignedBB NORTH = new AxisAlignedBB(0.25, 0.25, 0.375, 0.75, 0.75, 1);
    protected static final AxisAlignedBB EAST = new AxisAlignedBB(0, 0.25, 0.25 , 0.625, 0.75, 0.75);
    protected static final AxisAlignedBB SOUTH = new AxisAlignedBB(0.25, 0.25, 0, 0.75, 0.75, 0.625);
    protected static final AxisAlignedBB WEST = new AxisAlignedBB(0.375, 0.25, 0.25, 1, 0.75, 0.75);
    protected static final AxisAlignedBB UP = new AxisAlignedBB(0.25, 0.0D, 0.25, 0.75, 0.625, 0.75);
    protected static final AxisAlignedBB DOWN = new AxisAlignedBB(0.25, 0.4375, 0.25, 0.75, 1.0D, 0.75);

    public BlockSpitter(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(CHARGED, false));
        setRegistryName(new ResourceLocation(GtfoCraft.MODID, "spitter"));
        setUnlocalizedName("spitter");
        setCreativeTab(GtfoCraftCreativeTab.INSTANCE);
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
        return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(FACING, facing).withProperty(CHARGED, false);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntitySpitter(600);
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return true;
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7)).withProperty(CHARGED, (meta & 8) == 8);
    }

    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i |= state.getValue(FACING).getIndex();
        i |= state.getValue(CHARGED) ? 0 : 8;
        return i;
    }

    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.getBlock() != this ? state : state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, CHARGED);
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        EnumFacing enumfacing = state.getValue(FACING);
        switch (enumfacing) {
            case SOUTH:
                return SOUTH;
            case WEST:
                return WEST;
            case EAST:
                return EAST;
            case UP:
                return UP;
            case DOWN:
                return DOWN;
            case NORTH:
                return NORTH;
        }
        return NULL_AABB;
    }
}
