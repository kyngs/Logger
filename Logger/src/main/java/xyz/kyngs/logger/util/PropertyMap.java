package xyz.kyngs.logger.util;

import java.util.HashMap;
import java.util.Map;

public class PropertyMap {

    private final Map<String, Object> map;

    public PropertyMap() {
        map = new HashMap<>();
    }

    public <T> void put(String key, T value) {
        map.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) map.get(key);
    }

}
