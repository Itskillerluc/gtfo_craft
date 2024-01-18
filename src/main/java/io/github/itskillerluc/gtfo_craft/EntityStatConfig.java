package io.github.itskillerluc.gtfo_craft;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EntityStatConfig {
    private static final Gson GSON = new GsonBuilder().serializeNulls().create();
    public static final Map<String, EntityEntry> defaultStats = new HashMap<>();
    public static final Map<String, EntityEntry> loadedStats = new HashMap<>();

    public static double getMaxHealth(String entity) {
        if (loadedStats.containsKey(entity)) {
            if (loadedStats.get(entity).maxHealth != null) {
                return loadedStats.get(entity).maxHealth;
            }
        }
        return defaultStats.get(entity).maxHealth;
    }

    public static double getFollowRange(String entity) {
        if (loadedStats.containsKey(entity)) {
            if (loadedStats.get(entity).followRange != null) {
                return loadedStats.get(entity).followRange;
            }
        }
        return defaultStats.get(entity).followRange;
    }

    public static double getKnockBackResistance(String entity) {
        if (loadedStats.containsKey(entity)) {
            if (loadedStats.get(entity).knockBackResistance != null) {
                return loadedStats.get(entity).knockBackResistance;
            }
        }
        return defaultStats.get(entity).knockBackResistance;
    }

    public static double getMovementSpeed(String entity) {
        if (loadedStats.containsKey(entity)) {
            if (loadedStats.get(entity).movementSpeed != null) {
                return loadedStats.get(entity).movementSpeed;
            }
        }
        return defaultStats.get(entity).movementSpeed;
    }

    public static double getFlyingSpeed(String entity) {
        if (loadedStats.containsKey(entity)) {
            if (loadedStats.get(entity).flyingSpeed != null) {
                return loadedStats.get(entity).flyingSpeed;
            }
        }
        return defaultStats.get(entity).flyingSpeed;
    }

    public static double getAttackDamage(String entity) {
        if (loadedStats.containsKey(entity)) {
            if (loadedStats.get(entity).attackDamage != null) {
                return loadedStats.get(entity).attackDamage;
            }
        }
        return defaultStats.get(entity).attackDamage;
    }

    public static double getAttackSpeed(String entity) {
        if (loadedStats.containsKey(entity)) {
            if (loadedStats.get(entity).attackSpeed != null) {
                return loadedStats.get(entity).attackSpeed;
            }
        }
        return defaultStats.get(entity).attackSpeed;
    }

    public static double getArmor(String entity) {
        if (loadedStats.containsKey(entity)) {
            if (loadedStats.get(entity).armor != null) {
                return loadedStats.get(entity).armor;
            }
        }
        return defaultStats.get(entity).armor;
    }

    public static double getArmorToughness(String entity) {
        if (loadedStats.containsKey(entity)) {
            if (loadedStats.get(entity).armorToughness != null) {
                return loadedStats.get(entity).armorToughness;
            }
        }
        return defaultStats.get(entity).armorToughness;
    }

    public static double getProjectileDamage(String entity) {
        if (loadedStats.containsKey(entity)) {
            if (loadedStats.get(entity).projectileDamage != null) {
                return loadedStats.get(entity).projectileDamage;
            }
        }
        return defaultStats.get(entity).projectileDamage;
    }

    public static void loadConfig(File configFile) throws IOException {
        String config = FileUtils.readFileToString(configFile, Charset.defaultCharset());
        Type listType = new TypeToken<ArrayList<EntityEntry>>(){}.getType();
        ArrayList<EntityEntry> list = GSON.fromJson(config, listType);
        for (EntityEntry entityEntry : list) {
            loadedStats.put(entityEntry.entity, entityEntry);
        }
    }

    public static class EntityEntry {
        public EntityEntry(String entity, Double maxHealth, Double followRange, Double knockBackResistance,
                           Double movementSpeed, Double flyingSpeed, Double attackDamage, Double attackSpeed,
                           Double armor, Double armorToughness, Double projectileDamage) {
            if (entity == null) throw new IllegalArgumentException("Entity key cannot be null, check gtfo_config.json");
            this.entity = entity;
            this.maxHealth = maxHealth;
            this.followRange = followRange;
            this.knockBackResistance = knockBackResistance;
            this.movementSpeed = movementSpeed;
            this.flyingSpeed = flyingSpeed;
            this.attackDamage = attackDamage;
            this.attackSpeed = attackSpeed;
            this.armor = armor;
            this.armorToughness = armorToughness;
            this.projectileDamage = projectileDamage;
        }

        private final String entity;

        private final Double maxHealth;
        private final Double followRange;
        private final Double knockBackResistance;
        private final Double movementSpeed;
        private final Double flyingSpeed;
        private final Double attackDamage;
        private final Double attackSpeed;
        private final Double armor;
        private final Double armorToughness;
        private final Double projectileDamage;
    }

    static {
        defaultStats.put("baby", new EntityEntry("baby", 6D, 100D, 0D, 0.35D, 0.35D, 2D, 60D, 0D, 0D,0D));
        defaultStats.put("big_charger", new EntityEntry("big_charger", 25D, 100D, 0D, 0.4D, 0.4D, 5D, 100D, 0D, 0D, 0D));
        defaultStats.put("big_flyer", new EntityEntry("big_flyer", 30D, 100D, 0D, 0.2D, 0.47D, 2.5D, 100D, 0D, 0D,3D));
        defaultStats.put("big_mother", new EntityEntry("big_mother", 75D, 100D, 0D, 0.3D, 0.3D, 0D, 15D, 0D, 0D, 0D));
        defaultStats.put("big_shadow_charger", new EntityEntry("big_shadow_charger", 30D, 100D, 0D, 0.3D, 0.3D, 6D, 60D, 5D, 0D, 0D));
        defaultStats.put("big_shadow_hybrid", new EntityEntry("big_shadow_hybrid", 30D, 100D, 0D, 0.3D, 0.3D, 6D, 60D, 5D, 0D, 0D));
        defaultStats.put("big_shadow_shooter", new EntityEntry("big_shadow_shooter", 30D, 100D, 0D, 0.3D, 0.3D, 6D, 60D, 5D, 0D, 0D));
        defaultStats.put("big_shooter", new EntityEntry("big_shooter", 35D, 100D, 0D, 0.3D, 0.3D, 5D, 100D, 0D, 0D, 7D));
        defaultStats.put("big_striker", new EntityEntry("big_striker", 30D, 100D, 0D, 0.3D, 0.3D, 6D, 60D, 5D, 0D, 0D));
        defaultStats.put("charger", new EntityEntry("charger", 10D, 100D, 0D, 0.4D, 0.4D, 2D, 100D, 0D, 0D, 0D));
        defaultStats.put("charger_scout", new EntityEntry("charger_scout", 12D, 100D, 0D, 0.3D, 0.3D, 2.5D, 200D, 0D, 0D, 0D));
        defaultStats.put("flyer", new EntityEntry("flyer", 12D, 100D, 0D, 0.2D, 0.4D, 2.5D, 60D, 0D, 0D, 3D));
        defaultStats.put("hybrid", new EntityEntry("hybrid", 12D, 100D, 0D, 0.3D, 0.3D, 2.5D, 100D, 0D, 0D, 3D));
        defaultStats.put("immortal", new EntityEntry("immortal", 50D, 100D, 0D, 0.3D, 0.3D, 10D, 20D, 0D, 0D, 0D));
        defaultStats.put("mother", new EntityEntry("mother", 50D, 100D, 0D, 0.3D, 0.3D, 0D, 20D, 0D, 0D, 0D));
        defaultStats.put("scout", new EntityEntry("scout", 12D, 100D, 0D, 0.3D, 0.3D, 2.5D, 200D, 0D, 0D, 0D));
        defaultStats.put("scout_charger", new EntityEntry("scout_charger", 10D, 100D, 0D, 0.4D, 0.4D, 2D, 20D, 0D, 0D, 0D));
        defaultStats.put("scout_shadow", new EntityEntry("scout_shadow", 12D, 100D, 0D, 0.3D, 0.3D, 2.5D, 60D, 0D, 0D, 2D));
        defaultStats.put("shadow_crouching", new EntityEntry("shadow_crouching", 12D, 100D, 0D, 0.3D, 0.3D, 2.5D, 60D, 0D, 0D, 0D));
        defaultStats.put("shadow_scout", new EntityEntry("shadow_scout", 12D, 100D, 0D, 0.3D, 0.3D, 2.5D, 200D, 0D, 0D, 0D));
        defaultStats.put("shadow_standing", new EntityEntry("shadow_standing", 12D, 100D, 0D, 0.3D, 0.3D, 2.5D, 60D, 0D, 0D, 0D));
        defaultStats.put("shooter", new EntityEntry("shooter", 12D, 100D, 0D, 0.3D, 0.3D, 2.5D, 60D, 0D, 0D, 2D));
        defaultStats.put("striker_crawling", new EntityEntry("striker_crawling", 12D, 100D, 0D, 0.3D, 0.3D, 2.5D, 60D, 0D, 0D, 0D));
        defaultStats.put("striker_crouching", new EntityEntry("striker_crouching", 12D, 100D, 0D, 0.3D, 0.3D, 2.5D, 60D, 0D, 0D, 0D));
        defaultStats.put("striker_standing", new EntityEntry("striker_standing", 12D, 100D, 0D, 0.3D, 0.3D, 2.5D, 60D, 0D, 0D, 0D));
        defaultStats.put("tank", new EntityEntry("tank", 50D, 100D, 0D, 0.3D, 0.3D, 10D, 20D, 5D, 0D, 0D));
    }
}
