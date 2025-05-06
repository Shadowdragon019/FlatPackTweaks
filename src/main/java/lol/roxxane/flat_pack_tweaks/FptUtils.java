package lol.roxxane.flat_pack_tweaks;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.toml.TomlFormat;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("unused")
public class FptUtils {
	@SuppressWarnings("unchecked")
	public static <K, V> HashMap<K, V> mutable_map(Object... objects) {
		var hashmap = new HashMap<K, V>();
		Object key = null;
		var is_key = true;
		for (var object : objects) {
			if (is_key) {key = object; is_key = false;}
			else { hashmap.put((K) key, (V) object); is_key = true; }
		}
		return hashmap;
	}

	@SafeVarargs
	public static <T> ArrayList<T> mutable_list(T... elements) {
		return new ArrayList<>(List.of(elements));
	}

	public static CommentedConfig config(HashMap<String, Object> map) {
		return TomlFormat.newConfig(() -> map);
	}

	public static CommentedConfig config(Object... objects) {
		return TomlFormat.newConfig(() -> mutable_map(objects));
	}

	public static String resource_recipe_string(ResourceLocation resource) {
		if (resource == null) return "nothing";
		else return resource.getNamespace() + "_" + resource.getPath();
	}
}
