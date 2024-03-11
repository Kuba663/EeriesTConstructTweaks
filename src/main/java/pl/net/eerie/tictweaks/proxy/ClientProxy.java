package pl.net.eerie.tictweaks.proxy;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pl.net.eerie.tictweaks.TiCTweaks;
import slimeknights.tconstruct.common.ModelRegisterUtil;

@SideOnly(Side.CLIENT)
public class ClientProxy implements CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        if (Loader.isModLoaded("bloodmagic")) ModelRegisterUtil.registerModifierModel(TiCTweaks.INSTANCE.BLOOD_MAGIC_INTEGRATION.TRAIT_SENTIENT, new ResourceLocation(TiCTweaks.MOD_ID, "models/item/modifier/sentient"));
    }
}
