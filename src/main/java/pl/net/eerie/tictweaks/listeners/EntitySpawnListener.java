package pl.net.eerie.tictweaks.listeners;

import com.google.common.collect.Lists;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import pl.net.eerie.tictweaks.TiCTweaks;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tinkering.TinkersItem;
import slimeknights.tconstruct.tools.TinkerMaterials;
import slimeknights.tconstruct.tools.melee.TinkerMeleeWeapons;

import java.util.concurrent.ThreadLocalRandom;


@Mod.EventBusSubscriber(modid = TiCTweaks.MOD_ID)
public class EntitySpawnListener {
    private static final TinkersItem[] SWORDS = {TinkerMeleeWeapons.longSword, TinkerMeleeWeapons.broadSword, TinkerMeleeWeapons.rapier};

    private static final Material[] MAT_COMMON = {TinkerMaterials.wood, TinkerMaterials.bone, TinkerMaterials.stone, TinkerMaterials.paper};
    private static final Material[] MAT_UNCOMMON = {TinkerMaterials.slime, TinkerMaterials.cactus, TinkerMaterials.flint, TinkerMaterials.iron, TinkerMaterials.copper};
    private static final Material[] MAT_RARE = {TinkerMaterials.bronze, TinkerMaterials.cobalt, TinkerMaterials.steel, TinkerMaterials.pigiron, TinkerMaterials.electrum, TinkerMaterials.silver};
    private static final Material[] MAT_VRARE = {TinkerMaterials.obsidian, TinkerMaterials.blaze, TinkerMaterials.netherrack, TinkerMaterials.prismarine};

    @SubscribeEvent
    public static void onSpawn(@NotNull LivingSpawnEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity instanceof EntityPlayer) return;
        Item item = entity.getActiveItemStack().getItem();
        if (item instanceof ItemSword) {
            TinkersItem newItem = SWORDS[ThreadLocalRandom.current().nextInt(3)];
            ItemStack newStack = newItem.buildItem(Lists.newArrayList(randomMaterial(), randomMaterial(), randomMaterial()));
            entity.setHeldItem(entity.getActiveHand(), newStack);
        }
    }

    private static Material randomMaterial() {
        int rarity = ThreadLocalRandom.current().nextInt(101);
        if (rarity <= 43) return MAT_COMMON[ThreadLocalRandom.current().nextInt(4)];
        else if (rarity <= 75) return MAT_UNCOMMON[ThreadLocalRandom.current().nextInt(6)];
        else if (rarity <= 99) return MAT_RARE[ThreadLocalRandom.current().nextInt(4)];
        else return MAT_VRARE[ThreadLocalRandom.current().nextInt(4)];
    }
}
