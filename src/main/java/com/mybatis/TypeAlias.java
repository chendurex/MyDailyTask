package com.mybatis;

import com.google.common.collect.ImmutableMap;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author chen
 * @description
 * @pachage com.mybatis
 * @date 2016/12/23 10:00
 */
public class TypeAlias {
    private static final Map<Class<?>, String> tableAlias = new HashMap<>();
    private static final Map<Class<?>, Map<String, String>> fieldAlias = new HashMap<>();

    /**
     * 获取表名
     * 如果有别名则取别名
     * @param clazz
     * @return
     */
    public static String getTable(Class<?> clazz) {
        return tableAlias.get(clazz) == null ? Utils.javaFieldToTableField(clazz.getSimpleName()) : tableAlias.get(clazz);
    }

    /**
     * 获取表字段名称
     * 如果有别名则取别名
     * @param clazz
     * @param name
     * @return
     */
    public static String getField(Class<?> clazz, String name) {
        return fieldAlias.get(clazz) == null ? Utils.javaFieldToTableField(name) : fieldAlias.get(clazz).get(name);
    }

    /**
     * 注册字段别名
     * @param clazz
     * @param fieldMap key = javaProperty, value = table_field
     */
    public static void registryField(Class<?> clazz, Map<String, String> fieldMap) {
        Map<String, String> fields = fieldAlias.get(clazz);
        if (fields == null) {
            throw  new IllegalArgumentException();
        }
        for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
            String value = fields.get(entry.getKey());
            if (value == null) {
                throw new IllegalArgumentException();
            } else {
                fields.put(entry.getKey(), entry.getValue());
            }
        }
    }

    public static void registryField(Class<?> clazz, String javaField, String tableField) {
        registryField(clazz, ImmutableMap.of(javaField, tableField));
    }

    public static void registryField(Class<?> clazz, String... objects) {
        if (objects == null || objects.length == 0 || objects.length % 2 != 0) {
            throw new IllegalArgumentException();
        }
        for (int i = 0, len = objects.length; i < len;) {
            registryField(clazz, objects[i++], objects[i++]);
        }
    }

    private static void registryField(Class<?> clazz) {
        Set<Field> fields = Utils.getAllFields(clazz);
        Map<String, String> map = new HashMap<>();
        for (Field field : fields) {
            map.put(field.getName(), Utils.javaFieldToTableField(field.getName()));
        }
        fieldAlias.put(clazz, map);
    }

    /**
     * 注册表别名
     * @param alias
     */
    public static void registryTable(Map<Class<?>, String> alias) {
        tableAlias.putAll(alias);
        for (Class<?> clazz : alias.keySet()) {
            registryField(clazz);
        }
    }

    public static void registryTable(Class<?> clazz, String table) {
        registryTable(ImmutableMap.of(clazz, table));
    }

    public static void registryTable(Object... objects) {
        if (objects == null || objects.length == 0 || objects.length % 2 != 0) {
            throw new IllegalArgumentException();
        }
        for (int i = 0, len = objects.length; i < len;) {
            registryTable(ImmutableMap.<Class<?>, String>of((Class<?>) objects[i++], String.valueOf(objects[i++])));
        }
    }
}
