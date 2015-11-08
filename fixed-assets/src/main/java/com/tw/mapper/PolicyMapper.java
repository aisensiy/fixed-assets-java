package com.tw.mapper;

import com.tw.domain.Policy;
import org.apache.ibatis.annotations.Param;

public interface PolicyMapper {
    int savePolicy(@Param("categoryId") int id, @Param("policy") Policy policy);
}
