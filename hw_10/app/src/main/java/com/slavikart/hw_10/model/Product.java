package com.slavikart.hw_10.model;

public class Product {
    private long id;
    private String name;
    private double count;
    private long listId;
    private int checked;
    private long countType;

    public Product(long id, String name, double count, long listId, int checked, long countType) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.listId = listId;
        this.checked = checked;
        this.countType = countType;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getCount() { return count; }
    public void setCount(double count) { this.count = count; }
    public long getListId() { return listId; }
    public void setListId(long listId) { this.listId = listId; }
    public int getChecked() { return checked; }
    public void setChecked(int checked) { this.checked = checked; }
    public long getCountType() { return countType; }
    public void setCountType(long countType) { this.countType = countType; }
} 