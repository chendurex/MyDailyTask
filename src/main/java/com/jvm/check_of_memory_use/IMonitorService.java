package com.jvm.check_of_memory_use;

/**
 * @author chen
 * @description TODO
 * @pachage com.jvm.check_of_memory_use
 * @date 2016/5/19 22:18
 */
public interface IMonitorService {
    /**
     * 获得当前的监控对象.
     * @return 返回构造好的监控对象
     * @throws Exception
     */
    public MonitorInfoBean getMonitorInfoBean() throws Exception;
}
