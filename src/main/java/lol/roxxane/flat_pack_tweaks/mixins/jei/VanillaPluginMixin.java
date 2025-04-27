package lol.roxxane.flat_pack_tweaks.mixins.jei;

import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.library.plugins.vanilla.VanillaPlugin;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(VanillaPlugin.class)
abstract class VanillaPluginMixin {
	// Remove the crafting table as a catalyst from the crafting category
	// This is done as when an item that a catalyst represents is hidden,
	//   the category that the catalyst corresponds to disappears also,
	//   which is a big issue as we don't use the crafting table (at least for now)
	@Redirect(method = "registerRecipeCatalysts",
		remap = false,
		at = @At(value = "INVOKE", ordinal = 0,
			target = "Lmezz/jei/api/registration/IRecipeCatalystRegistration;addRecipeCatalyst(Lnet/minecraft/world/item/ItemStack;[Lmezz/jei/api/recipe/RecipeType;)V"))
	private void flat_pack_tweaks$registerRecipeCatalysts_Redirect(IRecipeCatalystRegistration instance, ItemStack ingredient, RecipeType<?>[] recipeTypes) {}
}
