package com.tw.api;

import com.tw.api.exception.NotFoundException;
import com.tw.api.util.Routing;
import com.tw.domain.Asset;
import com.tw.domain.AssetRepository;
import com.tw.domain.Base;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.Date;

@Path("assets")
public class AssetsApi {

    @POST
    public Response createAsset(Form form,
                                @Context AssetRepository assetRepository) {
        Asset asset = assetRepository.createAsset(form.asMap());
        if (asset == null) {
            return Response.status(400).build();
        }
        final Base newBase = asset.createNewBase(new Timestamp(new Date().getTime()));
        assetRepository.addBase(newBase);
        return Response.created(Routing.asset(asset)).build();
    }

    @Path("{assetId}")
    public AssetApi getAssetApi(@PathParam("assetId") int id,
                                @Context AssetRepository assetRepository) {
        Asset asset = assetRepository.getAssetById(id);
        if (asset == null)
            throw new NotFoundException();
        return new AssetApi(asset);
    }
}
