package lol.roxxane.flat_pack_tweaks.mixins.create.change_what_functions_as_super_glue;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.contraptions.chassis.AbstractChassisBlock;
import lol.roxxane.flat_pack_tweaks.config.FptConfig;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractChassisBlock.class)
abstract class MakeChassisSticky {
	@ModifyExpressionValue(method = "use",
		at = @At(value = "INVOKE", remap = false,
			target = "Lcom/tterrag/registrate/util/entry/ItemEntry;isIn(Lnet/minecraft/world/item/ItemStack;)Z"))
	private boolean fpt$use$ModifyExpressionValue(boolean value, @Local ItemStack stack) {
		return FptConfig.SUPER_GLUE.get() == stack.getItem();
	}
}