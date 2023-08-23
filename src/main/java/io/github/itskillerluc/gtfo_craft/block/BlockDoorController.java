package io.github.itskillerluc.gtfo_craft.block;

import io.github.itskillerluc.gtfo_craft.network.DoorPacket;
import io.github.itskillerluc.gtfo_craft.network.PacketHandler;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityDoor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockDoorController extends Block {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyBool POWERED = PropertyBool.create("powered");
    public static final PropertyBool OPEN = PropertyBool.create("open");
    public BlockDoorController(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
    }

    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer)
                .withProperty(FACING, placer.getHorizontalFacing().rotateYCCW())
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

    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.getBlock() != this ? state : state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, POWERED, OPEN);
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        if (state.getValue(OPEN)) {
            EnumFacing enumfacing = state.getValue(BlockBreakableDoorLargeHelper.FACING);
            boolean isEastWest = enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.EAST;
            boolean isInversed = enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.SOUTH;
            if (!isInversed) {
                return isEastWest ? BlockBreakableDoorLargeHelper.EAST_RIGHT : BlockBreakableDoorLargeHelper.NORTH_LEFT;
            } else {
                return isEastWest ? BlockBreakableDoorLargeHelper.EAST_LEFT : BlockBreakableDoorLargeHelper.NORTH_RIGHT;
            }
        }
        EnumFacing enumfacing = state.getValue(FACING);
        switch (enumfacing) {
            case WEST:
            case EAST:
                return BlockDoorHelper.EAST_CENTER;
            default:
                return BlockDoorHelper.NORTH_CENTER;
        }
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return true;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        BlockPos blockpos1 = pos.up();
        IBlockState iblockstate1 = worldIn.getBlockState(blockpos1);
        boolean flag = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(blockpos1);

        if (blockIn != this && (flag || blockIn.getDefaultState().canProvidePower()) && flag != iblockstate1.getValue(POWERED)) {


            if (!state.getValue(OPEN)) {
                if (worldIn.getTileEntity(pos) instanceof TileEntityDoor && worldIn.getTileEntity(pos) != null) {
                    ((TileEntityDoor) worldIn.getTileEntity(pos)).open();
                    for (EntityPlayerMP player : worldIn.getMinecraftServer().getPlayerList().getPlayers()) {
                        PacketHandler.sendTo(player, new DoorPacket(pos));
                    }
                    worldIn.markBlockRangeForRenderUpdate(pos, pos);
                }
            }
        }
    }
}
