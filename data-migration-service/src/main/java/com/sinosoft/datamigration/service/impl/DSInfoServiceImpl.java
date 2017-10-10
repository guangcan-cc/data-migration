package com.sinosoft.datamigration.service.impl;

import com.sinosoft.datamigration.common.Pager;
import com.sinosoft.datamigration.dao.IDSInfoDAO;
import com.sinosoft.datamigration.exception.NonePrintException;
import com.sinosoft.datamigration.po.Dmdatasource;
import com.sinosoft.datamigration.service.IDSInfoService;
import com.sinosoft.datamigration.util.ErrorCodeDesc;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Elvis on 2017/9/5.
 */
@Service
public class DSInfoServiceImpl implements IDSInfoService {

    @Resource
    private IDSInfoDAO dsInfoDao;

    @Override
    public void insertDSInfo(Dmdatasource datasource) throws NonePrintException {
        //判断名称是否重复
        Dmdatasource _dmdatasource = dsInfoDao.queryDSInfoByName(datasource.getDsname());
        if(_dmdatasource != null){
            throw new NonePrintException(ErrorCodeDesc.DSNAME_REPEAT.getCode(),ErrorCodeDesc.DSNAME_REPEAT.getDesc());
        }
        dsInfoDao.insertPO(datasource);
    }

    @Override
    public Pager<Dmdatasource> queryDSInfoByMap(Pager<Dmdatasource> pager, Map<String, Object> paramMap) throws NonePrintException {
        return dsInfoDao.queryDSInfoByMap(pager,paramMap);
    }

    @Override
    public Dmdatasource queryDSInfoById(String id) throws NonePrintException {
        return dsInfoDao.queryDSInfoById(id);
    }

    @Override
    public void updateDSInfo(Dmdatasource datasource) throws NonePrintException {

        dsInfoDao.deletePO(datasource);
        dsInfoDao.insertPO(datasource);

    }

    @Override
    public List<Dmdatasource> queryDSNameLikeName(String dsName) throws NonePrintException {
        return dsInfoDao.queryDSNameLikeName(dsName);
    }
}





























