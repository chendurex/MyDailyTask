package com.design.patterns.prototype_patterns;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.prototype_patterns
 * @date 2016/4/7 9:22
 */
public class CacheShape {
    private static Map<Integer,IShape> cacheShape = new HashMap<>(4);
    public static void loadCache(Integer id,IShape shape) {
        cacheShape.put(id,shape);
    }
    public static IShape getCache(Integer id) {
        return cacheShape.get(id);
    }
}
