package com.tw.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Asset implements Record {
    private final String name;
    private final int price;
    Timestamp createdAt;
    int id;
    Base currentBase;
    public Category category;
    List<Base> bases = new ArrayList<>();

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

    public Base createNewBase(Timestamp timestamp) {
        final Timestamp currentBaseTimestamp;
        final int amount;

        if (currentBase != null) {
            currentBaseTimestamp = currentBase.getCreatedAt();
            amount = currentBase.getAmount();
        } else {
            currentBaseTimestamp = createdAt;
            amount = price;
        }

        Policy policy = category.getPolicy();
        final int termLengthByMonth = policy.getTermLengthByMonth();
        if (currentBase != null && termLengthByMonth * 30 * 24 * 3600 * 1000 + currentBaseTimestamp.getTime() < timestamp.getTime()) {
            return null;
        }
        int depreciation = (int) (amount * (0.01 * policy.getPercentage()) * policy.getFactor());
        int newBaseAmount = amount - depreciation;

        final Base newBase = new Base(newBaseAmount, depreciation, timestamp);
        currentBase = newBase;
        bases.add(newBase);

        return newBase;
    }

    public Base getCurrentBase() {
        return currentBase;
    }
}
