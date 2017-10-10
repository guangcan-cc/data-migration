package com.sinosoft.datamigration.po;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by Elvis on 2017/9/7.
 */
@Entity
public class Dmmigrationlog {
    private String id;
    private String groupid;
    private String handlecontent;
    private Date handlestarttime;
    private Date handleendtime;
    private String handleperson;
    private String handleresult;//0 失败 1成功 2正在执行
    private Date createtime;

    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "GROUPID")
    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    @Basic
    @Column(name = "HANDLECONTENT")
    public String getHandlecontent() {
        return handlecontent;
    }

    public void setHandlecontent(String handlecontent) {
        this.handlecontent = handlecontent;
    }

    @Basic
    @Column(name = "HANDLESTARTTIME")
    public Date getHandlestarttime() {
        return handlestarttime;
    }

    public void setHandlestarttime(Date handlestarttime) {
        this.handlestarttime = handlestarttime;
    }

    @Basic
    @Column(name = "HANDLEENDTIME")
    public Date getHandleendtime() {
        return handleendtime;
    }

    public void setHandleendtime(Date handleendtime) {
        this.handleendtime = handleendtime;
    }

    @Basic
    @Column(name = "HANDLEPERSON")
    public String getHandleperson() {
        return handleperson;
    }

    public void setHandleperson(String handleperson) {
        this.handleperson = handleperson;
    }

    @Basic
    @Column(name = "HANDLERESULT")
    public String getHandleresult() {
        return handleresult;
    }

    public void setHandleresult(String handleresult) {
        this.handleresult = handleresult;
    }

    @Basic
    @Column(name = "CREATETIME")
    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dmmigrationlog that = (Dmmigrationlog) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (groupid != null ? !groupid.equals(that.groupid) : that.groupid != null) return false;
        if (handlecontent != null ? !handlecontent.equals(that.handlecontent) : that.handlecontent != null)
            return false;
        if (handlestarttime != null ? !handlestarttime.equals(that.handlestarttime) : that.handlestarttime != null)
            return false;
        if (handleendtime != null ? !handleendtime.equals(that.handleendtime) : that.handleendtime != null)
            return false;
        if (handleperson != null ? !handleperson.equals(that.handleperson) : that.handleperson != null) return false;
        if (handleresult != null ? !handleresult.equals(that.handleresult) : that.handleresult != null) return false;
        if (createtime != null ? !createtime.equals(that.createtime) : that.createtime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (groupid != null ? groupid.hashCode() : 0);
        result = 31 * result + (handlecontent != null ? handlecontent.hashCode() : 0);
        result = 31 * result + (handlestarttime != null ? handlestarttime.hashCode() : 0);
        result = 31 * result + (handleendtime != null ? handleendtime.hashCode() : 0);
        result = 31 * result + (handleperson != null ? handleperson.hashCode() : 0);
        result = 31 * result + (handleresult != null ? handleresult.hashCode() : 0);
        result = 31 * result + (createtime != null ? createtime.hashCode() : 0);
        return result;
    }
}
