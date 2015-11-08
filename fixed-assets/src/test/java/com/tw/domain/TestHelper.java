package com.tw.domain;

import java.sql.Timestamp;
import java.util.Date;

import static java.util.Arrays.asList;

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

    public static Asset assetWithCategoryAndBase(int id, Asset asset, Category category, Base... bases) {
        asset.id = id;
        asset.category = category;
        if (bases.length == 0) {
            return asset;
        }
        asset.currentBase = bases[bases.length - 1];
        asset.bases.addAll(asList(bases));
        return asset;
    }

    public static Base base(int id, Base base) {
        base.id = id;
        return base;
    }
}
