package io.github.itskillerluc.gtfo_craft.block;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.GtfoCraftCreativeTab;
import io.github.itskillerluc.gtfo_craft.registry.BlockRegistry;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityBulkheadDoorLarge;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityDoorHelper;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityDoorHelper.Location;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockBulkheadDoorLargeController extends BlockDoorController implements ITileEntityProvider {

    public BlockBulkheadDoorLargeController(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
        setCreativeTab(GtfoCraftCreativeTab.INSTANCE);
        setRegistryName(new ResourceLocation(GtfoCraft.MODID, "bulkhead_door_large_controller"));
        setUnlocalizedName("bulkhead_door_large");
        setDefaultState(super.getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, false).withProperty(OPEN, false));
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityBulkheadDoorLarge();
    }


    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        for (int k = -1; k <= 1; k++) {
            for (int i = 3; i >= 0; i--) {
                for (int j = 0; j <= 6; j++) {
                    BlockPos xz = pos.offset(placer.getHorizontalFacing().rotateY(), j);
                    BlockPos zx = pos.offset(placer.getHorizontalFacing(), k);
                    BlockPos y = pos.offset(EnumFacing.UP, i);
                    boolean eastWest = state.getValue(FACING) == EnumFacing.EAST || state.getValue(FACING) == EnumFacing.WEST;
                    if (!worldIn.getBlockState(new BlockPos(eastWest ? xz.getX() : zx.getX(), y.getY(), eastWest ? zx.getZ() : xz.getZ())).getMaterial().isReplaceable())
                        continue;
                    worldIn.setBlockState(new BlockPos(eastWest ? xz.getX() : zx.getX(), y.getY(), eastWest ? zx.getZ() : xz.getZ()), BlockRegistry.BULKHEAD_DOOR_LARGE_HELPER.getDefaultState()
                            .withProperty(BlockBulkheadDoorLargeHelper.FACING, placer.getHorizontalFacing().rotateY()), 3);
                    if (worldIn.getTileEntity(new BlockPos(eastWest ? xz.getX() : zx.getX(), y.getY(), eastWest ? zx.getZ() : xz.getZ())) instanceof TileEntityDoorHelper) {
                        ((TileEntityDoorHelper) worldIn.getTileEntity(new BlockPos(eastWest ? xz.getX() : zx.getX(), y.getY(), eastWest ? zx.getZ() : xz.getZ()))).master = pos;
                        Location location;
                        if (k == 0) {
                            if (j == 6 && i == 3) {
                                location = Location.CORNERR;
                            } else if (j == 6) {
                                location = Location.RIGHT;
                            } else if (j == 0 && i == 3) {
                                location = Location.CORNERL;
                            } else if (j == 0) {
                                location = Location.LEFT;
                            } else if (i == 3) {
                                location = Location.TOP;
                            } else {
                                location = Location.CENTER;
                            }
                        } else {
                            location = Location.CENTER;
                        }
                        ((TileEntityDoorHelper) worldIn.getTileEntity(new BlockPos(eastWest ? xz.getX() : zx.getX(), y.getY(), eastWest ? zx.getZ() : xz.getZ()))).setLocation(location);
                    }
                }
            }
        }
    }

    public static void breakDoor(World world, BlockPos pos, EnumFacing facing) {
        for (int k = -1; k <= 1; k++) {
            for (int i = 3; i >= 0; i--) {
                for (int j = 0; j <= 6; j++) {
                    boolean eastWest = facing == EnumFacing.EAST || facing == EnumFacing.WEST;
                    BlockPos xz = pos.offset(facing, -j);
                    BlockPos zx = pos.offset(facing.rotateY(), k);
                    BlockPos y = pos.offset(EnumFacing.UP, i);
                    if (world.getBlockState(new BlockPos(eastWest ? xz.getX() : zx.getX(), y.getY(), eastWest ? zx.getZ() : xz.getZ())).getBlock().equals(BlockRegistry.BULKHEAD_DOOR_LARGE_CONTROLLER))
                        continue;
                    world.setBlockToAir(new BlockPos(eastWest ? xz.getX() : zx.getX(), y.getY(), eastWest ? zx.getZ() : xz.getZ()));
                }
            }
        }
        world.setBlockToAir(pos);
    }
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        if (state.getValue(OPEN)) {
            EnumFacing enumfacing = state.getValue(BlockBulkheadDoorLargeHelper.FACING);
            boolean isEastWest = enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.EAST;
            boolean isInversed = enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.SOUTH;
            if (!isInversed) {
                return isEastWest ? BlockBulkheadDoorLargeHelper.EAST_RIGHT : BlockBulkheadDoorLargeHelper.NORTH_LEFT;
            } else {
                return isEastWest ? BlockBulkheadDoorLargeHelper.EAST_LEFT : BlockBulkheadDoorLargeHelper.NORTH_RIGHT;
            }
        }
        EnumFacing enumfacing = state.getValue(FACING);
        switch (enumfacing) {
            case WEST:
            case EAST:
                return BlockBulkheadDoorLargeHelper.EAST_CENTER;
            default:
                return BlockBulkheadDoorLargeHelper.NORTH_CENTER;
        }
    }
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        breakDoor(worldIn, pos, state.getValue(FACING));
    }

}
