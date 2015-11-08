package com.tw.api;

import com.tw.domain.Category;
import com.tw.domain.Policy;
import com.tw.domain.TestHelper;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

public class CategoryApiTest extends ApiTestBase {
    @Test
    public void should_create_category_return_201() throws Exception {
        MultivaluedMap<String, String> map = new MultivaluedHashMap<>();

        Category category = TestHelper.categoryWithPolicy(1, new Category("name"), TestHelper.policy(1, new Policy(10, 1, 12)));
        when(categoryRepository.createCategory(eq(map))).thenReturn(category);

        final Response response = target("/categories").request().post(Entity.form(new Form(map)));
        assertThat(response.getStatus(), is(201));
        assertThat(response.getHeaderString("Location"), endsWith("/categories/1"));
    }

    @Test
    public void should_return_400_with_wrong_form_parameters() throws Exception {
        MultivaluedMap<String, String> map = new MultivaluedHashMap<>();

        when(categoryRepository.createCategory(eq(map))).thenReturn(null);

        final Response response = target("/categories").request().post(Entity.form(new Form(map)));
        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_get_category_by_id_with_200() throws Exception {
        final Category category = TestHelper.categoryWithPolicy(1,
                new Category("name"), TestHelper.policy(1, new Policy(10, 1, 12)));
        when(categoryRepository.getCategoryById(eq(1))).thenReturn(category);
        final Response response = target("/categories/1").request().get();
        assertThat(response.getStatus(), is(200));
        Map map = response.readEntity(Map.class);
        assertThat(map.get("id"), is(1));
        assertThat(map.get("name"), is("name"));
        Map policy = (Map) map.get("policy");
        assertThat(policy.get("percentage"), is(10));
        assertThat(policy.get("factor"), is(1));
        assertThat(policy.get("termLengthByMonth"), is(12));
    }

    @Test
    public void should_return_404_if_category_not_exists() throws Exception {
        when(categoryRepository.getCategoryById(eq(1))).thenReturn(null);
        final Response response = target("/categories/1").request().get();
        assertThat(response.getStatus(), is(404));
    }
}
