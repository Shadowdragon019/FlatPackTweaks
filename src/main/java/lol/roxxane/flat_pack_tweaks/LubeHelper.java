package lol.roxxane.flat_pack_tweaks;

import net.minecraft.world.item.crafting.Recipe;

public class LubeHelper {
	public static boolean requires_lube(Recipe<?> recipe) {
		return recipe.getId().getPath().endsWith("requires_lube");
	}

	public static boolean requires_dry(Recipe<?> recipe) {
		return recipe.getId().getPath().endsWith("requires_dry");
	}
}
