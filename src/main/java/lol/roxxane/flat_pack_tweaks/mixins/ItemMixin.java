package lol.roxxane.flat_pack_tweaks.mixins;

import lol.roxxane.flat_pack_tweaks.config.FptConfig;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
abstract class ItemMixin {
	@Inject(method = "isFireResistant",
		cancellable = true,
		at = @At("RETURN"))
	private void isFireResistant_Inject(CallbackInfoReturnable<Boolean> cir) {
		if (FptConfig.ALL_ITEMS_ARE_FIREPROOF.get())
			cir.setReturnValue(true);
	}
}
