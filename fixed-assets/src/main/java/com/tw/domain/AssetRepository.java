package com.tw.domain;

import javax.ws.rs.core.MultivaluedMap;
import java.util.List;

public interface AssetRepository {
    Asset createAsset(MultivaluedMap<String, String> map);

    Asset getAssetById(int id);

    Asset addBase(Base newBase);

    void updateAsset(Asset asset);

    List<Asset> getAssets();
}
