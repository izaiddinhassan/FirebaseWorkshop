package com.basikal.firebaseworkshop;

public class User {
    String uid;
    String email;
    String name;
    String phoneNo;
    String address;

    public User() {

    }

    public User(String uid,String email, String name,  String phoneNo, String address) {
        this.uid = uid;

        this.email = email;
        this.name = name;
        this.phoneNo = phoneNo;
        this.address = address;
    }

    public String getUid() {
        return uid;
    }

    public String getName() { return name; }

    public String getEmail() {
        return email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getAddress() {
        return address;
    }

}