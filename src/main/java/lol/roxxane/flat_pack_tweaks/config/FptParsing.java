package lol.roxxane.flat_pack_tweaks.config;

import com.electronwill.nightconfig.core.UnmodifiableConfig;
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
import java.util.function.Function;

// Try to reduce the amount of calls to pre-existing functions to improve readability.
// As much as I love it, don't be clever.
@SuppressWarnings("unused")
public final class FptParsing {
	public static final Function<Object, Boolean> parse_bool = FptParsing::parse_bool;
	public static final Function<Object, Integer> parse_int = FptParsing::parse_int;
	public static final Function<Object, Double> parse_double = FptParsing::parse_double;
	public static final Function<Object, String> parse_string = FptParsing::parse_string;
	public static final Function<Object, Item> parse_item = FptParsing::parse_item;
	public static final Function<Object, Block> parse_block = FptParsing::parse_block;
	public static final Function<Object, Fluid> parse_fluid = FptParsing::parse_fluid;
	public static final Function<Object, List<Object>> parse_list = FptParsing::parse_list;
	public static final Function<Object, Map<String, Object>> parse_map = FptParsing::parse_map;

	// Bool
	public static <R> R parse_optional_bool(Object o, Function<Boolean, R> func, R _default) {
		if (o == null) return _default;
		else if (o instanceof Boolean b) return func.apply(b);
		else throw new IllegalArgumentException("Could not parse bool as object was instead " + o.getClass());
	}
	public static boolean parse_optional_bool(Object o, boolean _default) {
		if (o == null) return _default;
		else if (o instanceof Boolean b) return b;
		else throw new IllegalArgumentException("Could not parse bool as object was instead " + o.getClass());
	}
	public static <R> R parse_bool(Object o, Function<Boolean, R> func) {
		if (o == null) throw new IllegalArgumentException("Could not parse bool as object was null");
		else if (o instanceof Boolean b) return func.apply(b);
		else throw new IllegalArgumentException("Could not parse bool as object was instead " + o.getClass());
	}
	public static boolean parse_bool(Object o) {
		if (o == null) throw new IllegalArgumentException("Could not parse bool as object was null");
		else if (o instanceof Boolean b) return b;
		else throw new IllegalArgumentException("Could not parse bool as object was instead " + o.getClass());
	}

	// Int
	public static <R> R parse_optional_int(Object o, Function<Integer, R> func, R _default) {
		if (o == null) return _default;
		else if (o instanceof Integer i) return func.apply(i);
		else throw new IllegalArgumentException("Could not parse int as object was instead " + o.getClass());
	}
	public static int parse_optional_int(Object o, int _default) {
		if (o == null) return _default;
		else if (o instanceof Integer i) return i;
		else throw new IllegalArgumentException("Could not parse int as object was instead " + o.getClass());
	}
	public static <R> R parse_int(Object o, Function<Integer, R> func) {
		if (o == null) throw new IllegalArgumentException("Could not parse int as object was null");
		else if (o instanceof Integer i) return func.apply(i);
		else throw new IllegalArgumentException("Could not parse int as object was instead " + o.getClass());
	}
	public static int parse_int(Object o) {
		if (o == null) throw new IllegalArgumentException("Could not parse int as object was null");
		else if (o instanceof Integer i) return i;
		else throw new IllegalArgumentException("Could not parse int as object was instead " + o.getClass());
	}

	// Double
	public static <R> R parse_optional_double(Object o, Function<Double, R> func, R _default) {
		if (o == null) return _default;
		else if (o instanceof Double d) return func.apply(d);
		else throw new IllegalArgumentException("Could not parse double as object was instead " + o.getClass());
	}
	public static double parse_optional_double(Object o, double _default) {
		if (o == null) return _default;
		else if (o instanceof Double d) return d;
		else throw new IllegalArgumentException("Could not parse double as object was instead " + o.getClass());
	}
	public static <R> R parse_double(Object o, Function<Double, R> func) {
		if (o == null) throw new IllegalArgumentException("Could not parse double as object was null");
		else if (o instanceof Double d) return func.apply(d);
		else throw new IllegalArgumentException("Could not parse double as object was instead " + o.getClass());
	}
	public static double parse_double(Object o) {
		if (o == null) throw new IllegalArgumentException("Could not parse double as object was null");
		else if (o instanceof Double d) return d;
		else throw new IllegalArgumentException("Could not parse double as object was instead " + o.getClass());
	}

	// String
	public static <R> R parse_optional_string(Object o, Function<String, R> func, R _default) {
		if (o == null) return _default;
		else if (o instanceof String s) return func.apply(s);
		else throw new IllegalArgumentException("Could not parse string as object was instead " + o.getClass());
	}
	public static String parse_optional_string(Object o, String _default) {
		if (o == null) return _default;
		else if (o instanceof String s) return s;
		else throw new IllegalArgumentException("Could not parse string as object was instead " + o.getClass());
	}
	public static <R> R parse_string(Object o, Function<String, R> func) {
		if (o == null) throw new IllegalArgumentException("Could not parse string as object was null");
		else if (o instanceof String s) return func.apply(s);
		else throw new IllegalArgumentException("Could not parse string as object was instead " + o.getClass());
	}
	public static String parse_string(Object o) {
		if (o == null) throw new IllegalArgumentException("Could not parse string as object was null");
		else if (o instanceof String s) return s;
		else throw new IllegalArgumentException("Could not parse string as object was instead " + o.getClass());
	}

	// Registry
	public static <T, R> R parse_optional_registry(Object o, IForgeRegistry<T> registry, Function<T, R> func,
		R _default
	) {
		return parse_optional_string(o, s -> func.apply(registry.getValue(ResourceLocation.parse(s))), _default);
	}
	public static <T> T parse_optional_registry(Object o, IForgeRegistry<T> registry, T _default) {
		return parse_optional_string(o, s -> registry.getValue(ResourceLocation.parse(s)), _default);
	}
	public static <T, R> R parse_registry(Object o, IForgeRegistry<T> registry, Function<T, R> func) {
		return parse_string(o, s -> func.apply(registry.getValue(ResourceLocation.parse(s))));
	}
	public static <T> T parse_registry(Object o, IForgeRegistry<T> registry) {
		return parse_string(o, s -> registry.getValue(ResourceLocation.parse(s)));
	}

	// Item
	public static <R> R parse_optional_item(Object o, Function<Item, R> func, R _default) {
		return parse_optional_registry(o, ForgeRegistries.ITEMS, func, _default);
	}
	public static Item parse_optional_item(Object o, Item _default) {
		return parse_optional_registry(o, ForgeRegistries.ITEMS, _default);
	}
	public static <R> R parse_item(Object o, Function<Item, R> func) {
		return parse_registry(o, ForgeRegistries.ITEMS, func);
	}
	public static Item parse_item(Object o) {
		return parse_registry(o, ForgeRegistries.ITEMS);
	}

	// Block
	public static <R> R parse_optional_block(Object o, Function<Block, R> func, R _default) {
		return parse_optional_registry(o, ForgeRegistries.BLOCKS, func, _default);
	}
	public static Block parse_optional_block(Object o, Block _default) {
		return parse_optional_registry(o, ForgeRegistries.BLOCKS, _default);
	}
	public static <R> R parse_block(Object o, Function<Block, R> func) {
		return parse_registry(o, ForgeRegistries.BLOCKS, func);
	}
	public static Block parse_block(Object o) {
		return parse_registry(o, ForgeRegistries.BLOCKS);
	}

	// Fluid
	public static <R> R parse_optional_fluid(Object o, Function<Fluid, R> func, R _default) {
		return parse_optional_registry(o, ForgeRegistries.FLUIDS, func, _default);
	}
	public static Fluid parse_optional_fluid(Object o, Fluid _default) {
		return parse_optional_registry(o, ForgeRegistries.FLUIDS, _default);
	}
	public static <R> R parse_fluid(Object o, Function<Fluid, R> func) {
		return parse_registry(o, ForgeRegistries.FLUIDS, func);
	}
	public static Fluid parse_fluid(Object o) {
		return parse_registry(o, ForgeRegistries.FLUIDS);
	}
	
	// List
	@SuppressWarnings("unchecked")
	public static <R> R parse_optional_list(Object o, Function<List<Object>, R> func, R _default) {
		if (o == null) return _default;
		else if (o instanceof List<?> l) return func.apply((List<Object>) l);
		else throw new IllegalArgumentException("Could not parse list as object was instead " + o.getClass());
	}
	@SuppressWarnings("unchecked")
	public static List<Object> parse_optional_list(Object o, List<Object> _default) {
		if (o == null) return _default;
		else if (o instanceof List<?> l) return (List<Object>) l;
		else throw new IllegalArgumentException("Could not parse list as object was instead " + o.getClass());
	}
	@SuppressWarnings("unchecked")
	public static <R> R parse_list(Object o, Function<List<Object>, R> func) {
		if (o == null) throw new IllegalArgumentException("Could not parse list as object was null");
		else if (o instanceof List<?> l) return func.apply((List<Object>) l);
		else throw new IllegalArgumentException("Could not parse list as object was instead " + o.getClass());
	}
	@SuppressWarnings("unchecked")
	public static List<Object> parse_list(Object o) {
		if (o == null) throw new IllegalArgumentException("Could not parse list as object was null");
		else if (o instanceof List<?> l) return (List<Object>) l;
		else throw new IllegalArgumentException("Could not parse list as object was instead " + o.getClass());
	}

	/** Parses elements to a list */
	public static <E, R> List<R> parse_elements(List<E> i, Function<E, R> func) {
		var list = new ArrayList<R>();
		for (var e : i)
			list.add(func.apply(e));
		return list;
	}
	/** Parses elements to a map */
	public static <E, K, V> Map<K, V> parse_elements(List<E> i, Function<Object, K> k_func,
		Function<Object, V> v_func
	) {
		var map = new HashMap<K, V>();
		for (var e : i)
			map.put(k_func.apply(e), v_func.apply(e));
		return map;
	}

	// Map
	@SuppressWarnings("unchecked")
	public static <R> R parse_optional_map(Object o, Function<Map<String, Object>, R> func, R _default) {
		if (o == null) return _default;
		else if (o instanceof Map<?,?> m) return func.apply((Map<String, Object>) m);
		else if (o instanceof UnmodifiableConfig c) return func.apply(c.valueMap());
		else throw new IllegalArgumentException("Could not parse map as object was instead " + o.getClass());
	}
	@SuppressWarnings("unchecked")
	public static Map<String, Object> parse_optional_map(Object o, Map<String, Object> _default) {
		if (o == null) return _default;
		else if (o instanceof Map<?,?> m) return (Map<String, Object>) m;
		else if (o instanceof UnmodifiableConfig c) return c.valueMap();
		else throw new IllegalArgumentException("Could not parse map as object was instead " + o.getClass());
	}
	@SuppressWarnings("unchecked")
	public static <R> R parse_map(Object o, Function<Map<String, Object>, R> func) {
		if (o == null) throw new IllegalArgumentException("Could not parse map as object was null");
		else if (o instanceof Map<?,?> m) return func.apply((Map<String, Object>) m);
		else if (o instanceof UnmodifiableConfig c) return func.apply(c.valueMap());
		else throw new IllegalArgumentException("Could not parse map as object was instead " + o.getClass());
	}
	@SuppressWarnings("unchecked")
	public static Map<String, Object> parse_map(Object o) {
		if (o == null) throw new IllegalArgumentException("Could not parse map as object was null");
		else if (o instanceof Map<?,?> m) return (Map<String, Object>) m;
		else if (o instanceof UnmodifiableConfig c) return c.valueMap();
		else throw new IllegalArgumentException("Could not parse map as object was instead " + o.getClass());
	}

	public static <K, V, R> R parse_optional_entry(Map<K, V> m, K k, Function<V, R> func, R _default) {
		if (m.containsKey(k)) return func.apply(m.get(k));
		else return _default;
	}
	public static <K, V> V parse_optional_entry(Map<K, V> m, K k, V _default) {
		return m.getOrDefault(k, _default);
	}
	public static <K, V, R> R parse_entry(Map<K, V> m, K k, Function<V, R> func) {
		if (m.containsKey(k)) return func.apply(m.get(k));
		else throw new IllegalArgumentException("Could not parse entry as key \"" + k + "\" could not be found");
	}
	public static <K, V> V parse_entry(Map<K, V> m, K k) {
		if (m.containsKey(k)) return m.get(k);
		else throw new IllegalArgumentException("Could not parse entry as key \"" + k + "\" could not be found");
	}

	public static <K, V, R> List<R> parse_entries(Map<K, V> map, BiFunction<K, V, R> entry_function) {
		var list = new ArrayList<R>();
		for (var e : map.entrySet())
			list.add(entry_function.apply(e.getKey(), e.getValue()));
		return list;
	}
	public static <K, V, RK, RV> Map<RK, RV> parse_entries(Map<K, V> map, Function<K, RK> k_func,
		Function<V, RV> v_func
	) {
		var new_m = new HashMap<RK, RV>();
		for (var e : map.entrySet())
			new_m.put(k_func.apply(e.getKey()), v_func.apply(e.getValue())) ;
		return new_m;
	}
}