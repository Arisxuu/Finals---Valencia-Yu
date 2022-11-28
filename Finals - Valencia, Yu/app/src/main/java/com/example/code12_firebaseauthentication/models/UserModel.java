package com.example.code12_firebaseauthentication.models;

public class UserModel {

    public String name;
    public String email;
    public String birthday;
    public String address;
    public String collegeProgram;
    public String cash;
    public String role;
    public UserModel() {

    }

    /*public UserModel(String name, String email, String address, String collegeProgram) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.collegeProgram = collegeProgram;
    }*/

    public UserModel(String email, String name, String birthday, String address, String cash, String role) {
        this.email = email;
        this.name = name;
        this.birthday = birthday;
        this.address = address;
        this.cash = cash;
        this.role = role;
    }
}
