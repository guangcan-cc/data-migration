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
    public int executeSQL(Dmdatasource dmdatasource, String sql, Object... params) throws SQLException {
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
            return pstat.executeUpdate();
        } finally {
            DBManager.release(conn, pstat, null);
        }
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
    public void createOrReplaceProduce(Dmdatasource dmdatasource, List<Dmgrouptable> dmgrouptables, Dmgroup dmgroup) throws SQLException {
        Connection conn = null;
        Statement stat = null;
        try {
            conn = DBManager.getConnection(DBManager.getOracleURL(dmdatasource),dmdatasource.getUsername(),dmdatasource.getPassword());
            stat = conn.createStatement();
            //迁移存储过程
            StringBuilder handlePackage = new StringBuilder("create or replace package ");
            handlePackage.append(dmgroup.getId());
            handlePackage.append(" as ");

            for(Dmgrouptable dmgrouptable : dmgrouptables){
                handlePackage.append(" procedure ")
                        .append(dmgrouptable.getHandleprocedurename())
                        .append(";");
                handlePackage.append(" procedure ")
                        .append(dmgrouptable.getRestoreprocedurename())
                        .append(";");
            }
            handlePackage.append(" end ").append(dmgroup.getId()).append(";");
            stat.executeUpdate(handlePackage.toString());

            StringBuilder handlePackageBody = new StringBuilder("create or replace package body ");
            handlePackageBody.append(dmgroup.getId());
            handlePackageBody.append(" as ");

            for(Dmgrouptable dmgrouptable : dmgrouptables){
                handlePackageBody.append(dmgrouptable.getHandleprocedure());
                handlePackageBody.append(dmgrouptable.getRestoreprocedure());
            }

            handlePackageBody.append(" end ").append(dmgroup.getId()).append(";");

            stat.executeUpdate(handlePackageBody.toString());

        } finally {
            DBManager.release(conn, stat, null);
        }
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
    public int deleteDataInMidTable(Dmdatasource dmdatasource, Dmgroup dmgroup) throws SQLException {
        String sql = "delete from " + dmgroup.getMidtablename();
        return executeSQL(dmdatasource,sql);
    }

    @Override
    public int executeExtract(Dmdatasource dmdatasource, Dmgroup dmgroup, MigrationParamVO paramVO) throws SQLException {
        if("1".equals(paramVO.getParamType())){
            executeSQL(dmdatasource,dmgroup.getExtractscript(),paramVO.getStartTime(),paramVO.getEndTime());
        } else {
            String[] paramValue = paramVO.getParamValue().split(",");
            StringBuilder paramBuilder = new StringBuilder();
            for(int i = 0; i < paramValue.length; i++){
                paramBuilder.append("?,");
            }
            paramBuilder.deleteCharAt(paramBuilder.length() - 1);
            executeSQL(dmdatasource,dmgroup.getExtractscript().replace("?",paramBuilder.toString()),paramValue);
        }
        Connection conn = null;
        PreparedStatement pstat = null;
        ResultSet rs = null;
        try {
            conn = DBManager.getConnection(DBManager.getOracleURL(dmdatasource), dmdatasource.getUsername(), dmdatasource.getPassword());
            String sql = "select count(*) from " + dmgroup.getMidtablename();
            pstat = conn.prepareStatement(sql);
            rs = pstat.executeQuery();
            return rs.getInt(1);
        } finally {
            DBManager.release(conn, pstat, rs);
        }
    }
}
