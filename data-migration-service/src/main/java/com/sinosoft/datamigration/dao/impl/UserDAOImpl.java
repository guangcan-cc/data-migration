package com.sinosoft.datamigration.dao.impl;

import com.sinosoft.datamigration.common.Pager;
import com.sinosoft.datamigration.dao.IUserDAO;
import com.sinosoft.datamigration.po.Dmuserinfo;
import com.sinosoft.datamigration.util.ObjectUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Elvis on 2017/8/30.
 */
@Repository
public class UserDAOImpl extends BaseDAOImpl implements IUserDAO {

    @Override
    public Dmuserinfo queryUserByUsername(String usercode) {
        String hql = "from Dmuserinfo where usercode=:usercode";
        return (Dmuserinfo) getSession().createQuery(hql)
                .setString("usercode",usercode).uniqueResult();
    }

    @Override
    public Pager queryUserInfoByMap(Pager pager, Map<String, Object> paramMap) {
        StringBuilder builder = new StringBuilder("from Dmuserinfo where 1=1");

        if(!ObjectUtils.isEmpty(paramMap)){
            if (paramMap.containsKey("username")){
                builder.append(" and username like '%").append(paramMap.get("username")).append("%' ");
            }
            if (paramMap.containsKey("isvalid")){
                builder.append(" and isvalid='").append(paramMap.get("isvalid")).append("' ");
            }
        }
        builder.append(" order by createtime desc ");
        StringBuilder countBuilder = new StringBuilder("select count(*) ").append(builder);
        Session session = getSession();
        Number count = (Number) session.createQuery(countBuilder.toString()).uniqueResult();
        List<Dmuserinfo> list = session.createQuery(builder.toString()).setFirstResult(pager.getBeginIndex()).setMaxResults(pager.getPageSize()).list();

        pager.setContent(list);
        pager.setRecordTotal(count.intValue());
        return pager;
    }
}
