package com;

import java.io.Serializable;
@SuppressWarnings("serial")
public class Address implements Serializable {
    private String profession,org,city;
    private String home;
    private String state;
    private String country;
    private String phone;
    public Address (){}
    public Address(String home, String state, String country, String phone) {
        this.home = home;
        this.state = state;
        this.country = country;
        this.phone = phone;
    }


    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
