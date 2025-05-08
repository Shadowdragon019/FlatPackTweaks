package lol.roxxane.flat_pack_tweaks.mixins.minecraft;

import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BlockBehaviour.Properties.class)
abstract class BlockHardnessLimiter {
	@ModifyVariable(method = "destroyTime", at = @At("HEAD"), argsOnly = true)
	private float fpt$destroyTime$ModifyVariable(float hardness) {
		if (hardness > 0.5) return 0.5F;
		else return hardness;
	}
}
