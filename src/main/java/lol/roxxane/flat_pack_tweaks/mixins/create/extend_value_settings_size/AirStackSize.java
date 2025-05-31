package lol.roxxane.flat_pack_tweaks.mixins.create.extend_value_settings_size;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Item.class)
abstract class AirStackSize {
	@ModifyReturnValue(method = "getMaxStackSize",
		at = @At("RETURN"))
	private int fpt$ModifyReturnValue$getMaxStackSize(int original) {
		if (((Object) this) == Items.AIR)
			return 256;
		return original;
	}
}
