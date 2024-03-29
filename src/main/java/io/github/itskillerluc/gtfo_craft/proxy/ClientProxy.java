package io.github.itskillerluc.gtfo_craft.proxy;

import com.google.common.util.concurrent.ListenableFuture;
import io.github.itskillerluc.gtfo_craft.client.entity.renderer.*;
import io.github.itskillerluc.gtfo_craft.client.tile.renderer.*;
import io.github.itskillerluc.gtfo_craft.entity.*;
import io.github.itskillerluc.gtfo_craft.registry.ItemRegistry;
import io.github.itskillerluc.gtfo_craft.tileentity.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;

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
    public void preInit(FMLPreInitializationEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityStrikerStanding.class, RenderStrikerStanding::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityStrikerCrawling.class, RenderStrikerCrawling::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityStrikerCrouching.class, RenderStrikerCrouching::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityBigStriker.class, RenderBigStriker::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityShooter.class, RenderShooter::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityBigShooter.class, RenderBigShooter::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityCharger.class, RenderCharger::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityBigCharger.class, RenderBigCharger::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityHybrid.class, RenderHybrid::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityBigShadowCharger.class, RenderBigShadowCharger::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityBigShadowHybrid.class, RenderBigShadowHybrid::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityBigShadowShooter.class, RenderBigShadowShooter::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityShadowStanding.class, RenderShadowStanding::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityShadowCrouching.class, RenderShadowCrouching::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityBaby.class, RenderBaby::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityMother.class, RenderMother::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityBigMother.class, RenderBigMother::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTank.class, RenderTank::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityImmortal.class, RenderImmortal::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityFlyer.class, RenderFlyer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityBigFlyer.class, RenderBigFlyer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityScout.class, RenderScout::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityChargerScout.class, RenderChargerScout::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityScoutCharger.class, RenderScoutCharger::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityShadowScout.class, RenderShadowScout::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityScoutShadow.class, RenderScoutShadow::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityPellet.class, RenderPellet::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityFogRepeller.class, renderManager -> new RenderProjectile<>(renderManager, ItemRegistry.FOG_REPELLER, Minecraft.getMinecraft().getRenderItem()));
        RenderingRegistry.registerEntityRenderingHandler(EntityGlowStick.class, renderManager -> new RenderProjectile<>(renderManager, ItemRegistry.GLOW_STICK, Minecraft.getMinecraft().getRenderItem()));

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTripMine.class, new RenderTripMine());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurret.class, new RenderTurret());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySecurityDoorSmall.class, new RenderSecurityDoorSmall());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySecurityDoorLarge.class, new RenderSecurityDoorLarge());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCommonDoorLarge.class, new RenderCommonDoorLarge());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCommonDoorSmall.class, new RenderCommonDoorSmall());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPossessedSecurityDoorLarge.class, new RenderPossessedSecurityDoorLarge());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPossessedSecurityDoorSmall.class, new RenderPossessedSecurityDoorSmall());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityApexDoorLarge.class, new RenderApexDoorLarge());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityApexDoorSmall.class, new RenderApexDoorSmall());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBulkheadDoorLarge.class, new RenderBulkheadDoorLarge());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBulkheadDoorSmall.class, new RenderBulkheadDoorSmall());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCocoon.class, new RenderCocoon());

        super.preInit(event);
    }
}
