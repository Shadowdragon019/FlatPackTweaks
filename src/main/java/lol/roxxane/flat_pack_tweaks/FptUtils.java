package lol.roxxane.flat_pack_tweaks;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.toml.TomlFormat;

import java.util.HashMap;

@SuppressWarnings("unused")
public class FptUtils {
	@SuppressWarnings("unchecked")
	public static <K, V> HashMap<K, V> new_hash_map(Object... objects) {
		var hashmap = new HashMap<K, V>();
		Object key = null;
		var is_key = true;
		for (var object : objects) {
			if (is_key) {key = object; is_key = false;}
			else { hashmap.put((K) key, (V) object); is_key = true; }
		}
		return hashmap;
	}

	public static CommentedConfig new_config(HashMap<String, Object> map) {
		return TomlFormat.newConfig(() -> map);
	}

	public static CommentedConfig new_config(Object... objects) {
		return TomlFormat.newConfig(() -> new_hash_map(objects));
	}
}
