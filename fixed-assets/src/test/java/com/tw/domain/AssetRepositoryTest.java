package com.tw.domain;

import com.tw.domain.impl.AssetRepositoryImpl;
import com.tw.mapper.AssetMapper;
import com.tw.mapper.BaseMapper;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.sql.Timestamp;
import java.util.Date;

import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class AssetRepositoryTest extends RepositoryTestBase {

    private AssetRepository assetRepository;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        AssetMapper assetMapper = sqlSession.getMapper(AssetMapper.class);
        final BaseMapper baseMapper = sqlSession.getMapper(BaseMapper.class);
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
}