package com.tw.api;

import com.tw.api.exception.NotFoundException;
import com.tw.api.util.Routing;
import com.tw.domain.Category;
import com.tw.domain.CategoryRepository;
import com.tw.domain.Record;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.stream.Collectors;

@Path("categories")
public class CategoriesApi {
    @POST
    public Response createCategory(Form form,
                                   @Context CategoryRepository categoryRepository) {
        final Category category = categoryRepository.createCategory(form.asMap());
        if (category != null)
            return Response.created(Routing.category(category)).build();
        else
            return Response.status(400).build();
    }

    @Path("{categoryId}")
    public CategoryApi getCategory(@PathParam("categoryId") int id,
                                   @Context CategoryRepository categoryRepository) {
        Category category = categoryRepository.getCategoryById(id);
        if (category == null) {
            throw new NotFoundException();
        }
        return new CategoryApi(category);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategories(@Context CategoryRepository categoryRepository) {
        return Response.ok().entity(
                categoryRepository.getCategories().stream().map(Record::toJson).collect(Collectors.toList())).build();
    }
}
