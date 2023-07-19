package io.github.itskillerluc.gtfo_craft.block;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.GtfoCraftCreativeTab;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class BlockBreakableDoor extends BlockDoor {
    public BlockBreakableDoor(Material materialIn) {
        super(materialIn);
        setCreativeTab(GtfoCraftCreativeTab.INSTANCE);
        setRegistryName(new ResourceLocation(GtfoCraft.MODID, "breakable_door"));
        setUnlocalizedName("breakable_door");
    }
}
