package com.tw.domain;

import com.tw.api.util.Routing;

import java.util.HashMap;
import java.util.Map;

public class Category implements Record {
    int id;
    Policy policy;
    String name;

    public Category(String name) {

        this.name = name;

    }

    public Category() {
    }

    public int getId() {
        return id;
    }

    public Policy getPolicy() {
        return policy;
    }

    public String getName() {
        return name;
    }

    @Override
    public Map<String, Object> toJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("id", id);
        map.put("uri", Routing.category(this).toString());
        map.put("policy", policy.toJson());
        return map;
    }

    @Override
    public Map<String, Object> toRefJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("uri", Routing.category(this).toString());
        map.put("id", id);
        return map;
    }
}
