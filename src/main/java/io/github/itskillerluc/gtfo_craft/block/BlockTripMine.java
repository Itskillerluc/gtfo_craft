package io.github.itskillerluc.gtfo_craft.block;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.GtfoCraftCreativeTab;
import io.github.itskillerluc.gtfo_craft.registry.ItemRegistry;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityTripMine;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityTurret;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockTripMine extends Block implements ITileEntityProvider {
    public static final PropertyDirection FACING = BlockDirectional.FACING;

    protected static final AxisAlignedBB NORTH = new AxisAlignedBB(0.1875D, 0.1875D, 0.5D, 0.8125D, 0.8125D, 1);
    protected static final AxisAlignedBB EAST = new AxisAlignedBB(0, 0.1875D, 0.1875D , 0.5, 0.8125D, 0.8125D);
    protected static final AxisAlignedBB SOUTH = new AxisAlignedBB(0.1875D, 0.1875D, 0, 0.8125D, 0.8125D, 0.5D);
    protected static final AxisAlignedBB WEST = new AxisAlignedBB(0.5, 0.1875D, 0.1875D, 1, 0.8125D, 0.8125D);
    protected static final AxisAlignedBB UP = new AxisAlignedBB(0.1875, 0.0D, 0.1875, 0.8125D, 0.5D, 0.8125D);
    protected static final AxisAlignedBB DOWN = new AxisAlignedBB(0.1875, 0.5D, 0.1875, 0.8125D, 1.0D, 0.8125);

    public BlockTripMine(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        setRegistryName(new ResourceLocation(GtfoCraft.MODID, "trip_mine"));
        setUnlocalizedName("trip_mine");
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
        return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(FACING, facing);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityTripMine(getStateFromMeta(meta).getValue(FACING));
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return true;
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
    }

    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | state.getValue(FACING).getIndex();
        return i;
    }

    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.getBlock() != this ? state : state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
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
