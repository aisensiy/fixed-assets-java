package com.tw.domain;

import com.tw.domain.impl.AssetRepositoryImpl;
import com.tw.mapper.AssetMapper;
import com.tw.mapper.BaseMapper;
import com.tw.mapper.CategoryMapper;
import com.tw.mapper.PolicyMapper;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class AssetRepositoryTest extends RepositoryTestBase {

    private AssetRepository assetRepository;
    private CategoryMapper categoryMapper;
    private PolicyMapper policyMapper;
    private AssetMapper assetMapper;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        assetMapper = sqlSession.getMapper(AssetMapper.class);
        final BaseMapper baseMapper = sqlSession.getMapper(BaseMapper.class);
        categoryMapper = sqlSession.getMapper(CategoryMapper.class);
        policyMapper = sqlSession.getMapper(PolicyMapper.class);
        assetRepository = new AssetRepositoryImpl(assetMapper, baseMapper);
    }

    @Test
    public void should_create_asset() throws Exception {
        MultivaluedMap<String, String> map = new MultivaluedHashMap<>();
        map.putSingle("name", "asset name");
        map.putSingle("price", "10000");
        map.putSingle("createdAt", new Timestamp(new Date().getTime()).toString());

        Asset asset = assetRepository.createAsset(map);
        assertThat(asset.id, not(0));
    }

    @Test
    public void should_get_asset_with_bases() throws Exception {
        Asset asset = saveAsset(new Category("name"), new Policy(10, 1, 1), "name1", 10000);
        asset = assetRepository.getAssetById(asset.getId());
        assertThat(asset.getCategory(), not(nullValue()));
        assertThat(asset.getCategory().getPolicy(), not(nullValue()));

        final Base newBase = asset.createNewBase(new Timestamp(new Date().getTime()));
        asset = assetRepository.addBase(newBase);
        assertThat(asset.bases.size(), is(1));
    }

    @Test
    public void should_sell_asset() throws Exception {
        Asset asset = saveAsset(new Category("name"), new Policy(10, 1, 1), "name1", 10000);
        final Base newBase = asset.createNewBase(new Timestamp(new Date().getTime()));
        asset = assetRepository.addBase(newBase);
        asset.sell();
        assetRepository.updateAsset(asset);
        asset = assetRepository.getAssetById(asset.getId());
        assertThat(asset.isSold(), is(true));
    }

    @Test
    public void should_list_assets() throws Exception {
        final Asset asset1 = saveAsset(new Category("name"), new Policy(10, 1, 1), "name1", 10000);
        final Asset asset2 = saveAsset(new Category("name"), new Policy(10, 1, 1), "name2", 10000);
        List<Asset> assetList = assetRepository.getAssets();
        assertThat(assetList.size(), is(2));
        assertThat(assetList.get(0).getName(), is("name1"));
    }

    private Asset saveAsset(Category category, Policy policy, String name, int price) {
        categoryMapper.saveCategory(category);
        policyMapper.savePolicy(category.getId(), policy);
        Asset asset = new Asset(name, price, category);
        assetMapper.saveAsset(asset);
        return assetMapper.findAssetById(asset.getId());
    }
}