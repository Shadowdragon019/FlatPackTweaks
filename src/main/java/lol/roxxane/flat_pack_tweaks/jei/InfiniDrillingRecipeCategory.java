package lol.roxxane.flat_pack_tweaks.jei;

import com.simibubi.create.AllBlocks;
import lol.roxxane.flat_pack_tweaks.recipes.InfiniDrillingRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static lol.roxxane.flat_pack_tweaks.Fpt.resource;
import static lol.roxxane.flat_pack_tweaks.FptUtils.resource_recipe_string;
import static lol.roxxane.flat_pack_tweaks.jei.FptJeiPlugin.INFINI_DRILLING_TEXTURE_RESOURCE;


public class InfiniDrillingRecipeCategory extends AbstractRecipeCategory<InfiniDrillingRecipe> {
	public InfiniDrillingRecipeCategory(IGuiHelper gui_helper) {
		super(
			FptJeiRecipeTypes.INFINI_DRILLING,
			Component.translatable("gui.flat_pack_tweaks.category.infini_drilling"),
			gui_helper.createDrawableItemStack(new ItemStack(AllBlocks.MECHANICAL_DRILL)),
			26,
			77
		);
	}

	@Override
	public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull InfiniDrillingRecipe recipe,
		@NotNull IFocusGroup $
	) {
		builder.addSlot(RecipeIngredientRole.INPUT, 5, 60)
			.addItemStack(recipe.block().asItem().getDefaultInstance());
		builder.addSlot(RecipeIngredientRole.OUTPUT, 5, 5)
			.addItemStack(recipe.item().getDefaultInstance());
	}

	@Override
	public void draw(@NotNull InfiniDrillingRecipe recipe, @NotNull IRecipeSlotsView $1,
		@NotNull GuiGraphics graphics, double $2, double $3
	) {
		if (recipe.generates_fire())
			graphics.blit(INFINI_DRILLING_TEXTURE_RESOURCE,
				0, 33, 26, 33, 26, 20);
		else graphics.blit(INFINI_DRILLING_TEXTURE_RESOURCE,
				0, 33, 0, 33, 26, 20);

		// Output slot
		graphics.blit(INFINI_DRILLING_TEXTURE_RESOURCE,
			4, 59, 4, 59, 18, 18);
		if (recipe.item() != Items.AIR)
			graphics.blit(INFINI_DRILLING_TEXTURE_RESOURCE,
				0, 0, 0, 0, 26, 26);
	}

	@Override
	public @Nullable ResourceLocation getRegistryName(@NotNull InfiniDrillingRecipe recipe) {
		var block_resource = ForgeRegistries.BLOCKS.getKey(recipe.block());
		var item_resource = ForgeRegistries.ITEMS.getKey(recipe.item());

		return (recipe.item() == Items.AIR) ? resource("infini_drilling/" + resource_recipe_string(block_resource)) :
			resource("infini_drilling/" + resource_recipe_string(item_resource) + "_from_" +
				resource_recipe_string(block_resource));
	}
}
