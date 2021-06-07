package com.app.pixelsfirenotes.inventory;
import android.net.Uri;

public class Set_item {
    private String imgres;
    private String Title;
    private String qty;
    private String initqty;

    public Set_item() {
    }

    public Set_item(String aimgres, String aTitle, String aqty, String ainitqty) {
        this.imgres = aimgres;
        this.Title = aTitle;
        this.qty = aqty;
        this.initqty = ainitqty;
    }

    public String getImgres() {
        return imgres;
    }

    public String getTitle() {
        return Title;
    }

    public String getQty() {
        return qty;
    }

    public String getInitqty() {
        return initqty;
    }

    public void setInitqtyQty(String t) {
        this.initqty = t;
    }

    public void setImgres(String t) {
        this.imgres = t;
    }

    public void setTitle(String t) {
        this.Title = t;
    }

    public void setQty(String t) {
        this.qty = t;
    }
}