package lol.roxxane.flat_pack_tweaks.config;

import com.google.common.base.Preconditions;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

import java.util.ArrayList;
import java.util.function.Function;

public class FptConfigValue<C, R> {
	public static final ArrayList<FptConfigValue<?,?>> VALUES = new ArrayList<>();

	public ConfigValue<C> config_value;
	public Function<C, R> transformer;
	protected R cached_value = null;

	public FptConfigValue(ConfigValue<C> config_value, Function<C, R> transformer) {
		VALUES.add(this);
		this.config_value = config_value;
		this.transformer = transformer;
	}

	public R get() {
		if (cached_value == null)
			cached_value = transformer.apply(config_value.get());
		return Preconditions.checkNotNull(cached_value, "Cannot get config value before val");
	}
}