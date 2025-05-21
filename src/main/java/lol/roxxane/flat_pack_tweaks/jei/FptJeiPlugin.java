package lol.roxxane.flat_pack_tweaks.jei;

import com.simibubi.create.AllBlocks;
import lol.roxxane.flat_pack_tweaks.Fpt;
import lol.roxxane.flat_pack_tweaks.config.server.FptServerConfig;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class FptJeiPlugin implements IModPlugin {
	public static final ResourceLocation ID = Fpt.resource(Fpt.ID);
	public static final ResourceLocation SWITCHING_TEXTURE_RESOURCE =
		Fpt.resource("textures/jei/gui/switching.png");
	public static final ResourceLocation INFINI_DRILLING_TEXTURE_RESOURCE =
		Fpt.resource("textures/jei/gui/drilling_drill.png");

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
		registration.addRecipes(FptJeiRecipeTypes.INFINI_DRILLING, FptServerConfig.INFINI_DRILLING_RECIPES.get());
		registration.addRecipes(FptJeiRecipeTypes.SWITCHING, FptServerConfig.SWITCHERS.get());
	}

	@Override
	public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(AllBlocks.MECHANICAL_DRILL), FptJeiRecipeTypes.INFINI_DRILLING);
	}
}
