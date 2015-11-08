package com.tw.api;

import com.tw.domain.Asset;
import com.tw.domain.AssetRepository;
import com.tw.domain.Base;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.Date;

public class BasesApi {
    private Asset asset;

    public BasesApi(Asset asset) {

        this.asset = asset;
    }

    @POST
    public Response createNewBase(@Context AssetRepository assetRepository) {
        final Base newBase = asset.createNewBase(new Timestamp(new Date().getTime()));
        if (newBase != null) {
            assetRepository.addBaseToAsset(newBase, asset);
            return Response.ok().build();
        } else {
            return Response.status(409).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBases() {
        return Response.ok().build();
    }
}
