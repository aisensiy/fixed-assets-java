package com.tw.api;

import com.tw.domain.Asset;
import com.tw.domain.AssetRepository;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
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

    @Path("bases")
    public BasesApi getBasesApi() {
        return new BasesApi(asset);
    }

    @Path("sold")
    @POST
    public Response sold(@Context AssetRepository assetRepository) {
        asset.sell();
        assetRepository.updateAsset(asset);
        return Response.ok().build();
    }
}
