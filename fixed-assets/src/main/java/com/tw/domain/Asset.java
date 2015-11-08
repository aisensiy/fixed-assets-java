package com.tw.domain;

public class Asset {
    private final String name;
    private final int price;
    int id;

    public Asset(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }
}
