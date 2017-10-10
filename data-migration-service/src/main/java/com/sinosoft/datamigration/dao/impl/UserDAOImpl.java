package com.sinosoft.datamigration.dao.impl;

import com.sinosoft.datamigration.dao.IUserDAO;
import com.sinosoft.datamigration.po.Dmuserinfo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

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
}
