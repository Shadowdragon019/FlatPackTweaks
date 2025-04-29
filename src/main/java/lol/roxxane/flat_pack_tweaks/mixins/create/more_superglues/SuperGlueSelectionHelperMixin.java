package lol.roxxane.flat_pack_tweaks.mixins.create.more_superglues;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.simibubi.create.content.contraptions.glue.SuperGlueSelectionHelper;
import lol.roxxane.flat_pack_tweaks.config.FptConfig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SuperGlueSelectionHelper.class)
abstract class SuperGlueSelectionHelperMixin {
	@ModifyExpressionValue(method = "collectGlueFromInventory",
		remap = false,
		at = @At(value = "INVOKE", remap = true,
			target = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;"))
	private static Item fpt$collectGlueFromInventory$ModifyExpressionValue(Item item) {
		return FptConfig.is_superglue_return_item(item);
	}

	@Inject(method = "collectGlueFromInventory",
		remap = false, cancellable = true,
		at = @At(value = "HEAD"))
	private static void fpt$collectGlueFromInventory$Inject(Player $, int $1, boolean $2,
		CallbackInfoReturnable<Boolean> cir
	) {
		if (!FptConfig.SUPER_GLUE.get().canBeDepleted())
			cir.setReturnValue(true);
	}
}
