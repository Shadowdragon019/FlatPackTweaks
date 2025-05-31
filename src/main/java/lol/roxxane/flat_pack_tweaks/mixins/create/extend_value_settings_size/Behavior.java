package lol.roxxane.flat_pack_tweaks.mixins.create.extend_value_settings_size;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = FilteringBehaviour.class, remap = false)
abstract class Behavior {
	@ModifyArg(method = "createBoard",
		index = 1,
		at = @At(value = "INVOKE",
			target = "Lcom/simibubi/create/foundation/blockEntity/behaviour/ValueSettingsBoard;<init>(Lnet/minecraft/network/chat/Component;IILjava/util/List;Lcom/simibubi/create/foundation/blockEntity/behaviour/ValueSettingsFormatter;)V"))
	private int fpt$ModifyArg$createBoard(int original, @Local ItemStack $) {
		if (original == 64)
			return 256;
		return original;
	}

	@ModifyConstant(method = "<init>", constant = @Constant(intValue = 64))
	private int fpt$ModifyConstant$init(int $) {
		return 256;
	}

	/*
	@Inject(method = "read",
		at = @At(value = "FIELD",
			shift = At.Shift.AFTER,
			opcode = Opcodes.PUTFIELD,
			target = "Lcom/simibubi/create/foundation/blockEntity/behaviour/filtering/FilteringBehaviour;count:I"))
	private void fpt$Inject$read(CompoundTag $, boolean $1, CallbackInfo $2) {
		Fpt.log("read");
		Fpt.log(count);
	}

	@Inject(method = "setFilter(Lnet/minecraft/world/item/ItemStack;)Z",
		at = @At(value = "FIELD",
			shift = At.Shift.AFTER,
			opcode = Opcodes.PUTFIELD,
			target = "Lcom/simibubi/create/foundation/blockEntity/behaviour/filtering/FilteringBehaviour;count:I"))
	private void fpt$Inject$setFilter(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		Fpt.log("Item filter: " + stack);
		Fpt.log("Item filter max stack: " + stack.getMaxStackSize());
		Fpt.log("setFilter");
		Fpt.log(count);
	}

	@Inject(method = "setValueSettings",
		at = @At(value = "FIELD",
			shift = At.Shift.AFTER,
			opcode = Opcodes.PUTFIELD,
			target = "Lcom/simibubi/create/foundation/blockEntity/behaviour/filtering/FilteringBehaviour;count:I"))
	private void fpt$Inject$setValueSettings(Player $, ValueSettings $1, boolean $2, CallbackInfo ci,
		@Local(argsOnly = true) ValueSettings settings) {
		Fpt.log("setValueSettings");
		Fpt.log("Value: " + settings.value());
		Fpt.log("Item filter: " + filter.item());
		Fpt.log("Item filter max stack: " + filter.item().getMaxStackSize());
		Fpt.log(count);
	}

	@ModifyExpressionValue(method = "setValueSettings",
		at = @At(value = "INVOKE",
			remap = true,
			target = "Lnet/minecraft/world/item/ItemStack;getMaxStackSize()I"))
	private int fpt$ModifyExpressionValue$setValueSettings(int original) {
		//if (filter.item().getItem() == Items.AIR)
			//return 256;
		return original;
	}*/
}
