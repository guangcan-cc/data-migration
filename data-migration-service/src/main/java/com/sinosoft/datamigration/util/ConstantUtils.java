package com.sinosoft.datamigration.util;

/**
 * Created by Elvis on 2017/9/20.
 */
public interface ConstantUtils {

    //是否禁用 1禁用 0不禁用
    String IS_FORBIDDEN = "1";
    String IS_NOT_FORBIDDEN = "0";

    //是否提数 1是 0否
    String IS_EXTRACTED = "1";
    String IS_NOT_EXTRACTED = "0";

    // R 外键 P主键
    String FOREIGN_TYPE = "R";
    String PRIMARY_TYPE = "P";

    //是否清理原数据 1是 0否
    String IS_CLEANUP = "1";
    String IS_NOT_CLEANUP = "0";

    //迁移结果
    String MIGRATION_FAILURE = "0";
    String MIGRATION_SUCCESS = "1";
    String MIGRATION_IN_PROGRESS = "2";
}
