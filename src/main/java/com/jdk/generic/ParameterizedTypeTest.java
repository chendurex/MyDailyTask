package com.jdk.generic;

import org.junit.Test;
import org.objectweb.asm.TypeReference;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author chen
 * @description
 * @pachage com.jdk.generic
 * @date 2016/11/9 9:31
 */
public class ParameterizedTypeTest {
    public Type getSuperclassTypeParameter(Class<?> clazz) {
        Type genericSuperclass = clazz.getGenericSuperclass();

        if (genericSuperclass instanceof Class) {
            // try to climb up the hierarchy until meet something useful
            if (TypeReference.class != genericSuperclass) {
                return getSuperclassTypeParameter(clazz.getSuperclass());
            }
            throw new RuntimeException("'" + getClass() + "' extends TypeReference but misses the type parameter." +
                    "Remove the extension or add a type parameter to it.");
        }

        Type rawType = ((ParameterizedType)genericSuperclass).getActualTypeArguments()[0];
        // remove this when Reflector is fixed to return types
        if (rawType instanceof ParameterizedType) {
            rawType = ((ParameterizedType)rawType).getRawType();
        }
        return rawType;
    }

    @Test
    public void test() {
        System.out.println(getSuperclassTypeParameter(Arrays.class));
    }

    private class SelfHashMap<K, V> extends HashMap<K,V> {

    }
}
