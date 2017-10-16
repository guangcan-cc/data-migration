package com.sinosoft.datamigration.service.impl;

import com.sinosoft.datamigration.common.Pager;
import com.sinosoft.datamigration.dao.IUserDAO;
import com.sinosoft.datamigration.exception.NonePrintException;
import com.sinosoft.datamigration.po.Dmuserinfo;
import com.sinosoft.datamigration.service.IUserService;
import com.sinosoft.datamigration.util.ErrorCodeDesc;
import com.sinosoft.datamigration.util.MD5Utils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
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
        Dmuserinfo user = userDao.queryUserByUserCode(usercode);

        //验证用户信息
        if(user == null){
            throw new NonePrintException(ErrorCodeDesc.USER_LOGIN_ERROR.getCode(),ErrorCodeDesc.USER_LOGIN_ERROR.getDesc());
        }

        String md5Password = MD5Utils.getMD5Password(password);

        if (!md5Password.equals(user.getPassword())) {
            throw new NonePrintException(ErrorCodeDesc.USER_LOGIN_ERROR.getCode(),ErrorCodeDesc.USER_LOGIN_ERROR.getDesc());
        }

        return user;
    }

    @Override
    public Pager queryUserInfoByMap(Pager pager, Map<String, Object> paramMap) throws NonePrintException {
        return userDao.queryUserInfoByMap(pager,paramMap);
    }

    @Override
    public void addUser(Dmuserinfo dmuserinfo) throws NonePrintException {

        //查询用户信息
        Dmuserinfo user = userDao.queryUserByUserCode(dmuserinfo.getUsercode());

        if(user != null){
            throw new NonePrintException(ErrorCodeDesc.USER_IS_EXIST.getCode(),ErrorCodeDesc.USER_IS_EXIST.getDesc());
        }
        dmuserinfo.setPassword(MD5Utils.getMD5Password(dmuserinfo.getPassword()));
        userDao.insertPO(dmuserinfo);
    }

    @Override
    public void updateUserInfo(Dmuserinfo dmuserinfo) throws NonePrintException {

        //查询用户信息
        Dmuserinfo user = userDao.queryUserByUserCode(dmuserinfo.getUsercode());

        if(user == null){
            throw new NonePrintException(ErrorCodeDesc.USER_IS_NULL.getCode(),ErrorCodeDesc.USER_IS_NULL.getDesc());
        }

        dmuserinfo.setCreatetime(dmuserinfo.getCreatetime());
        dmuserinfo.setAddress(dmuserinfo.getAddress());
        dmuserinfo.setPassword(user.getPassword());
        userDao.updatePO(dmuserinfo);
    }

    @Override
    public void updatePassword(Dmuserinfo userInfo, String passwordOld, String passwordNew)throws NonePrintException {
        //查询用户信息
        Dmuserinfo user = userDao.queryUserByUserCode(userInfo.getUsercode());

        if(user == null){
            throw new NonePrintException(ErrorCodeDesc.USER_IS_NULL.getCode(),ErrorCodeDesc.USER_IS_NULL.getDesc());
        }

        if(!user.getPassword().equals(MD5Utils.getMD5Password(passwordOld))){
            throw new NonePrintException(ErrorCodeDesc.USER_LOGIN_ERROR.getCode(),ErrorCodeDesc.USER_LOGIN_ERROR.getDesc());
        }

        String md5Password = MD5Utils.getMD5Password(passwordNew);

        user.setPassword(md5Password);

        userDao.updatePO(user);
    }
}























