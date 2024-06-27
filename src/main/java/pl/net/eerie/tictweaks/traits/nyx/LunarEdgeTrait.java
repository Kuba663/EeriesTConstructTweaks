package pl.net.eerie.tictweaks.traits.nyx;

import de.ellpeck.nyx.capabilities.NyxWorld;
import de.ellpeck.nyx.lunarevents.BloodMoon;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.jetbrains.annotations.NotNull;
import pl.net.eerie.tictweaks.Utils;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

import java.awt.*;
import java.util.Optional;

public class LunarEdgeTrait extends ModifierTrait {

    @GameRegistry.ObjectHolder("nyx:fallen_star")
    public static final Item star = null;

    private final RecipeMatch srtar_match = RecipeMatch.of(star);

    public LunarEdgeTrait(String identifier) {
        super(identifier, Color.YELLOW.getRGB(), 5, 1);
    }

    @Override
    public boolean isCriticalHit(ItemStack tool, EntityLivingBase player, @NotNull EntityLivingBase target) {
        return NyxWorld.get(target.world).currentEvent.getSkyColor() == 4328707;
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        float baseDamage = 1.25F + (float)Math.max(0, Utils.getLevel(tool, this) - 1) * 0.5F;
        return (NyxWorld.moonPhase * baseDamage) + damage;
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if (!(wasCritical && wasHit)) return;
        player.heal(damageDealt / 4);
        ToolHelper.healTool(tool, ((int)Math.ceil(damageDealt)) >> 2, player);
    }

    @Override
    public Optional<RecipeMatch.Match> matches(NonNullList<ItemStack> stacks) {
        return srtar_match.matches(stacks);
    }

    @Override
    public String getLocalizedName() {
        return I18n.format("eerietic.trait.lunar_edge.name");
    }

    @Override
    public String getLocalizedDesc() {
        return I18n.format("eerietic.trait.lunar_edge.desc");
    }
}
