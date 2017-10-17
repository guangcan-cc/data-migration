package com.sinosoft.common.web.controller;

import com.sinosoft.datamigration.common.Pager;
import com.sinosoft.datamigration.exception.NonePrintException;
import com.sinosoft.datamigration.po.Dmdatasource;
import com.sinosoft.datamigration.po.Dmuserinfo;
import com.sinosoft.datamigration.service.IDSInfoService;
import com.sinosoft.datamigration.util.*;
import com.sinosoft.datamigration.vo.DSInfoVO;
import org.springframework.core.SpringProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Elvis on 2017/8/31.
 */
@Controller
@RequestMapping("/dataSource")
public class DSInfoController extends BaseController{

    @Resource
    private IDSInfoService dsInfoService;

    @RequestMapping("/queryDSInfo")
    @ResponseBody
    public Map<String, Object> queryDSInfo(@RequestParam(value = "dsName",required = false) String dsName,
                              @RequestParam(value = "username",required = false) String username,
                              @RequestParam(value = "creator",required = false) String creator,
                              @RequestParam(value = "startTime",required = false) Date startTime,
                              @RequestParam(value = "endTime",required = false) Date endTime,
                              @RequestParam(value = "isForbidden",required = false) String isForbidden,Pager<Dmdatasource> pager){

        Map<String,Object> resultMap = new HashMap<>();

        Map<String, Object> paramMap = new HashMap<>();
        if(!ObjectUtils.isEmpty(dsName)){
            paramMap.put("dsName",dsName);
        }
        if(!ObjectUtils.isEmpty(username)){
            paramMap.put("username",username);
        }
        if(!ObjectUtils.isEmpty(creator)){
            paramMap.put("creator",creator);
        }
        if(!ObjectUtils.isEmpty(isForbidden)){
            paramMap.put("isForbidden",isForbidden);
        }
        if(!ObjectUtils.isEmpty(startTime) && !ObjectUtils.isEmpty(endTime)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            paramMap.put("startTime",sdf.format(startTime));
            paramMap.put("endTime",sdf.format(DateUtils.addDays(endTime,1)));
        }
        try {
            pager = dsInfoService.queryDSInfoByMap(pager,paramMap);
            resultMap.put(ResultDesc.CODE,ResultDesc.SUCCESS);
            resultMap.put("pager",pager);
        } catch (NonePrintException e) {
            e.printStackTrace();
            resultMap.put(ResultDesc.CODE,e.getErrCode());
            resultMap.put(ResultDesc.MSG,e.getErrMsg());
        }
        return resultMap;
    }

    @RequestMapping("/addDSInfo")
    @ResponseBody
    public Map<String,Object> addDSInfo(Dmdatasource datasource, HttpSession session){
        Map<String,Object> resultMap = new HashMap<>();
        Dmuserinfo user = (Dmuserinfo) session.getAttribute("user");
        if(user == null){
            resultMap.put(ResultDesc.CODE, ErrorCodeDesc.SESSION_USER_OBSOLETE.getCode());
            resultMap.put(ResultDesc.MSG,ErrorCodeDesc.SESSION_USER_OBSOLETE.getDesc());
            return resultMap;
        }
        datasource.setCreatetime(new Date());
        datasource.setCreator(user.getUsername());
        datasource.setId(UUID.randomUUID().toString());
        datasource.setIsforbidden("0");
        try {
            dsInfoService.insertDSInfo(datasource);
            resultMap.put(ResultDesc.CODE,ResultDesc.SUCCESS);
            resultMap.put(ResultDesc.MSG,"保存成功");
            resultMap.put("id",datasource.getId());
        } catch (NonePrintException e) {
            e.printStackTrace();
            resultMap.put(ResultDesc.CODE,e.getErrCode());
            resultMap.put(ResultDesc.MSG,e.getErrMsg());
        }
        return resultMap;
    }

    @RequestMapping("/connectTest")
    @ResponseBody
    public Map<String,Object> connectTest(DSInfoVO dsInfoVO){
        Map<String,Object> resultMap = new HashMap<>();

        String driverClassName = SpringProperties.getProperty("oracle_driver_class");
        String url = SpringProperties.getProperty("oracle_url")
                .replace("ip",dsInfoVO.getServerip())
                .replace("port",dsInfoVO.getPort())
                .replace("database",dsInfoVO.getServername());
        String username = dsInfoVO.getUsername();
        String password = dsInfoVO.getPassword();

        //测试连接数据源
        boolean result = DynamicDataSource.connectTest(driverClassName,url,username,password);
        resultMap.put(ResultDesc.CODE,result ? ResultDesc.SUCCESS : ResultDesc.FAILURE);
        return resultMap;
    }

    @RequestMapping("/editShowDSInfo")
    public String editShowDSInfo(@RequestParam(value = "id") String id, HttpServletRequest request){

        Dmuserinfo user = (Dmuserinfo) request.getSession().getAttribute("user");
        if(user == null){
            return "/admin/login";
        }
        try {
            Dmdatasource datasource = dsInfoService.queryDSInfoById(id);
            request.setAttribute("datasource",datasource);
        } catch (NonePrintException e) {
            e.printStackTrace();
            return "/error/error_500";
        }
        return "/dataSourceInfo/data_source_edit";
    }

    @RequestMapping("/editDSInfo")
    @ResponseBody
    public Map<String,Object> editDSInfo(Dmdatasource datasource){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            dsInfoService.updateDSInfo(datasource);
            resultMap.put(ResultDesc.CODE,ResultDesc.SUCCESS);
            resultMap.put(ResultDesc.MSG,"保存成功");
        } catch (NonePrintException e) {
            e.printStackTrace();
            resultMap.put(ResultDesc.CODE,e.getErrCode());
            resultMap.put(ResultDesc.MSG,e.getErrMsg());
        }
        return resultMap;

    }
}
