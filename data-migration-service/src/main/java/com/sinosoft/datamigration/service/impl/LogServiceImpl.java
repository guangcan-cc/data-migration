package com.sinosoft.datamigration.service.impl;

import com.sinosoft.datamigration.common.Pager;
import com.sinosoft.datamigration.dao.ILogDAO;
import com.sinosoft.datamigration.exception.NonePrintException;
import com.sinosoft.datamigration.po.Dmdatasource;
import com.sinosoft.datamigration.po.Dmhandlemsglog;
import com.sinosoft.datamigration.po.Dmmigrationlog;
import com.sinosoft.datamigration.service.ILogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Elvis on 2017/10/10.
 */
@Service
public class LogServiceImpl implements ILogService{

    @Resource
    private ILogDAO logDAO;

    @Override
    public Pager queryLogInfoByMap(Pager pager, Map<String, Object> paramMap) throws NonePrintException {
        return logDAO.queryLogInfoByMap(pager,paramMap);
    }

    @Override
    public List<Dmhandlemsglog> queryHandleLogByMiglogId(String logId) throws NonePrintException {
        return logDAO.queryHandleLogByMiglogId(logId);
    }

    @Override
    public void insertHandleLogs(List<Dmhandlemsglog> dmhandlemsglogs){
        logDAO.batchPO(dmhandlemsglogs);
    }

    @Override
    public void updatePO(Dmmigrationlog dmmigrationlog) {
        logDAO.updatePO(dmmigrationlog);
    }
}
