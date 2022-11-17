package com.example.code12_firebaseauthentication.models;

public class UserModel {

    public String name;
    public String email;
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

    public UserModel(String name, String email, String address, String collegeProgram, String cash, String role) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.collegeProgram = collegeProgram;
        this.cash = cash;
        this.role = role;
    }
}
