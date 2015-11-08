package com.tw.api;

import com.tw.domain.Asset;
import com.tw.domain.TestHelper;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

public class AssetApiTest extends ApiTestBase {
    @Test
    public void should_create_asset() throws Exception {
        when(assetRepository.createAsset(anyObject())).thenReturn(TestHelper.asset(1, new Asset("name", 100)));
        final Response response = target("/assets").request().post(Entity.form(new Form()));
        assertThat(response.getStatus(), is(201));
    }

    @Test
    public void should_fail_create_asset_with_code_400() throws Exception {
        when(assetRepository.createAsset(anyObject())).thenReturn(null);
        final Response response = target("/assets").request().post(Entity.form(new Form()));
        assertThat(response.getStatus(), is(400));
    }
}
