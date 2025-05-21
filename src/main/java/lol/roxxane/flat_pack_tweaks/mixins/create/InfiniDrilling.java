package lol.roxxane.flat_pack_tweaks.mixins.create;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.kinetics.base.BlockBreakingKineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.drill.DrillBlockEntity;
import lol.roxxane.flat_pack_tweaks.config.server.FptServerConfig;
import lol.roxxane.flat_pack_tweaks.recipes.InfiniDrillingRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockBreakingKineticBlockEntity.class)
abstract class InfiniDrilling extends KineticBlockEntity {
	@Unique
	private boolean is_drill() {
		return (BlockBreakingKineticBlockEntity) (Object) this instanceof DrillBlockEntity;
	}

	@Shadow(remap = false) public abstract void onBlockBroken(BlockState stateToBreak);
	@Shadow(remap = false) protected int ticksUntilNextProgress;
	@Shadow(remap = false) protected BlockPos breakingPos;
	@Unique public InfiniDrillingRecipe fpt$recipe = null;

	public InfiniDrilling(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
		super(typeIn, pos, state);
	}

	@ModifyExpressionValue(method = "tick",
		remap = false,
		at = @At(value = "INVOKE",
			remap = true,
			target = "Lnet/minecraft/world/level/block/state/BlockState;getDestroySpeed(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)F"))
	private float fpt$tick$ModifyExpressionValue(float original, @Local BlockState state_to_break) {
		fpt$recipe = FptServerConfig.get_infini_drill_recipe(state_to_break.getBlock());
		if (fpt$recipe == null) return original;
		else return (float) fpt$recipe.hardness();
	}

	@Inject(method = "tick",
		remap = false,
		at = @At(value = "INVOKE",
			remap = true,
			target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"))
	private void fpt$tick$Inject(CallbackInfo ci) {
		if (is_drill() && fpt$recipe != null && fpt$recipe.generates_fire() && level != null) {
			var random = level.random;
			var fire_pos = new BlockPos(
				breakingPos.getX() + random.nextIntBetweenInclusive(-1, 1),
				random.nextIntBetweenInclusive(
					Integer.max(level.getMinBuildHeight(), breakingPos.getY() - 1),
					Integer.min(level.getMaxBuildHeight(), breakingPos.getY() + 1)),
				breakingPos.getZ() + random.nextIntBetweenInclusive(-1, 1));

			if (FireBlock.canBePlacedAt(level, fire_pos, Direction.DOWN)) {
				level.setBlockAndUpdate(fire_pos, Blocks.FIRE.defaultBlockState());
				level.playSound(null, fire_pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS,
					1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
			}
		}
	}

	@Redirect(method = "tick",
		remap = false,
		at = @At(value = "INVOKE",
			target = "Lcom/simibubi/create/content/kinetics/base/BlockBreakingKineticBlockEntity;onBlockBroken(Lnet/minecraft/world/level/block/state/BlockState;)V"))
	private void fpt$tick$Redirect(BlockBreakingKineticBlockEntity instance, BlockState state_to_break) {
		if (is_drill() && fpt$recipe != null && level != null) {
			var drill_state = level.getBlockState(worldPosition);
			var drop_item_pos =
				worldPosition.relative(drill_state.getValue(BlockStateProperties.FACING), -1);

			if (fpt$recipe.item() != Items.AIR)
				level.addFreshEntity(new ItemEntity(level,
					drop_item_pos.getX() + 0.5,
					drop_item_pos.getY() + 0.5,
					drop_item_pos.getZ() + 0.5,
					new ItemStack(fpt$recipe.item())));
		} else onBlockBroken(state_to_break);
	}

	@ModifyArg(method = "tick",
		remap = false,
		index = 2,
		at = @At(value = "INVOKE",
			ordinal = 2,
			remap = true,
			target = "Lnet/minecraft/world/level/Level;destroyBlockProgress(ILnet/minecraft/core/BlockPos;I)V"))
	private int fpt$tick$ModifyArg(int progress, @Local(ordinal = 0) float block_hardness) {
		// Turns a 10 tick dealy (lazyTick reset it) to a 0 tick delay
		// This is needed else infini-drilling has an extra 10 tick delay.
		// I honestly don't really care that this affects normal drilling. Might be a qol despite some additional lag.
		ticksUntilNextProgress = 0;

		if (block_hardness <= -1 || (fpt$recipe == null || fpt$recipe.item() == Items.AIR)) return -1;
		else return progress;
	}

	@ModifyReturnValue(method = "canBreak",
		remap = false,
		at = @At("RETURN"))
	private boolean fpt$canBreak$ModifyReturnValue(boolean original,
		@Local(argsOnly = true) BlockState state_to_break
	) {
		if ((BlockBreakingKineticBlockEntity) (Object) this instanceof DrillBlockEntity)
			return original || FptServerConfig.can_infini_drill(state_to_break.getBlock());
		else return original;
	}
}