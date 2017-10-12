package com.sinosoft.datamigration.util;

/**
 * Created by Elvis on 2017/8/31.
 */
public enum ErrorCodeDesc {
    SYSTEM_ERROR("-1","系统出现异常，请稍后再试！"),
    USER_LOGIN_ERROR("1001","用户名密码不对"),
    USER_IS_NULL("1002","用户不存在，请联系管理员"),
    USERCODE_IS_NULL("1004","用户名或密码不能为空"),
    SESSION_USER_OBSOLETE("1005","请重新登录"),
    GROUP_NAME_REPEAT("1006","组名已存在"),
    TABLE_IN_GROUP_REPEAT("1007","该组已配置该表"),
    TABLE_IN_GROUP_RELATED("1008","该表已被表@tableName关联迁移，无法单独配置！"),
    RELATED_TABLE_IN_GROUP("1009","关联表@tableName已在组配置中，无法关联迁移"),
    FAILURE_IN_PROCEDURE("1010","创建存储过程失败"),
    FAILURE_IN_TABLE_UPDATE("1011","修改失败，该表信息已被删除"),
    TABLE_NOT_EXSIT("1012","表信息不存在"),
    DATASOURCE_NOT_EXSIT("1013","数据源不存在"),
    DSNAME_REPEAT("1014","数据源名已存在"),
    GROUP_NOT_EXSIT("1015","组信息不存在"),
    TABLE_NOT_EXSIT_IN_DS("1016","数据库不存在此原表"),
    NO_TABLE_IN_GROUP("1017","该组未配置表信息"),
    GROUPID_IS_NULL("1018","请输入组ID"),
    GROUPNAME_IS_NULL("1019","请输入组名"),
    TIMEPARAM_IS_NULL("1020","请输入起止时间"),
    PARAMVALUE_IS_NULL("1021","请输入迁移业务号"),
    ERROR_IN_DELETE_MID_TABLE("1022","删除中间表数据失败，请检查是否存在中间表"),
    ERROR_IN_EXTRACTED("1023","提数中间表过程失败，请校验提数脚本是否正确"),
    ERROR_IN_TABLE_HANDLE("1024","迁移表‘@tableName’创建/更新SQL出错"),
    ERROR_IN_MIGRATION("1025","迁移表‘@tableName’失败"),
    ERROR_IN_RESTORE("1026","还原表‘@tableName’失败"),
    ERROR_IN_EXECUTE_PROCEDURE("1027","执行存储过程出错"),
    FAILURE_IN_TABLE_DELETE("1028","删除失败，该表信息已被删除");

    private String code;
    private String desc;
    ErrorCodeDesc(String code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
