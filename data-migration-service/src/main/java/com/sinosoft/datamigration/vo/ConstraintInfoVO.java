package com.sinosoft.datamigration.vo;

/**
 * Created by Elvis on 2017/9/26.
 */
public class ConstraintInfoVO {

    private String constraintName;
    private String constrainType;
    private String columnName;

    public String getConstraintName() {
        return constraintName;
    }

    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    public String getConstrainType() {
        return constrainType;
    }

    public void setConstrainType(String constrainType) {
        this.constrainType = constrainType;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}
