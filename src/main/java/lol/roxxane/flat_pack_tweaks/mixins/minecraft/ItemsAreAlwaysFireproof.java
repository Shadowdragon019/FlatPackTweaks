package lol.roxxane.flat_pack_tweaks.mixins.minecraft;

import lol.roxxane.flat_pack_tweaks.config.server.FptServerConfig;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
abstract class ItemsAreAlwaysFireproof {
	@Inject(method = "isFireResistant",
		cancellable = true,
		at = @At("RETURN"))
	private void fpt$isFireResistant$Inject(CallbackInfoReturnable<Boolean> cir) {
		if (FptServerConfig.ALL_ITEMS_ARE_FIREPROOF.get())
			cir.setReturnValue(true);
	}
}
