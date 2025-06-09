package com.slavikart.hw_10.model;

public class ShoppingList {
    private long id;
    private String name;
    private String description;
    private long date;

    public ShoppingList(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = System.currentTimeMillis();
    }

    public ShoppingList(long id, String name, long date, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public long getDate() { return date; }
} 