package com.tw.api;

import com.tw.domain.Category;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CategoryApiTest extends ApiTestBase {
    @Test
    public void should_create_category_return_201() throws Exception {
        MultivaluedMap<String, String> map = new MultivaluedHashMap<>();
        map.putSingle("name", "cate1");

        Category category = mock(Category.class);
        when(category.getId()).thenReturn(1);
        when(categoryRepository.createCategory(eq(map))).thenReturn(category);

        final Response response = target("/categories").request().post(Entity.form(new Form(map)));
        assertThat(response.getStatus(), is(201));
        assertThat(response.getHeaderString("Location"), endsWith("/categories/1"));
    }


}
