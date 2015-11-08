package com.tw.mapper;

import com.tw.domain.Asset;
import org.apache.ibatis.annotations.Param;

public interface AssetMapper {
    int saveAsset(@Param("asset") Asset asset);

    Asset findAssetById(@Param("id") int id);

    int updateAsset(@Param("asset") Asset asset);
}
