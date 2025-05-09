package lol.roxxane.flat_pack_tweaks.recipes;

import net.minecraft.world.item.Item;

import java.util.List;

public record SwitchingRecipe(List<Item> items) {
	// Limit to 21 slots
	public SwitchingRecipe {
		if (items.size() > 21)
			throw new IllegalArgumentException("SwitchingRecipe contained more then 21 items");
	}
}
