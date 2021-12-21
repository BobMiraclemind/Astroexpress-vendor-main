package com.ashishandroid.ashishkumar.ModelClass;

public class offersmodel {

    String header,desc, type, india, share, pays, ouid;

    public offersmodel() {
    }

    public offersmodel(String header, String desc, String type, String india, String share, String pays, String ouid) {
        this.header = header;
        this.desc = desc;
        this.type = type;
        this.india = india;
        this.share = share;
        this.pays = pays;
        this.ouid = ouid;
    }

    public String getOuid() {
        return ouid;
    }

    public void setOuid(String ouid) {
        this.ouid = ouid;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIndia() {
        return india;
    }

    public void setIndia(String india) {
        this.india = india;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }
}
