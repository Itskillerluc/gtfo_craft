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
        registryEvent.getRegistry().register(getEntityEntry(id++, "striker", "striker", EntityStriker.class, 0xFF0000, 0x00FF00, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "big_striker", "big_striker", EntityBigStriker.class, 0x00FF00, 0xFF0000, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "shooter", "shooter", EntityShooter.class, 0x00FF00, 0xF0F0F0, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "big_shooter", "big_shooter", EntityBigShooter.class, 0x000000, 0xFFFFFF, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "charger", "charger", EntityCharger.class,0x076DF2 , 0x0fABDE, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "big_charger", "big_charger", EntityBigCharger.class, 0x0fABDE, 0x076DF2, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "hybrid", "hybrid", EntityHybrid.class, 0xa382dd, 0x123456, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "small_shadow", "small_shadow", EntitySmallShadow.class, 0x1a1290, 0xed45fa, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "big_shadow", "big_shadow", EntityBigShadow.class, 0xf3930a, 0x389fad, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "baby", "baby", EntityBaby.class, 0x83720a, 0x330938, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "mother", "mother", EntityMother.class, 0x98fa30, 0xad39a0, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "big_mother", "big_mother", EntityBigMother.class, 0x931303, 0x0a9302, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "tank", "tank", EntityTank.class, 0xfa987d, 0x149afe, 160, 2, false));
        registryEvent.getRegistry().register(getEntityEntry(id++, "immortal", "immortal", EntityImmortal.class, 0xaaa000, 0xa08939a, 160, 2, false));
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
