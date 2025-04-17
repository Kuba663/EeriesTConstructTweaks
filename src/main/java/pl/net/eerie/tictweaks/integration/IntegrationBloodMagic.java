package pl.net.eerie.tictweaks.integration;

import com.google.common.eventbus.Subscribe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import pl.net.eerie.tictweaks.TiCTweaks;
import pl.net.eerie.tictweaks.book.Modifiers;
import pl.net.eerie.tictweaks.traits.bloodmagic.SentientTrait;
import slimeknights.mantle.pulsar.pulse.Pulse;
import slimeknights.tconstruct.common.ModelRegisterUtil;
import slimeknights.tconstruct.library.TinkerRegistry;

@Pulse(id = "bloodMagicIntegration", description = "Integration with blood magic", modsRequired = "bloodmagic;tconstruct", defaultEnable = true)
public class IntegrationBloodMagic {

    @Subscribe
    public void preInit(FMLPreInitializationEvent event) {
        if (TinkerRegistry.getTrait("sentient") == null)
            Modifiers.modifiers.add(new SentientTrait("sentient"));
        ModelRegisterUtil.registerModifierModel(TinkerRegistry.getModifier("sentient"), new ResourceLocation(TiCTweaks.MOD_ID, "models/item/modifiers/sentient.mod.json"));
    }
}
