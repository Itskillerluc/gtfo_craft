package io.github.itskillerluc.gtfo_craft;

import io.github.itskillerluc.gtfo_craft.registry.BlockRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class GtfoCraftCreativeTab extends CreativeTabs {
    public static final GtfoCraftCreativeTab INSTANCE = new GtfoCraftCreativeTab();

    public GtfoCraftCreativeTab() {
        super(GtfoCraft.MODID);
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(BlockRegistry.FOG);
    }

    @Override
    public boolean hasSearchBar() {
        return true;
    }
}
