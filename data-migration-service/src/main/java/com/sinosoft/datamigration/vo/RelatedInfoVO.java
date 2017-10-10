package com.sinosoft.datamigration.vo;

import java.util.List;

/**
 * Created by Elvis on 2017/9/26.
 */
public class RelatedInfoVO {

    private String username;
    private String tableName;
    private String primaryKey;
    private String primaryKeyName;
    private List<String> foreignKeys;
    private List<String> foreignKeyNames;
    private String ext;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getPrimaryKeyName() {
        return primaryKeyName;
    }

    public void setPrimaryKeyName(String primaryKeyName) {
        this.primaryKeyName = primaryKeyName;
    }

    public List<String> getForeignKeys() {
        return foreignKeys;
    }

    public void setForeignKeys(List<String> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }

    public List<String> getForeignKeyNames() {
        return foreignKeyNames;
    }

    public void setForeignKeyNames(List<String> foreignKeyNames) {
        this.foreignKeyNames = foreignKeyNames;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
}
