package pl.net.eerie.tictweaks.traits.lycanitesmobs;

import com.lycanitesmobs.client.localisation.LanguageManager;
import com.lycanitesmobs.core.info.ElementInfo;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import pl.net.eerie.tictweaks.Utils;
import pl.net.eerie.tictweaks.config.ColorConfig;
import pl.net.eerie.tictweaks.integration.IntegrationLycanitesMobs;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;

import java.util.Optional;

public class ElementTrait extends ModifierTrait {

    private final ElementInfo info;
    private final RecipeMatch recipeMatch;

    public ElementTrait(@NotNull ElementInfo info) {
        super(String.format("elemental_%s", info.name ), Integer.parseInt(ColorConfig.elements.get(info.name), 16), 4, 1);
        this.info = info;
        this.recipeMatch = new IntegrationLycanitesMobs.ProjectileElementRecipeMatch(1, this.info);
    }

    @Override
    public Optional<RecipeMatch.Match> matches(NonNullList<ItemStack> stacks) {
        return this.recipeMatch.matches(stacks);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getLocalizedName() {
        return LanguageManager.translate(String.format("element.%s.name", info.name));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getLocalizedDesc() {
        return LanguageManager.translate(String.format("element.%s.description", info.name));
    }

    @Override
    public void onHit(ItemStack tool, @NotNull EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical) {
        if (isCritical && !player.world.isRemote) info.debuffEntity(target, (int) Math.floor(damage), Utils.getLevel(tool, this));
    }

    @Override
    public void onBlock(ItemStack tool, EntityPlayer player, @NotNull LivingHurtEvent event) {
        info.buffEntity(player, (int)Math.floor(event.getAmount()), Utils.getLevel(tool, this));
    }
}
