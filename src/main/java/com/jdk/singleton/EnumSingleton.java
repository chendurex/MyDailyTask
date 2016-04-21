package com.jdk.singleton;

import java.sql.Connection;
import java.sql.DriverManager;


/**
 * Created by LENOVO on 2016/3/15.
 */
//枚举创建
public enum EnumSingleton {
    INSTANCE;
    private static Connection connection;
    static {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("url","username","password");
            //do something
        }catch (Exception ex){

        }finally {

        }

    }
    public  Connection getConnection() {
        return connection;
    }
    //引用
    public void reference() {
        EnumSingleton.INSTANCE.getConnection();
    }
}
