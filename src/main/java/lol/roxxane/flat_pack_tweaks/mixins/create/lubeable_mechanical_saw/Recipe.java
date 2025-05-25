package lol.roxxane.flat_pack_tweaks.mixins.create.lubeable_mechanical_saw;

import com.google.gson.JsonObject;
import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = CuttingRecipe.class, remap = false)
abstract class Recipe extends ProcessingRecipe<RecipeWrapper> {
	private Recipe(IRecipeTypeInfo typeInfo, ProcessingRecipeBuilder.ProcessingRecipeParams params) {
		super(typeInfo, params);
	}

	@Unique
	public Boolean fpt$requires_lube = false;

	// ProcessingRecipe
	@Override
	public void readAdditional(@NotNull JsonObject json) {
		super.readAdditional(json);
		fpt$requires_lube = id.getPath().endsWith("requires_lube");
	}

	@Override
	public void readAdditional(@NotNull FriendlyByteBuf buffer) {
		super.readAdditional(buffer);
		fpt$requires_lube = id.getPath().endsWith("requires_lube");
	}

	// Mixing
	@Inject(method = "<init>",
		at = @At("RETURN"))
	private void fpt$Inject$init(ProcessingRecipeBuilder.ProcessingRecipeParams params, CallbackInfo $1) {
		fpt$requires_lube = id.getPath().endsWith("requires_lube");
	}
}