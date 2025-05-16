package lol.roxxane.flat_pack_tweaks.mixins.create.change_what_functions_as_super_glue;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.simibubi.create.content.contraptions.glue.SuperGlueSelectionHelper;
import lol.roxxane.flat_pack_tweaks.config.FptServerConfig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SuperGlueSelectionHelper.class)
abstract class UpdateGlueCollectingGlueSelectionHelper {
	@ModifyExpressionValue(method = "collectGlueFromInventory",
		remap = false,
		at = @At(value = "INVOKE", remap = true,
			target = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;"))
	private static Item fpt$collectGlueFromInventory$ModifyExpressionValue$update_glue_check(Item item) {
		return FptServerConfig.is_superglue_return_item(item);
	}

	@Inject(method = "collectGlueFromInventory",
		remap = false, cancellable = true,
		at = @At(value = "HEAD"))
	private static void fpt$collectGlueFromInventory$Inject$skip_check_for_undamageable_items(Player $, int $1,
		boolean $2, CallbackInfoReturnable<Boolean> cir
	) {
		if (!FptServerConfig.SUPER_GLUE.get().canBeDepleted())
			cir.setReturnValue(true);
	}
}
