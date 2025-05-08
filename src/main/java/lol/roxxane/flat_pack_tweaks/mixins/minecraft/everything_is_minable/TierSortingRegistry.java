package lol.roxxane.flat_pack_tweaks.mixins.minecraft.everything_is_minable;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(net.minecraftforge.common.TierSortingRegistry.class)
abstract class TierSortingRegistry {
	@ModifyReturnValue(method = "isCorrectTierForDrops",
		remap = false,
		at = @At("RETURN"))
	private static boolean fpt$isCorrectTierForDrops$ModifyReturnValue(boolean original) {
		return true;
	}
}
