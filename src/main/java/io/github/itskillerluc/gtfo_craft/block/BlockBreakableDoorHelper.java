package io.github.itskillerluc.gtfo_craft.block;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.GtfoCraftCreativeTab;
import io.github.itskillerluc.gtfo_craft.network.BulkheadDoorPacket;
import io.github.itskillerluc.gtfo_craft.network.PacketHandler;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityBreakableDoor;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityBulkheadDoor;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityBulkheadDoorHelper;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockBreakableDoorHelper extends BlockBulkheadDoorHelper implements ITileEntityProvider {
        public BlockBreakableDoorHelper(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
        setCreativeTab(GtfoCraftCreativeTab.INSTANCE);
        setRegistryName(new ResourceLocation(GtfoCraft.MODID, "breakable_door_helper"));
        setUnlocalizedName("breakable_door_helper");
        setDefaultState(super.getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, false).withProperty(OPEN, false));
    }


    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (worldIn.getTileEntity(pos) instanceof TileEntityBulkheadDoorHelper) {
            BlockBreakableDoorController.breakDoor(worldIn, ((TileEntityBulkheadDoorHelper) worldIn.getTileEntity(pos)).master, state.getValue(FACING).getOpposite());
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityBulkheadDoorHelper();
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (state.getValue(OPEN)) {
            if (worldIn.getTileEntity(pos) instanceof TileEntityBulkheadDoorHelper && worldIn.getTileEntity(pos) != null) {
                ((TileEntityBreakableDoor) worldIn.getTileEntity(((TileEntityBulkheadDoorHelper) worldIn.getTileEntity(pos)).master)).shouldClose = true;
                ((TileEntityBreakableDoor) worldIn.getTileEntity(((TileEntityBulkheadDoorHelper) worldIn.getTileEntity(pos)).master)).shouldOpen = false;
                worldIn.markBlockRangeForRenderUpdate(pos, pos);
                return true;
            }
        } else {
            if (worldIn.getTileEntity(pos) instanceof TileEntityBulkheadDoorHelper && worldIn.getTileEntity(pos) != null) {
                ((TileEntityBreakableDoor) worldIn.getTileEntity(((TileEntityBulkheadDoorHelper) worldIn.getTileEntity(pos)).master)).shouldOpen = true;
                worldIn.markBlockRangeForRenderUpdate(pos, pos);
                return true;
            }
        }
        return false;
    }
}
