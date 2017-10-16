package com.sinosoft.datamigration.dao.impl;

import com.sinosoft.datamigration.dao.IBaseDAO;
import com.sinosoft.datamigration.po.Dmgrouptable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * Created by Elvis on 2017/9/20.
 */
public class BaseDAOImpl extends HibernateDaoSupport implements IBaseDAO {

    @Resource
    private SessionFactory sessionFactory;

    //获取和当前线程绑定的Seesion
    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public <T> void insertPO(T po) {
        getSession().save(po);
    }

    @Override
    public <T> void batchPO(Collection<T> pos) {
        Session session = getSession();
        int i = 0;
        for(T po : pos){
            session.save(po);
            i++;
            if(i % 100 == 0){
                session.flush();
                session.clear();
            }
        }
    }

    @Override
    public <T> void deletePO(T po) {
        getSession().delete(po);
    }

    @Override
    public <T> T findById(Class<T> clazz, String id) {
        return this.getHibernateTemplate().get(clazz,id);
    }

    @Override
    public <T> void updatePO(T po) {
        this.getHibernateTemplate().saveOrUpdate(po);
    }

}
