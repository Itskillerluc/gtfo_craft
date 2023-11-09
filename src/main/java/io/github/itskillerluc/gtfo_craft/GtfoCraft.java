package io.github.itskillerluc.gtfo_craft;

import io.github.itskillerluc.gtfo_craft.client.tile.renderer.RenderTripMine;
import io.github.itskillerluc.gtfo_craft.command.ScanCommand;
import io.github.itskillerluc.gtfo_craft.network.PacketHandler;
import io.github.itskillerluc.gtfo_craft.proxy.CommonProxy;
import io.github.itskillerluc.gtfo_craft.tileentity.TileEntityTripMine;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ServerCommandManager;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

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

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        event.getModMetadata().version = VERSION;
        PacketHandler.init();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
    }

    @EventHandler
    public void registerCommands(FMLServerStartingEvent event) {
        event.registerServerCommand(new ScanCommand());
    }

    @Mod.EventHandler
    public void registerRenderers(FMLPreInitializationEvent event) {
        proxy.registerRenderers(event);
    }
}
