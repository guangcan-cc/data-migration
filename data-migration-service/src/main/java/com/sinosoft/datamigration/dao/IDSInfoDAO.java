package com.sinosoft.datamigration.dao;

import com.sinosoft.datamigration.common.Pager;
import com.sinosoft.datamigration.po.Dmdatasource;

import java.util.List;
import java.util.Map;

/**
 * Created by Elvis on 2017/9/5.
 */
public interface IDSInfoDAO extends IBaseDAO{

    /**
     * 分页查询数据
     * @param pager
     * @param paramMap
     * @return
     */
    Pager<Dmdatasource> queryDSInfoByMap(Pager<Dmdatasource> pager, Map<String,Object> paramMap);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Dmdatasource queryDSInfoById(String id);

    /**
     * 根据数据源名模糊查询
     * @return
     */
    List<Dmdatasource> queryDSNameLikeName(String dsname);

    /**
     * 根据数据源名查询
     * @param dsname
     * @return
     */
    Dmdatasource queryDSInfoByName(String dsname);
}
