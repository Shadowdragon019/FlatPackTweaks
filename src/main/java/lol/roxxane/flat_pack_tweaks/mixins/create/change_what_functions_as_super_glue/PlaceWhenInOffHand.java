package lol.roxxane.flat_pack_tweaks.mixins.create.change_what_functions_as_super_glue;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.contraptions.glue.SuperGlueHandler;
import lol.roxxane.flat_pack_tweaks.config.server.FptServerConfig;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SuperGlueHandler.class)
abstract class PlaceWhenInOffHand {
	@ModifyExpressionValue(method = "glueInOffHandAppliesOnBlockPlace",
		remap = false,
		at = @At(value = "INVOKE", ordinal = 0,
			target = "Lcom/tterrag/registrate/util/entry/ItemEntry;isIn(Lnet/minecraft/world/item/ItemStack;)Z"))
	private static boolean fpt$glueInOffHandAppliesOnBlockPlace$ModifyExpressionValue(boolean original,
		@Local ItemStack stack
	) {
		return FptServerConfig.SUPER_GLUE.get() == stack.getItem();
	}
}
