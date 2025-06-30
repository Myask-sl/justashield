package invalid.myask.targaseule;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.client.MinecraftForgeClient;

import invalid.myask.targaseule.client.ShowCrouchBlockEvent;
import invalid.myask.undertow.client.RenderShield;

@SuppressWarnings("unused")
public class ClientProxy extends CommonProxy {

    // Override CommonProxy methods here, if you want a different behaviour on the client (e.g. registering renders).
    // Don't forget to call the super methods as well.

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        MinecraftForgeClient.registerItemRenderer(TargaItems.SHIELD, RenderShield.instance);
        MinecraftForgeClient.registerItemRenderer(TargaItems.OLD_SHIELD, RenderShield.instance);

        MinecraftForge.EVENT_BUS.register(ShowCrouchBlockEvent.instance);
    }
}
