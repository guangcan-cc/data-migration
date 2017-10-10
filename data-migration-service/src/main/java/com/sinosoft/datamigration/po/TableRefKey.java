package com.sinosoft.datamigration.po;

import javax.persistence.Embedded;
import java.io.Serializable;

/**
 * Created by Elvis on 2017/9/22.
 */
public class TableRefKey implements Serializable{

    private String groupId;
    private String tableRef;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTableRef() {
        return tableRef;
    }

    public void setTableRef(String tableRef) {
        this.tableRef = tableRef;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof TableRefKey)){
            return false;
        }
        TableRefKey tableRefKey = (TableRefKey) obj;
        return this.groupId.equals(tableRefKey.getGroupId()) && this.tableRef.equals(tableRefKey.getTableRef());
    }

    @Override
    public int hashCode() {
        int result = groupId != null ? groupId.hashCode() : 0;
        result = 31 * result + (tableRef != null ? tableRef.hashCode() : 0);
        return result;
    }
}
