package com.tw.domain;

import com.tw.api.util.Routing;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class Asset implements Record {
    private String name;
    private int price;
    Timestamp createdAt;
    int id;
    public Category category;
    List<Base> bases = new ArrayList<>();
    private boolean sold = false;

    public Asset(String name, int price, Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public Asset() {
    }

    public int getId() {
        return id;
    }

    public Base getCurrentBase() {
        if (bases.size() == 0)
            return null;
        return bases.get(bases.size() - 1);
    }

    @Override
    public Map<String, Object> toJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("uri", Routing.asset(this));
        map.put("id", id);
        map.put("price", price);
        map.put("created", createdAt);
        map.put("currentBase", getCurrentBase().toJson());
        map.put("bases", bases.stream().map(Base::toJson).collect(toList()));
        return map;
    }

    @Override
    public Map<String, Object> toRefJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("id", id);
        map.put("uri", Routing.asset(this));
        map.put("price", price);
        map.put("created", createdAt);
        map.put("currentBase", getCurrentBase().toJson());
        return map;
    }

    public Category getCategory() {
        return category;
    }

    public Base createNewBase(Timestamp timestamp) {
        final Timestamp currentTimestamp;
        final int amount;

        if (getCurrentBase() != null) {
            currentTimestamp = getCurrentBase().getCreatedAt();
            amount = getCurrentBase().getAmount();
        } else {
            currentTimestamp = createdAt;
            amount = price;
        }

        Policy policy = category.getPolicy();
        final int termLengthByMonth = policy.getTermLengthByMonth();
        final int oneMonth = 30 * 24 * 3600 * 1000;
        if (getCurrentBase() != null && termLengthByMonth * oneMonth + currentTimestamp.getTime() < timestamp.getTime()) {
            return null;
        }
        int depreciation = (int) (amount * (0.01 * policy.getPercentage()) * policy.getFactor());
        int newBaseAmount = amount - depreciation;

        final Base newBase = new Base(newBaseAmount, depreciation, timestamp, this);
        bases.add(newBase);

        return newBase;
    }

    public void sell() {
        sold = true;
    }

    public boolean isSold() {
        return sold;
    }

    public String getName() {
        return name;
    }
}
