package lol.roxxane.flat_pack_tweaks;

import com.mojang.logging.LogUtils;
import com.simibubi.create.content.equipment.goggles.GogglesItem;
import com.simibubi.create.foundation.mixin.accessor.ServerLevelAccessor;
import com.tterrag.registrate.Registrate;
import lol.roxxane.flat_pack_tweaks.config.client.FptClientConfig;
import lol.roxxane.flat_pack_tweaks.config.server.FptServerConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Fpt.ID)
public final class Fpt {
	public static final String ID = "flat_pack_tweaks";
	public static final Logger LOGGER = LogUtils.getLogger();
	public static final Registrate REGISTRATE = Registrate.create(ID);

	public static ResourceLocation resource(String path) {
		return ResourceLocation.fromNamespaceAndPath(ID, path);
	}

	public Fpt(FMLJavaModLoadingContext context) {
		context.registerConfig(ModConfig.Type.SERVER, FptServerConfig.SPEC);
		context.registerConfig(ModConfig.Type.CLIENT, FptClientConfig.SPEC);

		GogglesItem.addIsWearingPredicate($ -> true);

		MinecraftForge.EVENT_BUS.addListener((TickEvent.LevelTickEvent event) -> {
			var level = event.level;

			if (!level.isClientSide && level instanceof ServerLevelAccessor level_with_entity_tick_list)
				level_with_entity_tick_list.create$getEntityTickList().forEach(entity -> {
					if (entity.isAlive() && entity instanceof ItemEntity item_entity)
						item_in_block_transformation(item_entity);
				});
		});

		MinecraftForge.EVENT_BUS.addListener((ItemTooltipEvent event) -> {
			for (var entry : FptClientConfig.TOOLTIPS.get().entrySet())
				if (event.getItemStack().getItem() == entry.getKey().asItem())
					event.getToolTip().addAll(entry.getValue());
		});
		
		// Lang
		REGISTRATE.addRawLang("gui.flat_pack_tweaks.category.infini_drilling", "Infini-Drilling");
		REGISTRATE.addRawLang("gui.flat_pack_tweaks.category.switching", "Switching");
	}

	@SuppressWarnings("unused")
	public static void log(Object o) {
		LOGGER.info(o.toString());
	}

	private void item_in_block_transformation(ItemEntity item_entity) {
		var level = item_entity.level();
		var position = item_entity.position();
		var block_position = item_entity.blockPosition();
		var block = level.getBlockState(block_position).getBlock();
		var stack = item_entity.getItem();
		var stack_count = stack.getCount();
		var delta = item_entity.getDeltaMovement();
		var recipe = FptServerConfig.get_item_in_block_recipe(block, item_entity.getItem().getItem());

		if (recipe != null) {
			item_entity.setItem(stack.copyWithCount(stack_count - 1));
			level.addFreshEntity(new ItemEntity(level, position.x, position.y, position.z,
				recipe.item_out().getDefaultInstance(), delta.x, delta.y, delta.z));
			if (recipe.consume_block())
				level.setBlockAndUpdate(block_position, Blocks.AIR.defaultBlockState());
		}
	}
}