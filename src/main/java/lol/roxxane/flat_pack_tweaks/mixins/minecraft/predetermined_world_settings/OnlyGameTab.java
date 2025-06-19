package lol.roxxane.flat_pack_tweaks.mixins.minecraft.predetermined_world_settings;

import net.minecraft.client.gui.components.tabs.Tab;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CreateWorldScreen.class)
abstract class OnlyGameTab {
	/** Remove other option tabs */
	@ModifyArg(method = "init",
		at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/gui/components/tabs/TabNavigationBar$Builder;addTabs([Lnet/minecraft/client/gui/components/tabs/Tab;)Lnet/minecraft/client/gui/components/tabs/TabNavigationBar$Builder;"))
	private Tab[] fpt$ModifyArg$init(Tab[] tabs) {
		return new Tab[]{tabs[0]};
	}
}