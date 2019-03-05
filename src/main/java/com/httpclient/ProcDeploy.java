package com.httpclient;

/**
 * @author cheny.huang
 * @date 2019-03-05 10:59.
 */
public class ProcDeploy {
    private String tenantId;

    public ProcDeploy(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
