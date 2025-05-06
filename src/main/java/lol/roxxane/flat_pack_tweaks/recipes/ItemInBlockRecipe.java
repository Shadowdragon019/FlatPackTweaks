package lol.roxxane.flat_pack_tweaks.recipes;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public record ItemInBlockRecipe(Block block, Item item_in, Item item_out, boolean consume_block) {}
