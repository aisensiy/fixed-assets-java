package com.tw.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

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
        map.put("currentBase", currentBase.toJson());
        map.put("bases", bases.stream().map(Base::toJson).collect(toList()));
        return map;
    }

    @Override
    public Map<String, Object> toRefJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("price", price);
        map.put("created", createdAt);
        map.put("currentBase", currentBase.toJson());
        return map;
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
