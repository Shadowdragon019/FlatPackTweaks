package lol.roxxane.flat_pack_tweaks.mixins.minecraft.everything_is_minable;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(net.minecraft.world.item.DiggerItem.class)
abstract class DiggerItem {
	@ModifyReturnValue(method = "isCorrectToolForDrops*",
		at = @At("RETURN"))
	private static boolean fpt$isCorrectToolForDrops$ModifyReturnValue(boolean original) {
		return true;
	}
}
