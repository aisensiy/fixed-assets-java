package com.tw.domain;

import java.util.HashMap;
import java.util.Map;

public class Policy implements Record {
    private int percentage;
    private int factor;
    private int termLengthByMonth;
    int id;
    private Category category;

    public Policy(int percentage, int factor, int termLengthByMonth) {

        this.percentage = percentage;
        this.factor = factor;
        this.termLengthByMonth = termLengthByMonth;
    }

    public Policy() {
    }

    public int getPercentage() {
        return percentage;
    }

    public int getFactor() {
        return factor;
    }

    public int getTermLengthByMonth() {
        return termLengthByMonth;
    }

    public int getId() {
        return id;
    }

    @Override
    public Map<String, Object> toJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("percentage", percentage);
        map.put("factor", factor);
        map.put("termLengthByMonth", termLengthByMonth);
        return map;
    }

    @Override
    public Map<String, Object> toRefJson() {
        return toJson();
    }

    public Category getCategory() {
        return category;
    }
}
