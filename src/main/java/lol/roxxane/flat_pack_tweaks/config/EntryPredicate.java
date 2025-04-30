package lol.roxxane.flat_pack_tweaks.config;

import java.util.function.Predicate;

public record EntryPredicate(String key, Predicate<Object> predicate) {
	public static EntryPredicate of(String key, Predicate<Object> predicate) {
		return new EntryPredicate(key, predicate);
	}
}
