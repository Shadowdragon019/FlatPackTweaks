package lol.roxxane.flat_pack_tweaks;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;

public class FptPartialModels {
	public static final PartialModel
		SAW_BLADE_HORIZONTAL_ACTIVE = block("mechanical_saw/blade_horizontal_active"),
		SAW_BLADE_HORIZONTAL_INACTIVE = block("mechanical_saw/blade_horizontal_inactive"),
		SAW_BLADE_HORIZONTAL_REVERSED = block("mechanical_saw/blade_horizontal_reversed"),
		SAW_BLADE_VERTICAL_ACTIVE = block("mechanical_saw/blade_vertical_active"),
		SAW_BLADE_VERTICAL_INACTIVE = block("mechanical_saw/blade_vertical_inactive"),
		SAW_BLADE_VERTICAL_REVERSED = block("mechanical_saw/blade_vertical_reversed");

	private static PartialModel block(String path) {
		var model = PartialModel.of(Fpt.resource("block/" + path));
		Fpt.log(model.modelLocation());
		return model;
	}
}