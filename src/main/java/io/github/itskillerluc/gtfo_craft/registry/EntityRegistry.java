package io.github.itskillerluc.gtfo_craft.registry;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

public class EntityRegistry {
    public static void registerEntities(RegistryEvent.Register<EntityEntry> registryEvent) {
        int id = 0;
        registryEvent.getRegistry().register(getEntityEntry(id++, "striker_standing", "striker_standing", EntityStrikerStanding.class, 0xc68642, 0xFF0000, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "striker_crouching", "striker_crouching", EntityStrikerCrouching.class, 0xc68642, 0xFF0000, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "striker_crawling", "striker_crawling", EntityStrikerCrawling.class, 0xc68642, 0xFF0000, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "big_striker", "big_striker", EntityBigStriker.class, 0x1f1f1f, 0xC68642, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "shooter", "shooter", EntityShooter.class, 0xF0F0F0, 0xC68642, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "scout", "scout", EntityScout.class, 0xF0F0F0, 0xC68642, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "big_shooter", "big_shooter", EntityBigShooter.class, 0xfff0fc, 0xff4242, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "charger", "charger", EntityCharger.class,0x0f0f0f , 0x1f1f1f, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "scout_charger", "scout_charger", EntityScoutCharger.class,0x0f0f0f , 0x1f1f1f, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "charger_scout", "charger_scout", EntityChargerScout.class,0x0f0f0f , 0x1f1f1f, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "big_charger", "big_charger", EntityBigCharger.class, 0x1f1f1f, 0x0f0f0f, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "hybrid", "hybrid", EntityHybrid.class, 0xfefff5, 0x2e2016, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "shadow_standing", "shadow_standing", EntityShadowStanding.class, 0, 0, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "shadow_crouching", "shadow_crouching", EntityShadowCrouching.class, 0, 0, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "scout_shadow", "scout_shadow", EntityScoutShadow.class, 0, 0, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "shadow_scout", "shadow_scout", EntityShadowScout.class, 0, 0, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "big_shadow_charger", "big_shadow_charger", EntityBigShadowCharger.class, 0, 0, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "big_shadow_shooter", "big_shadow_shooter", EntityBigShadowShooter.class, 0, 0, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "big_shadow_hybrid", "big_shadow_hybrid", EntityBigShadowHybrid.class, 0, 0, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "baby", "baby", EntityBaby.class, 0xc38a8a, 0xFFCBA4, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "mother", "mother", EntityMother.class, 0x8d5524, 0x8D021F, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "big_mother", "big_mother", EntityBigMother.class, 0x8d5524, 0xBF0A30, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "tank", "tank", EntityTank.class, 0x1a0101, 0x693232, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "immortal", "immortal", EntityImmortal.class, 0x141414, 0x2b2b2b, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "flyer", "flyer", EntityFlyer.class, 0x4a2511, 0x800000, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "big_flyer", "big_flyer", EntityBigFlyer.class, 0x800000, 0x4a2511, 160, 2, false));
        registryEvent.getRegistry().register(EntityEntryBuilder.create().name("Fog Repeller").id(new ResourceLocation(GtfoCraft.MODID, "fog_repeller"), id++).entity(EntityFogRepeller.class).tracker(160, 2, true).build());
        registryEvent.getRegistry().register(EntityEntryBuilder.create().name("Pellet").id(new ResourceLocation(GtfoCraft.MODID, "pellet"), id++).entity(EntityPellet.class).tracker(160, 2, true).build());
        registryEvent.getRegistry().register(EntityEntryBuilder.create().name("Glow Stick").id(new ResourceLocation(GtfoCraft.MODID, "glow_stick"), id++).entity(EntityGlowStick.class).tracker(160, 2, true).build());
    }

    private static <E extends Entity>EntityEntry getEntityEntry(int id, String entityName, String internalName, Class<? extends E> entity, int primaryEggColor, int secondaryEggColor, int range, int updateFrequency, boolean velocityUpdates) {
        return EntityEntryBuilder.create()
                .id(new ResourceLocation(GtfoCraft.MODID, internalName), id)
                .entity(entity).name(entityName).egg(primaryEggColor, secondaryEggColor)
                .tracker(range, updateFrequency, velocityUpdates).build();
    }
}
