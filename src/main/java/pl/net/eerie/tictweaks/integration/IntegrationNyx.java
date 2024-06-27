package pl.net.eerie.tictweaks.integration;

import com.google.common.eventbus.Subscribe;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import pl.net.eerie.tictweaks.TiCTweaks;
import pl.net.eerie.tictweaks.book.Modifiers;
import pl.net.eerie.tictweaks.traits.nyx.LunarEdgeTrait;
import slimeknights.mantle.pulsar.pulse.Pulse;
import slimeknights.tconstruct.library.TinkerRegistry;

@Pulse(id = "nyxIntegration", description = "Integration with Nyx", modsRequired = "nyx")
public class IntegrationNyx {

    @Subscribe
    public void init(FMLInitializationEvent event) {
        TiCTweaks.INSTANCE.log.info("Initializing Nyx Integration");
        if (TinkerRegistry.getTrait("lunar_edge") == null) {
            TiCTweaks.INSTANCE.log.info("Registering Lunar Edge");
            Modifiers.modifiers.add(new LunarEdgeTrait("lunar_edge"));
        } else {
            TiCTweaks.INSTANCE.log.info("Failed to register Lunar Edge: Already exists");
        }
    }
}
