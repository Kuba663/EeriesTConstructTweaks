package pl.net.eerie.tictweaks;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.Logger;
import pl.net.eerie.tictweaks.integration.IntegrationBloodMagic;
import pl.net.eerie.tictweaks.integration.IntegrationEnderIO;
import pl.net.eerie.tictweaks.integration.IntegrationLycanitesMobs;
import pl.net.eerie.tictweaks.integration.IntegrationNyx;
import pl.net.eerie.tictweaks.proxy.CommonProxy;
import slimeknights.mantle.pulsar.control.PulseManager;

@Mod(
        modid = TiCTweaks.MOD_ID,
        name = TiCTweaks.MOD_NAME,
        version = TiCTweaks.VERSION,
        dependencies = "required-before:tconstruct;required-before:mantle;before:bloodmagic;before:lycanitesmobs;before:enderio;before:nyx"
)
public class TiCTweaks {

    public static final String MOD_ID = "eerietictweaks";
    public static final String MOD_NAME = "EΣrie's tweaks for Tinker's Construct";
    public static final String VERSION = "1.0-SNAPSHOT";

    public Logger log;

    private final static PulseManager pulsar = new PulseManager("/eerietic/modules");

    static {
        pulsar.registerPulse(new IntegrationBloodMagic());
        pulsar.registerPulse(new IntegrationLycanitesMobs());
        pulsar.registerPulse(new IntegrationEnderIO());
        pulsar.registerPulse(new IntegrationNyx());
    }

    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance(MOD_ID)
    public static TiCTweaks INSTANCE;

    @SidedProxy(clientSide = "pl.net.eerie.tictweaks.proxy.ClientProxy", serverSide = "pl.net.eerie.tictweaks.proxy.ServerProxy")
    public static CommonProxy proxy;

    /**
     * This is the first initialization event. Register tile entities here.
     * The registry events below will have fired prior to entry to this method.
     */
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
        this.log = event.getModLog();
        pulsar.propagateEvent(event);
    }

    /**
     * This is the second initialization event. Register custom recipes
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        pulsar.propagateEvent(event);
    }

    /**
     * This is the final initialization event. Register actions from other mods here
     */
    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        pulsar.propagateEvent(event);
    }
}
