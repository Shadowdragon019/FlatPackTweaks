package lol.roxxane.flat_pack_tweaks.mixins.create.more_superglues;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.contraptions.glue.SuperGlueHandler;
import lol.roxxane.flat_pack_tweaks.config.FptConfig;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SuperGlueHandler.class)
abstract class SuperGlueHandlerMixin {
	@ModifyExpressionValue(method = "glueInOffHandAppliesOnBlockPlace",
		remap = false,
		at = @At(value = "INVOKE", ordinal = 0,
			target = "Lcom/tterrag/registrate/util/entry/ItemEntry;isIn(Lnet/minecraft/world/item/ItemStack;)Z"))
	private static boolean fpt$glueInOffHandAppliesOnBlockPlace$ModifyExpressionValue(boolean original,
		@Local ItemStack stack
	) {
		return FptConfig.is_superglue(stack.getItem());
	}
}
