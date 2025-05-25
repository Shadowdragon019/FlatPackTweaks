package lol.roxxane.flat_pack_tweaks.mixins.create.lubeable_mechanical_saw;

import com.simibubi.create.content.kinetics.deployer.DeployerHandler;
import com.simibubi.create.content.kinetics.saw.SawBlock;
import lol.roxxane.flat_pack_tweaks.tags.FptItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = DeployerHandler.class, remap = false)
abstract class Deployer {
	@Redirect(method = "activateInner",
		at = @At(value = "INVOKE",
			target = "Lnet/minecraftforge/common/ForgeHooks;onRightClickBlock(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraftforge/event/entity/player/PlayerInteractEvent$RightClickBlock;"))
	private static RightClickBlock fpt$Redirect$activateInner(Player player, InteractionHand hand, BlockPos pos,
		BlockHitResult result) {
		@SuppressWarnings("resource")
		var level = player.level();
		var state = level.getBlockState(pos);
		if (state.getBlock() instanceof SawBlock && player.getItemInHand(hand).is(FptItemTags.LUBE))
			return new RightClickBlock(player, hand, pos, result);
		return ForgeHooks.onRightClickBlock(player, hand, pos, result);
	}
}
