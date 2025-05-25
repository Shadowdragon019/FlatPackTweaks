package lol.roxxane.flat_pack_tweaks.accessor;

public interface LubeCountAccessor {
	int lube_count$get();
	void lube_count$set(int count);

	static int get(Object obj) {
		return ((LubeCountAccessor) obj).lube_count$get();
	}

	static void set(Object obj, int count) {
		((LubeCountAccessor) obj).lube_count$set(count);
	}
}
