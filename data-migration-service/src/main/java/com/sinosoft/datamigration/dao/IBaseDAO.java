package com.sinosoft.datamigration.dao;

import com.sinosoft.datamigration.po.Dmgrouptable;

import java.util.Collection;
import java.util.List;

/**
 * Created by Elvis on 2017/9/20.
 */
public interface IBaseDAO {

    <T> void insertPO(T po);

    <T> void batchPO(Collection<T> pos);

    <T> void deletePO(T po);

    <T> T findById(Class<T> clazz, String id);

    void updatePO(Dmgrouptable dmgrouptable);
}
