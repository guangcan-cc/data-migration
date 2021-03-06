package com.sinosoft.datamigration.service.impl;

import com.sinosoft.datamigration.common.Pager;
import com.sinosoft.datamigration.dao.IDSInfoDAO;
import com.sinosoft.datamigration.dao.IDynamicDAO;
import com.sinosoft.datamigration.dao.ILogDAO;
import com.sinosoft.datamigration.dao.IMigrationDAO;
import com.sinosoft.datamigration.exception.NonePrintException;
import com.sinosoft.datamigration.po.*;
import com.sinosoft.datamigration.service.IMigrationService;
import com.sinosoft.datamigration.task.MigrationTask;
import com.sinosoft.datamigration.thread.MigrationThreadPool;
import com.sinosoft.datamigration.util.*;
import com.sinosoft.datamigration.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Elvis on 2017/9/7.
 */
@Service
public class MigrationServiceImpl implements IMigrationService {

    private static final Logger logger = LoggerFactory.getLogger(MigrationServiceImpl.class);

    private static final String MAIL_SERVER = "smtp.qq.com";
    private static final String MAIL_SENDER = "294093292@qq.com";
    private static final String MAIL_LOGIN_AUTH_CODE = "jhxvtwxnvsnlbhaj";//授权码

    @Resource
    private IMigrationDAO migrationDao;
    @Resource
    private IDynamicDAO dynamicDAO;
    @Resource
    private IDSInfoDAO dsInfoDAO;
    @Resource
    private ILogDAO logDAO;

    @Override
    public Pager<Dmgroup> queryGroupInfoByVO(Pager<Dmgroup> pager, GroupQueryVO queryVO) throws NonePrintException {
        return migrationDao.queryGroupInfoByVO(pager,queryVO);
    }

    @Override
    public void insertGroupInfo(Dmgroup dmgroup) throws NonePrintException {
        Dmgroup _dmgroup = migrationDao.queryGroupInfoByGroupName(dmgroup.getGroupname());
        if(_dmgroup != null){
            throw new NonePrintException(ErrorCodeDesc.GROUP_NAME_REPEAT.getCode(),ErrorCodeDesc.GROUP_NAME_REPEAT.getDesc());
        }
        migrationDao.insertPO(dmgroup);
    }

    @Override
    public Dmgroup findGroupInfoById(String id) throws NonePrintException {
        return migrationDao.findById(Dmgroup.class,id);
    }

    @Override
    public void updateGroupInfo(Dmgroup dmgroup) throws NonePrintException {
        migrationDao.deletePO(dmgroup);
        migrationDao.insertPO(dmgroup);
    }

    @Override
    public Pager<Dmgrouptable> queryTablesById(Pager<Dmgrouptable> pager, String groupId) throws NonePrintException {
        return migrationDao.queryTablesById(pager,groupId);
    }

    private List<String> checkTableInfo(Dmgrouptable dmgrouptable, Dmdatasource dmdatasource) throws NonePrintException {
        //校验表是否唯一存在同一组
        Dmgrouptable _dmgrouptable = migrationDao.queryTableByTableNameAndGroupId(
                dmgrouptable.getGroupid(),dmgrouptable.getOriginaltable());
        if(_dmgrouptable != null){
            throw new NonePrintException(ErrorCodeDesc.TABLE_IN_GROUP_REPEAT.getCode(),ErrorCodeDesc.TABLE_IN_GROUP_REPEAT.getDesc());
        }
        //校验是否被关联
        String _tableName = migrationDao.queryTableByRefInGroup(dmgrouptable.getGroupid(),dmgrouptable.getOriginaltable());
        if(_tableName != null){
            throw new NonePrintException(ErrorCodeDesc.TABLE_IN_GROUP_RELATED.getCode(),ErrorCodeDesc.TABLE_IN_GROUP_RELATED.getDesc().replace("@tableName",_tableName));
        }

        List<String> relatedTables = dynamicDAO.findRelatedTable(dmdatasource,dmgrouptable.getOriginaltable());

        List<DmTableRef> dmTableRefs = null;
        //校验关联表是否在组配置设置中
        if(!AssertUtils.isEmpty(relatedTables)){
            dmTableRefs = new ArrayList<>();
            for(String tableName : relatedTables){
                _dmgrouptable = migrationDao.queryTableByTableNameAndGroupId(dmgrouptable.getGroupid(),tableName.toLowerCase());
                if(_dmgrouptable != null){
                    throw new NonePrintException(ErrorCodeDesc.RELATED_TABLE_IN_GROUP.getCode(),ErrorCodeDesc.RELATED_TABLE_IN_GROUP.getDesc().replace("@tableName",tableName));
                }
                //校验是否被关联
                _tableName = migrationDao.queryTableByRefInGroup(dmgrouptable.getGroupid(),tableName);
                if(_tableName != null){
                    throw new NonePrintException(ErrorCodeDesc.TABLE_IN_GROUP_REPEAT.getCode(),ErrorCodeDesc.TABLE_IN_GROUP_REPEAT.getDesc().replace("@tableName",_tableName));
                }
                DmTableRef dmTableRef = new DmTableRef();
                dmTableRef.setGroupId(dmgrouptable.getGroupid());
                dmTableRef.setTableRef(tableName.toLowerCase());
                dmTableRef.setTableName(dmgrouptable.getOriginaltable());
                dmTableRefs.add(dmTableRef);
            }
            //批量插入关联表信息
            migrationDao.batchPO(dmTableRefs);
        }

        return relatedTables;
    }

    //生成存储过程
    private void createProcedure(String migrationProcedureName,String restoreProcedureName,
                                 Dmgrouptable dmgrouptable,Dmgroup dmgroup,
                                 List<String> relatedTables,Dmdatasource dmdatasource) throws NonePrintException {

        String migrationProcedure = SQLUtils.createMigrationProduce(migrationProcedureName,
                dmgrouptable,dmgroup,relatedTables);

        String restoreProcedure = SQLUtils.createRestoreProduce(restoreProcedureName,
                dmgrouptable,dmgroup,relatedTables);

        dmgrouptable.setHandleprocedurename(migrationProcedureName);
        dmgrouptable.setHandleprocedure(migrationProcedure);
        dmgrouptable.setRestoreprocedurename(restoreProcedureName);
        dmgrouptable.setRestoreprocedure(restoreProcedure);
        dmgrouptable.setHandletargettable("create table " + dmgrouptable.getTargettable() +
                " as select * from " + dmgrouptable.getOriginaltable() + " where 1=2");

        List<Dmgrouptable> dmgrouptableList = migrationDao.findTablesByGroupId(dmgrouptable.getGroupid());
        dmgrouptableList.add(dmgrouptable);

        try {
            dynamicDAO.createOrReplaceProduce(dmdatasource, dmgrouptableList, dmgroup);
        } catch (SQLException e) {
            throw new NonePrintException(ErrorCodeDesc.FAILURE_IN_PROCEDURE.getCode(),ErrorCodeDesc.FAILURE_IN_PROCEDURE.getDesc());
        }
    }

    @Override
    public void addTableInfo(Dmgrouptable dmgrouptable) throws NonePrintException {

        //查询关联表
        Dmdatasource dmdatasource = dsInfoDAO.queryDSInfoById(dmgrouptable.getOriginaldsid());

        //校验 返回关联表
        List<String> relatedTables = checkTableInfo(dmgrouptable,dmdatasource);

        Dmgroup dmgroup = migrationDao.findById(Dmgroup.class,dmgrouptable.getGroupid());


        String dateTime = String.valueOf(new Date().getTime());
        String migrationProcedureName = "MIGRATION_" + dateTime;
        String restoreProcedureName = "RESTORE_" + dateTime;

        createProcedure(migrationProcedureName,restoreProcedureName,dmgrouptable,dmgroup,relatedTables,dmdatasource);

        migrationDao.insertPO(dmgrouptable);
    }

    @Override
    public void updateTableInfo(Dmgrouptable dmgrouptable) throws NonePrintException {
        Dmgrouptable _dmgrouptable = migrationDao.findById(Dmgrouptable.class,dmgrouptable.getId());
        if(_dmgrouptable == null){
            throw new NonePrintException(ErrorCodeDesc.FAILURE_IN_TABLE_UPDATE.getCode(),ErrorCodeDesc.FAILURE_IN_TABLE_UPDATE.getDesc());
        }

        _dmgrouptable.setTargetdsid(dmgrouptable.getTargetdsid());
        _dmgrouptable.setTargetdsname(dmgrouptable.getTargetdsname());
        _dmgrouptable.setTargetdsusername(dmgrouptable.getTargetdsusername());
        _dmgrouptable.setTargettable(dmgrouptable.getTargettable());
        _dmgrouptable.setIscleanup(dmgrouptable.getIscleanup());


        Dmdatasource dmdatasource = dsInfoDAO.queryDSInfoById(_dmgrouptable.getOriginaldsid());

        List<String> relatedTables = dynamicDAO.findRelatedTable(dmdatasource,dmgrouptable.getOriginaltable());

        Dmgroup dmgroup = migrationDao.findById(Dmgroup.class,_dmgrouptable.getGroupid());


        createProcedure(_dmgrouptable.getHandleprocedurename(),_dmgrouptable.getRestoreprocedurename(),
                _dmgrouptable,dmgroup,relatedTables,dmdatasource);

        migrationDao.updatePO(_dmgrouptable);
    }

    @Override
    public boolean checkTableInfoInOthers(String groupId, String tableName) throws NonePrintException {

        //校验表是否存在
        Dmgroup dmgroup = migrationDao.findById(Dmgroup.class,groupId);
        if(dmgroup == null){
            throw new NonePrintException(ErrorCodeDesc.GROUP_NOT_EXSIT.getCode(),ErrorCodeDesc.GROUP_NOT_EXSIT.getDesc());
        }
        Dmdatasource dmdatasource = dsInfoDAO.findById(Dmdatasource.class,dmgroup.getOriginaldsid());
        if(dmdatasource == null){
            throw new NonePrintException(ErrorCodeDesc.DATASOURCE_NOT_EXSIT.getCode(),ErrorCodeDesc.DATASOURCE_NOT_EXSIT.getDesc());
        }
        if(!dynamicDAO.checkTableExist(dmdatasource,tableName)){
            throw new NonePrintException(ErrorCodeDesc.TABLE_NOT_EXSIT_IN_DS.getCode(),ErrorCodeDesc.TABLE_NOT_EXSIT_IN_DS.getDesc());
        }
        List<Dmgrouptable> dmgrouptables = migrationDao.queryTablesByTableName(tableName);
        if(!AssertUtils.isEmpty(dmgrouptables)){
            //校验表是否唯一存在同一组
            for(Dmgrouptable dmgrouptable : dmgrouptables){
                if(dmgrouptable.getGroupid().equals(groupId)){
                    throw new NonePrintException(ErrorCodeDesc.TABLE_IN_GROUP_REPEAT.getCode(),ErrorCodeDesc.TABLE_IN_GROUP_REPEAT.getDesc());
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public Dmgrouptable findTableInfoById(String id) throws NonePrintException {
        return migrationDao.findById(Dmgrouptable.class,id);
    }

    @Override
    public void deleteTableInfo(String id) throws NonePrintException {
        Dmgrouptable _dmgrouptable = migrationDao.findById(Dmgrouptable.class,id);
        if(_dmgrouptable == null){
            throw new NonePrintException(ErrorCodeDesc.FAILURE_IN_TABLE_DELETE.getCode(),ErrorCodeDesc.FAILURE_IN_TABLE_DELETE.getDesc());
        }
        migrationDao.deletePO(_dmgrouptable);
        migrationDao.deleteTableRefByTable(_dmgrouptable.getOriginaltable(),_dmgrouptable.getGroupid());
        /*if(true){
            throw new RuntimeException("xxx");
        }*/
    }

    @Override
    public List<TableInfoVO> queryTableInfos(Dmgrouptable dmgrouptable, Dmdatasource dmdatasource, List<DmTableRef> dmTableRefList) throws NonePrintException {
        String groupId = dmgrouptable.getGroupid();
        String originalTable = dmgrouptable.getOriginaltable();
        List<TableInfoVO> tableInfoVOs = new ArrayList<>(1);
        //查询主表字段信息
        tableInfoVOs.add(queryFieldsInfoByTableName(dmdatasource,originalTable));
        /**
         * 查询关联表
         * 循环查关联表查询表字段信息
         */
        if(!AssertUtils.isEmpty(dmTableRefList)){
            for(DmTableRef dmTableRef : dmTableRefList){
                tableInfoVOs.add(queryFieldsInfoByTableName(dmdatasource,dmTableRef.getTableRef()));
            }
        }
        return tableInfoVOs;
    }

    @Override
    public List<DmTableRef> findTableRefByGrouIdAndtableName(String groupid, String originaltable) throws NonePrintException {
        return migrationDao.findTableRefByGrouIdAndtableName(groupid,originaltable);
    }

    /**
     * 根据表字段信息
     * @param dmdatasource
     * @param tableName
     * @return
     * @throws NonePrintException
     */
    private TableInfoVO queryFieldsInfoByTableName(Dmdatasource dmdatasource, String tableName)throws NonePrintException {
        TableInfoVO tableInfoVO = new TableInfoVO();

        tableInfoVO.setTableName(tableName);

        List<FieldInfoVO> fieldInfoVOList = dynamicDAO.findFieldsInfoByTableName(dmdatasource,tableName);

        tableInfoVO.setFields(fieldInfoVOList);

        return tableInfoVO;
    }

    @Override
    public List<RelatedInfoVO> queryRelatedInfo(Dmgrouptable dmgrouptable, Dmdatasource dmdatasource, List<DmTableRef> dmTableRefList) throws NonePrintException {
        List<RelatedInfoVO> relatedInfoVOList = new ArrayList<>(1);

        String originalTable = dmgrouptable.getOriginaltable();
        relatedInfoVOList.add(queryRelatedInfoByTableName(dmdatasource,originalTable));

        if(!AssertUtils.isEmpty(dmTableRefList)){
            for(DmTableRef tableRef : dmTableRefList){
                relatedInfoVOList.add(queryRelatedInfoByTableName(dmdatasource,tableRef.getTableRef()));
            }
        }

        return relatedInfoVOList;
    }

    /**
     * 查询表关联详情
     * @param dmdatasource
     * @param originalTable
     * @return
     */
    private RelatedInfoVO queryRelatedInfoByTableName(Dmdatasource dmdatasource, String originalTable) {
        RelatedInfoVO relatedInfoVO = new RelatedInfoVO();
        relatedInfoVO.setUsername(dmdatasource.getUsername());
        relatedInfoVO.setTableName(originalTable);
        List<ConstraintInfoVO> constraintInfoList = dynamicDAO.queryRelatedInfoByTableName(dmdatasource,originalTable);
        List<String> foreignKeys = null;
        List<String> foreignKeyNames = null;
        for(ConstraintInfoVO vo : constraintInfoList){
            if(ConstantUtils.FOREIGN_TYPE.equals(vo.getConstrainType())){
                if(foreignKeys == null){
                    foreignKeys = new ArrayList<>();
                }
                if(foreignKeyNames == null){
                    foreignKeyNames = new ArrayList<>();
                }
                foreignKeys.add(vo.getColumnName());
                foreignKeyNames.add(vo.getConstraintName());
            } else if(ConstantUtils.PRIMARY_TYPE.equals(vo.getConstrainType())){
                relatedInfoVO.setPrimaryKey(vo.getColumnName());
                relatedInfoVO.setPrimaryKeyName(vo.getConstraintName());
            }
        }
        relatedInfoVO.setForeignKeys(foreignKeys);
        relatedInfoVO.setForeignKeyNames(foreignKeyNames);
        return relatedInfoVO;
    }

    @Override
    public void updateMigrationProcedure(String id, String handleProcedure) throws NonePrintException {
        Dmgrouptable _dmgrouptable = migrationDao.findById(Dmgrouptable.class,id);
        if(_dmgrouptable == null){
            throw new NonePrintException(ErrorCodeDesc.FAILURE_IN_TABLE_UPDATE.getCode(),ErrorCodeDesc.FAILURE_IN_TABLE_UPDATE.getDesc());
        }
        _dmgrouptable.setHandleprocedure(handleProcedure);

        Dmdatasource dmdatasource = dsInfoDAO.findById(Dmdatasource.class,_dmgrouptable.getOriginaldsid());
        if(dmdatasource == null){
            throw new NonePrintException(ErrorCodeDesc.DATASOURCE_NOT_EXSIT.getCode(),ErrorCodeDesc.DATASOURCE_NOT_EXSIT.getDesc());
        }
        //修改存储过程
        Dmgroup dmgroup = migrationDao.findById(Dmgroup.class, _dmgrouptable.getGroupid());
        List<Dmgrouptable> dmgrouptables = migrationDao.findTablesByGroupId(_dmgrouptable.getGroupid());
        try {
            dynamicDAO.createOrReplaceProduce(dmdatasource, dmgrouptables, dmgroup);
        } catch (SQLException e) {
            throw new NonePrintException(ErrorCodeDesc.FAILURE_IN_PROCEDURE.getCode(),ErrorCodeDesc.FAILURE_IN_PROCEDURE.getDesc());
        }
        migrationDao.updatePO(_dmgrouptable);
    }

    @Override
    public void updateRestoreProcedure(String id, String retoreProcedure) throws NonePrintException {
        Dmgrouptable _dmgrouptable = migrationDao.findById(Dmgrouptable.class,id);
        if(_dmgrouptable == null){
            throw new NonePrintException(ErrorCodeDesc.FAILURE_IN_TABLE_UPDATE.getCode(),ErrorCodeDesc.FAILURE_IN_TABLE_UPDATE.getDesc());
        }
        _dmgrouptable.setRestoreprocedure(retoreProcedure);

        Dmdatasource dmdatasource = dsInfoDAO.findById(Dmdatasource.class,_dmgrouptable.getOriginaldsid());
        if(dmdatasource == null){
            throw new NonePrintException(ErrorCodeDesc.DATASOURCE_NOT_EXSIT.getCode(),ErrorCodeDesc.DATASOURCE_NOT_EXSIT.getDesc());
        }
        //修改存储过程
        Dmgroup dmgroup = migrationDao.findById(Dmgroup.class, _dmgrouptable.getGroupid());
        List<Dmgrouptable> dmgrouptables = migrationDao.findTablesByGroupId(_dmgrouptable.getGroupid());
        try {
            dynamicDAO.createOrReplaceProduce(dmdatasource, dmgrouptables, dmgroup);
        } catch (SQLException e) {
            throw new NonePrintException(ErrorCodeDesc.FAILURE_IN_PROCEDURE.getCode(),ErrorCodeDesc.FAILURE_IN_PROCEDURE.getDesc());
        }
        migrationDao.updatePO(_dmgrouptable);
    }

    @Override
    public void updateHandleContent(String id, String handleContent) throws NonePrintException {
        Dmgrouptable _dmgrouptable = migrationDao.findById(Dmgrouptable.class,id);
        if(_dmgrouptable == null){
            throw new NonePrintException(ErrorCodeDesc.FAILURE_IN_TABLE_UPDATE.getCode(),ErrorCodeDesc.FAILURE_IN_TABLE_UPDATE.getDesc());
        }
        _dmgrouptable.setHandletargettable(handleContent);
        migrationDao.updatePO(_dmgrouptable);
    }

    @Override
    public List<Dmgrouptable> findTablesByGroupIdOrGroupName(String groupId,String groupName) throws NonePrintException {

        if(!AssertUtils.isEmpty(groupName)){
            groupId = migrationDao.findGroupIdByGroupName(groupName);
        }
        List<Dmgrouptable> dmgrouptables = migrationDao.findTablesByGroupId(groupId);
        if(AssertUtils.isEmpty(dmgrouptables)){
            throw new NonePrintException(ErrorCodeDesc.NO_TABLE_IN_GROUP.getCode(),ErrorCodeDesc.NO_TABLE_IN_GROUP.getDesc());
        }
        return dmgrouptables;
    }

    @Override
    public List<String> findGroupIdByPartOfGroupId(String groupId) throws NonePrintException {
        return migrationDao.findGroupIdByPartOfGroupId(groupId);
    }

    @Override
    public List<String> findGroupNameByPartOfGroupName(String groupName) throws NonePrintException {
        return migrationDao.findGroupNameByPartOfGroupName(groupName);
    }

    @Override
    public void migrateData(Dmuserinfo user, MigrationParamVO paramVO) throws NonePrintException {
        //查询组ID
        if("2".equals(paramVO.getGroup())){
            if(AssertUtils.isEmpty(paramVO.getGroupName())) {
                throw new NonePrintException(ErrorCodeDesc.GROUPNAME_IS_NULL.getCode(), ErrorCodeDesc.GROUPNAME_IS_NULL.getDesc());
            }
            paramVO.setGroupId(migrationDao.findGroupIdByGroupName(paramVO.getGroupName()));
        }
        if(AssertUtils.isEmpty(paramVO.getGroupId())) {
            throw new NonePrintException(ErrorCodeDesc.GROUP_NOT_EXSIT.getCode(), ErrorCodeDesc.GROUP_NOT_EXSIT.getDesc());
        }
        //查询组信息
        Dmgroup dmgroup = migrationDao.findById(Dmgroup.class,paramVO.getGroupId());
        if(dmgroup == null){
            throw new NonePrintException(ErrorCodeDesc.GROUP_NOT_EXSIT.getCode(), ErrorCodeDesc.GROUP_NOT_EXSIT.getDesc());
        }
        //查询组表信息
        List<Dmgrouptable> dmgrouptableList = migrationDao.findTablesByGroupId(paramVO.getGroupId());
        if(AssertUtils.isEmpty(dmgrouptableList)){
            throw new NonePrintException(ErrorCodeDesc.NO_TABLE_IN_GROUP.getCode(), ErrorCodeDesc.NO_TABLE_IN_GROUP.getDesc());
        }
        // first 得到原数据源
        Dmdatasource dmdatasource = dsInfoDAO.findById(Dmdatasource.class, dmgroup.getOriginaldsid());
        if(AssertUtils.isEmpty(dmdatasource)){
            throw new NonePrintException(ErrorCodeDesc.DATASOURCE_NOT_EXSIT.getCode(), ErrorCodeDesc.DATASOURCE_NOT_EXSIT.getDesc());
        }
        if("1".equals(paramVO.getParamType())){
            if(AssertUtils.isEmpty(paramVO.getStartTime()) || AssertUtils.isEmpty(paramVO.getEndTime())){
                throw new NonePrintException(ErrorCodeDesc.TIMEPARAM_IS_NULL.getCode(), ErrorCodeDesc.TIMEPARAM_IS_NULL.getDesc());
            }
        } else {
            if(AssertUtils.isEmpty(paramVO.getParamValue())){
                throw new NonePrintException(ErrorCodeDesc.PARAMVALUE_IS_NULL.getCode(), ErrorCodeDesc.PARAMVALUE_IS_NULL.getDesc());
            }
        }
        executeMigrate(user,dmgroup,dmdatasource,dmgrouptableList,paramVO);
    }

    /**
     * 执行迁移
     * @param user
     * @param dmgroup
     * @param dmdatasource
     * @param dmgrouptableList
     * @param paramVO
     */
    private void executeMigrate(Dmuserinfo user, Dmgroup dmgroup, Dmdatasource dmdatasource, List<Dmgrouptable> dmgrouptableList, MigrationParamVO paramVO) throws NonePrintException {
        /**
         * 循环执行表迁移
         * 1、删除中间表数据
         * 2、查询数据到中间表
         * 3、创建/更新历史表SQL
         * 4、执行存储过程
         */
        // first
        try {
            dynamicDAO.deleteDataInMidTable(dmdatasource,dmgroup);
        } catch (SQLException e) {
            throw new NonePrintException(ErrorCodeDesc.ERROR_IN_DELETE_MID_TABLE.getCode(),ErrorCodeDesc.ERROR_IN_DELETE_MID_TABLE.getDesc());
        }

        // second 执行提数脚本
        //查询提数了多少条数据
        int count = 0;
        try {
            count = dynamicDAO.executeExtract(dmdatasource,dmgroup,paramVO);
        } catch (SQLException e) {
            throw new NonePrintException(ErrorCodeDesc.ERROR_IN_EXTRACTED.getCode(),ErrorCodeDesc.ERROR_IN_EXTRACTED.getDesc());
        }
        if(count == 0){
            throw new NonePrintException(ErrorCodeDesc.NO_DATA_EXTRACTED.getCode(),ErrorCodeDesc.NO_DATA_EXTRACTED.getDesc());
        }

        //迁移前执行sql
        for(Dmgrouptable dmgrouptable : dmgrouptableList){
            String hanleTable = dmgrouptable.getHandletargettable();
            if(AssertUtils.isEmpty(hanleTable)){
                continue;
            }
            // third 创建/更新历史表SQL
            String[] handleTargetTable = hanleTable.replaceAll("\\n","").split(";");
            for(String handleSQL : handleTargetTable){
                if(AssertUtils.isEmpty(handleSQL)){
                    continue;
                }
                try {
                    dynamicDAO.executeSQL(dmdatasource, handleSQL);
                }catch (SQLException e){
                    throw new NonePrintException(ErrorCodeDesc.ERROR_IN_TABLE_HANDLE.getCode(),
                            ErrorCodeDesc.ERROR_IN_TABLE_HANDLE.getDesc().replace("@tableName",dmgrouptable.getOriginaltable()));
                }
            }
        }

        //生成正在执行的日志
        Dmmigrationlog dmmigrationlog = createMigrationLog(dmgroup,user);
        //插入日志信息
        logDAO.insertPO(dmmigrationlog);

        MigrationTask task = new MigrationTask(dmgrouptableList,dmmigrationlog,paramVO,dmdatasource,dmgroup,count,"1");

        MigrationThreadPool.execute(task);
    }

    /**
     * 创建日志
     * @param dmgroup
     * @param user
     * @return
     */
    private Dmmigrationlog createMigrationLog(Dmgroup dmgroup, Dmuserinfo user){
        Date nowDate = new Date();
        Dmmigrationlog dmmigrationlog = new Dmmigrationlog();
        dmmigrationlog.setId("LOG" + nowDate.getTime());
        dmmigrationlog.setGroupid(dmgroup.getId());
        dmmigrationlog.setGroupname(dmgroup.getGroupname());
        dmmigrationlog.setCreatetime(nowDate);
        dmmigrationlog.setHandlestarttime(nowDate);
        dmmigrationlog.setHandleperson(user.getUsername());
        dmmigrationlog.setHandleresult(ConstantUtils.MIGRATION_IN_PROGRESS);
        return dmmigrationlog;
    }

    @Override
    public void restoreData(Dmuserinfo user, MigrationParamVO paramVO) throws NonePrintException {
        //查询组ID
        if("2".equals(paramVO.getGroup())){
            if(AssertUtils.isEmpty(paramVO.getGroupName())) {
                throw new NonePrintException(ErrorCodeDesc.GROUPNAME_IS_NULL.getCode(), ErrorCodeDesc.GROUPNAME_IS_NULL.getDesc());
            }
            paramVO.setGroupId(migrationDao.findGroupIdByGroupName(paramVO.getGroupName()));
        }
        if(AssertUtils.isEmpty(paramVO.getGroupId())) {
            throw new NonePrintException(ErrorCodeDesc.GROUP_NOT_EXSIT.getCode(), ErrorCodeDesc.GROUP_NOT_EXSIT.getDesc());
        }
        //查询组信息
        Dmgroup dmgroup = migrationDao.findById(Dmgroup.class,paramVO.getGroupId());
        if(dmgroup == null){
            throw new NonePrintException(ErrorCodeDesc.GROUP_NOT_EXSIT.getCode(), ErrorCodeDesc.GROUP_NOT_EXSIT.getDesc());
        }
        //查询组表信息
        List<Dmgrouptable> dmgrouptableList = migrationDao.findTablesByGroupId(paramVO.getGroupId());
        if(AssertUtils.isEmpty(dmgrouptableList)){
            throw new NonePrintException(ErrorCodeDesc.NO_TABLE_IN_GROUP.getCode(), ErrorCodeDesc.NO_TABLE_IN_GROUP.getDesc());
        }
        // first 得到原数据源
        Dmdatasource dmdatasource = dsInfoDAO.findById(Dmdatasource.class, dmgroup.getOriginaldsid());
        if(AssertUtils.isEmpty(dmdatasource)){
            throw new NonePrintException(ErrorCodeDesc.DATASOURCE_NOT_EXSIT.getCode(), ErrorCodeDesc.DATASOURCE_NOT_EXSIT.getDesc());
        }
        if("1".equals(paramVO.getParamType())){
            if(AssertUtils.isEmpty(paramVO.getStartTime()) || AssertUtils.isEmpty(paramVO.getEndTime())){
                throw new NonePrintException(ErrorCodeDesc.TIMEPARAM_IS_NULL.getCode(), ErrorCodeDesc.TIMEPARAM_IS_NULL.getDesc());
            }
        } else {
            if(AssertUtils.isEmpty(paramVO.getParamValue())){
                throw new NonePrintException(ErrorCodeDesc.PARAMVALUE_IS_NULL.getCode(), ErrorCodeDesc.PARAMVALUE_IS_NULL.getDesc());
            }
        }
        executeRestore(user,dmgroup,dmdatasource,dmgrouptableList,paramVO);
    }

    private void executeRestore(Dmuserinfo user, Dmgroup dmgroup, Dmdatasource dmdatasource, List<Dmgrouptable> dmgrouptableList, MigrationParamVO paramVO) throws NonePrintException{
        /**
         * 循环执行表还原
         * 1、删除中间表数据
         * 2、查询数据到中间表
         * 3、创建/更新历史表SQL
         * 4、执行存储过程
         */
        // first
        try {
            dynamicDAO.deleteDataInMidTable(dmdatasource,dmgroup);
        } catch (SQLException e) {
            throw new NonePrintException(ErrorCodeDesc.ERROR_IN_DELETE_MID_TABLE.getCode(),ErrorCodeDesc.ERROR_IN_DELETE_MID_TABLE.getDesc());
        }
        // second 执行提数脚本
        //查询提数了多少条数据
        int count = 0;
        try {
            count = dynamicDAO.executeExtract(dmdatasource,dmgroup,paramVO);
        } catch (SQLException e) {
            throw new NonePrintException(ErrorCodeDesc.ERROR_IN_EXTRACTED.getCode(),ErrorCodeDesc.ERROR_IN_EXTRACTED.getDesc());
        }
        if(count == 0){
            throw new NonePrintException(ErrorCodeDesc.NO_DATA_EXTRACTED.getCode(),ErrorCodeDesc.NO_DATA_EXTRACTED.getDesc());
        }
        //生成正在执行的日志
        Dmmigrationlog dmmigrationlog = createMigrationLog(dmgroup,user);

        //插入日志信息
        logDAO.insertPO(dmmigrationlog);

        MigrationTask task = new MigrationTask(dmgrouptableList,dmmigrationlog,paramVO,dmdatasource,dmgroup,count,"2");

        MigrationThreadPool.execute(task);

    }

    @Override
    public List<Dmgrouptable> queryAllTablesById(String groupId) throws NonePrintException {
        return migrationDao.queryAllTablesById(groupId);
    }
}



















