package com.ashishandroid.ashishkumar.ModelClass;

import java.util.ArrayList;

public class wallethistorymodel {

    String name1, date1, wallet1, tds, gateway;

    public wallethistorymodel() {
    }

    public wallethistorymodel(String name1, String date1, String wallet1, String tds, String gateway) {
        this.name1 = name1;
        this.date1 = date1;
        this.wallet1 = wallet1;
        this.gateway = gateway;
        this.tds = tds;
    }


    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getWallet1() {
        return wallet1;
    }

    public void setWallet1(String wallet1) {
        this.wallet1 = wallet1;
    }

    public String getGateway() {
        return gateway;
    }

    public String getTds() {
        return tds;
    }
}
