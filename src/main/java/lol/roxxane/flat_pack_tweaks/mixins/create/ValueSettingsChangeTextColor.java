package lol.roxxane.flat_pack_tweaks.mixins.create;

import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsScreen;
import lol.roxxane.flat_pack_tweaks.config.FptClientConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ValueSettingsScreen.class)
abstract class ValueSettingsChangeTextColor {
	@ModifyConstant(method = "renderWindow",
		remap = false,
		constant = @Constant(intValue = 0x442000))
	private int fpt$renderWindow$ModifyConstant(int constant) {
		return FptClientConfig.VALUE_SETTINGS_DARK_COLOR.get();
	}
}
