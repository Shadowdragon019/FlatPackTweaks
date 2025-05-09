package lol.roxxane.flat_pack_tweaks.jei;

import lol.roxxane.flat_pack_tweaks.Fpt;
import lol.roxxane.flat_pack_tweaks.recipes.InfiniDrillingRecipe;
import lol.roxxane.flat_pack_tweaks.recipes.SwitchingRecipe;
import mezz.jei.api.recipe.RecipeType;

public class FptJeiRecipeTypes {
	public static final RecipeType<InfiniDrillingRecipe> INFINI_DRILLING =
		RecipeType.create(Fpt.ID, "infini_drilling", InfiniDrillingRecipe.class);

	public static final RecipeType<SwitchingRecipe> SWITCHING =
		RecipeType.create(Fpt.ID, "switching", SwitchingRecipe.class);
}
