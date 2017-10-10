package com.sinosoft.datamigration.dao;

import com.sinosoft.datamigration.common.Pager;
import com.sinosoft.datamigration.po.Dmhandlemsglog;

import java.util.List;
import java.util.Map;

/**
 * Created by Elvis on 2017/9/28.
 */
public interface ILogDAO extends IBaseDAO {


    Pager queryLogInfoByMap(Pager pager, Map<String, Object> paramMap);

    List<Dmhandlemsglog> queryHandleLogByMiglogId(String logId);
}
