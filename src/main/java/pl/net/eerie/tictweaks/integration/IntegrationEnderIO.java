package pl.net.eerie.tictweaks.integration;


import com.google.common.eventbus.Subscribe;
import crazypants.enderio.base.item.soulvial.ItemSoulVial;
import crazypants.enderio.util.CapturedMob;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.GameData;
import org.jetbrains.annotations.NotNull;
import pl.net.eerie.tictweaks.traits.enderio.SoulfulTrait;
import slimeknights.mantle.pulsar.pulse.Pulse;
import slimeknights.mantle.util.RecipeMatch;

import java.util.*;
@Pulse(id = "enderioIntegration", description = "integration with EnderIO", modsRequired = "enderio")
public class IntegrationEnderIO {

    public static final Map<EntityEntry, SoulfulTrait> SOUL_TRAITS = new HashMap<>();

    @Subscribe
    public void postInit(FMLPostInitializationEvent event) {
        for(EntityEntry entry : GameData.getEntityRegistry().getValuesCollection())
            SOUL_TRAITS.put(entry, new SoulfulTrait(entry));
    }

    public static class CapturedMobRecipeMatch extends RecipeMatch {

        @GameRegistry.ObjectHolder("enderio:item_soul_vial")
        public static final ItemSoulVial SOUL_VIAL = null;

        private final ResourceLocation entity;

        public CapturedMobRecipeMatch(ResourceLocation entity) {
            super(1, 1);
            this.entity = entity;
        }

        @Override
        public List<ItemStack> getInputs() {
            return Collections.singletonList(SOUL_VIAL.createVesselWithEntityStub(this.entity));
        }

        @Override
        public Optional<Match> matches(@NotNull NonNullList<ItemStack> nonNullList) {
            for (ItemStack stack : nonNullList) {
                if (stack.getItem() instanceof ItemSoulVial
                        && CapturedMob.containsSoul(stack)
                        && CapturedMob.create(this.entity).isSameType(CapturedMob.create(stack)))
                    return Optional.of(new Match(Collections.singletonList(stack), 1));
            }
            return Optional.empty();
        }
    }
}
