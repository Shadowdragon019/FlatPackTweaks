package lol.roxxane.flat_pack_tweaks.mixins.create;

import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import com.simibubi.create.content.kinetics.gearbox.GearboxBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = GearboxBlock.class, remap = false)
abstract class GearboxDropsFromLootTable extends RotatedPillarKineticBlock {
	public GearboxDropsFromLootTable(Properties properties) {
		super(properties);
	}

	@SuppressWarnings("deprecation")
	@Inject(method = "getDrops", remap = true, cancellable = true, at = @At("HEAD"))
	private void fpt$Inject$getDrops(BlockState state, LootParams.Builder builder,
		CallbackInfoReturnable<List<ItemStack>> cir) {
		cir.setReturnValue(super.getDrops(state, builder));
	}
}
