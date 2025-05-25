package lol.roxxane.flat_pack_tweaks.config.client;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.google.common.base.Preconditions;
import lol.roxxane.flat_pack_tweaks.config.FptParsing;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static lol.roxxane.flat_pack_tweaks.FptUtils.config;
import static lol.roxxane.flat_pack_tweaks.FptUtils.list;
import static lol.roxxane.flat_pack_tweaks.config.FptParsing.*;
import static lol.roxxane.flat_pack_tweaks.config.FptValidating.*;

public class FptClientConfig {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

	public static final ForgeConfigSpec.IntValue VALUE_SETTINGS_DARK_COLOR =
		BUILDER.defineInRange("value_settings_dark_color", 0x442000, Integer.MIN_VALUE, Integer.MAX_VALUE);

	public static final Value<CommentedConfig, Map<Item, List<Component>>> TOOLTIPS = Value.of(
		BUILDER.define("tooltips",
			config("minecraft:dirt", list("gui.flat_pack_tweaks.category.infini_drilling",
				"gui.flat_pack_tweaks.category.switching")),
			o -> validate_map(o, m -> validate_entries(m, is_item::test, is_list))),
		o -> parse_map(o, m ->
			FptParsing.parse_entries(m, parse_item::apply, v ->
				parse_list(v, l ->
					parse_elements(l, e ->
						parse_string(e, Component::translatable))))));

	public static final ForgeConfigSpec SPEC = BUILDER.build();

	public static final class Value<C, R> {
		public static final ArrayList<Value<?, ?>> ALL_VALUES = new ArrayList<>();

		public ConfigValue<C> config_value;
		public Function<C, R> transformer;
		private R cached_value = null;

		public Value(ConfigValue<C> config_value, Function<C, R> transformer) {
			ALL_VALUES.add(this);
			this.config_value = config_value;
			this.transformer = transformer;
		}

		public static <C, R> Value<C, R> of(ConfigValue<C> config_value, Function<C, R> transformer) {
			return new Value<>(config_value, transformer);
		}

		public R get() {
			if (cached_value == null)
				cached_value = transformer.apply(config_value.get());
			return Preconditions.checkNotNull(cached_value,
				"Cannot get config value before value is cached");
		}

		public void invalidate() {
			cached_value = null;
		}
	}

	@SubscribeEvent
	static void on_load(final ModConfigEvent event) {
		if (SPEC.isLoaded())
			for (var value : Value.ALL_VALUES)
				value.invalidate();
	}
}
