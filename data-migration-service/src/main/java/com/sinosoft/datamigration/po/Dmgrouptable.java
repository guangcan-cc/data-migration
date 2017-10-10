package com.sinosoft.datamigration.po;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Elvis on 2017/9/7.
 */
@Entity
public class Dmgrouptable {
    private String id;
    private String groupid;
    private String originaldsid;
    private String originaldsname;
    private String originaldsusername;
    private String originaltable;
    private String targetdsid;
    private String targetdsname;
    private String targetdsusername;
    private String targettable;
    private String iscleanup;
    private String handleprocedurename;
    private String handleprocedure;
    private String restoreprocedurename;
    private String restoreprocedure;
    private String handletargettable;

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
    @Column(name = "ORIGINALDSID")
    public String getOriginaldsid() {
        return originaldsid;
    }

    public void setOriginaldsid(String originaldsid) {
        this.originaldsid = originaldsid;
    }

    @Basic
    @Column(name = "ORIGINALDSNAME")
    public String getOriginaldsname() {
        return originaldsname;
    }

    public void setOriginaldsname(String originaldsname) {
        this.originaldsname = originaldsname;
    }

    @Basic
    @Column(name = "ORIGINALDSUSERNAME")
    public String getOriginaldsusername() {
        return originaldsusername;
    }

    public void setOriginaldsusername(String originaldsusername) {
        this.originaldsusername = originaldsusername;
    }

    @Basic
    @Column(name = "ORIGINALTABLE")
    public String getOriginaltable() {
        return originaltable;
    }

    public void setOriginaltable(String originaltable) {
        this.originaltable = originaltable;
    }

    @Basic
    @Column(name = "TARGETDSID")
    public String getTargetdsid() {
        return targetdsid;
    }

    public void setTargetdsid(String targetdsid) {
        this.targetdsid = targetdsid;
    }

    @Basic
    @Column(name = "TARGETDSNAME")
    public String getTargetdsname() {
        return targetdsname;
    }

    public void setTargetdsname(String targetdsname) {
        this.targetdsname = targetdsname;
    }

    @Basic
    @Column(name = "TARGETDSUSERNAME")
    public String getTargetdsusername() {
        return targetdsusername;
    }

    public void setTargetdsusername(String targetdsusername) {
        this.targetdsusername = targetdsusername;
    }

    @Basic
    @Column(name = "TARGETTABLE")
    public String getTargettable() {
        return targettable;
    }

    public void setTargettable(String targettable) {
        this.targettable = targettable;
    }

    @Basic
    @Column(name = "ISCLEANUP")
    public String getIscleanup() {
        return iscleanup;
    }

    public void setIscleanup(String iscleanup) {
        this.iscleanup = iscleanup;
    }

    @Column(name = "handleprocedurename")
    public String getHandleprocedurename() {
        return handleprocedurename;
    }

    public void setHandleprocedurename(String handleprocedurename) {
        this.handleprocedurename = handleprocedurename;
    }

    @Column(name = "handleprocedure")
    public String getHandleprocedure() {
        return handleprocedure;
    }

    public void setHandleprocedure(String handleprocedure) {
        this.handleprocedure = handleprocedure;
    }

    @Column(name = "restoreprocedurename")
    public String getRestoreprocedurename() {
        return restoreprocedurename;
    }

    public void setRestoreprocedurename(String restoreprocedurename) {
        this.restoreprocedurename = restoreprocedurename;
    }

    @Column(name = "restoreprocedure")
    public String getRestoreprocedure() {
        return restoreprocedure;
    }

    public void setRestoreprocedure(String restoreprocedure) {
        this.restoreprocedure = restoreprocedure;
    }

    @Column(name = "handletargettable")
    public String getHandletargettable() {
        return handletargettable;
    }

    public void setHandletargettable(String handletargettable) {
        this.handletargettable = handletargettable;
    }
}
