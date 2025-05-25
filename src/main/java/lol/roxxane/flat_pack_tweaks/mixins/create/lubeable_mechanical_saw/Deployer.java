package lol.roxxane.flat_pack_tweaks.mixins.create.lubeable_mechanical_saw;

import com.simibubi.create.content.kinetics.deployer.DeployerHandler;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = DeployerHandler.class, remap = false)
abstract class Deployer {
	/*
	@ModifyExpressionValue(method = "activateInner",
		at = @At(value = "INVOKE",
			target = "Lnet/minecraftforge/common/ForgeHooks;onRightClickBlock(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraftforge/event/entity/player/PlayerInteractEvent$RightClickBlock;"))
	private static RightClickBlock fpt$ModifyExpressionValue$activateInner(RightClickBlock event,
		@Local(argsOnly = true) DeployerFakePlayer player, @Local(argsOnly = true) BlockPos pos) {
		var level = player.level();
		var state = level.getBlockState(pos);

		if (state.getBlock() instanceof SawBlock &
			ApplyLubeHelper.apply_lube(state, level, pos, player, InteractionHand.MAIN_HAND).orElse(PASS) == SUCCESS) {
			event.setUseBlock(Event.Result.DENY);
			event.setUseItem(Event.Result.DENY);
			event.setCanceled(true);
		}

		return event;
	}*/

}
