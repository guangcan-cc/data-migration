package com.sinosoft.datamigration.dao;

import com.sinosoft.datamigration.common.Pager;
import com.sinosoft.datamigration.po.Dmuserinfo;

import java.util.Map;

/**
 * Created by Elvis on 2017/8/30.
 */
public interface IUserDAO extends IBaseDAO{

    Dmuserinfo queryUserByUserCode(String usercode);

    Pager queryUserInfoByMap(Pager pager, Map<String, Object> paramMap);
}
