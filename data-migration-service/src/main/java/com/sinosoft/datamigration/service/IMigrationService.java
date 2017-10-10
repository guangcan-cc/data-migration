package com.sinosoft.datamigration.service;

import com.sinosoft.datamigration.common.Pager;
import com.sinosoft.datamigration.exception.NonePrintException;
import com.sinosoft.datamigration.po.*;
import com.sinosoft.datamigration.vo.GroupQueryVO;
import com.sinosoft.datamigration.vo.MigrationParamVO;
import com.sinosoft.datamigration.vo.RelatedInfoVO;
import com.sinosoft.datamigration.vo.TableInfoVO;

import java.util.List;

/**
 * Created by Elvis on 2017/9/7.
 */
public interface IMigrationService {

    /**
     * 分页查询数据
     * @param pager
     * @param queryVO
     * @return
     */
    Pager<Dmgroup> queryGroupInfoByVO(Pager<Dmgroup> pager, GroupQueryVO queryVO) throws NonePrintException;

    /**
     * 添加组配置信息
     * @param dmgroup
     * @throws NonePrintException
     */
    void insertGroupInfo(Dmgroup dmgroup) throws NonePrintException;

    /**
     * 根据id查询
     * @param id
     * @return
     * @throws NonePrintException
     */
    Dmgroup findGroupInfoById(String id) throws NonePrintException;

    /**
     * 修改组信息
     * @param dmgroup
     * @throws NonePrintException
     */
    void updateGroupInfo(Dmgroup dmgroup) throws NonePrintException;

    /**
     * 根据组ID查询组表信息
     * @param pager
     * @param groupId
     * @return
     * @throws NonePrintException
     */
    Pager<Dmgrouptable> queryTablesById(Pager<Dmgrouptable> pager, String groupId) throws NonePrintException;

    /**
     * 添加表信息
     * @param dmgrouptable
     * @throws NonePrintException
     */
    void addTableInfo(Dmgrouptable dmgrouptable) throws NonePrintException;

    /**
     * 查看该表是否已配置其他组
     * @param tableName
     * @throws NonePrintException
     */
    boolean checkTableInfoInOthers(String groupId, String tableName) throws NonePrintException;

    /**
     * 根据ID查表信息
     * @param id
     * @return
     * @throws NonePrintException
     */
    Dmgrouptable findTableInfoById(String id)throws NonePrintException;

    /**
     * 修改表信息
     * @param dmgrouptable
     */
    void updateTableInfo(Dmgrouptable dmgrouptable)throws NonePrintException;

    /**
     * 删除表信息
     * @param id
     * @throws NonePrintException
     */
    void deleteTableInfo(String id)throws NonePrintException;

    /**
     * 查询字段信息
     * @param dmgrouptable
     * @param dmdatasource
     * @param dmTableRefList
     * @return
     * @throws NonePrintException
     */
    List<TableInfoVO> queryTableInfos(Dmgrouptable dmgrouptable, Dmdatasource dmdatasource, List<DmTableRef> dmTableRefList)throws NonePrintException;

    /**
     * 查询关联表
     * @param groupid
     * @param originaltable
     * @return
     */
    List<DmTableRef> findTableRefByGrouIdAndtableName(String groupid, String originaltable)throws NonePrintException;

    /**
     * 查询关联信息详情
     * @param dmgrouptable
     * @param dmdatasource
     * @param dmTableRefList
     * @return
     * @throws NonePrintException
     */
    List<RelatedInfoVO> queryRelatedInfo(Dmgrouptable dmgrouptable, Dmdatasource dmdatasource, List<DmTableRef> dmTableRefList)throws NonePrintException;

    /**
     * 修改迁移信息
     * @param id
     * @param handleProcedure
     * @throws NonePrintException
     */
    void updateMigrationProcedure(String id, String handleProcedure)throws NonePrintException;

    /**
     * 修改还原信息
     * @param id
     * @param retoreProcedure
     */
    void updateRestoreProcedure(String id, String retoreProcedure)throws NonePrintException;

    /**
     * 修改信息
     * @param id
     * @param handleContent
     */
    void updateHandleContent(String id, String handleContent)throws NonePrintException;

    /**
     * 根据组ID查表
     * @param groupId
     * @return
     * @throws NonePrintException
     */
    List<Dmgrouptable> findTablesByGroupIdOrGroupName(String groupId,String groupName)throws NonePrintException;

    /**
     * 模糊查询组ID
     * @param groupId
     * @return
     * @throws NonePrintException
     */
    List<String> findGroupIdByPartOfGroupId(String groupId)throws NonePrintException;

    /**
     * 模糊查询组名
     * @param groupName
     * @return
     * @throws NonePrintException
     */
    List<String> findGroupNameByPartOfGroupName(String groupName)throws NonePrintException;

    /**
     * 迁移
     * @param user
     * @param paramVO
     * @throws NonePrintException
     */
    void migrateData(Dmuserinfo user, MigrationParamVO paramVO)throws NonePrintException;

    /**
     * 还原
     * @param user
     * @param paramVO
     * @throws NonePrintException
     */
    void restoreData(Dmuserinfo user, MigrationParamVO paramVO)throws NonePrintException;
}
