package lol.roxxane.flat_pack_tweaks.recipes;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;

import static lol.roxxane.flat_pack_tweaks.config.FptParsing.*;
import static lol.roxxane.flat_pack_tweaks.config.FptValidating.*;

public record ItemInFluidRecipe(Item item_input, Fluid fluid, Item item_output) {
	public static ItemInFluidRecipe of(Item item_input, Fluid fluid, Item item_output) {
		return new ItemInFluidRecipe(item_input, fluid, item_output);
	}

	public static ItemInFluidRecipe from_config(Object o) {
		return parse_map(o, map -> of(
			parse_entry(map, "item_input", parse_item),
			parse_entry(map, "fluid_input", parse_fluid),
			parse_entry(map, "item_output", parse_item)
		));
	}
	public static boolean validate(Object o) {
		return validate_map(o, m ->
			validate_entry(m, "item_input", is_item) &&
			validate_entry(m, "fluid_input", is_fluid) &&
			validate_entry(m, "item_output", is_item));
	}
}
