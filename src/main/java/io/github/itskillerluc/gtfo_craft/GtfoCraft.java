package io.github.itskillerluc.gtfo_craft;

import io.github.itskillerluc.gtfo_craft.command.ScanCommand;
import io.github.itskillerluc.gtfo_craft.command.StopScanCommand;
import io.github.itskillerluc.gtfo_craft.network.PacketHandler;
import io.github.itskillerluc.gtfo_craft.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

import java.io.File;
import java.io.IOException;

@Mod(modid = GtfoCraft.MODID, name = GtfoCraft.NAME, version = GtfoCraft.VERSION, dependencies = "required-after:geckolib3")
public class GtfoCraft
{
    public static final String MODID = "gtfo_craft";
    public static final String NAME = "GTFO Craft Protocol";
    public static final String VERSION = "1.0";

    @Mod.Instance(MODID)
    public static GtfoCraft instance;

    private static Logger logger;

    @SidedProxy(clientSide = "io.github.itskillerluc.gtfo_craft.proxy.ClientProxy", serverSide = "io.github.itskillerluc.gtfo_craft.proxy.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        GeckoLib.initialize();
    }

    @EventHandler
    public void registerCommands(FMLServerStartingEvent event) {
        event.registerServerCommand(new ScanCommand());
        event.registerServerCommand(new StopScanCommand());
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        event.getModMetadata().version = VERSION;
        proxy.preInit(event);
    }

    @EventHandler
    public void registerConfig(FMLServerStartingEvent event) {
        try {
            EntityStatConfig.loadConfig(new File(event.getServer().getWorld(0).getSaveHandler().getWorldDirectory(), "gtfo_config.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
