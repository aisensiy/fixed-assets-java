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
        final int oneMonth = 1;

        Category category = new Category("name");
        categoryMapper.saveCategory(category);
        Policy policy = new Policy(10, 1, oneMonth);
        policyMapper.savePolicy(category.getId(), policy);

        Asset asset = new Asset("name", 10000, category);
        assetMapper.saveAsset(asset);

        asset = assetRepository.getAssetById(asset.getId());
        assertThat(asset.getCategory(), not(nullValue()));
        assertThat(asset.getCategory().getPolicy(), not(nullValue()));

        final Base newBase = asset.createNewBase(new Timestamp(new Date().getTime()));
        asset = assetRepository.addBase(newBase);
        assertThat(asset.bases.size(), is(1));

        asset.sell();
        assetRepository.updateAsset(asset);
        asset = assetRepository.getAssetById(asset.getId());
        assertThat(asset.isSold(), is(true));
    }
}