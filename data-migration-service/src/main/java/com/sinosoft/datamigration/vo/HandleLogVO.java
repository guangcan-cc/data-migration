package com.sinosoft.datamigration.vo;

import com.sinosoft.datamigration.po.Dmgrouptable;
import com.sinosoft.datamigration.po.Dmhandlemsglog;

/**
 * Created by Elvis on 2017/10/10.
 */
public class HandleLogVO {
    private Dmhandlemsglog dmhandlemsglog;
    private Dmgrouptable dmgrouptable;

    public Dmhandlemsglog getDmhandlemsglog() {
        return dmhandlemsglog;
    }

    public void setDmhandlemsglog(Dmhandlemsglog dmhandlemsglog) {
        this.dmhandlemsglog = dmhandlemsglog;
    }

    public Dmgrouptable getDmgrouptable() {
        return dmgrouptable;
    }

    public void setDmgrouptable(Dmgrouptable dmgrouptable) {
        this.dmgrouptable = dmgrouptable;
    }
}
