package pl.net.eerie.tictweaks.traits.enderio;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.registry.EntityEntry;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;
import pl.net.eerie.tictweaks.TiCTweaks;
import pl.net.eerie.tictweaks.config.SoulTraitsConfig;
import pl.net.eerie.tictweaks.integration.IntegrationEnderIO;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;
import slimeknights.tconstruct.library.modifiers.TinkerGuiException;
import slimeknights.tconstruct.library.utils.TagUtil;

import java.util.Optional;

public class SoulfulTrait extends ModifierTrait {

    private final EntityEntry entity;

    private final IntegrationEnderIO.CapturedMobRecipeMatch recipeMatch;

    public SoulfulTrait(@NotNull EntityEntry entity) {
        super(String.format("soul_%s", entity.getRegistryName().getPath()), entity.getEgg().primaryColor);
        this.entity = entity;
        this.addAspects(new ModifierAspect.SingleAspect(this));
        this.addAspects(new ModifierAspect(this) {
            @Override
            public boolean canApply(ItemStack itemStack, ItemStack itemStack1) throws TinkerGuiException {
                return true;
            }

            @Override
            public void updateNBT(NBTTagCompound nbtTagCompound, NBTTagCompound nbtTagCompound1) {
               nbtTagCompound1.setString("soul", entity.getRegistryName().toString());
            }
        });
        this.recipeMatch = new IntegrationEnderIO.CapturedMobRecipeMatch(entity.getRegistryName());
    }

    @Override
    public Optional<RecipeMatch.Match> matches(NonNullList<ItemStack> stacks) {
        return this.recipeMatch.matches(stacks);
    }

    @Override
    public String getLocalizedName() {
        return I18n.format("eerietic.trait.kamui.name");
    }

    @Override
    public String getLocalizedDesc() {
        return I18n.format("eerietic.trait.kamui.desc");
    }

    @Override
    public String getTooltip(NBTTagCompound modifierTag, boolean detailed) {
        return super.getTooltip(modifierTag, detailed) + (detailed ? "\n" + entity.getName(): "");
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
        NBTTagList traits = TagUtil.getTraitsTagList(rootCompound);

        for(int i = 0; i < traits.tagCount(); ++i) {
            if (this.identifier.equals(traits.getStringTagAt(i))) {
                return;
            }
        }

        traits.appendTag(new NBTTagString(this.identifier));
        if (SoulTraitsConfig.entityTraits.containsKey(entity.getRegistryName().toString())) for (String str : SoulTraitsConfig.entityTraits.get(entity.getRegistryName().toString()))
            if (TinkerRegistry.getTrait(str) != null) traits.appendTag(new NBTTagString(str));
            else if (SoulTraitsConfig.logErrors) TiCTweaks.INSTANCE.log.log(Level.WARN, String.format("Trait %s does not exits", str));
        TagUtil.setTraitsTagList(rootCompound, traits);
    }
}
