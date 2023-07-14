package io.github.itskillerluc.gtfo_craft.proxy;

import com.google.common.util.concurrent.ListenableFuture;
import io.github.itskillerluc.gtfo_craft.client.entity.renderer.RenderBigStriker;
import io.github.itskillerluc.gtfo_craft.client.entity.renderer.RenderShooter;
import io.github.itskillerluc.gtfo_craft.client.entity.renderer.RenderStriker;
import io.github.itskillerluc.gtfo_craft.entity.EntityBigStriker;
import io.github.itskillerluc.gtfo_craft.entity.EntityFogRepeller;
import io.github.itskillerluc.gtfo_craft.entity.EntityShooter;
import io.github.itskillerluc.gtfo_craft.entity.EntityStriker;
import io.github.itskillerluc.gtfo_craft.registry.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void render() {
    }

    @Override
    public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule) {
        return Minecraft.getMinecraft().addScheduledTask(runnableToSchedule);
    }

    @Override
    public EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().player;
    }

    @Override
    public void registerRenderers(FMLPreInitializationEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityStriker.class, RenderStriker::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityBigStriker.class, RenderBigStriker::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityShooter.class, RenderShooter::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityFogRepeller.class, renderManager -> new RenderSnowball<>(renderManager, ItemRegistry.FOG_REPELLER, Minecraft.getMinecraft().getRenderItem()));
    }
}
