package pl.net.eerie.tictweaks.proxy;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pl.net.eerie.tictweaks.TiCTweaks;
import pl.net.eerie.tictweaks.book.ModifiersSectionTransformer;
import slimeknights.tconstruct.common.ModelRegisterUtil;
import slimeknights.tconstruct.library.book.TinkerBook;

@SideOnly(Side.CLIENT)
public class ClientProxy implements CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Override
    public void init(FMLInitializationEvent event) {
        TinkerBook.INSTANCE.addTransformer(new ModifiersSectionTransformer());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
    }
}
