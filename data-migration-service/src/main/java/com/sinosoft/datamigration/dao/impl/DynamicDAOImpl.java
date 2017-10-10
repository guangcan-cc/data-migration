package com.sinosoft.datamigration.dao.impl;

import com.sinosoft.datamigration.dao.IDynamicDAO;
import com.sinosoft.datamigration.exception.NonePrintException;
import com.sinosoft.datamigration.po.Dmdatasource;
import com.sinosoft.datamigration.po.Dmgroup;
import com.sinosoft.datamigration.po.Dmgrouptable;
import com.sinosoft.datamigration.util.AssertUtils;
import com.sinosoft.datamigration.util.ConstantUtils;
import com.sinosoft.datamigration.util.DBManager;
import com.sinosoft.datamigration.util.ErrorCodeDesc;
import com.sinosoft.datamigration.vo.ConstraintInfoVO;
import com.sinosoft.datamigration.vo.FieldInfoVO;
import com.sinosoft.datamigration.vo.MigrationParamVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elvis on 2017/9/22.
 */
@Repository
public class DynamicDAOImpl extends DynamicBaseDAOImpl implements IDynamicDAO{

    private static final Logger logger = LoggerFactory.getLogger(DynamicDAOImpl.class);

    @Override
    public boolean executeSQL(Dmdatasource dmdatasource, String sql, Object... params){
        Connection conn = null;
        PreparedStatement pstat = null;
        try {
            conn = DBManager.getConnection(DBManager.getOracleURL(dmdatasource), dmdatasource.getUsername(), dmdatasource.getPassword());
            pstat = conn.prepareStatement(sql);
            if(!AssertUtils.isEmpty(params)){
                int index = 1;
                for(Object param : params){
                    pstat.setObject(index++, param);
                }
            }
            pstat.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("执行SQL出错！");
        } finally {
            DBManager.release(conn, pstat, null);
        }
        return false;
    }

    @Override
    public List<String> findRelatedTable(Dmdatasource dmdatasource, String tableName) {
        List<String> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstat = null;
        ResultSet resultSet = null;
        try {
            conn = DBManager.getConnection(DBManager.getOracleURL(dmdatasource),dmdatasource.getUsername(),dmdatasource.getPassword());
            String sql = "select table_name from user_constraints " +
                    " where r_constraint_name=(" +
                    " select constraint_name from user_constraints a " +
                    "where table_name=? and constraint_type = 'P')";
            pstat = conn.prepareStatement(sql);
            pstat.setString(1,tableName.toUpperCase());
            resultSet = pstat.executeQuery();
            while(resultSet.next()){
                list.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            DBManager.release(conn, pstat, resultSet);
        }
        return list;
    }

    @Override
    public String createMigrationProduce(Dmdatasource dmdatasource, String procedureName, Dmgrouptable dmgrouptable, Dmgroup dmgroup, List<String> relatedTables) {
        Connection conn = null;
        Statement stat = null;
        try {
            conn = DBManager.getConnection(DBManager.getOracleURL(dmdatasource),dmdatasource.getUsername(),dmdatasource.getPassword());
            stat = conn.createStatement();
            StringBuilder produreBuilder = new StringBuilder("create or replace procedure ");
            produreBuilder.append(procedureName);
            produreBuilder.append(" is ").append(" begin ");
            if(dmgroup.getIsdataextracted().equals(ConstantUtils.IS_EXTRACTED)){

                produreBuilder.append(createPartInsertSQL(dmgrouptable.getTargettable(),
                        dmgrouptable.getOriginaltable(),dmgroup.getMidtablename(),dmgroup.getDefaultcondition()));

                if(!AssertUtils.isEmpty(relatedTables)){
                    for(String tableName : relatedTables){

                        String targetTableName = tableName + "his";

                        produreBuilder.append(createPartInsertSQL(targetTableName,
                                tableName,dmgroup.getMidtablename(),dmgroup.getDefaultcondition()));

                    }
                }
                if(ConstantUtils.IS_CLEANUP.equals(dmgrouptable.getIscleanup())){
                    /**
                     * 删除
                     * 先删除关联表
                     * 再删除主表
                     */
                    if(!AssertUtils.isEmpty(relatedTables)){
                        for(String tableName : relatedTables){

                            produreBuilder.append(createPartDeleteSQL(tableName,
                                    dmgroup.getMidtablename(),dmgroup.getDefaultcondition()));

                        }
                    }

                    produreBuilder.append(createPartDeleteSQL(dmgrouptable.getOriginaltable(),
                            dmgroup.getMidtablename(),dmgroup.getDefaultcondition()));

                }
            } else {//todo:不需要提数的

            }
            produreBuilder.append(" commit; ").append(" end; ");
            stat.executeUpdate(produreBuilder.toString());
            return produreBuilder.toString();
        } catch (SQLException e) {
            logger.error("执行存储过程SQL异常：{}" , e.getMessage());
            return null;
        } finally {
            DBManager.release(conn, stat, null);
        }
    }

    @Override
    public String createRestoreProduce(Dmdatasource dmdatasource, String restoreProcedureName, Dmgrouptable dmgrouptable, Dmgroup dmgroup, List<String> relatedTables) {
        Connection conn = null;
        Statement stat = null;
        try {
            conn = DBManager.getConnection(DBManager.getOracleURL(dmdatasource),dmdatasource.getUsername(),dmdatasource.getPassword());
            stat = conn.createStatement();
            StringBuilder produreBuilder = new StringBuilder("create or replace procedure ");
            produreBuilder.append(restoreProcedureName);
            produreBuilder.append(" is ").append(" begin ");
            if(dmgroup.getIsdataextracted().equals(ConstantUtils.IS_EXTRACTED)){

                produreBuilder.append(createPartInsertSQL(dmgrouptable.getOriginaltable(),
                        dmgrouptable.getTargettable(),dmgroup.getMidtablename(),dmgroup.getDefaultcondition()));

                if(!AssertUtils.isEmpty(relatedTables)){
                    for(String tableName : relatedTables){

                        String targetTableName = tableName + "his";

                        produreBuilder.append(createPartInsertSQL(tableName,
                                targetTableName,dmgroup.getMidtablename(),dmgroup.getDefaultcondition()));
                    }
                }
                /**
                 * 删除
                 * 先删除关联表
                 * 再删除主表
                 */
                if(!AssertUtils.isEmpty(relatedTables)){
                    for(String tableName : relatedTables){

                        produreBuilder.append(createPartDeleteSQL(tableName + "his",dmgroup.getMidtablename(),
                                dmgroup.getDefaultcondition()));

                    }
                }

                produreBuilder.append(createPartDeleteSQL(dmgrouptable.getTargettable(),
                        dmgroup.getMidtablename(),dmgroup.getDefaultcondition()));

            } else {//todo:不需要提数的

            }
            produreBuilder.append(" commit; ").append(" end; ");
            stat.executeUpdate(produreBuilder.toString());
            return produreBuilder.toString();
        } catch (SQLException e) {
            logger.error("执行存储过程SQL异常：{}" , e.getMessage());
            return null;
        } finally {
            DBManager.release(conn, stat, null);
        }
    }

    /**
     * 创建插入片段SQL
     * @param targetTableName
     * @param originalTableName
     * @param midTableName
     * @param condition
     * @return
     */
    private String createPartInsertSQL(String targetTableName, String originalTableName, String midTableName, String condition){
        return " insert into " + targetTableName +
                " select * from " + originalTableName +
                " where " + condition +
                " in ( select " + condition +
                " from " + midTableName + ");";
    }

    /**
     * 删除片段SQL
     * @param tableName
     * @param midTableName
     * @param condition
     * @return
     */
    private String createPartDeleteSQL(String tableName, String midTableName, String condition){
        return " delete from " + tableName +
                " where " + condition +
                " in (select " + condition +
                " from " + midTableName + ");";
    }

    @Override
    public List<FieldInfoVO> findFieldsInfoByTableName(Dmdatasource dmdatasource, String tableName) {
        Connection conn = null;
        PreparedStatement pstat = null;
        ResultSet rs = null;
        List<FieldInfoVO> fieldInfoVOList = null;
        try {
            conn = DBManager.getConnection(DBManager.getOracleURL(dmdatasource),dmdatasource.getUsername(),dmdatasource.getPassword());
            pstat = conn.prepareStatement("select column_name,comments from user_col_comments where table_name=?");
            pstat.setString(1,tableName.toUpperCase());
            rs = pstat.executeQuery();
            logger.info("动态查询SQL：select column_name,comments from user_col_comments where table_name={}",tableName);
            fieldInfoVOList = new ArrayList<>();
            while(rs.next()){
                FieldInfoVO fieldInfoVO = new FieldInfoVO();
                fieldInfoVO.setField(rs.getString(1));
                fieldInfoVO.setDesc(rs.getString(2) == null ? "" : rs.getString(2));
                fieldInfoVOList.add(fieldInfoVO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            DBManager.release(conn, pstat, rs);
        }
        return fieldInfoVOList;
    }

    @Override
    public List<ConstraintInfoVO> queryRelatedInfoByTableName(Dmdatasource dmdatasource, String tableName) {
        Connection conn = null;
        PreparedStatement pstat = null;
        ResultSet rs = null;
        List<ConstraintInfoVO> constraintInfoVOs = null;
        try {
            conn = DBManager.getConnection(DBManager.getOracleURL(dmdatasource), dmdatasource.getUsername(), dmdatasource.getPassword());
            String sql = "select uc.constraint_name,uc.constraint_type,ucc.column_name " +
                    "from user_constraints uc,user_cons_columns ucc  " +
                    "where uc.constraint_name=ucc.constraint_name and uc.table_name =? " +
                    "and (uc.constraint_type='R' or uc.constraint_type='P')";
            pstat = conn.prepareStatement(sql);
            pstat.setString(1,tableName.toUpperCase());
            rs = pstat.executeQuery();
            if(rs != null){
                constraintInfoVOs = new ArrayList<>();
                while(rs.next()){
                    ConstraintInfoVO vo = new ConstraintInfoVO();
                    vo.setConstraintName(rs.getString(1));
                    vo.setConstrainType(rs.getString(2));
                    vo.setColumnName(rs.getString(3));
                    constraintInfoVOs.add(vo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.release(conn, pstat, rs);
        }
        return constraintInfoVOs;
    }

    @Override
    public void updateProcedure(String handleProcedure, Dmdatasource dmdatasource) {
        Connection conn = null;
        Statement stat = null;
        try {
            conn = DBManager.getConnection(DBManager.getOracleURL(dmdatasource), dmdatasource.getUsername(), dmdatasource.getPassword());
            stat = conn.createStatement();
            stat.execute(handleProcedure);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.release(conn, stat, null);
        }
    }

    @Override
    public boolean checkTableExist(Dmdatasource dmdatasource, String tableName) {
        Connection conn = null;
        PreparedStatement pstat = null;
        ResultSet rs = null;
        try {
            conn = DBManager.getConnection(DBManager.getOracleURL(dmdatasource), dmdatasource.getUsername(), dmdatasource.getPassword());
            String sql = "select table_name from all_tables where table_name=?";
            pstat = conn.prepareStatement(sql);
            pstat.setString(1,tableName.toUpperCase());
            rs = pstat.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.release(conn, pstat, rs);
        }
        return false;
    }

    @Override
    public void executeProcedure(Dmdatasource dmdatasource, String procedureName) throws NonePrintException {
        Connection conn = null;
        CallableStatement cstat = null;
        try{
            conn = DBManager.getConnection(DBManager.getOracleURL(dmdatasource), dmdatasource.getUsername(), dmdatasource.getPassword());
            cstat = conn.prepareCall("{call " + procedureName + "()}");
            cstat.execute();
        } catch(SQLException e){
            e.printStackTrace();
            logger.error("执行存储过程失败...存储过程名称：{}",procedureName);
            throw new NonePrintException(ErrorCodeDesc.ERROR_IN_EXECUTE_PROCEDURE.getCode(),ErrorCodeDesc.ERROR_IN_EXECUTE_PROCEDURE.getDesc());
        } finally {
            DBManager.release(conn, cstat, null);
        }
    }

    @Override
    public boolean deleteDataInMidTable(Dmdatasource dmdatasource, Dmgroup dmgroup) {
        String sql = "delete from " + dmgroup.getMidtablename();
        return executeSQL(dmdatasource,sql);
    }

    @Override
    public int executeExtract(Dmdatasource dmdatasource, Dmgroup dmgroup, MigrationParamVO paramVO) {
        boolean result = false;
        if("1".equals(paramVO.getParamType())){
            result = executeSQL(dmdatasource,dmgroup.getExtractscript(),paramVO.getStartTime(),paramVO.getEndTime());
        } else {
            String[] paramValue = paramVO.getParamValue().split(",");
            StringBuilder paramBuilder = new StringBuilder();
            for(int i = 0; i < paramValue.length; i++){
                paramBuilder.append("?,");
            }
            paramBuilder.deleteCharAt(paramBuilder.length() - 1);
            result = executeSQL(dmdatasource,dmgroup.getExtractscript().replace("?",paramBuilder.toString()),paramValue);
        }
        if(!result){
            return -1;
        }
        Connection conn = null;
        PreparedStatement pstat = null;
        ResultSet rs = null;
        try {
            conn = DBManager.getConnection(DBManager.getOracleURL(dmdatasource), dmdatasource.getUsername(), dmdatasource.getPassword());
            String sql = "select count(*) from " + dmgroup.getMidtablename();
            pstat = conn.prepareStatement(sql);
            rs = pstat.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            DBManager.release(conn, pstat, rs);
        }
    }
}
