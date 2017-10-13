package com.sinosoft.datamigration.util;

import com.sinosoft.datamigration.po.Dmgroup;
import com.sinosoft.datamigration.po.Dmgrouptable;

import java.util.List;

/**
 * Created by Elvis on 2017/10/13.
 */
public class SQLUtils {

    public static String createMigrationProduce(String procedureName, Dmgrouptable dmgrouptable, Dmgroup dmgroup, List<String> relatedTables){
        StringBuilder produreBuilder = new StringBuilder("procedure ");
        produreBuilder.append(procedureName);
        produreBuilder.append(" is \n").append("begin \n");
        if(dmgroup.getIsdataextracted().equals(ConstantUtils.IS_EXTRACTED)){

            produreBuilder.append(createPartInsertSQL(dmgrouptable.getTargettable(),
                    dmgrouptable.getOriginaltable(),dmgroup.getMidtablename(),dmgroup.getDefaultcondition()));

            if(!AssertUtils.isEmpty(relatedTables)){
                for(String tableName : relatedTables){

                    String targetTableName = tableName + "HIS";

                    produreBuilder.append(createPartInsertSQL(targetTableName,
                            tableName,dmgroup.getMidtablename(),dmgroup.getDefaultcondition()));

                }
            }
            if(ConstantUtils.IS_CLEANUP.equals(dmgrouptable.getIscleanup())){
                /**
                 * 删除
                 * 先删除关联表
                 * 再删除主表
                 */
                if(!AssertUtils.isEmpty(relatedTables)){
                    for(String tableName : relatedTables){

                        produreBuilder.append(createPartDeleteSQL(tableName,
                                dmgroup.getMidtablename(),dmgroup.getDefaultcondition()));

                    }
                }

                produreBuilder.append(createPartDeleteSQL(dmgrouptable.getOriginaltable(),
                        dmgroup.getMidtablename(),dmgroup.getDefaultcondition()));

            }
        } else {//todo:不需要提数的

        }
        produreBuilder.append("commit; \n").append("end;");
        return produreBuilder.toString();
    }

    public static String createRestoreProduce(String restoreProcedureName, Dmgrouptable dmgrouptable, Dmgroup dmgroup, List<String> relatedTables){
        StringBuilder produreBuilder = new StringBuilder("procedure ");
        produreBuilder.append(restoreProcedureName);
        produreBuilder.append(" is \n").append("begin \n");
        if(dmgroup.getIsdataextracted().equals(ConstantUtils.IS_EXTRACTED)){

            produreBuilder.append(createPartInsertSQL(dmgrouptable.getOriginaltable(),
                    dmgrouptable.getTargettable(),dmgroup.getMidtablename(),dmgroup.getDefaultcondition()));

            if(!AssertUtils.isEmpty(relatedTables)){
                for(String tableName : relatedTables){

                    String targetTableName = tableName + "HIS";

                    produreBuilder.append(createPartInsertSQL(tableName,
                            targetTableName,dmgroup.getMidtablename(),dmgroup.getDefaultcondition()));
                }
            }
            /**
             * 删除
             * 先删除关联表
             * 再删除主表
             */
            if(!AssertUtils.isEmpty(relatedTables)){
                for(String tableName : relatedTables){

                    produreBuilder.append(createPartDeleteSQL(tableName + "HIS",dmgroup.getMidtablename(),
                            dmgroup.getDefaultcondition()));

                }
            }

            produreBuilder.append(createPartDeleteSQL(dmgrouptable.getTargettable(),
                    dmgroup.getMidtablename(),dmgroup.getDefaultcondition()));

        } else {//todo:不需要提数的

        }
        produreBuilder.append("commit; \n").append("end;");
        return produreBuilder.toString();
    }

    /**
     * 创建插入片段SQL
     * @param targetTableName
     * @param originalTableName
     * @param midTableName
     * @param condition
     * @return
     */
    public static String createPartInsertSQL(String targetTableName, String originalTableName, String midTableName, String condition){
        return "  insert into " + targetTableName +
                " select * from " + originalTableName +
                " where " + condition +
                " in ( select " + condition +
                " from " + midTableName + ");\n ";
    }

    /**
     * 删除片段SQL
     * @param tableName
     * @param midTableName
     * @param condition
     * @return
     */
    public static String createPartDeleteSQL(String tableName, String midTableName, String condition){
        return "  delete from " + tableName +
                " where " + condition +
                " in (select " + condition +
                " from " + midTableName + ");\n ";
    }
}
