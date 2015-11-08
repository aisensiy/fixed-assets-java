package com.tw.domain;

import java.util.Map;

public interface Record {
    Map<String, Object> toJson();
    Map<String, Object> toRefJson();
}
