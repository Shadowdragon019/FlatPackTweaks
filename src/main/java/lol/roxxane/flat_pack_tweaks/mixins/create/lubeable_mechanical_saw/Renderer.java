package lol.roxxane.flat_pack_tweaks.mixins.create.lubeable_mechanical_saw;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.kinetics.saw.SawBlock;
import com.simibubi.create.content.kinetics.saw.SawBlockEntity;
import com.simibubi.create.content.kinetics.saw.SawRenderer;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import lol.roxxane.flat_pack_tweaks.accessor.LubeCountAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import static lol.roxxane.flat_pack_tweaks.FptPartialModels.*;

@Mixin(value = SawRenderer.class, remap = false)
abstract class Renderer {
	@ModifyArg(method = "renderBlade",
		at = @At(value = "INVOKE",
			target = "Lnet/createmod/catnip/render/CachedBuffers;partialFacing(Ldev/engine_room/flywheel/lib/model/baked/PartialModel;Lnet/minecraft/world/level/block/state/BlockState;)Lnet/createmod/catnip/render/SuperByteBuffer;"))
	private PartialModel fpt$ModifyExpressionValue$renderBlade(PartialModel original,
		@Local(argsOnly = true) SawBlockEntity be) {
		var speed = be.getSpeed();
		if (LubeCountAccessor.has_lube(be))
			if (SawBlock.isHorizontal(be.getBlockState()))
				if (speed > 0) return SAW_BLADE_HORIZONTAL_ACTIVE;
				else if (speed < 0) return SAW_BLADE_HORIZONTAL_REVERSED;
				else return SAW_BLADE_HORIZONTAL_INACTIVE;
			else
				if (be.getSpeed() > 0) return SAW_BLADE_VERTICAL_ACTIVE;
				else if (speed < 0) return SAW_BLADE_VERTICAL_REVERSED;
				else return SAW_BLADE_VERTICAL_INACTIVE;
		return original;
	}
}