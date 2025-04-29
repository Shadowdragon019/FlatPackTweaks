package lol.roxxane.flat_pack_tweaks.mixins.lychee.remove_builder_mode;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import snownee.kiwi.customization.builder.BuildersButton;

@Mixin(BuildersButton.class)
abstract class BuildersButtonMixin {
	@Inject(method = "onLongPress",
		remap = false, cancellable = true,
		at = @At("HEAD"))
	private static void fpt$onLongPress$Inject(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(false);
	}
}
