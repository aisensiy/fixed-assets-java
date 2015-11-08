package com.tw.domain;

import javax.ws.rs.core.MultivaluedMap;

public interface CategoryRepository {
    Category createCategory(MultivaluedMap<String, String> map);
}
