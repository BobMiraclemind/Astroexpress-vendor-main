package com.ashishandroid.ashishkumar.try1;

/**
 * Created by ashishkumar on 31-01-2018.
 */

class User {
    public String displayname;
    public String uid;
    public String email;
    public String chatprice, callprice;
    public String chattime, calltime, reporttime, chatstatus, callstatus, reportstatus;
    public String verified, offers;


    public String pancard;


    // Landline to AlternatemobileNo

    public String mobile, address, alternatemobileNo, dateOfBirth, experience, longbio, shortbio, aadhaar, hours;

    public long createdAt;

    public User() {
    }

    public User(String displayname, String email, String uid, String mobile, String address, String alternatemobileNo, String dateOfBirth, String experience, String longbio, String shortbio, String aadhaar, String hours,
                long createdAt, String chatprice, String callprice, String chattime, String calltime, String reporttime, String chatstatus,
                String callstatus, String reportstatus, String verified, String offers, String pancard) {
        this.displayname = displayname;
        this.uid = uid;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
        this.alternatemobileNo = alternatemobileNo;
        this.dateOfBirth = dateOfBirth;
        this.experience = experience;
        this.longbio = longbio;
        this.shortbio = shortbio;
        //this.Account = account;
        this.aadhaar = aadhaar;
        this.hours = hours;
        this.createdAt = createdAt;

        this.chatprice = chatprice;
        this.callprice = callprice;

        this.chattime = chattime;
        this.calltime = calltime;
        this.reporttime = reporttime;
        this.chatstatus = chatstatus;
        this.callstatus = callstatus;
        this.reportstatus = reportstatus;

        this.verified = verified;
        this.offers = offers;

        this.pancard = pancard;



    }

    public String getOffers() {
        return offers;
    }

    public String getChattime() {
        return chattime;
    }



    public String getUid() {
        return uid;
    }



    public void setUid(String uid) {
        this.uid = uid;
    }


    public void setChattime(String chattime) {
        this.chattime = chattime;
    }



    public void setOffers(String offers) {
        this.offers = offers;
    }


}
