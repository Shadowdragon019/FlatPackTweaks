package lol.roxxane.flat_pack_tweaks.recipes;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public record InfiniDrillingRecipe(Block block, Item item, boolean generate_fire) {
	public InfiniDrillingRecipe(Block block, Item item) {
		this(block, item, false);
	}
}