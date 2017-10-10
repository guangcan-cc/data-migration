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
public class Dmhandlemsglog {
    private String id;
    private String migrationlogid;
    private String grouptableid;
    private Date handlestarttime;
    private Date handleendtime;
    private String threadinfo;
    private Integer datacount;
    private Integer handlecount;
    private Integer failedcount;
    private String failedreason;
    private String issuccess;

    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "MIGRATIONLOGID")
    public String getMigrationlogid() {
        return migrationlogid;
    }

    public void setMigrationlogid(String migrationlogid) {
        this.migrationlogid = migrationlogid;
    }

    @Basic
    @Column(name = "GROUPTABLEID")
    public String getGrouptableid() {
        return grouptableid;
    }

    public void setGrouptableid(String grouptableid) {
        this.grouptableid = grouptableid;
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
    @Column(name = "THREADINFO")
    public String getThreadinfo() {
        return threadinfo;
    }

    public void setThreadinfo(String threadinfo) {
        this.threadinfo = threadinfo;
    }

    @Basic
    @Column(name = "DATACOUNT")
    public Integer getDatacount() {
        return datacount;
    }

    public void setDatacount(Integer datacount) {
        this.datacount = datacount;
    }

    @Basic
    @Column(name = "HANDLECOUNT")
    public Integer getHandlecount() {
        return handlecount;
    }

    public void setHandlecount(Integer handlecount) {
        this.handlecount = handlecount;
    }

    @Basic
    @Column(name = "FAILEDCOUNT")
    public Integer getFailedcount() {
        return failedcount;
    }

    public void setFailedcount(Integer failedcount) {
        this.failedcount = failedcount;
    }

    @Basic
    @Column(name = "FAILEDREASON")
    public String getFailedreason() {
        return failedreason;
    }

    public void setFailedreason(String failedreason) {
        this.failedreason = failedreason;
    }

    @Basic
    @Column(name = "ISSUCCESS")
    public String getIssuccess() {
        return issuccess;
    }

    public void setIssuccess(String issuccess) {
        this.issuccess = issuccess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dmhandlemsglog that = (Dmhandlemsglog) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (migrationlogid != null ? !migrationlogid.equals(that.migrationlogid) : that.migrationlogid != null)
            return false;
        if (grouptableid != null ? !grouptableid.equals(that.grouptableid) : that.grouptableid != null) return false;
        if (handlestarttime != null ? !handlestarttime.equals(that.handlestarttime) : that.handlestarttime != null)
            return false;
        if (handleendtime != null ? !handleendtime.equals(that.handleendtime) : that.handleendtime != null)
            return false;
        if (threadinfo != null ? !threadinfo.equals(that.threadinfo) : that.threadinfo != null) return false;
        if (datacount != null ? !datacount.equals(that.datacount) : that.datacount != null) return false;
        if (handlecount != null ? !handlecount.equals(that.handlecount) : that.handlecount != null) return false;
        if (failedcount != null ? !failedcount.equals(that.failedcount) : that.failedcount != null) return false;
        if (failedreason != null ? !failedreason.equals(that.failedreason) : that.failedreason != null) return false;
        if (issuccess != null ? !issuccess.equals(that.issuccess) : that.issuccess != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (migrationlogid != null ? migrationlogid.hashCode() : 0);
        result = 31 * result + (grouptableid != null ? grouptableid.hashCode() : 0);
        result = 31 * result + (handlestarttime != null ? handlestarttime.hashCode() : 0);
        result = 31 * result + (handleendtime != null ? handleendtime.hashCode() : 0);
        result = 31 * result + (threadinfo != null ? threadinfo.hashCode() : 0);
        result = 31 * result + (datacount != null ? datacount.hashCode() : 0);
        result = 31 * result + (handlecount != null ? handlecount.hashCode() : 0);
        result = 31 * result + (failedcount != null ? failedcount.hashCode() : 0);
        result = 31 * result + (failedreason != null ? failedreason.hashCode() : 0);
        result = 31 * result + (issuccess != null ? issuccess.hashCode() : 0);
        return result;
    }
}
