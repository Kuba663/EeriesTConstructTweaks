package pl.net.eerie.tictweaks;

import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;
import slimeknights.tconstruct.library.utils.ModifierTagHolder;

public class Utils {
    public static int getLevel(ItemStack stack, @NotNull ModifierTrait trait) {
        ModifierNBT nbt = ModifierNBT.readTag(ModifierTagHolder.getModifier(stack, trait.getModifierIdentifier()).tag);
        return nbt.level;
    }


}
