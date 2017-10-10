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
public class Dmdatasource {
    private String id;
    private String dsname;
    private String username;
    private String password;
    private String serverip;
    private String port;
    private String servername;
    private String explain;
    private Date createtime;
    private String creator;
    private String isforbidden;

    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "DSNAME")
    public String getDsname() {
        return dsname;
    }

    public void setDsname(String dsname) {
        this.dsname = dsname;
    }

    @Basic
    @Column(name = "USERNAME")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "SERVERIP")
    public String getServerip() {
        return serverip;
    }

    public void setServerip(String serverip) {
        this.serverip = serverip;
    }

    @Basic
    @Column(name = "PORT")
    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Basic
    @Column(name = "SERVERNAME")
    public String getServername() {
        return servername;
    }

    public void setServername(String servername) {
        this.servername = servername;
    }

    @Basic
    @Column(name = "EXPLAIN")
    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    @Basic
    @Column(name = "CREATETIME")
    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Basic
    @Column(name = "CREATOR")
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Basic
    @Column(name = "ISFORBIDDEN")
    public String getIsforbidden() {
        return isforbidden;
    }

    public void setIsforbidden(String isforbidden) {
        this.isforbidden = isforbidden;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dmdatasource that = (Dmdatasource) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (dsname != null ? !dsname.equals(that.dsname) : that.dsname != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (serverip != null ? !serverip.equals(that.serverip) : that.serverip != null) return false;
        if (port != null ? !port.equals(that.port) : that.port != null) return false;
        if (servername != null ? !servername.equals(that.servername) : that.servername != null) return false;
        if (explain != null ? !explain.equals(that.explain) : that.explain != null) return false;
        if (createtime != null ? !createtime.equals(that.createtime) : that.createtime != null) return false;
        if (creator != null ? !creator.equals(that.creator) : that.creator != null) return false;
        if (isforbidden != null ? !isforbidden.equals(that.isforbidden) : that.isforbidden != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dsname != null ? dsname.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (serverip != null ? serverip.hashCode() : 0);
        result = 31 * result + (port != null ? port.hashCode() : 0);
        result = 31 * result + (servername != null ? servername.hashCode() : 0);
        result = 31 * result + (explain != null ? explain.hashCode() : 0);
        result = 31 * result + (createtime != null ? createtime.hashCode() : 0);
        result = 31 * result + (creator != null ? creator.hashCode() : 0);
        result = 31 * result + (isforbidden != null ? isforbidden.hashCode() : 0);
        return result;
    }
}
