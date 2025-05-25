package lol.roxxane.flat_pack_tweaks.mixins.create.lubeable_mechanical_saw;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.simibubi.create.content.kinetics.saw.SawBlockEntity;
import lol.roxxane.flat_pack_tweaks.LubeHelper;
import lol.roxxane.flat_pack_tweaks.accessor.LubeCountAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.crafting.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.stream.Stream;

@Mixin(value = SawBlockEntity.class, remap = false)
abstract class Processing implements LubeCountAccessor {
	@Shadow protected abstract List<? extends Recipe<?>> getRecipes();

	@Shadow private int recipeIndex;

	@Unique int fpt$lube_count = 0;

	@ModifyReturnValue(method = "shouldRun",
		at = @At("RETURN"))
	private boolean fpt$ModifyReturnValue$shouldRun(boolean original) {
		return original && fpt$lube_count > 0;
	}

	@Inject(method = "applyRecipe",
		at = @At(value = "INVOKE",
			remap = true,
			target = "Lnet/minecraft/world/item/ItemStack;getCount()I"))
	private void fpt$Inject$applyRecipe(CallbackInfo ci) {
		if (LubeHelper.requires_lube(getRecipes().get(recipeIndex)))
			fpt$lube_count--;
	}

	@ModifyExpressionValue(method = "getRecipes",
		at = @At(value = "INVOKE",
			target = "Ljava/util/List;stream()Ljava/util/stream/Stream;"))
	private Stream<Recipe<?>> fpt$ModifyExpressionValue$getRecipes(Stream<Recipe<?>> original) {
		return original.filter(recipe ->
			lube_count$get() > 0 && LubeHelper.requires_lube(recipe) ||
			lube_count$get() < 1 && LubeHelper.requires_dry(recipe));
	}

	@Inject(method = "write",
		at = @At("RETURN"))
	private void fpt$Inject$write(CompoundTag compound, boolean $, CallbackInfo ci) {
		compound.putInt("fpt$lube_count", fpt$lube_count);
	}

	@Inject(method = "read",
		at = @At("RETURN"))
	private void fpt$Inject$read(CompoundTag compound, boolean $, CallbackInfo ci) {
		fpt$lube_count = compound.getInt("fpt$lube_count");
	}

	// LubeCountAccessor
	@Override
	public int lube_count$get() {
		return fpt$lube_count;
	}

	@Override
	public void lube_count$set(int count) {
		fpt$lube_count = count;
	}
}
