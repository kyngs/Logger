package com.github.kyngs.logger.util;

import java.util.Map;

public class MapUtil {

    public static <K, V> K getKeyByValue(V value, Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) return entry.getKey();
        }
        return null;
    }

}
