package com.tw.api;

import com.tw.domain.Category;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class CategoryApi {
    private Category category;

    public CategoryApi(Category category) {
        this.category = category;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategory() {
        return Response.ok().entity(category.toJson()).build();
    }
}
