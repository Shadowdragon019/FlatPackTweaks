package lol.roxxane.flat_pack_tweaks.config;

import com.electronwill.nightconfig.core.UnmodifiableConfig;
import lol.roxxane.flat_pack_tweaks.Fpt;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
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
	@SuppressWarnings("unchecked")
	// TODO: Give information in console
	public static boolean validate_map(Object o, Predicate<Map<String, Object>> predicate) {
		if (o instanceof UnmodifiableConfig config) return predicate.test(config.valueMap());
		else if (o instanceof Map<?,?> map) return predicate.test((Map<String, Object>) map);
		else {
			Fpt.log("Object was not map, was " + o.getClass());
			return false;
		}
	}
	public static boolean validate_entries(Object o, BiPredicate<String, Object> predicate) {
		return validate_map(o, map -> {
			for (var entry : map.entrySet()) {
				var key = entry.getKey();
				var value = entry.getValue();
				if (!predicate.test(key, value))
					return false;
			}
			return true;
		});
	}
	@SuppressWarnings("unchecked")
	public static boolean validate_list(Object o, Predicate<List<Object>> predicate) {
		if (o instanceof List<?> list) return predicate.test((List<Object>) list);
		else {
			Fpt.log("Object was not list, was " + o.getClass());
			return false;
		}
	}
	public static boolean validate_elements(Object o, Predicate<Object> predicate) {
		return validate_list(o, list -> {
			for (var element : list)
				if (!predicate.test(element))
					return false;
			return true;
		});
	}
	public static boolean validate_string(Object o, Predicate<String> predicate) {
		return o instanceof String string && predicate.test(string);
	}
	public static boolean validate_required_entry(Object o, String key, Predicate<Object> predicate) {
		var result = validate_map(o, map -> map.containsKey(key) && predicate.test(map.get(key)));
		if (!result)
			Fpt.log("Key \"" + key + "\" failed");
		return result;
	}
	public static boolean validate_required_entries(Object o, EntryPredicate... pairs) {
		return validate_map(o, map -> {
			for (var pair : pairs)
				if (!validate_required_entry(map, pair.key(), pair.predicate()))
					return false;
			return true;
		});
	}
	public static boolean validate_optional_entry(Object o, String key, Predicate<Object> predicate) {
		return validate_map(o, map -> {
			if (map.containsKey(key))
				return predicate.test(map.get(key));
			else return true;
		});
	}
	public static boolean validate_optional_entries(Object o, EntryPredicate... pairs) {
		return validate_map(o, map -> {
			for (var pair : pairs)
				if (!validate_optional_entry(map, pair.key(), pair.predicate()))
					return false;
			return true;
		});
	}

	// Parsers
	public static <T> List<T> parse_elements_to_list(Object o, Function<Object, T> func) {
		if (o instanceof List<?> list) {
			var new_list = new ArrayList<T>();
			for (var element : list)
				new_list.add(func.apply(element));
			return new_list;
		} else throw new IllegalArgumentException("Object was not list, was " + o.getClass());
	}
	@SuppressWarnings("unchecked")
	public static <R> R parse_map(Object o, Function<Map<String, Object>, R> func) {
		if (o instanceof UnmodifiableConfig config)
			return func.apply(config.valueMap());
		else if (o instanceof Map<?,?> map)
			return func.apply((Map<String, Object>) map);
		else throw new IllegalArgumentException("Object was not map, was " + o.getClass());
	}
	public static <T> List<T> parse_entries_to_list(Object o, BiFunction<String, Object, T> func) {
		return parse_map(o, map -> {
			var list = new ArrayList<T>();
			for (var entry : map.entrySet())
				list.add(func.apply(entry.getKey(), entry.getValue()));
			return list;
		});
	}
	public static <K, V> Map<K, V> parse_entries_to_map(Object o, Function<String, K> key_func,
		Function<Object, V> value_func
	) {
		return parse_map(o, map -> {
			var return_map = new HashMap<K, V>();
			for (var entry : map.entrySet())
				return_map.put(key_func.apply(entry.getKey()), value_func.apply(entry.getValue()));
			return return_map;
		});
	}
	public static <R> R parse_entry(Object o, String key, Function<Object, R> func) {
		return parse_map(o, m -> func.apply(m.get(key)));
	}

	// Registry Utils
	public static <T> boolean registry_key_exists(IForgeRegistry<T> registry, Object o) {
		if (o instanceof ResourceLocation resource)
			return registry.containsKey(resource);
		else if (o instanceof String string)
			return registry.containsKey(ResourceLocation.parse(string));
		else return false;
	}
	public static <T> T registry_get_value(IForgeRegistry<T> registry, Object o) {
		if (o instanceof ResourceLocation resource)
			return registry.getValue(resource);
		else if (o instanceof String string)
			return registry.getValue(ResourceLocation.parse(string));
		else throw new IllegalArgumentException("Object was not an instance of ResourceLocation or String. Was " +
				o.getClass());
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
	public static boolean block_exists(Object object) {
		return registry_key_exists(ForgeRegistries.BLOCKS, object);
	}
	public static Fluid get_fluid(Object object) {
		return registry_get_value(ForgeRegistries.FLUIDS, object);
	}
	public static boolean fluid_exists(Object object) {
		return registry_key_exists(ForgeRegistries.FLUIDS, object);
	}
}
