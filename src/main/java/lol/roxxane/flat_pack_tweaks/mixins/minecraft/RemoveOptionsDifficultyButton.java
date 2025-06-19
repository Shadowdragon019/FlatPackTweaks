package lol.roxxane.flat_pack_tweaks.mixins.minecraft;

import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.OptionsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(OptionsScreen.class)
abstract class RemoveOptionsDifficultyButton {
	/** Remove difficulty button from options screen */
	@Redirect(method = "createOnlineButton",
		at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/gui/layouts/LinearLayout;addChild(Lnet/minecraft/client/gui/layouts/LayoutElement;)Lnet/minecraft/client/gui/layouts/LayoutElement;"))
	private LayoutElement fpt$Redirect$createOnlineButton(LinearLayout instance, LayoutElement pChild) {
		return null;
	}
}
