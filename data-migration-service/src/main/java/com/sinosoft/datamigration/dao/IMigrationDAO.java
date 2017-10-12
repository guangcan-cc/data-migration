package com.sinosoft.datamigration.dao;

import com.sinosoft.datamigration.common.Pager;
import com.sinosoft.datamigration.po.DmTableRef;
import com.sinosoft.datamigration.po.Dmgroup;
import com.sinosoft.datamigration.po.Dmgrouptable;
import com.sinosoft.datamigration.vo.GroupQueryVO;

import java.util.List;

/**
 * Created by Elvis on 2017/9/7.
 */
public interface IMigrationDAO extends IBaseDAO {

    /**
     * 分页查询数据
     * @param pager
     * @param queryVO
     * @return
     */
    Pager<Dmgroup> queryGroupInfoByVO(Pager<Dmgroup> pager, GroupQueryVO queryVO);

    /**
     * 根据组ID查询组表信息
     * @param pager
     * @param groupId
     * @return
     */
    Pager<Dmgrouptable> queryTablesById(Pager<Dmgrouptable> pager, String groupId);

    /**
     * 根据组名查找组信息
     * @param groupname
     * @return
     */
    Dmgroup queryGroupInfoByGroupName(String groupname);

    /**
     * 根据组ID以及表名查找组表信息
     * @param groupid
     * @param originaltable
     * @return
     */
    Dmgrouptable queryTableByTableNameAndGroupId(String groupid, String originaltable);

    /**
     * 根据表名查找组表信息
     * @param tableName
     * @return
     */
    List<Dmgrouptable> queryTablesByTableName(String tableName);

    /**
     * 查询是否被同组表关联迁移
     * @param groupid
     * @param ref
     * @return
     */
    String queryTableByRefInGroup(String groupid, String ref);

    /**
     * 查询关联表信息
     * @param groupid
     * @param originaltable
     * @return
     */
    List<DmTableRef> findTableRefByGrouIdAndtableName(String groupid, String originaltable);

    /**
     * 根据组ID查表信息
     * @param groupId
     * @return
     */
    List<Dmgrouptable> findTablesByGroupId(String groupId);

    /**
     * 模糊查询组ID
     * @param groupId
     * @return
     */
    List<String> findGroupIdByPartOfGroupId(String groupId);

    /**
     * 模糊查询组名
     * @param groupName
     * @return
     */
    List<String> findGroupNameByPartOfGroupName(String groupName);

    /**
     * 根据组名查组ID
     * @param groupName
     * @return
     */
    String findGroupIdByGroupName(String groupName);

    /**
     * 查询组表
     * @param groupId
     * @return
     */
    List<Dmgrouptable> queryAllTablesById(String groupId);

    /**
     * 删除关联表
     * @param originaltable
     * @param groupid
     */
    void deleteTableRefByTable(String originaltable, String groupid);
}
