package com.sinosoft.datamigration.util;

import com.sinosoft.datamigration.po.Dmhandlemsglog;

import java.util.Date;

/**
 * Created by Elvis on 2017/10/16.
 */
public class MigrationLogUtils {

    /**
     * 成功失败日志
     */
    public static Dmhandlemsglog createMigrationLogForFailure(Dmhandlemsglog dmhandlemsglog, int count, String failedReason){
        dmhandlemsglog.setHandleendtime(new Date());
        dmhandlemsglog.setDatacount(count);
        dmhandlemsglog.setIssuccess(ConstantUtils.MIGRATION_FAILURE);
        dmhandlemsglog.setHandlecount(0);
        dmhandlemsglog.setFailedcount(count);
        dmhandlemsglog.setFailedreason(failedReason);
        return dmhandlemsglog;
    }

    /**
     * 成功详情日志
     */
    public static Dmhandlemsglog createMigrationLogForSuccess(Dmhandlemsglog dmhandlemsglog, int count){
        dmhandlemsglog.setHandleendtime(new Date());
        dmhandlemsglog.setDatacount(count);
        dmhandlemsglog.setIssuccess(ConstantUtils.MIGRATION_SUCCESS);
        dmhandlemsglog.setHandlecount(count);
        dmhandlemsglog.setFailedcount(0);
        return dmhandlemsglog;
    }
}
