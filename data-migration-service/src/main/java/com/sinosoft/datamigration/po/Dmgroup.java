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
public class Dmgroup {
    private String id;

    @Id
    @javax.persistence.Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String groupname;

    @Basic
    @javax.persistence.Column(name = "GROUPNAME")
    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    private String type;

    @Basic
    @javax.persistence.Column(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String creator;

    @Basic
    @javax.persistence.Column(name = "CREATOR")
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    private Date createtime;

    @Basic
    @javax.persistence.Column(name = "CREATETIME")
    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    private String issendemail;

    @Basic
    @javax.persistence.Column(name = "ISSENDEMAIL")
    public String getIssendemail() {
        return issendemail;
    }

    public void setIssendemail(String issendemail) {
        this.issendemail = issendemail;
    }

    private String emailset;

    @Basic
    @javax.persistence.Column(name = "EMAILSET")
    public String getEmailset() {
        return emailset;
    }

    public void setEmailset(String emailset) {
        this.emailset = emailset;
    }

    private String originaldsid;

    @Basic
    @javax.persistence.Column(name = "ORIGINALDSID")
    public String getOriginaldsid() {
        return originaldsid;
    }

    public void setOriginaldsid(String originaldsid) {
        this.originaldsid = originaldsid;
    }

    private String originaldsname;

    @Basic
    @javax.persistence.Column(name = "originaldsname")
    public String getOriginaldsname() {
        return originaldsname;
    }

    public void setOriginaldsname(String originaldsname) {
        this.originaldsname = originaldsname;
    }

    private String originaldsusername;

    @Basic
    @javax.persistence.Column(name = "originaldsusername")
    public String getOriginaldsusername() {
        return originaldsusername;
    }

    public void setOriginaldsusername(String originaldsusername) {
        this.originaldsusername = originaldsusername;
    }

    private String targetdsid;

    @Basic
    @javax.persistence.Column(name = "TARGETDSID")
    public String getTargetdsid() {
        return targetdsid;
    }

    public void setTargetdsid(String targetdsid) {
        this.targetdsid = targetdsid;
    }

    private String targetdsname;

    @Basic
    @javax.persistence.Column(name = "targetdsname")
    public String getTargetdsname() {
        return targetdsname;
    }

    public void setTargetdsname(String targetdsname) {
        this.targetdsname = targetdsname;
    }

    private String targetdsusername;

    @Basic
    @javax.persistence.Column(name = "targetdsusername")
    public String getTargetdsusername() {
        return targetdsusername;
    }

    public void setTargetdsusername(String targetdsusername) {
        this.targetdsusername = targetdsusername;
    }

    private String isforbidden;

    @Basic
    @javax.persistence.Column(name = "ISFORBIDDEN")
    public String getIsforbidden() {
        return isforbidden;
    }

    public void setIsforbidden(String isforbidden) {
        this.isforbidden = isforbidden;
    }

    private String explain;

    @Basic
    @javax.persistence.Column(name = "EXPLAIN")
    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    private String isdataextracted;

    @Basic
    @javax.persistence.Column(name = "ISDATAEXTRACTED")
    public String getIsdataextracted() {
        return isdataextracted;
    }

    public void setIsdataextracted(String isdataextracted) {
        this.isdataextracted = isdataextracted;
    }

    private String midtablename;

    @Basic
    @javax.persistence.Column(name = "MIDTABLENAME")
    public String getMidtablename() {
        return midtablename;
    }

    public void setMidtablename(String midtablename) {
        this.midtablename = midtablename;
    }

    private String isbackupmidtable;

    @Basic
    @javax.persistence.Column(name = "ISBACKUPMIDTABLE")
    public String getIsbackupmidtable() {
        return isbackupmidtable;
    }

    public void setIsbackupmidtable(String isbackupmidtable) {
        this.isbackupmidtable = isbackupmidtable;
    }

    private String defaultcondition;

    @Basic
    @javax.persistence.Column(name = "DEFAULTCONDITION")
    public String getDefaultcondition() {
        return defaultcondition;
    }

    public void setDefaultcondition(String defaultcondition) {
        this.defaultcondition = defaultcondition;
    }

    private String extractscript;

    @Basic
    @javax.persistence.Column(name = "EXTRACTSCRIPT")
    public String getExtractscript() {
        return extractscript;
    }

    public void setExtractscript(String extractscript) {
        this.extractscript = extractscript;
    }

    private String backupscript;

    @Basic
    @javax.persistence.Column(name = "BACKUPSCRIPT")
    public String getBackupscript() {
        return backupscript;
    }

    public void setBackupscript(String backupscript) {
        this.backupscript = backupscript;
    }

    private String issynchroorisource;

    @Basic
    @javax.persistence.Column(name = "IsSynchroOriSource")
    public String getIssynchroorisource() {
        return issynchroorisource;
    }

    public void setIssynchroorisource(String issynchroorisource) {
        this.issynchroorisource = issynchroorisource;
    }
}
