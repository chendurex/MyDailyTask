package com.lombok.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Created by LENOVO on 2016/3/17.
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class LombokType {
    private String string_;
    private Integer integer_;
    private int int__;
    private Double Double_;
    private double double__;
    private Character character_;
    private char char__;
    private Boolean Boolean_;
    private boolean boolean__;
    private Long Long_;
    private long long__;
    private Byte Byte_;
    private byte byte__;
    private Short Short_;
    private short short__;
    private Float Float_;
    private float float__;
    private BigDecimal bigDecimal;

}
