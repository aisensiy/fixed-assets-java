package com.tw.domain;

public class TestHelper {

    public static Category categoryWithPolicy(int id, Category category, Policy policy) {
        category.policy = policy;
        category.id = id;
        return category;
    }

    public static Policy policy(int id, Policy policy) {
        policy.id = id;
        return policy;
    }

    public static Asset asset(int id, Asset asset) {
        asset.id = id;
        return asset;
    }
}
