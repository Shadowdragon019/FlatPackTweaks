package lol.roxxane.flat_pack_tweaks.mixins.create;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.compat.jei.CreateJEI;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(value = CreateJEI.class, remap = false)
abstract class ChangeSequenceAssemblyIcon {
	@Redirect(method = "loadCategories",
		slice = @Slice(
			from = @At(value = "FIELD",
				target = "Lcom/simibubi/create/AllRecipeTypes;SEQUENCED_ASSEMBLY:Lcom/simibubi/create/AllRecipeTypes;"),
			to = @At(value = "FIELD",
				target = "Lcom/simibubi/create/AllBlocks;PECULIAR_BELL:Lcom/tterrag/registrate/util/entry/BlockEntry;")),
		at = @At(value = "INVOKE",
			target = "Lcom/tterrag/registrate/util/entry/ItemEntry;get()Ljava/lang/Object;")
	)
	private Object fpt$loadCategories$Redirect(ItemEntry<Item> instance) {
		return AllBlocks.ANDESITE_CASING.get();
	}
}
