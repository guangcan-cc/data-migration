package com.sinosoft.datamigration.service;

import com.sinosoft.datamigration.common.Pager;
import com.sinosoft.datamigration.exception.NonePrintException;
import com.sinosoft.datamigration.po.Dmdatasource;

import java.util.List;
import java.util.Map;

/**
 * Created by Elvis on 2017/9/5.
 */
public interface IDSInfoService {

    /**
     * 插入数据源信息
     * @param tDatasource
     * @throws NonePrintException
     */
    void insertDSInfo(Dmdatasource tDatasource) throws NonePrintException;

    /**
     * 分页查询数据
     * @param pager
     * @param paramMap
     * @return
     */
    Pager<Dmdatasource> queryDSInfoByMap(Pager<Dmdatasource> pager, Map<String,Object> paramMap) throws NonePrintException;

    /**
     * 根据ID查询数据源信息
     * @param id
     * @return
     * @throws NonePrintException
     */
    Dmdatasource queryDSInfoById(String id) throws NonePrintException;

    /**
     * 修改数据源信息
     * @param datasource
     * @throws NonePrintException
     */
    void updateDSInfo(Dmdatasource datasource) throws NonePrintException;

    /**
     * 根据数据源名模糊查询
     * @param dsName
     * @return
     * @throws NonePrintException
     */
    List<Dmdatasource> queryDSNameLikeName(String dsName) throws NonePrintException;
}
