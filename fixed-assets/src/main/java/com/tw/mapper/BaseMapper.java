package com.tw.mapper;

import com.tw.domain.Base;
import org.apache.ibatis.annotations.Param;

public interface BaseMapper {
    int saveBase(@Param("base") Base base);
}
