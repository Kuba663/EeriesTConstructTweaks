package pl.net.eerie.tictweaks.integration;

import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import com.lycanitesmobs.ObjectManager;
import com.lycanitesmobs.core.info.ElementInfo;
import com.lycanitesmobs.core.info.ElementManager;
import com.lycanitesmobs.core.item.ChargeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import org.jetbrains.annotations.NotNull;
import pl.net.eerie.tictweaks.traits.lycanitesmobs.ElementTrait;
import slimeknights.mantle.pulsar.pulse.Pulse;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.modifiers.Modifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Pulse(id = "lycanitesmobsIntegration", description = "Integration with Lycanite's Mobs", modsRequired = "lycanitesmobs", defaultEnable = true)
public class IntegrationLycanitesMobs {

    @Subscribe
    public void postInit(FMLPostInitializationEvent event) {
        for (ElementInfo info : ElementManager.INSTANCE.elements.values()) {
            if (TinkerRegistry.getTrait(String.format("elemental_%s", info.name )) == null) {
                new ElementTrait(info);
            }
        }
    }

    public static class ProjectileElementRecipeMatch extends RecipeMatch {

        private final ElementInfo element;

        public ProjectileElementRecipeMatch(int amountNeeded, ElementInfo element) {
            super(1, amountNeeded);
            this.element = element;
        }

        @Override
        public List<ItemStack> getInputs() {
            return Lists.newArrayList(ObjectManager.items.values().stream().filter(item -> item instanceof ChargeItem).filter(item -> ((ChargeItem) item).getElements().contains(element)).map(ItemStack::new).toArray(ItemStack[]::new));
        }

        @Override
        public Optional<Match> matches(@NotNull NonNullList<ItemStack> nonNullList) {
            List<ItemStack> found = Lists.newLinkedList();
            int stillNeeded = this.amountNeeded;
            for (ItemStack stack : nonNullList) {
                if (stack.getItem() instanceof ChargeItem && ((ChargeItem)stack.getItem()).getElements().contains(this.element)) {
                    ItemStack copy = stack.copy();
                    copy.setCount(Math.min(copy.getCount(), stillNeeded));
                    found.add(copy);
                    if ((stillNeeded -= copy.getCount()) <= 0) {
                        return Optional.of(new Match(found, this.amountNeeded));
                    }
                }
            }
            return Optional.empty();
        }
    }
}
