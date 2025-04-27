package lol.roxxane.flat_pack_tweaks.mixins.create.more_superglues;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.simibubi.create.content.contraptions.glue.SuperGlueItem;
import lol.roxxane.flat_pack_tweaks.config.FptConfig;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SuperGlueItem.class)
abstract class SuperGlueItemMixin {
	@ModifyExpressionValue(method = "glueItemAlwaysPlacesWhenUsed",
		remap = false,
		at = @At(value = "INVOKE", remap = true,
			target = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;"))
	private static Item fpt$glueItemAlwaysPlacesWhenUsed$ModifyExpressionValue(Item item) {
		return FptConfig.is_superglue_return_item(item);
	}
}
