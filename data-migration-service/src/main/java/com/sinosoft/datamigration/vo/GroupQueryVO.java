package com.sinosoft.datamigration.vo;

import java.util.Date;

/**
 * Created by Elvis on 2017/9/7.
 */
public class GroupQueryVO {

    private String id;
    private String groupname;
    private String type;
    private Date startTime;
    private Date endTime;
    private String creator;
    private String isForbidden;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getIsForbidden() {
        return isForbidden;
    }

    public void setIsForbidden(String isForbidden) {
        this.isForbidden = isForbidden;
    }
}
