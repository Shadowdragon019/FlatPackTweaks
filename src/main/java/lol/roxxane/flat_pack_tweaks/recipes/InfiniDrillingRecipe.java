package lol.roxxane.flat_pack_tweaks.recipes;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

public record InfiniDrillingRecipe(Block block, Item item, double hardness, boolean generates_fire) {
	public boolean block_is_empty() {
		return block.defaultBlockState().isAir();
	}

	public boolean item_is_empty() {
		return item() == Items.AIR;
	}
}