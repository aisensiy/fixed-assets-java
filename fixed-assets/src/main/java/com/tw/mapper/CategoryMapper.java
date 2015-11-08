package com.tw.mapper;

import com.tw.domain.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper {
    int saveCategory(@Param("category") Category category);

    Category getCategoryById(@Param("id") int id);

    List<Category> getAllCategories();
}
