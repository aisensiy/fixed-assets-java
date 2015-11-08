package com.tw.domain;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class Asset implements Record {
    private final String name;
    private final int price;
    Timestamp createdAt;
    int id;
    private Base currentBase;

    public Asset(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public Base currentBase() {
        return currentBase;
    }

    @Override
    public Map<String, Object> toJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("price", price);
        map.put("created", createdAt);
        return map;
    }

    @Override
    public Map<String, Object> toRefJson() {
        return toJson();
    }
}
