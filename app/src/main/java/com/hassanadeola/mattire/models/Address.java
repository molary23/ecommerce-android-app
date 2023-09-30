package com.hassanadeola.mattire.models;

public class Address {

    private int number;

    private String street, city, zip;


    public Address(int number, String street, String city, String zip, String country) {
        this.number = number;
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.country = country;
    }

    public Address() {
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    private String country;
}
