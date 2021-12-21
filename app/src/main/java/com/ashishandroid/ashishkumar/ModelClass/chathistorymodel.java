package com.ashishandroid.ashishkumar.ModelClass;

public class chathistorymodel {

    String name,chattime, paid, earning, adminshare, tds, pgcharge;

    public chathistorymodel() {
    }

    public chathistorymodel(String name, String chattime, String paid, String earning, String adminshare, String tds, String pgcharge) {
        this.name = name;
        this.chattime = chattime;
        this.paid = paid;
        this.earning = earning;
        this.adminshare = adminshare;
        this.tds = tds;
        this.pgcharge = pgcharge;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChattime() {
        return chattime;
    }

    public void setChattime(String chattime) {
        this.chattime = chattime;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getEarning() {
        return earning;
    }

    public void setEarning(String earning) {
        this.earning = earning;
    }

    public String getAdminshare() {
        return adminshare;
    }

    public void setAdminshare(String adminshare) {
        this.adminshare = adminshare;
    }

    public String getTds() {
        return tds;
    }

    public void setTds(String tds) {
        this.tds = tds;
    }

    public String getPgcharge() {
        return pgcharge;
    }

    public void setPgcharge(String pgcharge) {
        this.pgcharge = pgcharge;
    }
}
