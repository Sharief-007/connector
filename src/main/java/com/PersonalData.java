package com;

public class PersonalData {
    String email;
    String name;
    String username,gender,dob;

    public PersonalData(String name, String username, String gender, String dob) {
        this.name = name;
        this.username = username;
        this.gender = gender;
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public PersonalData(String email, String name, String username, String gender, String dob) {
        this.email = email;
        this.name = name;
        this.username = username;
        this.gender = gender;
        this.dob = dob;
    }
}
