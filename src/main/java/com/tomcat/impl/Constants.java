package com.tomcat.impl;

import java.io.File;

/**
 * Created by Administrator on 2016/5/22.
 */
public interface Constants {
    String WEB_ROOT = System.getProperty("user.dir") + File.separator  + "webroot";

    // shutdown command
    String SHUTDOWN_COMMAND = "/SHUTDOWN";
}
