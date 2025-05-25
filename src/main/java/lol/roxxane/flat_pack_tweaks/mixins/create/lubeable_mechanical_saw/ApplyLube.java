package lol.roxxane.flat_pack_tweaks.mixins.create.lubeable_mechanical_saw;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.kinetics.base.DirectionalAxisKineticBlock;
import com.simibubi.create.content.kinetics.saw.SawBlock;
import com.simibubi.create.content.kinetics.saw.SawBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import lol.roxxane.flat_pack_tweaks.accessor.LubeCountAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static lol.roxxane.flat_pack_tweaks.FptStateProperties.LUBED;

// TODO: Proper blockstate file thing
@Mixin(SawBlock.class)
abstract class ApplyLube extends DirectionalAxisKineticBlock implements IBE<SawBlockEntity> {
	private ApplyLube(Properties properties) {
		super(properties);
	}

	@Unique
	private static BlockState fpt$update_lubed_state(Level level, BlockPos pos, BlockState original) {
		var be = level.getBlockEntity(pos);
		return be instanceof SawBlockEntity ?
			original.setValue(LUBED, LubeCountAccessor.get(be) > 0) : original;
	}

	@Inject(method = "createBlockStateDefinition",
		at = @At(value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/StateDefinition$Builder;add([Lnet/minecraft/world/level/block/state/properties/Property;)Lnet/minecraft/world/level/block/state/StateDefinition$Builder;"))
	private void fpt$Inject$createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder,
		CallbackInfo $) {
		builder.add(LUBED);
	}

	@ModifyReturnValue(method = "getStateForPlacement",
		at = @At("RETURN"))
	private BlockState fpt$ModifyReturnValue$getStateForPlacement(BlockState original,
		@Local(argsOnly = true) BlockPlaceContext context) {
		return fpt$update_lubed_state(context.getLevel(), context.getClickedPos(), original);
	}

	@Inject(method = "use",
		cancellable = true,
		at = @At("HEAD"))
	private void fpt$Inject$use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,
		BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
		var held_item = player.getItemInHand(hand);
		if (player.isSpectator() && hand != InteractionHand.MAIN_HAND)
			return;

		/*
		if (held_item.is(Items.STICK))
			onBlockEntityUse(world, pos, be -> {
				Fpt.log(LubeCountAccessor.get(be));
				return null;
			});*/

		if (held_item.is(Items.GLASS_BOTTLE))
		//if (held_item.is(FptItemTags.LUBE))
			cir.setReturnValue(onBlockEntityUse(world, pos, be -> {
				var lube_count_accessor = ((LubeCountAccessor) be);
				if (lube_count_accessor.lube_count$get() < 1) {
					lube_count_accessor.lube_count$set(10);
					if (!player.isCreative())
						player.setItemInHand(InteractionHand.MAIN_HAND,
							held_item.copyWithCount(held_item.getCount() - 1));
					return InteractionResult.SUCCESS;
				}
				return InteractionResult.PASS;
			}));
	}
}
