package lol.roxxane.flat_pack_tweaks.mixins.create.more_superglues;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.simibubi.create.content.contraptions.glue.SuperGlueEntity;
import com.simibubi.create.content.schematics.requirement.ItemRequirement;
import lol.roxxane.flat_pack_tweaks.config.FptConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SuperGlueEntity.class)
abstract class SuperGlueEntityMixin {
	@ModifyReturnValue(method = "getRequiredItems",
		remap = false,
		at = @At("RETURN"))
	private ItemRequirement fpt$getRequiredItems$ModifyReturnValue(ItemRequirement original) {
		return new ItemRequirement(FptConfig.SUPER_GLUE.get().canBeDepleted() ?
			ItemRequirement.ItemUseType.DAMAGE : ItemRequirement.ItemUseType.CONSUME,
			FptConfig.SUPER_GLUE.get());
	}
}
