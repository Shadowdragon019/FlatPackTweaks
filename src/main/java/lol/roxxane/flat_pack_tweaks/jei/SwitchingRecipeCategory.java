package lol.roxxane.flat_pack_tweaks.jei;

import lol.roxxane.flat_pack_tweaks.recipes.SwitchingRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiConsumer;

import static lol.roxxane.flat_pack_tweaks.jei.FptJeiPlugin.SWITCHING_TEXTURE_RESOURCE;

public class SwitchingRecipeCategory extends AbstractRecipeCategory<SwitchingRecipe> {
	public SwitchingRecipeCategory(IGuiHelper gui_helper) {
		super(
			FptJeiRecipeTypes.SWITCHING,
			Component.translatable("gui.flat_pack_tweaks.category.switching"),
			gui_helper.drawableBuilder(SWITCHING_TEXTURE_RESOURCE, 0, 64, 16, 16).build(),
			126,
			93
		);
	}

	@Override
	public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull SwitchingRecipe recipe,
		@NotNull IFocusGroup $
	) {
		var item_count = recipe.items().size();
		var items = recipe.items();

		BiConsumer<List<Item>, Integer> add_slots = (row_items, y) -> {
			var row_item_count = row_items.size();
			var horizontal_size = 18*row_item_count;
			var x = getWidth()/2-horizontal_size/2;

			var i = -1;
			while (i < row_item_count-1) {
				i++;
				builder.addSlot(RecipeIngredientRole.INPUT, x+i*18+1, y+1)
					.addItemStack(row_items.get(i).getDefaultInstance());
			}
		};

		for (var item : items)
			builder.addSlot(RecipeIngredientRole.OUTPUT, 9999, 9999)
				.addItemStack(item.getDefaultInstance());

		if (item_count > 0 && item_count <= 7)
			add_slots.accept(items.subList(0, item_count), 57);
		else if (item_count <= 14) {
			add_slots.accept(items.subList(0, item_count-7), 48);
			add_slots.accept(items.subList(item_count-7, item_count), 66);
		} else {
			add_slots.accept(items.subList(0, item_count-14), 39);
			add_slots.accept(items.subList(item_count-14, item_count-7), 57);
			add_slots.accept(items.subList(item_count-7, item_count), 75);
		}
	}

	@Override
	public void draw(@NotNull SwitchingRecipe recipe, @NotNull IRecipeSlotsView $1,
		@NotNull GuiGraphics graphics, double $2, double $3
	) {
		var item_count = recipe.items().size();

		BiConsumer<Integer, Integer> draw_row = (row_item_count, y) -> {
			var horizontal_size = 18*row_item_count;
			var x = getWidth()/2-horizontal_size/2;

			var i = -1;
			while (i < row_item_count-1) {
				i++;
				graphics.blit(SWITCHING_TEXTURE_RESOURCE,
					x+i*18, y, 0, 0, 18, 18);
			}
		};

		graphics.blit(SWITCHING_TEXTURE_RESOURCE,
			47, 0, 0, 32, 32, 32);

		if (item_count > 0 && item_count <= 7)
			draw_row.accept(item_count, 57);
		else if (item_count <= 14) {
			draw_row.accept(item_count-7, 48);
			draw_row.accept(7, 66);
		} else {
			draw_row.accept(item_count-14, 39);
			draw_row.accept(7, 57);
			draw_row.accept(7, 75);
		}
	}
}
