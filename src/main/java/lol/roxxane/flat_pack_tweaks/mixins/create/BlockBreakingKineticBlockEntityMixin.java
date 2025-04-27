package lol.roxxane.flat_pack_tweaks.mixins.create;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.kinetics.base.BlockBreakingKineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.drill.DrillBlockEntity;
import lol.roxxane.flat_pack_tweaks.config.FptConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockBreakingKineticBlockEntity.class)
abstract class BlockBreakingKineticBlockEntityMixin extends KineticBlockEntity {
	@Unique
	int fct$ore_drop_timer = 0;

	@Unique
	final int fct$max_ore_drop_timer_speed = 200 * 16;

	@Shadow(remap = false) protected BlockPos breakingPos;

	@Shadow(remap = false) protected abstract boolean shouldRun();

	@Shadow(remap = false) protected int breakerId;

	public BlockBreakingKineticBlockEntityMixin(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
		super(typeIn, pos, state);
	}

	//this is weirdly but not noticeably buggy because I can't *remove* progress (no, setting one to -1 doesn't fix the bugs)
	//to see what i mean, place an ore, place a drill & make it mine the ore, remove the power, then rotate the drill with a wrench or replace the block with any other (don't power it again)
	//MC eventually removes all (presumably inactive) block breaking progresses after a while
	@Inject(method = "tick",
		remap = false,
		at = @At("HEAD"))
	private void flat_pack_tweaks$tick_Inject(CallbackInfo ci) {
		if ((BlockBreakingKineticBlockEntity) (Object) this instanceof DrillBlockEntity) {
			if (level.isClientSide || getSpeed() == 0 || !shouldRun() || level == null || breakingPos == null)
				return;

			var block_to_break = level.getBlockState(breakingPos).getBlock();
			if (!FptConfig.can_infini_drill(block_to_break))
				fct$ore_drop_timer = 0;
			else {
				fct$ore_drop_timer += (int) (1 * Math.abs(getSpeed()));

				if (fct$ore_drop_timer > fct$max_ore_drop_timer_speed) {
					var drill_state = level.getBlockState(worldPosition);
					var drop_item_pos =
						worldPosition.relative(drill_state.getValue(BlockStateProperties.FACING), -1);

					level.addFreshEntity(new ItemEntity(level,
						drop_item_pos.getX() + 0.5,
						drop_item_pos.getY() + 0.5,
						drop_item_pos.getZ() + 0.5,
						new ItemStack(FptConfig.get_infini_drilling_result(block_to_break))));

					fct$ore_drop_timer = 0;
				}
				level.destroyBlockProgress(breakerId, breakingPos,
					fct$ore_drop_timer / (fct$max_ore_drop_timer_speed / 10));
			}
		}
	}

	@ModifyReturnValue(method = "canBreak",
		remap = false,
		at = @At("RETURN"))
	private boolean flat_pack_tweaks$canBreak_ModifyReturnValue(boolean original, @Local(argsOnly = true) BlockState state_to_break) {
		if ((BlockBreakingKineticBlockEntity) (Object) this instanceof DrillBlockEntity)
			return original && !FptConfig.can_infini_drill(state_to_break.getBlock());
		else return original;
	}
}
