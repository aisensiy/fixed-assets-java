package com.tw.mapper;

import com.tw.domain.Category;
import org.apache.ibatis.annotations.Param;

public interface CategoryMapper {
    int save(@Param("category") Category category);

    Category getById(int id);
}
