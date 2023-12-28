package io.github.itskillerluc.gtfo_craft.item;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.GtfoCraftCreativeTab;
import io.github.itskillerluc.gtfo_craft.registry.ItemRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemHealthSyringe extends Item {
    public ItemHealthSyringe() {
        this.setCreativeTab(GtfoCraftCreativeTab.INSTANCE);
        setRegistryName(new ResourceLocation(GtfoCraft.MODID, "i2_lp_syringe"));
        setUnlocalizedName("i2_lp_syringe");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        playerIn.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 1, 0));
        playerIn.getHeldItem(handIn).shrink(1);
        return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }
}
