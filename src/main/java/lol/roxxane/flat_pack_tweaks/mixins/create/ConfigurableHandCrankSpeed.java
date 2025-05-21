package lol.roxxane.flat_pack_tweaks.mixins.create;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.simibubi.create.content.kinetics.crank.HandCrankBlock;
import lol.roxxane.flat_pack_tweaks.config.server.FptServerConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HandCrankBlock.class)
abstract class ConfigurableHandCrankSpeed {
	@ModifyReturnValue(method = "getRotationSpeed",
		remap = false,
		at = @At("RETURN"))
	private int fpt$getRotationSpeed$ModifyReturnValue(int original) {
		return FptServerConfig.HAND_CRANK_ROTATION_SPEED.get();
	}
}
