package com.jvm.btrace.news;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chendurex
 * @date 2018-11-17 13:47
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrintableMetadata {
    private int intV;
    private boolean boolV;
    private char charV;
    private double doubV;
    private Integer intBoxedV;
    private Boolean boolBoxedV;
    private Character charBoxedV;
    private Double doubBoxedV;
    private String sV;
    private Map<Integer, Integer> mapV = new HashMap<>();
    private List<Integer> listV = new ArrayList<>();
    private List<Map<Integer, Integer>> lmv = new ArrayList<>();
    private Map<Integer, List<Integer>> mlv = new HashMap<>();
    private Stub stub;
    private List<Stub> lstub = new ArrayList<>();


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class Stub {
        private Integer intV;
        private SubStub subStub;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class  SubStub {
        private Integer intV;
        private Map<Integer, Integer> map = new HashMap<>();
        private List<Integer> list = new ArrayList<>();
    }

}
