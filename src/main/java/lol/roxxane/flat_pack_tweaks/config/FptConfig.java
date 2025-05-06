package lol.roxxane.flat_pack_tweaks.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.simibubi.create.AllItems;
import lol.roxxane.flat_pack_tweaks.Fpt;
import lol.roxxane.flat_pack_tweaks.recipes.InfiniDrillingRecipe;
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

import java.util.List;

import static lol.roxxane.flat_pack_tweaks.FptUtils.*;
import static lol.roxxane.flat_pack_tweaks.config.FptParsing.*;
import static lol.roxxane.flat_pack_tweaks.config.FptValidating.*;

@Mod.EventBusSubscriber(modid = Fpt.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FptConfig {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

	public static final FptConfigValue<CommentedConfig, List<InfiniDrillingRecipe>> INFINI_DRILLING_RECIPES =
		FptConfigValue.of(BUILDER.define("infini_drilling_recipes", config(
			"minecraft:raw_iron_block",
					config("item", "minecraft:raw_iron", "hardness", 10.0),
					"minecraft:bedrock",
					config("generates_fire", true, "hardness", 20.0),
					"minecraft:obsidian",
					config("item", "minecraft:obsidian", "generates_fire", true, "hardness", 100.0)),
				o -> validate_map(o,
					m -> validate_entries(m, is_block::test, v -> validate_map(v,
						v_map -> validate_nullable_entry(v_map, "item", is_item) &&
							validate_nullable_entry(v_map, "generates_fire", is_bool) &&
							validate_entry(v_map, "hardness", is_double))))),
			o -> parse_map(o,
				m -> parse_entries(FptParsing.parse_entries(m, parse_block::apply, parse_map),
					(k, v) -> new InfiniDrillingRecipe(k,
						FptParsing.parse_optional_entry(v, "item", parse_item, Items.AIR),
						parse_entry(v, "hardness", parse_double),
						FptParsing.parse_optional_entry(v, "generates_fire", parse_bool, true)
			))));

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
			.defineInRange("water_wheel_speed_factor", 2, 0, Double.MAX_VALUE);

	public static final ForgeConfigSpec SPEC = BUILDER.build();

	@SubscribeEvent
	static void on_load(final ModConfigEvent event) {
		if (SPEC.isLoaded())
			for (var value : FptConfigValue.VALUES)
				value.invalidate();
	}

	// Infini-Drilling
	public static InfiniDrillingRecipe get_infini_drill_recipe(Block block) {
		for (var recipe : INFINI_DRILLING_RECIPES.get())
			if (recipe.block() == block)
				return recipe;
		return null;
	}
	public static boolean can_infini_drill(Block block) {
		return INFINI_DRILLING_RECIPES.get().stream().anyMatch(r -> r.block() == block);
	}

	// Superglues
	public static Item is_superglue_return_item(Item item) {
		if (item == SUPER_GLUE.get())
			return AllItems.SUPER_GLUE.get(); // success
		else return Items.AIR; // failure
	}
}
