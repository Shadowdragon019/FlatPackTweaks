package lol.roxxane.flat_pack_tweaks.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.simibubi.create.compat.jei.CreateJEI;
import lol.roxxane.flat_pack_tweaks.config.FptConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(CreateJEI.class)
abstract class CreateJeiMixin {
	@ModifyExpressionValue(method = "registerRecipes",
		remap = false,
		at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;toList()Ljava/util/List;"))
	private List<Object> registerRecipes_ModifyExpressionValue(List<Object> original) {
		if (FptConfig.REMOVE_TOOLBOX_RECIPES.get())
			return List.of();
		else return original;
	}
}
