package io.github.itskillerluc.gtfo_craft.registry;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import io.github.itskillerluc.gtfo_craft.entity.EntityStriker;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

public class EntityRegistry {
    public static void registerEntities(RegistryEvent.Register<EntityEntry> registryEvent) {
        int id = 0;
        registryEvent.getRegistry().register(getEntityEntry(id++, "Striker", "striker", EntityStriker.class, 0xFF0000, 0x00FF00, 160, 2, false));
    }

    private static <E extends Entity>EntityEntry getEntityEntry(int id, String entityName, String internalName, Class<? extends E> entity, int primaryEggColor, int secondaryEggColor, int range, int updateFrequency, boolean velocityUpdates) {
        return EntityEntryBuilder.create()
                .id(new ResourceLocation(GtfoCraft.MODID, internalName), id)
                .entity(entity).name(entityName).egg(primaryEggColor, secondaryEggColor)
                .tracker(range, updateFrequency, velocityUpdates).build();
    }
}
