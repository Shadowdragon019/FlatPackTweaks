package lol.roxxane.flat_pack_tweaks.mixin_external_classes.create.lubeable_mechanical_saw;

import com.simibubi.create.content.kinetics.saw.SawBlock;
import com.simibubi.create.content.kinetics.saw.SawBlockEntity;
import lol.roxxane.flat_pack_tweaks.accessor.LubeCountAccessor;
import lol.roxxane.flat_pack_tweaks.tags.FptItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

import static lol.roxxane.flat_pack_tweaks.FptStateProperties.LUBED;

public class ApplyLubeHelper {
	public static Optional<InteractionResult> apply_lube(BlockState state, Level level, BlockPos pos, Player player,
		InteractionHand hand) {
		var held_item = player.getItemInHand(hand);
		if (held_item.is(FptItemTags.LUBE) && !player.isSpectator() && hand == InteractionHand.MAIN_HAND &&
			state.getBlock() instanceof SawBlock saw)
			return Optional.of(saw.onBlockEntityUse(level, pos, be ->
				apply_lube_block_entity(be, state, level, pos, player, held_item)));
		return Optional.empty();
	}

	private static InteractionResult apply_lube_block_entity(SawBlockEntity be, BlockState state, Level level,
		BlockPos pos, Player player, ItemStack held_item) {
		var lube_count_accessor = ((LubeCountAccessor) be);
		if (lube_count_accessor.lube_count$get() <= 5) {
			lube_count_accessor.lube_count$set(lube_count_accessor.lube_count$get() + 10);
			if (!player.isCreative())
				player.setItemInHand(InteractionHand.MAIN_HAND,
					held_item.copyWithCount(held_item.getCount() - 1));
			level.setBlockAndUpdate(pos, state.setValue(LUBED, true));
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
