package io.github.itskillerluc.gtfo_craft;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = GtfoCraft.MODID, name = GtfoCraft.NAME, version = GtfoCraft.VERSION)
public class GtfoCraft
{
    public static final String MODID = "gtfo_craft";
    public static final String NAME = "GTFO: Craft Protocol";
    public static final String VERSION = "1.0";

    public static GtfoCraft instance;

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {

    }
}
