package lol.roxxane.flat_pack_tweaks.mixins.create.lubeable_mechanical_saw;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.kinetics.base.DirectionalAxisKineticBlock;
import com.simibubi.create.content.kinetics.saw.SawBlock;
import com.simibubi.create.content.kinetics.saw.SawBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import lol.roxxane.flat_pack_tweaks.mixin_external_classes.create.lubeable_mechanical_saw.ApplyLubeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static lol.roxxane.flat_pack_tweaks.FptStateProperties.LUBED;

@Mixin(SawBlock.class)
abstract class Block extends DirectionalAxisKineticBlock implements IBE<SawBlockEntity> {
	private Block(Properties properties) {
		super(properties);
	}

	@Inject(method = "createBlockStateDefinition",
		at = @At(value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/StateDefinition$Builder;add([Lnet/minecraft/world/level/block/state/properties/Property;)Lnet/minecraft/world/level/block/state/StateDefinition$Builder;"))
	private void fpt$Inject$createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> builder,
		CallbackInfo $) {
		builder.add(LUBED);
	}

	@ModifyReturnValue(method = "getStateForPlacement",
		at = @At("RETURN"))
	private BlockState fpt$ModifyReturnValue$getStateForPlacement(BlockState original,
		@Local(argsOnly = true) BlockPlaceContext context) {
		return original.setValue(LUBED, false);
	}

	@Inject(method = "use",
		cancellable = true,
		at = @At("HEAD"))
	private void fpt$Inject$use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
		BlockHitResult $, CallbackInfoReturnable<InteractionResult> cir) {
		ApplyLubeHelper.apply_lube(state, level, pos, player, hand).ifPresent(cir::setReturnValue);
	}
}
