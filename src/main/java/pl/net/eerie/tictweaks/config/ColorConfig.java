package pl.net.eerie.tictweaks.config;

import com.google.common.collect.Maps;
import net.minecraftforge.common.config.Config;
import pl.net.eerie.tictweaks.TiCTweaks;

import java.util.Map;

@Config(modid = TiCTweaks.MOD_ID, name = "/eerietic/colors", category = "colors")
public class ColorConfig {

    @Config.RequiresMcRestart()
    public static Map<String, String> elements;

    static {
        elements = Maps.newHashMap();
        elements.put("acid", "00ff00");
        elements.put("aether", "f0ffff");
        elements.put("air", "ffffff");
        elements.put("arbour", "007c00");
        elements.put("arcane", "800080");
        elements.put("chaos", "000000");
        elements.put("earth", "a52a2a");
        elements.put("fae", "b67db6");
        elements.put("fire", "ff4d00");
        elements.put("frost", "ccf4f4");
        elements.put("lava", "df4300");
        elements.put("light", "fefff0");
        elements.put("lightning", "fefff0");
        elements.put("nether", "0f0000");
        elements.put("order", "ffffff");
        elements.put("phase", "630063");
        elements.put("poison", "00ff85");
        elements.put("quake", "525e58");
        elements.put("shadow", "000000");
        elements.put("void", "000000");
        elements.put("water", "0000ff");
    }
}
