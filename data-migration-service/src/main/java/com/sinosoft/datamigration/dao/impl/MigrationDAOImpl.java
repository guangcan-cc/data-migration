package com.sinosoft.datamigration.dao.impl;

import com.sinosoft.datamigration.common.Pager;
import com.sinosoft.datamigration.dao.IMigrationDAO;
import com.sinosoft.datamigration.po.DmTableRef;
import com.sinosoft.datamigration.po.Dmgroup;
import com.sinosoft.datamigration.po.Dmgrouptable;
import com.sinosoft.datamigration.util.AssertUtils;
import com.sinosoft.datamigration.util.DateUtils;
import com.sinosoft.datamigration.vo.GroupQueryVO;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Elvis on 2017/9/7.
 */
@Repository
public class MigrationDAOImpl extends BaseDAOImpl implements IMigrationDAO {

    @Override
    public Pager<Dmgroup> queryGroupInfoByVO(Pager<Dmgroup> pager, GroupQueryVO queryVO) {

        StringBuilder builder = new StringBuilder("from Dmgroup where 1=1");

        if(!AssertUtils.isEmpty(queryVO.getId())){
            builder.append(" and id='").append(queryVO.getId()).append("' ");
        }
        if(!AssertUtils.isEmpty(queryVO.getGroupname())){
            builder.append(" and groupname like '%").append(queryVO.getGroupname()).append("%' ");
        }
        if(!AssertUtils.isEmpty(queryVO.getType())){
            builder.append(" and type='").append(queryVO.getType()).append("' ");
        }
        if(!AssertUtils.isEmpty(queryVO.getCreator())){
            builder.append(" and creator like '%").append(queryVO.getCreator()).append("%' ");
        }
        if(!AssertUtils.isEmpty(queryVO.getIsForbidden())){
            builder.append(" and isforbidden='").append(queryVO.getIsForbidden()).append("' ");
        }
        if (!AssertUtils.isEmpty(queryVO.getStartTime()) && !AssertUtils.isEmpty(queryVO.getEndTime())){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            builder.append(" and createTime>to_date('").append(sdf.format(queryVO.getStartTime())).append("','yyyy-mm-dd hh24:mi:ss') ");
            builder.append(" and createTime<=to_date('").append(sdf.format(DateUtils.addDays(queryVO.getEndTime(),1))).append("','yyyy-mm-dd hh24:mi:ss') ");
        }

        builder.append(" order by createtime desc ");
        StringBuilder countBuilder = new StringBuilder("select count(*) ").append(builder);
        Session session = super.getSession();
        Number count = (Number) session.createQuery(countBuilder.toString()).uniqueResult();
        List list = session.createQuery(builder.toString()).setFirstResult(pager.getBeginIndex()).setMaxResults(pager.getPageSize()).list();
        pager.setContent(list);
        pager.setRecordTotal(count.intValue());
        return pager;
    }

    @Override
    public Pager<Dmgrouptable> queryTablesById(Pager<Dmgrouptable> pager, String groupId) {
        String hql = "from Dmgrouptable where groupid=:groupId";
        StringBuilder countBuilder = new StringBuilder("select count(*) ").append(hql);
        Session session = super.getSession();
        Number count = (Number) session.createQuery(countBuilder.toString()).setString("groupId",groupId).uniqueResult();
        List list = session.createQuery(hql).setString("groupId",groupId).list();
        pager.setContent(list);
        pager.setRecordTotal(count.intValue());
        return pager;
    }

    @Override
    public Dmgroup queryGroupInfoByGroupName(String groupname) {
        String hql = "from Dmgroup where groupname=:groupname";
        return (Dmgroup) getSession().createQuery(hql).setString("groupname",groupname).uniqueResult();
    }

    @Override
    public Dmgrouptable queryTableByTableNameAndGroupId(String groupid, String originaltable) {
        String hql = "from Dmgrouptable where groupid=:groupid and originaltable=:originaltable";
        return (Dmgrouptable) getSession().createQuery(hql)
                .setString("groupid",groupid)
                .setString("originaltable",originaltable).uniqueResult();
    }

    @Override
    public List<Dmgrouptable> queryTablesByTableName(String tableName) {
        String hql = "from Dmgrouptable where originaltable=:tableName";
        return getSession().createQuery(hql).setString("tableName",tableName).list();
    }

    @Override
    public String queryTableByRefInGroup(String groupId, String ref) {
        String hql = "select tableName from DmTableRef where groupId=:groupId and tableRef=:ref";
        return (String) getSession().createQuery(hql)
                .setString("groupId",groupId)
                .setString("ref",ref).uniqueResult();
    }

    @Override
    public List<DmTableRef> findTableRefByGrouIdAndtableName(String groupid, String originaltable) {
        String hql = "from DmTableRef where groupId=:groupid and tableName=:originaltable";
        return getSession().createQuery(hql)
                .setString("groupid",groupid)
                .setString("originaltable",originaltable).list();
    }

    @Override
    public List<Dmgrouptable> findTablesByGroupId(String groupId) {
        String hql = "from Dmgrouptable where groupid=:groupid";
        return getSession().createQuery(hql).setString("groupid",groupId).list();
    }

    @Override
    public List<String> findGroupIdByPartOfGroupId(String groupId) {
        String hql = "select id from Dmgroup where id like :groupId";
        return getSession().createQuery(hql).setString("groupId",groupId + "%").setMaxResults(4).list();
    }

    @Override
    public List<String> findGroupNameByPartOfGroupName(String groupName) {
        String hql = "select groupname from Dmgroup where groupname like :groupName";
        return getSession().createQuery(hql).setString("groupName",groupName + "%").setMaxResults(4).list();
    }

    @Override
    public String findGroupIdByGroupName(String groupName) {
        String hql = "select id from Dmgroup where groupname=:groupName";
        return (String) getSession().createQuery(hql).setString("groupName",groupName).uniqueResult();
    }

    @Override
    public List<Dmgrouptable> queryAllTablesById(String groupId) {
        String hql = "from Dmgrouptable where groupid=:groupId";
        return getSession().createQuery(hql).setString("groupId",groupId).list();
    }

    @Override
    public void deleteTableRefByTable(String tableName, String groupId) {
        String hql = "delete from DmTableRef where groupId=:groupId and tableName=:tableName";
        getSession().createQuery(hql).setString("groupId",groupId).setString("tableName",tableName).executeUpdate();
    }
}
