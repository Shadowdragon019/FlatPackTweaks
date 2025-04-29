package lol.roxxane.flat_pack_tweaks.config;

import com.electronwill.nightconfig.core.UnmodifiableConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

@SuppressWarnings("unused")
public class FptConfigUtil {
	// Validators
	public static boolean validate_map(Object object, Predicate<Map<String, Object>> predicate) {
		return object instanceof UnmodifiableConfig config && predicate.test(config.valueMap());
	}
	public static boolean validate_entries(Object object, BiPredicate<String, Object> predicate) {
		return validate_map(object, map -> {
			for (var entry : map.entrySet()) {
				var key = entry.getKey();
				var value = entry.getValue();
				if (!predicate.test(key, value))
					return false;
			}
			return true;
		});
	}
	public static boolean validate_string(Object object, Predicate<String> predicate) {
		return object instanceof String string && predicate.test(string);
	}

	// Parsers
	public static <T> T parse_map(Object object, Function<Map<String, Object>, T> func) {
		if (object instanceof UnmodifiableConfig config) {
			return func.apply(config.valueMap());
		} else throw new IllegalArgumentException("Object was not map, was " + object.getClass());
	}
	public static <T> List<T> parse_entries_to_list(Object object, BiFunction<String, Object, T> func) {
		if (object instanceof UnmodifiableConfig config) {
			var list = new ArrayList<T>();
			for (var entry : config.valueMap().entrySet())
				list.add(func.apply(entry.getKey(), entry.getValue()));
			return list;
		} else throw new IllegalArgumentException("Object was not map, was " + object.getClass());
	}
	public static <K, V> Map<K, V> parse_entries_to_map(Object object, Function<String, K> key_func,
		Function<Object, V> value_func
	) {
		if (object instanceof UnmodifiableConfig config) {
			var map = new HashMap<K, V>();
			for (var entry : config.valueMap().entrySet())
				map.put(key_func.apply(entry.getKey()), value_func.apply(entry.getValue()));
			return map;
		} else throw new IllegalArgumentException("Object was not map, was " + object.getClass());
	}

	// Registry Utils
	public static <T> boolean registry_key_exists(IForgeRegistry<T> registry, Object object) {
		if (object instanceof ResourceLocation resource)
			return registry.containsKey(resource);
		else if (object instanceof String string)
			return registry.containsKey(ResourceLocation.parse(string));
		else return false;
	}
	public static <T> T registry_get_value(IForgeRegistry<T> registry, Object object) {
		if (object instanceof ResourceLocation resource)
			return registry.getValue(resource);
		else if (object instanceof String string)
			return registry.getValue(ResourceLocation.parse(string));
		else throw new IllegalArgumentException("Object was not an instance of ResourceLocation or String. Was " +
				object.getClass());
	}
	public static Item get_item(Object object) {
		return registry_get_value(ForgeRegistries.ITEMS, object);
	}
	public static boolean item_exists(Object object) {
		return registry_key_exists(ForgeRegistries.ITEMS, object);
	}
	public static Block get_block(Object object) {
		return registry_get_value(ForgeRegistries.BLOCKS, object);
	}
	public static boolean blocks_exists(Object object) {
		return registry_key_exists(ForgeRegistries.BLOCKS, object);
	}
}
