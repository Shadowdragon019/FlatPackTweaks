package lol.roxxane.flat_pack_tweaks.mixins.create.lubeable_mechanical_saw;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.compat.jei.category.SawingCategory;
import com.simibubi.create.compat.jei.category.animations.AnimatedSaw;
import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import lol.roxxane.flat_pack_tweaks.LubeHelper;
import lol.roxxane.flat_pack_tweaks.mixin_external_classes.create.lubeable_mechanical_saw.AnimatedLubedSaw;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

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
}