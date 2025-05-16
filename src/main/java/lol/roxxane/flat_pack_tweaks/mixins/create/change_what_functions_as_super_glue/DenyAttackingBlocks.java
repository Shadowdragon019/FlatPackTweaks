package lol.roxxane.flat_pack_tweaks.mixins.create.change_what_functions_as_super_glue;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import lol.roxxane.flat_pack_tweaks.config.FptServerConfig;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Item.class)
abstract class DenyAttackingBlocks {
	// Could be done with a BlockBreak event but that leads to awkward flashing &
	// not immediate feedback you can't break the block
	@ModifyReturnValue(method = "canAttackBlock",
		at = @At("RETURN"))
	private boolean fpt$canAttackBlock$ModifyReturnValue(boolean original) {
		return FptServerConfig.SUPER_GLUE.get() != (Object) this && original;
	}
}
