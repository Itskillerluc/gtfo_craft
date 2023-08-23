package io.github.itskillerluc.gtfo_craft.block;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.GtfoCraftCreativeTab;
import io.github.itskillerluc.gtfo_craft.registry.BlockRegistry;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityBreakableDoorSmall;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityDoorHelper;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityDoorHelper.Location;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockBreakableDoorSmallController extends BlockDoorController implements ITileEntityProvider {
    public BlockBreakableDoorSmallController(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
        setCreativeTab(GtfoCraftCreativeTab.INSTANCE);
        setRegistryName(new ResourceLocation(GtfoCraft.MODID, "breakable_door_controller_small"));
        setUnlocalizedName("breakable_door_small");
        setDefaultState(super.getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, false).withProperty(OPEN, false));
    }


    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityBreakableDoorSmall();
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        for (int i = 2; i >= 0; i--) {
            for (int j = 0; j <= 2; j++) {
                BlockPos xz = pos.offset(placer.getHorizontalFacing().rotateY(), j);
                BlockPos y = pos.offset(EnumFacing.UP, i);
                boolean eastWest = state.getValue(FACING) == EnumFacing.EAST || state.getValue(FACING) == EnumFacing.WEST;
                if (!worldIn.getBlockState(new BlockPos(eastWest ? xz.getX() : pos.getX(), y.getY(), eastWest ? pos.getZ() : xz.getZ())).getMaterial().isReplaceable()) continue;
                worldIn.setBlockState(new BlockPos(eastWest ? xz.getX() : pos.getX(), y.getY(), eastWest ? pos.getZ() : xz.getZ()), BlockRegistry.BREAKABLE_DOOR_SMALL_HELPER.getDefaultState()
                        .withProperty(BlockBreakableDoorSmallHelper.FACING, placer.getHorizontalFacing().rotateY()), 3);
                if ( worldIn.getTileEntity(new BlockPos(eastWest ? xz.getX() : pos.getX(), y.getY(), eastWest ? pos.getZ() : xz.getZ())) instanceof TileEntityDoorHelper) {
                    ((TileEntityDoorHelper) worldIn.getTileEntity(new BlockPos(eastWest ? xz.getX() : pos.getX(), y.getY(), eastWest ? pos.getZ() : xz.getZ()))).master = pos;
                    Location location;
                    if (i == 2 && j == 2) {
                        location = Location.CORNERR;
                    } else if (i == 2) {
                        location = Location.RIGHT;
                    } else if (i == 0 && j == 2) {
                        location = Location.CORNERL;
                    } else if (i == 0) {
                        location = Location.LEFT;
                    } else if (j == 2) {
                        location = Location.TOP;
                    } else {
                        location = Location.CENTER;
                    }
                    ((TileEntityDoorHelper) worldIn.getTileEntity(new BlockPos(eastWest ? xz.getX() : pos.getX(), y.getY(), eastWest ? pos.getZ() : xz.getZ()))).setLocation(location);
                }
            }
        }
    }
    public static void breakDoor(World world, BlockPos pos, EnumFacing facing) {
        for (int i = 2; i >= 0; i--) {
            for (int j = 0; j <= 2; j++) {
                BlockPos xz = pos.offset(facing, -j);
                BlockPos y = pos.offset(EnumFacing.UP, i);
                boolean eastWest = facing == EnumFacing.EAST || facing == EnumFacing.WEST;
                if (world.getBlockState(new BlockPos(eastWest ? xz.getX() : pos.getX(), y.getY(), eastWest ? pos.getZ() : xz.getZ())).getBlock().equals(BlockRegistry.BREAKABLE_DOOR_SMALL_CONTROLLER)) continue;
                world.setBlockToAir(new BlockPos(eastWest ? xz.getX() : pos.getX(), y.getY(), eastWest ? pos.getZ() : xz.getZ()));
            }
        }
        world.setBlockToAir(pos);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        breakDoor(worldIn, pos, state.getValue(FACING));
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {}

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (state.getValue(OPEN)) {
            if (worldIn.getTileEntity(pos) instanceof TileEntityBreakableDoorSmall && worldIn.getTileEntity(pos) != null) {
                ((TileEntityBreakableDoorSmall) worldIn.getTileEntity(pos)).shouldClose = true;
                worldIn.markBlockRangeForRenderUpdate(pos, pos);
                return true;
            }
        } else {
            if (worldIn.getTileEntity(pos) instanceof TileEntityBreakableDoorSmall && worldIn.getTileEntity(pos) != null) {
                ((TileEntityBreakableDoorSmall) worldIn.getTileEntity(pos)).shouldOpen = true;
                worldIn.markBlockRangeForRenderUpdate(pos, pos);
                return true;
            }
        }
        return false;
    }
}
