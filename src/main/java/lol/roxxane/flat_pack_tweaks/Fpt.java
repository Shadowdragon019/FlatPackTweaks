package lol.roxxane.flat_pack_tweaks;

import com.mojang.logging.LogUtils;
import com.simibubi.create.content.equipment.goggles.GogglesItem;
import com.simibubi.create.foundation.mixin.accessor.ServerLevelAccessor;
import com.tterrag.registrate.Registrate;
import lol.roxxane.flat_pack_tweaks.config.FptConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import snownee.lychee.RecipeTypes;

import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

@Mod(Fpt.ID)
public class Fpt {
	public static final String ID = "flat_pack_tweaks";
	public static final Logger LOGGER = LogUtils.getLogger();
	public static final Registrate registrate = Registrate.create(ID);

	public static ResourceLocation resource(String path) {
		return ResourceLocation.fromNamespaceAndPath(ID, path);
	}

	@SuppressWarnings("resource")
	public Fpt(FMLJavaModLoadingContext context) {
		context.registerConfig(ModConfig.Type.SERVER, FptConfig.SPEC);

		GogglesItem.addIsWearingPredicate($ -> true);

		EVENT_BUS.addListener((TickEvent.LevelTickEvent event) -> {
			if (event.level instanceof ServerLevelAccessor level) {
				level.create$getEntityTickList().forEach(entity -> {
					if (!RecipeTypes.ITEM_INSIDE.isEmpty() && entity.isAlive() &&
						entity instanceof ItemEntity item_entity &&
						!entity.level().isClientSide && entity.tickCount % 20 != 10
					)
						RecipeTypes.ITEM_INSIDE.process(entity, item_entity.getItem(), entity.blockPosition(),
							entity.position());
				});
			}
		});



		// Lang
		registrate.addRawLang("gui.flat_pack_tweaks.category.infini_drilling", "Infini-Drilling");
	}
}
