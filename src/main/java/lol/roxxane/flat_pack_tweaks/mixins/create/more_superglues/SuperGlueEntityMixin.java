package lol.roxxane.flat_pack_tweaks.mixins.create.more_superglues;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.simibubi.create.content.contraptions.glue.SuperGlueEntity;
import lol.roxxane.flat_pack_tweaks.config.FptConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SuperGlueEntity.class)
abstract class SuperGlueEntityMixin {
	@ModifyExpressionValue(method = "getRequiredItems",
		remap = false,
		at = @At(value = "INVOKE",
			target = "Lcom/tterrag/registrate/util/entry/ItemEntry;get()Ljava/lang/Object;"))
	private Object fpt$getRequiredItems$ModifyExpressionValue(Object original) {
		return FptConfig.get_superglue();
	}
}
