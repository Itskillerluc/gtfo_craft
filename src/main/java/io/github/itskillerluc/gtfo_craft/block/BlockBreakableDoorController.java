package io.github.itskillerluc.gtfo_craft.block;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.GtfoCraftCreativeTab;
import io.github.itskillerluc.gtfo_craft.network.BulkheadDoorPacket;
import io.github.itskillerluc.gtfo_craft.network.PacketHandler;
import io.github.itskillerluc.gtfo_craft.registry.BlockRegistry;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityBreakableDoor;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityBulkheadDoor;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityBulkheadDoorSmall;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityBulkheadDoorHelper;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityBulkheadDoorHelper.Location;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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

public class BlockBreakableDoorController extends BlockBulkheadDoorController implements ITileEntityProvider {
    public BlockBreakableDoorController(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
        setCreativeTab(GtfoCraftCreativeTab.INSTANCE);
        setRegistryName(new ResourceLocation(GtfoCraft.MODID, "breakable_door_controller"));
        setUnlocalizedName("breakable_door");
        setDefaultState(super.getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, false).withProperty(OPEN, false));
    }


    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityBreakableDoor();
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
                worldIn.setBlockState(new BlockPos(eastWest ? xz.getX() : pos.getX(), y.getY(), eastWest ? pos.getZ() : xz.getZ()), BlockRegistry.BREAKABLE_DOOR_HELPER.getDefaultState()
                        .withProperty(BlockBreakableDoorHelper.FACING, placer.getHorizontalFacing().rotateY()), 3);
                if ( worldIn.getTileEntity(new BlockPos(eastWest ? xz.getX() : pos.getX(), y.getY(), eastWest ? pos.getZ() : xz.getZ())) instanceof TileEntityBulkheadDoorHelper) {
                    ((TileEntityBulkheadDoorHelper) worldIn.getTileEntity(new BlockPos(eastWest ? xz.getX() : pos.getX(), y.getY(), eastWest ? pos.getZ() : xz.getZ()))).master = pos;
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
                    ((TileEntityBulkheadDoorHelper) worldIn.getTileEntity(new BlockPos(eastWest ? xz.getX() : pos.getX(), y.getY(), eastWest ? pos.getZ() : xz.getZ()))).setLocation(location);
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
                if (world.getBlockState(new BlockPos(eastWest ? xz.getX() : pos.getX(), y.getY(), eastWest ? pos.getZ() : xz.getZ())).getBlock().equals(BlockRegistry.BREAKABLE_DOOR_CONTROLLER)) continue;
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
            if (worldIn.getTileEntity(pos) instanceof TileEntityBreakableDoor && worldIn.getTileEntity(pos) != null) {
                ((TileEntityBreakableDoor) worldIn.getTileEntity(pos)).shouldClose = true;
                worldIn.markBlockRangeForRenderUpdate(pos, pos);
                return true;
            }
        } else {
            if (worldIn.getTileEntity(pos) instanceof TileEntityBreakableDoor && worldIn.getTileEntity(pos) != null) {
                ((TileEntityBreakableDoor) worldIn.getTileEntity(pos)).shouldOpen = true;
                worldIn.markBlockRangeForRenderUpdate(pos, pos);
                return true;
            }
        }
        return false;
    }
}
