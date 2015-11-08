package com.tw.domain;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class Base implements Record {
    private Timestamp createdAt;
    private int amount;
    private final int depreciation;
    public int id;

    public Base(int amount, int depreciation, Timestamp createdAt) {

        this.amount = amount;
        this.depreciation = depreciation;
        this.createdAt = createdAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public Map<String, Object> toJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("amount", amount);
        map.put("createdAt", createdAt);
        map.put("depreciation", depreciation);
        return map;
    }

    @Override
    public Map<String, Object> toRefJson() {
        return null;
    }
}
