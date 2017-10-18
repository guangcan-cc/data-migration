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
    private String originaldsname;
    private String originaldsusername;
    private String originaltable;
    private String targetdsname;
    private String targetdsusername;
    private String targettable;
    private String iscleanup;
    private Date handlestarttime;
    private Date handleendtime;
    private String migrationparam;
    private String threadinfo;
    private Integer datacount;
    private Integer handlecount;
    private Integer failedcount;
    private String failedreason;
    private String issuccess;
    private String procedurename;
    private String procedure;

    @Basic
    @Column(name = "procedurename")
    public String getProcedurename() {
        return procedurename;
    }

    public void setProcedurename(String procedurename) {
        this.procedurename = procedurename;
    }

    @Basic
    @Column(name = "procedure")
    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "MIGRATIONPARAM")
    public String getMigrationparam() {
        return migrationparam;
    }

    public void setMigrationparam(String migrationparam) {
        this.migrationparam = migrationparam;
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

    @Basic
    @Column(name = "originaldsname")
    public String getOriginaldsname() {
        return originaldsname;
    }

    public void setOriginaldsname(String originaldsname) {
        this.originaldsname = originaldsname;
    }

    @Basic
    @Column(name = "originaldsusername")
    public String getOriginaldsusername() {
        return originaldsusername;
    }

    public void setOriginaldsusername(String originaldsusername) {
        this.originaldsusername = originaldsusername;
    }

    @Basic
    @Column(name = "originaltable")
    public String getOriginaltable() {
        return originaltable;
    }

    public void setOriginaltable(String originaltable) {
        this.originaltable = originaltable;
    }

    @Basic
    @Column(name = "targetdsname")
    public String getTargetdsname() {
        return targetdsname;
    }

    public void setTargetdsname(String targetdsname) {
        this.targetdsname = targetdsname;
    }

    @Basic
    @Column(name = "targetdsusername")
    public String getTargetdsusername() {
        return targetdsusername;
    }

    public void setTargetdsusername(String targetdsusername) {
        this.targetdsusername = targetdsusername;
    }

    @Basic
    @Column(name = "targettable")
    public String getTargettable() {
        return targettable;
    }

    public void setTargettable(String targettable) {
        this.targettable = targettable;
    }

    @Basic
    @Column(name = "iscleanup")
    public String getIscleanup() {
        return iscleanup;
    }

    public void setIscleanup(String iscleanup) {
        this.iscleanup = iscleanup;
    }
}
