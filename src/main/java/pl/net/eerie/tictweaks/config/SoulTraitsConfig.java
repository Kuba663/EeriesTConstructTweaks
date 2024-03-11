package pl.net.eerie.tictweaks.config;

import com.google.common.collect.Maps;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config;
import pl.net.eerie.tictweaks.TiCTweaks;

import java.util.Map;

@Config(modid = TiCTweaks.MOD_ID, name = "SoulTraits")
public class SoulTraitsConfig {

    public static boolean logErrors = true;

    public static Map<ResourceLocation, String[]> entityTraits;

    static {
        entityTraits = Maps.newHashMap();
        entityTraits.put(EntityList.getKey(EntityBlaze.class), new String[]{"flammable", "hellish"});
    }
}
