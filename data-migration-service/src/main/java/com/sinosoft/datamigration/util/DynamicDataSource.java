package com.sinosoft.datamigration.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Elvis on 2017/9/4.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicDataSource.class);

    @Resource
    private DataSource masterDataSource;

    private static final Map<String,DataSource> targetDataSources = new HashMap<>();

    @Override
    protected DataSource determineTargetDataSource() {
        // 根据数据库选择方案，拿到要访问的数据库
        String dataSourceName = determineCurrentLookupKey();
        if("dataSource".equals(dataSourceName)) {
            // 访问默认主库
            return masterDataSource;
        }

        // 根据数据库名字，从已创建的数据库中获取要访问的数据库
        DataSource dataSource = targetDataSources.get(dataSourceName);
        if(null == dataSource) {
            // 从已创建的数据库中获取要访问的数据库，如果没有则创建一个
            try {
                dataSource = this.selectDataSource(dataSourceName);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dataSource;
    }

    @Override
    protected String determineCurrentLookupKey() {
        String dataSourceName = DataSourceContextHolder.getDataSourceType();
        if (dataSourceName == null || dataSourceName == "dataSource") {
            // 默认的数据源名字
            dataSourceName = "dataSource";
        }
        LOGGER.debug("use datasource : " + dataSourceName);
        return dataSourceName;
    }

    public void addTargetDataSource(String key, DriverManagerDataSource dataSource) {
        targetDataSources.put(key, dataSource);
    }

    /**
     * 该方法为同步方法，防止并发创建两个相同的数据库
     * 使用双检锁的方式，防止并发
     * @param dbType
     * @return
     */
    private synchronized DataSource selectDataSource(String dbType) throws SQLException {
        // 再次从数据库中获取，双检锁
        DataSource obj = targetDataSources.get(dbType);
        if (null != obj) {
            return obj;
        }
        // 为空则创建数据库
        DriverManagerDataSource dataSource = this.getDataSource(dbType);
        if(null == dataSource){
            throw new SQLException("创建数据源失败！");
        }
        // 将新创建的数据库保存到map中
        this.setDataSource(dbType, dataSource);
        return dataSource;
    }

    /**
     * 查询对应数据库的信息
     * @param dbtype
     * @return
     */
    private DriverManagerDataSource getDataSource(String dbtype) throws SQLException {
        String oriType = DataSourceContextHolder.getDataSourceType();
        // 先切换回主库
        DataSourceContextHolder.setDataSourceType("dataSource");
        // 查询所需信息


        // 切换回目标库
        DataSourceContextHolder.setDataSourceType(oriType);

        return createDataSource("driverManagerName","url","username","password");
    }

    //创建数据源
    private static DriverManagerDataSource createDataSource(String driverClassName, String url,
                                             String username, String password) throws SQLException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    public void setDataSource(String type, DriverManagerDataSource dataSource) {
        this.addTargetDataSource(type, dataSource);
        DataSourceContextHolder.setDataSourceType(type);
    }

    @Override
    public void setTargetDataSources(Map<Object,Object> targetDataSources) {
        // TODO Auto-generated method stub
        super.setTargetDataSources(targetDataSources);
        // 重点：通知container容器数据源发生了变化
        afterPropertiesSet();
    }

    @Override
    public void afterPropertiesSet() {
    }

    /**
     * 测试数据源连接
     * @param driverClassName
     * @param url
     * @param username
     * @param password
     * @return
     */
    public static boolean connectTest(String driverClassName, String url,
                                      String username, String password){

        Connection conn = null;
        try {
            conn = createDataSource(driverClassName,url,username,password).getConnection();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

















