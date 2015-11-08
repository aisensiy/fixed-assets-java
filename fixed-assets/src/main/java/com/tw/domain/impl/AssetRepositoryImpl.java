package com.tw.domain.impl;

import com.tw.domain.Asset;
import com.tw.domain.AssetRepository;
import com.tw.domain.Base;
import com.tw.mapper.AssetMapper;
import com.tw.mapper.BaseMapper;

import javax.ws.rs.core.MultivaluedMap;
import java.util.List;

public class AssetRepositoryImpl implements AssetRepository {
    private final AssetMapper assetMapper;
    private final BaseMapper baseMapper;

    public AssetRepositoryImpl(AssetMapper assetMapper, BaseMapper baseMapper) {
        this.assetMapper = assetMapper;
        this.baseMapper = baseMapper;
    }

    @Override
    public Asset createAsset(MultivaluedMap<String, String> map) {
        Asset asset = new Asset(map.getFirst("name"), Integer.parseInt(map.getFirst("price")), null);
        assetMapper.saveAsset(asset);
        return assetMapper.findAssetById(asset.getId());
    }

    @Override
    public Asset getAssetById(int id) {
        return assetMapper.findAssetById(id);
    }

    @Override
    public Asset addBase(Base newBase) {
        baseMapper.saveBase(newBase);
        return assetMapper.findAssetById(newBase.getAsset().getId());
    }

    @Override
    public void updateAsset(Asset asset) {
        assetMapper.updateAsset(asset);
    }

    @Override
    public List<Asset> getAssets() {
        return assetMapper.getAllAssets();
    }
}
