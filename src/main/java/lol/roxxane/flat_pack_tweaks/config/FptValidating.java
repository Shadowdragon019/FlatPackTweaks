package lol.roxxane.flat_pack_tweaks.config;

import com.electronwill.nightconfig.core.UnmodifiableConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

// Try to reduce the amount of calls to pre-existing functions to improve readability.
// As much as I love it, don't be clever.
@SuppressWarnings("unused")
public final class FptValidating {
	public static final Predicate<Object> is_bool = o -> o instanceof Boolean;
	public static final Predicate<Object> is_int = o -> o instanceof Integer;
	public static final Predicate<Object> is_double = o -> o instanceof Double;
	public static final Predicate<Object> is_string = o -> o instanceof String;
	public static final Predicate<Object> is_item = FptValidating::validate_item;
	public static final Predicate<Object> is_block = FptValidating::validate_block;
	public static final Predicate<Object> is_fluid = FptValidating::validate_fluid;
	public static final Predicate<Object> is_list = FptValidating::validate_list;
	public static final Predicate<Object> is_map = FptValidating::validate_map;

	// Bool
	public static boolean validate_nullable_bool(Object o) {
		return o == null || o instanceof Boolean;
	}
	public static boolean validate_bool(Object o) {
		return o instanceof Boolean;
	}

	// Int
	public static boolean validate_nullable_int(Object o, Predicate<Integer> p) {
		return o == null || o instanceof Integer i && p.test(i);
	}
	public static boolean validate_nullable_int(Object o) {
		return o == null || o instanceof Integer;
	}
	public static boolean validate_int(Object o, Predicate<Integer> p) {
		return o instanceof Integer i && p.test(i);
	}
	public static boolean validate_int(Object o) {
		return o instanceof Integer;
	}

	// Double
	public static boolean validate_nullable_double(Object o, Predicate<Double> p) {
		return o == null || o instanceof Double d && p.test(d);
	}
	public static boolean validate_nullable_double(Object o) {
		return o == null || o instanceof Double;
	}
	public static boolean validate_double(Object o, Predicate<Double> p) {
		return o instanceof Double d && p.test(d);
	}
	public static boolean validate_double(Object o) {
		return o instanceof Double;
	}

	// String
	public static boolean validate_nullable_string(Object o, Predicate<String> p) {
		return o == null || o instanceof String s && p.test(s);
	}
	public static boolean validate_nullable_string(Object o) {
		return o == null || o instanceof String;
	}
	public static boolean validate_string(Object o, Predicate<String> p) {
		return o instanceof String s && p.test(s);
	}
	public static boolean validate_string(Object o) {
		return o instanceof String;
	}

	// Registry
	public static <T> boolean validate_nullable_registry(Object o, IForgeRegistry<T> registry, Predicate<T> p) {
		if (o == null) return true;
		else if (o instanceof String s) {
			var resource = ResourceLocation.parse(s);
			return registry.containsKey(resource) && p.test(registry.getValue(resource));
		} else return false;
	}
	public static <T> boolean validate_nullable_registry(Object o, IForgeRegistry<T> registry) {
		return o == null || o instanceof String s && registry.containsKey(ResourceLocation.parse(s));
	}
	public static <T> boolean validate_registry(Object o, IForgeRegistry<T> registry, Predicate<T> p) {
		if (o instanceof String s) {
			var resource = ResourceLocation.parse(s);
			return registry.containsKey(resource) && p.test(registry.getValue(resource));
		} else return false;
	}
	public static <T> boolean validate_registry(Object o, IForgeRegistry<T> registry) {
		return o instanceof String s && registry.containsKey(ResourceLocation.parse(s));
	}

	// Item
	public static boolean validate_nullable_item(Object o, Predicate<Item> p) {
		return validate_nullable_registry(o, ForgeRegistries.ITEMS, p);
	}
	public static boolean validate_nullable_item(Object o) {
		return validate_nullable_registry(o, ForgeRegistries.ITEMS);
	}
	public static boolean validate_item(Object o, Predicate<Item> p) {
		return validate_registry(o, ForgeRegistries.ITEMS, p);
	}
	public static boolean validate_item(Object o) {
		return validate_registry(o, ForgeRegistries.ITEMS);
	}

	// Block
	public static boolean validate_nullable_block(Object o, Predicate<Block> p) {
		return validate_nullable_registry(o, ForgeRegistries.BLOCKS, p);
	}
	public static boolean validate_nullable_block(Object o) {
		return validate_nullable_registry(o, ForgeRegistries.BLOCKS);
	}
	public static boolean validate_block(Object o, Predicate<Block> p) {
		return validate_registry(o, ForgeRegistries.BLOCKS, p);
	}
	public static boolean validate_block(Object o) {
		return validate_registry(o, ForgeRegistries.BLOCKS);
	}

	// Fluid
	public static boolean validate_nullable_fluid(Object o, Predicate<Fluid> p) {
		return validate_nullable_registry(o, ForgeRegistries.FLUIDS, p);
	}
	public static boolean validate_nullable_fluid(Object o) {
		return validate_nullable_registry(o, ForgeRegistries.FLUIDS);
	}
	public static boolean validate_fluid(Object o, Predicate<Fluid> p) {
		return validate_registry(o, ForgeRegistries.FLUIDS, p);
	}
	public static boolean validate_fluid(Object o) {
		return validate_registry(o, ForgeRegistries.FLUIDS);
	}

	// List
	@SuppressWarnings("unchecked")
	public static boolean validate_nullable_list(Object o, Predicate<List<Object>> p) {
		return o == null || o instanceof List<?> l && p.test((List<Object>) l);
	}
	public static boolean validate_nullable_list(Object o) {
		return o == null || o instanceof List<?>;
	}
	@SuppressWarnings("unchecked")
	public static boolean validate_list(Object o, Predicate<List<Object>> p) {
		return o instanceof List<?> l && p.test((List<Object>) l);
	}
	public static boolean validate_list(Object o) {
		return o instanceof List<?>;
	}

	public static boolean validate_elements(List<Object> i, Predicate<Object> p) {
		for (var o : i)
			if (!p.test(o))
				return false;
		return true;
	}

	// Map
	@SuppressWarnings("unchecked")
	public static boolean validate_nullable_map(Object o, Predicate<Map<String, Object>> p) {
		return o == null || o instanceof UnmodifiableConfig c && p.test(c.valueMap()) ||
			o instanceof Map<?,?> m && p.test((Map<String, Object>) m);
	}
	public static boolean validate_nullable_map(Object o) {
		return o == null || o instanceof UnmodifiableConfig || o instanceof Map<?,?>;
	}
	@SuppressWarnings("unchecked")
	public static boolean validate_map(Object o, Predicate<Map<String, Object>> p) {
		return o instanceof UnmodifiableConfig c && p.test(c.valueMap()) ||
			o instanceof Map<?,?> m && p.test((Map<String, Object>) m);
	}
	public static boolean validate_map(Object o) {
		return o instanceof UnmodifiableConfig || o instanceof Map<?,?>;
	}

	public static boolean validate_nullable_entry(Map<String, Object> m, String k, Predicate<Object> p) {
		var v = m.get(k);
		return v == null || p.test(v);
	}
	public static boolean validate_entry(Map<String, Object> m, String k, Predicate<Object> p) {
		var v = m.get(k);
		return v != null && p.test(v);
	}

	/** Applies {@code predicate} to every entry in the map */
	public static boolean validate_entries(Map<String, Object> map, BiPredicate<String, Object> predicate) {
		if (map == null) return false;
		for (var entry : map.entrySet())
			if (!predicate.test(entry.getKey(), entry.getValue()))
				return false;
		return true;
	}
	/** Applies {@code key_predicate} to every key in the map & {@code value_predicate} to every value in the map */
	public static boolean validate_entries(Map<String, Object> map, Predicate<String> key_predicate,
		Predicate<Object> value_predicate) {
		if (map == null) return false;
		for (var e : map.entrySet())
			if (!key_predicate.test(e.getKey()) || !value_predicate.test(e.getValue()))
				return false;
		return true;
	}

	public static boolean validate_keys(Map<String, ?> map, Predicate<String> predicate) {
		if (map == null) return false;
		for (var key : map.keySet())
			if (!predicate.test(key))
				return false;
		return true;
	}
	public static boolean validate_values(Map<?, Object> map, Predicate<Object> predicate) {
		if (map == null) return false;
		for (var value : map.values())
			if (!predicate.test(value))
				return false;
		return true;
	}
}