package com.sinosoft.datamigration.util;

import com.sinosoft.datamigration.po.Dmdatasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * Created by Elvis on 2017/9/22.
 */
public class DBManager {

    private static Logger logger = LoggerFactory.getLogger(DBManager.class);

    private static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";

    static {
        try {
            Class.forName(ORACLE_DRIVER);
        } catch (ClassNotFoundException e) {
            logger.error("迁移失败-----" + e.getMessage());
        }
    }

    public static Connection getConnection(String url, String username, String password){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url,username,password);
        } catch (SQLException e) {
            logger.error("获取连接失败-----" + e.getMessage());
        }
        return conn;
    }

    /**
     * 释放资源
     * @param conn
     * @param stat
     * @param rs
     */
    public static void release(Connection conn, Statement stat, ResultSet rs){
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                logger.error("关闭ResultSet失败-----" + e.getMessage());
            }
        }
        if(stat != null){
            try {
                stat.close();
            } catch (SQLException e) {
                logger.error("关闭Statement失败-----" + e.getMessage());
            }
        }
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                logger.error("关闭Connection失败-----" + e.getMessage());
            }
        }
    }

    public static String getOracleURL(Dmdatasource dmdatasource){
        StringBuilder urlBuilder = new StringBuilder("jdbc:oracle:thin:@");
        urlBuilder.append(dmdatasource.getServerip()).append(":")
                .append(dmdatasource.getPort()).append(":")
                .append(dmdatasource.getServername());
        return urlBuilder.toString();
    }
}
