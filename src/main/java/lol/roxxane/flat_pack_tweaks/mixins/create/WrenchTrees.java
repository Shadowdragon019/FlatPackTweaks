package lol.roxxane.flat_pack_tweaks.mixins.create;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.equipment.wrench.WrenchItem;
import lol.roxxane.flat_pack_tweaks.Fpt;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashSet;
import java.util.List;

@Mixin(WrenchItem.class)
abstract class WrenchTrees {
	@Unique private int fpt$blocks_destroyed = 0; // This doesn't work exactly because...
	// reasons I don't understand. But meh, good enough
	@Unique private final List<BlockPos> fpt$check_offsets = List.of(
		new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0),
		new BlockPos(0, 1, 0), new BlockPos(0, -1, 0),
		new BlockPos(0, 0, 1), new BlockPos(0, 0, -1));

	@Inject(method = "onItemUseOnOther",
		remap = false,
		at = @At(value = "INVOKE",
			remap = true,
			target = "Lnet/minecraft/world/level/block/state/BlockState;spawnAfterBreak(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/item/ItemStack;Z)V"))
	private void fpt$onItemUseOnOther$Inject(UseOnContext context, CallbackInfoReturnable<InteractionResult> $,
		@Local BlockPos starting_pos
	) {
		var level = context.getLevel();

		if (!level.isClientSide) {
			var player = context.getPlayer();
			fpt$blocks_destroyed = 0;

			for (var pos : fpt$gather_tree_blocks(level, starting_pos, new HashSet<>())) {
				var state = level.getBlockState(pos);

				if (player != null && !player.isCreative())
					Block.getDrops(state, (ServerLevel) level, pos, level.getBlockEntity(pos), player,
							context.getItemInHand())
						.forEach(stack -> player.getInventory().placeItemBackInInventory(stack));

				level.destroyBlock(pos, false);
				level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			}
			Fpt.log(fpt$blocks_destroyed);
		}
	}

	@Unique
	private HashSet<BlockPos> fpt$gather_tree_blocks(Level level, BlockPos starting_pos, HashSet<BlockPos> set) {
		for (var offset : fpt$check_offsets) {
			var pos = starting_pos.offset(offset.getX(), offset.getY(), offset.getZ());
			var state = level.getBlockState(pos);
			if (!set.contains(pos) && (state.is(BlockTags.LOGS) || state.is(BlockTags.LEAVES))) {
				set.add(pos);
				fpt$blocks_destroyed++;
				if (fpt$blocks_destroyed < 64)
					fpt$gather_tree_blocks(level, pos, set);
				else break;
			}
		}
		return set;

	}
}
