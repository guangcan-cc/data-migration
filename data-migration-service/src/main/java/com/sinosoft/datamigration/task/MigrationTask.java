package com.sinosoft.datamigration.task;

import com.sinosoft.datamigration.dao.IDynamicDAO;
import com.sinosoft.datamigration.dao.ILogDAO;
import com.sinosoft.datamigration.dao.impl.LogDAOImpl;
import com.sinosoft.datamigration.exception.NonePrintException;
import com.sinosoft.datamigration.po.*;
import com.sinosoft.datamigration.service.ILogService;
import com.sinosoft.datamigration.util.*;
import com.sinosoft.datamigration.vo.MigrationParamVO;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Elvis on 2017/10/16.
 */
public class MigrationTask implements Runnable{

    private static final String MAIL_SERVER = "smtp.qq.com";
    private static final String MAIL_SENDER = "294093292@qq.com";
    private static final String MAIL_LOGIN_AUTH_CODE = "jhxvtwxnvsnlbhaj";//授权码


    @Resource
    private IDynamicDAO dynamicDAO = (IDynamicDAO) SpringContextHelper.getBean(IDynamicDAO.class);
    @Resource
    private ILogService logService = (ILogService) SpringContextHelper.getBean(ILogService.class);

    private List<Dmgrouptable> dmgrouptableList;
    private Dmmigrationlog dmmigrationlog;
    private MigrationParamVO paramVO;
    private Dmdatasource dmdatasource;
    private Dmgroup dmgroup;
    private int count;
    private String taskType;// 1：迁移 2：还原

    public MigrationTask(List<Dmgrouptable> dmgrouptableList, Dmmigrationlog dmmigrationlog,
                         MigrationParamVO paramVO, Dmdatasource dmdatasource, Dmgroup dmgroup,
                         int count, String taskType) {

        this.dmgrouptableList = dmgrouptableList;
        this.dmmigrationlog = dmmigrationlog;
        this.paramVO = paramVO;
        this.dmdatasource = dmdatasource;
        this.dmgroup = dmgroup;
        this.count = count;
        this.taskType = taskType;
    }


    @Override
    public void run() {

        List<Dmhandlemsglog> dmhandlemsglogs = new ArrayList<>();
        for(Dmgrouptable dmgrouptable : dmgrouptableList){

            Dmhandlemsglog dmhandlemsglog = new Dmhandlemsglog();
            dmhandlemsglog.setId(UUID.randomUUID().toString());
            dmhandlemsglog.setMigrationlogid(dmmigrationlog.getId());
            dmhandlemsglog.setOriginaldsname(dmgrouptable.getOriginaldsname());
            dmhandlemsglog.setOriginaldsusername(dmgrouptable.getOriginaldsusername());
            dmhandlemsglog.setOriginaltable(dmgrouptable.getOriginaltable());
            dmhandlemsglog.setTargetdsname(dmgrouptable.getTargetdsname());
            dmhandlemsglog.setTargetdsusername(dmgrouptable.getTargetdsusername());
            dmhandlemsglog.setTargettable(dmgrouptable.getTargettable());
            dmhandlemsglog.setIscleanup(dmgrouptable.getIscleanup());
            dmhandlemsglog.setHandlestarttime(new Date());
            dmhandlemsglog.setProcedurename(dmgrouptable.getHandleprocedurename());
            dmhandlemsglog.setProcedure(dmgrouptable.getHandleprocedure());
            if("1".equals(paramVO.getParamType())){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                dmhandlemsglog.setMigrationparam(sdf.format(paramVO.getStartTime()) + "---" + sdf.format(paramVO.getEndTime()));
            } else {
                dmhandlemsglog.setMigrationparam(paramVO.getParamValue());
            }

            if("1".equals(taskType)){//迁移
                if(!migrateDate(dmgrouptable,dmhandlemsglog,dmhandlemsglogs)){
                    continue;
                }
            } else {//还原
                if(!restoreDate(dmgrouptable,dmhandlemsglog,dmhandlemsglogs)){
                    continue;
                }
            }
            dmhandlemsglogs.add(MigrationLogUtils.createMigrationLogForSuccess(dmhandlemsglog,count));
        }
        logService.insertHandleLogs(dmhandlemsglogs);
        //更新日志为成功状态
        dmmigrationlog.setHandleresult(ConstantUtils.MIGRATION_SUCCESS);
        dmmigrationlog.setHandleendtime(new Date());
        logService.updatePO(dmmigrationlog);
    }

    private boolean migrateDate(Dmgrouptable dmgrouptable, Dmhandlemsglog dmhandlemsglog, List<Dmhandlemsglog> dmhandlemsglogs){
        //执行存储过程
        try {
            dynamicDAO.executeProcedure(dmdatasource,dmgroup.getId() + "." + dmgrouptable.getHandleprocedurename() + "()");
            return true;
        } catch (NonePrintException e) {
            //如果执行存储过程出错，记录日志并发送邮箱
            String failedMsg = ErrorCodeDesc.ERROR_IN_MIGRATION.getDesc().
                    replace("@tableName",dmgrouptable.getOriginaltable());

            dmhandlemsglogs.add(MigrationLogUtils.createMigrationLogForFailure(dmhandlemsglog, count, failedMsg));

            if("1".equals(dmgroup.getIssendemail())){
                String emailSet = dmgroup.getEmailset();
                if(AssertUtils.isEmpty(emailSet)){
                    return false;
                }

                MailUtils.sendEmail(MAIL_SERVER,MAIL_SENDER,MAIL_LOGIN_AUTH_CODE,MAIL_SENDER,
                        emailSet.split(";"),"迁移失败",failedMsg,"text/html;charset=utf-8");
            }
            return false;
        }
    }

    private boolean restoreDate(Dmgrouptable dmgrouptable, Dmhandlemsglog dmhandlemsglog, List<Dmhandlemsglog> dmhandlemsglogs){
        //执行存储过程
        try {
            dynamicDAO.executeProcedure(dmdatasource, dmgroup.getId() + "." + dmgrouptable.getRestoreprocedurename() + "()");
            return true;
        } catch (NonePrintException e){
            //如果执行存储过程出错，记录日志
            String failedMsg = ErrorCodeDesc.ERROR_IN_RESTORE.getDesc().replace("@tableName",dmgrouptable.getOriginaltable());
            dmhandlemsglogs.add(MigrationLogUtils.createMigrationLogForFailure(dmhandlemsglog,count,failedMsg));

            if("1".equals(dmgroup.getIssendemail())){
                String emailSet = dmgroup.getEmailset();
                if(AssertUtils.isEmpty(emailSet)){
                    return false;
                }

                MailUtils.sendEmail(MAIL_SERVER,MAIL_SENDER,MAIL_LOGIN_AUTH_CODE,MAIL_SENDER,
                        emailSet.split(";"),"还原失败",failedMsg,"text/html;charset=utf-8");
            }
            return false;
        }
    }

}
