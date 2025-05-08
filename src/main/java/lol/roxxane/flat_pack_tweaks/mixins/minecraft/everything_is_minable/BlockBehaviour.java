package lol.roxxane.flat_pack_tweaks.mixins.minecraft.everything_is_minable;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Properties.class)
abstract class BlockBehaviour {
	@Shadow boolean requiresCorrectToolForDrops;

	@Inject(method = "requiresCorrectToolForDrops",
		at = @At("RETURN"))
	private void fpt$requiresCorrectToolForDrops$Inject(CallbackInfoReturnable<Properties> cir) {
		requiresCorrectToolForDrops = false;
	}
}