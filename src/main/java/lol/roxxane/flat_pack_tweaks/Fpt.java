package lol.roxxane.flat_pack_tweaks;

import com.mojang.logging.LogUtils;
import com.simibubi.create.content.equipment.goggles.GogglesItem;
import com.simibubi.create.foundation.mixin.accessor.ServerLevelAccessor;
import com.tterrag.registrate.Registrate;
import lol.roxxane.flat_pack_tweaks.config.FptConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import static lol.roxxane.flat_pack_tweaks.config.FptConfig.ITEM_IN_FLUID_RECIPES;
import static lol.roxxane.flat_pack_tweaks.config.FptConfig.ITEM_TRANSFORMATION_PERIOD;

@Mod(Fpt.ID)
public class Fpt {
	public static final boolean DEBUG = false;
	public static final String ID = "flat_pack_tweaks";
	public static final Logger LOGGER = LogUtils.getLogger();
	public static final Registrate REGISTRATE = Registrate.create(ID);

	public static ResourceLocation resource(String path) {
		return ResourceLocation.fromNamespaceAndPath(ID, path);
	}

	public Fpt(FMLJavaModLoadingContext context) {
		context.registerConfig(ModConfig.Type.SERVER, FptConfig.SPEC);

		GogglesItem.addIsWearingPredicate($ -> true);

		MinecraftForge.EVENT_BUS.addListener((TickEvent.LevelTickEvent event) -> {
			if (event.level instanceof ServerLevelAccessor level && !event.level.isClientSide)
				level.create$getEntityTickList().forEach(entity -> {
					if (entity.isAlive() && entity instanceof ItemEntity item_entity)
						item_in_fluid_transformation(item_entity);
				});
		});
		
		// Lang
		REGISTRATE.addRawLang("gui.flat_pack_tweaks.category.infini_drilling", "Infini-Drilling");
	}

	public static void debug(Object o) {
		if (DEBUG)
			LOGGER.debug(o.toString());
	}

	public static void log(Object o) {
		LOGGER.info(o.toString());
	}

	private void item_in_fluid_transformation(ItemEntity item_entity) {
		var level = item_entity.level();
		var position = item_entity.position();
		var block_position = item_entity.blockPosition();
		var fluid_state = level.getFluidState(block_position);
		var stack = item_entity.getItem();
		var stack_count = stack.getCount();
		var delta = item_entity.getDeltaMovement();

		for (var recipe : ITEM_IN_FLUID_RECIPES.get()) {
			if (fluid_state.is(recipe.fluid()) && stack.is(recipe.item_input()) &&
				item_entity.tickCount % ITEM_TRANSFORMATION_PERIOD.get() == 0
			) {
				item_entity.setItem(stack.copyWithCount(stack_count - 1));
				level.addFreshEntity(new ItemEntity(level, position.x, position.y, position.z,
					recipe.item_output().getDefaultInstance(), delta.x, delta.y, delta.z));
				level.setBlock(block_position, Blocks.AIR.defaultBlockState(), 1 | 2);
			}
		}
	}
}