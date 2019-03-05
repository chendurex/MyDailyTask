package com.httpclient;

/**
 * @author cheny.huang
 * @date 2019-03-05 10:59.
 */
public class ResResult<T> {
    private int status;
    private String desc;
    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
