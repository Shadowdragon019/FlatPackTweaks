package lol.roxxane.flat_pack_tweaks.mixins.create.change_what_functions_as_super_glue;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.contraptions.glue.SuperGlueSelectionHandler;
import lol.roxxane.flat_pack_tweaks.config.server.FptServerConfig;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SuperGlueSelectionHandler.class)
abstract class UpdateIsGlueSelectionHandler {
	@ModifyReturnValue(method = "isGlue",
		remap = false,
		at = @At("RETURN"))
	private boolean fpt$isGlue$ModifyReturnValue(boolean original,
		@Local(argsOnly = true) ItemStack stack
	) {
		return FptServerConfig.SUPER_GLUE.get() == stack.getItem();
	}
}
