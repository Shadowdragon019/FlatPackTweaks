package lol.roxxane.flat_pack_tweaks.jei;

import com.simibubi.create.AllBlocks;
import lol.roxxane.flat_pack_tweaks.Fpt;
import lol.roxxane.flat_pack_tweaks.FptUtils;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


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
		@NotNull IFocusGroup focuses
	) {
		builder.addSlot(RecipeIngredientRole.INPUT, 5, 60)
			.addItemStack(recipe.block().asItem().getDefaultInstance());
		builder.addSlot(RecipeIngredientRole.OUTPUT, 5, 5)
			.addItemStack(recipe.item().getDefaultInstance());
	}

	@Override
	public @Nullable ResourceLocation getRegistryName(@NotNull InfiniDrillingRecipe recipe) {
		var block_resource =
			ForgeRegistries.BLOCKS.getKey(recipe.block());
		var item_resource = ForgeRegistries.ITEMS.getKey(recipe.item());

		assert block_resource != null;
		assert item_resource != null;

		return Fpt.resource(FptUtils.resource_recipe_string(item_resource) + "_from_" +
			FptUtils.resource_recipe_string(block_resource));
	}

	/*
	private final IDrawable background;
	private final IDrawable icon;
	private final Component localized_name =
		Component.translatable("gui.flat_pack_tweaks.category.infini_drilling");

	public InfiniDrillingRecipeCategory(IGuiHelper gui_helper) {
		background = gui_helper.createDrawable(
			Fpt.resource("textures/jei/gui/drilling_drill.png"), 0, 0, 26, 77);
		icon = gui_helper.createDrawableItemStack(new ItemStack(AllBlocks.MECHANICAL_DRILL));
	}

	@Override
	public @NotNull RecipeType<InfiniDrillingRecipe> getRecipeType() {
		return FptJeiRecipeTypes.INFINI_DRILLING;
	}

	@Override
	public @NotNull Component getTitle() {
		return localized_name;
	}

	@Override
	public @NotNull IDrawable getBackground() {
		return background;
	}

	@Override
	public @Nullable IDrawable getIcon() {
		return icon;
	}
	*/
}
