package io.github.itskillerluc.gtfo_craft.block;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.GtfoCraftCreativeTab;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityBulkheadDoorSmallHelper;
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
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class BlockBulkheadDoorSmallHelper extends Block implements ITileEntityProvider {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyBool POWERED = PropertyBool.create("powered");
    public static final PropertyBool OPEN = PropertyBool.create("open");
    protected static final AxisAlignedBB NORTH = new AxisAlignedBB(0.3125D, 0.0D, 0D, 0.68750D, 1.0D, 1D);
    protected static final AxisAlignedBB EAST = new AxisAlignedBB(0D, 0.0D, 0.3125D, 1, 1.0D, 0.68750D);
    public BlockBulkheadDoorSmallHelper(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
        setCreativeTab(GtfoCraftCreativeTab.INSTANCE);
        setRegistryName(new ResourceLocation(GtfoCraft.MODID, "bulkhead_door_small_helper"));
        setUnlocalizedName("bulkhead_door_small_helper");
        setDefaultState(super.getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, false).withProperty(OPEN, false));
    }


    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer)
                .withProperty(FACING, placer.getHorizontalFacing())
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
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (worldIn.getTileEntity(pos) instanceof TileEntityBulkheadDoorSmallHelper) {
            BlockBulkheadDoorSmallController.breakDoor(worldIn, ((TileEntityBulkheadDoorSmallHelper) worldIn.getTileEntity(pos)).master, state.getValue(FACING));
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
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityBulkheadDoorSmallHelper();
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
}
