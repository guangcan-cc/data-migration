package com.sinosoft.datamigration.po;

import javax.persistence.*;

/**
 * Created by Elvis on 2017/9/22.
 */
@Entity
@IdClass(TableRefKey.class)
public class DmTableRef {

    private String groupId;
    private String tableName;
    private String tableRef;

    @Id
    @Column(name = "groupId")
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Column(name = "tableName")
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Id
    @Column(name = "tableRef")
    public String getTableRef() {
        return tableRef;
    }

    public void setTableRef(String tableRef) {
        this.tableRef = tableRef;
    }
}
