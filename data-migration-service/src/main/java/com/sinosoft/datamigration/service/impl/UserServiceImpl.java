package com.sinosoft.datamigration.service.impl;

import com.sinosoft.datamigration.common.Pager;
import com.sinosoft.datamigration.dao.IUserDAO;
import com.sinosoft.datamigration.exception.NonePrintException;
import com.sinosoft.datamigration.po.Dmuserinfo;
import com.sinosoft.datamigration.service.IUserService;
import com.sinosoft.datamigration.util.ErrorCodeDesc;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Created by Elvis on 2017/8/30.
 */
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private IUserDAO userDao;

    @Override
    public Dmuserinfo login(String usercode, String password) throws NonePrintException{

        if(!StringUtils.hasLength(usercode) || !StringUtils.hasLength(password)){
            throw new NonePrintException(ErrorCodeDesc.USERCODE_IS_NULL.getCode(),ErrorCodeDesc.USERCODE_IS_NULL.getDesc());
        }

        //查询用户信息
        Dmuserinfo user = userDao.queryUserByUsername(usercode);

        //验证用户信息
        if(user == null){
            throw new NonePrintException(ErrorCodeDesc.USER_IS_NULL.getCode(),ErrorCodeDesc.USER_IS_NULL.getDesc());
        }

        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new NonePrintException(ErrorCodeDesc.SYSTEM_ERROR.getCode(),ErrorCodeDesc.SYSTEM_ERROR.getDesc());
        }
        BigInteger number = new BigInteger(1, (md5.digest(password
                .getBytes())));
        String md5Password = number.toString(16);

        if (!md5Password.equals(user.getPassword())) {
            throw new NonePrintException(ErrorCodeDesc.USER_LOGIN_ERROR.getCode(),ErrorCodeDesc.USER_LOGIN_ERROR.getDesc());
        }

        return user;
    }

    @Override
    public Pager queryUserInfoByMap(Pager pager, Map<String, Object> paramMap) throws NonePrintException {
        return userDao.queryUserInfoByMap(pager,paramMap);
    }
}























