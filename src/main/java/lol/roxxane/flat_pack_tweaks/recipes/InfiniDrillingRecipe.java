package lol.roxxane.flat_pack_tweaks.recipes;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public record InfiniDrillingRecipe(Block block, Item item, double hardness, boolean generates_fire) {}