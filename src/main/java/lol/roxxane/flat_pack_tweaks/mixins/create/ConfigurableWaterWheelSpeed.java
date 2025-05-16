package lol.roxxane.flat_pack_tweaks.mixins.create;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.simibubi.create.content.kinetics.waterwheel.WaterWheelBlockEntity;
import lol.roxxane.flat_pack_tweaks.config.FptServerConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WaterWheelBlockEntity.class)
abstract class ConfigurableWaterWheelSpeed {
	@ModifyReturnValue(method = "getGeneratedSpeed",
		remap = false,
		at = @At("RETURN"))
	private float fpt$getGeneratedSpeed$ModifyReturnValue(float original) {
		return (float) (original * FptServerConfig.WATER_WHEEL_SPEED_FACTOR.get());
	}
}
