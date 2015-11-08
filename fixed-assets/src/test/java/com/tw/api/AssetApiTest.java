package com.tw.api;

import com.tw.domain.*;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

public class AssetApiTest extends ApiTestBase {
    @Test
    public void should_create_asset() throws Exception {
        final Asset asset = TestHelper.asset(1, new Asset("name", 100, null));
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
        final int oneyear = 12 * 30 * 24 * 86400000;
        Asset asset = TestHelper.assetWithCategoryAndBase(
                1, new Asset("name", 100, null),
                TestHelper.categoryWithPolicy(1, new Category("name"), TestHelper.policy(1, new Policy(10, 1, 12))),
                new Base(90, 10, new Timestamp(new Date().getTime() - oneyear), null));
        when(assetRepository.getAssetById(eq(1))).thenReturn(asset);

        final Response response = target("/assets/1/bases").request().post(Entity.form(new Form()));
        assertThat(response.getStatus(), is(200));
        final Base currentBase = asset.getCurrentBase();
        assertThat(currentBase, not(nullValue()));
        assertThat(currentBase.getAmount(), is(81));
    }

    @Test
    public void should_create_deprecidation_failed_with_invalid_time() throws Exception {
        final int notoneyear = 11 * 30 * 24 * 86400000;
        final int termasonyear = 12;
        Asset asset = TestHelper.assetWithCategoryAndBase(
                1, new Asset("name", 100, null),
                TestHelper.categoryWithPolicy(1, new Category("name"), TestHelper.policy(1, new Policy(10, 1, termasonyear))),
                new Base(90, 10, new Timestamp(new Date().getTime() - notoneyear), null));
        when(assetRepository.getAssetById(eq(1))).thenReturn(asset);

        final Response response = target("/assets/1/bases").request().post(Entity.form(new Form()));
        assertThat(response.getStatus(), is(409));
    }

    @Test
    public void should_list_all_depreciation() throws Exception {
        final long now = new Date().getTime();
        final int oneyear = 12 * 30 * 24 * 86400000;
        final Base base1 = TestHelper.base(1, new Base(100, 10, new Timestamp(now - 2 * oneyear), null));
        final Base base2 = TestHelper.base(1, new Base(100, 10, new Timestamp(now - oneyear), null));
        final Base base3 = TestHelper.base(1, new Base(100, 10, new Timestamp(now), null));
        final Policy policy = TestHelper.policy(1, new Policy(10, 1, 12));
        final Category category = TestHelper.categoryWithPolicy(1, new Category("name"), policy);
        final Asset asset = TestHelper.assetWithCategoryAndBase(1, new Asset("name", 100, null), category, base1, base2, base3);
        when(assetRepository.getAssetById(eq(1))).thenReturn(asset);

        final Response response = target("/assets/1").request().get();
        assertThat(response.getStatus(), is(200));
        Map map = response.readEntity(Map.class);
        List bases = (List) map.get("bases");
        assertThat(bases.size(), is(3));
        Map base = (Map) bases.get(0);
        assertThat(base.get("amount"), is(100));

    }

    @Test
    public void should_get_asset_by_id() throws Exception {
        Asset asset = TestHelper.asset(1, new Asset("name", 100, null));
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
        final Response response = target("/assets/1").request().get();
        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_sold_asset() throws Exception {
        final Policy policy = TestHelper.policy(1, new Policy(10, 1, 12));
        final Category category = TestHelper.categoryWithPolicy(1, new Category("name"), policy);
        final Asset asset = TestHelper.assetWithCategoryAndBase(1, new Asset("name", 100, null), category);
        asset.createNewBase(new Timestamp(new Date().getTime()));
        when(assetRepository.getAssetById(eq(1))).thenReturn(asset);
        final Response response = target("/assets/1/sold").request().post(Entity.form(new Form()));
        assertThat(response.getStatus(), is(200));
        assertThat(asset.isSold(), is(true));
    }

    @Test
    public void should_list_assets_with_current_base() throws Exception {
        when(assetRepository.getAssets()).thenReturn(asList(
                TestHelper.asset(1, new Asset("name1", 100000, null)),
                TestHelper.asset(2, new Asset("name2", 200000, null))
        ));
        final Response response = target("/assets").request().get();
        assertThat(response.getStatus(), is(200));
        List list = response.readEntity(List.class);
        assertThat(list.size(), is(2));
        Map map = (Map) list.get(0);
        assertThat(map.get("id"), is(1));
        assertThat(map.get("currentBase"), notNullValue());
    }
}
