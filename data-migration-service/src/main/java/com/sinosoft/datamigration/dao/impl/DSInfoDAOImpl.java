package com.sinosoft.datamigration.dao.impl;

import com.sinosoft.datamigration.common.Pager;
import com.sinosoft.datamigration.dao.IDSInfoDAO;
import com.sinosoft.datamigration.po.Dmdatasource;
import com.sinosoft.datamigration.util.ObjectUtils;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Elvis on 2017/9/5.
 */
@Repository
public class DSInfoDAOImpl extends BaseDAOImpl implements IDSInfoDAO {

    @Override
    public Pager<Dmdatasource> queryDSInfoByMap(Pager<Dmdatasource> pager, Map<String, Object> paramMap) {

        StringBuilder builder = new StringBuilder("from Dmdatasource where 1=1");

        if(!ObjectUtils.isEmpty(paramMap)){
            if(paramMap.containsKey("dsName")){
                builder.append(" and dsName like '%").append(paramMap.get("dsName")).append("%' ");
            }
            if (paramMap.containsKey("username")){
                builder.append(" and username='").append(paramMap.get("username")).append("' ");
            }
            if (paramMap.containsKey("creator")){
                builder.append(" and creator like '%").append(paramMap.get("creator")).append("%' ");
            }
            if (paramMap.containsKey("isForbidden")){
                builder.append(" and isForbidden='").append(paramMap.get("isForbidden")).append("' ");
            }
            if (paramMap.containsKey("startTime") && paramMap.containsKey("endTime")){
                builder.append(" and createTime>to_date('").append(paramMap.get("startTime")).append("','yyyy-mm-dd hh24:mi:ss') ");
                builder.append(" and createTime<=to_date('").append(paramMap.get("endTime")).append("','yyyy-mm-dd hh24:mi:ss') ");
            }
        }
        builder.append(" order by createtime desc ");
        StringBuilder countBuilder = new StringBuilder("select count(*) ").append(builder);
        Session session = getSession();
        Number count = (Number) session.createQuery(countBuilder.toString()).uniqueResult();
        List<Dmdatasource> list = session.createQuery(builder.toString()).setFirstResult(pager.getBeginIndex()).setMaxResults(pager.getPageSize()).list();

        pager.setContent(list);
        pager.setRecordTotal(count.intValue());
        return pager;
    }

    @Override
    public Dmdatasource queryDSInfoById(String id) {
        String hql = "from Dmdatasource where id=:id";
        return (Dmdatasource) getSession().createQuery(hql).setString("id",id).uniqueResult();
    }

    @Override
    public List<Dmdatasource> queryDSNameLikeName(String dsname) {
        String hql = "from Dmdatasource where dsname like :dsname";
        return getSession().createQuery(hql).setString("dsname",dsname + "%").setMaxResults(3).list();
    }

    @Override
    public Dmdatasource queryDSInfoByName(String dsname) {
        String hql = "from Dmdatasource where dsname=:dsname";
        return (Dmdatasource) getSession().createQuery(hql).setString("dsname",dsname).uniqueResult();
    }
}

















