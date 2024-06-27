package pl.net.eerie.tictweaks.traits.bloodmagic;

import WayofTime.bloodmagic.entity.mob.EntitySentientSpecter;
import WayofTime.bloodmagic.iface.ISentientTool;
import WayofTime.bloodmagic.item.soul.ItemMonsterSoul;
import WayofTime.bloodmagic.item.soul.ItemSentientSword;
import WayofTime.bloodmagic.soul.EnumDemonWillType;
import WayofTime.bloodmagic.soul.IDemonWill;
import WayofTime.bloodmagic.soul.IDemonWillWeapon;
import WayofTime.bloodmagic.soul.PlayerDemonWillHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import pl.net.eerie.tictweaks.TiCTweaks;
import pl.net.eerie.tictweaks.Utils;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;
import slimeknights.mantle.util.RecipeMatch;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SentientTrait extends ModifierTrait implements IDemonWillWeapon, ISentientTool {

    @GameRegistry.ObjectHolder("bloodmagic:soul_gem")
    public static final Item tartaric_gem = null;

    @GameRegistry.ObjectHolder("bloodmagic:monster_soul")
    public static final ItemMonsterSoul demon_will = null;

    private final RecipeMatch tartaricGemMatch = RecipeMatch.of(tartaric_gem);

    public SentientTrait(String id) {
        super(id, Color.CYAN.getRGB(), 7, 10);
    }

    @Override
    public Optional<RecipeMatch.Match> matches(NonNullList<ItemStack> nonNullList) {
        return tartaricGemMatch.matches(nonNullList);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getLocalizedName() {
        return I18n.format("eerietic.trait.sentient.name");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getLocalizedDesc() {
        return I18n.format("eerietic.trait.sentient.desc");
    }

    @Override
    public void afterHit(ItemStack tool, @NotNull EntityLivingBase attacker, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if (!attacker.world.isRemote) {
            if (!(wasHit && attacker instanceof EntityPlayer)) return;
            EntityPlayer player = (EntityPlayer) attacker;
            if (PlayerDemonWillHandler.isDemonWillFull(EnumDemonWillType.DEFAULT, player))
                player.inventory.addItemStackToInventory(this.getRandomDemonWillDrop(target, attacker, tool, 1).get(0));
            double willModifier = target instanceof EntitySlime ? 0.67 : 1.0;
            PlayerDemonWillHandler.addDemonWill(EnumDemonWillType.DEFAULT, player, willModifier * ItemSentientSword.soulDrop[Utils.getLevel(tool, this) - 1]);
        }
    }

    @Override
    public List<ItemStack> getRandomDemonWillDrop(@NotNull EntityLivingBase killedEntity, EntityLivingBase attackingEntity, ItemStack stack, int looting) {
        List<ItemStack> soulList = new ArrayList<ItemStack>();
        if (killedEntity.getEntityWorld().getDifficulty() == EnumDifficulty.PEACEFUL || killedEntity instanceof IMob) {
            double willModifier = killedEntity instanceof EntitySlime ? 0.67 : 1.0;

            ItemStack soulStack = ((IDemonWill) demon_will).createWill(EnumDemonWillType.DEFAULT.ordinal(), willModifier * (ItemSentientSword.soulDrop[Utils.getLevel(stack, this) - 1] * attackingEntity.getEntityWorld().rand.nextDouble() + ItemSentientSword.soulDrop[Utils.getLevel(stack, this) - 1]) * (double) killedEntity.getMaxHealth() / 20.0);
            soulList.add(soulStack);

        }
        return soulList;
    }

    @Override
    public boolean spawnSentientEntityOnDrop(ItemStack itemStack, @NotNull EntityPlayer entityPlayer) {
        World world = entityPlayer.getEntityWorld();
        if (!world.isRemote) {
            EnumDemonWillType type = EnumDemonWillType.DEFAULT;
            double soulsRemaining = PlayerDemonWillHandler.getTotalDemonWill(type, entityPlayer);
            if (soulsRemaining < 1024.0) {
                return false;
            } else {
                PlayerDemonWillHandler.consumeDemonWill(type, entityPlayer, 100.0);
                EntitySentientSpecter specterEntity = new EntitySentientSpecter(world);
                specterEntity.setPosition(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ);
                world.spawnEntity(specterEntity);
                specterEntity.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, itemStack.copy());
                specterEntity.setType(type);
                specterEntity.setOwner(entityPlayer);
                specterEntity.setTamed(true);
                return true;
            }
        } else {
            return false;
        }
    }


}
