package lol.roxxane.flat_pack_tweaks.mixins.create;

import com.simibubi.create.content.kinetics.motor.CreativeMotorBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(value = CreativeMotorBlockEntity.class, remap = false)
abstract class MotorRemoveSpeedSelector {
	@Redirect(method = "addBehaviours",
		at = @At(value = "INVOKE",
			target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
	private boolean fpt$Redirect$addBehaviours(List<Object> instance, Object e) {
		return false;
	}
}
