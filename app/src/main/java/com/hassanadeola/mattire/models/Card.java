package com.hassanadeola.mattire.models;

import androidx.annotation.NonNull;

public class Card {

    private String number;
    private String month;
        private String year;

    private String cvv;

    public Card(String number, String month,  String year,  String cvv) {
        this.number = number;
        this.month = month;
        this.year = year;
        this.cvv = cvv;
    }

    public Card() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public String getMonth() {
        return month;
    }

    public void setMonth( String month) {
        this.month = month;
    }


    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


    public String getCvv() {
        return cvv;
    }

    public void setCvv( String cvv) {
        this.cvv = cvv;
    }
}
