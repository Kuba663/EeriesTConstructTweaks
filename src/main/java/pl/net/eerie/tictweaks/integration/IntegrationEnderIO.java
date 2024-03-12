package pl.net.eerie.tictweaks.integration;


import crazypants.enderio.base.item.soulvial.ItemSoulVial;
import crazypants.enderio.util.CapturedMob;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.util.RecipeMatch;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class IntegrationEnderIO {
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
