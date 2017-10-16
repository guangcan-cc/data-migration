package com.sinosoft.datamigration.service;

import com.sinosoft.datamigration.common.Pager;
import com.sinosoft.datamigration.exception.NonePrintException;
import com.sinosoft.datamigration.po.Dmuserinfo;

import java.util.Map;

/**
 * Created by Elvis on 2017/8/30.
 */
public interface IUserService {

    Dmuserinfo login(String usercode, String password) throws NonePrintException;

    Pager queryUserInfoByMap(Pager pager, Map<String, Object> paramMap)throws NonePrintException;

    void addUser(Dmuserinfo dmuserinfo)throws NonePrintException;

    void updateUserInfo(Dmuserinfo dmuserinfo)throws NonePrintException;

    void updatePassword(Dmuserinfo userInfo, String passwordOld, String passwordNew)throws NonePrintException;
}
