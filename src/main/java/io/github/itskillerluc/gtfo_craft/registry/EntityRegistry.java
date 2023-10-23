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
        registryEvent.getRegistry().register(getEntityEntry(id++, "Striker", "striker", EntityStriker.class, 0xFF0000, 0x00FF00, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "Big Striker", "big_striker", EntityBigStriker.class, 0x00FF00, 0xFF0000, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "Shooter", "shooter", EntityShooter.class, 0x00FF00, 0xF0F0F0, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "Big Shooter", "big_shooter", EntityBigShooter.class, 0x000000, 0xFFFFFF, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "Charger", "charger", EntityCharger.class,0x076DF2 , 0x0fABDE, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "Big Charger", "big_charger", EntityBigCharger.class, 0x0fABDE, 0x076DF2, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "Hybrid", "hybrid", EntityHybrid.class, 0xa382dd, 0x123456, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "Small Shadow", "small_shadow", EntitySmallShadow.class, 0x1a1290, 0xed45fa, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "Big Shadow", "big_shadow", EntityBigShadow.class, 0xf3930a, 0x389fad, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "Baby", "baby", EntityBaby.class, 0x83720a, 0x330938, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "Mother", "mother", EntityMother.class, 0x98fa30, 0xad39a0, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "Big Mother", "big_mother", EntityBigMother.class, 0x931303, 0x0a9302, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "Tank", "tank", EntityTank.class, 0xfa987d, 0x149afe, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "Immortal", "immortal", EntityImmortal.class, 0xaaa000, 0xa08939a, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "Flyer", "flyer", EntityFlyer.class, 0x000333, 0x3024a0, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "Big Flyer", "big_flyer", EntityBigFlyer.class, 0xab038d, 0x298000, 160, 2, false));
        registryEvent.getRegistry().register(EntityEntryBuilder.create().name("Spitter").id(new ResourceLocation(GtfoCraft.MODID, "spitter"), id++).entity(EntitySpitter.class).tracker(160, 2, false).build());
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
