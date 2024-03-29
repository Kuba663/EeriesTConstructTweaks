package pl.net.eerie.tictweaks.integration;

import com.google.common.eventbus.Subscribe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import pl.net.eerie.tictweaks.TiCTweaks;
import pl.net.eerie.tictweaks.traits.bloodmagic.SentientTrait;
import slimeknights.mantle.pulsar.pulse.Pulse;
import slimeknights.tconstruct.common.ModelRegisterUtil;

@Pulse(id = "bloodMagicIntegration", description = "Integration with blood magic", modsRequired = "bloodmagic")
public class IntegrationBloodMagic {
    private SentientTrait TRAIT_SENTIENT;

    @Subscribe
    public void init(FMLInitializationEvent event) {
        TRAIT_SENTIENT = new SentientTrait();
        ModelRegisterUtil.registerModifierModel(TRAIT_SENTIENT, new ResourceLocation(TiCTweaks.MOD_ID, "models/item/modifier/sentient"));
    }
}
