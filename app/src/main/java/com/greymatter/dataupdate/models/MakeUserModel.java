package com.greymatter.dataupdate.models;

public class MakeUserModel {

    private String Name,Mobile,Password;

    public MakeUserModel(String name, String mobile, String password) {
        Name = name;
        Mobile = mobile;
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
