package lol.roxxane.flat_pack_tweaks.mixins.minecraft.predetermined_world_settings;

import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.layouts.LayoutSettings;
import net.minecraft.client.gui.screens.worldselection.WorldCreationUiState;
import net.minecraft.client.gui.screens.worldselection.WorldCreationUiState.SelectedGameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Consumer;

@Mixin(targets = "net.minecraft.client.gui.screens.worldselection.CreateWorldScreen$GameTab")
abstract class RemoveDifficultyGameTab {
	/** Remove difficulty button from create world screen */
	@Redirect(method = "<init>",
		at = @At(value = "INVOKE",
			ordinal = 4,
			target = "Lnet/minecraft/client/gui/layouts/GridLayout$RowHelper;addChild(Lnet/minecraft/client/gui/layouts/LayoutElement;Lnet/minecraft/client/gui/layouts/LayoutSettings;)Lnet/minecraft/client/gui/layouts/LayoutElement;"))
	private LayoutElement fpt$Redirect$init(GridLayout.RowHelper $, LayoutElement $1, LayoutSettings $2) {
		return null;
	}

	/** Remove difficulty button from create world screen */
	@Redirect(method = "<init>",
		at = @At(value = "INVOKE",
			ordinal = 2,
			target = "Lnet/minecraft/client/gui/screens/worldselection/WorldCreationUiState;addListener(Ljava/util/function/Consumer;)V"))
	private void fpt$Redirect$init(WorldCreationUiState $, Consumer<WorldCreationUiState> $1) {}

	/** Only allow Creative & Survival game modes on create world screen */
	@ModifyArg(method = "<init>",
		at = @At(value = "INVOKE",
			ordinal = 0,
			target = "Lnet/minecraft/client/gui/components/CycleButton$Builder;withValues([Ljava/lang/Object;)Lnet/minecraft/client/gui/components/CycleButton$Builder;"))
	private Object[] fpt$ModifyArg$init(Object[] $) {
		return new SelectedGameMode[]{SelectedGameMode.SURVIVAL, SelectedGameMode.CREATIVE};
	}
}