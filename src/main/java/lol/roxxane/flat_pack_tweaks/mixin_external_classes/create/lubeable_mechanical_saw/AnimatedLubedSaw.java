package lol.roxxane.flat_pack_tweaks.mixin_external_classes.create.lubeable_mechanical_saw;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.content.kinetics.saw.SawBlock;
import lol.roxxane.flat_pack_tweaks.FptPartialModels;
import lol.roxxane.flat_pack_tweaks.FptStateProperties;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Direction;

public class AnimatedLubedSaw extends AnimatedKinetics {
	private AnimatedLubedSaw() {}

	public static final AnimatedLubedSaw INSTANCE = new AnimatedLubedSaw();

	@Override
	public void draw(GuiGraphics graphics, int xOffset, int yOffset) {
		PoseStack matrixStack = graphics.pose();
		matrixStack.pushPose();
		matrixStack.translate(xOffset, yOffset, 0);
		matrixStack.translate(0, 0, 200);
		matrixStack.translate(2, 22, 0);
		matrixStack.mulPose(Axis.XP.rotationDegrees(-15.5f));
		matrixStack.mulPose(Axis.YP.rotationDegrees(22.5f + 90));
		int scale = 25;

		blockElement(shaft(Direction.Axis.X))
			.rotateBlock(-getCurrentAngle(), 0, 0)
			.scale(scale)
			.render(graphics);

		blockElement(AllBlocks.MECHANICAL_SAW.getDefaultState()
			.setValue(SawBlock.FACING, Direction.UP)
			.setValue(FptStateProperties.LUBED, true))
			.rotateBlock(0, 0, 0)
			.scale(scale)
			.render(graphics);

		blockElement(FptPartialModels.SAW_BLADE_VERTICAL_ACTIVE)
			.rotateBlock(0, -90, -90)
			.scale(scale)
			.render(graphics);

		matrixStack.popPose();
	}

}
