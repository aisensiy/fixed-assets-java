package com.tw.api;

import com.tw.api.util.Routing;
import com.tw.domain.Category;
import com.tw.domain.CategoryRepository;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

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
}
