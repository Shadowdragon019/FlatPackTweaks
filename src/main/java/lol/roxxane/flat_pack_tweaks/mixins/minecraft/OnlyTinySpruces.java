package lol.roxxane.flat_pack_tweaks.mixins.minecraft;

import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.grower.SpruceTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SaplingBlock.class)
abstract class OnlyTinySpruces {
	static {
		new AbstractTreeGrower() {
			@Override
			protected @NotNull ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource $, boolean $1) {
				return TreeFeatures.SPRUCE;
			}
		};
	}

	@ModifyVariable(method = "<init>",
		argsOnly = true,
		at = @At("HEAD"))
	private static AbstractTreeGrower fpt$new$ModifyVariable(AbstractTreeGrower original_tree_grower) {
		if (original_tree_grower instanceof SpruceTreeGrower)
			return new AbstractTreeGrower() {
				@Override
				protected @NotNull ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource $, boolean $1) {
					return TreeFeatures.SPRUCE;
				}
			};
		return original_tree_grower;
	}
}
