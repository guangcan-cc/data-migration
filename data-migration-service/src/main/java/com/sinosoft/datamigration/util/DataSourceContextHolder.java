package com.sinosoft.datamigration.util;

/**
 * Created by Elvis on 2017/9/4.
 */
public class DataSourceContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setDataSourceType(String dataSourceName){
        contextHolder.set(dataSourceName);
    }

    public static String getDataSourceType(){
        return contextHolder.get();
    }

    public static void clearDataSourceType(){
        contextHolder.remove();
    }
}
