package lol.roxxane.flat_pack_tweaks.mixins.create;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.equipment.wrench.WrenchItem;
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

@Mixin(WrenchItem.class)
abstract class WrenchTrees {

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

			for (var pos : fpt$gather_tree_blocks(level, starting_pos, new HashSet<>())) {
				var state = level.getBlockState(pos);

				if (player != null && !player.isCreative())
					Block.getDrops(state, (ServerLevel) level, pos, level.getBlockEntity(pos), player,
							context.getItemInHand())
						.forEach(stack -> player.getInventory().placeItemBackInInventory(stack));

				level.destroyBlock(pos, false);
				level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			}
		}
	}

	@Unique
	private HashSet<BlockPos> fpt$gather_tree_blocks(Level level, BlockPos starting_pos, HashSet<BlockPos> set) {
		for (int x = 1; x >= -1; --x)
			for (int y = 1; y >= -1; --y)
				for (int z = 1; z >= -1; --z) {
					var pos = starting_pos.offset(x, y, z);
					var state = level.getBlockState(pos);
					if (!set.contains(pos) && (state.is(BlockTags.LOGS) || state.is(BlockTags.LEAVES))) {
						set.add(pos);
						fpt$gather_tree_blocks(level, pos, set);
					}
				}
		return set;
	}
}
