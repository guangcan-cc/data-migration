package com.sinosoft.datamigration.dao.impl;

import com.sinosoft.datamigration.common.Pager;
import com.sinosoft.datamigration.dao.ILogDAO;
import com.sinosoft.datamigration.po.Dmdatasource;
import com.sinosoft.datamigration.po.Dmhandlemsglog;
import com.sinosoft.datamigration.util.ObjectUtils;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Elvis on 2017/9/29.
 */
@Repository
public class LogDAOImpl extends BaseDAOImpl implements ILogDAO {

    @Override
    public Pager queryLogInfoByMap(Pager pager, Map<String, Object> paramMap) {

        StringBuilder builder = new StringBuilder("from Dmmigrationlog where 1=1");

        if(!ObjectUtils.isEmpty(paramMap)){
            if(paramMap.containsKey("groupName")){
                builder.append(" and groupname like '%").append(paramMap.get("groupName")).append("%' ");
            }
            if (paramMap.containsKey("groupId")){
                builder.append(" and groupid='").append(paramMap.get("groupId")).append("' ");
            }
            if (paramMap.containsKey("creator")){
                builder.append(" and handleperson like '%").append(paramMap.get("creator")).append("%' ");
            }
            if (paramMap.containsKey("startTime") && paramMap.containsKey("endTime")){
                builder.append(" and createtime>to_date('").append(paramMap.get("startTime")).append("','yyyy-mm-dd hh24:mi:ss') ");
                builder.append(" and createtime<=to_date('").append(paramMap.get("endTime")).append("','yyyy-mm-dd hh24:mi:ss') ");
            }
        }
        builder.append(" order by createtime desc ");
        String countHQL = "select count(*) " + builder;
        Session session = getSession();
        Number count = (Number) session.createQuery(countHQL).uniqueResult();
        List<Dmdatasource> list = session.createQuery(builder.toString()).setFirstResult(pager.getBeginIndex()).setMaxResults(pager.getPageSize()).list();

        pager.setContent(list);
        pager.setRecordTotal(count.intValue());
        return pager;
    }

    @Override
    public List<Dmhandlemsglog> queryHandleLogByMiglogId(String logId) {
        String hql = "from Dmhandlemsglog where migrationlogid=:logId";
        return getSession().createQuery(hql).setString("logId",logId).list();
    }
}
