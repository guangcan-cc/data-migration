package com.sinosoft.datamigration.service;

import com.sinosoft.datamigration.common.Pager;
import com.sinosoft.datamigration.exception.NonePrintException;
import com.sinosoft.datamigration.po.Dmhandlemsglog;
import com.sinosoft.datamigration.po.Dmmigrationlog;

import java.util.List;
import java.util.Map;

/**
 * Created by Elvis on 2017/10/10.
 */

public interface ILogService {

    Pager queryLogInfoByMap(Pager pager, Map<String, Object> paramMap) throws NonePrintException;

    List<Dmhandlemsglog> queryHandleLogByMiglogId(String logId) throws NonePrintException;

    void insertHandleLogs(List<Dmhandlemsglog> dmhandlemsglogs);

    void updatePO(Dmmigrationlog dmmigrationlog);
}
