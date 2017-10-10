package com.sinosoft.datamigration.service;

import com.sinosoft.datamigration.exception.NonePrintException;
import com.sinosoft.datamigration.po.Dmuserinfo;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Elvis on 2017/8/30.
 */
public interface IUserService {

    Dmuserinfo login(String usercode, String password) throws NonePrintException, NoSuchAlgorithmException;
}
