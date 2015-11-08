package com.tw.domain;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class Base implements Record {
    private Timestamp createdAt;
    private int amount;
    private int depreciation;
    int id;

    public Asset getAsset() {
        return asset;
    }

    Asset asset;

    public Base(int amount, int depreciation, Timestamp createdAt, Asset asset) {

        this.amount = amount;
        this.depreciation = depreciation;
        this.createdAt = createdAt;
        this.asset = asset;
    }

    public Base() {
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
