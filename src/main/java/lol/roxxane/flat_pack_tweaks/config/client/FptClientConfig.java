package lol.roxxane.flat_pack_tweaks.config.client;

import net.minecraftforge.common.ForgeConfigSpec;

public class FptClientConfig {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

	public static final ForgeConfigSpec.IntValue VALUE_SETTINGS_DARK_COLOR =
		BUILDER.defineInRange("value_settings_dark_color", 0x442000, Integer.MIN_VALUE, Integer.MAX_VALUE);

	public static final ForgeConfigSpec SPEC = BUILDER.build();

}
