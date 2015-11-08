package com.tw.api;

import com.tw.domain.*;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import java.util.Map;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

public class AssetApiTest extends ApiTestBase {
    @Test
    public void should_create_asset() throws Exception {
        final Asset asset = TestHelper.asset(1, new Asset("name", 100));
        when(assetRepository.createAsset(anyObject())).thenReturn(asset);
        final Response response = target("/assets").request().post(Entity.form(new Form()));
        assertThat(response.getStatus(), is(201));
    }

    @Test
    public void should_fail_create_asset_with_code_400() throws Exception {
        when(assetRepository.createAsset(anyObject())).thenReturn(null);
        final Response response = target("/assets").request().post(Entity.form(new Form()));
        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_create_depreciation_with_validate_time() throws Exception {
        Asset asset = TestHelper.assetWithCategoryAndBase(
                1, new Asset("name", 100),
                TestHelper.categoryWithPolicy(1, new Category("name"), TestHelper.policy(1, new Policy(10, 1, 12))),
                null);
        when(assetRepository.getAssetById(eq(1))).thenReturn(asset);

        final Response response = target("/assets/1/bases").request().post(Entity.form(new Form()));
        assertThat(response.getStatus(), is(200));
        final Base currentBase = asset.getCurrentBase();
        assertThat(currentBase, not(nullValue()));
        assertThat(currentBase.getAmount(), is(90));
    }

    @Test
    public void should_get_asset_by_id() throws Exception {
        Asset asset = TestHelper.asset(1, new Asset("name", 100));
        when(assetRepository.getAssetById(eq(1))).thenReturn(asset);
        final Response response = target("/assets/1/").request().get();
        assertThat(response.getStatus(), is(200));
        Map map = response.readEntity(Map.class);
        assertThat(map.get("name"), is("name"));
        assertThat(map.get("price"), is(100));
    }

    @Test
    public void should_get_404_if_asset_not_exist() throws Exception {
        when(assetRepository.getAssetById(eq(1))).thenReturn(null);
        final Response response = target("/assets/1/").request().get();
        assertThat(response.getStatus(), is(404));
    }
}
