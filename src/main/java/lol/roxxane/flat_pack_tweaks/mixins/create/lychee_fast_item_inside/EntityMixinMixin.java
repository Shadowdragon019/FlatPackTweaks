package lol.roxxane.flat_pack_tweaks.mixins.create.lychee_fast_item_inside;

import com.bawnorton.mixinsquared.TargetHandler;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = Entity.class, priority = 1500)
abstract class EntityMixinMixin {
	@TargetHandler(mixin = "snownee.lychee.mixin.EntityMixin", name = "lychee_tick")
	@ModifyConstant(method = "@MixinSquared:Handler",
		remap = false,
		constant = @Constant(intValue = 20))
	private int fpt$lychee_tick$ModifyConstant$TargetHandler$change_period(int constant) {
		return 1;
	}

	@TargetHandler(mixin = "snownee.lychee.mixin.EntityMixin", name = "lychee_tick")
	@ModifyConstant(method = "@MixinSquared:Handler",
		remap = false,
		constant = @Constant(intValue = 10))
	private int fpt$lychee_tick$ModifyConstant$TargetHandler$(int constant) {
		return 0;
	}
}