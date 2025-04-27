package lol.roxxane.flat_pack_tweaks;

import com.mojang.logging.LogUtils;
import com.tterrag.registrate.Registrate;
import lol.roxxane.flat_pack_tweaks.config.FptConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Fpt.ID)
public class Fpt {
    public static final String ID = "flat_pack_tweaks";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Registrate registrate = Registrate.create(ID);

    public static ResourceLocation resource(String path) {
        return ResourceLocation.fromNamespaceAndPath(ID, path);
    }

    public Fpt(FMLJavaModLoadingContext context) {
        context.registerConfig(ModConfig.Type.SERVER, FptConfig.SPEC);

        // Lang
        registrate.addRawLang("gui.flat_pack_tweaks.category.infini_drilling", "Infini-Drilling");
    }
}
