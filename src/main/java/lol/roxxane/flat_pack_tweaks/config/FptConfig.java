package lol.roxxane.flat_pack_tweaks.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.simibubi.create.AllItems;
import lol.roxxane.flat_pack_tweaks.Fpt;
import lol.roxxane.flat_pack_tweaks.recipes.InfiniDrillingRecipe;
import lol.roxxane.flat_pack_tweaks.recipes.ItemInFluidRecipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import javax.annotation.Nullable;
import java.util.List;

import static lol.roxxane.flat_pack_tweaks.FptUtils.config;
import static lol.roxxane.flat_pack_tweaks.FptUtils.mutable_list;
import static lol.roxxane.flat_pack_tweaks.config.FptParsing.*;
import static lol.roxxane.flat_pack_tweaks.config.FptValidating.*;

@Mod.EventBusSubscriber(modid = Fpt.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FptConfig {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

	public static final FptConfigValue<CommentedConfig, List<InfiniDrillingRecipe>> INFINI_DRILLING_RECIPES =
		FptConfigValue.of(BUILDER.define("infini_drilling_recipes2",
				config("minecraft:raw_iron_block", "minecraft:raw_iron",
					"minecraft:bedrock", config("item", "minecraft:air", "generates_fire", true),
					"minecraft:obsidian", config("item", "minecraft:obsidian")),
				o -> validate_map(o,
					m -> validate_entries(m, is_block::test, v -> validate_item(v) || validate_map(v,
						m2 -> validate_entry(m2, "item", is_item)
							&& validate_nullable_entry(m2, "generates_fire", is_bool))))),
			o -> parse_map(o,
				m -> parse_entries(m,
					(k, v) -> {
					if (v instanceof String) return new InfiniDrillingRecipe(parse_block(k), parse_item(v));
					else {
						var v_map = parse_map(v);
						return new InfiniDrillingRecipe(parse_block(k), parse_entry(v_map, "item", parse_item),
							parse_entry(v_map, "generates_fire", parse_bool, true));
					}}
				)));

	public static final FptConfigValue<List<CommentedConfig>, List<ItemInFluidRecipe>>
		ITEM_IN_FLUID_RECIPES = FptConfigValue.of(BUILDER.define("item_in_fluid_recipes",
			mutable_list(config(
				"item_input", "minecraft:raw_iron",
				"fluid_input", "minecraft:water",
				"item_output", "minecraft:dirt")),
			o -> validate_iterable(o, i -> validate_elements(i, ItemInFluidRecipe::validate))),
		o -> parse_list(o, l -> parse_elements(l, ItemInFluidRecipe::from_config)));

	public static final FptConfigValue<String, Item> SUPER_GLUE = FptConfigValue.of(
		BUILDER.define("super_glue", "create:super_glue", is_item), parse_item::apply);

	public static final BooleanValue REMOVE_TOOLBOX_RECIPES_FROM_JEI =
		BUILDER.define("remove_toolbox_recipes_from_jei", true);
	public static final BooleanValue WRENCH_CAN_PICKUP_ANYTHING_DESTRUCTIBLE =
		BUILDER.define("wrench_can_pickup_anything_destructible", true);
	public static final BooleanValue ALL_ITEMS_ARE_FIREPROOF =
		BUILDER.define("all_items_are_fireproof", true);
	public static final IntValue HAND_CRANK_ROTATION_SPEED =
		BUILDER.defineInRange("hand_crank_rotation_speed",
			32, Integer.MIN_VALUE, Integer.MAX_VALUE);
	public static final DoubleValue WATER_WHEEL_SPEED_FACTOR =
		BUILDER.comment("By default, water wheels have 8 speed & large water wheels have 4 speed")
			.defineInRange("water_wheel_speed_factor", 1, 0, Double.MAX_VALUE);
	public static final IntValue ITEM_TRANSFORMATION_PERIOD =
		BUILDER.defineInRange("item_transformation_period", 1, 1, Integer.MAX_VALUE);

	public static final ForgeConfigSpec SPEC = BUILDER.build();

	@SubscribeEvent
	static void on_load(final ModConfigEvent event) {
		if (SPEC.isLoaded())
			// Invalidate caches
			for (var value : FptConfigValue.VALUES)
				value.cached_value = null;
	}

	// Infini-Drilling
	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public static boolean can_infini_drill(Block block) {
		for (var recipe : INFINI_DRILLING_RECIPES.get())
			if (recipe.block() == block)
				return true;
		return false;
	}
	public static @Nullable Item get_infini_drilling_result(Block block) {
		for (var recipe : INFINI_DRILLING_RECIPES.get())
			if (recipe.block() == block)
				return recipe.item();
		return null;
	}

	// Superglues
	public static Item is_superglue_return_item(Item item) {
		if (item == SUPER_GLUE.get())
			return AllItems.SUPER_GLUE.get(); // success
		else return Items.AIR; // failure
	}
}
