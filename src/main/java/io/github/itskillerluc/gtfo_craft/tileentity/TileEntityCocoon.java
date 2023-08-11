package io.github.itskillerluc.gtfo_craft.tileentity;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.GameData;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class TileEntityCocoon extends TileEntity implements ITickable {
    private final int range;
    @Nullable
    private Entity entity;
    @Nullable
    private EntityEntry entityFactory;
    private UUID uuid = UUID.randomUUID();

    public TileEntityCocoon(int range) {
        this.range = range;
    }

    public int getRange() {
        return range;
    }

    public Optional<Entity> getEntity() {
        return Optional.ofNullable(entity);
    }

    public void setEntity(@Nullable EntityLiving entity) {
        this.entity = entity;
    }

    public void setEntityFactory(@Nullable EntityEntry entityFactory) {
        this.entityFactory = entityFactory;
    }

    @Nullable
    public EntityEntry getEntityFactory() {
        return entityFactory;
    }

    @Override
    public void update() {
        if (world.isRemote) return;
        if ((entity == null || !entity.isEntityAlive()) && ((WorldServer) world).getEntityFromUuid(uuid) == null && entityFactory != null && !world.isAnyPlayerWithinRangeAt(pos.getX(), pos.getY(), pos.getZ(), range)) {
            entity = entityFactory.newInstance(world);
            entity.setUniqueId(uuid);
            world.spawnEntity(entity);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("uuid", NBTUtil.createUUIDTag(uuid));
        compound.setString("entity", ForgeRegistries.ENTITIES.getKey(GameData.getEntityClassMap().get(entityFactory)).toString());
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        this.uuid = NBTUtil.getUUIDFromTag(compound.getCompoundTag("uuid"));
        this.entityFactory = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(compound.getString("entity")));
        super.readFromNBT(compound);
    }
}
