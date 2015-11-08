package com.tw.api.util;

import com.tw.domain.Asset;
import com.tw.domain.Category;

import java.net.URI;

import static javax.ws.rs.core.UriBuilder.fromUri;

public class Routing {
    public static URI category(Category category) {
        return fromUri("/categories/{categoryId}").build(category.getId());
    }

    public static URI asset(Asset asset) {
        return fromUri("/assets/{assetId}").build(asset.getId());
    }
}
