package com.sinosoft.datamigration.vo;

import java.util.List;
import java.util.Map;

/**
 * Created by Elvis on 2017/9/25.
 */
public class TableInfoVO {

    private String tableName;
    private List<FieldInfoVO> fields;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<FieldInfoVO> getFields() {
        return fields;
    }

    public void setFields(List<FieldInfoVO> fields) {
        this.fields = fields;
    }
}
