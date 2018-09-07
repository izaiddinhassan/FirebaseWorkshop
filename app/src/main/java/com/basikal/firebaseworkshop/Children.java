package com.basikal.firebaseworkshop;

public class Children {
    String uid;
    String name;
    String mykid;
    String dob;
    String userUid;

    public Children(){

    }

    public Children(String uid, String name, String mykid, String dob, String userUid) {
        this.uid = uid;
        this.name = name;
        this.mykid = mykid;
        this.dob = dob;
        this.userUid = userUid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMykid() {
        return mykid;
    }

    public void setMykid(String mykid) {
        this.mykid = mykid;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }
}
