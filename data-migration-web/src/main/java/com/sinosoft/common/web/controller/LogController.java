package com.sinosoft.common.web.controller;

import com.sinosoft.datamigration.common.Pager;
import com.sinosoft.datamigration.exception.NonePrintException;
import com.sinosoft.datamigration.po.Dmgrouptable;
import com.sinosoft.datamigration.po.Dmhandlemsglog;
import com.sinosoft.datamigration.service.ILogService;
import com.sinosoft.datamigration.service.IMigrationService;
import com.sinosoft.datamigration.util.AssertUtils;
import com.sinosoft.datamigration.util.DateUtils;
import com.sinosoft.datamigration.util.ObjectUtils;
import com.sinosoft.datamigration.util.ResultDesc;
import com.sinosoft.datamigration.vo.HandleLogVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Elvis on 2017/10/10.
 */
@Controller
@RequestMapping("/log")
public class LogController {

    @Resource
    private ILogService logService;
    @Resource
    private IMigrationService migrationService;

    @RequestMapping("/queryLogInfo")
    @ResponseBody
    public Map<String, Object> queryLogInfo(@RequestParam(value = "groupId",required = false) String groupId,
                                            @RequestParam(value = "groupName",required = false) String groupName,
                                            @RequestParam(value = "creator",required = false) String creator,
                                            @RequestParam(value = "startTime",required = false) Date startTime,
                                            @RequestParam(value = "endTime",required = false) Date endTime,
                                            Pager pager){

        Map<String,Object> resultMap = new HashMap<>();

        Map<String, Object> paramMap = new HashMap<>();
        if(!ObjectUtils.isEmpty(groupId)){
            paramMap.put("groupId",groupId);
        }
        if(!ObjectUtils.isEmpty(groupName)){
            paramMap.put("groupName",groupName);
        }
        if(!ObjectUtils.isEmpty(creator)){
            paramMap.put("creator",creator);
        }
        if(!ObjectUtils.isEmpty(startTime) && !ObjectUtils.isEmpty(endTime)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            paramMap.put("startTime",sdf.format(startTime));
            paramMap.put("endTime",sdf.format(DateUtils.addDays(endTime,1)));
        }
        try {
            pager = logService.queryLogInfoByMap(pager,paramMap);
            resultMap.put(ResultDesc.CODE,ResultDesc.SUCCESS);
            resultMap.put("pager",pager);
        } catch (NonePrintException e) {
            e.printStackTrace();
            resultMap.put(ResultDesc.CODE,e.getErrCode());
            resultMap.put(ResultDesc.MSG,e.getErrMsg());
        }
        return resultMap;
    }

    @RequestMapping("/showDetailLogInfo")
    public String showDetailLogInfo(@RequestParam(value = "logId") String logId,
                                    @RequestParam(value = "groupId") String groupId,
                                    ModelMap modelMap){
        try {
            List<Dmgrouptable> tables = migrationService.queryAllTablesById(groupId);

            List<Dmhandlemsglog> dmhandleMsgLogs = logService.queryHandleLogByMiglogId(logId);

            modelMap.put("dmgroup",migrationService.findGroupInfoById(groupId));
            modelMap.put("tables",tables);
            modelMap.put("dmhandleMsgLogs",dmhandleMsgLogs);
        } catch (NonePrintException e) {
            e.printStackTrace();
            return "/error/error_500";
        }
        return "/log/detail_log_info";
    }
}
