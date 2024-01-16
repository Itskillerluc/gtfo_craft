package io.github.itskillerluc.gtfo_craft.block;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.GtfoCraftCreativeTab;
import io.github.itskillerluc.gtfo_craft.registry.BlockRegistry;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityCommonDoorLarge;
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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockCommonDoorLargeController extends BlockDoorController implements ITileEntityProvider {
    public BlockCommonDoorLargeController(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
        setCreativeTab(GtfoCraftCreativeTab.INSTANCE);
        setRegistryName(new ResourceLocation(GtfoCraft.MODID, "common_door_large_controller"));
        setUnlocalizedName("common_door_large");
        setDefaultState(super.getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, false).withProperty(OPEN, false));
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityCommonDoorLarge();
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        for (int i = 3; i >= 0; i--) {
            for (int j = 0; j <= 6; j++) {
                BlockPos xz = pos.offset(placer.getHorizontalFacing().rotateY(), j);
                BlockPos y = pos.offset(EnumFacing.UP, i);
                boolean eastWest = state.getValue(FACING) == EnumFacing.EAST || state.getValue(FACING) == EnumFacing.WEST;
                if (!worldIn.getBlockState(new BlockPos(eastWest ? xz.getX() : pos.getX(), y.getY(), eastWest ? pos.getZ() : xz.getZ())).getMaterial().isReplaceable()) continue;
                worldIn.setBlockState(new BlockPos(eastWest ? xz.getX() : pos.getX(), y.getY(), eastWest ? pos.getZ() : xz.getZ()), BlockRegistry.COMMON_DOOR_LARGE_HELPER.getDefaultState()
                        .withProperty(BlockCommonDoorLargeHelper.FACING, placer.getHorizontalFacing().rotateY()), 3);
                if ( worldIn.getTileEntity(new BlockPos(eastWest ? xz.getX() : pos.getX(), y.getY(), eastWest ? pos.getZ() : xz.getZ())) instanceof TileEntityDoorHelper) {
                    ((TileEntityDoorHelper) worldIn.getTileEntity(new BlockPos(eastWest ? xz.getX() : pos.getX(), y.getY(), eastWest ? pos.getZ() : xz.getZ()))).master = pos;
                    Location location;
                    if (i == 3 && j == 6) {
                        location = Location.CORNERR;
                    } else if (i == 3) {
                        location = Location.RIGHT;
                    } else if (i == 0 && j == 6) {
                        location = Location.CORNERL;
                    } else if (i == 0) {
                        location = Location.LEFT;
                    } else if (j == 6) {
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
        for (int i = 3; i >= 0; i--) {
            for (int j = 0; j <= 6; j++) {
                BlockPos xz = pos.offset(facing, -j);
                BlockPos y = pos.offset(EnumFacing.UP, i);
                boolean eastWest = facing == EnumFacing.EAST || facing == EnumFacing.WEST;
                if (world.getBlockState(new BlockPos(eastWest ? xz.getX() : pos.getX(), y.getY(), eastWest ? pos.getZ() : xz.getZ())).getBlock().equals(BlockRegistry.COMMON_DOOR_LARGE_CONTROLLER)) continue;
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
        if (state.getValue(POWERED)) return false;
        if (state.getValue(OPEN)) {
            if (worldIn.getTileEntity(pos) instanceof TileEntityCommonDoorLarge && worldIn.getTileEntity(pos) != null) {
                if (((TileEntityCommonDoorLarge) worldIn.getTileEntity(pos)).isLocked()) {
                    playerIn.sendStatusMessage(new TextComponentString("This door is locked!"), true);
                    return true;
                }
                ((TileEntityCommonDoorLarge) worldIn.getTileEntity(pos)).shouldClose = true;
                worldIn.markBlockRangeForRenderUpdate(pos, pos);
                return true;
            }
        } else {
            if (worldIn.getTileEntity(pos) instanceof TileEntityCommonDoorLarge && worldIn.getTileEntity(pos) != null) {
                if (((TileEntityCommonDoorLarge) worldIn.getTileEntity(pos)).isLocked()) {
                    playerIn.sendStatusMessage(new TextComponentString("This door is locked!"), true);
                    return true;
                }
                ((TileEntityCommonDoorLarge) worldIn.getTileEntity(pos)).shouldOpen = true;
                worldIn.markBlockRangeForRenderUpdate(pos, pos);
                return true;
            }
        }
        return false;
    }
}
