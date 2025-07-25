package invalid.myask.targaseule;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;

import invalid.myask.undertow.util.ShieldUtil;

public class CommonProxy {

    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        Config.synchronizeConfiguration(event.getSuggestedConfigurationFile());

        GameRegistry.registerItem(TargaItems.SHIELD, "shield", TargaSeule.MODID);
        TargaItems.SHIELD.setCreativeTab(CreativeTabs.tabCombat);
        GameRegistry.registerItem(TargaItems.OLD_SHIELD, "old_shield", TargaSeule.MODID);
        TargaItems.OLD_SHIELD.setCreativeTab(CreativeTabs.tabCombat);

        MinecraftForge.EVENT_BUS.register(TargaEvent.instance);
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {
        TargaRecipes.register();
    }

    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {
        ShieldUtil.checkForCleaving();
    }

    // register server commands in this event handler (Remove if not needed)
    // public void serverStarting(FMLServerStartingEvent event) {}
}
