package com.sinosoft.common.web.controller;

import com.sinosoft.datamigration.common.Pager;
import com.sinosoft.datamigration.exception.NonePrintException;
import com.sinosoft.datamigration.po.*;
import com.sinosoft.datamigration.service.IDSInfoService;
import com.sinosoft.datamigration.service.IMigrationService;
import com.sinosoft.datamigration.util.ConstantUtils;
import com.sinosoft.datamigration.util.ErrorCodeDesc;
import com.sinosoft.datamigration.util.ResultDesc;
import com.sinosoft.datamigration.vo.GroupQueryVO;
import com.sinosoft.datamigration.vo.MigrationParamVO;
import com.sinosoft.datamigration.vo.RelatedInfoVO;
import com.sinosoft.datamigration.vo.TableInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by Elvis on 2017/9/1.
 */
@Controller
@RequestMapping("/migration")
public class MigrationController {

    private static final Logger logger = LoggerFactory.getLogger(MigrationController.class);

    @Resource
    private IMigrationService migrationService;
    @Resource
    private IDSInfoService dsInfoService;

    @RequestMapping("/queryGroupInfo")
    @ResponseBody
    public Map<String, Object> queryGroupInfo(GroupQueryVO queryVO, Pager<Dmgroup> pager){

        Map<String,Object> resultMap = new HashMap<>();

        try {
            pager = migrationService.queryGroupInfoByVO(pager,queryVO);
            resultMap.put(ResultDesc.CODE,ResultDesc.SUCCESS);
            resultMap.put("pager",pager);
        } catch (NonePrintException e) {
            resultMap.put(ResultDesc.CODE,e.getErrCode());
            resultMap.put(ResultDesc.MSG,e.getErrMsg());
        }

        return resultMap;
    }

    @RequestMapping("/ajaxQueryDSName")
    @ResponseBody
    public Map<String, Object> ajaxQueryDSName(String dsName){

        Map<String,Object> resultMap = new HashMap<>();

        try {
            List<Dmdatasource> dmdatasources =  dsInfoService.queryDSNameLikeName(dsName);
            resultMap.put("dmdatasources",dmdatasources);
            resultMap.put(ResultDesc.CODE,ResultDesc.SUCCESS);
        } catch (NonePrintException e) {
            resultMap.put(ResultDesc.CODE,e.getErrCode());
            resultMap.put(ResultDesc.MSG,e.getErrMsg());
        }

        return resultMap;
    }

    @RequestMapping("/addGroupInfo")
    @ResponseBody
    public Map<String,Object> addGroupInfo(Dmgroup dmgroup, HttpSession session){
        Map<String,Object> resultMap = new HashMap<>();
        Dmuserinfo user = (Dmuserinfo) session.getAttribute("user");
        if(user == null){
            resultMap.put(ResultDesc.CODE, ErrorCodeDesc.SESSION_USER_OBSOLETE.getCode());
            resultMap.put(ResultDesc.MSG,ErrorCodeDesc.SESSION_USER_OBSOLETE.getDesc());
            return resultMap;
        }
        dmgroup.setCreator(user.getUsername());
        dmgroup.setCreatetime(new Date());
        dmgroup.setIsforbidden(ConstantUtils.IS_NOT_FORBIDDEN);
        try {
            migrationService.insertGroupInfo(dmgroup);
            resultMap.put(ResultDesc.CODE,ResultDesc.SUCCESS);
            resultMap.put(ResultDesc.MSG,"保存成功");
            resultMap.put("id",dmgroup.getId());
        } catch (NonePrintException e) {
            resultMap.put(ResultDesc.CODE,e.getErrCode());
            resultMap.put(ResultDesc.MSG,e.getErrMsg());
        }
        return resultMap;
    }

    @RequestMapping("/editShowGroupInfo")
    public String editShowGroupInfo(@RequestParam(value = "id") String id, HttpServletRequest request){

        try {
            Dmgroup dmgroup = migrationService.findGroupInfoById(id);
            request.setAttribute("dmgroup",dmgroup);
        } catch (NonePrintException e) {
            return "/error/error_500";
        }

        return "/migrationConfig/group_edit";
    }

    @RequestMapping("/editGroupInfo")
    @ResponseBody
    public Map<String,Object> editGroupInfo(Dmgroup dmgroup){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            migrationService.updateGroupInfo(dmgroup);
            resultMap.put(ResultDesc.CODE,ResultDesc.SUCCESS);
            resultMap.put(ResultDesc.MSG,"保存成功");
        } catch (NonePrintException e) {
            resultMap.put(ResultDesc.CODE,e.getErrCode());
            resultMap.put(ResultDesc.MSG,e.getErrMsg());
        }
        return resultMap;
    }

    @RequestMapping("/showTablesInGroup")
    public String showTablesInGroup(@RequestParam(value = "id") String id, ModelMap modelMap){
        try {
            modelMap.put("dmgroup",migrationService.findGroupInfoById(id));
        } catch (NonePrintException e) {
            return "/error/error_500";
        }
        return "/migrationConfig/tables_in_group_view";
    }

    @RequestMapping("/queryTables")
    @ResponseBody
    public Map<String,Object> queryTables(@RequestParam(value = "id") String id, Pager<Dmgrouptable> pager){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            pager = migrationService.queryTablesById(pager,id);
            resultMap.put(ResultDesc.CODE,ResultDesc.SUCCESS);
            resultMap.put("pager",pager);
        } catch (NonePrintException e) {
            resultMap.put(ResultDesc.CODE,e.getErrCode());
            resultMap.put(ResultDesc.MSG,e.getErrMsg());
        }
        return resultMap;
    }

    @RequestMapping("/queryGroup")
    @ResponseBody
    public Map<String,Object> queryGroup(@RequestParam(value = "groupId") String groupId){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            resultMap.put("groupInfo",migrationService.findGroupInfoById(groupId));
            resultMap.put(ResultDesc.CODE,ResultDesc.SUCCESS);
        } catch (NonePrintException e) {

            resultMap.put(ResultDesc.CODE,e.getErrCode());
            resultMap.put(ResultDesc.MSG,e.getErrMsg());
        }
        return resultMap;
    }

    @RequestMapping("/checkTableInfo")
    @ResponseBody
    public Map<String,Object> checkTableInfo(@RequestParam(value = "tableName") String tableName,
                                             @RequestParam(value = "groupId") String groupId){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            boolean result = migrationService.checkTableInfoInOthers(groupId,tableName);
            resultMap.put(ResultDesc.CODE,ResultDesc.SUCCESS);
            resultMap.put("result",result);
        } catch (NonePrintException e) {

            resultMap.put(ResultDesc.CODE,e.getErrCode());
            resultMap.put(ResultDesc.MSG,e.getErrMsg());
        }
        return resultMap;
    }

    @RequestMapping("/addTableInfo")
    @ResponseBody
    public Map<String,Object> addTableInfo(Dmgrouptable dmgrouptable){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            dmgrouptable.setId(UUID.randomUUID().toString());
            migrationService.addTableInfo(dmgrouptable);
            resultMap.put(ResultDesc.CODE,ResultDesc.SUCCESS);
            resultMap.put(ResultDesc.MSG,"保存成功");
        } catch (NonePrintException e) {

            resultMap.put(ResultDesc.CODE,e.getErrCode());
            resultMap.put(ResultDesc.MSG,e.getErrMsg());
        }
        return resultMap;
    }

    @RequestMapping("/editShowTableInfo")
    public String editShowTableInfo(@RequestParam(value = "id") String id, ModelMap modelMap){
        try {
            Dmgrouptable dmgrouptable = migrationService.findTableInfoById(id);
            modelMap.put("dmgrouptable",dmgrouptable);
        } catch (NonePrintException e) {
            logger.error(e.getMessage());
            return "/error/error_500";
        }
        return "/migrationConfig/table_edit";
    }

    @RequestMapping("/ajaxQueryTables")
    @ResponseBody
    public Map<String,Object> ajaxQueryTables(String groupId,String groupName){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            List<Dmgrouptable> dmgrouptables = migrationService.findTablesByGroupIdOrGroupName(groupId,groupName);
            resultMap.put("dmgrouptables",dmgrouptables);
            resultMap.put(ResultDesc.CODE,ResultDesc.SUCCESS);
        } catch (NonePrintException e) {

            resultMap.put(ResultDesc.CODE,e.getErrCode());
            resultMap.put(ResultDesc.MSG,e.getErrMsg());
        }
        return resultMap;
    }

    @RequestMapping("/ajaxEditTableInfo")
    @ResponseBody
    public Map<String,Object> ajaxEditTableInfo(Dmgrouptable dmgrouptable){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            migrationService.updateTableInfo(dmgrouptable);
            resultMap.put(ResultDesc.CODE,ResultDesc.SUCCESS);
            resultMap.put(ResultDesc.MSG,"编辑成功");
        } catch (NonePrintException e) {

            resultMap.put(ResultDesc.CODE,e.getErrCode());
            resultMap.put(ResultDesc.MSG,e.getErrMsg());
        }
        return resultMap;
    }

    @RequestMapping("/ajaxDeleteTableInfo")
    @ResponseBody
    public Map<String,Object> ajaxDeleteTableInfo(@RequestParam(value = "id") String id){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            migrationService.deleteTableInfo(id);
            resultMap.put(ResultDesc.CODE,ResultDesc.SUCCESS);
            resultMap.put(ResultDesc.MSG,"删除成功");
        } catch (NonePrintException e) {

            resultMap.put(ResultDesc.CODE,e.getErrCode());
            resultMap.put(ResultDesc.MSG,e.getErrMsg());
        }
        return resultMap;
    }

    @RequestMapping("/queryTableDetail")
    @ResponseBody
    public Map<String,Object> queryTableDetail(@RequestParam(value = "id") String id){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            Dmgrouptable dmgrouptable = migrationService.findTableInfoById(id);
            if(dmgrouptable == null){
                throw new NonePrintException(ErrorCodeDesc.TABLE_NOT_EXSIT.getCode(),ErrorCodeDesc.TABLE_NOT_EXSIT.getDesc());
            }
            Dmdatasource dmdatasource = dsInfoService.queryDSInfoById(dmgrouptable.getOriginaldsid());
            if(dmdatasource == null){
                throw new NonePrintException(ErrorCodeDesc.DATASOURCE_NOT_EXSIT.getCode(),ErrorCodeDesc.DATASOURCE_NOT_EXSIT.getDesc());
            }
            //查询主表的关联表(todo：删除时需要删除关联表信息---一张表可能存在多个关联表，但是一张表可能被多张表关联吗？)
            List<DmTableRef> dmTableRefList = migrationService.findTableRefByGrouIdAndtableName(dmgrouptable.getGroupid(),dmgrouptable.getOriginaltable());
            //查询表字段信息
            List<TableInfoVO> tableInfoVOList = migrationService.queryTableInfos(dmgrouptable,dmdatasource,dmTableRefList);
            //查询关联信息
            List<RelatedInfoVO> relatedInfoVOList = migrationService.queryRelatedInfo(dmgrouptable,dmdatasource,dmTableRefList);
            //获取表处理内容

            resultMap.put("dmgrouptable",dmgrouptable);
            resultMap.put("tableInfoVOList",tableInfoVOList);
            resultMap.put("relatedInfoVOList",relatedInfoVOList);
            resultMap.put(ResultDesc.CODE,ResultDesc.SUCCESS);
            resultMap.put(ResultDesc.MSG,"查询成功");
        } catch (NonePrintException e) {

            resultMap.put(ResultDesc.CODE,e.getErrCode());
            resultMap.put(ResultDesc.MSG,e.getErrMsg());
        }
        return resultMap;
    }

    @RequestMapping("/ajaxEditMigration")
    @ResponseBody
    public Map<String,Object> ajaxEditMigration(@RequestParam(value = "id") String id,
                                                @RequestParam(value = "handleProcedure") String handleProcedure){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            migrationService.updateMigrationProcedure(id,handleProcedure);
            resultMap.put(ResultDesc.CODE,ResultDesc.SUCCESS);
            resultMap.put(ResultDesc.MSG,"修改成功");
        } catch (NonePrintException e) {

            resultMap.put(ResultDesc.CODE,e.getErrCode());
            resultMap.put(ResultDesc.MSG,e.getErrMsg());
        }
        return resultMap;
    }

    @RequestMapping("/ajaxEditRestore")
    @ResponseBody
    public Map<String,Object> ajaxEditRestore(@RequestParam(value = "id") String id,
                                                @RequestParam(value = "restoreProcedure") String restoreProcedure){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            migrationService.updateRestoreProcedure(id,restoreProcedure);
            resultMap.put(ResultDesc.CODE,ResultDesc.SUCCESS);
            resultMap.put(ResultDesc.MSG,"修改成功");
        } catch (NonePrintException e) {

            resultMap.put(ResultDesc.CODE,e.getErrCode());
            resultMap.put(ResultDesc.MSG,e.getErrMsg());
        }
        return resultMap;
    }

    @RequestMapping("/ajaxEditHandle")
    @ResponseBody
    public Map<String,Object> ajaxEditHandle(@RequestParam(value = "id") String id,
                                              @RequestParam(value = "handleContent") String handleContent){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            migrationService.updateHandleContent(id,handleContent);
            resultMap.put(ResultDesc.CODE,ResultDesc.SUCCESS);
            resultMap.put(ResultDesc.MSG,"修改成功");
        } catch (NonePrintException e) {

            resultMap.put(ResultDesc.CODE,e.getErrCode());
            resultMap.put(ResultDesc.MSG,e.getErrMsg());
        }
        return resultMap;
    }

    @RequestMapping("/ajaxQueryGroupId")
    @ResponseBody
    public Map<String,Object> ajaxQueryGroupId(@RequestParam(value = "groupId") String groupId){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            List<String> groupIds = migrationService.findGroupIdByPartOfGroupId(groupId);
            resultMap.put("groupIds",groupIds);
            resultMap.put(ResultDesc.CODE,ResultDesc.SUCCESS);
        } catch (NonePrintException e) {

            resultMap.put(ResultDesc.CODE,e.getErrCode());
            resultMap.put(ResultDesc.MSG,e.getErrMsg());
        }
        return resultMap;
    }

    @RequestMapping("/ajaxQueryGroupName")
    @ResponseBody
    public Map<String,Object> ajaxQueryGroupName(@RequestParam(value = "groupName") String groupName){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            List<String> groupNames = migrationService.findGroupNameByPartOfGroupName(groupName);
            resultMap.put("groupNames",groupNames);
            resultMap.put(ResultDesc.CODE,ResultDesc.SUCCESS);
        } catch (NonePrintException e) {

            resultMap.put(ResultDesc.CODE,e.getErrCode());
            resultMap.put(ResultDesc.MSG,e.getErrMsg());
        }
        return resultMap;
    }

    @RequestMapping("/ajaxMigrate")
    @ResponseBody
    public Map<String,Object> ajaxMigrate(MigrationParamVO paramVO,HttpSession session){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            Dmuserinfo user = (Dmuserinfo) session.getAttribute("user");
            if(user == null){
                throw new NonePrintException(ErrorCodeDesc.SESSION_USER_OBSOLETE.getCode(),ErrorCodeDesc.SESSION_USER_OBSOLETE.getDesc());
            }
            migrationService.migrateData(user,paramVO);
            resultMap.put(ResultDesc.CODE,ResultDesc.SUCCESS);
            resultMap.put(ResultDesc.MSG,"迁移完成，请查看日志结果");
        } catch (NonePrintException e) {

            resultMap.put(ResultDesc.CODE,e.getErrCode());
            resultMap.put(ResultDesc.MSG,e.getErrMsg());
        }
        return resultMap;
    }

    @RequestMapping("/ajaxRestore")
    @ResponseBody
    public Map<String,Object> ajaxRestore(MigrationParamVO paramVO,HttpSession session){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            Dmuserinfo user = (Dmuserinfo) session.getAttribute("user");
            if(user == null){
                throw new NonePrintException(ErrorCodeDesc.SESSION_USER_OBSOLETE.getCode(),ErrorCodeDesc.SESSION_USER_OBSOLETE.getDesc());
            }
            migrationService.restoreData(user,paramVO);
            resultMap.put(ResultDesc.CODE,ResultDesc.SUCCESS);
            resultMap.put(ResultDesc.MSG,"还原完成，请查看日志结果");
        } catch (NonePrintException e) {

            resultMap.put(ResultDesc.CODE,e.getErrCode());
            resultMap.put(ResultDesc.MSG,e.getErrMsg());
        }
        return resultMap;
    }

}
















