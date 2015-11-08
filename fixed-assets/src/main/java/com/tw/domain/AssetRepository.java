package com.tw.domain;

import javax.ws.rs.core.MultivaluedMap;

public interface AssetRepository {
    Asset createAsset(MultivaluedMap<String, String> map);

    Asset getAssetById(int id);

    Asset addBaseToAsset(Base newBase, Asset asset);

    void updateAsset(Asset asset);
}
