package lol.roxxane.flat_pack_tweaks.jei;

import com.simibubi.create.AllBlocks;
import lol.roxxane.flat_pack_tweaks.Fpt;
import lol.roxxane.flat_pack_tweaks.config.FptConfig;
import lol.roxxane.flat_pack_tweaks.recipes.SwitchingRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@JeiPlugin
public class FptJeiPlugin implements IModPlugin {
	public static final ResourceLocation ID = Fpt.resource(Fpt.ID);

	@Override
	public @NotNull ResourceLocation getPluginUid() {
		return ID;
	}

	@Override
	public void registerCategories(@NotNull IRecipeCategoryRegistration registration) {
		var jei_helpers = registration.getJeiHelpers();
		var gui_helper = jei_helpers.getGuiHelper();

		registration.addRecipeCategories(new InfiniDrillingRecipeCategory(gui_helper));
		registration.addRecipeCategories(new SwitchingRecipeCategory(gui_helper));
	}

	@Override
	public void registerRecipes(@NotNull IRecipeRegistration registration) {
		registration.addRecipes(FptJeiRecipeTypes.INFINI_DRILLING, FptConfig.INFINI_DRILLING_RECIPES.get());
		//registration.addRecipes(FptJeiRecipeTypes.SWITCHING, FptConfig.SWITCHERS.get());

		final var all_items = new ArrayList<Item>();
		all_items.add(Items.STONE);
		all_items.add(Items.GRANITE);
		all_items.add(Items.POLISHED_GRANITE);
		all_items.add(Items.DIORITE);
		all_items.add(Items.POLISHED_DIORITE);
		all_items.add(Items.ANDESITE);
		all_items.add(Items.POLISHED_ANDESITE);
		all_items.add(Items.DEEPSLATE);
		all_items.add(Items.COBBLED_DEEPSLATE);
		all_items.add(Items.POLISHED_DEEPSLATE);
		all_items.add(Items.CALCITE);
		all_items.add(Items.TUFF);
		all_items.add(Items.DRIPSTONE_BLOCK);
		all_items.add(Items.GRASS_BLOCK);
		all_items.add(Items.DIRT);
		all_items.add(Items.COARSE_DIRT);
		all_items.add(Items.PODZOL);
		all_items.add(Items.ROOTED_DIRT);
		all_items.add(Items.MUD);
		all_items.add(Items.CRIMSON_NYLIUM);
		all_items.add(Items.WARPED_NYLIUM);

		final var switching_recipes = new ArrayList<SwitchingRecipe>();
		var list_index = 0;

		while (list_index < 21) {
			list_index++;
			var item_index = 0;
			var items = new ArrayList<Item>();
			while (item_index < list_index) {
				item_index++;
				items.add(all_items.get(item_index-1));
			}
			switching_recipes.add(new SwitchingRecipe(items));
		}

		registration.addRecipes(FptJeiRecipeTypes.SWITCHING, switching_recipes);
	}

	@Override
	public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(AllBlocks.MECHANICAL_DRILL), FptJeiRecipeTypes.INFINI_DRILLING);
	}
}
