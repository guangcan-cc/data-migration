package com.sinosoft.datamigration.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Elvis on 2017/9/4.
 */
public class DSInfoVOBase implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;
    private String dsName;
    private String username;
    private String password;
    private String serverip;
    private String port;
    private String servername;
    private String explain;
    private Date createTime;
    private String creator;
    private String isForbidden;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDsName() {
        return dsName;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getServerip() {
        return serverip;
    }

    public void setServerip(String serverip) {
        this.serverip = serverip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getServername() {
        return servername;
    }

    public void setServername(String servername) {
        this.servername = servername;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
