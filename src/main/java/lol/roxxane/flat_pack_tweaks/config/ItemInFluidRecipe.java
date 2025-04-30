package lol.roxxane.flat_pack_tweaks.config;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;

import static lol.roxxane.flat_pack_tweaks.config.FptConfigUtil.*;

public record ItemInFluidRecipe(Item item_input, Fluid fluid, Item item_output) {
	public static ItemInFluidRecipe of(Item item_input, Fluid fluid, Item item_output) {
		return new ItemInFluidRecipe(item_input, fluid, item_output);
	}

	public static ItemInFluidRecipe from_config(Object o) {
		return parse_map(o, map -> of(
			parse_entry(map, "item_input", FptConfigUtil::get_item),
			parse_entry(map, "fluid_input", FptConfigUtil::get_fluid),
			parse_entry(map, "item_output", FptConfigUtil::get_item)
		));
	}
	public static boolean validate(Object o) {
		return validate_required_entries(o,
			EntryPredicate.of("item_input", FptConfigUtil::item_exists),
			EntryPredicate.of("fluid_input", FptConfigUtil::fluid_exists),
			EntryPredicate.of("item_output", FptConfigUtil::item_exists));
	}
}
