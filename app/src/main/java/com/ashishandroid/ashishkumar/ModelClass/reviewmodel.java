package com.ashishandroid.ashishkumar.ModelClass;

public class reviewmodel
{
    String header,desc,review,orderid;

    public reviewmodel() {
    }

    public reviewmodel(String header, String desc, String orderid, String review) {
        this.header = header;
        this.desc = desc;
        this.orderid = orderid;
        this.review = review;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
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
}

