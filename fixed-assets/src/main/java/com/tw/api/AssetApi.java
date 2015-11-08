package com.tw.api;

import com.tw.domain.Asset;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class AssetApi {
    private Asset asset;

    public AssetApi(Asset asset) {
        this.asset = asset;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAsset() {
        return Response.ok().entity(asset.toJson()).build();
    }
}
