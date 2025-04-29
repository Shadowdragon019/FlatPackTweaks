package lol.roxxane.flat_pack_tweaks.mixins.create;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.equipment.wrench.WrenchItem;
import lol.roxxane.flat_pack_tweaks.config.FptConfig;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WrenchItem.class)
abstract class WrenchAnyDestructibleItem {
	@ModifyExpressionValue(method = "useOn", at = @At(value = "INVOKE", remap = false,
		target = "Lcom/simibubi/create/content/equipment/wrench/WrenchItem;canWrenchPickup(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
	private boolean fpt$useOn$ModifyExpressionValue(boolean original, @Local() BlockState state,
		@Local(argsOnly = true) UseOnContext context
	) {
		if (FptConfig.WRENCH_CAN_PICKUP_ANYTHING_DESTRUCTIBLE.get())
			return state.getDestroySpeed(context.getLevel(), context.getClickedPos()) > -1;
		else return original;
	}
}
