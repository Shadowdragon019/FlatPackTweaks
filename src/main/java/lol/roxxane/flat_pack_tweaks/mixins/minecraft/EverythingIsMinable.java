package lol.roxxane.flat_pack_tweaks.mixins.minecraft;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraftforge.common.TierSortingRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TierSortingRegistry.class)
abstract class EverythingIsMinable {
	@ModifyReturnValue(method = "isCorrectTierForDrops",
		remap = false,
		at = @At("RETURN"))
	private static boolean ee(boolean original) {
		return true;
	}
}
