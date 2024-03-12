package pl.net.eerie.tictweaks.mixins;

import net.minecraft.entity.IEntityOwnable;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public interface IEntityThrall extends IEntityOwnable {
    boolean isEnthralled();
    boolean isThrallOf(UUID player);
    void setItem(ItemStack stack);
    ItemStack getItem();
    ItemStack getDeathItem();
}
