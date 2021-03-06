package com.sinosoft.datamigration.dao;

import com.sinosoft.datamigration.exception.NonePrintException;
import com.sinosoft.datamigration.po.Dmdatasource;
import com.sinosoft.datamigration.po.Dmgroup;
import com.sinosoft.datamigration.po.Dmgrouptable;
import com.sinosoft.datamigration.vo.ConstraintInfoVO;
import com.sinosoft.datamigration.vo.FieldInfoVO;
import com.sinosoft.datamigration.vo.MigrationParamVO;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Elvis on 2017/9/22.
 */
public interface IDynamicDAO extends IDynamicBaseDAO{

    /**
     * 执行sql
     * @param dmdatasource
     * @param sql
     * @return
     */
    int executeSQL(Dmdatasource dmdatasource, String sql, Object... params) throws SQLException;

    /**
     * 查关联表
     * @param dmdatasource
     * @param tableName
     * @return
     */
    List<String> findRelatedTable(Dmdatasource dmdatasource, String tableName);

    /**
     * 创建存储过程
     */
    void createOrReplaceProduce(Dmdatasource dmdatasource, List<Dmgrouptable> dmgrouptables, Dmgroup dmgroup) throws SQLException;

    /**
     * 查询表字段信息
     * @param dmdatasource
     * @param tableName
     * @return
     */
    List<FieldInfoVO> findFieldsInfoByTableName(Dmdatasource dmdatasource, String tableName);

    /**
     * 查询主外键信息
     * @param dmdatasource
     * @param originalTable
     * @return
     */
    List<ConstraintInfoVO> queryRelatedInfoByTableName(Dmdatasource dmdatasource, String originalTable);

    /**
     * 修改存储过程
     * @param handleProcedure
     * @param dmdatasource
     */
    void updateProcedure(String handleProcedure, Dmdatasource dmdatasource);

    /**
     * 验证是否存在此表
     * @param tableName
     * @return
     */
    boolean checkTableExist(Dmdatasource dmdatasource,String tableName);

    /**
     * 执行存储过程
     */
    void executeProcedure(Dmdatasource dmdatasource, String procedureName) throws NonePrintException;

    /**
     * 删除中间表数据
     * @param dmdatasource
     * @param dmgroup
     */
    int deleteDataInMidTable(Dmdatasource dmdatasource, Dmgroup dmgroup) throws NonePrintException, SQLException;

    /**
     * 提取数据 返回提取的数量
     * @param dmdatasource
     * @param dmgroup
     * @param paramVO
     * @return
     */
    int executeExtract(Dmdatasource dmdatasource, Dmgroup dmgroup, MigrationParamVO paramVO) throws NonePrintException, SQLException;
}
