package com.tw.domain;

import javax.ws.rs.core.MultivaluedMap;
import java.util.List;

public interface CategoryRepository {
    Category createCategory(MultivaluedMap<String, String> map);

    Category getCategoryById(int id);

    List<Category> getCategories();
}
