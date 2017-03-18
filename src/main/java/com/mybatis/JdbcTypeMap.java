package com.mybatis;

import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @author chen
 * @description
 * @pachage com.dayhr.web.module.hr.time.attendance.constants
 * @date 2016/12/22 22:06
 */
public class JdbcTypeMap {
    public static final Map<Class<?>, String> TYPE = ImmutableMap.<Class<?>, String>builder()
            .put(String.class, "VARCHAR")
            .put(Integer.class, "INTEGER")
            .put(BigDecimal.class, "DECIMAL")
            .put(Long.class, "BIGINT")
            .put(Boolean.class, "INTEGER")
            .put(Double.class, "DOUBLE")
            .put(Float.class, "FLOAT")
            .put(Character.class, "CHAR")
            .put(Date.class, "DATE")
            .build();
}
