package io.github.itskillerluc.gtfo_craft;

import io.github.itskillerluc.gtfo_craft.client.entity.renderer.RenderStriker;
import io.github.itskillerluc.gtfo_craft.entity.EntityStriker;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

@Mod(modid = GtfoCraft.MODID, name = GtfoCraft.NAME, version = GtfoCraft.VERSION)
public class GtfoCraft
{
    public static final String MODID = "gtfo_craft";
    public static final String NAME = "GTFO: Craft Protocol";
    public static final String VERSION = "1.0";

    @Mod.Instance(MODID)
    public static GtfoCraft instance;

    private static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        event.getModMetadata().version = VERSION;
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @SideOnly(Side.CLIENT)
    @Mod.EventHandler
    public void registerRenderers(FMLPreInitializationEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityStriker.class, RenderStriker::new);
    }
}
