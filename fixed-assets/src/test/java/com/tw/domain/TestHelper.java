package com.tw.domain;

import java.util.Date;
import java.sql.Timestamp;

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
        asset.createdAt = new Timestamp(new Date().getTime());
        return asset;
    }

    public static Asset assetWithCategoryAndBase(int id, Asset asset, Category category, Base base) {
        asset.id = id;
        asset.category = category;
        asset.currentBase = base;
        return asset;
    }
}
