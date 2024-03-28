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
import pl.net.eerie.tictweaks.proxy.CommonProxy;

@Mod(
        modid = TiCTweaks.MOD_ID,
        name = TiCTweaks.MOD_NAME,
        version = TiCTweaks.VERSION,
        dependencies = "required-before:tconstruct;required-before:mantle;before:bloodmagic;before:lycanitesmobs;before:enderio"
)
public class TiCTweaks {

    public static final String MOD_ID = "eerietictweaks";
    public static final String MOD_NAME = "EΣrie's tweaks for Tinker's Construct";
    public static final String VERSION = "1.0-SNAPSHOT";

    public IntegrationBloodMagic BLOOD_MAGIC_INTEGRATION;

    public Logger log;

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
    }

    /**
     * This is the second initialization event. Register custom recipes
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    /**
     * This is the final initialization event. Register actions from other mods here
     */
    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {
        if (Loader.isModLoaded("bloodmagic")) BLOOD_MAGIC_INTEGRATION = new IntegrationBloodMagic();
        if (Loader.isModLoaded("lycanitesmobs")) IntegrationLycanitesMobs.registerModifiers();
        if (Loader.isModLoaded("enderio")) IntegrationEnderIO.registerSoulTraits();
        proxy.postInit(event);
    }
}
