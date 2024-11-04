package duncan.api.auto.context;

import java.util.concurrent.ConcurrentHashMap;

public class TestRunContext {
	private static ConcurrentHashMap<String, Object> hashMap = new ConcurrentHashMap<>();

	public static ConcurrentHashMap<String, Object> getHashMap() {
		return hashMap;
	}
}