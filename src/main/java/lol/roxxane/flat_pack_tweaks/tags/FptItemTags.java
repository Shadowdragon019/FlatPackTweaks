package lol.roxxane.flat_pack_tweaks.tags;

import lol.roxxane.flat_pack_tweaks.Fpt;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class FptItemTags {
	public static final TagKey<Item> LUBE =
		ItemTags.create(ResourceLocation.fromNamespaceAndPath(Fpt.ID, "lube"));
}
