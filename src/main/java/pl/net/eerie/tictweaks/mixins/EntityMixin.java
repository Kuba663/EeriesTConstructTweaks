package pl.net.eerie.tictweaks.mixins;

import com.google.common.base.Optional;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

import java.util.UUID;

@Mixin(EntityCreature.class)
@Implements({@Interface(iface = IEntityOwnable.class, prefix = "own$"), @Interface(iface = IEntityThrall.class, prefix = "thrall$")})
public class EntityMixin {

    protected static final DataParameter<Optional<UUID>> OWNER_ID = EntityDataManager.createKey(EntityCreature.class, DataSerializers.OPTIONAL_UNIQUE_ID);
    protected static final DataParameter<Boolean> IS_THRALL = EntityDataManager.createKey(EntityCreature.class, DataSerializers.BOOLEAN);

    @Shadow
    protected EntityDataManager dataManager;

    @Intrinsic
    public UUID own$getOwnerId() {
        return this.dataManager.get(OWNER_ID).orNull();
    }

    @Intrinsic
    public Entity own$getOwner() {
        try {
            UUID uuid = this.own$getOwnerId();
            return uuid == null ? null : this.getEntityWorld().getPlayerEntityByUUID(uuid);
        } catch (IllegalArgumentException var2) {
            return null;
        }
    }

    public boolean thrall$isEnthralled() {
        return this.dataManager.get(IS_THRALL);
    }

    public boolean thrall$isThrallOf(UUID player) {
        return this.thrall$isEnthralled() && this.own$getOwnerId() == player;
    }

    public void thrall$setItem(ItemStack stack) {
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, stack);
    }

    public ItemStack thrall$getItem() {
        return this.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);
    }

    public ItemStack thrall$getDeathItem() {
        ItemStack stack = this.thrall$getItem();
        NBTTagCompound tag = TagUtil.getToolTag(stack);
        tag.setBoolean("Broken", true);
        TagUtil.setToolTag(stack, tag);
        return stack;
    }

    @Shadow
    public World getEntityWorld() {
        return null;
    }

    @Shadow
    public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {}

    @Shadow
    public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn){
        return null;
    }

    @Inject(method = "heal(F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;setHealth(F)V"))
    private void healInjection(float healAmount, CallbackInfo info) {
        if (this.thrall$isEnthralled()) ToolHelper.healTool(this.thrall$getItem(), (int)Math.ceil(healAmount), null);
    }

    private void damageInjection() {

    }
}
