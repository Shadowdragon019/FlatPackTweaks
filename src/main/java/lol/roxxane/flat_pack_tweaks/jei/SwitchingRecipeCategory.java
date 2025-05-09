package lol.roxxane.flat_pack_tweaks.jei;

import lol.roxxane.flat_pack_tweaks.Fpt;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import org.apache.logging.log4j.util.TriConsumer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiConsumer;

public class SwitchingRecipeCategory extends AbstractRecipeCategory<SwitchingRecipe> {
	public SwitchingRecipeCategory(IGuiHelper gui_helper) {
		super(
			FptJeiRecipeTypes.SWITCHING,
			Component.translatable("gui.flat_pack_tweaks.category.switching"),
			gui_helper.createDrawableItemStack(new ItemStack(Blocks.BARRIER)), //TODO: Icon
			126,
			154
		);
	}

	@Override
	public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull SwitchingRecipe recipe,
		@NotNull IFocusGroup $
	) {
		var item_count = recipe.items().size();
		var items = recipe.items();

		TriConsumer<List<Item>, Integer, Boolean> add_slots = (row_items, y, is_input) -> {
			var row_item_count = row_items.size();
			var horizontal_size = 18*row_item_count;
			var x = getWidth()/2-horizontal_size/2;

			var i = -1;
			while (i < row_item_count-1) {
				i++;
				builder.addSlot(is_input ? RecipeIngredientRole.INPUT : RecipeIngredientRole.OUTPUT,
					x+i*18+1, y+1).addItemStack(row_items.get(i).getDefaultInstance());
			}
		};

		if (item_count > 0 && item_count <= 7) {
			add_slots.accept(items.subList(0, item_count), 36, true);
			add_slots.accept(items.subList(0, item_count), 100, false);
		} else if (item_count <= 14) {
			add_slots.accept(items.subList(0, item_count-7), 18, true);
			add_slots.accept(items.subList(item_count-7, item_count), 36, true);
			add_slots.accept(items.subList(item_count-7, item_count), 100, false);
			add_slots.accept(items.subList(0, item_count-7), 118, false);
		} else {
			add_slots.accept(items.subList(0, item_count-14), 0, true);
			add_slots.accept(items.subList(item_count-14, item_count-7), 18, true);
			add_slots.accept(items.subList(item_count-7, item_count), 36, true);
			add_slots.accept(items.subList(item_count-7, item_count), 100, false);
			add_slots.accept(items.subList(item_count-14, item_count-7), 118, false);
			add_slots.accept(items.subList(0, item_count-14), 136, false);
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
				graphics.blit(Fpt.resource("textures/jei/gui/switching.png"),
					x+i*18, y, 0, 0, 18, 18);
			}
		};

		if (item_count > 0 && item_count <= 7) {
			draw_row.accept(item_count, 36);
			draw_row.accept(item_count, 100);
		} else if (item_count <= 14) {
			draw_row.accept(item_count-7, 18);
			draw_row.accept(7, 36);

			draw_row.accept(7, 100);
			draw_row.accept(item_count-7, 118);
		} else {
			draw_row.accept(item_count-14, 0);
			draw_row.accept(7, 18);
			draw_row.accept(7, 36);

			draw_row.accept(7, 100);
			draw_row.accept(7, 118);
			draw_row.accept(item_count-14, 136);
		}

		graphics.blit(Fpt.resource("textures/jei/gui/switching.png"),
			47, 61, 0, 32, 32, 32);
	}
}
