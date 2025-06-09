package com.slavikart.hw_10.model;

public class Type {
    private long id;
    private String label;
    private String rule;

    public Type(long id, String label, String rule) {
        this.id = id;
        this.label = label;
        this.rule = rule;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
    public String getRule() { return rule; }
    public void setRule(String rule) { this.rule = rule; }

    @Override
    public String toString() {
        return label;
    }
} 