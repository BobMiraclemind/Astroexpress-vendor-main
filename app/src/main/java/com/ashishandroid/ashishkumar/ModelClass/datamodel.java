package com.ashishandroid.ashishkumar.ModelClass;

public class datamodel {
    String name, email, uidu, dob, tob, pob;

    public datamodel(String name, String email, String uidu, String dob, String tob, String pob) {
        this.name = name;
        this.email = email;
        this.uidu = uidu;
        this.dob = dob;
        this.tob = tob;
        this.pob = pob;

    }

    public String getUidu() {
        return uidu;
    }

    public void setUidu(String uidu) {
        this.uidu = uidu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getTob() {
        return tob;
    }

    public void setTob(String tob) {
        this.tob = tob;
    }

    public String getPob() {
        return pob;
    }

    public void setPob(String pob) {
        this.pob = pob;
    }
}
