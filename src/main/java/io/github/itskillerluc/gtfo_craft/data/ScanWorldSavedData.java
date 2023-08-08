package io.github.itskillerluc.gtfo_craft.data;

import io.github.itskillerluc.gtfo_craft.GtfoCraft;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScanWorldSavedData extends WorldSavedData {
    private static final String DATA_NAME = GtfoCraft.MODID + "_scans";
    public final List<Scan> scanList = Collections.synchronizedList(new ArrayList<>());

    public ScanWorldSavedData() {
        super(DATA_NAME);
    }
    public ScanWorldSavedData(String name) {
        super(name);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        NBTTagList tagList = nbt.getTagList("scans", 10);
        for (NBTBase nbtBase : tagList) {
            scanList.add(Scan.fromTag((NBTTagCompound) nbtBase));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagList tagList = new NBTTagList();
        for (Scan scan : scanList) {
            tagList.appendTag(scan.toTag());
        }
        compound.setTag("scans", tagList);
        return compound;
    }

    public static ScanWorldSavedData get(World world) {
        MapStorage storage = world.getPerWorldStorage();
        ScanWorldSavedData instance = (ScanWorldSavedData) storage.getOrLoadData(ScanWorldSavedData.class, DATA_NAME);

        if (instance == null) {
            instance = new ScanWorldSavedData();
            storage.setData(DATA_NAME, instance);
        }
        return instance;
    }
}
