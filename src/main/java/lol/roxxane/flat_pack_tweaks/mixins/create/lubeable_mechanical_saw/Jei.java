package lol.roxxane.flat_pack_tweaks.mixins.create.lubeable_mechanical_saw;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.compat.jei.category.SawingCategory;
import com.simibubi.create.compat.jei.category.animations.AnimatedSaw;
import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import lol.roxxane.flat_pack_tweaks.LubeHelper;
import lol.roxxane.flat_pack_tweaks.mixin_external_classes.create.lubeable_mechanical_saw.AnimatedLubedSaw;
import lol.roxxane.flat_pack_tweaks.tags.FptItemTags;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SawingCategory.class, remap = false)
abstract class Jei {
	@WrapWithCondition(method = "draw(Lcom/simibubi/create/content/kinetics/saw/CuttingRecipe;Lmezz/jei/api/gui/ingredient/IRecipeSlotsView;Lnet/minecraft/client/gui/GuiGraphics;DD)V",
		at = @At(value = "INVOKE",
			target = "Lcom/simibubi/create/compat/jei/category/animations/AnimatedSaw;draw(Lnet/minecraft/client/gui/GuiGraphics;II)V"))
	private boolean fpt$WrapWithCondition$draw(AnimatedSaw $, GuiGraphics graphics, int x_offset,
		int y_offset, @Local(argsOnly = true) CuttingRecipe recipe) {
		if (LubeHelper.requires_lube(recipe)) {
			AnimatedLubedSaw.INSTANCE.draw(graphics, x_offset, y_offset);
			return false;
		} else if (LubeHelper.requires_dry(recipe)) {
			graphics.drawString(Minecraft.getInstance().font,
				Component.translatable("gui.flat_pack_tweaks.category.sawing.dry_1"), 0, 50, 0xFFFFFF);
			graphics.drawString(Minecraft.getInstance().font,
				Component.translatable("gui.flat_pack_tweaks.category.sawing.dry_2"), 0, 60, 0xFFFFFF);
		}
		return true;
	}

	@Inject(method = "setRecipe(Lmezz/jei/api/gui/builder/IRecipeLayoutBuilder;Lcom/simibubi/create/content/kinetics/saw/CuttingRecipe;Lmezz/jei/api/recipe/IFocusGroup;)V",
		at = @At("HEAD"))
	private void fpt$Inject$setRecipe(IRecipeLayoutBuilder builder, CuttingRecipe recipe, IFocusGroup $,
		CallbackInfo $1) {
		if (LubeHelper.requires_lube(recipe))
			builder.addSlot(RecipeIngredientRole.INPUT, 32, 48)
				.setBackground(CreateRecipeCategory.getRenderedSlot(), -1, -1)
				.addIngredients(Ingredient.of(FptItemTags.LUBE));
	}
}