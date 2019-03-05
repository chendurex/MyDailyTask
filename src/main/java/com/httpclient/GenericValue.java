package com.httpclient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author cheny.huang
 * @date 2019-03-05 11:00.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenericValue{
    private Integer bi;
    private Integer bl;
    private String eName;

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public Integer getBi() {
        return bi;
    }

    public void setBi(Integer bi) {
        this.bi = bi;
    }

    public Integer getBl() {
        return bl;
    }

    public void setBl(Integer bl) {
        this.bl = bl;
    }
}
