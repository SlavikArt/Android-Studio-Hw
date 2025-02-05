package com.slavikart.hw_08;

public class NameModel {
    private String name;
    private String date;
    private String meaning;

    public NameModel(String name, String date, String meaning) {
        this.name = name;
        this.date = date;
        this.meaning = meaning;
    }

    public String getName() { return name; }
    public String getDate() { return date; }
    public String getMeaning() { return meaning; }
}
